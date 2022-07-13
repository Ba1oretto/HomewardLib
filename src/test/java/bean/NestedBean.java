package bean;

import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedReturnValue")
@ToString
public class NestedBean {
    String content;

    List<NestedBean> children = new ArrayList<>();

    public List<NestedBean> children() {
        return this.children;
    }

    public String content() {
        return this.content;
    }

    public NestedBean content(String content) {
        this.content = content;
        return this;
    }

    public NestedBean children(NestedBean children) {
        this.children.add(children);
        return this;
    }

    public NestedBean children(List<NestedBean> children) {
        this.children = children;
        return this;
    }
}
