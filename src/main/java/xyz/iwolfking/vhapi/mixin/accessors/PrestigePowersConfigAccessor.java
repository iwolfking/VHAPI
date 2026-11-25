package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.PrestigePowersConfig;
import iskallia.vault.skill.tree.PrestigeTree;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PrestigePowersConfig.class)
public interface PrestigePowersConfigAccessor {

    @Accessor("tree")
    PrestigeTree getTree();

    @Accessor("tree")
    void setTree(PrestigeTree tree);

    @Accessor("ladder")
    PrestigePowersConfig.Ladder getLadder();

    @Accessor("ladder")
    void setLadder(PrestigePowersConfig.Ladder ladder);
}