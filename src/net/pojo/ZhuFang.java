package net.pojo;

import java.io.Serializable;
import java.sql.Date;

public class ZhuFang implements Serializable {
    private String title="";
    private String perType="";//住房类型
    private String price="";//价格
    private String addr="";//地址
    private String urlType="";
    private String content="";
    private String phone="";//联系方式
    private String froms="";//来源
    private Date date; //时间

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPerType() {
        return perType;
    }

    public void setPerType(String perType) {
        this.perType = perType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddr() {
        return addr;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFrom() {
        return froms;
    }

    public void setFrom(String froms) {
        this.froms = froms;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public String getContent() {
        return content;
    }

    public String isNull(){
        if("".equals(title+perType+price+addr+urlType+content)){
            return null;
        }
        return "";
    }
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ZhuFang{" +
                "title='" + title + '\'' +
                ", perType='" + perType + '\'' +
                ", price='" + price + '\'' +
                ", addr='" + addr + '\'' +
                ", urlType='" + urlType + '\'' +
                ", content='" + content + '\'' +
                ", phone='" + phone + '\'' +
                ", froms='" + froms + '\'' +
                ", date=" + date +
                '}';
    }
}
