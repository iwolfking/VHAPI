package xyz.iwolfking.vhapi.api.extensions.gear;

import iskallia.vault.gear.VaultGearClassification;
import iskallia.vault.gear.VaultGearRarity;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.extensions.lib.IEnumFactory;

import java.util.Objects;
import java.util.function.Function;

public class VaultGearClassificationFactory implements IEnumFactory {
    public static final VaultGearClassificationFactory INSTANCE = new VaultGearClassificationFactory();

    private VaultGearClassificationFactory() {
    }

    @Override
    public String getTargetEnumClassName() {
        return VaultGearClassification.class.getName();
    }

    // Declared field in VaultGearClassification:
    // 0 Function -> modifierCountFn
    @Override
    public Object[] getFieldSpec() {
        return new Object[]{
                0, Function.class
        };
    }

    public VaultGearClassification create(
            String enumName,
            Function<VaultGearRarity, Integer> modifierCountFn,
            Class<?>... switchUsers
    ) {
        Function<VaultGearRarity, Integer> fn = Objects.requireNonNull(modifierCountFn, "modifierCountFn");
        return appendConstant(
                enumName,
                new Object[]{fn},
                switchUsers
        );
    }

    public VaultGearClassification create(
            ResourceLocation enumId,
            Function<VaultGearRarity, Integer> modifierCountFn,
            Class<?>... switchUsers
    ) {
        return create(toInternalName(enumId), modifierCountFn, switchUsers);
    }
}