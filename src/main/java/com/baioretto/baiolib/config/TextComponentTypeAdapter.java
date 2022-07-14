package com.baioretto.baiolib.config;

import com.baioretto.baiolib.enumerate.ChatField;
import com.baioretto.baiolib.exception.BaioLibInternalException;
import com.baioretto.baiolib.util.Validate;
import com.google.common.base.CaseFormat;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static java.util.Objects.nonNull;

/**
 * The {@code TextComponentTypeAdapter} class allows you to convert between {@link TextComponent} and {@code JSON string} at any time.
 *
 * @deprecated
 * @author baioretto
 * @since 1.1.0
 */
@Deprecated
public class TextComponentTypeAdapter extends TypeAdapter<TextComponent> {
    @Override
    public void write(JsonWriter out, TextComponent value) {
        try {
            this.textComponentToString(out, value);
        } catch (IOException e) {
            throw new BaioLibInternalException(e.getMessage());
        }
    }

    @Override
    public TextComponent read(JsonReader in) {
        try {
            return this.stringToTextComponent(in);
        } catch (IOException e) {
            throw new BaioLibInternalException(e.getMessage());
        }
    }

    @SuppressWarnings("PatternValidation")
    private TextComponent stringToTextComponent(JsonReader in) throws IOException {
        TextComponent.Builder builder = Component.text();

        in.beginObject();

        ChatField chatField = ChatField.UNDEFINED;

        while (in.hasNext()) {
            switch (in.peek()) {
                case NAME -> chatField = ChatField.get(in.nextName());
                case BEGIN_ARRAY -> {
                    in.beginArray();
                    while (in.hasNext()) {
                        builder.append(this.read(in));
                    }
                    in.endArray();
                }
            }
            if (chatField == null) continue;

            switch (chatField) {
                case TEXT -> builder.content(in.nextString());

                case BOLD, ITALIC, UNDERLINED, STRIKETHROUGH, OBFUSCATED -> {
                    TextDecoration decoration = TextDecoration.valueOf(chatField.asString().toUpperCase(Locale.ROOT));
                    String flag = in.nextString();
                    builder.decoration(decoration, Validate.getBoolean(flag));
                }

                case FONT -> builder.font(Key.key(in.nextString()));
                case COLOR -> {
                    String colorCode = in.nextString();
                    try {
                        Field field = NamedTextColor.class.getField(colorCode.toUpperCase(Locale.ROOT));
                        NamedTextColor color = (NamedTextColor) field.get(null);
                        builder.color(color);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        builder.color(TextColor.fromHexString(colorCode));
                    }
                }
                case INSERTION -> builder.insertion(in.nextString());

                case CLICK_EVENT -> {
                    in.beginObject();

                    ChatField eventField = ChatField.UNDEFINED;
                    String action = null;
                    String value = null;

                    // get action & value
                    while (in.hasNext()) {
                        if (JsonToken.NAME.equals(in.peek())) {
                            eventField = ChatField.get(in.nextName());
                        }
                        if (eventField == null) continue;
                        switch (eventField) {
                            case ACTION -> action = in.nextString();
                            case VALUE -> value = in.nextString();
                        }
                    }

                    if (action != null && value != null) {
                        ClickEvent clickEvent = null;
                        try {
                            String actionLowerCamel = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, action);
                            Method method = ClickEvent.class.getMethod(actionLowerCamel, String.class);
                            try {
                                clickEvent = (ClickEvent) method.invoke(null, value);
                            } catch (IllegalAccessException | InvocationTargetException ignore) {
                                System.out.println(1111);
                            }
                        } catch (NoSuchMethodException ignore) {
                            System.out.println(1111);
                        }
                        if (clickEvent != null) {
                            builder.clickEvent(clickEvent);
                        }
                    }

                    in.endObject();
                }
                case HOVER_EVENT -> {
                    in.beginObject();

                    ChatField eventField = ChatField.UNDEFINED;
                    ChatField action = null;

                    // get action & value
                    while (in.hasNext()) {
                        if (JsonToken.NAME.equals(in.peek())) {
                            eventField = ChatField.get(in.nextName());
                        }
                        if (eventField == null) continue;
                        switch (eventField) {
                            case ACTION -> action = ChatField.get(in.nextString());
                            case CONTENTS -> {
                                if (null == action) continue;
                                HoverEvent<?> hoverEvent = null;

                                switch (action) {
                                    case SHOW_TEXT -> {
                                        try {
                                            hoverEvent = (HoverEvent<?>) HoverEvent.class.getMethod(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, action.asString()), Component.class).invoke(null, read(in));
                                        } catch (NoSuchMethodException | IllegalAccessException |
                                                 InvocationTargetException ignore) {
                                            System.out.println(2222);
                                        }
                                        if (hoverEvent != null) {
                                            builder.hoverEvent(hoverEvent);
                                        }
                                    }
                                    case SHOW_ITEM -> {
                                        String id = null;
                                        int count = 0;

                                        in.beginObject();
                                        while (in.hasNext()) {
                                            ChatField name = null;
                                            if (JsonToken.NAME.equals(in.peek())) {
                                                name = ChatField.get(in.nextName());
                                            }
                                            if (name == null) continue;
                                            switch (name) {
                                                case ID -> id = in.nextString();
                                                case COUNT -> count = in.nextInt();
                                            }
                                        }
                                        in.endObject();

                                        if (id != null) {
                                            try {
                                                hoverEvent = (HoverEvent<?>) HoverEvent.class.getMethod(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, action.asString()), Key.class, int.class).invoke(null, Key.key(id), count);
                                            } catch (NoSuchMethodException | IllegalAccessException |
                                                     InvocationTargetException ignore) {
                                                System.out.println(2222);
                                            }
                                            if (hoverEvent != null) {
                                                builder.hoverEvent(hoverEvent);
                                            }
                                        }
                                    }
                                    case SHOW_ENTITY -> {
                                        UUID id = null;
                                        String type = null;

                                        in.beginObject();
                                        while (in.hasNext()) {
                                            ChatField name = null;
                                            if (JsonToken.NAME.equals(in.peek())) {
                                                name = ChatField.get(in.nextName());
                                            }
                                            if (name == null) continue;
                                            switch (name) {
                                                case TYPE -> type = in.nextString();
                                                case ID -> id = UUID.fromString(in.nextString());
                                            }
                                        }
                                        in.endObject();

                                        if (id != null && type != null) {
                                            try {
                                                hoverEvent = (HoverEvent<?>) HoverEvent.class.getMethod(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, action.asString()), Key.class, UUID.class).invoke(null, Key.key(type), id);
                                            } catch (NoSuchMethodException | IllegalAccessException |
                                                     InvocationTargetException ignore) {
                                                System.out.println(2222);
                                            }
                                            if (hoverEvent != null) {
                                                builder.hoverEvent(hoverEvent);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    in.endObject();
                }
            }

            chatField = ChatField.UNDEFINED;
        }

        in.endObject();

        return builder.build();
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
            if (Validate.matchesBoolean(stateName)) try {
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