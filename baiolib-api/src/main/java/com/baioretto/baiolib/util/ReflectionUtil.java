package com.baioretto.baiolib.util;

import com.baioretto.baiolib.exception.BaioLibInternalException;
import com.baioretto.baiolib.exception.NotActuallyBaioLibException;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * The {@code ReflectionUtil} class helps you get something by reflection quickly
 *
 * @author baioretto
 * @since 1.0.0
 */
public class ReflectionUtil {
    /**
     * get {@link  Class} associated with coordinate
     *
     * @param coordinate class reference
     * @return the matching class
     * @throws BaioLibInternalException if class not found
     */
    public static Class<?> getClass(String coordinate) {
        throw new NotActuallyBaioLibException();
    }

    /**
     * get a {@link Constructor} from given class
     *
     * @param clazz          the class where the constructor exists
     * @param parameterTypes type of parameters required by the constructor
     * @return the matching constructor
     * @throws BaioLibInternalException if no constructor matches given parameters type
     */
    public Constructor<?> getConstructor(String clazz, Class<?>... parameterTypes) {
        throw new NotActuallyBaioLibException();
    }

    /**
     * get a {@link Constructor} from given class
     *
     * @param clazz          the class where the constructor exists
     * @param parameterTypes type of parameters required by the constructor
     * @return the matching constructor
     * @throws BaioLibInternalException if no constructor matches given parameters type
     */
    public <T> Constructor<T> getConstructor(@NotNull Class<T> clazz, @NotNull Class<?>... parameterTypes) {
        throw new NotActuallyBaioLibException();
    }

    /**
     * get an {@code instance} of a given class from no-argument constructor.
     *
     * @param clazz the class required for instantiation
     * @return an instance of the class
     * @throws BaioLibInternalException if exceptions occur when getting an instance
     */
    public <T> T newInstance(Class<T> clazz) {
        throw new NotActuallyBaioLibException();
    }

    /**
     * get an {@code instance} from given constructor.
     *
     * @param constructor the constructor required for instantiation
     * @param initArgs    constructor parameters
     * @return an instance of the class where the constructor exists
     * @throws BaioLibInternalException If exceptions occur when instancing class
     */
    public <T> T newInstance(Constructor<T> constructor, Object... initArgs) {
        throw new NotActuallyBaioLibException();
    }

    /**
     * get {@link Field} by class name and field name
     *
     * @param clazz the class where field exists
     * @param name  the field name
     * @return the matching field
     */
    public Field getField(String clazz, String name) {
        throw new NotActuallyBaioLibException();
    }

    /**
     * get {@link Field} by class and field name
     *
     * @param clazz the class where field exists
     * @param name  the field name
     * @return the matching field
     */
    public <T> Field getField(Class<T> clazz, String name) {
        throw new NotActuallyBaioLibException();
    }

    /**
     * get {@code instance} of the field
     *
     * @param field the field to be get
     * @param obj   the class instance
     * @return field instance
     */
    public Object get(Field field, Object obj) {
        throw new NotActuallyBaioLibException();
    }

    /**
     * set a {@code field} value
     *
     * @param field field to be set
     * @param obj   class instance
     * @param value value to be set
     */
    public static void set(Field field, Object obj, Object value) {
        throw new NotActuallyBaioLibException();
    }

    /**
     * get a {@link Method}
     *
     * @param clazz          class name where the method exists
     * @param name           method name
     * @param parameterTypes parameter types required for method
     * @return the matching method
     */
    public static Method getMethod(String clazz, String name, Class<?>... parameterTypes) {
        throw new NotActuallyBaioLibException();
    }

    /**
     * get a {@link Method}
     *
     * @param clazz          class where the method exists
     * @param name           method name
     * @param parameterTypes parameter types required for method
     * @return the matching method
     */
    public static Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
        throw new NotActuallyBaioLibException();
    }

    /**
     * invoke a {@link Method}
     *
     * @param method method to be invoke
     * @param obj    class instance where method exists
     * @param args   arguments required for method
     * @return result of this invocation
     */
    public static Object invoke(Method method, Object obj, Object... args) {
        throw new NotActuallyBaioLibException();
    }

    public <T> ImmutableSet<T> getSubtypeOf(String packageName, Class<T> subtype, ClassLoader... classLoader) {
        throw new NotActuallyBaioLibException();
    }
}