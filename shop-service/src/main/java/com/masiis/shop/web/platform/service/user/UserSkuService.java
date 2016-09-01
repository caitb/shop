package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.certificate.CertificateInfo;
import com.masiis.shop.dao.platform.certificate.CertificateMapper;
import com.masiis.shop.dao.platform.product.PfSkuAgentMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfSkuAgent;
import com.masiis.shop.dao.po.PfUserSku;
import com.masiis.shop.dao.po.PfUserSkuStock;
import com.masiis.shop.web.common.service.UserService;
import com.masiis.shop.web.platform.service.product.PfUserSkuStockService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * UserSkuService
 *
 * @author ZhaoLiang
 * @date 2016/3/8
 */
@Service
@Transactional
public class UserSkuService {

    private final static Logger log = Logger.getLogger(UserSkuService.class);

    @Resource
    private PfUserSkuMapper pfUserSkuMapper;
    @Resource
    private CertificateMapper certificateMapper;
    @Resource
    private UserSkuService userSkuService;
    @Resource
    private PfUserSkuStockService pfUserSkuStockService;
    @Resource
    private PfSkuAgentMapper pfSkuAgentMapper;
    @Resource
    private UserService userService;



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
     * 获取下级合伙人
     * @author muchaofeng
     * @date 2016/4/19 11:43
     */

    public List<PfUserSku> getAgentNumByUserId(Long userId) throws Exception {
        List<PfUserSku> res = pfUserSkuMapper.selectAgentNum(userId);
        if(res == null || res.size() == 0){
            return null;
        }
        return res;
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
     * @param pUserId 上级合伙人id
     * @param skuId   代理的商品
     * @return 返回上一级代理的用户商品数据
     * @author ZhaoLiang
     * @date 2016/4/1 12:11
     */
    public PfUserSku checkParentData(ComUser comUser, Long pUserId, Integer skuId) throws Exception {
        PfUserSku pfUserSku = null;
        if (pUserId == null || pUserId <= 0) {
            throw new BusinessException(" 上级用户id不合法!");
        }
        ComUser pUser = userService.getUserById(pUserId);
        if (pUser == null) {
            throw new BusinessException(" 您的推荐人还未注册，请联系您的推荐人先注册!");
        } else {
            pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(pUser.getId(), skuId);
            if (null == pfUserSku || pfUserSku.getIsPay() == 0) {
                throw new BusinessException("您的推荐人还未代理此款商品");
            }
        }
        if (comUser.getSendType() != 0 && comUser.getSendType() != pUser.getSendType()) {
            throw new BusinessException(" 您和您的上级合伙人的拿货方式不一致不能建立关系!");
        }

        return pfUserSku;
    }

    /**
     * @param pUserId      上级合伙人id
     * @param skuId        代理的商品
     * @param agentLevelId 自己的代理等级
     * @return 返回上一级代理的用户商品数据
     * @author ZhaoLiang
     * @date 2016/4/1 12:11
     */
    public PfUserSku checkParentData(ComUser comUser, Long pUserId, Integer skuId, Integer agentLevelId) throws Exception {
        PfUserSku pfUserSku = null;
        if (pUserId == null || pUserId <= 0) {
            throw new BusinessException(" 上级用户id不合法!");
        }
        ComUser pUser = userService.getUserById(pUserId);
        if (pUser == null) {
            throw new BusinessException(" 您的推荐人还未注册，请联系您的推荐人先注册!");
        } else {
            pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(pUser.getId(), skuId);
            if (null == pfUserSku || pfUserSku.getIsPay() == 0) {
                throw new BusinessException("您的推荐人还未代理此款商品");
            }
            if (pfUserSku.getAgentLevelId() >= agentLevelId) {
                throw new BusinessException("您的代理等级不能高于或等于您的推荐人代理等级!");
            }
        }
        //拿货方式: 0,未选择; 1,平台代发; 2,自己发货
        if (pUser.getSendType() == 2) {
            PfUserSkuStock pfUserSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(pUser.getId(), skuId);
            if (pfUserSkuStock == null) {
                throw new BusinessException("您的推荐人商品库存不足!");
            }
            if (pfUserSkuStock.getCustomStock() <= 0) {
                throw new BusinessException("您的推荐人商品库存不足!");
            } else {
                PfSkuAgent pfSkuAgent = pfSkuAgentMapper.selectBySkuIdAndLevelId(skuId, agentLevelId);
                if (pfSkuAgent == null) {
                    throw new BusinessException("您代理的商品有误!");
                } else {
                    if (pfUserSkuStock.getCustomStock() < pfSkuAgent.getQuantity()) {
                        throw new BusinessException("您的推荐人商品库存不足!");
                    }
                }
            }
        }
        if (comUser.getSendType() != 0 && comUser.getSendType() != pUser.getSendType()) {
            throw new BusinessException(" 您和您的上级合伙人的拿货方式不一致不能建立关系!");
        }
        return pfUserSku;
    }

    /**
     * 下级合伙人 (姓名 id) 列表
     * @param userPid
     * @return
     */
    public List<Map<String, Object>> getLowerLevelPartnerListByUserPid(Long userPid){
        return pfUserSkuMapper.getLowerLevelPartnerListByUserPid(userPid);
    }

}
