package com.baioretto.homewardlib.api;

import static com.baioretto.homewardlib.util.Validate.isInstantiable;

import com.baioretto.homewardlib.annotation.Instantiable;
import com.baioretto.homewardlib.exception.NotInstantiableException;
import com.baioretto.homewardlib.util.ReflectionUtil;
import org.bukkit.Bukkit;

@SuppressWarnings("JavadocDeclaration")
public class Wrapper {
    /**
     * @param clazz
     */
    public static <T> T getClassInstance(Class<T> clazz) {
        return getClassInstance(clazz, null, false);
    }

    /**
     * @param clazz
     * @param delegate
     */
    public static <T> T getClassInstance(Class<T> clazz, Object delegate) {
        return getClassInstance(clazz, delegate, false);
    }

    /**
     * @param clazz
     * @param delegate
     * @param ignoreError
     */
    @SuppressWarnings("unchecked")
    public static <T> T getClassInstance(Class<T> clazz, Object delegate, boolean ignoreError) {
        if (!isInstantiable(clazz, Instantiable.class)) {
            if (!ignoreError)
                throw new NotInstantiableException("This class is not instantiable.");
            return null;
        }

        String implementationClassName = String.format("%s.version.%s", clazz.getPackageName(), version);
        try {
            Class<T> implementationClass = (Class<T>) Class.forName(implementationClassName);
            if (delegate == null) {
                return ReflectionUtil.newInstance(implementationClass);
            }
            return ReflectionUtil.newInstance(implementationClass, delegate.getClass(), delegate);
        } catch (ClassNotFoundException e) {
            if (!ignoreError)
                throw new RuntimeException(e);
            return null;
        }
    }

    private final static String version;

    static {
        String[] packageNameArray = Bukkit.getServer().getClass().getPackageName().split("\\.");
        version = packageNameArray[packageNameArray.length - 1];
    }
}
