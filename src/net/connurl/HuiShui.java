package net.connurl;

import net.conn.CallBack;
import net.conn.Conn;

import java.io.BufferedReader;
import java.io.IOException;

public class HuiShui {
    private String huishuiUrl;
    private String huishuiEnc;
    public HuiShui( String huishuiUrl,String huishuiEnc){
        this.huishuiUrl=huishuiUrl;
        this.huishuiEnc=huishuiEnc;
    }
    //惠水在线
    //http://www.huishui.ccoo.cn/post/zhaopins/list-116757-0-0-0-0-0-1.html
    //http://www.huishui.ccoo.cn/post/job/
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
        con.close();
        if(str.length()>0){
            for(String urlStr:str.toString().split(",")){

            }
        }
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