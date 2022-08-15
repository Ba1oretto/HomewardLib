package com.baioretto.baiolib.exception;

public class NotActuallyBaioLibException extends UnsupportedOperationException {
    public NotActuallyBaioLibException() {
        super("Warn: BaioLib does not seem to be installed. This is just the API reference, not the actual implementation.");
    }
}
