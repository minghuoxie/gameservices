package net.connurl;

import net.conn.CallBack;
import net.conn.Conn;
import net.help.FirstNodeTree;

import java.io.BufferedReader;
import java.io.IOException;

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
                    buf.append(line);
                }
            }
        });
        con.close();
        FirstNodeTree treeNode=new FirstNodeTree(buf);
        treeNode.setNode();
    }
}
