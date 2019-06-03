package net.connurl;

import net.conn.CallBack;
import net.conn.Conn;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GoMain {
    /**
     * 要爬的主网页
     * */
    public void go(){
        try {
           // new HuiShui("http://www.huishui.ccoo.cn","utf-8").huishuihomechuzhu();  惠水住房
           // new WuBa().zhuFan();  58住房
            new HuiShui("http://www.huishui.ccoo.cn","gb2312").zhaopin();

            //-------------lin
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
