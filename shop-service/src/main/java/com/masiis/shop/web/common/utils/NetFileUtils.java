package com.masiis.shop.web.common.utils;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import org.apache.commons.io.IOUtils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;

public class NetFileUtils {

    public static InputStream getInputStream(String urlStr) {
        URL url = null;
        URLConnection connection = null;
        try {
            url = new URL(urlStr);
            connection = url.openConnection();
            connection.setConnectTimeout(5 * 1000);    //设置请求超时为5s
            return connection.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static byte[] getBytes(String urlStr) {
        byte[] bytes = null;

        try {
            InputStream input = getInputStream(urlStr);
            if(input != null) {
                bytes = IOUtils.toByteArray(input);
                input.close();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return bytes;

    }

    public static BufferedImage getBufferedImage(String urlStr) throws Exception {
        InputStream input = getInputStream(urlStr);
        return ImageIO.read(input);
    }

}
