package com.baioretto.baiolib.util;

import com.baioretto.baiolib.BaioLib;
import com.baioretto.baiolib.api.AbstractUtil;
import com.google.common.base.CaseFormat;
import lombok.experimental.UtilityClass;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Almost bridge.
 *
 * @author baioretto
 * @since 1.1.0
 */
@UtilityClass
@SuppressWarnings("unused")
public class Util {
    /**
     * @param function supplier
     * @param <T>      type
     * @return T
     * @see Supplier
     */
    public <T> T get(Supplier<T> function) {
        return function.get();
    }

    /**
     * Get a {@link Reflections} from package with custom classloader.
     *
     * @param pkg         package
     * @param classLoader classloader
     * @return reflections instance
     */
    public Reflections getReflections(String pkg, ClassLoader... classLoader) {
        return new Reflections(new ConfigurationBuilder()
                .addClassLoaders(classLoader)
                .addUrls(ClasspathHelper.forPackage(pkg, classLoader))
                .filterInputsBy(new FilterBuilder().includePackage(pkg)));
    }
}
