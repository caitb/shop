package com.masiis.shop.scheduler.platform.service.stock;

import com.masiis.shop.common.util.MobileMessageUtil;
import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * 库存不足短信提醒
 * Created by wangbingjian on 2016/4/26.
 */
@Service
public class ReplenishStockService {

    private static final Logger logger = Logger.getLogger(ReplenishStockService.class);
    @Autowired
    private PfUserSkuStockMapper pfUserSkuStockMapper;

    /**
     * 代理商库存不足短信提醒
     */
    public void replenishStockRemind(){
        logger.info("代理商库存不足短信提醒");
        List<Map<String,String>> mapList = pfUserSkuStockMapper.selectReplenishStock();
        String name;
        String mobile;
        for (Map<String, String> map : mapList){
            name = map.get("name") == null?"" : map.get("name");
            mobile = map.get("mobile") == null  ?"" : map.get("mobile");
            if (!"".equals(name) && !"".equals(mobile)){
                MobileMessageUtil.stockNotEnoughWarning(mobile,name);
            }
        }
    }

//    public static void main(String[] args)throws Exception{
//        GenericXmlApplicationContext context = new GenericXmlApplicationContext();
//        context.setValidating(false);
//        context.load("classpath*:/spring/*.xml");
//        context.refresh();
//        ReplenishStockService replenishStockService = context.getBean(ReplenishStockService.class);
//        replenishStockService.replenishStockRemind();
//    }
}
