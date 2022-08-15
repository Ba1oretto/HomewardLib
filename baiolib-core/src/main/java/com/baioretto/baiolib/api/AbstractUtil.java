package com.baioretto.baiolib.api;

import com.baioretto.baiolib.ServerConfig;
import com.baioretto.baiolib.annotation.Instantiable;
import com.baioretto.baiolib.exception.BaioLibInternalException;
import com.baioretto.baiolib.util.ReflectionUtil;
import com.baioretto.baiolib.util.Validate;

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
        return instance;
    }

    protected final C instance;

    protected AbstractUtil(Class<C> clazz) {
        this.instance = getInstance(clazz);
    }

    protected C getInstance(Class<C> clazz) {
        return defaultGetInstance(clazz);
    }

    @SuppressWarnings("unchecked")
    private C defaultGetInstance(Class<C> clazz) {
        if (!Validate.hasAnnotation(clazz, Instantiable.class)) {
            throw new BaioLibInternalException("This class is not instantiable.");
        }
        String implClassName = String.format("%s.impl.%s", clazz.getPackageName(), ServerConfig.NET_MINECRAFT_VERSION);

        Class<C> implClass = (Class<C>) ReflectionUtil.getClass(implClassName);

        return ReflectionUtil.newInstance(implClass);
    }
}
