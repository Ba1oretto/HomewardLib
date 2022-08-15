package com.baioretto.baiolib.util;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.network.chat.IChatBaseComponent;

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
     * Serialize a json string to {@link net.minecraft.network.chat.IChatBaseComponent}.
     *
     * @param input json string
     * @return the output
     */
    public IChatBaseComponent serializeToMinecraft(String input) {
        return IChatBaseComponent.ChatSerializer.b(input);
    }

    /**
     * Serialize {@link Component} to {@link net.minecraft.network.chat.IChatBaseComponent}.
     *
     * @param component component
     * @return the output
     */
    public IChatBaseComponent serializeToMinecraft(Component component) {
        return serializeToMinecraft(serialize(component));
    }
}
