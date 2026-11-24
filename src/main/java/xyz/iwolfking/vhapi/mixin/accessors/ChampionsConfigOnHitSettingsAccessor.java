package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.ChampionsConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChampionsConfig.OnHitSettings.class)
public interface ChampionsConfigOnHitSettingsAccessor {

    @Accessor("enabled")
    boolean getEnabled();

    @Accessor("enabled")
    void setEnabled(boolean enabled);

    @Accessor("value")
    float getValue();

    @Accessor("value")
    void setValue(float value);

    @Accessor("maxStacks")
    void setMaxStacks(int maxStacks);

    @Accessor("durationTicks")
    void setDurationTicks(int durationTicks);

    @Accessor("weight")
    float getWeight();

    @Accessor("weight")
    void setWeight(float weight);
}