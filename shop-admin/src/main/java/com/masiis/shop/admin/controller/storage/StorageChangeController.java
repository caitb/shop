package com.masiis.shop.admin.controller.storage;

import com.masiis.shop.admin.service.storage.PbStorageBillItemService;
import com.masiis.shop.admin.service.storage.PbStorageBillService;
import com.masiis.shop.dao.po.PbStorageBill;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Date 2016/7/19
 * @Author lzh
 */
@Controller
@RequestMapping("/storagechange")
public class StorageChangeController {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private PbStorageBillService billService;
    @Resource
    private PbStorageBillItemService itemService;

    @RequestMapping("/list.shtml")
    public String list(){
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
        return "storage/list";
    }

    @RequestMapping("/create.shtml")
    public String toCreateStorageChangeBill(){
        return "storage/storagechange_create";
    }
}
