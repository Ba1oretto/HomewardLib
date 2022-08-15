package com.baioretto.baiolib.api;

import com.baioretto.baiolib.exception.NotActuallyBaioLibException;

/**
 * get class instance from api package
 *
 * @since 1.6.0
 * @author baioretto
 */
public class CachedVersionWrapper {
    /**
     * get instance from specific class
     *
     * @param clazz the class required for instantiation
     * @return instance of this {@code clazz}
     * @param <T> class type
     * @since 1.6.0
     */
    public static <T extends AbstractUtil<?>> T of(Class<T> clazz) {
        throw new NotActuallyBaioLibException();
    }
}
