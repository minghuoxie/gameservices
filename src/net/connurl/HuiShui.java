package net.connurl;

import net.conn.CallBack;
import net.conn.Conn;
import net.dbconnect.Db;
import net.dbconnect.sqlstr.SqlHuiShui;
import net.help.Time;
import net.pojo.ZhuFang;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class HuiShui {
    private String huishuiUrl;
    private String huishuiEnc;
    public HuiShui( String huishuiUrl,String huishuiEnc){
        this.huishuiUrl=huishuiUrl;
        this.huishuiEnc=huishuiEnc;
    }
    //惠水在线 房屋出租
    // /post/fangwu/chuzu/list-0-0-0-1-0-0-1.html
    public void huishuihomechuzhu()throws Exception{
        List<ZhuFang> listZhuFan=new ArrayList<>();
        Conn con=new Conn();
        for(int page=1;page<=3;page++) {
            con.getConne(huishuiUrl + "/post/fangwu/chuzu/list-0-0-0-1-0-0-" + page + ".html", huishuiEnc, new CallBack() {
                @Override
                public void callBackOne(BufferedReader reader) throws IOException {
                    StringBuffer buf = new StringBuffer();
                    String line = null;
                    boolean isAdd = false;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                        if (line.contains("<ul class=\"zf_list\">")) {
                            isAdd = true;
                        }
                        if (isAdd) {
                            buf.append(line);
                            if (line.contains("</ul>")) {
                                isAdd = false;
                            }
                        }
                    }

                    for (String zuStr : buf.toString().split("</li>")) {
                        ZhuFang zhufang = new ZhuFang();
                        zuStr = zuStr.replaceAll(" +", " ");
                        for (int i = 0; i < zuStr.length(); i++) {
                            char ch = zuStr.charAt(i);
                            String addrUrl = "";
                            String title = "";
                            String peoType = "";
                            String price = "";
                            String addr = "";
                            String content = "";
                            if (ch == '<' && (i + 9 < zuStr.length()) && "<a href=\"".equals(zuStr.substring(i, i + 9))) {
                                //获取url
                                for (int j = i + 9; j < zuStr.length(); j++) {
                                    if (zuStr.charAt(j) == '"') {
                                        i = j;
                                        break;
                                    }
                                    addrUrl += zuStr.charAt(j);
                                }
                            } else if (ch == 'a' && (i + 5 < zuStr.length()) && "alt=\"".equals(zuStr.substring(i, i + 5))) {
                                //获取title
                                for (int j = i + 5; j < zuStr.length(); j++) {
                                    if (zuStr.charAt(j) == '"') {
                                        i = j;
                                        break;
                                    }
                                    title += zuStr.charAt(j);
                                }
                            } else if (ch == '<' && (i + 21 < zuStr.length()) && "<span class=\"espan1\">".equals(zuStr.substring(i, i + 21))) {
                                for (int j = i + 21; j < zuStr.length(); j++) {
                                    if (zuStr.charAt(j) == '<') {
                                        i = j;
                                        break;
                                    }
                                    peoType += zuStr.charAt(j);
                                }
                            } else if (ch == '<' && (i + 21 < zuStr.length()) && "<span class=\"espan3\">".equals(zuStr.substring(i, i + 21))) {
                                for (int j = i + 21; j < zuStr.length(); j++) {
                                    if (zuStr.charAt(j) == '<' && (j + 6 < zuStr.length()) && "</div>".equals(zuStr.substring(j, j + 6))) {
                                        i = j + 6;
                                        break;
                                    }
                                    price += zuStr.charAt(j);
                                }
                            } else if (ch == '<' && (i + 18 < zuStr.length()) && "<div class=\"addr\">".equals(zuStr.substring(i, i + 18))) {
                                for (int j = i + 18; j < zuStr.length(); j++) {
                                    if (zuStr.charAt(j) == '<') {
                                        i = j;
                                        break;
                                    }
                                    addr += zuStr.charAt(j);
                                }
                            } else if (ch == '<' && (i + 18 < zuStr.length()) && "<div class=\"fccc\">".equals(zuStr.substring(i, i + 18))) {
                                for (int j = i + 18; j < zuStr.length(); j++) {
                                    if (zuStr.charAt(j) == '<' && (j + 6 < zuStr.length()) && "</div>".equals(zuStr.substring(j, j + 6))) {
                                        i = j + 6;
                                        break;
                                    }
                                    content += zuStr.charAt(j);
                                }
                            }
                            //<div class="fccc">

                            if (zhufang.getUrlType().equals("") && !addrUrl.equals("")) {
                                zhufang.setUrlType(huishuiUrl + addrUrl);
                            }
                            if (zhufang.getTitle().equals("") && !title.equals("")) {
                                zhufang.setTitle(title);
                            }
                            if (zhufang.getPerType().equals("") && !peoType.equals("")) {
                                zhufang.setPerType(peoType);
                            }
                            if (zhufang.getPrice().equals("") && !price.equals("")) {
                                zhufang.setPrice(price.replace("</span>", ""));
                            }
                            if (zhufang.getAddr().equals("") && !addr.equals("")) {
                                zhufang.setAddr(addr);
                            }
                            if (zhufang.getContent().equals("") && !content.equals("")) {
//                            String conStr="";
//                            boolean isConAdd=true;
//                            for(int ci=0;ci<content.length();ci++){
//                                if(content.charAt(ci)=='>'){
//                                    isConAdd=true;
//                                }else if(content.charAt(ci)=='<'){
//                                    conStr+=",";
//                                    isConAdd=false;
//                                }
//                                if(isConAdd){
//                                    conStr+=content.charAt(ci);
//                                }
//                            }
                                zhufang.setContent(content.replaceAll("<span>|</span>", ""));
                            }
                        }
                        if (zhufang.isNull() != null) {
                            listZhuFan.add(zhufang);
                        }
                    }
                }
            });
        }
        con.close();
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
                        db.insertDbOne(SqlHuiShui.insertDats,new Object[]{z.getTitle(),z.getPerType(),z.getPrice(),z.getUrlType(),z.getAddr(),z.getContent(),"惠水",Time.getTime("yyyy-MM-dd")});
                    }
                }
            }
        }
        db.closeConn();
    }

    //惠水在线
    //http://www.huishui.ccoo.cn/post/zhaopins/list-116757-0-0-0-0-0-1.html
    //http://www.huishui.ccoo.cn/post/zhaopins/list-116757-0-0-0-0-0-1.html
    public void huishui() throws Exception {
        Conn con=new Conn();
        StringBuffer str=new StringBuffer();
        con.getConne(huishuiUrl+"/post/job/", huishuiEnc, new CallBack() {
            @Override
            public void callBackOne(BufferedReader reader) throws IOException {
                String line=null;
                StringBuffer buf=new StringBuffer();
                while((line=reader.readLine())!=null){
                    buf.append(line.replace(" +",""));
                   // System.out.println(line);
                }
                //--------------start
                int isSubIndex=0;
                boolean isSub=false;
                boolean isSubs=false;
                for(int i=0;i<buf.length();i++){
                    char ch=buf.charAt(i);
                    if((ch=='<')&&((i+20)<buf.length())&&("<div class=\"leibie\">".equals((buf.substring(i,i+20))))){
                        i=i+20;
                        isSub=true;
                    }
                    if(isSub){
                        //截取需要的地址
                        if((ch=='<')&&((i+13)<buf.length())&&("<li><a href=\"".equals(buf.substring(i,i+13)))){
                            i=i+13;
                            isSubs=true;
                        }else if(isSubs&&(ch=='"')&&("\">".equals(buf.substring(i,i+2)))){
                            i=i+2;
                            str.append(",");
                            isSubs=false;
                        }
                        if(isSubs){
                            str.append(buf.charAt(i));
                        }
                        if((ch=='<')&&((i+4)<buf.length())&&("<div".equals(buf.substring(i,i+4)))){
                            isSubIndex++;
                        }else if((ch=='<')&&((i+6)<buf.length())&&("</div>".equals(buf.substring(i,i+6)))){
                            isSubIndex--;
                        }
                        if(isSubIndex<0){
                            break;
                        }
                    }
                }
                //----------------end
            }
        });
        if(str.length()>0){
            con.getConne(huishuiUrl + "/post/zhaopins/list-116756-0-0-0-0-0-1.html", huishuiEnc, new CallBack() {
                @Override
                public void callBackOne(BufferedReader reader) throws IOException {
                    String line=null;
                    StringBuffer buf=new StringBuffer();
                    StringBuffer addBuf=new StringBuffer();
                    while((line=reader.readLine())!=null){
                        buf.append(line);
                    }
                    boolean isSub=false;
                    boolean isAdd=false;
                    int isSubIndex=0;
                    for(int i=0;i<buf.length();i++){
                        char ch=buf.charAt(i);
                        if(ch=='<'&&(i+23<buf.length())&&"<ul class=\"zhaopin-xx\">".equals(buf.substring(i,i+23))){
                            i=i+23;
                            isSub=true;
                        }
                        if(isSub){
                            addBuf.append(ch);
                            if(ch=='<'&&(i+3)<buf.length()&&"<ul".equals(buf.substring(i,i+3))){
                                i=i+3;
                                isSubIndex++;
                            }else if(ch=='<'&&(i+5<buf.length())&&"</ul>".equals(buf.substring(i,i+5))){
                                i=i+5;
                                isSubIndex--;
                            }
                            if(isAdd){
                                addBuf.append(ch);
                            }
                            if(isSubIndex<0){
                                break;
                            }
                        }
                    }
                    System.out.println(addBuf);
                }
            });
//            for(String urlStr:str.toString().split(",")){
//
//                break;
//            }
        }

        con.close();
    }
}
/**
 * <li><a href="/post/zhaopins/list-116757-0-0-0-0-0-1.html">外卖配送员</a></li>
 * <li><a href="/post/zhaopins/list-116756-0-0-0-0-0-1.html">化妆师</a></li>
 * <li><a href="/post/zhaopins/list-116755-0-0-0-0-0-1.html">厨师</a></li>
 * <li><a href="/post/zhaopins/list-116696-0-0-0-0-0-1.html">收银员</a></li>
 * <li><a href="/post/zhaopins/list-116680-0-0-0-0-0-1.html">编辑/摄影</a></li>
 * <li><a href="/post/zhaopins/list-41371-0-0-0-0-0-1.html">传菜员/服务员</a></li>
 * <li><a href="/post/zhaopins/list-18691-0-0-0-0-0-1.html">驾驶/代驾</a></li>
 * <li><a href="/post/zhaopins/list-18690-0-0-0-0-0-1.html">医疗/卫生</a></li>
 * <li><a href="/post/zhaopins/list-4118-0-0-0-0-0-1.html">营业员/店员</a></li>
 * <li><a href="/post/zhaopins/list-4116-0-0-0-0-0-1.html">会计/出纳</a></li>
 * <li><a href="/post/zhaopins/list-4115-0-0-0-0-0-1.html">行政/文秘</a></li>
 * <li><a href="/post/zhaopins/list-4117-0-0-0-0-0-1.html">业务/销售</a></li>
 * <li><a href="/post/zhaopins/list-23365-0-0-0-0-0-1.html">客服/话务</a></li>
 * <li><a href="/post/zhaopins/list-4114-0-0-0-0-0-1.html">经理/管理</a></li>
 * <li><a href="/post/zhaopins/list-26366-0-0-0-0-0-1.html">工人/技工</a></li>
 * <li><a href="/post/zhaopins/list-29367-0-0-0-0-0-1.html">保安/仓管</a></li>
 * <li><a href="/post/zhaopins/list-44372-0-0-0-0-0-1.html">司机/跟车</a></li>
 *  <li><a href="/post/zhaopins/list-47373-0-0-0-0-0-1.html">老师/家教</a></li>
 * <li><a href="/post/zhaopins/list-32368-0-0-0-0-0-1.html">快递员/送货员</a></li>
 * <li><a href="/post/zhaopins/list-35369-0-0-0-0-0-1.html">厨师/配菜员</a></li>
 * <li><a href="/post/zhaopins/list-38370-0-0-0-0-0-1.html">保姆/钟点工</a></li>
 * <li><a href="/post/zhaopins/list-50374-0-0-0-0-0-1.html">美工/设计师</a></li>
 * <li><a href="/post/zhaopins/list-4113-0-0-0-0-0-1.html">程序/网管</a></li>
 * <li><a href="/post/zhaopins/list-53375-0-0-0-0-0-1.html">按摩师/美容师</a></li>
 *
 * */