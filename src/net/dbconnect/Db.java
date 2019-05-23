package net.dbconnect;

import net.help.MapToObj;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Db{
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

    //根据条件查询条数
    public int selectCount(String sql,Object[] objs){
        conn();
        if(con!=null){
            try{
                statement=con.prepareStatement(sql);
                setObjs(objs);
                reSet=statement.executeQuery();
                if(reSet.next()){
                    return reSet.getInt(1);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        closeConn();
        return 0;
    }

    //查询返回Map<String,String>
    public Map<String,String> selectMap(String sql,Object[] objs){
        conn();
        Map<String,String> map=new HashMap<>();
        if(con!=null){
            try {
                statement=con.prepareStatement(sql);
                setObjs(objs);
                reSet=statement.executeQuery();
                ResultSetMetaData reDatas=reSet.getMetaData();
                int colNum=reDatas.getColumnCount();
                if(reSet.next()){
                    for(int i=0;i<colNum;i++){
                        String keyName=reDatas.getColumnName(i+1).toLowerCase();
                        map.put(keyName,reSet.getObject(keyName)+"");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        closeConn();
        return map;
    }

    //查询 返回List<Map<String,Object>>
    public List<Map<String,String>> selectLists(String sql,Object[] objs){
        conn();
        List<Map<String,String>> list=new ArrayList<>();
        if(con!=null){
            try {
                statement=con.prepareStatement(sql);
                setObjs(objs);
                reSet=statement.executeQuery();
                ResultSetMetaData reDatas=reSet.getMetaData();
                int colNum=reDatas.getColumnCount();
                while(reSet.next()){
                    Map<String,String> map=new HashMap<>();
                    for(int i=0;i<colNum;i++){
                        String keyName=reDatas.getColumnName(i+1).toLowerCase();
                        map.put(keyName,reSet.getObject(keyName)+"");
                    }
                    list.add(map);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        closeConn();
        return list;
    }

    public<T> T selectObj(String sql,Object[] objs,Class<T> c){
        return new MapToObj().mapToObj(selectMap(sql,objs),c);
    }

    public<T> List<T> selectListObjs(String sql,Object[] objs,Class<T> c){
        return new MapToObj().mapToObj(selectLists(sql,objs),c);
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
