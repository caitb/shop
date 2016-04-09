package com.masiis.shop.common.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.commons.logging.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * @Date:2016/4/9
 * @auth:lzh
 */
public class HttpsUtils {
    private Logger log = Logger.getLogger(this.getClass());
    private CloseableHttpClient httpClient;

    /**
     * 证书密码和证书文件对象
     *
     * @param pwd
     * @param file
     */
    public HttpsUtils(String pwd, File file) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        createClientWithPKCS12(pwd, file);
    }

    private CloseableHttpClient createClientWithPKCS12(String pwd, File file) throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException, KeyManagementException {
        //指定读取证书格式为PKCS12
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        //读取本机存放的PKCS12证书文件
        FileInputStream instream = new FileInputStream(file);
        try {
            //指定PKCS12的密码(商户ID)
            keyStore.load(instream, pwd.toCharArray());
        } finally {
            instream.close();
        }
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, pwd.toCharArray()).build();
        //指定TLS版本
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,new String[] { "TLSv1" },null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        //设置httpclient的SSLSocketFactory
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        return httpclient;
    }

    public String sendPostByXML(String url, Object xmlObj){
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        httpPost.addHeader("Content-Type", "text/xml");
        if(xmlObj != null) {
            //解决XStream对出现双下划线的bug
            XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
            //将要提交给API的数据对象转换成XML格式数据Post给API
            xStreamForRequestPostData.processAnnotations(xmlObj.getClass());
            String postDataXML = xStreamForRequestPostData.toXML(xmlObj);
            StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
            httpPost.setEntity(postEntity);

            log.info("API，POST过去的数据是：");
            log.info(postDataXML);
        }

        log.info("executing request" + httpPost.getRequestLine());

        try {
            if(httpClient == null){
                httpClient = new DefaultHttpClient();
            }
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        } catch (ConnectionPoolTimeoutException e) {
            log.error("http get throw ConnectionPoolTimeoutException(wait time out)");
        } catch (ConnectTimeoutException e) {
            log.error("http get throw ConnectTimeoutException");
        } catch (SocketTimeoutException e) {
            log.error("http get throw SocketTimeoutException");
        } catch (Exception e) {
            log.error("http get throw Exception:" + e.getMessage());
        } finally {
            httpPost.abort();
        }

        return result;
    }
}
