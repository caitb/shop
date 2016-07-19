package com.masiis.shop.admin.controller.storage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by huangwei on 2016/7/19.
 */
@Controller
@RequestMapping("/storagechange")
public class StorageController {


    @RequestMapping("/list.shtml")
    public String list(){
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
        return "storage/list";
    }

}
