import net.conn.WoffTest;
import net.connurl.DouYun;
import net.connurl.GoMain;
import net.connurl.Test;
import net.connurl.WuBa;
import net.dbconnect.Db;
import net.dbconnect.DbCallBack;
import net.help.FirstNodeTree;
import net.help.MapToObj;
import net.help.Time;
import net.help.node.HtmlNodeOne;
import net.pojo.MainUser;
import net.pojo.TestTab;
import net.pojo.ZhuFang;
import net.test.TestOne;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OnlyMain {
    //测试java.net
    private static void test_TestOne_testOne(){
        new TestOne().testOne();
    }
    //测试连接mysql数据库
    private static void test_Db_testSelect() throws SQLException {
        new Db().testSelect();
    }
    //测试插入数据
    private static void test_Db_insertDbOne(){
        new Db().insertDbOne("insert into main_user(ip,ukey) value(?,?)",new Object[]{"14","sdfsdfsdf"});
    }

    //测试删除数据
    private static void test_Db_delDbs(){
        new Db().delDbs("delete from main_user where ip=?",new Object[]{"12"});
    }

    //测试修改数据
    private static void test_Db_upDbs(){
        new Db().upDbs("update main_user set ip=?,ukey=? where ip=?",new Object[]{"112","hahahahah","111"});
    }
    //测试回调的查询
    private static void test_Db_selectCallBack(){
        List<MainUser> list=new ArrayList<>();
        new Db().selectCallBack("select * from main_user where ip=?", new Object[]{"13"}, new DbCallBack() {
            @Override
            public void callBack(ResultSet reSet, ResultSetMetaData reData) {
                try {
                    int colNum=reData.getColumnCount();
                    while(reSet.next()){
                        MainUser user=new MainUser();
                        Field[] fileds=user.getClass().getDeclaredFields();
                        for(int i=0;i<colNum;i++){
                            String keyName=reData.getColumnName(i+1);
                            for(int j=0;j<fileds.length;j++){
                                if(fileds[j].toString().endsWith(keyName)) {
                                    fileds[j].setAccessible(true);
                                    fileds[j].set(user,reSet.getObject(keyName));
                                }
                            }
                            //System.out.print(keyName+":"+reSet.getObject(keyName)+"   ");
                        }
                        list.add(user);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).closeConn();

        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
    }

    //测试查询数据的条数
    private static void test_Db_selectCount(){
       int num=new Db().selectCount("select count(1) from main_user where ip=?",new Object[]{"78"});
       System.out.println(num);
    }

    //测试List<Map<String,String>>
    private static void test_Db_selectLists(){
        List<Map<String,String>> list=new Db().selectLists("select * from main_user",null);

//        TestTab tab=new MapToObj().mapToObj(list.get(0), TestTab.class);
//        System.out.println(tab);

        List<MainUser> listTabs=new MapToObj().mapToObj(list,MainUser.class);
        for(MainUser tb:listTabs){
            System.out.println(tb);
        }

//        if(list!=null&&list.size()>0){
//            for(Map<String,String> map:list){
//                Iterator<String> ite=map.keySet().iterator();
//                while(ite.hasNext()){
//                    String keyName=ite.next();
//                    System.out.print(keyName+":"+map.get(keyName)+"   ");
//                }
//                System.out.println();
//            }
//        }
    }

    //测试获取Map<String,String>
    private static void test_Db_selectMap(){
        Map<String,String> map=new Db().selectMap("select * from test_tab where pint=?",new Object[]{2});
        System.out.println(map.toString());
        TestTab tab=new MapToObj().mapToObj(map,TestTab.class);
        System.out.println(tab);
    }

    //测试获取对象
    private static void test_Db_selectObj(){
        TestTab tb=new Db().selectObj("select pint,pstr,pdate,pdatetime,pdecimal from test_tab where pint=?",new Object[]{2},TestTab.class);
        System.out.println(tb);
    }

    //测试获取对象集合
    private static void test_Db_selectListObjs(){
        //in 查询失败
       // List<TestTab> list=new Db().selectListObjs("select pint,pstr,pdate,pdatetime,pdecimal from test_tab where pint in ?",new Object[]{new Integer[]{1,2,3}},TestTab.class);
        List<ZhuFang> list=new Db().selectListObjs("select * from tab_zhu_fang",null, ZhuFang.class);
        if(list!=null&&list.size()>0){
            for(ZhuFang tb:list){
                System.out.println(tb);
            }
        }
    }

    //测试goMain 惠水住房
    private static void test_GoMain_go(){
        new GoMain().go();
    }

    //测试时间
    private static void test_Time_getTime(){
       System.out.println(Time.getTime("yyyy-MM-dd"));
    }

    //测试 BufRead
    private static void test_FirstNodeTree_firstNode(){
    }

    //58同城住房的测试
    private static void test_WuBa_zhufan(){
        new WuBa().zhufan();
    }

    //测试都匀住房
    private static void test_DouYun_douYunFang(){
        new DouYun().douYunFang();
    }

    //测试woff文件
    private static void test_WoffTest_woff(){
        new WoffTest().woff();
    }

    //https://blog.csdn.net/u013886628/article/details/51820221   haha
    // 高级爬虫   模拟javascript

    public static void main(String[] args){
        test_WuBa_zhufan();
    }
}
