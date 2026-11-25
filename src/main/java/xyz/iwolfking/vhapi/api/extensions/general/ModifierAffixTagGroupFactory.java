package xyz.iwolfking.vhapi.api.extensions.general;

import iskallia.vault.config.gear.VaultGearTierConfig.ModifierAffixTagGroup;
import iskallia.vault.gear.attribute.VaultGearModifier;
import iskallia.vault.gear.data.VaultGearData;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.extensions.lib.IEnumFactory;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.BiPredicate;

public class ModifierAffixTagGroupFactory implements IEnumFactory {
    public static final ModifierAffixTagGroupFactory INSTANCE = new ModifierAffixTagGroupFactory();

    private ModifierAffixTagGroupFactory() {
    }

    @Override
    public String getTargetEnumClassName() {
        return ModifierAffixTagGroup.class.getName();
    }

    // Declared fields in ModifierAffixTagGroup:
    // 0 VaultGearModifier.AffixType -> targetAffixType
    // 0 BiPredicate                 -> applyFn
    @Override
    public Object[] getFieldSpec() {
        return new Object[]{
                0, VaultGearModifier.AffixType.class,
                0, BiPredicate.class
        };
    }

    public static BiPredicate<VaultGearData, VaultGearModifier<?>> withTarget(VaultGearModifier.AffixType type) {
        Objects.requireNonNull(type, "type");
        return (data, mod) -> data.addModifier(type, mod);
    }

    public ModifierAffixTagGroup create(
            String enumName,
            @Nullable VaultGearModifier.AffixType targetAffixType,
            BiPredicate<VaultGearData, VaultGearModifier<?>> applyFn,
            Class<?>... switchUsers
    ) {
        BiPredicate<VaultGearData, VaultGearModifier<?>> predicate =
                Objects.requireNonNull(applyFn, "applyFn");
        return appendConstant(
                enumName,
                new Object[]{targetAffixType, predicate},
                switchUsers
        );
    }

    public ModifierAffixTagGroup createForAffixType(
            String enumName,
            VaultGearModifier.AffixType affixType,
            Class<?>... switchUsers
    ) {
        return create(enumName, affixType, withTarget(affixType), switchUsers);
    }

    public ModifierAffixTagGroup create(
            ResourceLocation enumId,
            @Nullable VaultGearModifier.AffixType targetAffixType,
            BiPredicate<VaultGearData, VaultGearModifier<?>> applyFn,
            Class<?>... switchUsers
    ) {
        return create(toInternalName(enumId), targetAffixType, applyFn, switchUsers);
    }

    public ModifierAffixTagGroup createForAffixType(
            ResourceLocation enumId,
            VaultGearModifier.AffixType affixType,
            Class<?>... switchUsers
    ) {
        return createForAffixType(toInternalName(enumId), affixType, switchUsers);
    }
}