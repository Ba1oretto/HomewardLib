package com.baioretto.homewardlib.util;

import java.lang.annotation.Annotation;

import static java.util.Objects.*;

public class Validate extends BaseUtil {
    public static boolean isInstantiable(Class<?> clazz, Class<? extends Annotation> annotation) {
        return nonNull(requireNonNull(clazz).getAnnotation(requireNonNull(annotation)));
    }

    public static boolean isEmpty(Object[] array) {
        return array.length == 0;
    }
}
