package net.conn;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

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
    public void getConne(String strUrl,String encoding,CallBack callBack) throws Exception {
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
        //建立连接
        conn.connect();
        if(conn.getResponseCode()==200){
            reader=new BufferedReader(new InputStreamReader(conn.getInputStream(),encoding));
            callBack.callBackOne(reader);
        }
    }
}
