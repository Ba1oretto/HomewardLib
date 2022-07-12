package com.baioretto.baiolib.util;

import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

@UtilityClass
public class Util {
    public <T> T get(Supplier<T> function) {
        return function.get();
    }
}
