package xyz.iwolfking.vhapi.api.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import iskallia.vault.VaultMod;
import iskallia.vault.gear.VaultGearRarity;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import xyz.iwolfking.vhapi.api.datagen.lib.gear.UniqueGearEntry;
import xyz.iwolfking.vhapi.api.loaders.gear.transmog.lib.GsonHandheldModel;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractUniqueGearProvider implements DataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    protected final DataGenerator generator;
    protected final String modid;

    public AbstractUniqueGearProvider(DataGenerator generator, String modid) {
        this.generator = generator;
        this.modid = modid;
    }

    protected abstract void addGear(Consumer<GeneratedEntry> consumer);

    public record GeneratedEntry(ResourceLocation id, UniqueGearEntry entry) {}

    @Override
    public void run(HashCache cache) throws IOException {
        JsonObject root = new JsonObject();
        JsonObject registry = new JsonObject();
        JsonObject pools = new JsonObject();
        root.add("registry", registry);
        root.add("pools", pools);

        JsonObject discoveryRoot = new JsonObject();
        JsonObject discoveryHints = new JsonObject();
        discoveryRoot.add("discoveryHints", discoveryHints);


        JsonObject codexRoot = new JsonObject();
        JsonObject introduction = new JsonObject();
        JsonObject index = new JsonObject();
        JsonArray pages = new JsonArray();
        introduction.add("index", index);
        codexRoot.add("introduction", introduction);
        codexRoot.add("pages", pages);

        Map<String, JsonArray> modelsMap = new HashMap<>();

        JsonObject modelRolls = new JsonObject();
        String[] itemTypes = {
                "AXE","WAND","SWORD","ARMOR","SHIELD","FOCUS","MAGNETS",
                "TRIDENT","BATTLESTAFF","PLUSHIE","LOOT_SACK", "JEWEL", "RANG"
        };

        for (String type : itemTypes) {
            JsonObject rolls = new JsonObject();
            for (VaultGearRarity rarity : VaultGearRarity.values()) {
                rolls.add(rarity.name(), new JsonArray());
            }
            modelRolls.add(type + "_MODEL_ROLLS", rolls);
        }

        List<String> craftedPool = new ArrayList<>();
        List<String> collectionPool = new ArrayList<>();

        addGear(result -> {
            String key = result.id().toString();
            UniqueGearEntry entry = result.entry();

            // Registry entry
            registry.add(key, toJson(entry));

            // Pools
            JsonObject poolEntry = new JsonObject();
            poolEntry.addProperty(key, entry.weight());
            pools.add(key, poolEntry);
            if(!result.entry.uncraftable()) {
                craftedPool.add(result.id.toString());
            }

            if(!result.entry.uncollectable()) {
                collectionPool.add(result.id.toString());
            }

            // Codex index
            String type = entry.codexSlotType().toString();
            JsonArray typeArray = index.has(type) ? index.getAsJsonArray(type) : new JsonArray();
            typeArray.add(key);
            index.add(type, typeArray);

            // Codex page
            JsonObject page = new JsonObject();

            JsonObject title = new JsonObject();
            JsonObject titleDesc = new JsonObject();
            titleDesc.addProperty("text", entry.name());
            titleDesc.addProperty("color", "#7D520E");
            title.add("description", titleDesc);
            page.add("title", title);

            JsonObject description = new JsonObject();
            JsonArray descArray = new JsonArray();
            entry.codexDescription().forEach(descArray::add);

            JsonObject d2 = new JsonObject();
            d2.addProperty("text", "\n\nDrops in: " + entry.codexDropLocation());
            d2.addProperty("color", "#7D520E");
            descArray.add(d2);

            description.add("description", descArray);
            page.add("description", description);

            page.addProperty("uniqueId", key);
            pages.add(page);

            // Handheld model JSON generation
            for (UniqueGearEntry.WeightedModel wm : entry.models()) {
                GsonHandheldModel handheld = new GsonHandheldModel(
                        wm.value(),
                        entry.name(),
                        true,
                        true
                );

                modelsMap.computeIfAbsent(entry.modelType().toLowerCase(), k -> new JsonArray())
                        .add(handheld.toJson());
            }

            // Model Roll entries
            String rollKey = entry.modelType().toUpperCase() + "_MODEL_ROLLS";
            JsonObject rolls = modelRolls.getAsJsonObject(rollKey);
            JsonArray uniqueArray = rolls.getAsJsonArray("UNIQUE");
            entry.models().forEach(m -> uniqueArray.add(m.value().toString()));

            //Discovery
            discoveryHints.addProperty(result.id.toString(), entry.codexDropLocation());
        });

        JsonObject craftedPoolObj = new JsonObject();
        JsonObject collectionPoolObj = new JsonObject();

        craftedPool.forEach(string -> {
            craftedPoolObj.addProperty(string, 1);
        });

        collectionPool.forEach(string -> {
            collectionPoolObj.addProperty(string, 1);
        });




        pools.add(VaultMod.id("crafted").toString(), craftedPoolObj);
        pools.add(VaultMod.id("collection").toString(), collectionPoolObj);

        // Save unique_gear.json
        Path gearTarget = generator.getOutputFolder()
                .resolve("data/" + modid + "/vault_configs/gear/unique_gear/unique_gear.json");
        DataProvider.save(GSON, cache, root, gearTarget);

        // Save codex
        Path codexTarget = generator.getOutputFolder()
                .resolve("data/" + modid + "/vault_configs/unique_codex/unique_codex.json");
        DataProvider.save(GSON, cache, codexRoot, codexTarget);

        // Save discovery
        Path discoveryTarget = generator.getOutputFolder()
                .resolve("data/" + modid + "/vault_configs/unique_discovery/unique_discovery.json");
        DataProvider.save(GSON, cache, discoveryRoot, discoveryTarget);

        // Save handheld model definitions
        for (String modelType : modelsMap.keySet()) {
            JsonObject modelsRoot = new JsonObject();
            modelsRoot.add("MODELS", modelsMap.get(modelType));
            Path modelsTarget = getPath(modelType);

            DataProvider.save(GSON, cache, modelsRoot, modelsTarget);
        }

        // Save rolls
        Path rollsTarget = generator.getOutputFolder()
                .resolve("data/" + modid + "/vault_configs/gear/model_rolls/unique_model_rolls.json");
        DataProvider.save(GSON, cache, modelRolls, rollsTarget);
    }

    private @NotNull Path getPath(String modelType) {
        Path modelsTarget;
        if(modelType.equals("focus") || modelType.equals("wand") || modelType.equals("magnets")) {
            modelsTarget = generator.getOutputFolder()
                    .resolve("data/" + modid + "/vault_configs/gear/plain_models/" + modelType + "/unique_models.json");
        }
        else if(modelType.equals("shield")) {
            modelsTarget = generator.getOutputFolder()
                    .resolve("data/" + modid + "/vault_configs/gear/shield_models/" + modelType + "/unique_models.json");
        }
        else {
            modelsTarget = generator.getOutputFolder()
                    .resolve("data/" + modid + "/vault_configs/gear/handheld_models/" + modelType + "/unique_models.json");
        }
        return modelsTarget;
    }

    private JsonObject toJson(UniqueGearEntry entry) {
        JsonObject obj = new JsonObject();

        obj.addProperty("name", entry.name());
        obj.addProperty("item", entry.item().getRegistryName().toString());

        // NEW: models array
        JsonArray models = new JsonArray();
        entry.models().forEach(m -> {
            JsonObject mObj = new JsonObject();
            mObj.addProperty("value", m.value().toString());
            mObj.addProperty("weight", m.weight());
            models.add(mObj);
        });
        obj.add("models", models);

        JsonObject ids = new JsonObject();
        entry.modifierIdentifiers().forEach((type, list) -> {
            JsonArray arr = new JsonArray();
            list.forEach(arr::add);
            ids.add(type, arr);
        });
        obj.add("modifierIdentifiers", ids);

        JsonArray tagArr = new JsonArray();
        entry.modifierTags().forEach(tagArr::add);
        obj.add("modifierTags", tagArr);

        obj.addProperty("weight", entry.weight());

        return obj;
    }

    @Override
    public String getName() {
        return modid + " Unique Gear Provider";
    }
}
