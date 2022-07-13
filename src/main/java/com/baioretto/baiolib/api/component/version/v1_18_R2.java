package com.baioretto.baiolib.api.component.version;

import com.baioretto.baiolib.api.component.IComponentUtil;
import com.google.gson.JsonElement;
import net.minecraft.network.chat.MutableComponent;

@SuppressWarnings("unused")
public class v1_18_R2 implements IComponentUtil {
    @Override
    public MutableComponent jsonToMinecraftComponent(JsonElement element) {
        return net.minecraft.network.chat.Component.Serializer.fromJson(element);
    }
}