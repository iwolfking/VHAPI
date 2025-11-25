package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.PrestigePowersGUIConfig;
import iskallia.vault.config.TalentsGUIConfig;
import iskallia.vault.config.entry.SkillStyle;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.ExpertisesGUIConfigAccessor;

import java.util.HashMap;

public abstract class AbstractTalentStyleProvider extends AbstractVaultConfigDataProvider<AbstractTalentStyleProvider.Builder> {
    protected AbstractTalentStyleProvider(DataGenerator generator, String modid) {
        super(generator, modid, "talents/talents_gui", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Talents Style Provider";
    }


    public static class Builder extends VaultConfigBuilder<TalentsGUIConfig> {
        private final HashMap<String, SkillStyle> styles = new HashMap<>();

        public Builder() {
            super(TalentsGUIConfig::new);
        }

        public Builder addStyle(String expertiseName, SkillStyle style) {
            styles.put(expertiseName, style);
            return this;
        }

        @Override
        protected void configureConfig(TalentsGUIConfig config) {
            ((ExpertisesGUIConfigAccessor)config).setStyles(styles);
        }


    }

}
