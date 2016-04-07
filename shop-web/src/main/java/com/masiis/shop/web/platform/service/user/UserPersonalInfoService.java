package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.po.*;
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
    @Resource
    private ComUserAccountService accountService;

    /**
     * 个人信息首页信息
     * @author hanzengzhi
     * @date 2016/4/1 11:59
     */
    @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
    public Map<String,Object> getPersonalHomePageInfo(ComUser comUser){
        //获取代理商品信息
        List<PfSkuAgent> pfSkuAgents = skuAgentService.getSkuLevelIconByUserId(comUser.getId());
        ComUserAccount comUserAccount = accountService.findAccountByUserid(comUser.getId());
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        map.put("pfskuAgents",pfSkuAgents);
        map.put("comUserAccount",comUserAccount);
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
