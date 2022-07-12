package com.baioretto.baiolib.exception;

import lombok.NoArgsConstructor;

/**
 * The {@code NotInstantiableException} class is an exception class.
 * 
 * @since 1.0.0
 * @author baioretto
 */
@NoArgsConstructor
public class NotInstantiableException extends RuntimeException {
    public NotInstantiableException(String messages) {
        super(messages);
    }
}
