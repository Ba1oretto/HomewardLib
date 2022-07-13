import bean.NestedBean;
import com.baioretto.baiolib.config.TextComponentTypeAdapter;
import com.baioretto.baiolib.util.Util;
import com.google.gson.*;
import lombok.SneakyThrows;
import net.kyori.adventure.text.TextComponent;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class NestedBeanTest {
    private static final String BEAN;
    private static final Gson GSON = Util.get(() -> {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(TextComponent.class, new TextComponentTypeAdapter());
        return builder.create();
    });

    static {
        NestedBean bean = new NestedBean().content("1");
        NestedBean bean$1 = new NestedBean().content("1.1");
        NestedBean bean$1$1 = new NestedBean().content("1.1.1");
        NestedBean bean$1$2 = new NestedBean().content("1.1.2");
        bean.children(bean$1.children(bean$1$1).children(bean$1$2));

        BEAN = GSON.toJson(bean);
    }

    @Test
    @SneakyThrows
    void test() {
        String test = "{\"color\":\"green\",\"extra\":[{\"bold\":true,\"color\":\"#ffc0cb\",\"text\":\"2\"},{\"extra\":[{\"hoverEvent\":{\"action\":\"show_item\",\"contents\":{\"id\":\"minecraft:paper\",\"count\":2}},\"text\":\"3.1\"}],\"text\":\"3\"}],\"text\":\"1\"}";
        // String test = "{\"color\":\"green\",\"extra\":[{\"color\":\"#ffc0cb\",\"text\":\"2\"},{\"extra\":[{\"text\":\"3.1\"}],\"text\":\"3\"}],\"text\":\"1\"}";
        TypeAdapter<TextComponent> adapter = GSON.getAdapter(TextComponent.class);
        TextComponent textComponent = adapter.fromJson(test);
        System.out.println(textComponent);
    }



    private NestedBean recursion(JsonElement element) {
        JsonObject object = element.getAsJsonObject();
        NestedBean nestedBean = new NestedBean();

        String content = object.get("content").getAsString();
        nestedBean.content(content);

        JsonArray children = object.get("children").getAsJsonArray();

        List<NestedBean> nestedBeans = new ArrayList<>();
        for (JsonElement child : children) {
            nestedBeans.add(recursion(child));
        }

        nestedBean.children(nestedBeans);

        return nestedBean;
    }
}
