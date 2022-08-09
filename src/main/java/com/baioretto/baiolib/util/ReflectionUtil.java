package com.baioretto.baiolib.util;

import com.baioretto.baiolib.api.Pool;
import com.google.common.collect.ImmutableSet;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.util.Objects.requireNonNull;

/**
 * The {@code ReflectionUtil} class helps you get something quickly
 *
 * @author baioretto
 * @since 1.0.0
 */
@UtilityClass
public class ReflectionUtil {

    /**
     * Get an {@code instance} of a given class from no-argument constructor.
     *
     * @param clazz The given class
     * @return An instance of the class in which this constructor resides
     * @throws RuntimeException If exceptions occur when getting an instance
     * @since 1.0.0
     */
    public <T> T newInstance(Class<T> clazz) {
        return newInstance(getConstructor(clazz));
    }

    /**
     * Get an {@code instance} of a given class from one-argument constructor.
     *
     * @param clazz         The given class
     * @param parameterType The parameter type
     * @param parameter     The parameter
     * @return An instance of the class in which this constructor resides
     * @throws RuntimeException If exceptions occur when getting an instance
     * @since 1.0.0
     */
    public <T> @NotNull T newInstance(@NotNull Class<T> clazz, @NotNull Class<?> parameterType, Object parameter) {
        return newInstance(getConstructor(clazz, parameterType), parameter);
    }

    /**
     * Get an {@code instance} from given constructor.
     *
     * @param constructor The given constructor
     * @param parameters  Constructor parameters
     * @return An instance of the class in which this constructor resides
     * @throws RuntimeException If exceptions occur when instancing class
     * @since 1.0.0
     */
    public <T> @NotNull T newInstance(@NotNull Constructor<T> constructor, @NotNull Object... parameters) {
        try {
            requireNonNull(constructor).setAccessible(true);
            return constructor.getParameterCount() > 0 ? constructor.newInstance(parameters) : constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get a {@code constructor} from given class.
     *
     * @param clazz          The given class
     * @param parameterTypes Parameter types
     * @return A constructor matching the given arguments
     * @throws RuntimeException If no constructor matches given parameter types
     * @since 1.0.0
     */
    public <T> @NotNull Constructor<T> getConstructor(@NotNull Class<T> clazz, @NotNull Class<?>... parameterTypes) {
        try {
            return requireNonNull(clazz).getDeclaredConstructor(requireNonNull(parameterTypes));
        } catch (NoSuchMethodException ignore) {
            try {
                return clazz.getDeclaredConstructor();
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Get a {@code field} from the given class.
     *
     * @param from The class of this field
     * @param of   The field class
     * @param name The field name
     * @return The field instance
     * @throws RuntimeException If an exception caught when instancing class
     * @since 1.1.0
     */
    @SuppressWarnings({"unchecked", "ConstantConditions"})
    public <T> T getSingletonField(Class<?> from, Class<T> of, String name) {
        Field classField;
        try {
            classField = from.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        classField.setAccessible(true);

        T field;
        try {
            field = (T) classField.get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        if (field == null) {
            if (Pool.ABSTRACT_API_CLASSES().get(name) != null) {
                synchronized (Pool.ABSTRACT_API_CLASSES().get(name)) {
                    if (field == null) {
                        field = ReflectionUtil.newInstance(of);
                    }
                }
            } else field = ReflectionUtil.newInstance(of);

            try {
                classField.set(null, field);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return field;
    }

    public <T> ImmutableSet<T> getSubtypeOf(String packageName, Class<T> subtype) {
        Reflections reflections = ServerUtil.getReflections(packageName);
        ConcurrentLinkedQueue<T> queue = new ConcurrentLinkedQueue<>();
        reflections.getSubTypesOf(subtype).forEach(impl -> queue.add(newInstance(impl)));
        return ImmutableSet.copyOf(queue);
    }
}
