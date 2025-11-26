package xyz.iwolfking.vhapi.api.loaders.skills;

import iskallia.vault.config.AbilitiesConfig;
import iskallia.vault.init.ModConfigs;
import iskallia.vault.skill.base.LearnableSkill;
import iskallia.vault.skill.base.Skill;
import iskallia.vault.skill.base.SpecializedSkill;
import iskallia.vault.skill.tree.AbilityTree;
import iskallia.vault.skill.tree.SkillTree;
import xyz.iwolfking.vhapi.api.events.VaultConfigEvent;
import xyz.iwolfking.vhapi.api.loaders.lib.core.VaultConfigProcessor;
import xyz.iwolfking.vhapi.mixin.accessors.AbilitiesConfigAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.LearnableSkillAccessor;
import xyz.iwolfking.vhapi.mixin.accessors.SpecializedSkillAccessor;

import java.util.*;
import java.util.stream.Collectors;

public class AbilitiesConfigLoader extends VaultConfigProcessor<AbilitiesConfig> {
    public AbilitiesConfigLoader() {
        super(new AbilitiesConfig(), "abilities/abilities");
    }


    @Override
    public void afterConfigsLoad(VaultConfigEvent.End event) {
        List<SkillTree> abilityTrees = new ArrayList<>();
        abilityTrees.add(ModConfigs.ABILITIES.tree);
        for(AbilitiesConfig config : this.CUSTOM_CONFIGS.values()) {
            abilityTrees.add(config.tree);
        }
        System.out.println("=== [VHAPI] BEGIN ABILITY TREE MERGE ===");
        int i = 0;

        for (SkillTree tree : abilityTrees) {
            System.out.println("Tree #" + (i++) + "  class=" + tree.getClass().getSimpleName());
            System.out.println("  Skill count: " + tree.skills.size());

            for (Skill s : tree.skills) {
                System.out.println("    - " + s.getId() + " (" + s.getClass().getSimpleName() + ")");
            }
        }

        System.out.println("=== [VHAPI] AFTER MERGE ===");
        System.out.println("Merged class: " + ModConfigs.ABILITIES.tree.getClass().getSimpleName());
        System.out.println("Merged skills: " + ModConfigs.ABILITIES.tree.skills.size());

        for (Skill s : ModConfigs.ABILITIES.tree.skills) {
            System.out.println("    => " + s.getId() + " (" + s.getClass().getSimpleName() + ")");
        }
        System.out.println("=== [VHAPI] END ABILITY TREE MERGE ===");


        ((AbilitiesConfigAccessor)ModConfigs.ABILITIES).setTree(mergeAbilityTrees(abilityTrees));

        super.afterConfigsLoad(event);
    }


    public static AbilityTree mergeAbilityTrees(List<SkillTree> trees) {
        AbilityTree merged = new AbilityTree();

        // Preserve insertion order: base skills first, then additions/overrides in config order
        Map<String, Skill> byId = new LinkedHashMap<>();

        // Iterate trees in order: base first, then custom configs in load order
        for (SkillTree tree : trees) {
            if (tree == null) continue;
            for (Skill incoming : tree.skills) {
                if (incoming == null) continue;
                String id = incoming.getId();
                if (id == null) continue;

                if (!byId.containsKey(id)) {
                    // first time: take a defensive copy
                    Skill copied = incoming.copy();
                    byId.put(id, copied);
                } else {
                    // merge with existing record (existing = earlier tree's skill)
                    Skill existing = byId.get(id);
                    Skill mergedSkill = mergeSkill(existing, incoming);
                    // ensure we store the merged result (keep insertion order)
                    byId.put(id, mergedSkill);
                }
            }
        }

        // Add merged skills into final tree in insertion order
        for (Skill s : byId.values()) {
            // ensure parent pointer is set to merged
            s.setParent(merged);
            merged.skills.add(s);
        }

        return merged;
    }

    private static Skill mergeSkill(Skill base, Skill incoming) {
        // If both are SpecializedSkill -> merge recursively
        if (base instanceof SpecializedSkill aSpec && incoming instanceof SpecializedSkill bSpec) {
            return mergeSpecializedSkill(aSpec, bSpec);
        }

        // If both are LearnableSkill -> merge basic fields
        if (base instanceof LearnableSkill aLearn && incoming instanceof LearnableSkill bLearn) {
            return mergeLearnableSkill(aLearn, bLearn);
        }

        // Default: incoming overrides, but preserve parent/id by copying incoming
        Skill copy = incoming.copy();
        copy.setParent(base.getParent()); // keep same parent reference (will be set to merged later)
        return copy;
    }

    private static LearnableSkill mergeLearnableSkill(LearnableSkill a, LearnableSkill b) {
        // start with a copy of a (earlier tree)
        LearnableSkill merged = a.copy();

        // Accessors (your mixin accessors)
        LearnableSkillAccessor accA = (LearnableSkillAccessor) a;
        LearnableSkillAccessor accB = (LearnableSkillAccessor) b;
        LearnableSkillAccessor accM = (LearnableSkillAccessor) merged;

        // Merge numeric fields: choose the min cost/unlock (or choose other policy)
        accM.setUnlockLevel(Math.min(accA.getUnlockLevel(), accB.getUnlockLevel()));
        accM.setLearnCost(Math.min(accA.getLearnCost(), accB.getLearnCost()));
        accM.setRegretCost(Math.min(accA.getRegretCost(), accB.getRegretCost()));

        // Merge present/unlocked state: if any config has it present, keep it
        merged.setPresent(a.isUnlocked() || b.isUnlocked(), null); // SkillContext unused here

        return merged;
    }

    private static SpecializedSkill mergeSpecializedSkill(SpecializedSkill a, SpecializedSkill b) {
        // Start from a copy of 'a' (base / earlier)
        SpecializedSkill merged = a.copy();

        // Map children by id for both a and b (preserve order of 'a' then include b-only)
        Map<String, LearnableSkill> mapA = a.getSpecializations()
                .stream().collect(Collectors.toMap(Skill::getId, s -> s, (x,y)->x, LinkedHashMap::new));
        Map<String, LearnableSkill> mapB = b.getSpecializations()
                .stream().collect(Collectors.toMap(Skill::getId, s -> s, (x,y)->x, LinkedHashMap::new));

        List<LearnableSkill> mergedChildren = new ArrayList<>();

        // Keep order from A, merging B's versions where present
        for (LearnableSkill childA : a.getSpecializations()) {
            LearnableSkill child;
            if (mapB.containsKey(childA.getId())) {
                child = mergeLearnableSkill(childA, mapB.get(childA.getId()));
            } else {
                child = (LearnableSkill) childA.copy();
            }
            child.setParent(merged);
            mergedChildren.add(child);
        }

        // Add any children that are only present in B (new specializations)
        for (LearnableSkill childB : b.getSpecializations()) {
            if (!mapA.containsKey(childB.getId())) {
                LearnableSkill copy = (LearnableSkill) childB.copy();
                copy.setParent(merged);
                mergedChildren.add(copy);
            }
        }

        // Set merged children on object (use accessor)
        ((SpecializedSkillAccessor) merged).setSpecializations(mergedChildren);

        // Merge selected index: if B selected a specialization, prefer that; else keep A
        String selectedFromB = b.getSpecialization() != null ? b.getSpecialization().getId() : null;
        if (selectedFromB != null) {
            int idx = merged.indexOf(selectedFromB);
            merged.specialize(Math.max(idx, 0), null); // SkillContext unused here
        }

        return merged;
    }






}
