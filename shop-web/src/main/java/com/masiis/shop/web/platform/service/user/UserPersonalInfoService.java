package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.po.ComUserExtractwayInfo;
import com.masiis.shop.dao.po.PfUserCertificate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hzz on 2016/3/29.
 */
@Service
public class UserPersonalInfoService {

    @Resource
    private UserCertificateService userCertificateService;

    @Resource
    private UserExtractwayInfoService userExtractwayInfoService;


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
    public PfUserCertificate selectSkuWeChatInfo(Long userId){
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
