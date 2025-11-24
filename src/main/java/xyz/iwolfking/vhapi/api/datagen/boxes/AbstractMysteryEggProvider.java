package xyz.iwolfking.vhapi.api.datagen.boxes;

import iskallia.vault.config.MysteryEggConfig;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.api.datagen.AbstractVaultConfigDataProvider;
import xyz.iwolfking.vhapi.api.datagen.lib.loot.LootableConfigBuilder;

import java.util.function.Supplier;

public abstract class AbstractMysteryEggProvider extends AbstractVaultConfigDataProvider<AbstractMysteryEggProvider.Builder> {

    protected AbstractMysteryEggProvider(DataGenerator generator, String modid) {
        super(generator, modid, "loot_box/mystery_egg", Builder::new);
    }


    @Override
    public String getName() {
        return modid + " Mystery Egg Data Provider";
    }

    public static class Builder extends LootableConfigBuilder<MysteryEggConfig> {

        public Builder() {
            super(MysteryEggConfig::new);
        }

        @Override
        protected void configureConfig(MysteryEggConfig config) {
            config.POOL.addAll(entries);
        }
    }
}
