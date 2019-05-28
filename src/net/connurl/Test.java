package net.connurl;

import net.conn.CallBack;
import net.conn.Conn;
import net.help.FirstNodeTree;
import net.help.node.HtmlNodeOne;
import net.help.node.HtmlNodeTwo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

public class Test {
    public static void testt() throws Exception {
        Conn con=new Conn();
        StringBuffer buf=new StringBuffer();
        con.getConne("http://www.duyun.ccoo.cn/post/fangwu/chuzu/list-0-0-0-0-0-2-1-0-0-0-0-0.html", "UTF-8", new CallBack() {
            @Override
            public void callBackOne(BufferedReader reader) throws IOException {
                String line=null;
                int index=0;
                while((line=reader.readLine())!=null){
                    buf.append(line.replaceAll(" +"," "));
                   // System.out.println(line.replaceAll(" +"," "));
                }
            }
        });
        con.close();
        Document doc= Jsoup.parse(buf.toString());
        Element body=doc.body();
        Elements childs= body.children();
        for(int i=0;i<childs.size();i++){
            Element ele=childs.get(i);
            System.out.println(ele.tag().getName()+":"+ele.text()+":"+ele.children().size());
        }

        System.out.println("---");
    }
}
