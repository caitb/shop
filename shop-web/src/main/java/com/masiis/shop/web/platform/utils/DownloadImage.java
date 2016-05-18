package com.masiis.shop.web.platform.utils;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileOutputStream;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.net.URL;  
import java.net.URLConnection;

/**
 * Created by caitingbiao on 2016/3/23.
 */
public class DownloadImage {

    private final static Log log = LogFactory.getLog(DownloadImage.class);
  
    /** 
     * @param args 
     * @throws Exception  
     */  
    public static void main(String[] args) throws Exception {  
        // TODO Auto-generated method stub  
         download("http://wx.qlogo.cn/mmopen/h3YBVg6icqR7oVemR36gynKh2SnwzDZsakBQEicgwIqBFt8nBM4JJJpwfQrZAH2wE9RA9oHIibyeVFFrkSc8BSzwzogDflGOgN3/0", "cai_tb.gif","/Users/cai_tb/Documents");
    }  
      
    public static void download(String urlString, String filename,String savePath) throws Exception {
        InputStream is = null;
        OutputStream os = null;

        try {
            // 构造URL
            URL url = new URL(urlString);
            // 打开连接
            URLConnection con = url.openConnection();
            //设置请求超时为5s
            con.setConnectTimeout(5*1000);
            // 输入流
            is = con.getInputStream();

            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            File sf=new File(savePath);
            if(!sf.exists()){
                sf.mkdirs();
            }
            os = new FileOutputStream(sf.getPath()+File.separator+filename);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (Exception e) {
            log.error("下载图片失败![urlString="+urlString+"][filename="+filename+"][savePath="+savePath+"]");
            e.printStackTrace();
            // 完毕，关闭所有链接
            os.close();
            is.close();
        }
    }   
  
}