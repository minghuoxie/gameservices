package net.conn;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

public class Conn {
    private static class TrustAnyTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }
    }

    private HttpURLConnection conn;
    private BufferedReader reader;
    public void close(){
        try{
            if(reader!=null){
                reader.close();
            }
            conn.disconnect();
        }catch (Exception e){
            System.err.println("Class:net.Conn line:15 or 18");
            e.printStackTrace();
        }
    }
    private void connTop(String strUrl,String encoding)throws Exception{
        URL url=new URL(strUrl);
        //打开连接
        conn=(HttpURLConnection)url.openConnection();

        //假的安全证书
        if (conn instanceof HttpsURLConnection)  {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
            ((HttpsURLConnection) conn).setSSLSocketFactory(sc.getSocketFactory());
            ((HttpsURLConnection) conn).setHostnameVerifier(new TrustAnyHostnameVerifier());
        }

        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36");
        conn.setDoInput(true);
    }
    public void getConne(String strUrl,String encoding,CallBack callBack) throws Exception {
        connTop(strUrl,encoding);
        //建立连接
        conn.connect();
        if(conn.getResponseCode()==200){
            reader=new BufferedReader(new InputStreamReader(conn.getInputStream(),encoding));
            callBack.callBackOne(reader);
        }
    }

    public Element getBodyElement(String strUrl,String encoding)throws Exception{
        connTop(strUrl,encoding);
        //建立连接
        conn.connect();
        if(conn.getResponseCode()==200){
            reader=new BufferedReader(new InputStreamReader(conn.getInputStream(),encoding));
            String line=null;
            StringBuffer buf=new StringBuffer();
            while((line=reader.readLine())!=null){
                buf.append(line);
            }
            Document doc = Jsoup.parse(buf.toString());
            Element body = doc.body();
            return body;
        }
        return null;
    }

    //根据标签名称和属性名称获取文本
    public String getTextByTagAndAttr(String tagName,String attrName,String attrVal,Element root){
        if(root!=null){
            if(tagName.equals(root.tagName())&&attrVal.equals(root.attributes().get(attrName))){
                return root.text();
            }else if(root.children().size()>0){
                for(int i=0;i<root.children().size();i++){
                    String txt=getTextByTagAndAttr(tagName,attrName,attrVal,root.children().get(i));
                    if(!txt.equals("")){
                        return txt;
                    }
                }
            }
        }
        return "";
    }


    //根据标签名称和属性名称获取其他属性值
    public String getAttrValByTagAndAttr(String tagName,String attrName,String attrVal,String muName,Element root){
        if(root!=null){
            if(tagName.equals(root.tagName())&&attrVal.equals(root.attributes().get(attrName))){
                String re=root.attributes().get(muName);
                return re;
            }else if(root.children().size()>0){
                for(int i=0;i<root.children().size();i++){
                    String txt=getAttrValByTagAndAttr(tagName,attrName,attrVal,muName,root.children().get(i));
                    if(!txt.equals("")){
                        return txt;
                    }
                }
            }
        }
        return "";
    }

    //根据标签获取对应的属性值
    public String getAttrValByTag(String tagName,String muName,Element root){
        if(root!=null){
            if(tagName.equals(root.tagName())){
                String re=root.attributes().get(muName);
                return re;
            }else if(root.children().size()>0){
                for(int i=0;i<root.children().size();i++){
                    String txt=getAttrValByTag(tagName,muName,root.children().get(i));
                    if(!txt.equals("")){
                        return txt;
                    }
                }
            }
        }
        return "";
    }

    public String getStrByTagAndAttr(String pTagName,String pAttrName,String pAttrVal,String tagName,String attrName,String attrVal,String muName,char type,Element root){
        if(root!=null){
            if(pTagName.equals(root.tagName())&&pAttrVal.equals(root.attributes().get(pAttrName))){
                return getStrByTagAndAttr(tagName,attrName,attrVal,muName,type,root);
            }else if(root.children().size()>0){
                for(int i=0;i<root.children().size();i++){
                   String txt=getStrByTagAndAttr(pTagName,pAttrName,pAttrVal,tagName,attrName,attrVal,muName,type,root.children().get(i));
                   if(!txt.equals("")){
                       return txt;
                   }
                }
            }
        }
        return "";
    }

    public String getStrByTagAndAttr(String tagName,String attrName,String attrVal,String muName,char type,Element root){
        if(root!=null){
           if(tagName.equals(root.tagName())&&attrVal.equals(root.attributes().get(attrName))){
               if(type=='K'){
                   return root.attributes().get(muName);
               }else if(type=='C'){
                   return root.text();
               }
           }else if(root.childNodes().size()>0){
               if(root.children().size()>0) {
                   for (int i = 0; i < root.children().size(); i++) {
                       String txt = getStrByTagAndAttr(tagName, attrName, attrVal, muName, type,root.children().get(i));
                       if (!txt.equals("")) {
                           return txt;
                       }
                   }
               }
           }
        }
        return "";
    }

    //根据标签获取对应的属性值和节点内容
    public String getAttrValByTag(String tagName,String muName,char type,Element root){
        if(root!=null){
            if(tagName.equals(root.tagName())){
                if(type=='K'){
                    return root.attributes().get(muName);
                }else if(type=='C'){
                    return root.text();
                }
            }else if(root.childNodes().size()>0){
                for(int i=0;i<root.children().size();i++){
                    String txt=getAttrValByTag(tagName,muName,type,root.children().get(i));
                    if(!txt.equals("")){
                        return txt;
                    }
                }
            }
        }
        return "";
    }
    //根据标签以及父节点获取对应的属性值和节点内容
    public String getAttrValByTag(String pTagName,String pAttrName,String pAttrVal,String tagName,String muName,char type,Element root){
        if(root!=null){
            if(pTagName.equals(root.tagName())&&pAttrVal.equals(root.attributes().get(pAttrName))){
                return getAttrValByTag(tagName,muName,type,root);
            }else if(root.children().size()>0){
                for(int i=0;i<root.children().size();i++) {
                    String txt = getAttrValByTag(pTagName, pAttrName, pAttrVal, tagName, muName, type,root.children().get(i));
                    if(!txt.equals("")){
                        return txt;
                    }
                }
            }
        }
        return "";
    }

    //获取指定标签下的指定标签
    public void findElesByPTag(String pTagName,String pAttrName,String pAttrVal,String tagName,List<Element> eleList,Element root){
        if(root!=null){
            if(pTagName.equals(root.tagName())&&pAttrVal.equals(root.attributes().get(pAttrName))&&root.children().size()>0){
                for(int i=0;i<root.children().size();i++){
                    Element e=root.children().get(i);
                    if(tagName.equals(e.tagName())){
                        eleList.add(e);
                    }
                }
            }else if(root.children().size()>0){
                for(int i=0;i<root.children().size();i++){
                    findElesByPTag(pTagName,pAttrName,pAttrVal,tagName,eleList,root.children().get(i));
                }
            }
        }
    }

    //获取指定的标签
    public Element findEleByTagAndAttrAndTxt(String tagName,String attrName,String attrVal,String text,Element root){
        if(tagName.equals(root.tagName())&&attrVal.equals(root.attributes().get(attrName))&&text.equals(root.text().trim())){
            return root;
        }else if(root.children().size()>0){
            for(int i=0;i<root.children().size();i++){
                Element ele=findEleByTagAndAttrAndTxt(tagName,attrName,attrVal,text,root.children().get(i));
                if(ele!=null){
                    return ele;
                }
            }
        }
        return null;
    }

    //根据标签名称和属性名称以及父节点获取文本
    public String getTextByTagAndAttr(String tagName,String attrName,String attrVal,String pTagName,String pAttrName,String pAttrVal,Element root){
        if(root!=null){
            if(pTagName.equals(root.tagName())&&pAttrVal.equals(root.attributes().get(pAttrName))){
                return getTextByTagAndAttr(tagName,attrName,attrVal,root);
            }else if(root.children().size()>0){
                for(int i=0;i<root.children().size();i++){
                    String txt=getTextByTagAndAttr(tagName,attrName,attrVal,pTagName,pAttrName,pAttrVal,root.children().get(i));
                    if(!txt.equals("")){
                        return txt;
                    }
                }
            }
        }
        return "";
    }
}
