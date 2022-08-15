package com.baioretto.baiolib.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BaioLibInternalException extends RuntimeException {
    public BaioLibInternalException(String cause) {
        super(cause);
    }

    public BaioLibInternalException(Exception e) {
        super(e);
    }
}
