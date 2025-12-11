package xyz.iwolfking.vhapi.init;

import xyz.iwolfking.vhapi.api.config.AttributeCapConfig;
import xyz.iwolfking.vhapi.api.config.ResearchExclusionConfig;
import xyz.iwolfking.vhapi.api.config.VaultarResearchGatesConfig;

public class ModConfigs {
    public static ResearchExclusionConfig RESEARCH_EXCLUSIONS = new ResearchExclusionConfig();
    public static VaultarResearchGatesConfig VAULTAR_RESEARCH_GATES = new VaultarResearchGatesConfig();
    public static AttributeCapConfig ATTRIBUTE_CAP_CONFIG = new AttributeCapConfig();

    public static void register() {
        RESEARCH_EXCLUSIONS = new ResearchExclusionConfig().readConfig();
        VAULTAR_RESEARCH_GATES = new VaultarResearchGatesConfig().readConfig();
        ATTRIBUTE_CAP_CONFIG = new AttributeCapConfig().readConfig();
    }
}
