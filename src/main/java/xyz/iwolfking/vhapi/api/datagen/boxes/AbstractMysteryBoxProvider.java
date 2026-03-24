package xyz.iwolfking.vhapi.api.datagen.boxes;

import iskallia.vault.config.MysteryBoxConfig;
import iskallia.vault.config.MysteryHostileEggConfig;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.api.datagen.AbstractVaultConfigDataProvider;
import xyz.iwolfking.vhapi.api.datagen.lib.loot.LootableConfigBuilder;

public abstract class AbstractMysteryBoxProvider extends AbstractVaultConfigDataProvider<AbstractMysteryBoxProvider.Builder> {

    protected AbstractMysteryBoxProvider(DataGenerator generator, String modid) {
        super(generator, modid, "loot_box/mystery_box", Builder::new);
    }


    @Override
    public String getName() {
        return modid + " Mystery Box Provider";
    }

    public static class Builder extends LootableConfigBuilder<MysteryBoxConfig> {

        public Builder() {
            super(MysteryBoxConfig::new);
        }

        @Override
        protected void configureConfig(MysteryBoxConfig config) {
            config.POOL.addAll(entries);
        }
    }
}
