package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.core.vault.challenge.action.FloatingItemRewardChallengeAction;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = FloatingItemRewardChallengeAction.Config.class,remap = false)
public interface FloatingItemRewardChallengeActionConfigAccessor {
    @Accessor("item")
    void setItemStack(ItemStack stack);

    @Accessor("name")
    void setName(String name);

    @Accessor("color1")
    void setColor1(int color);

    @Accessor("color2")
    void setColor2(int color);
}
