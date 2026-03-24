package xyz.iwolfking.vhapi.api.datagen.boxes;

import iskallia.vault.config.UnidentifiedTreasureKeyConfig;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.api.datagen.AbstractVaultConfigDataProvider;
import xyz.iwolfking.vhapi.api.datagen.lib.loot.LootableConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.UnidentifiedTreasureKeyAccessorConfig;

public abstract class AbstractTreasureKeyProvider extends AbstractVaultConfigDataProvider<AbstractTreasureKeyProvider.Builder> {

    protected AbstractTreasureKeyProvider(DataGenerator generator, String modid) {
        super(generator, modid, "loot_box/unidentified_treasure_key", Builder::new);
    }


    @Override
    public String getName() {
        return modid + " Unidentified Treasure Key Data Provider";
    }

    public static class Builder extends LootableConfigBuilder<UnidentifiedTreasureKeyConfig> {

        public Builder() {
            super(UnidentifiedTreasureKeyConfig::new);
        }

        @Override
        protected void configureConfig(UnidentifiedTreasureKeyConfig config) {
            ((UnidentifiedTreasureKeyAccessorConfig)config).getTreasureKeys().addAll(entries);
        }
    }
}
