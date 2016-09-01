package com.masiis.shop.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Set;

/**
 * Created by lzh on 2016/2/23.
 */
public class HttpClientUtils {
    private static Logger logger = Logger.getLogger(HttpClientUtils.class);    //日志记录

    /**
     * post请求
     * @param url         url地址
     * @param jsonParam     参数
     * @return
     */
    public static String httpPost(String url,String jsonParam){
        //post请求返回结果
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String res = null;
        HttpPost method = new HttpPost(url);
        try {
            if (null != jsonParam) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json;charset=utf-8");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200) {
                String str = "";
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    res = EntityUtils.toString(result.getEntity());
                    String contentType = result.getHeaders("Content-Type")[0].getValue();
                    logger.info("Content-Type:" + contentType);
                    if(contentType.contains("UTF-8") || contentType.contains("utf-8")){
                        // UTF-8的不用解析
                    } else if(contentType.contains("GBK") || contentType.contains("gbk")){
                        res = new String(res.getBytes("GBK"), "UTF-8");
                    } else if(contentType.contains("ISO-8859-1") || contentType.contains("iso-8859-1")) {
                        res = new String(res.getBytes("ISO-8859-1"), "UTF-8");
                    } else if(contentType.contains("gb2312") || contentType.contains("GB2312")) {
                        res = new String(res.getBytes("gb2312"), "UTF-8");
                    } else {
                        res = new String(res.getBytes("ISO-8859-1"), "UTF-8");
                    }
                    logger.info("请求res:" + res);
                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        }
        return res;
    }

    /**
     * 发送get请求
     * @param url    路径
     * @return
     */
    public static String httpGet(String url){
        //get请求返回结果
        String strResult = null;
        try {
            url = URLDecoder.decode(url, "UTF-8");
            DefaultHttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request;
            request = new HttpGet(url);
            request.addHeader("Content-Type", "text/plain");
            HttpResponse response = client.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                strResult = EntityUtils.toString(response.getEntity(), "UTF-8");
            } else {
                logger.error("get请求提交失败:" + url);
            }
        } catch (IOException e) {
            logger.error("get请求提交失败:" + url, e);
        }
        return strResult;
    }
}
