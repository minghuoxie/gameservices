package net.dbconnect;

import java.sql.*;
import java.util.List;

public class Db {
    private Connection con;
    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/testgms";
    private String user = "root";
    private String pwd="root";

    //操作数据库
    private PreparedStatement statement;
    private ResultSet reSet;
    private void conn(){
        try {
            Class.forName(driver);
            con= DriverManager.getConnection(url,user,pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //插入数据
    public boolean insertDbOne(String sql,Object[] obs){
        conn();
        if(con!=null){
            try {
                statement=con.prepareStatement(sql);
                setObjs(obs);
                return statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        closeConn();
        return -1==0;
    }

    //删除数据
    public boolean delDbs(String sql,Object[] obj){
        return insertDbOne(sql,obj);
    }

    //修改数据
    public boolean upDbs(String sql,Object[] objs){
        return insertDbOne(sql,objs);
    }

    //回调的查询方法
    public Db selectCallBack(String sql, Object[] objs, DbCallBack callBack){
        conn();
        if(con!=null){
            try {
                statement=con.prepareStatement(sql);
                setObjs(objs);
                reSet=statement.executeQuery();
                ResultSetMetaData setDatas=reSet.getMetaData();
                callBack.callBack(reSet,setDatas);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return this;
    }



    private void setObjs(Object[] obs) throws SQLException {
        if(obs!=null&&obs.length>0){
            for(int i=0;i<obs.length;i++){
                statement.setObject(i+1,obs[i]);
            }
        }
    }

    //测试数据库连接成功
    public void testSelect() throws SQLException {
        conn();
        if(con!=null) {
            String sql = "select * from main_user where ip=?";
            statement=con.prepareStatement(sql);
            statement.setObject(1,"12");
            reSet=statement.executeQuery();

            ResultSetMetaData setDatas=reSet.getMetaData();
           int colNum=setDatas.getColumnCount();
           while(reSet.next()){
               for(int i=0;i<colNum;i++){
                   String name=setDatas.getColumnName(i+1);
                   System.out.print(name+":"+reSet.getObject(name)+"  ");
               }
               System.out.println();
           }
            closeConn();
        }
    }


    public void closeConn(){
        try {
            if(con!=null) {
                con.close();
            }
            if(reSet!=null){
                reSet.close();
            }
            if(statement!=null){
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
