package net.connurl.html;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TestHtmlUnit {

    public void testHtmlUnitOne(){
        WebClient client=new WebClient(BrowserVersion.CHROME);//新建模拟谷歌Chrome浏览器的浏览器客户端对象

        client.getOptions().setThrowExceptionOnScriptError(false);//当执行js出错的时候是否抛出异常
        client.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
        client.getOptions().setActiveXNative(false);
        client.getOptions().setCssEnabled(false);//是否启用CSS
        client.getOptions().setJavaScriptEnabled(true);//是否启用js
        client.setAjaxController(new NicelyResynchronizingAjaxController()); //设置支持AJAX

        HtmlPage page=null;
        try{
            page=client.getPage("http://ent.sina.com.cn/film/");//加载网页
        }catch (Exception e){
            e.printStackTrace();
        }
        client.close();
        client.waitForBackgroundJavaScript(3000);

        String pageXml=page.asXml();//直接将加载玩成的xml转换位字符串
        Document doc= Jsoup.parse(pageXml);

        System.out.println("--");
    }
}
