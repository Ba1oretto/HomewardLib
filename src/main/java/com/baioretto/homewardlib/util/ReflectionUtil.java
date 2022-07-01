package com.baioretto.homewardlib.util;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static java.util.Objects.requireNonNull;

@SuppressWarnings({"unused", "JavadocDeclaration", "UnusedReturnValue"})
public class ReflectionUtil extends BaseUtil {
    /**
     * @param clazz
     * @param parameterTypes
     * @param <T>
     */
    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... parameterTypes) {
        try {
            return requireNonNull(clazz).getDeclaredConstructor(parameterTypes);
        } catch (NoSuchMethodException ignore) {
            try {
                return requireNonNull(clazz).getDeclaredConstructor();
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * @param constructor
     * @param parameters
     */
    public static <T> T newInstance(Constructor<T> constructor, Object... parameters) {
        try {
            requireNonNull(constructor).setAccessible(true);
            return constructor.getParameterCount() > 0 ? constructor.newInstance(parameters) : constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param clazz
     */
    public static <T> T newInstance(Class<T> clazz) {
        return newInstance(getConstructor(clazz));
    }

    /**
     * @param clazz
     * @param parameterType
     * @param parameter
     */
    public static <T> T newInstance(Class<T> clazz, @NotNull Class<?> parameterType, Object... parameter) {
        return newInstance(getConstructor(clazz, parameterType), parameter);
    }
}
