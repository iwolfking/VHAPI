package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.research.type.Research;
import java.util.List;
import javax.annotation.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Research.class)
public interface ResearchAccessor {

    @Accessor("name")
    void setName(String name);

    @Accessor("cost")
    void setCost(int cost);

    @Accessor("gatedBy")
    @Nullable
    String getGatedBy();

    @Accessor("gatedBy")
    void setGatedBy(@Nullable String gatedBy);

    @Accessor("discoversModels")
    @Nullable
    List<String> getDiscoversModels();

    @Accessor("discoversModels")
    void setDiscoversModels(@Nullable List<String> discoversModels);

    @Accessor("gameruleLock")
    void setGameruleLock(boolean gameruleLock);
}