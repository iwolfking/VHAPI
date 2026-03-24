package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.TooltipConfig;
import iskallia.vault.config.VaultPortalConfig;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.api.loaders.vault.portal.VaultPortalBlockConfigLoader;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractVaultPortalBlocksProvider extends AbstractVaultConfigDataProvider<AbstractVaultPortalBlocksProvider.Builder> {
    protected AbstractVaultPortalBlocksProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vault/portal", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Vault Portal Blocks Data Provider";
    }

    public static class Builder extends VaultConfigBuilder<VaultPortalConfig> {
        List<String> portalBlocks = new ArrayList<>();

        public Builder() {
            super(VaultPortalConfig::new);
        }

        public Builder add(String validPortalBlock) {
            portalBlocks.add(validPortalBlock);
            return this;
        }

        public Builder add(Block validPortalBlock) {
            portalBlocks.add(validPortalBlock.getRegistryName().toString());
            return this;
        }

        @Override
        protected void configureConfig(VaultPortalConfig config) {
            config.VALID_BLOCKS = portalBlocks.toArray(new String[0]);
        }
    }
}
