package net.pojo;

import java.sql.Date;
import java.sql.Timestamp;

public class TestTab {
    private int pInt;
    private String pStr;
    private Date pDate;
    private Timestamp pDateTime;
    private double pDecimal;

    public int getpInt() {
        return pInt;
    }

    public void setpInt(int pInt) {
        this.pInt = pInt;
    }

    public String getpStr() {
        return pStr;
    }

    public void setpStr(String pStr) {
        this.pStr = pStr;
    }

    public Date getpDate() {
        return pDate;
    }

    public void setpDate(Date pDate) {
        this.pDate = pDate;
    }

    public Timestamp getpDateTime() {
        return pDateTime;
    }

    public void setpDateTime(Timestamp pDateTime) {
        this.pDateTime = pDateTime;
    }

    public Double getpDecimal() {
        return pDecimal;
    }

    public void setpDecimal(Double pDecimal) {
        this.pDecimal = pDecimal;
    }

    @Override
    public String toString() {
        return "TestTab{" +
                "pInt=" + pInt +
                ", pStr='" + pStr + '\'' +
                ", pDate=" + pDate +
                ", pDateTime=" + pDateTime +
                ", pDecimal=" + pDecimal +
                '}';
    }
}
