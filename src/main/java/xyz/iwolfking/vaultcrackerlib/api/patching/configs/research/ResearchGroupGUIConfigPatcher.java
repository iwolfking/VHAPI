package xyz.iwolfking.vaultcrackerlib.api.patching.configs.research;

import iskallia.vault.config.entry.ResearchGroupStyle;
import iskallia.vault.config.entry.SkillStyle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ResearchGroupGUIConfigPatcher {

    private static boolean isPatched = false;

    private static Map<String, ResearchGroupStyle> PATCHED_RESEARCH_GROUP_GUI_CONFIG = new HashMap<>();

    private static Set<String> RESEARCH_GUI_GROUPS_TO_REMOVE = new HashSet<>();

    public static boolean isPatched() {
        return isPatched;
    }

    public static void setPatched(boolean isPatched) {
        ResearchGroupGUIConfigPatcher.isPatched = isPatched;
    }


    public static void addResearchGroupStyle(String name, ResearchGroupStyle style) {
        PATCHED_RESEARCH_GROUP_GUI_CONFIG.put(name, style);
    }

    public static void removeResearchGroupStyle(String name) {
        RESEARCH_GUI_GROUPS_TO_REMOVE.add(name);
    }

    public static Map<String, ResearchGroupStyle> getResearchGroupGUIConfigPatches() {
        return PATCHED_RESEARCH_GROUP_GUI_CONFIG;
    }

    public static Set<String> getResearchGuiGroupsToRemove() {
        return RESEARCH_GUI_GROUPS_TO_REMOVE;
    }





}
