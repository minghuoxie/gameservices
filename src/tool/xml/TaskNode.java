package tool.xml;

public class TaskNode {
    private String nodeName;
    private String id;
    private String name;
    private char type;// r 矩形   o 圆形
    private TaskPoint taskPoint;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public TaskPoint getTaskPoint() {
        return taskPoint;
    }

    public void setTaskPoint(TaskPoint taskPoint) {
        this.taskPoint = taskPoint;
    }
}
