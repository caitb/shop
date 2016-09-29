package com.masiis.shop.scheduler.platform.business.user;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfShop;
import com.masiis.shop.web.common.service.UserService;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Date 2016/9/28
 * @Author lzh
 */
@Service
public class PfUserShopService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private UserService userService;
    @Resource
    private SfShopService sfShopService;

    /**
     * 自动关闭未实名认证代理商店铺
     */
    public void autoCloseUnAuditUserShop() {
        Date time = DateUtil.getDateNextdays(new Date(), -3);
        log.info("查询时间临界点:" + DateUtil.Date2String(time, "yyyy-MM-dd HH:mm:ss"));

        log.info("查询时间临界点之前未实名认证的用户");
        List<ComUser> users = userService.findUnAuditUsersAndNotCloseShopByTime(time);
        if(users == null || users.size() <= 0){
            throw new BusinessException("暂无需要关闭店铺用户");
        }
        log.info("开始关闭用户店铺");
        for(ComUser user:users){
            log.info("开始,用户id:" + user.getId());
            try{
                userService.closeUnAuditUserShop(user, time);
            } catch (Exception e) {
                log.error("关闭用户店铺失败,用户id:" + user.getId(), e);
            }
            log.info("结束,用户id:" + user.getId());
        }
        log.info("关闭用户店铺操作结束");
    }

    public static void main(String... args){
        System.out.println(DateUtil.getDateNextdays(new Date(), -3));
    }
}
