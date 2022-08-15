package com.baioretto.baiolib.util;

import com.baioretto.baiolib.exception.NotActuallyBaioLibException;
import net.kyori.adventure.text.Component;
import net.minecraft.network.chat.IChatBaseComponent;

/**
 * A {@link Component} util class.
 *
 * @author baioretto
 * @since 1.1.0
 */
public class ComponentSerializer {
    /**
     * Serialize a {@link Component} to json {@link String}.
     *
     * @param component the component you want to serialize
     * @return json string
     */
    public String serialize(Component component) {
        throw new NotActuallyBaioLibException();
    }

    /**
     * Deserialize a {@link String} to {@link Component}.
     *
     * @param input the string you want to deserialize.
     * @return component
     */
    public Component deserialize(String input) {
        throw new NotActuallyBaioLibException();
    }

    /**
     * Serialize a json string to {@link IChatBaseComponent}.
     *
     * @param input json string
     * @return the output
     */
    public IChatBaseComponent serializeToMinecraft(String input) {
        throw new NotActuallyBaioLibException();
    }

    /**
     * Serialize {@link Component} to {@link IChatBaseComponent}.
     *
     * @param component component
     * @return the output
     */
    public IChatBaseComponent serializeToMinecraft(Component component) {
        throw new NotActuallyBaioLibException();
    }
}
