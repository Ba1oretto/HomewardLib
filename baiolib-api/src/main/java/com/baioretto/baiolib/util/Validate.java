package com.baioretto.baiolib.util;

import com.baioretto.baiolib.exception.NotActuallyBaioLibException;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;

/**
 * The {@code Validate} class provides additional verification methods.
 *
 * @author baioretto
 * @since 1.0.0
 */
public class Validate {
    /**
     * Check if the specific class has this annotation.
     *
     * @param clazz      The class you want to check
     * @param annotation The annotation class should have
     * @return true if The class has that annotation
     * @throws NullPointerException if given parameter is null
     */
    public boolean hasAnnotation(@NotNull Class<?> clazz, @NotNull Class<? extends Annotation> annotation) {
        throw new NotActuallyBaioLibException();
    }

    /**
     * Check if this array is empty.
     *
     * @param array The array you want to check
     * @return true if the length of this array is 0
     * @throws NullPointerException if array is null
     */
    public boolean isEmpty(@NotNull Object[] array) {
        throw new NotActuallyBaioLibException();
    }

    /**
     * Check if the json element is null.
     *
     * @param element Target json element
     * @return true if the element not null
     */
    public boolean notNull(JsonElement element) {
        throw new NotActuallyBaioLibException();
    }

    /**
     * Check the given {@code String} is matches true or false.
     *
     * @param string The string you want to check
     * @return true if the given string is matches
     */
    public boolean matchesBoolean(String string) {
        throw new NotActuallyBaioLibException();
    }

    /**
     * Get a {@code boolean} value from given {@link String}.
     *
     * @param string The string you want to convert
     * @return boolean
     * @throws IllegalArgumentException if the given {@link String} cannot convert to boolean.
     */
    public boolean getBoolean(String string) {
        throw new NotActuallyBaioLibException();
    }
}
