package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.bounty.BountyBoardTasksConfig;
import iskallia.vault.gear.VaultGearRarity;
import iskallia.vault.task.Task;
import javax.annotation.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = BountyBoardTasksConfig.Entry.class, remap = false)
public interface BountyBoardTasksEntryAccessor {
    @Accessor("id")
    void setId(String id);

    @Accessor("title")
    void setTitle(@Nullable String title);

    @Accessor("type")
    void setType(@Nullable String type);

    @Accessor("kind")
    void setKind(@Nullable BountyBoardTasksConfig.Kind kind);

    @Accessor("normalNumber")
    void setNormalNumber(int normalNumber);

    @Accessor("maxRarity")
    void setMaxRarity(@Nullable VaultGearRarity maxRarity);

    @Accessor("weight")
    void setWeight(float weight);

    @Accessor("minLevel")
    void setMinLevel(int minLevel);

    @Accessor("maxLevel")
    void setMaxLevel(int maxLevel);

    @Accessor("minGreedTier")
    void setMinGreedTier(int minGreedTier);

    @Accessor("targetGrowthPerGreedTier")
    void setTargetGrowthPerGreedTier(float targetGrowthPerGreedTier);

    @Accessor("flavor")
    void setFlavor(@Nullable String flavor);

    @Accessor("cardIcon")
    void setCardIcon(@Nullable String cardIcon);

    @Accessor("effort")
    void setEffort(int effort);

    @Accessor("task")
    void setTask(@Nullable Task task);
}