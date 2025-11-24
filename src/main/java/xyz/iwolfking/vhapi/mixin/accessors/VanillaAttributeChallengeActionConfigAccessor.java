package xyz.iwolfking.vhapi.mixin.accessors;

import iskallia.vault.block.entity.challenge.raid.action.VanillaAttributeChallengeAction;
import iskallia.vault.core.world.data.entity.EntityPredicate;
import iskallia.vault.core.world.roll.DoubleRoll;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(value = VanillaAttributeChallengeAction.Config.class,remap = false)
public interface VanillaAttributeChallengeActionConfigAccessor {
    @Accessor("filter")
    void setFilter(EntityPredicate entity);

    @Accessor("name")
    void setName(String name);

    @Accessor("attribute")
    void setAttribute(Attribute attribute);

    @Accessor("operation")
    void setOperation(AttributeModifier.Operation operation);

    @Accessor("amount")
    void setAmount(List<DoubleRoll> amount);
}
