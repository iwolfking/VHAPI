package xyz.iwolfking.vaultcrackerlib.api.patching.configs.research;

import iskallia.vault.config.entry.SkillStyle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ResearchGUIConfigPatcher {

    private static boolean isPatched = false;

    private static Map<String, SkillStyle> PATCHED_RESEARCH_GUI_CONFIG = new HashMap<>();

    private static Set<String> RESEARCH_GUI_TO_REMOVE = new HashSet<>();

    public static boolean isPatched() {
        return isPatched;
    }

    public static void setPatched(boolean isPatched) {
        ResearchGUIConfigPatcher.isPatched = isPatched;
    }


    public static void addResearchGUIStyle(String name, SkillStyle style) {
        PATCHED_RESEARCH_GUI_CONFIG.put(name, style);
    }

    public static void removeResearchGUIStyle(String name) {
        RESEARCH_GUI_TO_REMOVE.add(name);
    }

    public static Map<String, SkillStyle> getResearchConfigPatches() {
        return PATCHED_RESEARCH_GUI_CONFIG;
    }
    public static Set<String> getResearchGuiToRemove() {
        return RESEARCH_GUI_TO_REMOVE;
    }





}
