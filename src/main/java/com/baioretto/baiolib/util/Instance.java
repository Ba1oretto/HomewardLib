package com.baioretto.baiolib.util;

import com.baioretto.baiolib.config.TextComponentTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.TextComponent;
import net.minecraft.util.LowerCaseEnumTypeAdapterFactory;

@UtilityClass
public class Instance {
    public Gson getGson() {
        return Util.get(() -> {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapterFactory(new LowerCaseEnumTypeAdapterFactory());
            builder.registerTypeAdapter(TextComponent.class, new TextComponentTypeAdapter());
            return builder.create();
        });
    }
}
