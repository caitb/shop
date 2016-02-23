package com.masiis.shop.web.controller;

import com.masiis.shop.dao.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by cai_tb on 16/2/16.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/login",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response, User user,
                        String signature, String timestamp, String nonce, String echostr){
        String token = "masiiswxtest09";
        String[] arrs = {token, timestamp, nonce};
        Arrays.sort(arrs);
        String bigStr = arrs[0] + arrs[1] + arrs[2];
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(bigStr.getBytes());
        byte[] messageDigest = md.digest();
        StringBuffer hexString = new StringBuffer();
        // 字节数组转换为 十六进制 数
        for (int i = 0; i < messageDigest.length; i++) {
            String shaHex = Integer.toHexString(messageDigest[i] & 0xFF).toLowerCase();
            if (shaHex.length() < 2) {
                hexString.append(0);
            }
            hexString.append(shaHex);
        }
        if(hexString.toString().equals(signature)){
            return echostr;
        }
        return "fail";
    }
}
