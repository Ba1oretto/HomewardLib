import bean.NestedBean;
import com.baioretto.baiolib.util.Util;
import com.google.gson.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class NestedBeanTest {
    private static final String BEAN;
    private static final Gson GSON = Util.get(() -> {
        GsonBuilder builder = new GsonBuilder();
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
    void test() {
        JsonObject object = JsonParser.parseString(BEAN).getAsJsonObject();

        NestedBean bean = recursion(object);
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
