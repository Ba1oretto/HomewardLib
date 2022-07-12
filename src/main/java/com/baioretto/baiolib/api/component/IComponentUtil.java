package com.baioretto.baiolib.api.component;

import com.baioretto.baiolib.annotation.Instantiable;
import com.baioretto.baiolib.util.Instance;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.kyori.adventure.text.TextComponent;
import net.minecraft.network.chat.MutableComponent;

/**
 * The {@code IComponentUtil} class allow you convert component to json object.
 *
 * @author baioretto
 * @since 1.1.0
 */
@Instantiable
public interface IComponentUtil {
    char HEX_PREFIX = '#';
    Gson GSON = Instance.getGson();

    /**
     * Decomponent the given text component.
     *
     * @param component The component you want to decomponent
     * @return json formatted object
     */
    default JsonObject decomponent(TextComponent component) {
        JsonObject jsonObject = JsonParser.parseString(GSON.toJson(component)).getAsJsonObject();
        decomponent(jsonObject);
        return jsonObject;
    }

    /**
     * Get {@link MutableComponent} by {@link JsonElement}
     *
     * @param element the json object
     * @return {@link MutableComponent}
     */
    MutableComponent jsonToMinecraftComponent(JsonElement element);

    /**
     * Decomponent the given text component.
     *
     * @param object The component json formatted string you want to decomponent
     */
    void decomponent(JsonObject object);
}
