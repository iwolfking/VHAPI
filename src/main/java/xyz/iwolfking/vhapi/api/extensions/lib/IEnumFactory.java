package xyz.iwolfking.vhapi.api.extensions.lib;

import net.minecraft.resources.ResourceLocation;
import xyz.iwolfking.vhapi.api.lib.core.extensibleenum.EnumAppender;

import java.util.Locale;
import java.util.Objects;

public interface IEnumFactory {

    /**
     * Binary class name of the target enum (e.g. {@code "iskallia.vault...Foo$Bar"}).
     */
    String getTargetEnumClassName();

    default <E extends Enum<E>> Class<E> getTargetEnumClass() throws ClassNotFoundException {
        @SuppressWarnings("unchecked")
        Class<E> clazz = (Class<E>) Class.forName(getTargetEnumClassName());
        return clazz;
    }

    /**
     * Alternates {@code ordinal_i, clazz_i} pairs matching the declared fields on the target enum.
     * Override if the target enum has fields to populate.
     */
    default Object[] getFieldSpec() {
        return new Object[0];
    }

    default String toInternalName(ResourceLocation id) {
        Objects.requireNonNull(id, "id");
        return id.toDebugFileName().toUpperCase(Locale.ROOT);
    }

    default <E extends Enum<E>> E appendConstant(
            ResourceLocation id,
            Object[] fieldValues,
            Class<?>... switchUsers
    ) {
        return appendConstant(toInternalName(id), fieldValues, switchUsers);
    }

    @SuppressWarnings("unchecked")
    default <E extends Enum<E>> E appendConstant(
            String enumConstantName,
            Object[] fieldValues,
            Class<?>... switchUsers
    ) {
        Objects.requireNonNull(enumConstantName, "enumConstantName");
        try {
            Class<E> enumClass = getTargetEnumClass();
            EnumAppender<E> appender = EnumAppender.createFromSpec(enumClass, getFieldSpec());

            appender.addEnumConstant(enumConstantName, fieldValues);
            appender.applyTo(switchUsers);

            for (E constant : enumClass.getEnumConstants()) {
                if (constant.name().equals(enumConstantName)) {
                    return constant;
                }
            }
            throw new IllegalStateException("Added constant " + enumConstantName + " to " + enumClass.getName() + " but could not find it");
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to register enum constant " + enumConstantName + " on " + getTargetEnumClassName(), e);
        }
    }
}