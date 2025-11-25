package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.ExpertisesGUIConfig;
import iskallia.vault.config.PrestigePowersGUIConfig;
import iskallia.vault.config.entry.SkillStyle;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.ExpertisesGUIConfigAccessor;

import java.util.HashMap;

public abstract class AbstractPrestigePowerStyleProvider extends AbstractVaultConfigDataProvider<AbstractPrestigePowerStyleProvider.Builder> {
    protected AbstractPrestigePowerStyleProvider(DataGenerator generator, String modid) {
        super(generator, modid, "prestige/prestige_gui", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Prestige Powers Style Provider";
    }


    public static class Builder extends VaultConfigBuilder<PrestigePowersGUIConfig> {
        private final HashMap<String, SkillStyle> styles = new HashMap<>();

        public Builder() {
            super(PrestigePowersGUIConfig::new);
        }

        public Builder addStyle(String expertiseName, SkillStyle style) {
            styles.put(expertiseName, style);
            return this;
        }

        @Override
        protected void configureConfig(PrestigePowersGUIConfig config) {
            ((ExpertisesGUIConfigAccessor)config).setStyles(styles);
        }


    }

}
