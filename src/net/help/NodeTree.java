package net.help;

import java.util.List;
import java.util.Map;

public class NodeTree {
    // 将html页面 转换为数
    private String flabel;
    private String elabel;
    private String content;
    private Map<String,String> map;
    private List<NodeTree> childNodes;

    public String getFlabel() {
        return flabel;
    }

    public void setFlabel(String flabel) {
        this.flabel = flabel;
    }

    public String getElabel() {
        return elabel;
    }

    public void setElabel(String elabel) {
        this.elabel = elabel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public List<NodeTree> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<NodeTree> childNodes) {
        this.childNodes = childNodes;
    }
}
