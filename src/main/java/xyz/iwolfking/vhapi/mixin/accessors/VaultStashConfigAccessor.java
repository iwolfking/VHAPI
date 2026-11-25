package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.VaultStashConfig;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(VaultStashConfig.class)
public interface VaultStashConfigAccessor {

    @Accessor("categories")
    @Nullable
    List<VaultStashConfig.Category> getCategories();

    @Accessor("categories")
    void setCategories(@Nullable List<VaultStashConfig.Category> categories);

    @Accessor("ladders")
    @Nullable
    List<VaultStashConfig.Ladder> getLadders();

    @Accessor("ladders")
    void setLadders(@Nullable List<VaultStashConfig.Ladder> ladders);

    @Accessor("breakdowns")
    @Nullable
    List<VaultStashConfig.Breakdown> getBreakdowns();

    @Accessor("breakdowns")
    void setBreakdowns(@Nullable List<VaultStashConfig.Breakdown> breakdowns);

    @Accessor("facetedFocus")
    @Nullable
    Item getFacetedFocus();

    @Accessor("facetedFocus")
    void setFacetedFocus(@Nullable Item facetedFocus);

    @Accessor("logLines")
    int getLogLines();

    @Accessor("logLines")
    void setLogLines(int logLines);

    @Accessor("logMergeSeconds")
    int getLogMergeSeconds();

    @Accessor("logMergeSeconds")
    void setLogMergeSeconds(int logMergeSeconds);

    @Accessor("looks")
    @Nullable
    VaultStashConfig.Looks getLooks();

    @Accessor("looks")
    void setLooks(@Nullable VaultStashConfig.Looks looks);
}