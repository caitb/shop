package com.masiis.shop.admin.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class GetLocalIPUtil {

    /**
     * 获取本地IP
     * @author muchaofeng
     * @date 2016/5/30 13:37
     */
    public static String getLocalIP(){
        StringBuilder sb = new StringBuilder();
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface intf = (NetworkInterface) en.nextElement();
                Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
                while (enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()
                            && inetAddress.isSiteLocalAddress()) {
                        sb.append(inetAddress.getHostAddress().toString()+"\n");
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
