package net.connurl;

import net.conn.CallBack;
import net.conn.Conn;
import net.dbconnect.Db;
import net.dbconnect.sqlstr.SqlHuiShui;
import net.help.Time;
import net.pojo.ZhuFang;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class DouYun {
    //http://www.duyun.ccoo.cn/post/fangwu/chuzu/
    //都匀的在线住房
    public void douYunFang(){
        Conn con=new Conn();
        Element body=null;
        int index=0;
        String baseUrl="http://www.duyun.ccoo.cn";
        String nextUrl="/post/fangwu/chuzu/";

        try {
            while(index<3) {
                List<ZhuFang> listZhuFan=new ArrayList<>();
                body = con.getBodyElement(baseUrl+nextUrl, "utf-8");
                Elements page = body.getElementById("page_x").children();
                Elements zfList = body.getElementsByClass("zf_list").get(0).children();
                for (int i = 0; i < page.size(); i++) {
                    if("a".equals(page.get(i).tagName())&&"下一页".equals(page.get(i).text())){
                        nextUrl=page.get(i).attributes().get("href");
                    }
                }
                for (int j = 0; j < zfList.size(); j++) {
                    ZhuFang zhufang = new ZhuFang();
                    Elements title=zfList.get(j).getElementsByTag("h3");
                    Elements content=zfList.get(j).getElementsByClass("fccc");
                    Elements addr=zfList.get(j).getElementsByClass("addr");
                    Elements room=zfList.get(j).getElementsByClass("room");
                    Elements orice=zfList.get(j).getElementsByClass("espanx");

                    zhufang.setUrlType(baseUrl+title.get(0).children().get(0).attributes().get("href"));
                    zhufang.setPerType(title.get(0).children().get(0).children().get(0).text());
                    zhufang.setTitle(title.get(0).children().get(0).text().replaceAll(" +",""));
                    String cont="";
                    for(int ci=0;ci<content.get(0).children().size();ci++){
                        cont+=content.get(0).children().get(ci).text();
                    }
                    cont+=room.get(0).children().get(0).text();
                    zhufang.setContent(cont);
                    zhufang.setAddr(addr.get(0).text().replaceAll(" +",""));
                    try {
                        zhufang.setPrice(orice.get(0).text());
                    }catch (Exception e){
                        zhufang.setPrice("[面议]");
                    }
                    listZhuFan.add(zhufang);
                }
                Db db=new Db();
                if(listZhuFan!=null&&listZhuFan.size()>0){
                    for(ZhuFang z:listZhuFan){
                        String priStr=z.getPrice();
                        String numStr="";
                        for(int i=0;i<priStr.length();i++){
                            char c=priStr.charAt(i);
                            if(c>='0'&&c<='9'){
                                numStr+=c;
                            }
                        }
                        if(z.getPerType().equals("[个人]")&&numStr!=null&&numStr.length()>0&&Integer.parseInt(numStr)<=700){
                            int num=db.selectCount(SqlHuiShui.findNum,new Object[]{z.getTitle(),z.getUrlType()});
                            if(num==0){
                                db.insertDbOne(SqlHuiShui.insertDats,new Object[]{z.getTitle(),z.getPerType(),z.getPrice(),z.getUrlType(),z.getAddr(),z.getContent(),"都匀", Time.getTime("yyyy-MM-dd")});
                            }
                        }
                    }
                }
                db.closeConn();
                index++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            con.close();
        }
    }

}
