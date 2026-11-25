package xyz.iwolfking.vhapi.api.extensions.gear;

import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.attribute.VaultGearModifier.AffixCategory;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.extensions.lib.IEnumFactory;

import javax.annotation.Nullable;
import java.util.function.Function;

public class AffixCategoryFactory implements IEnumFactory {
    public static final AffixCategoryFactory INSTANCE = new AffixCategoryFactory();

    private AffixCategoryFactory() {
    }

    @Override
    public String getTargetEnumClassName() {
        return VaultGearModifier.AffixCategory.class.getName();
    }

    @Override
    public Object[] getFieldSpec() {
        return new Object[]{
                0, String.class,
                0, Function.class,
                0, ResourceLocation.class
        };
    }
    public AffixCategory create(
            String enumName,
            String descriptor,
            Function<MutableComponent, MutableComponent> modifierFormatter,
            @Nullable ResourceLocation overlayIcon,
            Class<?>... switchUsers
    ) {
        Function<MutableComponent, MutableComponent> formatter = modifierFormatter != null
                ? modifierFormatter
                : Function.identity();
        return appendConstant(
                enumName,
                new Object[]{descriptor, formatter, overlayIcon},
                switchUsers
        );
    }

    public AffixCategory create(
            String enumName,
            String descriptor,
            Class<?>... switchUsers
    ) {
        return create(enumName, descriptor, Function.identity(), null, switchUsers);
    }

    public AffixCategory create(
            ResourceLocation id,
            String descriptor,
            Function<MutableComponent, MutableComponent> modifierFormatter,
            @Nullable ResourceLocation overlayIcon,
            Class<?>... switchUsers
    ) {
        return create(toInternalName(id), descriptor, modifierFormatter, overlayIcon, switchUsers);
    }

    public AffixCategory create(
            ResourceLocation id,
            String descriptor,
            Class<?>... switchUsers
    ) {
        return create(toInternalName(id), descriptor, Function.identity(), null, switchUsers);
    }
}