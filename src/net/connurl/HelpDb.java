package net.connurl;

import net.dbconnect.Db;
import net.dbconnect.sqlstr.SqlHuiShui;
import net.help.Time;
import net.pojo.ZhuFang;

import java.util.ArrayList;
import java.util.List;

public class HelpDb {
    public static void saveZhuFan(List<ZhuFang> list,String perType,int money,String from){
        if(list!=null&&list.size()>0) {
            Db db = new Db();
            for(ZhuFang z:list){
                try {
                    String priStr = z.getPrice();
                    String numStr = "";
                    for (int i = 0; i < priStr.length(); i++) {
                        char c = priStr.charAt(i);
                        if (c >= '0' && c <= '9') {
                            numStr += c;
                        }
                    }
                    if (z.getPerType().equals(perType) && numStr != null && numStr.length() > 0 && Integer.parseInt(numStr) <= money) {
                        int num = db.selectCount(SqlHuiShui.findNum, new Object[]{z.getTitle(), z.getUrlType()});
                        if (num == 0) {
                            db.insertDbOne(SqlHuiShui.insertDats, new Object[]{z.getTitle(), z.getPerType(), z.getPrice(), z.getUrlType(), z.getAddr(), z.getContent(), from, Time.getTime("yyyy-MM-dd")});
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            db.closeConn();
        }
    }
}