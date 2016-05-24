package com.masiis.shop.api.controller.system;

import com.masiis.shop.api.bean.base.BaseReq;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @Date 2016/5/24
 * @Auther lzh
 */
@Controller
@RequestMapping("/code")
public class CodeController extends BaseController {

    @RequestMapping("/list")
    @SignValid(paramType = BaseReq.class, hasToken = false, isPageReturn = true, hasData = false)
    public String codeList(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, IOException {
        Map<String, String> preMap = new HashMap<>();
        Map<String, String> sufMap = new HashMap<>();
        Map<String, String> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        Field[] fields = SysResCodeCons.class.getDeclaredFields();
        for(Field f:fields){
            String name = f.getName();
            if(name.matches(".*\\_MSG")){
                sufMap.put(name.substring(0, name.length() - 4), (String) f.get(SysResCodeCons.class));
            } else {
                preMap.put(name, (String) f.get(SysResCodeCons.class));
            }
        }
        StringBuilder res = new StringBuilder();
        for(String pre:preMap.keySet()){
            String p = preMap.get(pre);
            map.put(p, sufMap.get(pre));
            list.add(p);
        }
        String[] arrayToSort = list.toArray(new String[list.size()]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);

        request.setAttribute("list", arrayToSort);
        request.setAttribute("map", map);
        return "system/status_code";
    }
}
