import media.image.ImageTest;
import media.image.ImgLog;
import media.image.SetBgColor;
import media.media.TestMain;
import media.media.XmlAndBpmn;
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
import unicode.myhuffmancode.CreateHuffmanShu;
import unicode.myhuffmancode.HufNode;
import unicode.myhuffmancode.MyHuffmanList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
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
       String stoe=Each.stoo("228",16);

        //十六进制转二进制
        stoe=Each.ltoo("F0",10);
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
           String findCode=UtfBa.testFindCode("你哥哥哥帅");
           System.out.println("--:"+findCode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    private static void test_MyHuffmanList_test(){
        HufNode nodeA=new HufNode(null,null,"A",5);
        HufNode nodeB=new HufNode(null,null,"B",4);
        HufNode nodeC=new HufNode(null,null,"C",7);
        HufNode nodeD=new HufNode(null,null,"D",6);
        HufNode nodeE=new HufNode(null,null,"E",3);

        MyHuffmanList list1=new MyHuffmanList();
        list1.addNode(nodeB,nodeE); //7
        MyHuffmanList list2=new MyHuffmanList();
        list2.addNode(nodeA,nodeD);//11
        MyHuffmanList list3=new MyHuffmanList();
        list3.addNode(nodeC,list1.getFirstNode()); //14
        MyHuffmanList list4=new MyHuffmanList();
        list4.addNode(list2.getFirstNode(),list3.getFirstNode());
        //0走左结点  1走又结点   读到具有字符code的结点再从新循环
        //10:C  00:A 01:D 111:E 110:B
        String code="100010000100111000111001110000111010110111";
        String setCode="";
        StringBuffer strBuffer=new StringBuffer();
        for(int i=0;i<code.length();i++){
            char ch=code.charAt(i);
            HufNode linNode=list4.getNextNode(ch);
            setCode+=ch;
            if(linNode.getCode()!=null&&!linNode.getCode().equals("")){
                strBuffer.append(linNode.getCode());
                list4.setNextNode();
                setCode+=",";
            }
        }
        System.out.println(setCode);
        System.out.println(strBuffer.toString());
        System.out.println("-------------end-------------");
    }
    private static void test_MyHuffmanList_Create(){
        List<HufNode> list=new LinkedList<>();
        HufNode nodeA=new HufNode(null,null,"A",5);
        HufNode nodeB=new HufNode(null,null,"B",4);
        HufNode nodeC=new HufNode(null,null,"C",7);
        HufNode nodeD=new HufNode(null,null,"D",6);
        HufNode nodeE=new HufNode(null,null,"E",3);
        list.add(nodeA);
        list.add(nodeB);
        list.add(nodeC);
        list.add(nodeD);
        list.add(nodeE);
        MyHuffmanList hufList= CreateHuffmanShu.createHufByNode(list);
        String co=hufList.getCode("100010000100111000111001110000111010110111");
        //100010000100111000111001110000111010110111
        //100010000100111000111001110000111010110111
        System.out.println("解码:"+co);
        String setCode=hufList.setCode(co);
        System.out.println("编码:"+setCode);
        System.out.println("-------------end-------------");
    }

    private static void test_MyHuffmanList_error(){
        String s="BT";
        List<HufNode> list=new LinkedList<>();
        HufNode nodeA=new HufNode(null,null,"A",5);
        HufNode nodeB=new HufNode(null,null,"B",4);
        HufNode nodeC=new HufNode(null,null,"C",7);
        HufNode nodeD=new HufNode(null,null,"D",6);
        HufNode nodeE=new HufNode(null,null,"E",3);
        list.add(nodeA);
        list.add(nodeB);
        list.add(nodeC);
        list.add(nodeD);
        list.add(nodeE);
        MyHuffmanList hufList= CreateHuffmanShu.createHufByNode(list);
        String setCode=hufList.setCode(s);
        System.out.println("编码:"+setCode);
        System.out.println("-------------end-------------");
    }
    private static void test_MyHuffmanList(){
        try{
            test_MyHuffmanList_Create();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void test_SetBgColor(){
        System.out.println("ahskjdsdfdfgdfgfghsdfsdffgdfgasdfasdfdgfdfgsdfasdfasdsdfsdfasdasfdfsdfasdasdjksd".length());
       // SetBgColor.setBgColort(210,"D:\\Temp\\img\\test.jpg","D:\\Temp\\img\\ntest.png");
       // SetBgColor.setImage("D:\\Temp\\img\\rangTwo.jpg");
        SetBgColor.createImg("E:\\temp\\testpng.png");
        //   https://blog.csdn.net/STN_LCD/article/details/78629055   JPEG的解码过程

        // SetBgColor.setBgColort(210,"D:\\Temp\\img\\test.jpg","D:\\Temp\\img\\ntest.png");
        //  SetBgColor.setImage("D:\\Temp\\img\\rangTwo.jpg");

       // SetBgColor.setImage("D:/Temp/img/lxyone.jpg","D:/Temp/img/lxyonecolor.txt","D:/Temp/img/lxyonecolor.jpg");
     //   SetBgColor.rgbToYUV("D:/Temp/img/lxyone.jpg","D:/Temp/img/lxyoneyuv.jpg");
      //  SetBgColor.yuvToRGB("D:/Temp/img/lxyoneyuv.jpg","D:/Temp/img/yuvtolxone.jpg");
        // SetBgColor.readImage("D:/Temp/img/lxyone.jpg");

        //SetBgColor.changeImageBig("D:/Temp/img/img20190717/lxyone.jpg","D:/Temp/img/img20190717/changeOnelxyone.jpg");
        try {
            //SetBgColor.dctEach("D:/Temp/img/img20190717/changeOnelxyone.jpg");
           // SetBgColor.dctSaveImg("D:/Temp/img/img20190717/changeOnelxyone.jpg","D:/Temp/img/img20190717/dcttlxyone.jpg");
          //  SetBgColor.imgEblock("D:/Temp/img/img20190717/dct/lxyone.jpg","D:/Temp/img/img20190717/dct/elxyone.jpg");
            //SetBgColor.imgEblock("D:/Temp/img/img20190717/dct/elxyone.jpg");
           // SetBgColor.dctEach("D:/Temp/img/img20190717/dct/elxyone01.jpg",'n','n');
            SetBgColor.jpegStruct("D:/Temp/img/img20190717/elxyone.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void test_XmlAndBpmn(){
        XmlAndBpmn.eachXmlAndBpmn("D:\\Temp\\img\\imgbpmn\\ceshisss.bpmn","D:\\Temp\\img\\imgbpmn\\ceshisss.xml");
    }

    private static void test_ImgLog(){
        ImgLog.paint("E:\\temp\\logimg.png");
    }
    public static void main(String[] args){
        System.out.println("------------------start-------------------");
        test_ImgLog();

        System.out.println("------------------end-------------------");
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
