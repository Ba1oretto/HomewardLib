package com.baioretto.homewardlib.exception;

@SuppressWarnings("unused")
public class NotInstantiableException extends RuntimeException {
    public NotInstantiableException() {

    }

    public NotInstantiableException(String messages) {
        super(messages);
    }
}
