package xyz.iwolfking.vhapi.api.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;


public abstract class AbstractVaultMobsProvider implements DataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    protected final DataGenerator generator;
    protected final String modid;

    private final LinkedHashMap<ResourceLocation, VaultMob> map = new LinkedHashMap<>();

    protected AbstractVaultMobsProvider(DataGenerator generator, String modid) {
        this.generator = generator;
        this.modid = modid;
    }

    protected abstract void registerOverrides();

    protected void add(ResourceLocation entityId, Consumer<VaultMobBuilder> consumer) {
        VaultMobBuilder b = new VaultMobBuilder(entityId);
        consumer.accept(b);
        map.put(entityId, b.build());
    }

    @Override
    public void run(HashCache cache) throws IOException {
        map.clear();
        registerOverrides();

        //Vault mob stats file
        AttributeOverridesFile file = new AttributeOverridesFile(mapToStringKeyedMap(map));

        Path out = generator.getOutputFolder().resolve("data/" + modid + "/vault_configs/vault/mobs/mob_stats.json");
        DataProvider.save(GSON, cache, GSON.toJsonTree(file), out);

        //Mob XP values -- Entity groups
        MobXPStatsFileBuilder mobXPBuilder = new MobXPStatsFileBuilder();
        Map<String, Map<String, List<String>>> entityGroupsFile = new HashMap<>();
        List<BestiaryEntry> bestiaryEntries = new ArrayList<>();
        entityGroupsFile.put("groups", new HashMap<>());

        map.forEach(((resourceLocation, vaultMob) -> {
            if(vaultMob.xpReward >= 0) {
                mobXPBuilder.add(resourceLocation.toString(), vaultMob.xpReward);
            }
            vaultMob.entityGroups.forEach(resourceLocation1 -> {
                if(entityGroupsFile.get("groups").containsKey(resourceLocation1.toString())) {
                    entityGroupsFile.get("groups").get(resourceLocation1.toString()).add(resourceLocation.toString());
                }
                else {
                    List<String> mobIds = new ArrayList<>();
                    mobIds.add(resourceLocation.toString());
                    entityGroupsFile.get("groups").put(resourceLocation1.toString(), mobIds);
                }
            });
            if(vaultMob.entry != null) {
                bestiaryEntries.add(vaultMob.entry);
            }
        }));


        Path xpOut = generator.getOutputFolder().resolve("data/" + modid + "/vault_configs/vault/stats/mob_xp.json");
        DataProvider.save(GSON, cache, GSON.toJsonTree(mobXPBuilder.build()), xpOut);

        //Entity groups
        Path groupsOut = generator.getOutputFolder().resolve("data/" + modid + "/vault_configs/entity_groups/mob_groups.json");
        DataProvider.save(GSON, cache, GSON.toJsonTree(entityGroupsFile), groupsOut);

        generateBestiaryFile(cache, bestiaryEntries);

    }

    @Override
    public String getName() {
        return modid + " Vault Mobs Provider";
    }

    private static Map<String, VaultMob> mapToStringKeyedMap(Map<ResourceLocation, VaultMob> src) {
        Map<String, VaultMob> out = new LinkedHashMap<>();
        src.forEach((k, v) -> out.put(k.toString(), v));
        return out;
    }


    private void generateBestiaryFile(HashCache cache, List<BestiaryEntry> bestiaryEntries) throws IOException {
        JsonObject root = new JsonObject();
        root.add("groupDescriptions", new JsonObject());

        JsonArray entities = new JsonArray();

        for (BestiaryEntry entry : bestiaryEntries) {
            JsonObject entityJson = new JsonObject();
            entityJson.addProperty("entityId", entry.entityId);

            JsonObject descriptionData = new JsonObject();
            JsonArray descriptionArray = new JsonArray();
            entry.descriptions.forEach(descriptionArray::add);
            descriptionData.add("description", descriptionArray);
            entityJson.add("descriptionData", descriptionData);

            JsonArray themes = new JsonArray();
            for (String theme : entry.themes) {
                themes.add(theme);
            }
            entityJson.add("themes", themes);

            entityJson.addProperty("minLevel", entry.minLevel);

            entityJson.add("drops", new JsonArray());

            entities.add(entityJson);
        }

        root.add("entities", entities);
        root.add("hiddenGroups", new JsonArray());

        Path bestiaryOut = generator.getOutputFolder().resolve("data/" + modid + "/vault_configs/bestiary/mobs_bestiary.json");
        DataProvider.save(GSON, cache, root, bestiaryOut);
    }

    public record BestiaryEntry(String entityId, List<String> themes, int minLevel, List<JsonObject> descriptions) {
    }

    public record VaultMob(ResourceLocation entityId, List<AttrSpec> attributes, double xpReward,
                           List<ResourceLocation> entityGroups, BestiaryEntry entry) {
    }

    public static class VaultMobBuilder {
        private final ResourceLocation entityId;
        private final List<AttrSpec> entries = new ArrayList<>();
        public double xpValue = -1.0;
        public final List<ResourceLocation> entityGroups = new ArrayList<>();
        public BestiaryEntry entry;

        public VaultMobBuilder(ResourceLocation entityId) {
            this.entityId = entityId;
        }

        public VaultMobBuilder xpValue(double xpReward) {
            this.xpValue = xpReward;
            return this;
        }

        public VaultMobBuilder entityGroup(ResourceLocation groupId) {
            this.entityGroups.add(groupId);
            return this;
        }

        public VaultMobBuilder bestiaryEntry(Consumer<List<String>> themesConsumer, int minLevel, Consumer<List<JsonObject>> descriptionsConsumer) {
            List<String> themes = new ArrayList<>();
            List<JsonObject> descriptions = new ArrayList<>();
            themesConsumer.accept(themes);
            descriptionsConsumer.accept(descriptions);
            entry = new BestiaryEntry(entityId.toString(), themes, minLevel, descriptions);
            return this;
        }

        public VaultMobBuilder attributeWithLevels(String name, Consumer<LevelsBuilder> levelsConsumer) {
            LevelsBuilder lb = new LevelsBuilder(name);
            levelsConsumer.accept(lb);
            entries.add(lb.build());
            return this;
        }

        public VaultMobBuilder attributeSimple(String name, double min, double max, String operator,
                                               double rollChance, double scalePerLevel, int scaleMaxLevel) {
            AttrSpec spec = new AttrSpec();
            spec.NAME = name;
            spec.MIN = asNumber(min);
            spec.MAX = asNumber(max);
            spec.OPERATOR = operator;
            spec.ROLL_CHANCE = asNumber(rollChance);
            spec.SCALE_PER_LEVEL = asNumber(scalePerLevel).doubleValue();
            spec.SCALE_MAX_LEVEL = scaleMaxLevel;
            entries.add(spec);
            return this;
        }

        public VaultMob build() {
            return new VaultMob(entityId, List.copyOf(entries), xpValue, entityGroups, entry);
        }

        private static Number asNumber(double d) {
            if (Math.floor(d) == d) return (long)d;
            return d;
        }
    }

    public static class LevelsBuilder {
        private final String name;
        private final List<LevelSpec> levels = new ArrayList<>();

        public LevelsBuilder(String name) {
            this.name = name;
        }

        public LevelsBuilder addLevel(int minLevel, Number minValue, Number maxValue, String operator,
                                      double rollChance, double scalePerLevel, int scaleMaxLevel) {
            LevelSpec ls = new LevelSpec();
            ls.MIN_LEVEL = minLevel;
            ls.MIN = minValue;
            ls.MAX = maxValue;
            ls.OPERATOR = operator;
            ls.ROLL_CHANCE = rollChance;
            ls.SCALE_PER_LEVEL = scalePerLevel;
            ls.SCALE_MAX_LEVEL = scaleMaxLevel;
            levels.add(ls);
            return this;
        }

        public AttrSpec build() {
            AttrSpec s = new AttrSpec();
            s.NAME = name;
            s.LEVELS = List.copyOf(levels);
            return s;
        }
    }

    public static final class MobXPStatsFile {
        final double percentOfExperienceDealtAsDurabilityDamage = 0.0;
        final double freeExperienceNotDealtAsDurabilityDamage = 6000.0;
        final Map<String, Map<String, Double>> chests = new HashMap<>();
        final Map<String, Double> blocksMined = new HashMap<>();
        final double treasureRoomsOpened = 3400;
        final Map<String, Double> mobsKilled;
        final Map<String, Map<String, Double>> completion = new HashMap<>();

        public MobXPStatsFile(Map<String, Double> mobsKilled) {
            this.mobsKilled = mobsKilled;
        }
    }

    public static final class MobXPStatsFileBuilder {
        private Map<String, Double> mobsToXPMap = new HashMap<>();

        public MobXPStatsFileBuilder add(String entityId, Double xpAmount) {
            mobsToXPMap.put(entityId, xpAmount);
            return this;
        }

        public MobXPStatsFile build() {
            return new MobXPStatsFile(mobsToXPMap);
        }
    }

    public static final class AttributeOverridesFile {
        public final Map<String, List<AttrSpec>> ATTRIBUTE_OVERRIDES;
        public final List<String> LEVEL_OVERRIDES = new ArrayList<>();

        public AttributeOverridesFile(Map<String, VaultMob> map) {
            Map<String, List<AttrSpec>> mobAttributes = new HashMap<>();
            map.forEach(((s, vaultMob) -> {
                if(vaultMob.attributes != null) {
                    mobAttributes.put(s, vaultMob.attributes);
                }
            }));
            this.ATTRIBUTE_OVERRIDES = mobAttributes;
        }
    }

    public static final class AttrSpec {
        public String NAME;

        public Number MIN;
        public Number MAX;
        public String OPERATOR;
        public Number ROLL_CHANCE;
        public Double SCALE_PER_LEVEL;
        public Integer SCALE_MAX_LEVEL;

        public List<LevelSpec> LEVELS;
    }

    public static final class LevelSpec {
        public Integer MIN_LEVEL;
        public Number MIN;
        public Number MAX;
        public String OPERATOR;
        public Double ROLL_CHANCE;
        public Double SCALE_PER_LEVEL;
        public Integer SCALE_MAX_LEVEL;
    }
}
