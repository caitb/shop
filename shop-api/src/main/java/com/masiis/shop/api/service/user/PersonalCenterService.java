package com.masiis.shop.api.service.user;

import com.masiis.shop.api.constants.SysConstants;
import com.masiis.shop.api.service.product.SkuAgentService;
import com.masiis.shop.api.service.product.SkuService;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by wangbingjian on 2016/5/5.
 */
@Service
public class PersonalCenterService {

    @Autowired
    private ComUserAccountService accountService;
    @Autowired
    private SkuAgentService skuAgentService;
    @Autowired
    private SkuService skuService;
    /**
     * 个人信息首页信息
     * @author hanzengzhi
     * @date 2016/4/1 11:59
     */
    @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
    public Map<String,Object> getPersonalHomePageInfo(ComUser comUser){
        //获取代理商品信息
        List<PfSkuAgentDetail> pfSkuAgentDetails = getSkuAgentDetail(comUser.getId());
        ComUserAccount comUserAccount = accountService.findAccountByUserid(comUser.getId());
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        map.put("agentLevelIConUrl", SysConstants.AGENT_LEVEL_PRODUCT_ICON_URL);
        map.put("pfSkuAgentDetails",pfSkuAgentDetails);
        map.put("comUserAccount",comUserAccount);
        return map;
    }

    /**
     * 获得商品等级详情信息
     * @author hanzengzhi
     * @date 2016/4/27 15:05
     */
    public List<PfSkuAgentDetail>  getSkuAgentDetail(Long userId){
        List<PfSkuAgent> pfSkuAgents = skuAgentService.getSkuLevelIconByUserId(userId);
        Map<Integer,String> map = new HashMap<Integer, String>();
        List<PfSkuAgentDetail> skuAgentDetails = new LinkedList<PfSkuAgentDetail>();
        for (PfSkuAgent skuAgent: pfSkuAgents){
            PfSkuAgentDetail skuAgentDetail = new PfSkuAgentDetail();
            if (!map.containsKey(skuAgent.getSkuId())){
                ComSku comSku = skuService.getSkuById(skuAgent.getSkuId());
                if (comSku != null){
                    skuAgentDetail.setPfSkuAgent(skuAgent);
                    skuAgentDetail.setSkuName(comSku.getName());
                    map.put(skuAgent.getSkuId(),comSku.getName());
                }else{
                    throw new BusinessException("商品不存在");
                }
            }else{
                skuAgentDetail.setPfSkuAgent(skuAgent);
                skuAgentDetail.setSkuName(map.get(skuAgent.getSkuId()));
            }
            skuAgentDetails.add(skuAgentDetail);
        }
        return skuAgentDetails;
    }
}
