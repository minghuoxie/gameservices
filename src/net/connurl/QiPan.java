package net.connurl;

import net.conn.CallBack;
import net.conn.Conn;
import net.dbconnect.Db;
import net.dbconnect.sqlstr.SqlHuiShui;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.BufferedReader;
import java.io.IOException;

public class QiPan {
    public void qiPan(){
        for (int in = 1; in <= 176; in++) {
            String furl="www.baidu.com_"+in+".html";
            System.out.println(furl);
            StringBuffer buf = new StringBuffer();
            Conn con = new Conn();
            try {
                con.getConne(furl, "UTF-8", new CallBack() {
                    @Override
                    public void callBackOne(BufferedReader reader) throws IOException {
                        String lin = null;
                        while ((lin = reader.readLine()) != null) {
                            buf.append(lin.replaceAll(" +", " "));
                        }
                    }
                });
                con.close();
                Db db = new Db();
                Document doc = Jsoup.parse(buf.toString());
                Element body = doc.body();
                Elements eles = body.getElementById("colList").getElementsByTag("ul");
                for (int i = 0; i < eles.size(); i++) {
                    Elements childs = eles.get(i).children();
                    int len = 0;
                    if (childs != null && (len = childs.size()) > 0) {
                        for (int j = 0; j < len; j++) {
                            String tagName = childs.get(j).tagName();
                            if ("li".equals(tagName)) {
                                Element a = childs.get(j).children().get(0);
                                String url = a.attributes().get("href");
                                String title = a.children().get(2).toString();
                                boolean isTrue = filter(title.replaceAll(" +", ""));
                                System.out.println(isTrue + ":::" + title);
                                //设置filter
                                if (isTrue) {
                                    //SqlHuiShui
                                    int num = db.selectCount(SqlHuiShui.findQiPanNum, new Object[]{"img", title});
                                    if (num == 0) {
                                        db.insertDbOne(SqlHuiShui.insertQiPan, new Object[]{"img", title, url});
                                    }
                                }
                            }
                        }
                    }
                }
                db.closeConn();
            }catch (Exception e){
                in=in-1;
                e.printStackTrace();
            }
            try {
                Thread.sleep(10*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void qiPanTwo(){
        String preUrl="www.baidu.com";
        for(int ih=1;ih<=5;ih++){
            String nextUrl="/t0"+ih+"/index.html";
            int page=18;
            boolean pageFirst=true;
            String uq="";
            String uh="";

            while(page>=1) {
                StringBuffer buf = new StringBuffer();
                Conn con = new Conn();
                try {
                    con.getConne(preUrl + nextUrl, "UTF-8", new CallBack() {
                        @Override
                        public void callBackOne(BufferedReader reader) throws IOException {
                            String lin = null;
                            while ((lin = reader.readLine()) != null) {
                                buf.append(lin.replaceAll(" +", " "));
                            }
                        }
                    });
                    con.close();
                    Db db = new Db();
                    Document doc = Jsoup.parse(buf.toString());
                    Element body = doc.body();
                    Elements eles = body.getElementById("colList").getElementsByTag("ul");
                    if(pageFirst) {
                        Element nextPags = body.getElementsByClass("pagination").get(0);
                        Element nextPa = nextPags.children().get(3);
                        nextUrl = nextPa.attributes().get("href");
                        String[] one=nextUrl.split("\\.");
                        String[] two=one[0].split("_");
                        page=Integer.parseInt(two[1]);
                        pageFirst=false;
                        uq=two[0]+"_";
                        uh=".html";
                    }else{
                        if(--page>=1){
                            nextUrl=uq+page+uh;
                        }else{
                            break;
                        }
                    }
                    for (int i = 0; i < eles.size(); i++) {
                        Elements childs = eles.get(i).children();
                        int len = 0;
                        if (childs != null && (len = childs.size()) > 0) {
                            for (int j = 0; j < len; j++) {
                                String tagName = childs.get(j).tagName();
                                if ("li".equals(tagName)) {
                                    Element a = childs.get(j).children().get(0);
                                    String url = a.attributes().get("href");
                                    String title = a.children().get(2).toString();
                                    boolean isTrue=filter(title.replaceAll(" +", ""));
                                    System.out.println(isTrue+":::"+title);
                                    //设置filter
                                    if (isTrue) {
                                        //SqlHuiShui
                                        int num = db.selectCount(SqlHuiShui.findQiPanNum, new Object[]{"txt", title});
                                        if (num == 0) {
                                            db.insertDbOne(SqlHuiShui.insertQiPan, new Object[]{"txt", title, url});
                                        }
                                    }
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println(preUrl+nextUrl);
                try {
                    Thread.sleep(10*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


        private boolean filter (String str){
            return true;
        }

}
