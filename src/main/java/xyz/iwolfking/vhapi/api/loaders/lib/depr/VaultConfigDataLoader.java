package xyz.iwolfking.vhapi.api.loaders.lib.depr;

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
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.core.readers.CustomVaultConfigReader;
import xyz.iwolfking.vhapi.api.LoaderRegistry;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
@Deprecated
@Mod.EventBusSubscriber(modid = "vhapi", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VaultConfigDataLoader<T extends Config> extends SimpleJsonResourceReloadListener {
    public static final Gson GSON = Config.GSON;
    private final T instance;
    private final String namespace;
    private final String directory;
    public Map<ResourceLocation, T> CUSTOM_CONFIGS;
    private Set<ResourceLocation> CONFIGS_TO_IGNORE = new HashSet<>();

    public VaultConfigDataLoader(T instance, String directory, Map<ResourceLocation, T> configMap, String namespace) {
        super(GSON, directory);
        this.directory = directory;
        this.instance = instance;
        this.CUSTOM_CONFIGS = configMap;
        this.namespace = namespace;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> dataMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        dataMap.forEach((resourceLocation, jsonElement) -> {
            if(!CONFIGS_TO_IGNORE.contains(resourceLocation)) {
                CustomVaultConfigReader<T> configReader = new CustomVaultConfigReader<>();
                T config = configReader.readCustomConfig(resourceLocation.getPath(), jsonElement, instance.getClass());
                CUSTOM_CONFIGS.put(new ResourceLocation(namespace, resourceLocation.getPath()), config);
            }
        });

        performFinalActions();
    }

    public void onAddListeners(AddReloadListenerEvent event) {
        event.addListener(this);
        MinecraftForge.EVENT_BUS.addListener(this::afterConfigsLoad);
    }

    @SubscribeEvent
    public void afterConfigsLoad(VaultConfigEvent.End event) {
    }

    protected void performFinalActions() {

    }

    public void addIgnoredConfig(ResourceLocation configLocation) {
        CONFIGS_TO_IGNORE.add(configLocation);
    }

    public Set<ResourceLocation> getIgnoredConfigs() {
        return CONFIGS_TO_IGNORE;
    }

    public String getNamespace() {
        return namespace;
    }


    @Override
    public @NotNull String getName() {
        return this.directory;
    }

    public ResourceLocation namespaceLoc(String name) {
        return new ResourceLocation(namespace, name);
    }

    @Override
    protected Map<ResourceLocation, JsonElement> prepare(ResourceManager p_10771_, ProfilerFiller p_10772_) {
        return super.prepare(p_10771_, p_10772_);
    }
}
