package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.certificate.CertificateInfo;
import com.masiis.shop.dao.platform.certificate.CertificateMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserSku;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * UserSkuService
 *
 * @author ZhaoLiang
 * @date 2016/3/8
 */
@Service
@Transactional
public class UserSkuService {
    @Resource
    private PfUserSkuMapper pfUserSkuMapper;

    @Resource
    private CertificateMapper certificateMapper;

    @Resource
    private UserSkuService userSkuService;
    /**
     * 获取用户产品代理关系
     *
     * @author ZhaoLiang
     * @date 2016/3/8 16:16
     */
    public PfUserSku getUserSkuByUserIdAndSkuId(Long userId, Integer SkuId) throws Exception {
        return pfUserSkuMapper.selectByUserIdAndSkuId(userId, SkuId);
    }

    /**
     * 根据主键获取数据
     *
     * @author ZhaoLiang
     * @date 2016/3/10 18:42
     */
    public PfUserSku getUserSkuById(Integer id) {
        return pfUserSkuMapper.selectByPrimaryKey(id);
    }

    /**
     * @Author 贾晶豪
     * @Date 2016/3/21 0021 下午 6:33
     * 获取用户代理信息
     */
    public List<CertificateInfo> getUserSkuByUserId(Long userId) throws Exception {
        return certificateMapper.getCertificatesByUser(userId);
    }

    /**
     * 获取数据集
     * @author ZhaoLiang
     * @date 2016/3/24 17:04
     */
    public List<PfUserSku> getAllByCondition(PfUserSku pfUserSku) {
        return pfUserSkuMapper.selectByCondition(pfUserSku);
    }

    /**
     * @param pUser 上级合伙人
     * @param skuId 代理的商品
     * @author ZhaoLiang
     * @date 2016/4/1 12:11
     */
    public void checkParentData(ComUser pUser, Integer skuId) throws Exception {
        if (pUser == null) {
            throw new BusinessException(" 您的推荐人还未注册，请联系您的推荐人先注册!");
        } else {
            PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(pUser.getId(), skuId);
            if (null == pfUserSku || pfUserSku.getIsPay() == 0) {
                throw new BusinessException("您的推荐人还未代理此款商品");
            }
        }
    }

    /**
     * @param pUser        上级合伙人
     * @param skuId        代理的商品
     * @param agentLevelId 自己的代理等级
     * @author ZhaoLiang
     * @date 2016/4/1 12:11
     */
    public PfUserSku checkParentData(ComUser pUser, Integer skuId, Integer agentLevelId) throws Exception {
        PfUserSku pfUserSku = null;
        if (pUser == null) {
            throw new BusinessException(" 您的推荐人还未注册，请联系您的推荐人先注册!");
        } else {
            pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(pUser.getId(), skuId);
            if (null == pfUserSku || pfUserSku.getIsPay() == 0) {
                throw new BusinessException("您的推荐人还未代理此款商品");
            }
            if (pfUserSku.getAgentLevelId() >= agentLevelId) {
                throw new BusinessException("您的代理等级不能高于您的推荐人代理等级");
            }
        }
        return pfUserSku;
    }
}
