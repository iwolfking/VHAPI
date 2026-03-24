package xyz.iwolfking.vhapi.api.datagen.boxes;

import iskallia.vault.config.MysteryHostileEggConfig;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.api.datagen.AbstractVaultConfigDataProvider;
import xyz.iwolfking.vhapi.api.datagen.lib.loot.LootableConfigBuilder;

public abstract class AbstractMysteryHostileEggProvider extends AbstractVaultConfigDataProvider<AbstractMysteryHostileEggProvider.Builder> {

    protected AbstractMysteryHostileEggProvider(DataGenerator generator, String modid) {
        super(generator, modid, "loot_box/mystery_hostile_egg", Builder::new);
    }


    @Override
    public String getName() {
        return modid + " Mystery Hosile Egg Data Provider";
    }

    public static class Builder extends LootableConfigBuilder<MysteryHostileEggConfig> {

        public Builder() {
            super(MysteryHostileEggConfig::new);
        }

        @Override
        protected void configureConfig(MysteryHostileEggConfig config) {
            config.POOL.addAll(entries);
        }
    }
}
