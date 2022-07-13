package com.baioretto.baiolib.enumerate;

import com.google.common.collect.Maps;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;

public enum ChatField {
    // content
    TEXT("text", String.class),

    // style
    BOLD("bold", Boolean.class),
    ITALIC("italic", Boolean.class),
    UNDERLINED("underlined", Boolean.class),
    STRIKETHROUGH("strikethrough", Boolean.class),
    OBFUSCATED("obfuscated", Boolean.class),

    FONT("font", String.class),
    COLOR("color", String.class),
    INSERTION("insertion", String.class),

    CLICK_EVENT("clickEvent", Object.class),
    HOVER_EVENT("hoverEvent", Object.class),

    // children
    EXTRA("extra", Collection.class),

    // event
    ACTION("action", String.class),
    VALUE("value", String.class),
    CONTENTS("contents", String.class),
    SHOW_TEXT("show_text", String.class),
    SHOW_ITEM("show_item", String.class),
    SHOW_ENTITY("show_entity", String.class),
    ID("id", String.class),
    COUNT("count", Integer.class),
    TYPE("type", String.class),

    // children
    UNDEFINED(null, null);

    private final String name;

    private final Class<?> type;

    ChatField(String name, Class<?> type) {
        this.name = name;
        this.type = type;
    }

    public Class<?> getType() {
        return this.type;
    }

    public String asString() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    private static final Map<String, ChatField> NAMES = Maps.newHashMap();

    public static @Nullable ChatField get(String name) {
        return NAMES.get(name);
    }

    static {
        for (ChatField value : values()) {
            NAMES.put(value.asString(), value);
        }
    }
}
