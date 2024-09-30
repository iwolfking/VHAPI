package xyz.iwolfking.vaultcrackerlib.api.patching.configs.research;

import iskallia.vault.research.group.ResearchGroup;
import iskallia.vault.research.type.CustomResearch;
import iskallia.vault.research.type.ModResearch;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ResearchGroupPatcher {

    private static boolean isPatched = false;

    private static Map<String, ResearchGroup> PATCHED_RESEARCH_GROUP_CONFIG = new HashMap<>();

    private static Set<String> RESEARCH_GROUPS_TO_REMOVE = new HashSet<>();

    public static boolean isPatched() {
        return isPatched;
    }

    public static void setPatched(boolean isPatched) {
        ResearchGroupPatcher.isPatched = isPatched;
    }


    public static void addResearchGroup(String name, ResearchGroup research) {
        PATCHED_RESEARCH_GROUP_CONFIG.put(name, research);
    }

    public static void removeResearchGroup(String name) {
        RESEARCH_GROUPS_TO_REMOVE.add(name);
    }


    public static Map<String, ResearchGroup> getCustomResearchGroups() {
        return PATCHED_RESEARCH_GROUP_CONFIG;
    }
    public static Set<String> getResearchGroupsToRemove() {
        return RESEARCH_GROUPS_TO_REMOVE;
    }





}
