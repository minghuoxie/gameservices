package net.connurl;

import net.conn.CallBack;
import net.conn.Conn;
import net.help.MapToObj;
import net.help.Time;
import net.pojo.ZhuFang;
import net.pojo.ZhuFangChild;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WuBa {

    //https://gy.58.com/zufang/?key=租房信息&cmcskey=租房信息&final=1&jump=1&sourcetype=11&PGTID=0d100000-007d-f92b-8baa-8c86b6699175&ClickID=2  58贵阳在线住房
    private Conn con;
    private StringBuffer base64_Str=new StringBuffer();
    private Element wubaZhuFang() throws Exception {
        StringBuffer buf=new StringBuffer();
        con.getConne("https://gy.58.com/zufang/?key=租房信息&cmcskey=租房信息&final=1&jump=1&sourcetype=11&PGTID=0d100000-007d-f92b-8baa-8c86b6699175&ClickID=2", "UTF-8", new CallBack() {
            @Override
            public void callBackOne(BufferedReader reader) throws IOException {
                String line=null;
                while((line=reader.readLine())!=null){
                    buf.append(line);
                }
            }
        });
        Document doc = Jsoup.parse(buf.toString());
        Element body = doc.body();
        Element head=doc.head();
        String textScrip="";
        for(int i=0;i<head.children().size();i++){
            if("script".equals(head.children().get(i).tagName())){
                Element sc=head.children().get(i);
                textScrip=sc.childNodes().get(0).attributes().get("data")+"";
                if(textScrip.startsWith("!function(w,d)")){
                    break;
                }
            }
        }
        boolean isAdd=false;
        for(int i=0;i<textScrip.length();i++){
            char ch=textScrip.charAt(i);
            if(ch=='b'&&(i+7)<textScrip.length()&&"base64,".equals(textScrip.substring(i,i+7))){
                isAdd=true;
                i=i+6;
                continue;
            }else if(ch=='\''&&(i+2)<textScrip.length()&&"')".equals(textScrip.substring(i,i+2))){
                break;
            }
            if(isAdd){
                base64_Str.append(ch);
            }
        }
        return body;
    }
    //https://www.cnblogs.com/q1ang/p/10176936.html   特殊的编码
    //https://blog.csdn.net/xx117501/article/details/86512803
    public void zhufan(){
        List<ZhuFang> list=new ArrayList<>();
        String perType="无";
        String from="58";
        int maxPrice=2000;
        con=new Conn();
        try {
            Element body=wubaZhuFang();
            Element hoseList=body.getElementsByClass("house-list").get(0);
            for(int i=0;i<hoseList.children().size();i++){
                if("li".equals(hoseList.children().get(i).tagName())) {
                    Element li=hoseList.children().get(i);
                    ZhuFang zhuFang=new ZhuFang();
                    String price = coding(con.getTextByTagAndAttr("div", "class", "money", li));
                    zhuFang.setPrice(price);

                    String title = coding(con.getTextByTagAndAttr("a", "class", "strongbox", li));
                    zhuFang.setTitle(title);

                    String room =coding(con.getTextByTagAndAttr("p", "class", "room", li));
                    zhuFang.setContent(room);

                    String addr = coding(con.getTextByTagAndAttr("p", "class", "infor", li));
                    zhuFang.setAddr(addr);

                    String urlType=con.getAttrValByTagAndAttr("a", "class", "strongbox","href", li);
                    zhuFang.setUrlType(urlType);

                    zhuFang.setPerType(perType);
                    zhuFang.setFrom(from);
                   Thread.sleep(2*1000);
                    list.add(zhuFang);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        con.close();
        HelpDb.saveZhuFan(list,perType,maxPrice,from);
    }

    public void zhuFan(){
        List<ZhuFang> list=new ArrayList<>();
        String perType="无";
        String from="58";
        int maxPrice=2000;
        con=new Conn();
        try {
            Element body=wubaZhuFang();
            Element hoseList=body.getElementsByClass("house-list").get(0);
            Map<String,String> cmap=getCoding();
            for(int i=0;i<hoseList.children().size();i++){
                if("li".equals(hoseList.children().get(i).tagName())) {
                    Element li=hoseList.children().get(i);
                    ZhuFang zhuFang=new ZhuFang();
                    String price =getCoding(con.getTextByTagAndAttr("div", "class", "money", li),cmap);
                    zhuFang.setPrice(price);

                    String title =getCoding(con.getTextByTagAndAttr("a", "class", "strongbox", li),cmap);
                    zhuFang.setTitle(title);

                    String room =getCoding(con.getTextByTagAndAttr("p", "class", "room", li),cmap);
                    zhuFang.setContent(room);

                    String addr =getCoding(con.getTextByTagAndAttr("p", "class", "infor", li),cmap);
                    zhuFang.setAddr(addr);

                    String urlType=con.getAttrValByTagAndAttr("a", "class", "strongbox","href", li);
                    zhuFang.setUrlType(urlType);

                    zhuFang.setPerType(perType);
                    zhuFang.setFrom(from);
                    Thread.sleep(2*1000);
                    list.add(zhuFang);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        con.close();
        HelpDb.saveZhuFan(list,perType,maxPrice,from);
    }

    private String getCoding(String codeStr,Map<String,String> map){
        String reStr="";
        for(int i=0;i<codeStr.length();i++){
            char ch=codeStr.charAt(i);
            String chs=((int)ch)+"";
            if(map.containsKey(chs)){
                reStr+=map.get(chs);
            }else{
                reStr+=ch;
            }
        }
        return reStr;
    }

    private Map<String,String> getCoding(){
        String[] arg=new String[]{"python","D:/yunzhi/pypro/woff.py",base64_Str.toString()};
        Process pr=null;
        BufferedReader in=null;
        String reTxt="";
        try{
            pr=Runtime.getRuntime().exec(arg);
            //in=new BufferedReader(new InputStreamReader(pr.getErrorStream()));
            in=new BufferedReader(new InputStreamReader(pr.getInputStream(),"gb2312"));
            String line=null;
            while((line=in.readLine())!=null){
                reTxt+=line;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(in!=null){
                    in.close();
                }
                if(pr!=null) {
                    pr.waitFor();
                }
            }catch (Exception e){

            }
        }
        //map::::{38006: 'glyph00001', 38287: 'glyph00007', 39228: 'glyph00005', 39499: 'glyph00006', 40506: 'glyph00008', 40611: 'glyph00004', 40804: 'glyph00010', 40850: 'glyph00003', 40868: 'glyph00002', 40869: 'glyph00009'}
        //
        System.out.println("map::::"+reTxt);
        if(!reTxt.equals("")){
            reTxt=reTxt.substring(1,reTxt.length()-1);
            reTxt=reTxt.replaceAll("'","");
            Map<String,String> map=new HashMap<>();
            String[] arrs=reTxt.split(",");
            for(String s:arrs){
                String key=s.split(":")[0].trim();
                String vals=s.split(":")[1].trim();
                String val="";
                boolean isAdd=false;
                for(int i=0;i<vals.length();i++){
                    char c=vals.charAt(i);
                    if(c>='1'&&c<='9'){
                        isAdd=true;
                    }
                    if(isAdd){
                        val+=c;
                    }
                }
                if(!val.equals("")) {
                    map.put(key, (Integer.parseInt(val)-1)+"");
                }
            }
            return map;
        }
        return null;
    }

    private String coding(String txt) throws UnsupportedEncodingException {
        String roomNew="";
        for(int j=0;j<txt.length();j++){
            if(((int)(txt.charAt(j)))!=160){
                roomNew+=txt.charAt(j);
            }
        }
        String reTxt="";
        String[] arg=new String[]{"python","D:/yunzhi/pypro/example.py",base64_Str.toString(),roomNew};
        Process pr=null;
        BufferedReader in=null;
        try{
            pr=Runtime.getRuntime().exec(arg);
          //in=new BufferedReader(new InputStreamReader(pr.getErrorStream()));
          in=new BufferedReader(new InputStreamReader(pr.getInputStream(),"gb2312"));
            String line=null;
            while((line=in.readLine())!=null){
                reTxt+=line;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(in!=null){
                    in.close();
                }
                if(pr!=null) {
                    pr.waitFor();
                }
            }catch (Exception e){

            }
        }
        return new String(reTxt.getBytes(),"utf-8");
    }

    //58工作
    //https://gy.58.com/ruanjiangong/?PGTID=0d202408-007d-fe8e-a7fc-34f350106fc5&ClickID=2

    public void zhaopin(String url,String enCoding){
        Conn con=new Conn();
        List<ZhuFang> list=new ArrayList<>();
        try {
            Element body=con.getBodyElement(url,enCoding);
            Elements eleLis= body.getElementById("list_con").children();
            if(eleLis!=null&&eleLis.size()>0){
                for(int i=0;i<eleLis.size();i++){
                    Element li=eleLis.get(i);
                    //(String pTagName,String pAttrName,String pAttrVal,String tagName,String muName,char type,Element root)
                    String title=con.getAttrValByTag("div","class","job_name clearfix","a",null,'C',li);
                    System.out.println("--"+title);
                    if(MapToObj.isMapTitle(title)){
                        ZhuFang zhuFang=new ZhuFang();
                        zhuFang.setTitle(title);
                        zhuFang.setPerType("招聘");
                        zhuFang.setFrom("58");

                        //item_con job_title
                        zhuFang.setUrlType(con.getAttrValByTag("div","class","job_name clearfix","a","href",'K',li));
                        zhuFang.setPrice(con.getAttrValByTag("div","class","item_con job_title","p",null,'C',li));
                        zhuFang.setAddr(con.getAttrValByTag("div","class","comp_name","a",null,'C',li));
                        String content="";
                        content+=con.getTextByTagAndAttr("div","class","job_wel clearfix",li);
                        content+=con.getTextByTagAndAttr("p","class","job_require",li);
                        zhuFang.setContent(content);
                        list.add(zhuFang);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        con.close();
        HelpDb.saveJops(list);
    }

    /****************************前程无忧********************************/
    public void qiancheng(String url,String enCoding){
        Conn con=new Conn();
        List<Element> eleLists=new ArrayList<>();
        try {
            Element body=con.getBodyElement(url,enCoding);
            Element eleList=body.getElementsByClass("cn hlist").first();

            //(String pTagName,String pAttrName,String pAttrVal,String tagName,List<Element> eleList,Element root)
            con.findElesByPTag("div","class","e","a",eleLists,eleList);
            if(eleLists!=null&&eleLists.size()>0){
                for(Element e:eleLists){
                    if(MapToObj.isMapTitle(e.text().trim())){
                        Thread.sleep(10*1000);
                        qianchengZhaoPing(e.attributes().get("href"),enCoding);
                    }
                }
            }
            System.out.println("end");
        } catch (Exception e) {
            e.printStackTrace();
        }
        con.close();
    }

    private void qianchengZhaoPing(String url,String enCoding){
        Conn con=new Conn();
        List<ZhuFangChild> listZhu=new ArrayList<>();
        try {
            Element body=con.getBodyElement(url,enCoding);
            Elements eleList=body.getElementById("resultList").children();
            for(Element e:eleList){
                String title=con.getAttrValByTag("p","class","t1 ","a",null,'C',e);
                if(MapToObj.isMapTitle(title)){
                    ZhuFangChild zhuFang=new ZhuFangChild();
                    zhuFang.setTitle(title);
                    zhuFang.setPerType("招聘");
                    zhuFang.setFrom("前程");

                    zhuFang.setContent(con.getAttrValByTag("span","class","t2","a",null,'C',e));
                    zhuFang.setUrlType(con.getAttrValByTag("span","class","t2","a","href",'K',e));
                    zhuFang.setAddr(con.getTextByTagAndAttr("span","class","t3",e));
                    zhuFang.setPrice(con.getTextByTagAndAttr("span","class","t4",e));
                    String time = Time.getTime("yyyy") + "-" + con.getTextByTagAndAttr("span","class","t5",e);
                    zhuFang.setAddDate(time);
                    System.out.println(zhuFang);
                    listZhu.add(zhuFang);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        con.close();
        HelpDb.saveJopsTwo(listZhu);
    }


    /*******************************智联招聘************************************/
    public void zhilianZhaoPin(String url,String enCoding){
        Conn con=new Conn();
        try {
            Element body=con.getBodyElement(url,enCoding);
            List<String> strList=new ArrayList<>();
            strList.add("开发");
            strList.add("android");
            strList.add("Android");
            strList.add("Python");
            strList.add("python");
            strList.add("java");
            strList.add("Java");
            List<Element> needEle=new ArrayList<>();
            con.findElesByText("a",strList,needEle,body);

            System.out.println("----");
        } catch (Exception e) {
            e.printStackTrace();
        }
        con.close();
    }

    /**
     * js分析
     * index.web.31be5b.js
     *      class:zp-jobNavigater__pop--href
     *      e.exports = {
     *         apiDomain: "https://fe-api.zhaopin.com",
     *         searchPageUrl: "https://sou.zhaopin.com",
     *         homePageNav: "https://www.zhaopin.com/?flag=1",
     *         iPageUrl: "https://i.zhaopin.com",
     *         jobSearchNav: "https://sou.zhaopin.com",
     *         campusRecruitNav: "//xiaoyuan.zhaopin.com",
     *         highpinNav: "https://www.highpin.cn",
     *         cepingNav: "https://td.zhaopin.com/",
     *         article: "http://article.zhaopin.com",
     *         industrySeek: "https://www.zhaopin.com/jobseeker/index_industry.html",
     *         zhaopin: "https://www.zhaopin.com",
     *         sou: "https://sou.zhaopin.com",
     *         jianliCenter: "https://my.zhaopin.com",
     *         resumeDownload: "https://i.zhaopin.com",
     *         resumeUpload: "https://i.zhaopin.com",
     *         newHomePageNav: "https://www.zhaopin.com",
     *         shangbanNav: "https://zq.zhaopin.com",
     *         overseaNav: "https://overseas.zhaopin.com/",
     *         enterpriseRegisterUrl: "https://rd5.zhaopin.com/custom/register?za=2&ps=1",
     *         statSdkUrl: "//common-bucket.zhaopin.cn/js/zpfe-stat-sdk/zpfe-stat-sdk-latest.js",
     *         zpPassportWidgetUrl: "//common-bucket.zhaopin.cn/js/zp-passport-widget/zp-passport-widget-latest.js",
     *         stage: "PRODUCTION"
     *     }
     *
     *
     * https://www.cnblogs.com/davidwang456/articles/8693050.html
     * **/
}
