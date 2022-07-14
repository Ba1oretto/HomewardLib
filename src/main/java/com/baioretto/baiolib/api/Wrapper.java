package com.baioretto.baiolib.api;

import com.baioretto.baiolib.util.Validate;

import com.baioretto.baiolib.annotation.Instantiable;
import com.baioretto.baiolib.exception.NotInstantiableException;
import com.baioretto.baiolib.util.ReflectionUtil;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

/**
 * The {@code Wrapper} class provides a method to obtain a class instance object.
 * All {@code methods} have the same function, just the {@code parameters} passed are different.
 * <p>
 * For example:
 * <blockquote><pre>
 *     Wrapper.getInstance(IBlockUtil.class);
 * </pre></blockquote><p>
 * is equivalent to:
 * <blockquote><pre>
 *     Wrapper.getInstance(IBlockUtil.class, instance);
 * </pre></blockquote><p>
 * Here are some more examples of how wrapper can be used:
 * <blockquote><pre>
 *     Wrapper.getInstance(IBlockUtil.class, null);
 * </pre></blockquote>
 *
 * @author baioretto
 * @since 1.0.0
 */
@UtilityClass
@SuppressWarnings("unused")
public class Wrapper {
    /**
     * Return an {@code instance} of the given class.
     *
     * @param clazz The given class
     * @return An instance of generic type T
     * @throws NotInstantiableException If a class is not marked as instantiable
     * @since 1.0.0
     */
    public static <T> T getInstance(Class<T> clazz) {
        return getInstance(clazz, null);
    }

    /**
     * Return an {@code instance} of the given class.
     *
     * @param clazz    The given class
     * @param delegate The delegate object
     * @return An instance of generic type T
     * @throws NotInstantiableException If a class is not marked as instantiable
     * @since 1.0.0
     */
    public static <T> T getInstance(Class<T> clazz, Object delegate) {
        return getInstance(clazz, delegate, false);
    }

    /**
     * Return an {@code instance} of the given class.
     *
     * @param clazz       The given class
     * @param delegate    The delegate object
     * @param ignoreError Whether to ignore exceptions
     * @return An instance of generic type T
     * @throws NotInstantiableException If a class is not marked as instantiable
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    public static <T> T getInstance(Class<T> clazz, Object delegate, boolean ignoreError) {
        if (!Validate.hasAnnotation(clazz, Instantiable.class)) {
            if (!ignoreError)
                throw new NotInstantiableException("This class is not instantiable.");
            return null;
        }

        String implementationClassName = String.format("%s.version.%s", clazz.getPackageName(), version);
        try {
            Class<T> implementationClass = (Class<T>) Class.forName(implementationClassName);
            if (delegate == null) {
                return ReflectionUtil.newInstance(implementationClass);
            }
            return ReflectionUtil.newInstance(implementationClass, delegate.getClass(), delegate);
        } catch (ClassNotFoundException e) {
            if (!ignoreError)
                throw new RuntimeException(e);
            return null;
        }
    }

    /**
     * The version of Minecraft
     */
    private final static String version;

    static {
        String[] packageNameArray = Bukkit.getServer().getClass().getPackageName().split("\\.");
        version = packageNameArray[packageNameArray.length - 1];
    }
}
