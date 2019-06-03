package net.dbconnect.sqlstr;

public class SqlHuiShui {

    //根据title和urlType查询条数
    public static String findNum="select count(1) from tab_zhu_fang where title=? and urltype=?";
    //根据title查询条数
    public static String finNumByTitle="select count(1) from tab_zhu_fang where title=?";
    //添加数据
    public static String insertDats="insert into tab_zhu_fang(title,pertype,price,urlType,addr,content,froms,date) value(?,?,?,?,?,?,?,?)";

    public static String insertQiPan="insert into qi_pan(type,title,url) value(?,?,?)";
    public static String findQiPanNum="select count(1) from qi_pan where type=? and title=?";

}
