package xyz.iwolfking.vhapi.api.datagen;

import iskallia.vault.config.ExpertisesConfig;
import iskallia.vault.config.ExpertisesGUIConfig;
import iskallia.vault.config.ResearchesGUIConfig;
import iskallia.vault.config.entry.SkillStyle;
import net.minecraft.data.DataGenerator;

import java.util.HashMap;

public abstract class AbstractExpertiseStyleProvider extends AbstractVaultConfigDataProvider {
    protected AbstractExpertiseStyleProvider(DataGenerator generator, String modid) {
        super(generator, modid, "expertise/expertise_gui");
    }

    public abstract void registerConfigs();

    @Override
    public String getName() {
        return modid + " Expertise Style Provider";
    }


    public static class ExpertiseStyleBuilder {
        private final HashMap<String, SkillStyle> styles = new HashMap<>();

        public ExpertiseStyleBuilder addStyle(String expertiseName, SkillStyle style) {
            styles.put(expertiseName, style);
            return this;
        }

        public ExpertisesGUIConfig build() {
            ExpertisesGUIConfig config = new ExpertisesGUIConfig();
            config.getStyles().putAll(styles);
            return config;
        }

    }

}
