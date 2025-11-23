package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.TooltipConfig;
import iskallia.vault.config.VaultMapRoomIconsConfig;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import xyz.iwolfking.vhapi.mixin.accessors.TooltipConfigAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractVaultMapIconsProvider extends AbstractVaultConfigDataProvider {
    protected AbstractVaultMapIconsProvider(DataGenerator generator, String modid) {
        super(generator, modid, "vault/map_icons");
    }

    public abstract void registerConfigs();

    @Override
    public @NotNull String getName() {
        return modid + " Vault Map Icons Provider";
    }

    public static class VaultMapIconsConfigBuilder {
        private Map<ResourceLocation, List<ResourceLocation>> roomIcons = new HashMap<>();

        public VaultMapIconsConfigBuilder addRoomIcons(ResourceLocation textureId, Consumer<List<ResourceLocation>> templateIdsConsumer) {
            List<ResourceLocation> templateIds = new ArrayList<>();
            templateIdsConsumer.accept(templateIds);
            roomIcons.put(textureId, templateIds);
            return this;
        }

        public VaultMapRoomIconsConfig build() {
            VaultMapRoomIconsConfig newConfig = new VaultMapRoomIconsConfig();
            newConfig.getRoomIcons().putAll(roomIcons);
            return newConfig;
        }

    }
}
