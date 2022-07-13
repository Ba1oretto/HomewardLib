package com.baioretto.baiolib.config;

import com.baioretto.baiolib.exception.BaioLibInternalException;
import com.baioretto.baiolib.util.Validate;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.nonNull;

/**
 * The {@code TextComponentTypeAdapter} class allows you to convert between {@link TextComponent} and {@code JSON string} at any time.
 *
 * @author baioretto
 * @since 1.1.0
 */
public class TextComponentTypeAdapter extends TypeAdapter<TextComponent> {
    @Override
    public void write(JsonWriter out, TextComponent value) {
        try {
            textComponentToString(out, value);
        } catch (IOException e) {
            throw new BaioLibInternalException(e.getMessage());
        }
    }

    @Override
    public TextComponent read(JsonReader in) {
        return null;
    }

    private void textComponentToString(JsonWriter out, TextComponent value) throws IOException {
        out.beginObject();

        // text
        String content = value.content();
        out.name("text").value(content);

        Style style = value.style();
        // bold italic underlined strikethrough obfuscated
        style.decorations().forEach((textDecoration, state) -> {
            String stateName = state.toString();
            if (Validate.matchesBoolean(stateName))
                try {
                    out.name(textDecoration.toString()).value(stateName);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        });

        // font
        Key font = style.font();
        if (nonNull(font)) {
            out.name("font").value(font.asString());
        }

        // color
        TextColor color = style.color();
        if (nonNull(color)) {
            out.name("color").value(color.asHexString());
        }

        // insertion
        String insertion = style.insertion();
        if (nonNull(insertion)) {
            out.name("insertion").value(insertion);
        }

        // clickEvent
        ClickEvent clickEvent = style.clickEvent();
        if (nonNull(clickEvent)) {
            out.name("clickEvent").beginObject();
            out.name("action").value(clickEvent.action().toString());
            out.name("value").value(clickEvent.value()).endObject();
        }

        // hoverEvent
        HoverEvent<?> hoverEvent = style.hoverEvent();
        if (nonNull(hoverEvent)) {
            HoverEvent.Action<?> action = hoverEvent.action();
            out.name("hoverEvent").beginObject();

            out.name("action").value(action.toString());
            if (Component.class.isAssignableFrom(action.type())) {
                // show_text
                out.name("value");
                textComponentToString(out, (TextComponent) hoverEvent.value());
            } else if (HoverEvent.ShowItem.class.equals(action.type())) {
                // show_item
                out.name("contents").beginObject();
                HoverEvent.ShowItem showItem = (HoverEvent.ShowItem) hoverEvent.value();
                out.name("id").value(showItem.item().asString());
                out.name("count").value(showItem.count());
                BinaryTagHolder binaryTagHolder = showItem.nbt();
                if (nonNull(binaryTagHolder)) {
                    out.name("tag").value(binaryTagHolder.string());
                }
                out.endObject();
            } else if (HoverEvent.ShowEntity.class.equals(action.type())) {
                // show_entity
                out.name("contents").beginObject();
                HoverEvent.ShowEntity showEntity = (HoverEvent.ShowEntity) hoverEvent.value();
                out.name("type").value(showEntity.type().asString());
                out.name("id").value(showEntity.id().toString());
                Component name = showEntity.name();
                if (nonNull(name)) {
                    out.name("name");
                    textComponentToString(out, (TextComponent) name);
                }
                out.endObject();
            } else throw new IllegalArgumentException();

            out.endObject();
        }

        // extra
        List<Component> children = value.children();
        if (children.size() != 0) {
            out.name("extra").beginArray();
            for (Component child : children) {
                textComponentToString(out, (TextComponent) child);
            }
            out.endArray();
        }

        out.endObject();
    }
}