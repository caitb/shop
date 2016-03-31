package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.po.ComUserExtractwayInfo;
import com.masiis.shop.dao.po.PfSkuAgent;
import com.masiis.shop.dao.po.PfUserCertificate;
import com.masiis.shop.web.platform.service.product.SkuAgentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by hzz on 2016/3/29.
 */
@Service
public class UserPersonalInfoService {

    @Resource
    private UserCertificateService userCertificateService;
    @Resource
    private UserExtractwayInfoService userExtractwayInfoService;
    @Resource
    private SkuAgentService skuAgentService;


    @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
    public Map<String,Object> getPersonalHomePageInfo(Long userId){
        //获取代理商品信息
        List<PfSkuAgent> pfSkuAgents = skuAgentService.getSkuLevelIconByUserId(userId);
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        map.put("pfskuAgents",pfSkuAgents);
        return map;
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
