package com.baioretto.baiolib.api;

import com.baioretto.baiolib.exception.NotActuallyBaioLibException;

/**
 * The {@code AbstractUtil} class provides a method named {@code impl},
 * which will returns an instance of generic type C.
 *
 * @param <C> Util class generic type
 * @author baioretto
 * @since 1.1.0
 */
public abstract class AbstractUtil<C> {
    /**
     * Get instance of generic type {@code C}
     *
     * @return instance of C
     */
    public C impl() {
        throw new NotActuallyBaioLibException();
    }
}
