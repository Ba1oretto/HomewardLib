package com.baioretto.baiolib.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BaioLibInternalException extends RuntimeException {
    private static final String template = """
                This is BaioLib internal exception
                you can copy and send this message to my email: sunjiamu@outlook.com
                %s
                """;

    public BaioLibInternalException(String cause) {
        super(String.format(template, cause));
    }

    public BaioLibInternalException(Exception e) {
        super(String.format(template, e.getMessage()));
    }
}
