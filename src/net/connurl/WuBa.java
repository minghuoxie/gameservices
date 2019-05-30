package net.connurl;

import net.conn.CallBack;
import net.conn.Conn;
import net.pojo.ZhuFang;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;

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
//        html.append("<!DOCTYPE html>");
//        html.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"/>\r\n<script>");
//        html.append(scTxt);
//        html.append("</script>\r\n</head><body>");
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

                    zhuFang.setPerType("无");
                    zhuFang.setFrom("58");
                   Thread.sleep(2*1000);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        con.close();
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
