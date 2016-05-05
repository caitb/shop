package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.service.product.SkuAgentService;
import com.masiis.shop.web.platform.service.product.SkuService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by hzz on 2016/3/29.
 */
@Service
public class UserPersonalInfoService {

    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private UserCertificateService userCertificateService;
    @Resource
    private UserExtractwayInfoService userExtractwayInfoService;
    @Resource
    private SkuAgentService skuAgentService;
    @Resource
    private ComUserAccountService accountService;
    @Resource
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
            log.info("商品等级skuId-----"+skuAgent.getSkuId());
            log.info("商品等级icon-----"+skuAgent.getIcon());
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

    /**
     * 查询用户的银行卡信息
     * @author hanzengzhi
     * @date 2016/3/29 14:26
     */
    @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
    public List<ComUserExtractwayInfo>  findBankCardInfoByUserId(Long userId){
        return userExtractwayInfoService.findByUserId(userId);
    }

    /**
     * 个人信息--微信号查询
     * @author hanzengzhi
     * @date 2016/3/29 13:59
     */
    @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
    public List<PfUserCertificate> selectSkuWeChatInfo(Long userId){
        return userCertificateService.selectSkuWeChatInfo(userId);
    }
    /**
     * 删除银行卡信息
     * @author hanzengzhi
     * @date 2016/3/29 14:44
     */
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public int deleteBankCardInfoById(Long id){
        return  userExtractwayInfoService.deleteById(id);
    }
}
