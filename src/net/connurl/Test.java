package net.connurl;

import net.conn.CallBack;
import net.conn.Conn;
import net.help.FirstNodeTree;
import net.help.node.HtmlNodeOne;

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
                }
            }
        });
        con.close();
//
//        BufferedWriter writer=
//                new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Users\\dell\\Desktop\\linhtml.txt")));
//        writer.write(buf.toString());
//        writer.close();

        HtmlNodeOne treeNode=new HtmlNodeOne(buf);
        treeNode.setNode();
    }
}
