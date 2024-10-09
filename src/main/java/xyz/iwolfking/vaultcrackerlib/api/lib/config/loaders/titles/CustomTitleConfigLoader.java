package xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.titles;

import iskallia.vault.config.PlayerTitlesConfig;
import iskallia.vault.dynamodel.DynamicModel;
import iskallia.vault.dynamodel.DynamicModelProperties;
import iskallia.vault.dynamodel.model.item.HandHeldModel;
import iskallia.vault.dynamodel.registry.DynamicModelRegistry;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.init.ModDynamicModels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.client.model.SimpleModelState;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import xyz.iwolfking.vaultcrackerlib.api.events.VaultConfigEvent;
import xyz.iwolfking.vaultcrackerlib.api.helpers.workstations.AscensionForgeHelper;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog.lib.GsonHandheldModel;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.gear.transmog.lib.HandheldModelConfig;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.titles.lib.CustomTitleConfig;
import xyz.iwolfking.vaultcrackerlib.api.lib.config.loaders.titles.lib.GsonPlayerTitle;
import xyz.iwolfking.vaultcrackerlib.api.lib.loaders.VaultConfigDataLoader;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.DynamicModelPropertiesAccessor;
import xyz.iwolfking.vaultcrackerlib.mixin.accessors.PlayerTitlesConfigAccessor;

import java.util.HashMap;

public class CustomTitleConfigLoader extends VaultConfigDataLoader<CustomTitleConfig> {

    public CustomTitleConfigLoader(String namespace) {
        super(new CustomTitleConfig(), "player_titles", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(CustomTitleConfig config : this.CUSTOM_CONFIGS.values()) {
            for(GsonPlayerTitle title : config.PREFIXES) {
                ModConfigs.PLAYER_TITLES.getAll(PlayerTitlesConfig.Affix.PREFIX).put(title.id, title.title());
                ModConfigs.ASCENSION_FORGE.getListings().add(title.titleStack(PlayerTitlesConfig.Affix.PREFIX));
            }

            for(GsonPlayerTitle title : config.SUFFIXES) {
                ModConfigs.PLAYER_TITLES.getAll(PlayerTitlesConfig.Affix.SUFFIX).put(title.id, title.title());
                ModConfigs.ASCENSION_FORGE.getListings().add(title.titleStack(PlayerTitlesConfig.Affix.SUFFIX));
            }
        }
    }


}
