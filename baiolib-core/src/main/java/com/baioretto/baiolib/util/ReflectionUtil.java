package com.baioretto.baiolib.util;

import com.baioretto.baiolib.exception.BaioLibInternalException;
import com.google.common.collect.ImmutableSet;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The {@code ReflectionUtil} class helps you get something by reflection quickly
 *
 * @author baioretto
 * @since 1.0.0
 */
@UtilityClass
public class ReflectionUtil {
    /**
     * get {@link  Class} associated with coordinate
     *
     * @param coordinate class reference
     * @return the matching class
     * @throws BaioLibInternalException if class not found
     */
    public static Class<?> getClass(String coordinate) {
        try {
            return Class.forName(coordinate);
        } catch (ClassNotFoundException e) {
            throw new BaioLibInternalException("An error occur when getting class: " + coordinate + ", class not found.");
        }
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
        return ReflectionUtil.getConstructor(ReflectionUtil.getClass(clazz), parameterTypes);
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
        try {
            return clazz.getDeclaredConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new BaioLibInternalException(e);
        }
    }

    /**
     * get an {@code instance} of a given class from no-argument constructor.
     *
     * @param clazz the class required for instantiation
     * @return an instance of the class
     * @throws BaioLibInternalException if exceptions occur when getting an instance
     */
    public <T> T newInstance(Class<T> clazz) {
        return newInstance(getConstructor(clazz));
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
        constructor.setAccessible(true);
        try {
            return constructor.newInstance(initArgs);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new BaioLibInternalException(e);
        }
    }

    /**
     * get {@link Field} by class name and field name
     *
     * @param clazz the class where field exists
     * @param name  the field name
     * @return the matching field
     */
    public Field getField(String clazz, String name) {
        return ReflectionUtil.getField(ReflectionUtil.getClass(clazz), name);
    }

    /**
     * get {@link Field} by class and field name
     *
     * @param clazz the class where field exists
     * @param name  the field name
     * @return the matching field
     */
    public <T> Field getField(Class<T> clazz, String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            throw new BaioLibInternalException(e);
        }
    }

    /**
     * get {@code instance} of the field
     *
     * @param field the field to be got
     * @param obj   the class instance
     * @return field instance
     */
    public Object get(Field field, Object obj) {
        field.setAccessible(true);
        try {
            return field.get(obj);
        } catch (IllegalAccessException e) {
            throw new BaioLibInternalException(e);
        }
    }

    /**
     * set a {@code field} value
     *
     * @param field field to be set
     * @param obj   class instance
     * @param value value to be set
     */
    public static void set(Field field, Object obj, Object value) {
        field.setAccessible(true);
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            throw new BaioLibInternalException(e);
        }
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
        return ReflectionUtil.getMethod(ReflectionUtil.getClass(clazz), name, parameterTypes);
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
        try {
            return clazz.getDeclaredMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new BaioLibInternalException(e);
        }
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
        method.setAccessible(true);
        try {
            return method.invoke(obj, args);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new BaioLibInternalException(e);
        }
    }

    /**
     * Get a {@link Reflections} from package with custom classloader.
     *
     * @param pkg         package
     * @param classLoader classloader
     * @return reflections instance
     */
    public Reflections getReflections(String pkg, ClassLoader... classLoader) {
        return new Reflections(new ConfigurationBuilder().addClassLoaders(classLoader).addUrls(ClasspathHelper.forPackage(pkg, classLoader)).filterInputsBy(new FilterBuilder().includePackage(pkg)));
    }

    public <T> ImmutableSet<T> getSubtypeOf(String packageName, Class<T> subtype, ClassLoader... classLoader) {
        Reflections reflections = ReflectionUtil.getReflections(packageName, classLoader);
        ConcurrentLinkedQueue<T> queue = new ConcurrentLinkedQueue<>();
        reflections.getSubTypesOf(subtype).forEach(impl -> queue.add(newInstance(impl)));
        return ImmutableSet.copyOf(queue);
    }
}