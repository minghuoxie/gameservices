import media.image.ImageTest;
import media.image.SetBgColor;
import media.media.TestMain;
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
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import study.Basebean;
import study.ChildBean;
import study.NoBaseBean;
import unicode.ChinaUtf;
import unicode.Each;
import unicode.UnicodeTest;
import unicode.UtfBa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
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
    private static void test_WuBa_zhuFan(){
        new WuBa().zhuFan();
    }

    //测试都匀住房
    private static void test_DouYun_douYunFang(){
        new DouYun().douYunFang();
    }

    //测试woff文件
    private static void test_WoffTest_woff(){
        new WoffTest().woff();
    }

    //java调用python的测试
    private static void test_pythonOne(){
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile("D:/yunzhi/pypro/example.py");
        PyFunction func = (PyFunction)interpreter.get("retHtml",PyFunction.class);
       // PyObject pyobj = func.__call__(new PyInteger(2016),new PyInteger(2016));
        System.out.println("retMsg = ");
    }
    private static void test_pythonTwo(){
        System.out.println("start python");
        //设置命令行传如参数
        String[] arg=new String[]{"python","D:/yunzhi/pypro/example.py"};
        Process pr=null;
        try{

            pr=Runtime.getRuntime().exec(arg);
          //  BufferedReader in=new BufferedReader(new InputStreamReader(pr.getErrorStream()));
            BufferedReader in=new BufferedReader(new InputStreamReader(pr.getInputStream(),"gb2312"));
            String line=null;
            while((line=in.readLine())!=null){
                System.out.println(line);
            }
            in.close();
            pr.waitFor();
            System.out.println("end:::");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //测试goMain 惠水住房
    private static void test_GoMain_go(){
        new GoMain().go();
    }

    //测试GBK编码
    private static void test_ChinaUtf(){
        //test_GoMain_go();
        // ChinaUtf.saveToFile("D:/Temp/unicode.txt");

        ChinaUtf.testFindStrs("你好啊，大哥，，，，五五五五，，，刚刚聊");
        //ChinaUtf.randomChines();
        // 44
        // 38+4+8
    }


    //进制转换测试
    private static void test_Each(){
        //二级制转十进制
       //String ss=Each.etoo("1111011111",10);
        // //二级制转十六进制
        //String str=Each.etoo("1111011111",16);

        //十进制转二进制
       String stoe=Each.stoo("129",16);

        //十六进制转二进制
       // String erltoo=Each.ltoo("03DF",2);
        //十六进制转十进制
       // String sltoo=Each.ltoo("03DF",10);

       System.out.println(stoe);
    }
    //测试unicode编码
    private static void test_UnicodeTest(){
        /**
         * 46   129   ⺁
         * 0x2E 0x81 ⺁
         * \u2e81
         * 46 224 -46 255 没有编码
         * 47 214 -47 255 没有编码
         *
         *
         * */
        try {
            //  UnicodeTest.saveFile("D:/Temp/unicode.txt");
           // UnicodeTest.testFind("谢");
           // String s=UnicodeTest.eachCodef("谢");
            String s=UnicodeTest.codeEach("\\u4E25");
            System.out.println(s);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //utf-8编码
    private static void test_UtfBa(){
        try {
           // UtfBa.testOneByte();
//            UtfBa.testPrintUTFONE("D:/Temp/utf8.txt");
//            UtfBa.testPrintUTFTWO("D:/Temp/utf8.txt");
//            UtfBa.testPringUTFSAN("D:/Temp/utf8.txt");
           String findCode=UtfBa.testFindCode("中");
           System.out.println("--:"+findCode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static void test_SetBgColor(){
       // SetBgColor.setBgColort(210,"D:\\Temp\\img\\test.jpg","D:\\Temp\\img\\ntest.png");
        SetBgColor.setImage("D:\\Temp\\img\\rangTwo.jpg");
    }
    public static void main(String[] args){
        System.out.println("------------------start-------------------");
        test_SetBgColor();
        System.out.println("------------------end-------------------");
//        ImageTest.readFile("D:\\Temp\\img\\lxyone.jpg");
//        //2147483648-15195862=2132287786
//        //2132287786=1111111000110000010000100101010
//        //
//
//        String ss=Each.etoo("11111111",10);
//        String stoe=Each.stoo("2132287785",2);
//        System.out.println(ss);
    }

    //11111111111111111111111111111111  2147483647
    //00000000111001111101111011010110  15195862
    //11111111 00011000 00100001 00101010   2132287786   RGB(24,33,42)
    //11111111000110000010000100101001   2132287785


    private static void sub(){
        String str="<ul id='thread_list' >";
        System.out.println(str.substring(0,20));
    }

    //引用类型
    private static void addSum(){
        List<Integer> list=new ArrayList<>();

        list.add(new Integer(1));
        list.add(new Integer(2));
        list.add(new Integer(3));
        list.add(new Integer(4));
        Integer sum=new Integer(0);
        for(int i=0;i<list.size();i++){
            sum+=list.get(i);
            list.set(i,sum);
        }
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
    }
}
