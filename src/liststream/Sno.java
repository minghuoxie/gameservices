package liststream;

public class Sno {
    private int pid;
    private int id;
    private String name;

    public Sno(int pid, int id, String name) {
        this.pid = pid;
        this.id = id;
        this.name = name;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Sno{" +
                "pid=" + pid +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
