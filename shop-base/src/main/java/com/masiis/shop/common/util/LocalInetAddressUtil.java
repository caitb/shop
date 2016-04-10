package com.masiis.shop.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Date:2016/4/10
 * @auth:lzh
 */
public class LocalInetAddressUtil {
    public static void main(String[] args){
        System.out.println(getHostIp(getInetAddress()));
        System.out.println(getHostName(getInetAddress()));
    }

    public static InetAddress getInetAddress(){

        try{
            return InetAddress.getLocalHost();
        }catch(UnknownHostException e){
            System.out.println("unknown host!");
        }
        return null;

    }

    public static String getHostIp(){
        String ip = getInetAddress().getHostAddress(); //get the ip address
        return ip;
    }

    public static String getHostIp(InetAddress netAddress){
        if(null == netAddress){
            return null;
        }
        String ip = netAddress.getHostAddress(); //get the ip address
        return ip;
    }

    public static String getHostName(InetAddress netAddress){
        if(null == netAddress){
            return null;
        }
        String name = netAddress.getHostName(); //get the host address
        return name;
    }
}
