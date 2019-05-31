package net.connurl;

import net.conn.CallBack;
import net.conn.Conn;
import net.pojo.ZhuFang;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
}
