package com.masiis.shop.web.platform.service.product;

import com.masiis.shop.common.util.MobileMessageUtil;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.common.service.UserService;
import com.masiis.shop.web.common.utils.notice.SysNoticeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    private PfUserSkuStockService pfUserSkuStockService;
    @Autowired
    private UserService userService;

    /**
     * 代理商库存不足短信提醒
     */
    public void replenishStockRemind() throws Exception{
        logger.info("代理商库存不足短信提醒");
        List<Map<String,Object>> mapList = pfUserSkuStockService.selectReplenishStock();
        String skuName;
        String mobile;
        String skuId;
        Long userId;
        Integer stock;
        String url;
        String[] params;
        for (Map<String, Object> map : mapList){
            stock = Integer.parseInt(map.get("stock")==null?"0":map.get("stock").toString());
            logger.info("检测代理库存是否充足："+stock);
            if (stock < 10){
                skuName = map.get("name") == null?"" : map.get("name").toString();
                mobile = map.get("mobile") == null  ?"" : map.get("mobile").toString();
                if (!"".equals(skuName) && !"".equals(mobile)){
                    MobileMessageUtil.getInitialization("").stockNotEnoughWarning(mobile,skuName);
                }
                skuId = map.get("skuId").toString();
                userId = Long.valueOf(map.get("userId").toString());
                url = PropertiesUtils.getStringValue("web.domain.name.address") + "/product/user/" + userId;
                logger.info("url:" + url);
                if(stock < 0) stock = 0;
                params = new String[3];
                params[0] = skuId;
                params[1] = skuName;
                params[2] = String.valueOf(stock);
                ComUser comUser = userService.getUserById(userId);
                SysNoticeUtils.getInstance().inventoryShortageNotice(comUser,params,url);
            }
        }
    }
}
