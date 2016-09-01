package com.masiis.shop.web.common.service;

import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.PfUserSku;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by jiajinghao on 2016/8/8.
 */
@Service
@Transactional
public class CheckSkuAgentStatusService {

    @Resource
    private PfUserSkuMapper pfUserSkuMapper;
    @Resource
    private ComSkuMapper comSkuMapper;
    /**
     * jjh
     * 检查该商品的代理状态--只适用于申请主打商品的情况
     * userId -当前用户
     * skuId
     */
    public Boolean availableSkuForAgent(Integer skuId,Long userId) throws Exception{
        boolean hasAgent = false;
        List<PfUserSku> pfUserSkuList = pfUserSkuMapper.selectByUserId(userId);
        if(pfUserSkuList==null || pfUserSkuList.size()<=0){
            hasAgent =false;//小白用户
        }else {
            PfUserSku pfUserSku = pfUserSkuMapper.selectByUserIdAndSkuId(pfUserSkuList.get(0).getUserPid(),skuId);
            if(pfUserSku==null){
                hasAgent =false;//上级没代理
            }else {
                hasAgent = true;//可以代理
            }
        }
       return hasAgent;
    }
}
