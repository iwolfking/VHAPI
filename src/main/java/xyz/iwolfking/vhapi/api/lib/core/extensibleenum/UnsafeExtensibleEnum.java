package xyz.iwolfking.vhapi.api.lib.core.extensibleenum;

import org.objectweb.asm.Opcodes;
import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

/**
 * a helper class for extending enums, this uses {@link Unsafe} as reflection is no longer a viable option for extending enums in Java 12+
 * only use for enums which are not used in switch statements since they will break otherwise (mainly the newer fancy ones where the default case can be omitted)
 */
public final class UnsafeExtensibleEnum {
    /**
     * the unsafe referent required for most operations
     */
    private static final Unsafe UNSAFE;

    static {
        try {
            Constructor<?> constructor = Unsafe.class.getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            UNSAFE = (Unsafe) constructor.newInstance();
        }
        catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    private UnsafeExtensibleEnum() {

    }

    /**
     * create a new enum constant, set <code>name</code> and <code>ordinal</code> fields accordingly (since we invoke the constant using unsafe, no fields will be initialized)
     * and also add it to the enum values array, clearing enum cache (used for switch statements) afterwards
     *
     * @param enumMainClass the enum class to add a constant to, also just for instantiating new constant
     * @param internalName name of the new enum value
     * @param <T> enum type
     * @return the new enum constant
     *
     * @throws Throwable something went wrong during unsafe operations oh no
     */
    public static <T extends Enum<T>> T invokeEnumConstructor(Class<T> enumMainClass, String internalName) throws Throwable {
        return invokeEnumConstructor(enumMainClass, enumMainClass, internalName);
    }

    /**
     * create a new enum constant, set <code>name</code> and <code>ordinal</code> fields accordingly (since we invoke the constant using unsafe, no fields will be initialized)
     *
     * @param enumMainClass the enum class to add a constant to, also just for instantiating new constant
     * @param internalName name of the new enum value
     * @param internalId ordinal id, controls if the new enum constant is properly added to enum values, set this as -1 to do so, specify a different value to skip it
     * @param <T> enum type
     * @return the new enum constant
     *
     * @throws Throwable something went wrong during unsafe operations oh no
     */
    public static <T extends Enum<T>> T invokeEnumConstructor(Class<T> enumMainClass, String internalName, int internalId) throws Throwable {
        return invokeEnumConstructor(enumMainClass, enumMainClass, internalName, internalId);
    }

    /**
     * create a new enum constant, set <code>name</code> and <code>ordinal</code> fields accordingly (since we invoke the constant using unsafe, no fields will be initialized)
     * and also add it to the enum values array, clearing enum cache (used for switch statements) afterwards
     *
     * @param enumMainClass the enum class to add a constant to
     * @param enumConcreteClass the enum class to create the constant from, should usually be the same as <code>enumMainClass</code>, but in case of an abstract enum supply the class of any of its constants
     * @param internalName name of the new enum value
     * @param <T> enum type
     * @return the new enum constant
     *
     * @throws Throwable something went wrong during unsafe operations oh no
     */
    public static <T extends Enum<T>> T invokeEnumConstructor(Class<T> enumMainClass, Class<? extends T> enumConcreteClass, String internalName) throws Throwable {
        return invokeEnumConstructor(enumMainClass, enumConcreteClass, internalName, -1);
    }

    /**
     * create a new enum constant, set <code>name</code> and <code>ordinal</code> fields accordingly (since we invoke the constant using unsafe, no fields will be initialized)
     * and also add it to the enum values array, clearing enum cache (used for switch statements) afterwards
     *
     * @param enumMainClass the enum class to add a constant to
     * @param enumConcreteClass the enum class to create the constant from, should usually be the same as <code>enumMainClass</code>, but in case of an abstract enum supply the class of any of its constants
     * @param internalName name of the new enum value
     * @param internalId ordinal id, controls if the new enum constant is properly added to enum values, set this as -1 to do so, specify a different value to skip it
     * @param <T> enum type
     * @return the new enum constant
     *
     * @throws Throwable something went wrong during unsafe operations oh no
     */
    public static <T extends Enum<T>> T invokeEnumConstructor(Class<T> enumMainClass, Class<? extends T> enumConcreteClass, String internalName, int internalId) throws Throwable {
        T enumValue = enumMainClass.cast(UNSAFE.allocateInstance(enumConcreteClass));
        boolean addToEnumValues = internalId == -1;
        if (addToEnumValues) {
            internalId = addToEnumValues(enumMainClass, enumValue, internalName);
        }
        initEnumFields(enumValue, internalName, internalId);
        if (addToEnumValues) {
            cleanEnumCache(enumMainClass);
        }
        return enumValue;
    }

    /**
     * add new enum constant to enum values array
     *
     * @param enumMainClass enum class containing internal <code>$VALUES</code> field for adding our new constant
     * @param enumValue enum constant to add
     * @param internalName  name required for verifying no enum constant of the same name already exists
     * @param <T> enum type
     * @return ordinal position <code>enumValue</code> was added to <code>$VALUES</code> at
     *
     * @throws ReflectiveOperationException thrown by reflective operations
     */
    @SuppressWarnings("unchecked")
    private static <T extends Enum<T>> int addToEnumValues(Class<T> enumMainClass, T enumValue, String internalName) throws ReflectiveOperationException {
        // don't test for final and private, some mixin accessor / access wideners might make the field mutable, removing the flag
        final int valuesFieldModifiers = Opcodes.ACC_STATIC | Opcodes.ACC_SYNTHETIC;
        for (Field field : enumMainClass.getDeclaredFields()) {
            // we don't go looking for $VALUES field by name as Proguard will probably mess with that name
            if (field.getType().isArray() && (field.getModifiers() & valuesFieldModifiers) == valuesFieldModifiers) {
                field.setAccessible(true);
                // does not work in Java 12+ due to private field members of Field.class no longer being accessible via reflection
//                Field modifiers = field.getClass().getDeclaredField("modifiers");
//                modifiers.setAccessible(true);
//                modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                T[] values = (T[]) field.get(null);
                for (T value : values) {
                    if (value.name().equals(internalName)) {
                        throw new IllegalArgumentException(String.format("%s already exists in enum class %s", internalName, enumMainClass.getName()));
                    }
                }
                T[] modifiedValues = Arrays.copyOf(values, values.length + 1);
                modifiedValues[modifiedValues.length - 1] = enumValue;
                // use more unsafe hacks here since modifying final fields no longer works via reflection
//                field.set(null, modifiedValues);
                setStaticObjectField(field, modifiedValues);
                return values.length;
            }
        }
        throw new IllegalAccessException("Could not find enum values field");
    }

    /**
     * since we created our enum constant using unsafe, we need to set all fields manually
     * this only sets default enum fields, every field specific to that particular enum needs to be manually set by the user
     *
     * @param enumValue enum value to set fields for
     * @param internalName value name
     * @param internalId ordinal id, this comes from {@link #addToEnumValues} depending on where in the internal <code>$VALUES</code> array our new constant has been added
     * @throws ReflectiveOperationException thrown by reflective operations duh
     */
    private static void initEnumFields(Enum<?> enumValue, String internalName, int internalId) throws ReflectiveOperationException {
        Field ordinalField = Enum.class.getDeclaredField("ordinal");
//        ordinalField.setAccessible(true);
//        ordinalField.setInt(enumValue, internalId);
        setIntField(ordinalField, enumValue, internalId);
        Field nameField = Enum.class.getDeclaredField("name");
//        nameField.setAccessible(true);
//        nameField.set(enumValue, internalName);
        setObjectField(nameField, enumValue, internalName);
    }

    /**
     * set a value to a field using unsafe (since reflection will not work with final fields)
     *
     * @param field field to set
     * @param instance instance field belongs to
     * @param newValue value to set
     */
    public static void setObjectField(Field field, Object instance, Object newValue) {
        final long objectFieldOffset = UNSAFE.objectFieldOffset(field);
        UNSAFE.putObject(instance, objectFieldOffset, newValue);
    }

    /**
     * set a value to a field using unsafe (since reflection will not work with final fields)
     *
     * @param field field to set
     * @param instance instance field belongs to
     * @param newValue value to set
     */
    public static void setIntField(Field field, Object instance, int newValue) {
        final long objectFieldOffset = UNSAFE.objectFieldOffset(field);
        UNSAFE.putInt(instance, objectFieldOffset, newValue);
    }

    /**
     * set a value to a field using unsafe (since reflection will not work with final fields)
     *
     * @param field field to set
     * @param instance instance field belongs to
     * @param newValue value to set
     */
    public static void setBooleanField(Field field, Object instance, boolean newValue) {
        final long objectFieldOffset = UNSAFE.objectFieldOffset(field);
        UNSAFE.putBoolean(instance, objectFieldOffset, newValue);
    }

    /**
     * set a value to a field using unsafe (since reflection will not work with final fields)
     *
     * @param field field to set
     * @param instance instance field belongs to
     * @param newValue value to set
     */
    public static void setByteField(Field field, Object instance, byte newValue) {
        final long objectFieldOffset = UNSAFE.objectFieldOffset(field);
        UNSAFE.putByte(instance, objectFieldOffset, newValue);
    }

    /**
     * set a value to a field using unsafe (since reflection will not work with final fields)
     *
     * @param field field to set
     * @param instance instance field belongs to
     * @param newValue value to set
     */
    public static void setShortField(Field field, Object instance, short newValue) {
        final long objectFieldOffset = UNSAFE.objectFieldOffset(field);
        UNSAFE.putShort(instance, objectFieldOffset, newValue);
    }

    /**
     * set a value to a field using unsafe (since reflection will not work with final fields)
     *
     * @param field field to set
     * @param instance instance field belongs to
     * @param newValue value to set
     */
    public static void setCharField(Field field, Object instance, char newValue) {
        final long objectFieldOffset = UNSAFE.objectFieldOffset(field);
        UNSAFE.putChar(instance, objectFieldOffset, newValue);
    }

    /**
     * set a value to a field using unsafe (since reflection will not work with final fields)
     *
     * @param field field to set
     * @param instance instance field belongs to
     * @param newValue value to set
     */
    public static void setLongField(Field field, Object instance, long newValue) {
        final long objectFieldOffset = UNSAFE.objectFieldOffset(field);
        UNSAFE.putLong(instance, objectFieldOffset, newValue);
    }

    /**
     * set a value to a field using unsafe (since reflection will not work with final fields)
     *
     * @param field field to set
     * @param instance instance field belongs to
     * @param newValue value to set
     */
    public static void setFloatField(Field field, Object instance, float newValue) {
        final long objectFieldOffset = UNSAFE.objectFieldOffset(field);
        UNSAFE.putFloat(instance, objectFieldOffset, newValue);
    }

    /**
     * set a value to a field using unsafe (since reflection will not work with final fields)
     *
     * @param field field to set
     * @param instance instance field belongs to
     * @param newValue value to set
     */
    public static void setDoubleField(Field field, Object instance, double newValue) {
        final long objectFieldOffset = UNSAFE.objectFieldOffset(field);
        UNSAFE.putDouble(instance, objectFieldOffset, newValue);
    }

    /**
     * set a value to a static field object using unsafe (since reflection will not work with final fields)
     *
     * @param field static field to set
     * @param newValue object value to set
     */
    public static void setStaticObjectField(Field field, Object newValue) {
        final Object staticFieldBase = UNSAFE.staticFieldBase(field);
        final long staticFieldOffset = UNSAFE.staticFieldOffset(field);
        UNSAFE.putObject(staticFieldBase, staticFieldOffset, newValue);
    }

    /**
     * from Forge, probably supposed to clear enum cache used for switch statements
     * not sure if this is needed, cache doesn't seem to be an issue (maybe with some compilers?)
     *
     * @param enumClass enum class to clear cache for
     */
    private static void cleanEnumCache(Class<? extends Enum<?>> enumClass) {
        findField(Class.class, "enumConstantDirectory").ifPresent(field -> {
            setObjectField(field, enumClass, null);
        });
        findField(Class.class, "enumConstants").ifPresent(field -> {
            setObjectField(field, enumClass, null);
        });
    }

    /**
     * get a field from class as optional to make sure we don't crash if any future versions change field names
     *
     * @param clazz class type
     * @param target field name
     * @return the field
     */
    private static Optional<Field> findField(Class<?> clazz, String target) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals(target)) {
                return Optional.of(field);
            }
        }
        return Optional.empty();
    }

    // all code below comes from here: https://www.javaspecialists.eu/archive/Issue272-Hacking-Enums-Revisited.html
    // we aren't really using that, so it's also somewhat untested (was also written for Java 12)

    /**
     * goes through all switch statements in <code>switchUsers</code> and updates those
     *
     * @param enumClass                         the enum class used in the switch statements we want to adjust
     * @param switchUsers                       classes those switch statements are found in
     * @throws ReflectiveOperationException     switches array couldn't be updated
     */
    public static void updateRelatedSwitchStatements(Class<? extends Enum<?>> enumClass, Class<?>[] switchUsers) throws ReflectiveOperationException {
        addSwitchCase(findRelatedSwitchFields(enumClass, switchUsers));
    }

    /**
     * find all switch statement classes and related values field from those
     *
     * @param enumClass         the enum class used in the switch statements we want to adjust
     * @param switchUsers       classes those switch statements are found in
     * @return                  fields containing the switch cases as an array
     */
    private static Collection<Field> findRelatedSwitchFields(Class<? extends Enum<?>> enumClass, Class<?>[] switchUsers) {
        Collection<Field> result = new ArrayList<>();
        for (Class<?> switchUser : switchUsers) {
            Class<?>[] clazzes = getAnonymousClasses(switchUser);
            for (Class<?> suspect : clazzes) {
                Field[] fields = suspect.getDeclaredFields();
                for (Field field : fields) {
                    if (field.getName().startsWith("$SwitchMap$" + enumClass.getName().replace(".", "$"))) {
                        field.setAccessible(true);
                        result.add(field);
                    }
                }
            }
        }
        return result;
    }

    /**
     * finds all anonymous classes in a class
     *
     * @param clazz     class to scan
     * @return          all anonymous classes in <code>clazz</code>
     */
    private static Class<?>[] getAnonymousClasses(Class<?> clazz) {
        Collection<Class<?>> classes = new ArrayList<>();
        for (int i = 1; ; i++) {
            try {
                classes.add(Class.forName(clazz.getName() + "$" + i, false, // do not initialize
                        Thread.currentThread().getContextClassLoader()));
            } catch (ClassNotFoundException e) {
                break;
            }
        }
        for (Class<?> inner : clazz.getDeclaredClasses()) {
            Collections.addAll(classes, getAnonymousClasses(inner));
        }
        for (Class<?> anon : new ArrayList<>(classes)) {
            Collections.addAll(classes, getAnonymousClasses(anon));
        }
        return classes.toArray(new Class<?>[0]);
    }

    /**
     * updates the values field for the switch statement with one more index for our new value
     *
     * @param switchFields                      the switch case arrays to modify
     * @throws ReflectiveOperationException     field couldn't be updated
     */
    private static void addSwitchCase(final Collection<Field> switchFields) throws ReflectiveOperationException {
        for (Field switchField : switchFields) {
            int[] switches = (int[]) switchField.get(null);
            switches = Arrays.copyOf(switches, switches.length + 1);
            setStaticObjectField(switchField, switches);
        }
    }
}