package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.ExpertisesGUIConfig;
import iskallia.vault.config.entry.SkillStyle;
import net.minecraft.data.DataGenerator;
import xyz.iwolfking.vhapi.api.datagen.lib.VaultConfigBuilder;
import xyz.iwolfking.vhapi.mixin.accessors.ExpertisesGUIConfigAccessor;

import java.util.HashMap;
import java.util.function.Supplier;

public abstract class AbstractExpertiseStyleProvider extends AbstractVaultConfigDataProvider {
    protected AbstractExpertiseStyleProvider(DataGenerator generator, String modid) {
        super(generator, modid, "expertise/expertise_gui", Builder::new);
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Expertise Style Provider";
    }


    public static class Builder extends VaultConfigBuilder<ExpertisesGUIConfig> {
        private final HashMap<String, SkillStyle> styles = new HashMap<>();

        public Builder() {
            super(ExpertisesGUIConfig::new);
        }

        public Builder addStyle(String expertiseName, SkillStyle style) {
            styles.put(expertiseName, style);
            return this;
        }

        @Override
        protected void configureConfig(ExpertisesGUIConfig config) {
            ((ExpertisesGUIConfigAccessor)config).setStyles(styles);
        }


    }

}
