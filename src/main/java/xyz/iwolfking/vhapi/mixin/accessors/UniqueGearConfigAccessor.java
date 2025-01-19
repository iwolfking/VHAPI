package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.UniqueGearConfig;
import iskallia.vault.core.util.WeightedList;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = UniqueGearConfig.class, remap = false)
public interface UniqueGearConfigAccessor {
    @Accessor("pools")
    Map<ResourceLocation, WeightedList<ResourceLocation>> getPools();

    @Accessor("pools")
    void setPools( Map<ResourceLocation, WeightedList<ResourceLocation>> pools);

    @Accessor("registry")
    Map<ResourceLocation, UniqueGearConfig.Entry> getEntries();

    @Accessor("registry")
    void setEntries(Map<ResourceLocation, UniqueGearConfig.Entry> registry);
}
