package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.skill.expertise.type.FarmerTwerker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = FarmerTwerker.class, remap = false)
public interface FarmerTwerkerAccessor {
    @Accessor("tickDelay")
    int getTickDelay();

    @Accessor("horizontalRange")
    int getHorizontalRange();

    @Accessor("verticalRange")
    int getVerticalRange();

    @Accessor("adultChance")
    float getAdultChance();

    @Accessor("tickDelay")
    void setTickDelay(int delay);

    @Accessor("horizontalRange")
    void setHorizontalRange(int range);

    @Accessor("verticalRange")
    void setVerticalRange(int range);

    @Accessor("adultChance")
    void setAdultChance(float adultChance);
}
