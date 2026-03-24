package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.VaultMapRoomIconsConfig;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.VaultMapRoomIconsConfigAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractVaultMapIconsProvider extends AbstractVaultConfigDataProvider<AbstractVaultMapIconsProvider.Builder> {
    protected AbstractVaultMapIconsProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vault/map_icons", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public @NotNull String getName() {
        return modid + " Vault Map Icons Provider";
    }

    public static class Builder extends VaultConfigBuilder<VaultMapRoomIconsConfig> {
        private Map<ResourceLocation, List<ResourceLocation>> roomIcons = new HashMap<>();

        public Builder() {
            super(VaultMapRoomIconsConfig::new);
        }

        public Builder addRoomIcons(ResourceLocation textureId, Consumer<List<ResourceLocation>> templateIdsConsumer) {
            List<ResourceLocation> templateIds = new ArrayList<>();
            templateIdsConsumer.accept(templateIds);
            roomIcons.put(textureId, templateIds);
            return this;
        }

        @Override
        protected void configureConfig(VaultMapRoomIconsConfig config) {
            ((VaultMapRoomIconsConfigAccessor)config).setRoomIcons(roomIcons);
        }

    }
}
