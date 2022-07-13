package com.baioretto.baiolib.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BaioLibInternalException extends RuntimeException {
    public BaioLibInternalException(String cause) {
        super(String.format("""
                This is BaioLib internal exception
                you can copy and send this message to my email: sunjiamu@outlook.com
                %s
                """, cause));
    }
}
