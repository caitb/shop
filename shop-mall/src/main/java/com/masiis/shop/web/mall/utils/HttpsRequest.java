package com.masiis.shop.web.mall.utils;

import com.masiis.shop.web.mall.beans.pay.wxpay.Configure;
import com.masiis.shop.web.mall.beans.pay.wxpay.IServiceRequest;
import com.masiis.shop.web.mall.beans.pay.wxpay.UnifiedOrderReq;
import com.masiis.shop.common.constant.wx.WxConsSF;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
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
 * @author lzh
 */
public class HttpsRequest implements IServiceRequest {

    public interface ResultListener {


        public void onConnectionPoolTimeoutError();

    }

    private static Logger log = Logger.getLogger(HttpsRequest.class);

    //表示请求器是否已经做了初始化工作
    private boolean hasInit = false;

    //连接超时时间，默认10秒
    private int socketTimeout = 10000;

    //传输超时时间，默认30秒
    private int connectTimeout = 30000;

    //请求器的配置
    private RequestConfig requestConfig;

    //HTTP请求器
    private CloseableHttpClient httpClient;

    public HttpsRequest() {
        //init();
    }

    private void init() throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File(Configure.getCertLocalPath()));//加载本地的证书进行https加密传输
        try {
            keyStore.load(instream, Configure.getCertPassword().toCharArray());//设置证书密码
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, Configure.getCertPassword().toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

        httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();

        //根据默认超时限制初始化requestConfig
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();

        hasInit = true;
    }

    public static void main(String[] args) throws UnrecoverableKeyException, IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        HttpsRequest h = new HttpsRequest();
        UnifiedOrderReq order = new UnifiedOrderReq();
        order.setAppid(WxConsSF.APPID);
        //order.setAttach();
        order.setBody("麦士测试商品10");
        //order.setDetail();
        //order.setDevice_info();
        //order.setFee_type(); //默认中文
        //order.setGoods_tag();
        //order.setLimit_pay();
        order.setMch_id(WxConsSF.WX_PAY_MCHID);
        order.setNonce_str("AAAAAAAAAABBBBBBBBBB223225220");
        order.setNotify_url(WxConsSF.WX_PAY_URL_UNIORDER_NOTIFY);
        order.setOut_trade_no("TESTORDER0000000000000010");
        order.setOpenid("oUIwkwgLzn8CKMDrvbCSE3T-u5fs");
        //order.setProduct_id();
        order.setSpbill_create_ip("127.0.0.1");
        //order.setSign();
        order.setTrade_type("JSAPI");
        order.setTotal_fee("1");
        //order.setTime_start();
        //order.setTime_expire();
        String sign = "";
        sign = WXBeanUtils.toSignString(order);
        order.setSign(sign);
        System.out.println(h.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", order));
    }

    /**
     * 通过Https往API post xml数据
     *
     * @param url    API地址
     * @param xmlObj 要提交的XML数据对象
     * @return API回包的实际数据
     * @throws IOException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public String sendPost(String url, Object xmlObj) {
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
            //String postDataXML = "<xml><appid><![CDATA[wxd5afa1deb29c6197]]></appid><mch_id><![CDATA[1319531601]]></mch_id><nonce_str><![CDATA[AAAAAAAAAABBBBBBBBBB223223211]]></nonce_str><sign><![CDATA[CF6E9C1A47CBA796F5A42BA7C3829075]]></sign><body><![CDATA[麦士测试商品02]]></body><out_trade_no><![CDATA[TESTORDER0000000000000002]]></out_trade_no><total_fee><![CDATA[1]]></total_fee><spbill_create_ip><![CDATA[127.0.0.1]]></spbill_create_ip><notify_url><![CDATA[http://www.rd.masiis.com]]></notify_url><trade_type><![CDATA[JSAPI]]></trade_type><openid><![CDATA[o2-m4wZWlG4mdln4SQJRo29YnL2U]]></openid></xml>"; //xStreamForRequestPostData.toXML(xmlObj);
            //postDataXML = URLEncoder.encode(postDataXML, "UTF-8");
            StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
            httpPost.setEntity(postEntity);

            log.info("API，POST过去的数据是：");
            log.info(postDataXML);
        }

        //设置请求器的配置
        httpPost.setConfig(requestConfig);

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

    /**
     * 设置连接超时时间
     *
     * @param socketTimeout 连接时长，默认10秒
     */
    public void setSocketTimeout(int socketTimeout) {
        socketTimeout = socketTimeout;
        resetRequestConfig();
    }

    /**
     * 设置传输超时时间
     *
     * @param connectTimeout 传输时长，默认30秒
     */
    public void setConnectTimeout(int connectTimeout) {
        connectTimeout = connectTimeout;
        resetRequestConfig();
    }

    private void resetRequestConfig(){
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
    }

    /**
     * 允许商户自己做更高级更复杂的请求器配置
     *
     * @param requestConfig 设置HttpsRequest的请求器配置
     */
    public void setRequestConfig(RequestConfig requestConfig) {
        requestConfig = requestConfig;
    }
}
