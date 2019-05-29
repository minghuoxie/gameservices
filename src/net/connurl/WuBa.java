package net.connurl;

import net.conn.CallBack;
import net.conn.Conn;
import net.pojo.ZhuFang;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

public class WuBa {

    //https://gy.58.com/zufang/?key=租房信息&cmcskey=租房信息&final=1&jump=1&sourcetype=11&PGTID=0d100000-007d-f92b-8baa-8c86b6699175&ClickID=2  58贵阳在线住房
    private Conn con;
    private  StringBuffer html=new StringBuffer();
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
        String scTxt="";
        for(int i=0;i<head.children().size();i++){
            if("script".equals(head.children().get(i).tagName())){
                Element sc=head.children().get(i);
                scTxt=sc.childNodes().get(0).attributes().get("data")+"";
                if(scTxt.startsWith("!function(w,d)")){
                    break;
                }
            }
        }
        html.append("<!DOCTYPE html>");
        html.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\"/>\r\n<script>");
        html.append(scTxt);
        html.append("</script>\r\n</head><body>");
        return body;
    }

    //https://www.cnblogs.com/q1ang/p/10176936.html   特殊的编码
    public void zhufan(){
        con=new Conn();
        try {
            Element body=wubaZhuFang();
            Element hoseList=body.getElementsByClass("house-list").get(0);
            for(int i=0;i<hoseList.children().size();i++){
                if("li".equals(hoseList.children().get(i).tagName())) {
                    Element li=hoseList.children().get(i);
                    ZhuFang zhuFang=new ZhuFang();
                    String price = con.getTextByTagAndAttr("div", "class", "money", li);
                    String title = con.getTextByTagAndAttr("a", "class", "strongbox", li);
                    String room = con.getTextByTagAndAttr("p", "class", "room", li);
                    String addr = con.getTextByTagAndAttr("p", "class", "infor", li);
                    String urlType=con.getAttrValByTagAndAttr("a", "class", "strongbox","href", li);
                    zhuFang.setTitle(title);
                    zhuFang.setPrice(price);
                    zhuFang.setAddr(addr);
                    zhuFang.setPerType("无");
                    zhuFang.setContent(room);
                    zhuFang.setFrom("58");
                    zhuFang.setUrlType(urlType);
                    html.append("<div class=\"strongbox\">");
                    html.append(zhuFang.getPrice());
                    html.append("</div></body></html>");
//                    OutputStreamWriter writer=
//                            new OutputStreamWriter(new FileOutputStream("D:\\yunzhi\\lin\\wuba.html"),"utf-8");
//                    writer.write(html.toString());
//                    writer.close();

                   BufferedReader reader=
                            new BufferedReader(new InputStreamReader(new FileInputStream("D:\\yunzhi\\lin\\wuba.html")));
                   String txtLine=null;
                   while((txtLine=reader.readLine())!=null){
                       System.out.println(txtLine);
                   }
                   reader.close();
                   System.out.println(zhuFang);
                   return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        con.close();
    }
}
