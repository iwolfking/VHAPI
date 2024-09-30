package xyz.iwolfking.vaultcrackerlib.api.patching.configs.research;

import iskallia.vault.config.entry.SkillStyle;
import iskallia.vault.research.type.CustomResearch;
import iskallia.vault.research.type.ModResearch;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ResearchPatcher {

    private static boolean isPatched = false;

    private static Set<ModResearch> PATCHED_RESEARCH_CONFIG = new HashSet<>();
    private static Set<CustomResearch> PATCHED_CUSTOM_RESEARCH_CONFIG = new HashSet<>();

    public static boolean isPatched() {
        return isPatched;
    }

    public static void setPatched(boolean isPatched) {
        ResearchPatcher.isPatched = isPatched;
    }


    public static void addResearch(ModResearch research) {
        PATCHED_RESEARCH_CONFIG.add(research);
    }

    public static void addCustomResearch(CustomResearch research) {
        PATCHED_CUSTOM_RESEARCH_CONFIG.add(research);
    }

    public static Set<ModResearch> getCustomModResearches() {
        return PATCHED_RESEARCH_CONFIG;
    }
    public static Set<CustomResearch> getCustomResearches() {
        return PATCHED_CUSTOM_RESEARCH_CONFIG;
    }





}
