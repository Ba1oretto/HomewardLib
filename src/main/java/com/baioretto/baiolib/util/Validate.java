package com.baioretto.baiolib.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * The {@code Validate} class provides additional verification methods.
 *
 * @author baioretto
 * @since 1.0.0
 */
@UtilityClass
public class Validate {
    /**
     * Check if the specific class has this annotation.
     *
     * @param clazz The class you want to check
     * @param annotation The annotation class should have
     * @return true if The class has that annotation
     * @throws NullPointerException if given parameter is null
     */
    public boolean hasAnnotation(@NotNull Class<?> clazz, @NotNull Class<? extends Annotation> annotation) {
        return Objects.nonNull(Objects.requireNonNull(clazz).getAnnotation(Objects.requireNonNull(annotation)));
    }

    /**
     * Check if this array is empty.
     *
     * @param array The array you want to check
     * @return true if the length of this array is 0
     * @throws NullPointerException if array is null
     */
    public boolean isEmpty(@NotNull Object[] array) {
        return Objects.requireNonNull(array).length == 0;
    }

    /**
     * Check if the json element is null.
     *
     * @param element Target json element
     * @return true if the element not null
     */
    public boolean notNull(JsonElement element) {
        return !(element == null || element instanceof JsonNull);
    }

    /**
     * Check the given {@code String} is matches true or false.
     *
     * @param string The string you want to check
     * @return true if the given string is matches
     */
    public boolean matchesBoolean(String string) {
        return string.matches("(?i)true|false");
    }
}
