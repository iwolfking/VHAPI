package xyz.iwolfking.vhapi.api.lib.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import iskallia.vault.antique.condition.AntiqueCondition;
import iskallia.vault.antique.reward.AntiqueReward;
import iskallia.vault.config.*;
import iskallia.vault.config.adapter.*;
import iskallia.vault.config.gear.VaultGearTierConfig;
import iskallia.vault.config.skillgate.SkillGateType;
import iskallia.vault.core.card.Card;
import iskallia.vault.core.card.CardCondition;
import iskallia.vault.core.card.CardEntry;
import iskallia.vault.core.card.CardScaler;
import iskallia.vault.core.data.adapter.Adapters;
import iskallia.vault.core.data.key.VersionedKey;
import iskallia.vault.core.vault.objective.bingo.BingoItem;
import iskallia.vault.core.vault.objective.elixir.ElixirTask;
import iskallia.vault.core.vault.objective.scavenger.ScavengeTask;
import iskallia.vault.core.world.data.entity.EntityPredicate;
import iskallia.vault.core.world.data.item.ItemPredicate;
import iskallia.vault.core.world.data.tile.TilePredicate;
import iskallia.vault.core.world.loot.LootPool;
import iskallia.vault.core.world.loot.LootTable;
import iskallia.vault.core.world.processor.Palette;
import iskallia.vault.core.world.processor.Processor;
import iskallia.vault.core.world.roll.FloatRoll;
import iskallia.vault.core.world.roll.IntRoll;
import iskallia.vault.core.world.template.data.TemplatePool;
import iskallia.vault.item.crystal.CrystalData;
import iskallia.vault.item.crystal.layout.CrystalLayout;
import iskallia.vault.item.crystal.modifiers.CrystalModifiers;
import iskallia.vault.item.crystal.objective.CrystalObjective;
import iskallia.vault.item.crystal.properties.CrystalProperties;
import iskallia.vault.item.crystal.theme.CrystalTheme;
import iskallia.vault.item.crystal.time.CrystalTime;
import iskallia.vault.quest.base.Quest;
import iskallia.vault.skill.SkillGates;
import iskallia.vault.skill.base.Skill;
import iskallia.vault.task.Task;
import iskallia.vault.util.EnchantmentCost;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.iwolfking.vhapi.VHAPI;

import java.lang.reflect.Type;

public class GenFileReader<T> {

    public static final Gson GSON = (new GsonBuilder()).registerTypeHierarchyAdapter(MobEffect .class, RegistryCodecAdapter.of(ForgeRegistries.MOB_EFFECTS)).registerTypeHierarchyAdapter(Item .class, RegistryCodecAdapter.of(ForgeRegistries.ITEMS)).registerTypeHierarchyAdapter(Block .class, RegistryCodecAdapter.of(ForgeRegistries.BLOCKS)).registerTypeHierarchyAdapter(Enchantment .class, Adapters.ENCHANTMENT).registerTypeAdapterFactory(IdentifierAdapter.FACTORY).registerTypeAdapterFactory(TextColorAdapter.FACTORY).registerTypeAdapterFactory(ItemStackAdapter.FACTORY).registerTypeAdapterFactory(PartialTileAdapter.FACTORY).registerTypeAdapterFactory(WeightedListAdapter.Factory.INSTANCE).registerTypeHierarchyAdapter(VaultGearTierConfig.AttributeGroup.class, new VaultGearTierConfig.AttributeGroup.Serializer()).registerTypeHierarchyAdapter(EtchingConfig.EtchingMap.class, new EtchingConfig.EtchingMap.Serializer()).registerTypeHierarchyAdapter(TrinketConfig.TrinketMap.class, new TrinketConfig.TrinketMap.Serializer()).registerTypeHierarchyAdapter(CharmConfig.CharmMap.class, new CharmConfig.CharmMap.Serializer()).registerTypeAdapter(VaultModifiersConfig.ModifierTypeGroups.class, new VaultModifiersConfig.ModifierTypeGroups.Serializer()).registerTypeAdapter(CompoundTag .class, Adapters.COMPOUND_NBT).registerTypeAdapter(EnchantmentCost .class, EnchantmentCost.ADAPTER).registerTypeHierarchyAdapter(VersionedKey .class, VersionedKeyAdapter.INSTANCE).registerTypeHierarchyAdapter(CrystalTheme .class, CrystalData.THEME).registerTypeHierarchyAdapter(CrystalLayout .class, CrystalData.LAYOUT).registerTypeHierarchyAdapter(CrystalObjective .class, CrystalData.OBJECTIVE).registerTypeHierarchyAdapter(CrystalTime .class, CrystalData.TIME).registerTypeHierarchyAdapter(CrystalModifiers .class, CrystalData.MODIFIERS).registerTypeHierarchyAdapter(CrystalProperties .class, CrystalData.PROPERTIES).registerTypeHierarchyAdapter(ScavengeTask .class, ScavengeTask.Adapter.INSTANCE).registerTypeHierarchyAdapter(LootPool .class, Adapters.LOOT_POOL).registerTypeHierarchyAdapter(LootTable.Entry.class, Adapters.LOOT_TABLE_ENTRY).registerTypeHierarchyAdapter(IntRoll .class, Adapters.INT_ROLL).registerTypeHierarchyAdapter(FloatRoll .class, FloatRoll.Adapter.INSTANCE).registerTypeHierarchyAdapter(Skill .class, Adapters.SKILL).registerTypeHierarchyAdapter(Task .class, Adapters.TASK).registerTypeAdapter(ElixirTask.Config.class, ElixirTask.Config.Serializer.INSTANCE).registerTypeHierarchyAdapter(Quest .class, Quest.Adapter.INSTANCE).registerTypeAdapter(TilePredicate .class, Adapters.TILE_PREDICATE).registerTypeAdapter(EntityPredicate .class, Adapters.ENTITY_PREDICATE).registerTypeAdapter(ItemPredicate .class, Adapters.ITEM_PREDICATE).registerTypeAdapter(SkillGateType .class, SkillGates.GATE_TYPE).registerTypeAdapter(VaultAltarConfig.Interface.class, Adapters.ALTAR_INTERFACE).registerTypeAdapter(BingoItem .class, BingoItemAdapter.INSTANCE).registerTypeAdapter(Card.Config.class, Card.Config.ADAPTER).registerTypeHierarchyAdapter(Processor .class, ProcessorAdapter.INSTANCE).registerTypeHierarchyAdapter(AntiqueCondition .class, AntiqueCondition.Serializer.INSTANCE).registerTypeHierarchyAdapter(AntiqueReward .class, AntiqueReward.Serializer.INSTANCE).registerTypeAdapter(CardEntry.Config.class, CardEntry.Config.ADAPTER).registerTypeAdapter(CardScaler .class, CardScaler.ADAPTER).registerTypeAdapter(CardCondition .class, CardCondition.ADAPTER).registerTypeHierarchyAdapter(Component .class, Adapters.COMPONENT).registerTypeAdapterFactory(ThemeAdapter.FACTORY).registerTypeAdapter(TemplatePool.class, Adapters.TEMPLATE_POOL).registerTypeAdapter(Palette.class, PaletteAdapter.INSTANCE).enableComplexMapKeySerialization().setPrettyPrinting().create();
    /**
     *
     * @param name The file name of the datapack file.
     * @param json The JSON data collected from the datapack loader.
     * @param instance The type of class for GSON to use as a reference.
     * @return An instance of the instance class with the data from json.
     */
    public T readCustomConfig(String name, JsonElement json, Type instance) {
        VHAPI.LOGGER.debug("Reading custom " + instance.getTypeName().toLowerCase() + " gen file: "  + name);
        T readConfig;
        try {
            readConfig = (T)GSON.fromJson(json, instance);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        }
        return readConfig;
    }

}
