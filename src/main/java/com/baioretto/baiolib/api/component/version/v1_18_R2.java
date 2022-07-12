package com.baioretto.baiolib.api.component.version;

import com.baioretto.baiolib.api.component.IComponentUtil;
import com.baioretto.baiolib.util.Validate;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.chat.MutableComponent;

import java.util.Stack;

@SuppressWarnings("unused")
public class v1_18_R2 implements IComponentUtil {
    @Override
    public MutableComponent jsonToMinecraftComponent(JsonElement element) {
        return net.minecraft.network.chat.Component.Serializer.fromJson(element);
    }

    @Override
    public void decomponent(JsonObject object) {
        Stack<JsonObject> stack = new Stack<>();
        stack.push(object);

        while (!stack.empty()) {
            JsonObject top = stack.pop();

            JsonElement content = top.remove("content");
            top.add("text", content);

            JsonElement children = top.remove("children");
            top.add("extra", children);

            JsonObject style = top.remove("style").getAsJsonObject();
            applyStyle(top, style);

            JsonArray extra = top.get("extra").getAsJsonArray();
            if (extra.size() == 0) top.remove("extra");
            extra.forEach(jsonElement -> stack.push((JsonObject) jsonElement));
        }
    }

    private void applyStyle(JsonObject object, JsonObject style) {
        JsonElement colorElement = style.get("color");
        if (Validate.notNull(colorElement)) {
            String hexString = Integer.toHexString(colorElement.getAsJsonObject().get("value").getAsInt());
            object.addProperty("color", HEX_PREFIX + hexString);
        }

        JsonElement clickEventElement = style.get("clickEvent");
        if (Validate.notNull(clickEventElement)) {
            JsonObject var0 = clickEventElement.getAsJsonObject();
            JsonObject var1 = new JsonObject();
            var1.addProperty("action", var0.get("action").getAsString());
            var1.addProperty("value", var0.get("value").getAsString());
            object.add("clickEvent", var1);
        }

        JsonElement hoverEventElement = style.get("hoverEvent");
        if (Validate.notNull(hoverEventElement)) {
            JsonObject var0 = hoverEventElement.getAsJsonObject();
            JsonObject var1 = new JsonObject();
            var1.addProperty("action", var0.get("action").getAsString());
            var1.addProperty("value", var0.get("value").getAsString());
            object.add("hoverEvent", var1);
        }

        JsonElement insertionElement = style.get("insertion");
        if (Validate.notNull(insertionElement))
            object.addProperty("insertion", insertionElement.getAsString());

        JsonElement fontElement = style.get("font");
        if (Validate.notNull(fontElement))
            object.addProperty("font", fontElement.getAsString());

        String bold = style.get("bold").getAsString();
        if (Validate.matchesBoolean(bold)) {
            object.addProperty("bold", bold);
        }

        String italic = style.get("italic").getAsString();
        if (Validate.matchesBoolean(italic)) {
            object.addProperty("italic", italic);
        }

        String underlined = style.get("underlined").getAsString();
        if (Validate.matchesBoolean(underlined)) {
            object.addProperty("underlined", underlined);
        }

        String strikethrough = style.get("strikethrough").getAsString();
        if (Validate.matchesBoolean(strikethrough)) {
            object.addProperty("strikethrough", strikethrough);
        }

        String obfuscated = style.get("obfuscated").getAsString();
        if (Validate.matchesBoolean(obfuscated)) {
            object.addProperty("obfuscated", obfuscated);
        }
    }
}