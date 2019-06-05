package net.connurl;

import net.conn.Conn;
import net.help.MapToObj;
import net.pojo.ZhuFang;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class ShiDa {
    //http://zjc.gznu.edu.cn:888/article/7957
    private String baseUrl;
    private String coding;
    public void shidachuangye(String url,String encoding){
        baseUrl=url;
        coding=encoding;
        int index=10000;
        List<ZhuFang> list=new ArrayList<>();
        while(index<=10000) {
            list.clear();
            Conn con = new Conn();
            try {
                Element body = con.getBodyElement(baseUrl + index, coding);
                List<Element> eleList = new ArrayList<>();
                con.findElesByPTag("div", "style", "border: 1px solid #D6D6D6;margin: 10px;background-color: #fff", "div", eleList, body);
                if (eleList != null && eleList.size() > 0) {
                    for (int i = 0; i < eleList.size(); i++) {
                        if ("center".equals(eleList.get(i).attributes().get("align"))) {
                            String title = eleList.get(i).text();
                            System.out.println(index+":"+title);
                            if(title.contains("创业")){
                                ZhuFang zhuFang=new ZhuFang();
                                zhuFang.setTitle(title);
                                zhuFang.setUrlType(baseUrl+index);
                                zhuFang.setFrom("师大");
                                zhuFang.setPerType("创业");
                                list.add(zhuFang);
                            }
                        }
                    }
                    try {
                        Thread.sleep(2*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("end");
            } catch (Exception e) {
                e.printStackTrace();
            }
            HelpDb.saveJops(list);
            con.close();
            index++;
        }
    }
}
/**
 * 2069:HR：招聘时 这9种人我们坚决不要
 * 2271:毕业生求职面试的六大自杀式回答
 * 2742:面试小技巧：三分钟让面试官眼前一亮
 * 2948:面试技巧：面试自我介绍30秒讲3个要点
 * 7939:面试时说话很重要 绝不能说这12句话
 *
 * **/
