package xyz.iwolfking.vhapi.api.lib.core.extensibleenum;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * a builder for extending enums with additional constants
 * somewhat similar to <a href="https://github.com/Chocohead/Fabric-ASM/blob/master/src/com/chocohead/mm/api/EnumAdder.java">EnumAdder.java</a>
 * main difference is this uses {@link sun.misc.Unsafe} instead of ASM
 * call {@link #applyTo} at the end, or nothing will be added!
 *
 * @param <T> type of enum constant
 */
public final class EnumAppender<T extends Enum<T>> {
    /**
     * map for correctly handling boxed and unboxed class types
     * important to actually use the class type originally defined for the enum, not what we get from argument objects
     */
    private static final Map<Class<?>, Class<?>> PRIMITIVE_TYPES = ImmutableMap.<Class<?>, Class<?>>builder().put(Integer.class, int.class).put(Boolean.class, boolean.class).put(Byte.class, byte.class).put(Short.class, short.class).put(Character.class, char.class).put(Long.class, long.class).put(Float.class, float.class).put(Double.class, double.class).build();

    /**
     * the enum class we wish to append constant values to
     */
    private final Class<T> enumClazz;
    /**
     * if {@link #enumClazz} is abstract, we need a concrete implementation to be able to create new instances
     * otherwise this is the same as {@link #enumClazz}
     */
    private final Class<? extends T> enumConcreteClazz;
    /**
     * relevant fields for constructing this enum
     * during actual construction no constructor will be called, so all data types will have their default Java value (0, false, null, etc.)
     * since we cannot look inside the constructor to find out what fields are actually set (there might be more fields in the enum than the constructor populates),
     * we can access all fields in the enum class using this, for setting our desired values
     * IMPORTANT: this needs to include all fields set in an enum's constructor, even those with default value (which are not passed when creating a new enum constant)
     */
    private final List<Field> fields;
    /**
     * map of all additions, will be applied when {@link #applyTo} is called
     */
    private final Map<String, Runnable> additions = Maps.newHashMap();

    /**
     * @param enumClazz the enum class we wish to add a constant to
     */
    public EnumAppender(Class<T> enumClazz) {
        this(enumClazz, Collections.emptyList());
    }

    /**
     * @param enumClazz the enum class we wish to add a constant to
     * @param fields    fields we need to initialize
     */
    private EnumAppender(Class<T> enumClazz, List<FieldAccess> fields) {
        this(enumClazz, enumClazz, fields);
    }

    /**
     * @param enumClazz         the enum class we wish to add a constant to
     * @param enumConcreteClazz in case of <code>enumClazz</code> being abstract, this is an implementation of it (otherwise equal)
     */
    public EnumAppender(Class<T> enumClazz, Class<? extends T> enumConcreteClazz) {
        this(enumClazz, enumConcreteClazz, Collections.emptyList());
    }

    /**
     * @param enumClazz         the enum class we wish to add a constant to
     * @param enumConcreteClazz in case of <code>enumClazz</code> being abstract, this is an implementation of it (otherwise equal)
     * @param fields            fields we need to initialize
     */
    private EnumAppender(Class<T> enumClazz, Class<? extends T> enumConcreteClazz, List<FieldAccess> fields) {
        this.enumClazz = enumClazz;
        this.enumConcreteClazz = enumConcreteClazz;
        this.fields = fields.stream().map(f -> findField(enumClazz, f.ordinal(), f.clazz())).collect(ImmutableList.toImmutableList());
    }

    /**
     * helper method for handling boxed and unboxed classes of primitive types
     *
     * @param oClazz       class of the object we've received for enum construction
     * @param fClazz       the actual type this value needs to have at the end
     * @param boxedClazz   boxed class we want to check (e.g. <code>Integer.class</code>)
     * @param unboxedClazz unboxed class (e.g. <code>int.class</code>)
     * @return <code>fClazz</code> when there was a match, otherwise empty
     */
    private static Optional<Class<?>> findCommonType(Class<?> oClazz, Class<?> fClazz, Class<?> boxedClazz, Class<?> unboxedClazz) {
        if (oClazz == boxedClazz || oClazz == unboxedClazz) {
            if (fClazz == boxedClazz || fClazz == unboxedClazz) {
                return Optional.of(fClazz);
            }
        }
        return Optional.empty();
    }

    /**
     * find a field at a given ordinal
     *
     * @param enumClazz the class to look for the field in
     * @param ordinal   ordinal of this field (like with mixins)
     * @param clazz     clazz type of the field
     * @return the field, it must be present or an exception will be raised
     */
    private static Field findField(Class<? extends Enum<?>> enumClazz, int ordinal, Class<?> clazz) {
        for (Field field : enumClazz.getDeclaredFields()) {
            if (field.getType() == clazz && ordinal-- == 0) return field;
        }
        throw new IllegalStateException("No field of type %s found at ordinal %s in enum class %s".formatted(clazz, ordinal, enumClazz));
    }

    /**
     * basic version, enum doesn't require any arguments on construction
     *
     * @param enumClazz type of enum constant to construct
     * @param <T>       type of enum
     * @return a new builder
     */
    public static <T extends Enum<T>> EnumAppender<T> create(Class<T> enumClazz) {
        return new EnumAppender<>(enumClazz);
    }

    /**
     * basic version with types, only works when no type has a duplicate, otherwise use one of the overloads below with ordinals (indices)
     *
     * @param enumClazz type of enum constant to construct
     * @param clazzes   class types for construction
     * @param <T>       type of enum
     * @return a new builder
     */
    public static <T extends Enum<T>> EnumAppender<T> create(Class<T> enumClazz, Class<?>... clazzes) {
        return new EnumAppender<>(enumClazz, Stream.of(clazzes).map(clazz -> new FieldAccess(0, clazz)).toList());
    }

    /**
     * allows for defining an ordinal where the field of <code>clazz_</code> is to be found
     * must be used when multiple fields have the same type
     *
     * @param enumClazz type of enum constant to construct
     * @param <T>       type of enum
     * @return a new builder
     */
    public static <T extends Enum<T>> EnumAppender<T> create(Class<T> enumClazz, int ordinal0, Class<?> clazz0, int ordinal1, Class<?> clazz1) {
        ImmutableList.Builder<FieldAccess> builder = ImmutableList.builder();
        builder.add(new FieldAccess(ordinal0, clazz0));
        builder.add(new FieldAccess(ordinal1, clazz1));
        return new EnumAppender<>(enumClazz, builder.build());
    }

    /**
     * allows for defining an ordinal where the field of <code>clazz_</code> is to be found
     * must be used when multiple fields have the same type
     *
     * @param enumClazz type of enum constant to construct
     * @param <T>       type of enum
     * @return a new builder
     */
    public static <T extends Enum<T>> EnumAppender<T> create(Class<T> enumClazz, int ordinal0, Class<?> clazz0, int ordinal1, Class<?> clazz1, int ordinal2, Class<?> clazz2) {
        ImmutableList.Builder<FieldAccess> builder = ImmutableList.builder();
        builder.add(new FieldAccess(ordinal0, clazz0));
        builder.add(new FieldAccess(ordinal1, clazz1));
        builder.add(new FieldAccess(ordinal2, clazz2));
        return new EnumAppender<>(enumClazz, builder.build());
    }

    /**
     * allows for defining an ordinal where the field of <code>clazz_</code> is to be found
     * must be used when multiple fields have the same type
     *
     * @param enumClazz type of enum constant to construct
     * @param <T>       type of enum
     * @return a new builder
     */
    public static <T extends Enum<T>> EnumAppender<T> create(Class<T> enumClazz, int ordinal0, Class<?> clazz0, int ordinal1, Class<?> clazz1, int ordinal2, Class<?> clazz2, int ordinal3, Class<?> clazz3) {
        ImmutableList.Builder<FieldAccess> builder = ImmutableList.builder();
        builder.add(new FieldAccess(ordinal0, clazz0));
        builder.add(new FieldAccess(ordinal1, clazz1));
        builder.add(new FieldAccess(ordinal2, clazz2));
        builder.add(new FieldAccess(ordinal3, clazz3));
        return new EnumAppender<>(enumClazz, builder.build());
    }

    /**
     * allows for defining an ordinal where the field of <code>clazz_</code> is to be found
     * must be used when multiple fields have the same type
     *
     * @param enumClazz type of enum constant to construct
     * @param <T>       type of enum
     * @return a new builder
     */
    public static <T extends Enum<T>> EnumAppender<T> create(Class<T> enumClazz, int ordinal0, Class<?> clazz0, int ordinal1, Class<?> clazz1, int ordinal2, Class<?> clazz2, int ordinal3, Class<?> clazz3, int ordinal4, Class<?> clazz4) {
        ImmutableList.Builder<FieldAccess> builder = ImmutableList.builder();
        builder.add(new FieldAccess(ordinal0, clazz0));
        builder.add(new FieldAccess(ordinal1, clazz1));
        builder.add(new FieldAccess(ordinal2, clazz2));
        builder.add(new FieldAccess(ordinal3, clazz3));
        builder.add(new FieldAccess(ordinal4, clazz4));
        return new EnumAppender<>(enumClazz, builder.build());
    }

    /**
     * allows for defining an ordinal where the field of <code>clazz_</code> is to be found
     * must be used when multiple fields have the same type
     *
     * @param enumClazz type of enum constant to construct
     * @param <T>       type of enum
     * @return a new builder
     */
    public static <T extends Enum<T>> EnumAppender<T> create(Class<T> enumClazz, int ordinal0, Class<?> clazz0, int ordinal1, Class<?> clazz1, int ordinal2, Class<?> clazz2, int ordinal3, Class<?> clazz3, int ordinal4, Class<?> clazz4, int ordinal5, Class<?> clazz5) {
        ImmutableList.Builder<FieldAccess> builder = ImmutableList.builder();
        builder.add(new FieldAccess(ordinal0, clazz0));
        builder.add(new FieldAccess(ordinal1, clazz1));
        builder.add(new FieldAccess(ordinal2, clazz2));
        builder.add(new FieldAccess(ordinal3, clazz3));
        builder.add(new FieldAccess(ordinal4, clazz4));
        builder.add(new FieldAccess(ordinal5, clazz5));
        return new EnumAppender<>(enumClazz, builder.build());
    }

    /**
     * allows for defining an ordinal where the field of <code>clazz_</code> is to be found
     * must be used when multiple fields have the same type
     *
     * @param enumClazz type of enum constant to construct
     * @param <T>       type of enum
     * @return a new builder
     */
    public static <T extends Enum<T>> EnumAppender<T> create(Class<T> enumClazz, int ordinal0, Class<?> clazz0, int ordinal1, Class<?> clazz1, int ordinal2, Class<?> clazz2, int ordinal3, Class<?> clazz3, int ordinal4, Class<?> clazz4, int ordinal5, Class<?> clazz5, int ordinal6, Class<?> clazz6) {
        ImmutableList.Builder<FieldAccess> builder = ImmutableList.builder();
        builder.add(new FieldAccess(ordinal0, clazz0));
        builder.add(new FieldAccess(ordinal1, clazz1));
        builder.add(new FieldAccess(ordinal2, clazz2));
        builder.add(new FieldAccess(ordinal3, clazz3));
        builder.add(new FieldAccess(ordinal4, clazz4));
        builder.add(new FieldAccess(ordinal5, clazz5));
        builder.add(new FieldAccess(ordinal6, clazz6));
        return new EnumAppender<>(enumClazz, builder.build());
    }

    /**
     * allows for defining an ordinal where the field of <code>clazz_</code> is to be found
     * must be used when multiple fields have the same type
     *
     * @param enumClazz type of enum constant to construct
     * @param <T>       type of enum
     * @return a new builder
     */
    public static <T extends Enum<T>> EnumAppender<T> create(Class<T> enumClazz, int ordinal0, Class<?> clazz0, int ordinal1, Class<?> clazz1, int ordinal2, Class<?> clazz2, int ordinal3, Class<?> clazz3, int ordinal4, Class<?> clazz4, int ordinal5, Class<?> clazz5, int ordinal6, Class<?> clazz6, int ordinal7, Class<?> clazz7, int ordinal8, Class<?> clazz8, int ordinal9, Class<?> clazz9) {
        ImmutableList.Builder<FieldAccess> builder = ImmutableList.builder();
        builder.add(new FieldAccess(ordinal0, clazz0));
        builder.add(new FieldAccess(ordinal1, clazz1));
        builder.add(new FieldAccess(ordinal2, clazz2));
        builder.add(new FieldAccess(ordinal3, clazz3));
        builder.add(new FieldAccess(ordinal4, clazz4));
        builder.add(new FieldAccess(ordinal5, clazz5));
        builder.add(new FieldAccess(ordinal6, clazz6));
        builder.add(new FieldAccess(ordinal7, clazz7));
        builder.add(new FieldAccess(ordinal8, clazz8));
        builder.add(new FieldAccess(ordinal9, clazz9));
        return new EnumAppender<>(enumClazz, builder.build());
    }

    /**
     * adds a new enum constant,
     * the constant is not actually created/added until {@link #applyTo} is called
     *
     * @param enumConstantName name of the new constant, it's a good practice to have your mod id somewhere in there to be able to find the origin of this value later
     * @param args             arguments required for constructing a new enum value
     * @return this builder instance
     */
    public EnumAppender<T> addEnumConstant(String enumConstantName, Object... args) {
        this.additions.put(enumConstantName, () -> this.add(enumConstantName, args));
        return this;
    }

    /**
     * actually creates and adds the new enum constant
     *
     * @param enumConstantName name of the new constant
     * @param args             arguments required for constructing a new enum value
     */
    private void add(String enumConstantName, Object... args) {
        // do this first so possible exceptions are raised before new enum constant is created
        Class<?>[] objectTypes = this.getObjectTypes(args);
        T enumConstant;
        try {
            enumConstant = UnsafeExtensibleEnum.invokeEnumConstructor(this.enumClazz, this.enumConcreteClazz, enumConstantName);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < objectTypes.length; i++) {
            Class<?> clazz = objectTypes[i];
            Object arg = args[i];
            Field field = this.fields.get(i);
            if (clazz == int.class) {
                UnsafeExtensibleEnum.setIntField(field, enumConstant, (int) arg);
            } else if (clazz == boolean.class) {
                UnsafeExtensibleEnum.setBooleanField(field, enumConstant, (boolean) arg);
            } else if (clazz == byte.class) {
                UnsafeExtensibleEnum.setByteField(field, enumConstant, (byte) arg);
            } else if (clazz == short.class) {
                UnsafeExtensibleEnum.setShortField(field, enumConstant, (short) arg);
            } else if (clazz == char.class) {
                UnsafeExtensibleEnum.setCharField(field, enumConstant, (char) arg);
            } else if (clazz == long.class) {
                UnsafeExtensibleEnum.setLongField(field, enumConstant, (long) arg);
            } else if (clazz == float.class) {
                UnsafeExtensibleEnum.setFloatField(field, enumConstant, (float) arg);
            } else if (clazz == double.class) {
                UnsafeExtensibleEnum.setDoubleField(field, enumConstant, (double) arg);
            } else {
                UnsafeExtensibleEnum.setObjectField(field, enumConstant, arg);
            }
        }
    }

    /**
     * finalizes this builder, by constructing and adding the new enum values.
     * also checks all additions were successful, and can update switch statements in other classes using this enum.
     * may only be called once, the builder is cleared afterward
     *
     * @param switchUsers classes containing switch statements that need updating
     */
    public void applyTo(Class<?>... switchUsers) {
        if (this.additions.isEmpty()) throw new IllegalStateException("Invalid builder, no additions have been made!");
        this.additions.values().forEach(Runnable::run);
        // test that everything worked
        this.additions.keySet().forEach(s -> {
            if (Stream.of(this.enumClazz.getEnumConstants()).map(Enum::name).noneMatch(name -> name.equals(s))) {
                throw new IllegalStateException("Failed to add enum value %s".formatted(s));
            }
        });
        try {
            UnsafeExtensibleEnum.updateRelatedSwitchStatements(this.enumClazz, switchUsers);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        // we're done with this builder
        this.additions.clear();
    }

    /**
     * check input <code>args</code> so that we have the correct amount, and types match
     * also handles boxed and unboxed classes of primitive types
     *
     * @param args input instances for enum construction
     * @return final types for enum constant construction
     */
    private Class<?>[] getObjectTypes(Object[] args) {
        if (args.length != this.fields.size())
            throw new IllegalArgumentException("Provided constructor args do not match enum description! Size %s does not match %s".formatted(args.length, this.fields.size()));
        Class<?>[] argClasses = new Class[args.length];
        $1:
        for (int i = 0; i < args.length; i++) {
            Class<?> fClazz = this.fields.get(i).getType();
            if (args[i] == null) {
                argClasses[i] = fClazz;
            } else {
                Class<?> oClazz = args[i].getClass();
                if (oClazz == fClazz || fClazz.isAssignableFrom(oClazz)) {
                    argClasses[i] = fClazz;
                } else {
                    for (Map.Entry<Class<?>, Class<?>> e : PRIMITIVE_TYPES.entrySet()) {
                        Optional<Class<?>> optional = findCommonType(oClazz, fClazz, e.getKey(), e.getValue());
                        if (optional.isPresent()) {
                            argClasses[i] = optional.get();
                            continue $1;
                        }
                    }
                    throw new IllegalArgumentException("Class type mismatch between %s and %s".formatted(oClazz, fClazz));
                }
            }
        }
        return argClasses;
    }

    public static <T extends Enum<T>> EnumAppender<T> createFromSpec(Class<T> enumClazz, Object[] spec) {
        if (spec == null || spec.length == 0) {
            return new EnumAppender<>(enumClazz);
        }
        if (spec.length % 2 != 0) {
            throw new IllegalArgumentException("Field spec must contain alternating (Integer ordinal, Class<?> type) pairs");
        }
        ImmutableList.Builder<FieldAccess> builder = ImmutableList.builder();
        for (int i = 0; i < spec.length; i += 2) {
            builder.add(new FieldAccess((Integer) spec[i], (Class<?>) spec[i + 1]));
        }
        return new EnumAppender<>(enumClazz, builder.build());
    }

    /**
     * record for finding fields in target class that need initializing
     *
     * @param ordinal ordinal of this type
     * @param clazz   the type
     */
    private record FieldAccess(int ordinal, Class<?> clazz) {

    }
}