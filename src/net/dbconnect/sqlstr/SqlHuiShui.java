package net.dbconnect.sqlstr;

public class SqlHuiShui {

    //根据title和urlType查询条数
    public static String findNum="select count(1) from tab_zhu_fang where title=? and urlType=?";
    //添加数据
    public static String insertDats="insert into tab_zhu_fang(title,pertype,price,urlType,addr,content) value(?,?,?,?,?,?)";
}
