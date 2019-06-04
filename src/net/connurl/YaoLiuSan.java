package net.connurl;

import net.conn.Conn;
import net.help.MapToObj;
import net.help.Time;
import net.pojo.ZhuFang;
import net.pojo.ZhuFangChild;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class YaoLiuSan {
// http://www.163gz.com/gzzp8/zkxx/index.shtml
    // http://www.163gz.com/gzzp8/zkxx/
    private String baseUrl;
    private String coding;

    public void zhaoPin(String url,String enCoding){
       baseUrl=url;
       coding=enCoding;
        String useUrl="index.shtml";
        boolean go=true;
        int index=0;

        List<ZhuFangChild> list=new ArrayList<>();
        while(go) {
            if(index>=10){
                break;
            }
            Conn con = new Conn();
            try {
                Element body = con.getBodyElement(baseUrl+useUrl, coding);
                Element nextPage=con.findEleByTagAndAttrAndTxt("a","class","style335","下一页",body);
                if(nextPage!=null&&!useUrl.equals(nextPage.attributes().get("href"))){
                    useUrl=nextPage.attributes().get("href");
                }else{
                    go=false;
                }
                List<Element> eleList = new ArrayList<>();
                con.findElesByPTag("span", "class", "style331", "a", eleList, body);
                if (eleList != null && eleList.size() > 0) {
                    for (Element a : eleList) {
                        String title = a.text().replaceAll(" +", "");
                        String[] titles = title.split("・");
                        System.out.println(titles[0]+":::"+titles[1]);
                        if (MapToObj.isMapTitleYaoLiuSan(titles[1])) {
                            ZhuFangChild zhuFang = new ZhuFangChild();
                            zhuFang.setTitle(titles[1]);
                            String time = Time.getTime("yyyy") + "-" + titles[0];
                            zhuFang.setFrom("163");
                            zhuFang.setPerType("人事");
                            zhuFang.setUrlType(a.attributes().get("href"));
                            list.add(zhuFang);
                            zhuFang.setAddDate(time);
                            if(!Time.isTrue(time.split("-")[1])){
                                go=false;
                            }
                        }
                    }
                }
                index=0;
            } catch (Exception e) {
                e.printStackTrace();
                index++;
            }
            con.close();
            HelpDb.saveJopsTwo(list);
            list.clear();
        }
    }
}
