package com.baioretto.baiolib.util;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

/**
 * A {@link Component} util class.
 *
 * @author baioretto
 * @since 1.1.0
 */
@UtilityClass
public class ComponentSerializer {
    /**
     * Serialize a {@link Component} to json {@link String}.
     *
     * @param component the component you want to serialize
     * @return json string
     */
    public String serialize(Component component) {
        return GsonComponentSerializer.gson().serialize(component);
    }

    /**
     * Deserialize a {@link String} to {@link Component}.
     *
     * @param input the string you want to deserialize.
     * @return component
     */
    public Component deserialize(String input) {
        return GsonComponentSerializer.gson().deserialize(input);
    }

    /**
     * Serialize a json string to {@link net.minecraft.network.chat.Component}.
     *
     * @param input json string
     * @return the output
     */
    public net.minecraft.network.chat.Component serializeToMinecraft(String input) {
        return net.minecraft.network.chat.Component.Serializer.fromJson(input);
    }

    /**
     * Serialize {@link Component} to {@link net.minecraft.network.chat.Component}.
     *
     * @param component component
     * @return the output
     */
    public net.minecraft.network.chat.Component serializeToMinecraft(Component component) {
        return serializeToMinecraft(serialize(component));
    }
}
