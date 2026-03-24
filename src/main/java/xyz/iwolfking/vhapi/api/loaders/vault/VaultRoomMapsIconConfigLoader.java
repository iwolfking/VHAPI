package xyz.iwolfking.vhapi.api.loaders.vault;

import iskallia.vault.config.VaultMapRoomIconsConfig;
import iskallia.vault.init.ModConfigs;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;

public class VaultRoomMapsIconConfigLoader extends VaultConfigProcessor<VaultMapRoomIconsConfig> {
    public VaultRoomMapsIconConfigLoader() {
        super(new VaultMapRoomIconsConfig(), "vault/map_icons");
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        this.CUSTOM_CONFIGS.forEach((resourceLocation, vaultMapRoomIconsConfig) -> {
            ModConfigs.VAULT_MAP_ICONS.getRoomIcons().putAll(vaultMapRoomIconsConfig.getRoomIcons());
        });
        super.afterConfigsLoad(event);
    }
}
