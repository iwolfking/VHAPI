package xyz.iwolfking.vhapi.api.lib.config.loaders.card;

import iskallia.vault.config.card.BoosterPackConfig;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.BoosterPackItem;
import iskallia.vault.mixin.MixinModelBakery;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraftforge.client.model.ForgeModelBakery;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.lib.loaders.VaultConfigDataLoader;
import java.util.HashMap;


public class BoosterPacksConfigLoader extends VaultConfigDataLoader<BoosterPackConfig> {
    public BoosterPacksConfigLoader(String namespace) {
        super(new BoosterPackConfig(), "card/booster_packs", new HashMap<>(), namespace);
    }

    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        for(BoosterPackConfig config : this.CUSTOM_CONFIGS.values()) {
            ModConfigs.BOOSTER_PACK.getValues().putAll(config.getValues());
        }

    }
}
