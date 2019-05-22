package net.test;

import net.conn.Stant;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TestOne {
    /**
     * java.net包的练习，爬虫的基础
     * */
    private BufferedReader reader=null;
    private HttpURLConnection conn=null;
    public void testOne(){
        try{
            //将字符串转为URL
            URL url=new URL("http://www.huishui.ccoo.cn/post/job/");
            //打开连接
            conn=(HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            //设置请i求的参数
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36");
            //conn.setRequestProperty("Accept-Charset", "UTF-8");
            //conn.setRequestProperty("Connection", "Keep-Alive");
            //建立连接
            conn.connect();
//            System.out.println("getResponseMessage:"+conn.getResponseMessage());  OK
//            System.out.println("getResponseCode:"+conn.getResponseCode()); 200
//            System.out.println("getContentEncoding:"+conn.getContentEncoding()); null
//            System.out.println("getContentType:"+conn.getContentType()); text/html
            if(200==conn.getResponseCode()) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), Stant.encoding));
                String line = null;
                int index = 0;
                while ((line = reader.readLine()) != null) {
                    System.out.println((++index) + line);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try {
                if (reader != null) {
                    reader.close();
                }
                conn.disconnect();
            }catch (Exception e){ }
        }
    }

    private void printHeaderstwo(){
        for (int i = 0;; i++) {
            String mine=conn.getHeaderField(i);
            if(mine==null){
                break;
            }
            System.out.println(conn.getHeaderFieldKey(i)+"::"+mine);
        }
    }

    private void printHeaders(){
        Map<String, List<String>> map= conn.getHeaderFields();
        if(!map.isEmpty()){
            Iterator ite=map.keySet().iterator();
            while(ite.hasNext()){
                String key=ite.next()+"";
                System.out.println(key+"::::"+map.get(key));
            }
        }
    }
}
