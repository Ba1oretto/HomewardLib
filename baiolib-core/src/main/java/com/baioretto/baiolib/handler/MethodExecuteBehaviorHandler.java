package com.baioretto.baiolib.handler;

public interface MethodExecuteBehaviorHandler {
    void paperImpl();

    void craftBukkitImpl();

    default void defaultImpl() {
    }

    void nonAsyncImpl();
}
