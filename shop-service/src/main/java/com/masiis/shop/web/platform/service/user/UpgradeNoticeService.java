package com.masiis.shop.web.platform.service.user;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.masiis.shop.common.enums.platform.UpGradeStatus;
import com.masiis.shop.common.enums.platform.UpGradeUpStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.OrderMakeUtils;
import com.masiis.shop.dao.beans.order.BOrderAdd;
import com.masiis.shop.dao.beans.order.BOrderUpgradeDetail;
import com.masiis.shop.dao.beans.user.PfUserUpGradeInfo;
import com.masiis.shop.dao.beans.user.upgrade.UpGradeInfoPo;
import com.masiis.shop.dao.beans.user.upgrade.UpgradeManagePo;
import com.masiis.shop.dao.platform.user.PfUserRebateMapper;
import com.masiis.shop.dao.platform.user.PfUserUpgradeNoticeMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.common.service.ComAgentLevelService;
import com.masiis.shop.web.common.service.SkuService;
import com.masiis.shop.web.common.service.UserService;
import com.masiis.shop.web.platform.service.order.BOrderAddService;
import com.masiis.shop.web.platform.service.product.SkuAgentService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by JingHao on 2016/6/15
 * 升级管理service
 */
@Service
@Transactional
public class UpgradeNoticeService {
    private static final Logger logger = Logger.getLogger(UpgradeNoticeService.class);
    @Resource
    private PfUserUpgradeNoticeMapper pfUserUpgradeNoticeMapper;
    @Resource
    private PfUserRebateMapper pfUserRebateMapper;
    @Resource
    private SkuService comSkuService;
    @Resource
    private UserService comUserService;
    @Resource
    private ComAgentLevelService comAgentLevelService;
    @Resource
    private SkuAgentService skuAgentService;
    @Resource
    private PfUserSkuService pfUserSkuService;
    @Resource
    private SkuService skuService;
    @Resource
    private UpgradeNoticeService upgradeNoticeService;
    @Resource
    private BOrderAddService bOrderAddService;
    @Resource
    private UpgradeWechatNewsService upgradeWechatNewsService;
    @Resource
    private UserService userService;

    private static final Integer pageSize = 10;
    /**
     * 根据订单id查询通知单
     * @param orderId
     * @return
     */
    public PfUserUpgradeNotice selectByPfBorderId(Long orderId){
        return pfUserUpgradeNoticeMapper.selectByPfBorderId(orderId);
    }

	/**
     * 通过id查询申请单数据
     * @param id            申请单id
     * @return
     * @throws Exception
     */
    public PfUserUpgradeNotice getUpgradeNoticeById(Long id){
        return pfUserUpgradeNoticeMapper.selectByPrimaryKey(id);
    }
    /**
     * jjh
     * 我的升级申请记录
     * @param userId
     * @return
     */
    public List<PfUserUpgradeNotice> getPfUserUpGradeInfoByUserId(Long userId) throws Exception{
        return pfUserUpgradeNoticeMapper.selectByUserId(userId);
    }

    /**
     * jjh
     * 我的下级申请记录
     * @param userId
     * @return
     */
    public List<PfUserUpgradeNotice> getPfUserUpGradeInfoByUserPId(Long userId) throws Exception {
        return pfUserUpgradeNoticeMapper.selectByUserPId(userId);
    }

    /**
     * jjh
     * 一次性返利<获得的返利>
     * @param userId
     * @return
     */
    public List<PfUserRebate> getPfUserRebateByUserId(Long userId) throws Exception{
        return pfUserRebateMapper.selectByUserId(userId);
    }

    /**
     * jjh
     * 一次性返利<支付的返利>
     * @param userId
     * @return
     */
    public List<PfUserRebate> getPfUserRebateByUserPId(Long userId) throws Exception{
        return pfUserRebateMapper.selectByUserPId(userId);
    }

    /**
     * 一次性返利<支付的返利,获得的返利>
     * @param userId
     * @return
     * @throws Exception
     */
    public List<PfUserRebate> getPfUserRebateByUserPIdOrUserId(Long userId) throws Exception{
        return pfUserRebateMapper.selectByUserPIdOrUserId(userId);
    }

    /**
     * jjh
     * 根据主键获取申请信息
     * @param id
     * @return
     */
    public PfUserUpgradeNotice getPfUserUpGradeInfoByPrimaryKey(Long id) throws Exception{
        return pfUserUpgradeNoticeMapper.selectByPrimaryKey(id);
    }

    /**
     * jjh
     * 组合查询
     * @param UserPId 我作为上级
     * @param skuId 商品
     * @param upStatus 申请状态
     * @return
     * @throws Exception
     */
    public List<PfUserUpgradeNotice> getPfUserUpGradeInfoByParam(Long UserPId, Integer skuId, Integer upStatus) throws Exception {
        return pfUserUpgradeNoticeMapper.selectByParam(UserPId, skuId, upStatus);
    }

    /**
     * 申请单、一次性返利查询Service
     * @param comUser       comUser
     * @param pageNum       页码
     * @param tab           tab 1：我的申请单  2：下级申请单  3：一次性返利
     * @param skuId         商品id
     * @param upStatus      申请状态
     * @param rebateType    一次性返利   0：获得返利   1：支付返利
     * @return
     * @throws Exception
     */
    public UpgradeManagePo getPfUserUpGradeInfoPaging(ComUser comUser, Integer pageNum, int tab, Integer skuId, Integer upStatus, Integer rebateType) throws Exception{
        logger.info("我的升级申请记录Service");
        logger.info("传过来的页码：" + pageNum);
        UpgradeManagePo upgradeManagePo = new UpgradeManagePo();
        List<PfUserUpGradeInfo> pfUserUpGradeInfoList = new ArrayList<>();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Page pageHelp = null;
        List<PfUserUpgradeNotice> pfUserUpgradeNoticeList = null;
        switch (tab){
            case 1 : {
                pageHelp = PageHelper.startPage(pageNum.intValue(), pageSize);
                pfUserUpgradeNoticeList = pfUserUpgradeNoticeMapper.selectByUserId(comUser.getId());
                break;
            }
            case 2 : {
                pageHelp = PageHelper.startPage(pageNum.intValue(), pageSize);
                pfUserUpgradeNoticeList = pfUserUpgradeNoticeMapper.selectByParam(comUser.getId(), skuId, upStatus);
                break;
            }
            case 3 : {
                pageHelp = PageHelper.startPage(pageNum.intValue(), pageSize);
                if (rebateType == null){
                    pfUserUpgradeNoticeList = pfUserUpgradeNoticeMapper.selectBySkuIdAndRebateALLType(skuId, comUser.getId());
                } else if (rebateType.intValue() == 0) {//获得返利
                    pfUserUpgradeNoticeList = pfUserUpgradeNoticeMapper.selectBySkuIdAndRebateType(skuId, comUser.getId(), null);
                } else if (rebateType.intValue() == 1) { //支付返利
                    pfUserUpgradeNoticeList = pfUserUpgradeNoticeMapper.selectBySkuIdAndRebateType(skuId, null, comUser.getId());
                }
            }
        }
        logger.info("总数量：" + pageHelp.getTotal());
        logger.info("总页数：" + pageHelp.getPages());
        logger.info("当前页：" + pageHelp.getPageNum());
        logger.info("每页展示条数：" + pageHelp.getPageSize());
        if (pageHelp.getPages() > 0){
            if (pageHelp.getPages() < pageNum.intValue()){
                throw new BusinessException("1");
            }
        }
        if (pfUserUpgradeNoticeList != null && pfUserUpgradeNoticeList.size() > 0) {
            for (PfUserUpgradeNotice pfUserUpgradeNotice : pfUserUpgradeNoticeList) {
                PfUserUpGradeInfo pfUserUpGradeInfo = new PfUserUpGradeInfo();
                pfUserUpGradeInfo.setPfUserUpgradeNotice(pfUserUpgradeNotice);
                ComSku comSku = skuService.getSkuById(pfUserUpgradeNotice.getSkuId());
                ComAgentLevel orglevel = comAgentLevelService.selectByPrimaryKey(pfUserUpgradeNotice.getOrgAgentLevelId());
                ComAgentLevel wishLevel = comAgentLevelService.selectByPrimaryKey(pfUserUpgradeNotice.getWishAgentLevelId());
                pfUserUpGradeInfo.setSkuName(comSku.getName());
                if (tab == 1){
                    pfUserUpGradeInfo.setWxHeadImg(comUser.getWxHeadImg());
                    pfUserUpGradeInfo.setRealName(comUser.getRealName());
                }else {
                    ComUser user = comUserService.getUserById(pfUserUpgradeNotice.getUserId());
                    pfUserUpGradeInfo.setWxHeadImg(user.getWxHeadImg());
                    pfUserUpGradeInfo.setRealName(user.getRealName());
                }
                pfUserUpGradeInfo.setOrgLevelName(orglevel.getName());
                pfUserUpGradeInfo.setWishLevelName(wishLevel.getName());
                String sDate = sdf.format(pfUserUpgradeNotice.getCreateTime());
                pfUserUpGradeInfo.setCreateDate(sDate);
                pfUserUpGradeInfo.setStatusValue(upgradeNoticeService.coverCodeByLowerUpgrade(pfUserUpgradeNotice.getStatus()));
                pfUserUpGradeInfoList.add(pfUserUpGradeInfo);
            }
        }
        upgradeManagePo.setUpGradeInfos(pfUserUpGradeInfoList);
        upgradeManagePo.setTotoalCount(pageHelp.getTotal());
        upgradeManagePo.setTotalPage(pageHelp.getPages());
        upgradeManagePo.setCurrentPage(pageHelp.getPageNum());
        upgradeManagePo.setPageSize(pageHelp.getPageSize());
        return upgradeManagePo;
    }


    /**
     * jjh
     * 组合查询
     * @param skuId 商品
     * @param userPid 支付返利
     * @param userId 获取返利
     * @return
     */
    public List<PfUserUpgradeNotice> getPfUserUpGradeInfoByRebateAndSkuId(Integer skuId, Long userPid, Long userId) {
        return pfUserUpgradeNoticeMapper.selectBySkuIdAndRebateType(skuId, userPid, userId);
    }

    public List<PfUserUpgradeNotice> getPfUserUpGradeInfoByALLRebateAndSkuId(Integer skuId,Long userId) {
        return pfUserUpgradeNoticeMapper.selectBySkuIdAndRebateALLType(skuId, userId);
    }

    public String coverCodeByMyUpgrade(Integer upStatus) {
        return UpGradeStatus.statusPickList.get(upStatus);
    }

    public String coverCodeByLowerUpgrade(Integer upStatus) {
        return UpGradeStatus.statusPickList.get(upStatus);
    }
	/**
     * 处理代理用户升级
     * @param userId        代理用户id
     * @param userPid       代理用户上级id
     * @param curAgentLevel 代理用户当前代理等级
     * @param upgradeLevel  代理用户申请代理等级
     * @param pAgentLevel   代理用户上级代理等级
     * @param skuId         合伙skuId
     * @return              主键
     * @auth:wbj
     * @throws Exception
     */
    public PfUserUpgradeNotice dealAgentUpGrade(Long userId, Long userPid, Integer curAgentLevel, Integer upgradeLevel, Integer pAgentLevel, Integer skuId) throws Exception{
        logger.info("查询是否已经有申请单");
        List<PfUserUpgradeNotice> upgradeNotices = pfUserUpgradeNoticeMapper.selectBySkuIdAndUserIdAndUserPid(skuId, userPid, userId);
        if (upgradeNotices != null & upgradeNotices.size()>0){
            for (PfUserUpgradeNotice upGrade : upgradeNotices){
                if (upGrade.getStatus().intValue() == UpGradeStatus.STATUS_Untreated.getCode().intValue() ||
                    upGrade.getStatus().intValue() == UpGradeStatus.STATUS_NoPayment.getCode().intValue() ||
                    upGrade.getStatus().intValue() == UpGradeStatus.STATUS_Processing.getCode().intValue())
                {
                    throw new BusinessException("当前还有未处理完成的升级申请单");
                }
            }
        }
        logger.info("判断上级代理是否可以升级");
        PfUserSku pfUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(userPid,skuId);
        if (pfUserSku == null){
            throw new BusinessException("上级代理商品信息为空");
        }
        logger.info("代理用户id："+userId);
        logger.info("代理用户上级id："+userPid);
        logger.info("代理用户当前代理等级："+curAgentLevel);
        logger.info("代理用户申请代理等级："+upgradeLevel);
        logger.info("代理用户上级代理等级："+pAgentLevel);
        logger.info("合伙skuId："+skuId);
        Long userPpid = pfUserSku.getUserPid();
        logger.info("上级代理的上级代理用户id："+userPpid);
        if (userPpid.longValue() == 0){
            logger.info("上级代理用户不可以向上升级");
            return upAgentCannotUpgrade(userId, userPid, curAgentLevel, upgradeLevel, pAgentLevel, skuId);
        }else {
            PfUserSku pfPUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(userPpid,skuId);
            if (pfPUserSku == null){
                throw new BusinessException("商品代理数据为空");
            }
            List<PfSkuAgent> pfSkuAgents = pfUserSkuService.getUpgradeAgents(skuId, pAgentLevel, pfPUserSku.getAgentLevelId());
            if (pfSkuAgents == null || pfSkuAgents.size() == 0){
                logger.info("上级代理用户不可以向上升级");
                return upAgentCannotUpgrade(userId, userPid, curAgentLevel, upgradeLevel, pAgentLevel, skuId);
            }else {
                logger.info("上级代理用户可以向上升级");
                return upAgentCanUpgrade(userId, userPid, curAgentLevel, upgradeLevel, pAgentLevel, skuId);
            }
        }
    }

    /**
     * 处理代理用户升级(api接口调用，直接生成订单)
     * @param userId        代理用户id
     * @param userPid       代理用户上级id
     * @param curAgentLevel 代理用户当前代理等级
     * @param upgradeLevel  代理用户申请代理等级
     * @param pAgentLevel   代理用户上级代理等级
     * @param skuId         合伙skuId
     * @return              主键
     * @auth:wbj
     * @throws Exception
     */
    public PfUserUpgradeNotice dealAgentUpGradeApi(Long userId, Long userPid, Integer curAgentLevel, Integer upgradeLevel, Integer pAgentLevel, Integer skuId)throws Exception{
        PfUserUpgradeNotice upgradeNotice = dealAgentUpGrade(userId, userPid, curAgentLevel, upgradeLevel, pAgentLevel, skuId);
//        //添加升级订单
//        BOrderUpgradeDetail upgradeDetail = upgradeNoticeService.getUpgradeNoticeInfo(upgradeNotice.getId());
//        if(upgradeNotice.getPfBorderId() == null){
//            //插入订单表
//            PfSkuAgent newSkuAgent = skuAgentService.getBySkuIdAndLevelId(upgradeDetail.getSkuId(), upgradeDetail.getApplyAgentLevel());
//            BOrderAdd orderAdd = new BOrderAdd();
//            orderAdd.setUpgradeNoticeId(upgradeNotice.getId());
//            logger.info("升级订单对应的通知单id--------" + upgradeNotice.getId());
//            orderAdd.setOrderType(3);
//            orderAdd.setUserId(userId);
//            orderAdd.setOldPUserId(upgradeDetail.getOldPUserId());
//            orderAdd.setpUserId(upgradeDetail.getNewPUserId() == null?0:upgradeDetail.getNewPUserId());//设置新的上级
//            logger.info("新上级id----------" + upgradeDetail.getNewPUserId());
//            orderAdd.setSendType(1);//拿货方式
//            orderAdd.setSkuId(upgradeDetail.getSkuId());
//            orderAdd.setQuantity(newSkuAgent.getQuantity());
//            logger.info("订单数量---------" + newSkuAgent.getQuantity());
//            orderAdd.setCurrentAgentLevel(upgradeDetail.getCurrentAgentLevel());
//            orderAdd.setAgentLevelId(upgradeDetail.getApplyAgentLevel());
//            logger.info("原始等级--------" + upgradeDetail.getCurrentAgentLevel());
//            logger.info("期望等级--------" + upgradeDetail.getApplyAgentLevel());
//            orderAdd.setUserSource(0);
//            Long orderId = bOrderAddService.addBOrder(orderAdd);
//            logger.info("添加的升级订单id = " + orderId);
//            //升级申请表添加orderId
//            upgradeNotice.setPfBorderId(orderId);
//            upgradeNoticeService.updateUpgradeNotice(upgradeNotice);
//        }
        return upgradeNotice;
    }

    /**
     * 处理代理用户升级申请单（上级可以申请）
     * @param userId        代理用户id
     * @param userPid       代理用户上级id
     * @param curAgentLevel 代理用户当前代理等级
     * @param upgradeLevel  代理用户申请代理等级
     * @param pAgentLevel   代理用户上级代理等级
     * @param skuId         合伙skuId
     * @return              主键
     * @auth:wbj
     * @throws Exception
     */
    public PfUserUpgradeNotice upAgentCanUpgrade(Long userId, Long userPid, Integer curAgentLevel, Integer upgradeLevel, Integer pAgentLevel, Integer skuId) throws Exception{
        PfUserUpgradeNotice upgradeNotice = new PfUserUpgradeNotice();
        logger.info("生成申请单号begin");
        String code = OrderMakeUtils.makeOrder("U");
        logger.info("生成申请单号end 申请单号："+code);
        Date date = new Date();
        upgradeNotice.setCode(code);
        upgradeNotice.setCreateTime(date);
        upgradeNotice.setUserId(userId); //申请人id
        upgradeNotice.setUserPid(userPid);  //申请人上级合伙人id
        upgradeNotice.setSkuId(skuId);      //申请升级skuid
        upgradeNotice.setOrgAgentLevelId(curAgentLevel);    //原合伙代理等级
        upgradeNotice.setWishAgentLevelId(upgradeLevel);    //申请合伙代理等级
        upgradeNotice.setUpdateTime(date);
        if (upgradeLevel.intValue() == pAgentLevel.intValue()){
            logger.info("代理用户申请代理等级等于上级代理等级");
            logger.info("查询是否已经有类似的申请单begin");
            PfUserUpgradeNotice pUserUpgrade = pfUserUpgradeNoticeMapper.selectLastUpgrade(userPid, skuId, upgradeLevel);
            logger.info("查询是否已经有类似的申请单end");
            if (pUserUpgrade == null){
                logger.info("上级没有申请单：设置状态为未处理状态");
                upgradeNotice.setStatus(UpGradeStatus.STATUS_Untreated.getCode());
                upgradeNotice.setUpStatus(UpGradeUpStatus.UP_STATUS_Untreated.getCode());
            }else {
                logger.info("上级有申请单：根据上级申请单的status处理");
                if (pUserUpgrade.getUpStatus().intValue() == UpGradeUpStatus.UP_STATUS_NotUpgrade.getCode() || pUserUpgrade.getUpStatus().intValue() == UpGradeUpStatus.UP_STATUS_Untreated.getCode().intValue()){
                    upgradeNotice.setStatus(UpGradeStatus.STATUS_Untreated.getCode());
                    upgradeNotice.setUpStatus(UpGradeUpStatus.UP_STATUS_Untreated.getCode());
                }
                if (pUserUpgrade.getUpStatus().intValue() == UpGradeUpStatus.UP_STATUS_Upgrade.getCode().intValue()){
                    upgradeNotice.setStatus(UpGradeStatus.STATUS_Processing.getCode());
                    upgradeNotice.setUpStatus(UpGradeUpStatus.UP_STATUS_Upgrade.getCode().intValue());
                }
                if (pUserUpgrade.getUpStatus().intValue() == UpGradeUpStatus.UP_STATUS_Complete.getCode().intValue()){
                    logger.info("查询上级当前等级");
                    PfUserSku pfUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(userPid,skuId);
                    if (pfUserSku.getAgentLevelId().intValue() > pUserUpgrade.getUpStatus().intValue()){
                        upgradeNotice.setStatus(UpGradeStatus.STATUS_NoPayment.getCode());
                        upgradeNotice.setUpStatus(UpGradeUpStatus.UP_STATUS_Complete.getCode());
                    }
                }
            }
        }else {
            upgradeNotice.setStatus(UpGradeStatus.STATUS_NoPayment.getCode());
            upgradeNotice.setUpStatus(UpGradeUpStatus.UP_STATUS_Complete.getCode());
            logger.info("代理用户申请代理等级小于上级代理等级");
        }
        upgradeNotice.setRemark("升级提交申请");
        if (pfUserUpgradeNoticeMapper.insert(upgradeNotice) == 0){
            throw new BusinessException("创建升级申请数据失败");
        }
        //创建申请单完成后处理下级代理申请单
        this.dealLowerUpgrades(userId, skuId, curAgentLevel);
        return upgradeNotice;
    }

    /**
     * 处理代理用户升级申请单（上级不可以升级）
     * @param userId        代理用户id
     * @param userPid       代理用户上级id
     * @param curAgentLevel 代理用户当前代理等级
     * @param upgradeLevel  代理用户申请代理等级
     * @param pAgentLevel   代理用户上级代理等级
     * @param skuId         合伙skuId
     * @return              主键
     * @auth:wbj
     * @throws Exception
     */
    public PfUserUpgradeNotice upAgentCannotUpgrade(Long userId, Long userPid, Integer curAgentLevel, Integer upgradeLevel, Integer pAgentLevel, Integer skuId) throws Exception{
        PfUserUpgradeNotice upgradeNotice = new PfUserUpgradeNotice();
        logger.info("生成申请单号begin");
        String code = OrderMakeUtils.makeOrder("U");
        logger.info("生成申请单号end 申请单号："+code);
        Date date = new Date();
        upgradeNotice.setCode(code);
        upgradeNotice.setCreateTime(date);
        upgradeNotice.setUserId(userId); //申请人id
        upgradeNotice.setUserPid(userPid);  //申请人上级合伙人id
        upgradeNotice.setSkuId(skuId);      //申请升级skuid
        upgradeNotice.setOrgAgentLevelId(curAgentLevel);    //原合伙代理等级
        upgradeNotice.setWishAgentLevelId(upgradeLevel);    //申请合伙代理等级
        upgradeNotice.setUpdateTime(date);
        upgradeNotice.setStatus(UpGradeStatus.STATUS_NoPayment.getCode());
        upgradeNotice.setUpStatus(UpGradeUpStatus.UP_STATUS_Complete.getCode());
        upgradeNotice.setRemark("上级代理用户不可升级");
        if (pfUserUpgradeNoticeMapper.insert(upgradeNotice) == 0){
            throw new BusinessException("创建升级申请数据失败");
        }
        //创建申请单完成后处理下级代理申请单
        this.dealLowerUpgrades(userId, skuId, curAgentLevel);
        return upgradeNotice;
    }

    /**
     * 创建完升级订单后处理下级申请单
     * @param userId        代理用户id
     * @param skuId         合伙skuId
     * @param curAgentLevel 代理用户当前代理等级
     * @throws Exception
     */
    private void dealLowerUpgrades(Long userId, Integer skuId, Integer curAgentLevel) throws Exception{
        logger.info("创建升级申请单后处理下级申请单数据");
        PfUserUpgradeNotice notice = new PfUserUpgradeNotice();
        notice.setUserPid(userId);
        notice.setSkuId(skuId);
        notice.setWishAgentLevelId(curAgentLevel);
        List<PfUserUpgradeNotice> pfUserUpgradeNotices = pfUserUpgradeNoticeMapper.selectByCondition(notice);
        for (PfUserUpgradeNotice upgrade : pfUserUpgradeNotices){
            //当申请用户处理状态为未处理时进行数据更新
            if (upgrade.getStatus().intValue() == UpGradeStatus.STATUS_Untreated.getCode().intValue()){
                upgrade.setUpdateTime(new Date());
                upgrade.setUpStatus(UpGradeUpStatus.UP_STATUS_Upgrade.getCode());
                upgrade.setStatus(UpGradeStatus.STATUS_Processing.getCode());
                upgrade.setRemark("上级代理提交升级申请");
                //更新代理升级申请信息
                updateUpgradeNotice(upgrade);
            }
        }
    }
    /**
     * 根据id查询申请信息
     * @param id 申请信息表id
     * @return
     */
    public UpGradeInfoPo getUpGradeInfo(Long id){
        logger.info("根据id查询申请信息 id=" + id);
        return pfUserUpgradeNoticeMapper.selectUpGradeInfoPoById(id);
    }

    /**
     * 更新升级申请信息
     * @param pfUserUpgradeNotice
     * @return
     */
    public int updateUpgradeNotice(PfUserUpgradeNotice pfUserUpgradeNotice) throws Exception{
        int a = pfUserUpgradeNoticeMapper.updateByPrimaryKey(pfUserUpgradeNotice);
        if (a == 0){
            throw new BusinessException("处理失败");
        }
        return a;
    }

    /**
     * 代理暂不升级处理下级申请单数据
     * @param pfUserUpgradeNotice
     * @throws Exception
     */
    public void dealLowerUpgradeNotice(PfUserUpgradeNotice pfUserUpgradeNotice) throws Exception{
        //当申请用户处理状态不是取消状态则将处理状态设置为待支付状态
        if (pfUserUpgradeNotice.getStatus().intValue() != UpGradeStatus.STATUS_Revocation.getCode().intValue()){
//            PfUserUpgradeNotice upgrade = new PfUserUpgradeNotice();
//            upgrade.setSkuId(pfUserUpgradeNotice.getSkuId());
//            upgrade.setUserPid(pfUserUpgradeNotice.getUserPid());
//            upgrade.setWishAgentLevelId(pfUserUpgradeNotice.getWishAgentLevelId());
//            List<PfUserUpgradeNotice> upgradeNotices = pfUserUpgradeNoticeMapper.selectByCondition(upgrade);
//            if (upgradeNotices == null || upgradeNotices.size() == 0){
//                throw new BusinessException("无下级代理申请信息");
//            }
//            for (PfUserUpgradeNotice upgradeNotice : upgradeNotices){
//                upgradeNotice.setUpdateTime(new Date());
//                upgradeNotice.setStatus(UpGradeStatus.STATUS_NoPayment.getCode());
//                upgradeNotice.setUpStatus(UpGradeUpStatus.UP_STATUS_NotUpgrade.getCode());
//                upgradeNotice.setRemark("上级代理暂不升级");
//                //更新代理升级申请信息
//                updateUpgradeNotice(upgradeNotice);
//            }
            pfUserUpgradeNotice.setUpdateTime(new Date());
            pfUserUpgradeNotice.setStatus(UpGradeStatus.STATUS_NoPayment.getCode());
            pfUserUpgradeNotice.setUpStatus(UpGradeUpStatus.UP_STATUS_NotUpgrade.getCode());
            pfUserUpgradeNotice.setRemark("上级代理暂不升级");
            //更新代理升级申请信息
            updateUpgradeNotice(pfUserUpgradeNotice);
        }
    }

    /**
     * 订单界面获得升级通知信息
     * @param id   通知单id
     */
    public BOrderUpgradeDetail getUpgradeNoticeInfo(Long id) {
        //升级订单通知信息
        if (id==null){
            throw new BusinessException("升级通知信息id为null");
        }
        logger.info("获取升级通知信息------start");
        logger.info("获取升级通知信息------id----"+id);

        PfUserUpgradeNotice upgradeNotice = pfUserUpgradeNoticeMapper.selectByPrimaryKey(id);
        ComUser user = userService.getUserById(upgradeNotice.getUserId());
        //添加升级订单
        BOrderUpgradeDetail detail = upgradeNoticeService.getUpgradeNoticeInfo(upgradeNotice.getId());
        Long orderId = null;
        try {
            if(upgradeNotice.getPfBorderId() == null){
                //插入订单表
                PfSkuAgent newSkuAgent = skuAgentService.getBySkuIdAndLevelId(detail.getSkuId(), detail.getApplyAgentLevel());
                BOrderAdd orderAdd = new BOrderAdd();
                orderAdd.setUpgradeNoticeId(upgradeNotice.getId());
                logger.info("升级订单对应的通知单id--------" + upgradeNotice.getId());
                orderAdd.setOrderType(3);
                orderAdd.setUserId(user.getId());
                orderAdd.setOldPUserId(detail.getOldPUserId());
                orderAdd.setpUserId(detail.getNewPUserId() == null?0:detail.getNewPUserId());//设置新的上级
                logger.info("新上级id----------" + detail.getNewPUserId());
                orderAdd.setSendType(1);//拿货方式
                orderAdd.setSkuId(detail.getSkuId());
                orderAdd.setQuantity(newSkuAgent.getQuantity());
                logger.info("订单数量---------" + newSkuAgent.getQuantity());
                orderAdd.setCurrentAgentLevel(detail.getCurrentAgentLevel());
                orderAdd.setAgentLevelId(detail.getApplyAgentLevel());
                logger.info("原始等级--------" + detail.getCurrentAgentLevel());
                logger.info("期望等级--------" + detail.getApplyAgentLevel());
                orderAdd.setUserSource(0);
                orderId = bOrderAddService.addBOrder(orderAdd);
                logger.info("添加的升级订单id = " + orderId);
                //升级申请表添加orderId
                upgradeNotice.setPfBorderId(orderId);
                upgradeNoticeService.updateUpgradeNotice(upgradeNotice);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("添加升级订单失败");
        }

        BOrderUpgradeDetail upgradeDetail = null;
        if (upgradeNotice!=null){
            //验证条件是否可以进入
            if (true){
                upgradeDetail = new BOrderUpgradeDetail();
                upgradeDetail.setUpgradeNoticeId(id);
                upgradeDetail.setUpgradeOrderCode(upgradeNotice.getCode());
                upgradeDetail.setPfBorderId(orderId);
                logger.info("订单id-------"+upgradeDetail.getPfBorderId());
                upgradeDetail.setUpgradeStatus(upgradeNotice.getStatus());
                logger.info("上级状态--------"+upgradeDetail.getUpStatus());
                upgradeDetail.setUpStatus(upgradeNotice.getUpStatus());
                ComUser oldComUser = comUserService.getUserById(upgradeNotice.getUserPid());
                if (oldComUser!=null){
                    logger.info("旧上级-------"+upgradeNotice.getUserPid());
                    upgradeDetail.setOldPUserId(upgradeNotice.getUserPid());
                    upgradeDetail.setOldPUserName(oldComUser.getRealName());
                    Long newComUserId = getNewComUserId(upgradeDetail.getOldPUserId(),upgradeNotice.getSkuId(),upgradeNotice.getWishAgentLevelId());
                    if (newComUserId!=null){
                        logger.info("新上级id----------"+newComUserId);
                        ComUser newComUser = comUserService.getUserById(newComUserId);
                        if (newComUser!=null){
                            upgradeDetail.setNewPUserId(newComUserId);
                            upgradeDetail.setNewPUserName(newComUser.getRealName());
                        }
                    }
                }
                //商品信息
                ComSku comSku = getComSku(upgradeNotice.getSkuId());
                if (comSku!=null){
                    upgradeDetail.setSkuId(comSku.getId());
                    upgradeDetail.setSpuId(comSku.getSpuId());
                    upgradeDetail.setSkuName(comSku.getName());
                    upgradeDetail.setPriceRetail(comSku.getPriceRetail());
                    logger.info("商品销售价-------"+comSku.getPriceRetail());
                }else{
                    logger.info("商品信息为null-----id--"+upgradeNotice.getSkuId());
                    throw new BusinessException("商品信息为null-----id--"+upgradeNotice.getSkuId());
                }
                //级别
                logger.info("获得商品代理原等级信息----商品skuId----"+comSku.getId()+"-----原始等级id----"+upgradeNotice.getOrgAgentLevelId());
                PfSkuAgent oldSkuAgent = getPfSkuAgent(comSku.getId(),upgradeNotice.getOrgAgentLevelId());
                logger.info("获得商品代理新等级信息----商品skuId----"+comSku.getId()+"-----期望等级id----"+upgradeNotice.getWishAgentLevelId());
                PfSkuAgent newSkuAgent = getPfSkuAgent(comSku.getId(),upgradeNotice.getWishAgentLevelId());
                if (oldSkuAgent!=null){
                    ComAgentLevel oldAgentLevel = getComAgentLeveal(oldSkuAgent.getAgentLevelId());
                    upgradeDetail.setCurrentAgentLevel(upgradeNotice.getOrgAgentLevelId());
                    upgradeDetail.setCurrentAgentLevelName(oldAgentLevel.getName());
                }else{
                    logger.info("原始等级商品代理为null");
                    throw new BusinessException("原始等级商品代理为null");
                }
                if (newSkuAgent!=null){
                    ComAgentLevel newAgentLevel = getComAgentLeveal(newSkuAgent.getAgentLevelId());
                    upgradeDetail.setApplyAgentLevel(newSkuAgent.getAgentLevelId());
                    upgradeDetail.setApplyAgentLevelName(newAgentLevel.getName());
                }else{
                    logger.info("新等级商品代理为null");
                    throw new BusinessException("新等级商品代理为null");
                }
                //拿货数量
                upgradeDetail.setQuantity(newSkuAgent.getQuantity());
                //保证金差额
                upgradeDetail.setBailChange(getBailChange(oldSkuAgent,newSkuAgent));
                logger.info("保证金差额------"+upgradeDetail.getBailChange());
                //总价
                BigDecimal totalPrice = getTotalPrice(oldSkuAgent,newSkuAgent);
                logger.info("总价------"+totalPrice);
                upgradeDetail.setTotalPrice(totalPrice);
            }else{
                logger.info("升级通知信息状态不正确-----");
                throw new BusinessException("升级通知信息状态不正确-----");
            }
        }else{
            logger.info("升级通知信息为null------id----"+id);
        }
        logger.info("获取升级通知信息------end");
        return upgradeDetail;
    }

    private Long getNewComUserId(Long oldPUserId,Integer skuId,Integer applyAgentLevel){
        PfUserSku _parentPfUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(oldPUserId, skuId);
        PfSkuAgent pfSkuAgent = skuAgentService.getBySkuIdAndLevelId(skuId, applyAgentLevel);
        Long newPUserId = null;
        if (pfSkuAgent != null) {
            if (pfSkuAgent.getIsUpgrade() == 1) {
                Integer pUserAgentLevel = null;
                if (_parentPfUserSku != null) {
                    pUserAgentLevel = _parentPfUserSku.getAgentLevelId();
                        /*if (_parentPfUserSku.getUserPid()!=0){
                            pUserAgentLevel = _parentPfUserSku.getAgentLevelId();
                        }else{
                            logger.info("联合创始人不能升级到boss------当前用户id----"+bOrderAdd.getpUserId()+"----skuId---"+bOrderAdd.getSkuId()+"----上级用户----");
                            throw new BusinessException("联合创始人不能升级到boss");
                        }*/
                } else {
                    logger.info("查询父级userSku为null---");
                    throw new BusinessException("查询父级userSku为null------");
                }
                if (pUserAgentLevel - applyAgentLevel < 0) {
                    //直接升级  上级的等级大于用户要升级的等级
                    newPUserId = _parentPfUserSku.getUserId();
                } else if (pUserAgentLevel - applyAgentLevel == 0) {
                    //平级升级   上级的等级=用户要升级的等级
                    newPUserId = _parentPfUserSku.getUserPid();
                } else {
                    logger.info("申请等级超过了上级的等级-----skuId---" + skuId);
                    logger.info("----升级等级----" + applyAgentLevel);
                    throw new BusinessException("申请登记超过了上级的等级");
                }
            } else {
                logger.info("此等级商品不支持升级------skuId----" + skuId + "-----等级Id----" + applyAgentLevel);
                throw new BusinessException("此等级商品不支持升级------skuId----" + skuId + "-----等级Id----" + applyAgentLevel);
            }
        }
        return newPUserId;
    }

    /**
     * 商品信息
     * @param skuId
     * @return
     */
    private ComSku getComSku (Integer skuId){
        return comSkuService.getSkuById(skuId);
    }

    /**
     * 获取保证金的差额
     * @param oldSkuAgent  旧代理商品
     * @param newSkuAgent  新代理商品
     * @return
     */
    private BigDecimal getBailChange(PfSkuAgent oldSkuAgent,PfSkuAgent newSkuAgent){
        if (oldSkuAgent!=null&&newSkuAgent!=null){
            logger.info("保证金差额-------"+newSkuAgent.getBail().subtract(oldSkuAgent.getBail()).intValue());
            return newSkuAgent.getBail().subtract(oldSkuAgent.getBail());
        }else{
            logger.info("获取保证金差额失败");
            throw new BusinessException("获取保证金差额失败");
        }
    }

    /**
     * 获取订单的总价  = 新代理产品总价（数量*单价） + 保证金差额
     * @param oldSkuAgent
     * @param newSkuAgent
     * @return
     */
    private BigDecimal getTotalPrice(PfSkuAgent oldSkuAgent,PfSkuAgent newSkuAgent){
        BigDecimal totalPice = null;
        if (oldSkuAgent!=null&&newSkuAgent!=null){
            BigDecimal bailChange = getBailChange(oldSkuAgent,newSkuAgent);
            totalPice = newSkuAgent.getTotalPrice().add(bailChange);
        }else{
            totalPice = BigDecimal.ZERO ;
        }
        return totalPice;
    }

    /**
     * 获得商品代理等级信息
     * @param skuId
     * @param levelId
     * @return
     */
    private PfSkuAgent getPfSkuAgent(Integer skuId,Integer levelId){
        return skuAgentService.getBySkuIdAndLevelId(skuId,levelId);
    }
    /**
     * 获得等级信息
     * @param levelId
     * @return
     */
    private ComAgentLevel getComAgentLeveal(Integer levelId){
        logger.info("获得等级信息----等级id-----"+levelId);
        return comAgentLevelService.selectByPrimaryKey(levelId);
    }

    /**
     * 处理撤销申请单
     * @param upgradeId     升级申请单id
     * @throws Exception
     */
    public void cannelUpgradeNotice(Long upgradeId) throws Exception{
        logger.info("处理当前用户申请单begin  upgradeId="+upgradeId);
        PfUserUpgradeNotice upgradeNotice = pfUserUpgradeNoticeMapper.selectByPrimaryKey(upgradeId);
        if (upgradeNotice == null){
            throw new BusinessException("申请单信息不存在");
        }
        if (upgradeNotice.getStatus().intValue() == UpGradeStatus.STATUS_Revocation.getCode().intValue()){
            throw new BusinessException("申请单已经撤销");
        }
        upgradeNotice.setUpdateTime(new Date());
        upgradeNotice.setStatus(UpGradeStatus.STATUS_Revocation.getCode());
        upgradeNotice.setRemark("申请用户撤销申请单");
        logger.info("修改申请单");
        updateUpgradeNotice(upgradeNotice);
        logger.info("处理当前用户申请单end  upgradeId="+upgradeId);

        logger.info("处理下级用户申请单begin");
        PfUserUpgradeNotice newUpgrade = new PfUserUpgradeNotice();
        newUpgrade.setSkuId(upgradeNotice.getSkuId());
        newUpgrade.setUserPid(upgradeNotice.getUserId());
        logger.info("通过skuId与userPid查询代理信息 skuId："+upgradeNotice.getSkuId()+" userPid："+upgradeNotice.getUserId());
        List<PfUserUpgradeNotice> upgradeNotices = pfUserUpgradeNoticeMapper.selectByCondition(newUpgrade);
        for (PfUserUpgradeNotice upgrade : upgradeNotices){
            upgrade.setUpdateTime(new Date());
            upgrade.setStatus(UpGradeStatus.STATUS_Untreated.getCode());
            upgrade.setUpStatus(UpGradeUpStatus.UP_STATUS_Untreated.getCode());
            upgrade.setRemark("上级用户撤销申请，将申请人处理状态与上级处理状态设置为【未处理】");
            updateUpgradeNotice(upgrade);
        }
    }

    /**
     * 申請升級成功之後發送微信消息
     * @param upgradeLevel  申請代理等級
     * @param upAgentLevel  上級代理等級
     * @param upgradeId     通知单id
     */
    public String upgradeApplySubmitNotice(Integer upgradeLevel, Integer upAgentLevel, Long upgradeId, ComUser comUser){
        JSONObject jsonObject = new JSONObject();
        if (upAgentLevel.intValue() == upgradeLevel.intValue()){
            UpGradeInfoPo upGradeInfoPo = upgradeNoticeService.getUpGradeInfo(upgradeId);
            logger.info("申请人原上级商品代理信息");
            logger.info("upGradeInfoPo.getApplyPid() = "+upGradeInfoPo.getApplyPid());
            PfUserSku pfPUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(upGradeInfoPo.getApplyPid(), upGradeInfoPo.getSkuId());
            logger.info("查询申请人原上级的上级代理信息");
            PfUserSku pfPPUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(pfPUserSku.getUserPid(), upGradeInfoPo.getSkuId());
            logger.info("原上级代理等级："+pfPPUserSku.getAgentLevelId());
            logger.info("原上级代理的上级代理等级："+pfPPUserSku.getAgentLevelId());
            List<PfSkuAgent> pfSkuAgents = pfUserSkuService.getUpgradeAgents(upGradeInfoPo.getSkuId(), pfPUserSku.getAgentLevelId(), pfPPUserSku.getAgentLevelId());
            if (pfSkuAgents == null || pfSkuAgents.size() == 0){
                logger.info("-----------------------------上级不可以升级---------------------------------");
                boolean applyBoolean = upgradeWechatNewsService.upgradeApplyAuditPassNotice(comUser, upGradeInfoPo, "/upgrade/myApplyUpgrade.shtml?upgradeId="+upgradeId);
                jsonObject.put("applyBoolean",applyBoolean);
            }else {
                logger.info("-----------------------------上级可以升级---------------------------------");
                ComUser pUser = userService.getUserById(upGradeInfoPo.getApplyPid());
                boolean upBoolean = upgradeWechatNewsService.subLineUpgradeApplyNotice(pUser, upGradeInfoPo, "/upgradeInfo/lower?tabId=1");
                jsonObject.put("upBoolean",upBoolean);
                boolean applyBoolean = upgradeWechatNewsService.upgradeApplySubmitNotice(comUser, upGradeInfoPo, "/upgrade/myApplyUpgrade.shtml?upgradeId="+upgradeId);
                jsonObject.put("applyBoolean",applyBoolean);
            }
        }
        jsonObject.put("isTrue","true");
        logger.info(jsonObject.toJSONString());
        return jsonObject.toJSONString();
    }

    /**
     * 代理暂不升级发送微信消息
     * @param upgradeId 升级申请单id
     * @return          String
     */
    public String notUpgradeMessage(Long upgradeId){
        JSONObject jsonObject = new JSONObject();
        UpGradeInfoPo upGradeInfoPo = getUpGradeInfo(upgradeId);
        PfUserUpgradeNotice upgradeNotice = getUpgradeNoticeById(upgradeId);
        ComUser applyUser = userService.getUserById(upgradeNotice.getUserId());
        ComUser oldUpUser = userService.getUserById(upgradeNotice.getUserPid());
        logger.info("代理暂不升级发送消息");
        boolean oldBoolean = upgradeWechatNewsService.upgradeApplyResultNotice(oldUpUser, upGradeInfoPo, "/upgrade/upgradeInfo.shtml?upgradeId="+upgradeId);
        logger.info("代理暂不升级给下级发送微信消息");
        boolean applyBoolean = upgradeWechatNewsService.upgradeApplyAuditPassNotice(applyUser, upGradeInfoPo, "/upgrade/myApplyUpgrade.shtml?upgradeId="+upgradeId);
        jsonObject.put("isTrue","true");
        jsonObject.put("oldBoolean",oldBoolean);
        jsonObject.put("applyBoolean",applyBoolean);
        logger.info(jsonObject.toJSONString());
        return jsonObject.toJSONString();

    }
}
