package com.baioretto.baiolib.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.experimental.UtilityClass;
import net.minecraft.util.LowerCaseEnumTypeAdapterFactory;

@UtilityClass
public class Instance {
    public Gson getGson() {
        return Util.get(() -> {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapterFactory(new LowerCaseEnumTypeAdapterFactory());
            return builder.create();
        });
    }
}
