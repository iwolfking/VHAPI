package xyz.iwolfking.vhapi.api.extensions.general;

import iskallia.vault.VaultMod;
import iskallia.vault.item.tool.ToolMaterial;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.extensions.lib.IEnumFactory;

public class VaultToolMaterialFactory implements IEnumFactory {
    public static final VaultToolMaterialFactory INSTANCE = new VaultToolMaterialFactory();

    private VaultToolMaterialFactory() {
    }

    @Override
    public String getTargetEnumClassName() {
        return ToolMaterial.class.getName();
    }

    // Declared fields in ToolMaterial:
    // 0 String -> id
    // 1 String -> description
    // 0 int    -> level
    // 1 int    -> capacity
    // 0 float  -> miningSpeed
    // 2 int    -> durability
    // 3 int    -> repairs
    @Override
    public Object[] getFieldSpec() {
        return new Object[]{
                0, String.class,
                1, String.class,
                0, int.class,
                1, int.class,
                0, float.class,
                2, int.class,
                3, int.class
        };
    }

    public ToolMaterial create(
            String enumName,
            String id,
            int level,
            int capacity,
            float miningSpeed,
            int durability,
            int repairs,
            Class<?>... switchUsers
    ) {
        String description = Util.makeDescriptionId("item", VaultMod.id("tool.material." + id));
        return appendConstant(
                enumName,
                new Object[]{id, description, level, capacity, miningSpeed, durability, repairs},
                switchUsers
        );
    }

    public ToolMaterial create(
            ResourceLocation enumId,
            String id,
            int level,
            int capacity,
            float miningSpeed,
            int durability,
            int repairs,
            Class<?>... switchUsers
    ) {
        return create(toInternalName(enumId), id, level, capacity, miningSpeed, durability, repairs, switchUsers);
    }
}