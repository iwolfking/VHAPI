package xyz.iwolfking.vhapi.api.extensions.client;

import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.extensions.lib.IEnumFactory;

public class VaultStashTabFactory implements IEnumFactory {
    public static final VaultStashTabFactory INSTANCE = new VaultStashTabFactory();

    private static final String TAB_CLASS = "iskallia.vault.client.gui.screen.block.vaultstash.VaultStashScreen$Tab";
    private static final String TAB_ROW_CLASS = "iskallia.vault.client.gui.screen.block.vaultstash.VaultStashScreen$TabRow";

    private VaultStashTabFactory() {
    }

    @Override
    public String getTargetEnumClassName() {
        return TAB_CLASS;
    }

    @Override
    public Object[] getFieldSpec() {
        try {
            Class<?> rowClass = Class.forName(TAB_ROW_CLASS);
            return new Object[] {
                    0, rowClass,
                    0, int.class,
                    1, int.class,
                    0, boolean.class,
                    0, String.class
            };
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <E extends Enum<E>> E create(
            ResourceLocation id,
            String rowName,
            int column,
            int textureColumn,
            boolean alignedRight,
            String categoryId,
            Class<?>... switchUsers
    ) {
        try {
            Class<? extends Enum> rowClass = (Class<? extends Enum>) Class.forName(TAB_ROW_CLASS);
            Enum<?> row = Enum.valueOf(rowClass, rowName);
            return appendConstant(id, new Object[] { row, column, textureColumn, alignedRight, categoryId }, switchUsers);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to register VaultStashScreen.Tab " + id, e);
        }
    }
}