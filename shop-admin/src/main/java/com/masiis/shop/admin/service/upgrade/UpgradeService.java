package com.masiis.shop.admin.service.upgrade;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.platform.user.PfUserUpgradeNoticeMapper;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserSku;
import com.masiis.shop.dao.po.PfUserUpgradeNotice;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 升级服务类
 */
@Service
public class UpgradeService {

    @Resource
    private PfUserUpgradeNoticeMapper pfUserUpgradeNoticeMapper;
    @Resource
    private ComSkuMapper comSkuMapper;
    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private PfUserSkuMapper pfUserSkuMapper;


    public Map<String,Object> listByCondition(Integer pageNumber, Integer pageSize, Map<String,Object> conditionMap) {
        PageHelper.startPage(pageNumber, pageSize, "create_time desc");
        List<PfUserUpgradeNotice> upgradeNotices = pfUserUpgradeNoticeMapper.selectByMap(conditionMap);
        PageInfo<PfUserUpgradeNotice> pageInfo = new PageInfo<>(upgradeNotices);

        List<Map> upgradeNoticeWraps = new LinkedList<>();
        for(PfUserUpgradeNotice upgradeNotice : upgradeNotices) {
            Map<String,Object> upgradeNoticeWrap = new HashMap<>();
            upgradeNoticeWrap.put("upgradeNotice", upgradeNotice);

            // 获取商品名称
            ComSku sku = comSkuMapper.selectByPrimaryKey(upgradeNotice.getSkuId());
            if(sku != null) {
                upgradeNoticeWrap.put("skuName", sku.getName());
            }

            // 获取用户名称
            ComUser user = comUserMapper.selectByPrimaryKey(upgradeNotice.getUserId());
            if(user != null) {
                upgradeNoticeWrap.put("userName", user.getRealName());


            }

            // 获取现上级名
            PfUserSku  pfUserSku = pfUserSkuMapper.selectByUserIdAndSkuId(upgradeNotice.getUserId(), upgradeNotice.getSkuId());
            if(pfUserSku != null) {
                ComUser cpUser = comUserMapper.selectByPrimaryKey(pfUserSku.getUserPid());
                if(cpUser != null) {
                    upgradeNoticeWrap.put("cpUserName", cpUser.getRealName());
                }
            }


            // 原上级名称
            ComUser pUser = comUserMapper.selectByPrimaryKey(upgradeNotice.getUserPid());
            if(pUser != null) {
                upgradeNoticeWrap.put("userOldParentName", pUser.getRealName());
            }

            upgradeNoticeWraps.add(upgradeNoticeWrap);
        }

        Map<String,Object> pageMap = new HashMap<>();
        pageMap.put("total",pageInfo.getTotal());
        pageMap.put("rows", upgradeNoticeWraps);
        return pageMap;
    }

}
