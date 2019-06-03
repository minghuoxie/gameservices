package net.help;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapToObj {

    public<T> List<T> mapToObj(List<Map<String,String>> list, Class<T> c){
        List<T> objList=new ArrayList<>();
        if(list!=null&&list.size()>0){
            for(Map<String,String> map:list){
                objList.add(mapToObj(map,c));
            }
        }
        return objList;
    }

    //将Map转换为对应的类型 主 key和属性必须一样
    public<T> T mapToObj(Map<String,String> map,Class<T> c){
        if(map==null||map.isEmpty()){
            return null;
        }
        T t= null;
        try {
            t = c.newInstance();
            Field[] fields=c.getDeclaredFields();
            for(int i=0;i<fields.length;i++){
                String keyName=fields[i].getName().toLowerCase();
                if(map.containsKey(keyName)){
                    fields[i].setAccessible(true);
                    //查看类型 进行类型转换
                    String typeStr=fields[i].getType().toString().replaceAll(" +","");
                    if(typeStr.endsWith("int")||typeStr.endsWith("lang.Integer")){
                        fields[i].setInt(t,Integer.parseInt(map.get(keyName)));
                    }else if(typeStr.endsWith("lang.String")){
                        fields[i].set(t,map.get(keyName));
                    }else if(typeStr.endsWith("double")||typeStr.endsWith("float")){
                       fields[i].setDouble(t,Double.parseDouble(map.get(keyName)));
                    }else if(typeStr.endsWith("sql.Date")){
                        fields[i].set(t,java.sql.Date.valueOf(map.get(keyName)));
                    }else if(typeStr.endsWith("sql.Timestamp")){
                       fields[i].set(t,java.sql.Timestamp.valueOf(map.get(keyName)));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public static Map<String,String> strToMap(String str){
        if(str==null){
            return null;
        }
        Map<String,String> map=new HashMap<>();

        return map;
    }

    //对标题进行过滤。。。。
    public static boolean isMapTitle(String title){
        String lowTitle;
        if(title!=null&&title.length()>0&&((lowTitle=title.toLowerCase()).contains("java")||lowTitle.contains("android")||lowTitle.contains("维护")||
                lowTitle.contains("网络")||lowTitle.contains("程序")||lowTitle.contains("开发")||lowTitle.contains("电脑")||lowTitle.contains("计算机")||
                lowTitle.contains("IT")||lowTitle.contains("黑客"))){
            return true;
        }
        return false;
    }
}
