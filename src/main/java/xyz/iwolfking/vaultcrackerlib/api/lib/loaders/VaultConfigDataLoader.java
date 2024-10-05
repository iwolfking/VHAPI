package xyz.iwolfking.vaultcrackerlib.api.lib.loaders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
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
import iskallia.vault.core.world.processor.Processor;
import iskallia.vault.core.world.roll.FloatRoll;
import iskallia.vault.core.world.roll.IntRoll;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.CustomVaultConfigReader;
import xyz.iwolfking.vaultcrackerlib.api.LoaderRegistry;

import java.util.Map;
@Mod.EventBusSubscriber(modid = "vaultcrackerlib", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VaultConfigDataLoader<T extends Config> extends SimpleJsonResourceReloadListener {
    public static final Gson GSON = (new GsonBuilder()).registerTypeHierarchyAdapter(MobEffect.class, RegistryCodecAdapter.of(ForgeRegistries.MOB_EFFECTS)).registerTypeHierarchyAdapter(Item.class, RegistryCodecAdapter.of(ForgeRegistries.ITEMS)).registerTypeHierarchyAdapter(Block.class, RegistryCodecAdapter.of(ForgeRegistries.BLOCKS)).registerTypeHierarchyAdapter(Enchantment.class, Adapters.ENCHANTMENT).registerTypeAdapterFactory(IdentifierAdapter.FACTORY).registerTypeAdapterFactory(TextColorAdapter.FACTORY).registerTypeAdapterFactory(ItemStackAdapter.FACTORY).registerTypeAdapterFactory(PartialTileAdapter.FACTORY).registerTypeAdapterFactory(WeightedListAdapter.Factory.INSTANCE).registerTypeHierarchyAdapter(VaultGearTierConfig.AttributeGroup.class, new VaultGearTierConfig.AttributeGroup.Serializer()).registerTypeHierarchyAdapter(EtchingConfig.EtchingMap.class, new EtchingConfig.EtchingMap.Serializer()).registerTypeHierarchyAdapter(TrinketConfig.TrinketMap.class, new TrinketConfig.TrinketMap.Serializer()).registerTypeHierarchyAdapter(CharmConfig.CharmMap.class, new CharmConfig.CharmMap.Serializer()).registerTypeAdapter(VaultModifiersConfig.ModifierTypeGroups.class, new VaultModifiersConfig.ModifierTypeGroups.Serializer()).registerTypeAdapter(CompoundTag.class, Adapters.COMPOUND_NBT).registerTypeAdapter(EnchantmentCost.class, EnchantmentCost.ADAPTER).registerTypeHierarchyAdapter(VersionedKey.class, VersionedKeyAdapter.INSTANCE).registerTypeHierarchyAdapter(CrystalTheme.class, CrystalData.THEME).registerTypeHierarchyAdapter(CrystalLayout.class, CrystalData.LAYOUT).registerTypeHierarchyAdapter(CrystalObjective.class, CrystalData.OBJECTIVE).registerTypeHierarchyAdapter(CrystalTime.class, CrystalData.TIME).registerTypeHierarchyAdapter(CrystalModifiers.class, CrystalData.MODIFIERS).registerTypeHierarchyAdapter(CrystalProperties.class, CrystalData.PROPERTIES).registerTypeHierarchyAdapter(ScavengeTask.class, ScavengeTask.Adapter.INSTANCE).registerTypeHierarchyAdapter(LootPool.class, Adapters.LOOT_POOL).registerTypeHierarchyAdapter(LootTable.Entry.class, Adapters.LOOT_TABLE_ENTRY).registerTypeHierarchyAdapter(IntRoll.class, Adapters.INT_ROLL).registerTypeHierarchyAdapter(FloatRoll.class, iskallia.vault.core.world.roll.FloatRoll.Adapter.INSTANCE).registerTypeHierarchyAdapter(Skill.class, Adapters.SKILL).registerTypeHierarchyAdapter(Task.class, Adapters.TASK).registerTypeAdapter(ElixirTask.Config.class, ElixirTask.Config.Serializer.INSTANCE).registerTypeHierarchyAdapter(Quest.class, iskallia.vault.quest.base.Quest.Adapter.INSTANCE).registerTypeAdapter(TilePredicate.class, Adapters.TILE_PREDICATE).registerTypeAdapter(EntityPredicate.class, Adapters.ENTITY_PREDICATE).registerTypeAdapter(ItemPredicate.class, Adapters.ITEM_PREDICATE).registerTypeAdapter(SkillGateType.class, SkillGates.GATE_TYPE).registerTypeAdapter(VaultAltarConfig.Interface.class, Adapters.ALTAR_INTERFACE).registerTypeAdapter(BingoItem.class, BingoItemAdapter.INSTANCE).registerTypeAdapter(Card.Config.class, iskallia.vault.core.card.Card.Config.ADAPTER).registerTypeHierarchyAdapter(Processor.class, ProcessorAdapter.INSTANCE).registerTypeHierarchyAdapter(AntiqueCondition.class, iskallia.vault.antique.condition.AntiqueCondition.Serializer.INSTANCE).registerTypeHierarchyAdapter(AntiqueReward.class, iskallia.vault.antique.reward.AntiqueReward.Serializer.INSTANCE).registerTypeAdapter(CardEntry.Config.class, iskallia.vault.core.card.CardEntry.Config.ADAPTER).registerTypeAdapter(CardScaler.class, CardScaler.ADAPTER).registerTypeAdapter(CardCondition.class, CardCondition.ADAPTER).registerTypeHierarchyAdapter(Component.class, Adapters.COMPONENT).excludeFieldsWithoutExposeAnnotation().enableComplexMapKeySerialization().setPrettyPrinting().create();
    private final T instance;
    private final String namespace;
    private final String directory;
    public Map<ResourceLocation, T> CUSTOM_CONFIGS;

    public VaultConfigDataLoader(T instance, String directory, Map<ResourceLocation, T> configMap, String namespace) {
        super(GSON, directory);
        this.directory = directory;
        this.instance = instance;
        this.CUSTOM_CONFIGS = configMap;
        this.namespace = namespace;
        LoaderRegistry.LOADERS.put(new ResourceLocation(this.namespace, this.getName()), this);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> dataMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {

        dataMap.forEach((resourceLocation, jsonElement) -> {
            CustomVaultConfigReader<T> configReader = new CustomVaultConfigReader<>();
            T config = configReader.readCustomConfig(resourceLocation.getPath(), jsonElement, instance.getClass());
            CUSTOM_CONFIGS.put(new ResourceLocation(namespace, resourceLocation.getPath()), config);
        });

        performFinalActions();
    }

    @SubscribeEvent
    public void onAddListeners(AddReloadListenerEvent event) {
        event.addListener(this);
        MinecraftForge.EVENT_BUS.addListener(this::afterConfigsLoad);
    }

    @SubscribeEvent
    public void afterConfigsLoad(VaultConfigEvent.End event) {
    }

    protected void performFinalActions() {

    }

    public String getNamespace() {
        return namespace;
    }


    @Override
    public @NotNull String getName() {
        return this.directory;
    }
}
