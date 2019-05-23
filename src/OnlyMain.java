import net.connurl.GoMain;
import net.dbconnect.Db;
import net.dbconnect.DbCallBack;
import net.pojo.MainUser;
import net.test.TestOne;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OnlyMain {
    //测试java.net
    private static void test_TestOne_testOne(){
        new TestOne().testOne();
    }
    //测试goMain
    private static void test_GoMain_go(){
        new GoMain().go();
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

    public static void main(String[] args){
        //test
        try {
            test_Db_upDbs();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
