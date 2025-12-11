package xyz.iwolfking.vhapi.api.datagen;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import iskallia.vault.VaultMod;
import iskallia.vault.block.PlaceholderBlock;
import iskallia.vault.config.Config;
import iskallia.vault.config.CustomEntitySpawnerConfig;
import iskallia.vault.config.adapter.WeightedListAdapter;
import iskallia.vault.core.data.adapter.Adapters;
import iskallia.vault.core.data.adapter.util.IdentifierAdapter;
import iskallia.vault.core.vault.RoomCache;
import iskallia.vault.core.vault.modifier.modifier.HunterModifier;
import iskallia.vault.core.vault.modifier.spi.EntityAttributeModifier;
import iskallia.vault.core.world.data.entity.EntityPredicate;
import iskallia.vault.core.world.data.entity.PartialCompoundNbt;
import iskallia.vault.core.world.data.entity.PartialEntityGroup;
import iskallia.vault.core.world.data.tile.PartialBlockState;
import iskallia.vault.core.world.data.tile.PartialTile;
import iskallia.vault.core.world.data.tile.TilePredicate;
import iskallia.vault.core.world.processor.tile.TileProcessor;
import iskallia.vault.init.ModBlocks;
import iskallia.vault.util.calc.PlayerStat;
import iskallia.vault.world.VaultDifficulty;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.datagen.lib.BasicListBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.WeightedListBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.modifiers.ModifierBuilder;
import xyz.iwolfking.vhapi.api.datagen.lib.modifiers.ModifierFile;
import xyz.iwolfking.woldsvaults.WoldsVaults;

import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;

public abstract class AbstractVaultModifierProvider implements DataProvider {


    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().registerTypeAdapter(ResourceLocation .class, new IdentifierAdapter(true)).registerTypeAdapterFactory(WeightedListAdapter.Factory.INSTANCE).registerTypeHierarchyAdapter(TilePredicate.class, Adapters.TILE_PREDICATE).registerTypeHierarchyAdapter(EntityPredicate.class, Adapters.ENTITY_PREDICATE).create();
    private final DataGenerator gen;
    private final String modid;


    public AbstractVaultModifierProvider(DataGenerator gen, String modid) {
        this.gen = gen;
        this.modid = modid;
    }

    @Override
    public void run(HashCache cache) throws IOException {
        Map<String, Consumer<ModifierBuilder>> map = new LinkedHashMap<>();
        addFiles(map);

        for (var entry : map.entrySet()) {
            String fileName = entry.getKey();

            ModifierBuilder builder = new ModifierBuilder();
            entry.getValue().accept(builder);
            ModifierFile file = builder.build();

            JsonElement json = GSON.toJsonTree(file);

            Path outPath = gen.getOutputFolder().resolve("data/" + modid + "/vault_configs/vault/modifiers/" + fileName + ".json");

            if (outPath.getParent() != null) {
                Files.createDirectories(outPath.getParent());
            }

            DataProvider.save(GSON, cache, json, outPath);
        }
    }

    public abstract void addFiles(Map<String, Consumer<ModifierBuilder>> map);

    @Override
    public String getName() {
        return "VHAPI Modifier Provider";
    }

    public static final PartialEntityGroup EMPTY_ENTITY_PREDICATE = PartialEntityGroup.of(null, PartialCompoundNbt.empty());

    public static PartialTile customSpawnerTile(String customEntitySpawnerId) {
        CompoundTag tag = new CompoundTag();
        tag.putString("spawnerGroupName", customEntitySpawnerId);
        return PartialTile.of(PartialBlockState.of(ModBlocks.CUSTOM_ENTITY_SPAWNER), PartialCompoundNbt.of(tag));
    }


    public static void difficultyLock(ModifierBuilder builder, ResourceLocation modifierId, VaultDifficulty difficulty, boolean shouldLockHigher, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/difficulty_lock").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                modifierEntryBuilder.property("difficulty", difficulty.name());
                modifierEntryBuilder.property("lockHigher", shouldLockHigher);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void chance(ModifierBuilder builder, ResourceLocation modifierTypeId, ResourceLocation modifierId, float chance, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(modifierTypeId.toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                modifierEntryBuilder.property("chance", chance);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void percentage(ModifierBuilder builder, ResourceLocation modifierTypeId, ResourceLocation modifierId, float percentage, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(modifierTypeId.toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                modifierEntryBuilder.property("percentage", percentage);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void decoratorAdd(ModifierBuilder builder, ResourceLocation modifierId, PartialTile tile, int attemptsPerChunk, boolean requiresConditions, Set<RoomCache.RoomType> roomTypes, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/decorator_add").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                modifierEntryBuilder.property("output", tile.toString());
                modifierEntryBuilder.property("attemptsPerChunk", attemptsPerChunk);
                modifierEntryBuilder.property("requiresConditions", requiresConditions);
                modifierEntryBuilder.property("roomTypeWhitelist", roomTypes);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void decoratorAdd(ModifierBuilder builder, ResourceLocation modifierId, PartialTile tile, int attemptsPerChunk, boolean requiresConditions, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
       decoratorAdd(builder, modifierId, tile, attemptsPerChunk, requiresConditions, Set.of(RoomCache.RoomType.ORE, RoomCache.RoomType.COMMON), name, color, description, formattedDescription, icon);
    }

    public static void castOnKill(ModifierBuilder builder, ResourceLocation modifierId, EntityPredicate filter, String abilityId, float probability, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/cast_on_kill").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                if(filter == EntityPredicate.TRUE) {
                    modifierEntryBuilder.property("filter", "");
                }
                else {
                    modifierEntryBuilder.property("filter", filter.toString());
                }
                modifierEntryBuilder.property("ability", abilityId);
                modifierEntryBuilder.property("probability", probability);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void dropOnKill(ModifierBuilder builder, ResourceLocation modifierId, EntityPredicate filter, ResourceLocation lootTable, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/drop_on_kill").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                modifierEntryBuilder.property("filter", filter.toString());
                modifierEntryBuilder.property("lootTable", lootTable.toString());
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void mobAttribute(ModifierBuilder builder, ResourceLocation modifierId, EntityAttributeModifier.ModifierType attribute, float amount, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        try {
            Field attributeField = EntityAttributeModifier.ModifierType.class.getDeclaredField(attribute.name());
            attributeField.setAccessible(true);
            SerializedName serializedName = attributeField.getAnnotation(SerializedName.class);
            String serializedNameValue = serializedName != null ? serializedName.value() : attribute.name();
            builder.type(VaultMod.id("modifier_type/mob_attribute").toString(), typeBuilder -> {
                typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                    modifierEntryBuilder.property("type", serializedNameValue);
                    modifierEntryBuilder.property("amount", amount);
                    modifierEntryBuilder.property("attributeStackingStrategy", "STACK");
                    createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
                });
            });
        }
        catch (NoSuchFieldException e) {
            WoldsVaults.LOGGER.error("Invalid EntityAttributeModifier.ModifierType passed in, skipping adding {} modifier.", modifierId);
        }
    }

    public static void mobCurseOnHit(ModifierBuilder builder, ResourceLocation modifierId, ResourceLocation effect, int amplifier, int durationTicks, float chance, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/mob_curse_on_hit").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                modifierEntryBuilder.property("effect", effect.toString());
                modifierEntryBuilder.property("effectAmplifier", amplifier);
                modifierEntryBuilder.property("effectDurationTicks", durationTicks);
                modifierEntryBuilder.property("onHitApplyChance", chance);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void mobIncrease(ModifierBuilder builder, ResourceLocation modifierId, float increase, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/spawner_mobs").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                modifierEntryBuilder.property("increase", increase);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void objectiveDifficulty(ModifierBuilder builder, ResourceLocation modifierId, float increase, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/objective_target").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                modifierEntryBuilder.property("increase", increase);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void hunter(ModifierBuilder builder, ResourceLocation modifierId, Consumer<HunterBuilder> hunterBuilderConsumer, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/hunter").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                HunterBuilder entriesBuilder = new HunterBuilder();
                hunterBuilderConsumer.accept(entriesBuilder);
                modifierEntryBuilder.property("entries", entriesBuilder.build());
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }


    public static void legacyMobIncrease(ModifierBuilder builder, ResourceLocation modifierId, int maxMobsAdded, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/mob_spawn_count").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                modifierEntryBuilder.property("maxMobsAdded", maxMobsAdded);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void playerAttribute(ModifierBuilder builder, ResourceLocation modifierId, EntityAttributeModifier.ModifierType attribute, float amount, String name, String color, String description, String formattedDescription, ResourceLocation icon)  {
        try {
            Field attributeField = EntityAttributeModifier.ModifierType.class.getDeclaredField(attribute.name());
            attributeField.setAccessible(true);
            SerializedName serializedName = attributeField.getAnnotation(SerializedName.class);
            String serializedNameValue = serializedName != null ? serializedName.value() : attribute.name();
            builder.type(VaultMod.id("modifier_type/player_attribute").toString(), typeBuilder -> {
                typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                    modifierEntryBuilder.property("type", serializedNameValue);
                    modifierEntryBuilder.property("amount", amount);
                    modifierEntryBuilder.property("attributeStackingStrategy", "STACK");
                    createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
                });
            });
        } catch (NoSuchFieldException ignored) {
            WoldsVaults.LOGGER.error("Invalid EntityAttributeModifier.ModifierType passed in, skipping adding {} modifier.", modifierId);
        }

    }

    public static void playerDurability(ModifierBuilder builder, ResourceLocation modifierId, float durabilityDamageTakenMultiplier, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/player_durability_damage").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                modifierEntryBuilder.property("durabilityDamageTakenMultiplier", durabilityDamageTakenMultiplier);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void playerEffect(ModifierBuilder builder, ResourceLocation modifierId, ResourceLocation effect, int amplifier, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/player_effect").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                modifierEntryBuilder.property("effect", effect.toString());
                modifierEntryBuilder.property("effectAmplifier", amplifier);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void entityEffect(ModifierBuilder builder, ResourceLocation modifierId, EntityPredicate filter, ResourceLocation effect, int amplifier, float chance, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/entity_effect").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                modifierEntryBuilder.property("filter", filter.toString());
                modifierEntryBuilder.property("effect", effect.toString());
                modifierEntryBuilder.property("effectAmplifier", amplifier);
                modifierEntryBuilder.property("chance", chance);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void noSoulShards(ModifierBuilder builder, ResourceLocation modifierId, EntityPredicate filter, float chance, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/no_soul_shards").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                modifierEntryBuilder.property("filter", filter.toString());
                modifierEntryBuilder.property("chance", chance);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void inventoryRestore(ModifierBuilder builder, ResourceLocation modifierId, boolean preventsArtifact, float experienceMultiDeath, float experienceMultiSuccess, boolean isInstantRevival, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/player_inventory_restore").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                modifierEntryBuilder.property("preventsArtifact", preventsArtifact);
                modifierEntryBuilder.property("experienceMultiplierOnDeath", experienceMultiDeath);
                modifierEntryBuilder.property("experienceMultiplierOnSuccess", experienceMultiSuccess);
                modifierEntryBuilder.property("isInstantRevival", isInstantRevival);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void locked(ModifierBuilder builder, ResourceLocation modifierId, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/player_no_exit").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void noFruit(ModifierBuilder builder, ResourceLocation modifierId, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/player_no_vault_fruit").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void noCrate(ModifierBuilder builder, ResourceLocation modifierId, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/no_crate").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void noLoot(ModifierBuilder builder, ResourceLocation modifierId, boolean destroyChests, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/no_loot").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                modifierEntryBuilder.property("destroyChests", destroyChests);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void healCut(ModifierBuilder builder, ResourceLocation modifierId, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/player_heal_cut").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }


    public static void noTemporal(ModifierBuilder builder, ResourceLocation modifierId, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/player_no_temporal_shard").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void playerStat(ModifierBuilder builder, ResourceLocation modifierId, PlayerStat stat, float addend, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        try {
            Field attributeField = PlayerStat.class.getDeclaredField(stat.name());
            attributeField.setAccessible(true);
            SerializedName serializedName = attributeField.getAnnotation(SerializedName.class);
            String serializedNameValue = serializedName != null ? serializedName.value() : stat.name();
            builder.type(VaultMod.id("modifier_type/player_stat").toString(), typeBuilder -> {
                typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                    modifierEntryBuilder.property("stat", serializedNameValue);
                    modifierEntryBuilder.property("addend", addend);
                    createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
                });
            });
        } catch (NoSuchFieldException ignored) {
            WoldsVaults.LOGGER.error("Invalid PlayerStat passed in, skipping adding {} modifier.", modifierId);
        }

    }

    public static void vaultLevel(ModifierBuilder builder, ResourceLocation modifierId, int levelAdded, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/vault_level").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                modifierEntryBuilder.property("levelAdded", levelAdded);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void lootWeight(ModifierBuilder builder, ResourceLocation modifierId, PlaceholderBlock.Type type, float increase, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/vault_lootable_weight").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                modifierEntryBuilder.property("type", type.name());
                modifierEntryBuilder.property("chance", increase);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void vaultTime(ModifierBuilder builder, ResourceLocation modifierId, int timeAddedTicks, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/vault_time").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                modifierEntryBuilder.property("timeAddedTicks", timeAddedTicks);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void champLoot(ModifierBuilder builder, ResourceLocation modifierId, float increase, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/champion_loot_chance").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                modifierEntryBuilder.property("addend", increase);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void experience(ModifierBuilder builder, ResourceLocation modifierId, float increase, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/experience").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                modifierEntryBuilder.property("addend", increase);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void experienceMultiplier(ModifierBuilder builder, ResourceLocation modifierId, float increase, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/experience_multiplier").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                modifierEntryBuilder.property("addend", increase);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void inline(ModifierBuilder builder, ResourceLocation modifierId, ResourceLocation pool, int level, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/inline_pool").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                modifierEntryBuilder.property("pool", pool.toString());
                modifierEntryBuilder.property("level", level);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void decoratorCascade(ModifierBuilder builder, ResourceLocation modifierId, TilePredicate tile, float increase, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/decorator_cascade").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                modifierEntryBuilder.property("filter", tile.toString());
                modifierEntryBuilder.property("chance", increase);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void grouped(ModifierBuilder builder, ResourceLocation modifierId, Consumer<Map<ResourceLocation, Integer>> modifiersConsumer, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/grouped").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                Map<ResourceLocation, Integer> modifiers = new HashMap<>();
                modifiersConsumer.accept(modifiers);
                modifierEntryBuilder.property("children", modifiers);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void enchantedEventChance(ModifierBuilder builder, ResourceLocation modifierId, float chance, int ticksPerCheck, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/enchanted_event_chance").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                modifierEntryBuilder.property("chance", chance);
                modifierEntryBuilder.property("ticksPerCheck", ticksPerCheck);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void chestBreakBomb(ModifierBuilder builder, ResourceLocation modifierId, float chance, Consumer<WeightedListBuilder<Integer>> weightedListConsumer, Consumer<WeightedListBuilder<CustomEntitySpawnerConfig.SpawnerEntity>> spawnerEntitiesConsumer, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/chest_break_bomb").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                WeightedListBuilder<Integer> amountsBuilder = new WeightedListBuilder<>();
                WeightedListBuilder<CustomEntitySpawnerConfig.SpawnerEntity> entitiesBuilder = new WeightedListBuilder<>();
                weightedListConsumer.accept(amountsBuilder);
                spawnerEntitiesConsumer.accept(entitiesBuilder);
                modifierEntryBuilder.property("chance", chance);
                modifierEntryBuilder.property("amounts", amountsBuilder.build());
                modifierEntryBuilder.property("entities", entitiesBuilder.build());
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void mobDeathBomb(ModifierBuilder builder, ResourceLocation modifierId, EntityPredicate filter, boolean filterShouldExclude, float chance, Consumer<WeightedListBuilder<Integer>> weightedListConsumer, Consumer<WeightedListBuilder<CustomEntitySpawnerConfig.SpawnerEntity>> spawnerEntitiesConsumer, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/mob_death_bomb").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                WeightedListBuilder<Integer> amountsBuilder = new WeightedListBuilder<>();
                WeightedListBuilder<CustomEntitySpawnerConfig.SpawnerEntity> entitiesBuilder = new WeightedListBuilder<>();
                weightedListConsumer.accept(amountsBuilder);
                spawnerEntitiesConsumer.accept(entitiesBuilder);
                modifierEntryBuilder.property("filter", filter.toString());
                modifierEntryBuilder.property("filterShouldExclude", filterShouldExclude);
                modifierEntryBuilder.property("chance", chance);
                modifierEntryBuilder.property("amounts", amountsBuilder.build());
                modifierEntryBuilder.property("entities", entitiesBuilder.build());
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void infernalChance(ModifierBuilder builder, ResourceLocation modifierId, EntityPredicate filter, float chance, int numberOfAffixes, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/infernal_mobs").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                modifierEntryBuilder.property("filter", filter.toString());
                modifierEntryBuilder.property("chance", chance);
                modifierEntryBuilder.property("amount", numberOfAffixes);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void retroSpawn(ModifierBuilder builder, ResourceLocation modifierId, float chance, int ticksPerCheck, int entityCap, Consumer<WeightedListBuilder<Integer>> weightedListConsumer, Consumer<WeightedListBuilder<CustomEntitySpawnerConfig.SpawnerEntity>> spawnerEntitiesConsumer, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/retro_spawn_modifier").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                WeightedListBuilder<Integer> amountsBuilder = new WeightedListBuilder<>();
                WeightedListBuilder<CustomEntitySpawnerConfig.SpawnerEntity> entitiesBuilder = new WeightedListBuilder<>();
                weightedListConsumer.accept(amountsBuilder);
                spawnerEntitiesConsumer.accept(entitiesBuilder);
                modifierEntryBuilder.property("chance", chance);
                modifierEntryBuilder.property("ticksPerCheck", ticksPerCheck);
                modifierEntryBuilder.property("amounts", amountsBuilder.build());
                modifierEntryBuilder.property("entities", entitiesBuilder.build());
                modifierEntryBuilder.property("cap", entityCap);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void frenzy(ModifierBuilder builder, ResourceLocation modifierId, float damage, float movementSpeed, float maxHealth, boolean doHealthReduction, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/mob_frenzy").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                modifierEntryBuilder.property("damage", damage);
                modifierEntryBuilder.property("movementSpeed", movementSpeed);
                modifierEntryBuilder.property("maxHealth", maxHealth);
                modifierEntryBuilder.property("doHealthReduction", doHealthReduction);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void templateProcessor(ModifierBuilder builder, ResourceLocation modifierId, float probability, Consumer<List<TilePredicate>> blacklistConsumer, Consumer<List<TilePredicate>> whitelistConsumer, Consumer<List<TileProcessor>> fullBlockConsumer, Consumer<List<TileProcessor>> partialBlockConsumer, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        builder.type(VaultMod.id("modifier_type/template_processor").toString(), typeBuilder -> {
            typeBuilder.modifier(modifierId.toString(), modifierEntryBuilder -> {
                List<TilePredicate> blacklist = new ArrayList<>();
                List<TilePredicate> whitelist = new ArrayList<>();
                List<TileProcessor> fullBlock = new ArrayList<>();
                List<TileProcessor> partialBlock = new ArrayList<>();
                blacklistConsumer.accept(blacklist);
                whitelistConsumer.accept(whitelist);
                fullBlockConsumer.accept(fullBlock);
                partialBlockConsumer.accept(partialBlock);
                modifierEntryBuilder.property("probability", probability);
                modifierEntryBuilder.property("blacklist", blacklist);
                modifierEntryBuilder.property("whitelist", whitelist);
                modifierEntryBuilder.property("fullBlock", fullBlock);
                modifierEntryBuilder.property("partialBlock", partialBlock);
                createModifierDisplay(modifierEntryBuilder, name, color, description, formattedDescription, icon);
            });
        });
    }

    public static void artifactChance(ModifierBuilder builder, ResourceLocation modifierId, float chance, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        chance(builder, VaultMod.id("modifier_type/chance_artifact"), modifierId, chance, name, color, description, formattedDescription, icon);
    }

    public static void catalystChacne(ModifierBuilder builder, ResourceLocation modifierId, float chance, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        chance(builder, VaultMod.id("modifier_type/chance_catalyst"), modifierId, chance, name, color, description, formattedDescription, icon);
    }

    public static void trapChestChance(ModifierBuilder builder, ResourceLocation modifierId, float chance, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        chance(builder, VaultMod.id("modifier_type/chance_chest_trap"), modifierId, chance, name, color, description, formattedDescription, icon);
    }

    public static void soulShardChance(ModifierBuilder builder, ResourceLocation modifierId, float chance, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        chance(builder, VaultMod.id("modifier_type/chance_soul_shard"), modifierId, chance, name, color, description, formattedDescription, icon);
    }

    public static void championChance(ModifierBuilder builder, ResourceLocation modifierId, float chance, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        chance(builder, VaultMod.id("modifier_type/chance_champion"), modifierId, chance, name, color, description, formattedDescription, icon);
    }

    public static void itemQuantity(ModifierBuilder builder, ResourceLocation modifierId, float chance, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        percentage(builder, VaultMod.id("modifier_type/loot_item_quantity"), modifierId, chance, name, color, description, formattedDescription, icon);
    }

    public static void itemRarity(ModifierBuilder builder, ResourceLocation modifierId, float chance, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        percentage(builder, VaultMod.id("modifier_type/loot_item_rarity"), modifierId, chance, name, color, description, formattedDescription, icon);
    }

    public static void crateQuantity(ModifierBuilder builder, ResourceLocation modifierId, float chance, String name, String color, String description, String formattedDescription, ResourceLocation icon) {
        percentage(builder, VaultMod.id("modifier_type/crate_item_quantity"), modifierId, chance, name, color, description, formattedDescription, icon);
    }


    public static void createModifierDisplay(ModifierBuilder.ModifierEntryBuilder builder, String name, @Nullable String color, String description, @Nullable String formattedDescription, @Nullable ResourceLocation icon) {
        builder.display(name, color == null ? "#ffffff" : color, description, formattedDescription == null ? description : formattedDescription, icon == null ? VaultMod.id("impossible").toString() : icon.toString());
    }

    public static class HunterBuilder extends BasicListBuilder<HunterModifier.Properties.Entry> {
        public HunterBuilder entry(TilePredicate filter, int radius, String target, int color) {
            HunterModifier.Properties.Entry entry = new HunterModifier.Properties.Entry();
            entry.color = color;
            entry.filter = filter;
            entry.radius = radius;
            entry.target = target;
            add(entry);
            return this;
        }
    }
}

