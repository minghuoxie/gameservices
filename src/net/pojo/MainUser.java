package net.pojo;

public class MainUser {
    private String ip;
    private String ukey;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUser() {
        return ukey;
    }

    public void setUser(String ukey) {
        this.ukey = ukey;
    }

    @Override
    public String toString() {
        return "MainUser{" +
                "ip='" + ip + '\'' +
                ", ukey='" + ukey + '\'' +
                '}';
    }
}
