package com.baioretto.baiolib.api;

import com.baioretto.baiolib.BaioLib;
import com.baioretto.baiolib.util.ReflectionUtil;
import com.google.common.collect.ImmutableMap;
import lombok.experimental.UtilityClass;

import java.util.Hashtable;

/**
 * get class instance from api package
 *
 * @since 1.6.0
 * @author baioretto
 */
@UtilityClass
public class CachedVersionWrapper {
    private final Hashtable<Class<? extends AbstractUtil<?>>, AbstractUtil<?>> CACHE = new Hashtable<>();

    /**
     * get instance from specific class
     *
     * @param clazz the class required for instantiation
     * @return instance of this {@code clazz}
     * @param <T> class type
     * @since 1.6.0
     */
    @SuppressWarnings({"unchecked", "SynchronizationOnLocalVariableOrMethodParameter"})
    public <T extends AbstractUtil<?>> T of(Class<T> clazz) {
        AbstractUtil<?> instance = CACHE.get(clazz);

        if (instance == null) {
            final var lock = LOCK_COLLECTION.get(clazz.getName());
            if (lock != null) {
                synchronized (lock) {
                    instance = ReflectionUtil.newInstance(clazz);
                }
            } else instance = ReflectionUtil.newInstance(clazz);
            CACHE.put(clazz, instance);
        }

        return (T) instance;
    }

    @SuppressWarnings("rawtypes")
    private final ImmutableMap<String, Class<? extends AbstractUtil>> LOCK_COLLECTION;

    static {
        @SuppressWarnings("rawtypes") ImmutableMap.Builder<String, Class<? extends AbstractUtil>> builder = ImmutableMap.builder();
        ReflectionUtil.getReflections(BaioLib.class.getPackageName() + ".api").getSubTypesOf(AbstractUtil.class).forEach(clazz -> builder.put(clazz.getName(), clazz));
        LOCK_COLLECTION = builder.build();
    }
}