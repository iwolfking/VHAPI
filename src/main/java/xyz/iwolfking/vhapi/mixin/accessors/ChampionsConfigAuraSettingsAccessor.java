package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.config.ChampionsConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChampionsConfig.AuraSettings.class)
public interface ChampionsConfigAuraSettingsAccessor {

    @Accessor("enabled")
    boolean getEnabled();

    @Accessor("enabled")
    void setEnabled(boolean enabled);

    @Accessor("color")
    String getColor();

    @Accessor("color")
    void setColor(String color);

    @Accessor("radius")
    void setRadius(float radius);

    @Accessor("value")
    float getValue();

    @Accessor("value")
    void setValue(float value);

    @Accessor("intervalTicks")
    void setIntervalTicks(int intervalTicks);

    @Accessor("periodTicks")
    void setPeriodTicks(int periodTicks);

    @Accessor("chargeTicks")
    void setChargeTicks(int chargeTicks);

    @Accessor("cooldownTicks")
    void setCooldownTicks(int cooldownTicks);

    @Accessor("weight")
    float getWeight();

    @Accessor("weight")
    void setWeight(float weight);
}