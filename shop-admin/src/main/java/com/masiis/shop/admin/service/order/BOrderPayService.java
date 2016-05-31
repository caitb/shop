package com.masiis.shop.admin.service.order;

import com.masiis.shop.admin.service.product.PfSkuStockService;
import com.masiis.shop.admin.service.product.PfUserSkuStockService;
import com.masiis.shop.admin.service.product.SkuService;
import com.masiis.shop.admin.service.user.ComUserAccountService;
import com.masiis.shop.admin.utils.DrawPicUtil;
import com.masiis.shop.admin.utils.WxPFNoticeUtils;
import com.masiis.shop.common.enums.BOrder.BOrderShipStatus;
import com.masiis.shop.common.enums.BOrder.BOrderStatus;
import com.masiis.shop.common.enums.BOrder.BOrderType;
import com.masiis.shop.common.enums.product.SkuStockLogType;
import com.masiis.shop.common.enums.product.UserSkuStockLogType;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.MobileMessageUtil;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.mall.shop.SfShopMapper;
import com.masiis.shop.dao.mall.shop.SfShopSkuMapper;
import com.masiis.shop.dao.mall.user.SfUserRelationMapper;
import com.masiis.shop.dao.platform.order.PfBorderItemMapper;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.platform.order.PfBorderPaymentMapper;
import com.masiis.shop.dao.platform.product.ComAgentLevelMapper;
import com.masiis.shop.dao.platform.product.PfSkuStatisticMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * payBOrderService
 *
 * @author ZhaoLiang
 * @date 2016/3/30
 */
@Service
public class BOrderPayService {

    private Logger log = Logger.getLogger(this.getClass());
    @Resource
    private PfBorderMapper pfBorderMapper;
    @Resource
    private PfBorderItemMapper pfBorderItemMapper;
    @Resource
    private PfBorderPaymentMapper pfBorderPaymentMapper;
    @Resource
    private PfUserSkuMapper pfUserSkuMapper;
    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private PfSkuStatisticMapper pfSkuStatisticMapper;
    @Resource
    private PfSkuStockService pfSkuStockService;
    @Resource
    private PfUserSkuStockService pfUserSkuStockService;
    @Resource
    private PfUserCertificateMapper pfUserCertificateMapper;
    @Resource
    private ComAgentLevelMapper comAgentLevelMapper;
    @Resource
    private SfShopMapper sfShopMapper;
    @Resource
    private SfShopSkuMapper sfShopSkuMapper;
    @Resource
    private BOrderOperationLogService bOrderOperationLogService;
    @Resource
    private ComUserAccountService comUserAccountService;
    @Resource
    private SkuService skuService;
    @Resource
    private SfUserRelationMapper sfUserRelationMapper;

    /**
     * 订单支付回调入口
     *
     * @param pfBorderPayment 订单支付信息表数据
     * @param outOrderId      第三方支付订单号
     * @param rootPath        项目相对路径用户获取数据
     * @throws Exception
     */
    @Transactional
    public void mainPayBOrder(PfBorderPayment pfBorderPayment, String outOrderId, String rootPath) throws Exception {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd h:m:s");
//        System.out.println(dateFormat.format(new Date()));
        if (pfBorderPayment == null) {
            throw new BusinessException("pfBorderPayment为空");
        }
        if (pfBorderPayment.getIsEnabled() == 1) {
            throw new BusinessException("该支付记录已经被处理成功");
        }
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(pfBorderPayment.getPfBorderId());
        //处理支付逻辑
        if (pfBorder.getOrderType() == 0) {
            payBOrderTypeI(pfBorderPayment, outOrderId, rootPath);
        } else if (pfBorder.getOrderType() == 1) {
            payBOrderTypeII(pfBorderPayment, outOrderId, rootPath);
        } else {
            throw new BusinessException("订单类型有误");
        }
        //支付完成推送消息(发送失败不回滚事务)
        try {
            payEndPushMessage(pfBorderPayment);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
//        System.out.println(dateFormat.format(new Date()));
    }

    /**
     * 平台代发支付订单
     * <1>修改订单支付信息
     * <2>修改订单数据
     * <3>添加订单日志
     * <4>修改用户为已代理如果用户没有选择拿货方式更新用户的拿货方式为订单的拿货方式
     * <5>为用户生成小铺
     * <6>初始化分销关系(暂删除)
     * <7>为小铺生成商品
     * <8>修改用户sku代理关系数据
     * <9>修改代理人数(如果是代理类型的订单增加修改sku代理人数)
     * <10>处理发货库存
     * <11>处理收货库存
     */
    public void payBOrderTypeI(PfBorderPayment pfBorderPayment, String outOrderId, String rootPath) throws Exception {
        log.info("<1>修改订单支付信息");
        pfBorderPayment.setOutOrderId(outOrderId);
        pfBorderPayment.setIsEnabled(1);//设置为有效
        pfBorderPaymentMapper.updateById(pfBorderPayment);
        BigDecimal payAmount = pfBorderPayment.getAmount();
        Long bOrderId = pfBorderPayment.getPfBorderId();
        log.info("<2>修改订单数据");
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(bOrderId);
        if (pfBorder.getPayStatus() != BOrderStatus.NotPaid.getCode() && pfBorder.getPayStatus() != BOrderStatus.offLineNoPay.getCode()) {
            throw new BusinessException("订单号:" + pfBorder.getId() + ",已经支付成功.");
        }
        pfBorder.setReceivableAmount(pfBorder.getReceivableAmount().subtract(payAmount));
        pfBorder.setPayAmount(pfBorder.getPayAmount().add(payAmount));
        pfBorder.setPayTime(new Date());
        pfBorder.setPayStatus(1);
        //拿货方式(0未选择1平台代发2自己发货)
        if (pfBorder.getSendType() == 2) {
            pfBorder.setOrderStatus(BOrderStatus.WaitShip.getCode());//待发货
        } else {
            pfBorder.setOrderStatus(BOrderStatus.accountPaid.getCode());//已付款
        }
        pfBorderMapper.updateById(pfBorder);
        log.info("<3>添加订单日志");
        bOrderOperationLogService.insertBOrderOperationLog(pfBorder, "订单支付成功");
        log.info("<4>修改用户为已代理如果用户没有选择拿货方式更新用户的拿货方式为订单的拿货方式");
        ComUser comUser = comUserMapper.selectByPrimaryKey(pfBorder.getUserId());
        if (comUser.getIsAgent() == 0) {
            comUser.setIsAgent(1);
        }
        if (comUser.getSendType() == 0) {
            comUser.setSendType(pfBorder.getSendType());
        }
        comUserMapper.updateByPrimaryKey(comUser);
        log.info("<5>为用户生成小铺");
        SfShop sfShop = sfShopMapper.selectByUserId(comUser.getId());
        if (sfShop == null) {
            sfShop = new SfShop();
            sfShop.setCreateTime(new Date());
            sfShop.setUserId(comUser.getId());
            sfShop.setStatus(1);
            sfShop.setExplanation("主营各类化妆品、保健品");
            sfShop.setLogo(comUser.getWxHeadImg());
            sfShop.setName(comUser.getRealName() + "的小店");
            sfShop.setPageviews(0l);
            sfShop.setQrCode("");
            sfShop.setSaleAmount(BigDecimal.ZERO);
            sfShop.setShipType(1);//运费类型0：消费者出运费1：代理商出运费
            sfShop.setShipAmount(BigDecimal.ZERO);
            sfShop.setAgentShipAmount(new BigDecimal(8));
            sfShop.setShoutNum(0l);
            sfShop.setVersion(0l);
            sfShop.setRemark("");
            sfShopMapper.insert(sfShop);
        }
        log.info("<6>初始化分销关系");
        SfUserRelation sfUserRelation = sfUserRelationMapper.getSfUserRelationByUserId(comUser.getId());
        if (sfUserRelation == null) {
            sfUserRelation = new SfUserRelation();
            sfUserRelation.setCreateTime(new Date());
            sfUserRelation.setUserPid(0l);
            sfUserRelation.setUserId(comUser.getId());
            sfUserRelation.setTreeLevel(1);
            sfUserRelation.setRemark("代理人初始分销关系");
            sfUserRelationMapper.insert(sfUserRelation);
            String treeCode = sfUserRelation.getId() + ",";
            sfUserRelationMapper.updateTreeCodeById(sfUserRelation.getId(), treeCode);
        } else {
            sfUserRelation.setUserPid(0l);
            int i = sfUserRelationMapper.updateByPrimaryKey(sfUserRelation);
            if (i != 1) {
                throw new BusinessException("分销关系修改失败");
            }
            Long id = sfUserRelation.getId();
            String treeCode = sfUserRelation.getTreeCode();
            Integer id_index = treeCode.indexOf(String.valueOf(id)) + 1;
            Integer treeLevel = sfUserRelation.getTreeLevel() - 1;
            i = sfUserRelationMapper.updateTreeCodes(treeCode, id_index, treeLevel);
            if (i <= 0) {
                throw new BusinessException("分销关系树结构修改失败");
            }
        }
        for (PfBorderItem pfBorderItem : pfBorderItemMapper.selectAllByOrderId(bOrderId)) {
            PfUserSku thisUS = pfUserSkuMapper.selectByUserIdAndSkuId(comUser.getId(), pfBorderItem.getSkuId());
            if (thisUS == null) {
                log.info("<7>为小铺生成商品");
                SfShopSku sfShopSku = sfShopSkuMapper.selectByShopIdAndSkuId(sfShop.getId(), pfBorderItem.getSkuId());
                if (sfShopSku == null) {
                    sfShopSku = new SfShopSku();
                    sfShopSku.setCreateTime(new Date());
                    sfShopSku.setShopId(sfShop.getId());
                    sfShopSku.setShopUserId(comUser.getId());
                    sfShopSku.setSpuId(pfBorderItem.getSpuId());
                    sfShopSku.setSkuId(pfBorderItem.getSkuId());
                    sfShopSku.setIsSale(1);
                    sfShopSku.setAgentLevelId(pfBorderItem.getAgentLevelId());
                    sfShopSku.setBail(pfBorderItem.getBailAmount());
                    sfShopSku.setSaleNum(0l);
                    sfShopSku.setShareNum(0l);
                    sfShopSku.setQrCode("");
                    sfShopSku.setRemark("");
                    sfShopSkuMapper.insert(sfShopSku);
                }
                log.info("<8>修改用户sku代理关系数据");
                thisUS = new PfUserSku();
                thisUS.setCreateTime(new Date());
                PfUserSku parentUS = pfUserSkuMapper.selectByUserIdAndSkuId(pfBorder.getUserPid(), pfBorderItem.getSkuId());
                if (parentUS == null) {
                    thisUS.setPid(0);
                    thisUS.setUserPid(0L);
                    thisUS.setTreeLevel(1);
                } else {
                    thisUS.setPid(parentUS.getId());
                    thisUS.setUserPid(parentUS.getUserId());
                    thisUS.setTreeLevel(parentUS.getTreeLevel() + 1);
                }
                thisUS.setAgentNum(0l);
                thisUS.setUserId(pfBorder.getUserId());
                thisUS.setSkuId(pfBorderItem.getSkuId());
                thisUS.setAgentLevelId(pfBorderItem.getAgentLevelId());
                thisUS.setIsPay(1);
                thisUS.setIsCertificate(1);
                thisUS.setPfBorderId(pfBorder.getId());
                thisUS.setBail(pfBorder.getBailAmount());
                thisUS.setRemark("");
                PfUserCertificate pfUserCertificate = new PfUserCertificate();
                pfUserCertificate.setCreateTime(new Date());
                pfUserCertificate.setUserId(pfBorder.getUserId());
                pfUserCertificate.setSpuId(pfBorderItem.getSpuId());
                pfUserCertificate.setSkuId(pfBorderItem.getSkuId());
                pfUserCertificate.setIdCard(comUser.getIdCard());
                pfUserCertificate.setMobile(comUser.getMobile());
                pfUserCertificate.setWxId(pfBorderItem.getWxId());
                Calendar calendar = Calendar.getInstance();
                pfUserCertificate.setBeginTime(calendar.getTime());
                calendar.set(Calendar.MONTH, 11);
                calendar.set(Calendar.DAY_OF_MONTH, 31);
                pfUserCertificate.setEndTime(calendar.getTime());
                pfUserCertificate.setAgentLevelId(pfBorderItem.getAgentLevelId());
                pfUserCertificate.setStatus(1);
                pfUserCertificate.setRemark("");
                String code = getCertificateCode(pfUserCertificate);
                thisUS.setCode(code);
                pfUserSkuMapper.insert(thisUS);
                String treeCode = "";
                if (parentUS == null) {
                    treeCode = thisUS.getSkuId() + "," + thisUS.getId() + ",";
                } else {
                    treeCode = parentUS.getTreeCode() + thisUS.getId() + ",";
                }
                pfUserSkuMapper.updateTreeCodeById(thisUS.getId(), treeCode);
                pfUserCertificate.setPfUserSkuId(thisUS.getId());
                pfUserCertificate.setCode(code);
                ComAgentLevel comAgentLevel = comAgentLevelMapper.selectByPrimaryKey(pfUserCertificate.getAgentLevelId());
                ComSku comSku = skuService.getSkuById(pfBorderItem.getSkuId());
                String newIdCard = comUser.getIdCard().substring(0, 4) + "**********" + comUser.getIdCard().substring(comUser.getIdCard().length() - 4, comUser.getIdCard().length());
                String picName = uploadFile(rootPath + "/static/images/certificate/" + comAgentLevel.getImgUrl(),//filePath - 原图的物理路径
                        rootPath + "/static/font/",//字体路径
                        pfUserCertificate.getCode(),//certificateCode - 证书编号
                        comUser.getRealName(),//userName - 用户名称
                        comAgentLevel.getName(),//levelName - 代理等级名称
                        pfBorderItem.getSkuName(),//skuName - 商品名称
                        comSku.geteName(),
                        newIdCard,//idCard - 身份证号
                        comUser.getMobile(),//mobile - 手机号
                        pfBorderItem.getWxId(),//wxId - 微信号
                        DateUtil.Date2String(pfUserCertificate.getBeginTime(), "yyyy-MM-dd", null),//beginDate - 开始日期
                        DateUtil.Date2String(pfUserCertificate.getEndTime(), "yyyy-MM-dd", null));//endDate - 结束日期
                pfUserCertificate.setImgUrl(picName + ".jpg");
                pfUserCertificateMapper.insert(pfUserCertificate);
            }
            log.info("<9>修改代理人数(如果是代理类型的订单增加修改sku代理人数)");
            if (pfBorder.getOrderType() == 0) {
                pfSkuStatisticMapper.updateAgentNumBySkuId(pfBorderItem.getSkuId());
            }
            log.info("<10>初始化库存");
            PfUserSkuStock pfUserSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(pfBorder.getUserId(), pfBorderItem.getSkuId());
            if (pfUserSkuStock == null) {
                pfUserSkuStock = new PfUserSkuStock();
                pfUserSkuStock.setCreateTime(new Date());
                pfUserSkuStock.setUserId(pfBorder.getUserId());
                pfUserSkuStock.setSpuId(pfBorderItem.getSpuId());
                pfUserSkuStock.setSkuId(pfBorderItem.getSkuId());
                pfUserSkuStock.setStock(0);
                pfUserSkuStock.setFrozenStock(0);
                pfUserSkuStock.setCustomStock(0);
                pfUserSkuStock.setVersion(0);
                pfUserSkuStockService.insert(pfUserSkuStock);
            }
            log.info("<11>增加冻结库存");
            if (pfBorder.getUserPid() == 0) {
                PfSkuStock pfSkuStock = pfSkuStockService.selectBySkuId(pfBorderItem.getSkuId());
                //如果可售库存不足或者排单开关打开的情况下 订单进入排单
                if (pfSkuStock.getIsQueue() == 1 || pfSkuStock.getStock() - pfSkuStock.getFrozenStock() < pfBorderItem.getQuantity()) {
                    //平台库存不足，排单处理
                    pfBorder.setOrderStatus(BOrderStatus.MPS.getCode());//排队订单
                    pfBorderMapper.updateById(pfBorder);
                }
                //增加平台冻结库存
                pfSkuStock.setFrozenStock(pfSkuStock.getFrozenStock() + pfBorderItem.getQuantity());
                if (pfSkuStockService.updateByIdAndVersions(pfSkuStock) != 1) {
                    throw new BusinessException("(平台发货)排队订单增加冻结量失败");
                }
            } else {
                PfUserSkuStock parentSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(pfBorder.getUserPid(), pfBorderItem.getSkuId());
                //上级合伙人库存不足，排单处理
                if (pfBorder.getSendType() == 1 && (parentSkuStock.getStock() - parentSkuStock.getFrozenStock() < pfBorderItem.getQuantity())) {
                    pfBorder.setOrderStatus(BOrderStatus.MPS.getCode());//排队订单
                    pfBorderMapper.updateById(pfBorder);
                }
                //增加平台冻结库存
                parentSkuStock.setFrozenStock(parentSkuStock.getFrozenStock() + pfBorderItem.getQuantity());
                if (pfUserSkuStockService.updateByIdAndVersions(parentSkuStock) != 1) {
                    throw new BusinessException("(代理发货)排队订单增加冻结量失败");
                }
            }
        }
        //拿货方式(0未选择1平台代发2自己发货)
        if (pfBorder.getSendType() == 1 && pfBorder.getOrderStatus() == BOrderStatus.accountPaid.getCode()) {
            //处理平台发货类型订单
            saveBOrderSendType(pfBorder);
        }
    }

    /**
     * 自己拿货支付订单
     * <1>修改订单支付信息
     * <2>修改订单数据
     * <3>添加订单日志
     * <4>处理发货库存
     */
    @Transactional
    private void payBOrderTypeII(PfBorderPayment pfBorderPayment, String outOrderId, String rootPath) throws Exception {
        log.info("<1>修改订单支付信息");
        pfBorderPayment.setOutOrderId(outOrderId);
        pfBorderPayment.setIsEnabled(1);//设置为有效
        pfBorderPaymentMapper.updateById(pfBorderPayment);
        BigDecimal payAmount = pfBorderPayment.getAmount();
        Long bOrderId = pfBorderPayment.getPfBorderId();
        log.info("<2>修改订单数据");
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(bOrderId);
        if (pfBorder.getPayStatus() != BOrderStatus.NotPaid.getCode() && pfBorder.getPayStatus() != BOrderStatus.offLineNoPay.getCode()) {
            throw new BusinessException("订单号:" + pfBorder.getId() + ",已经支付成功.");
        }
        pfBorder.setReceivableAmount(pfBorder.getReceivableAmount().subtract(payAmount));
        pfBorder.setPayAmount(pfBorder.getPayAmount().add(payAmount));
        pfBorder.setPayTime(new Date());
        pfBorder.setPayStatus(1);
        //拿货方式(0未选择1平台代发2自己发货)
        if (pfBorder.getSendType() == 2) {
            pfBorder.setOrderStatus(BOrderStatus.WaitShip.getCode());//待发货
        } else {
            pfBorder.setOrderStatus(BOrderStatus.accountPaid.getCode());//已付款
        }
        pfBorderMapper.updateById(pfBorder);
        log.info("<3>添加订单日志");
        bOrderOperationLogService.insertBOrderOperationLog(pfBorder, "");
        for (PfBorderItem pfBorderItem : pfBorderItemMapper.selectAllByOrderId(bOrderId)) {
            log.info("<4>处理发货库存");
            if (pfBorder.getUserPid() == 0) {
                PfSkuStock pfSkuStock = pfSkuStockService.selectBySkuId(pfBorderItem.getSkuId());
                //如果可售库存不足或者排单开关打开的情况下 订单进入排单
                if (pfSkuStock.getIsQueue() == 1 || pfSkuStock.getStock() - pfSkuStock.getFrozenStock() < pfBorderItem.getQuantity()) {
                    //平台库存不足，排单处理
                    pfBorder.setOrderStatus(BOrderStatus.MPS.getCode());//排队订单
                    pfBorderMapper.updateById(pfBorder);
                }
                //增加平台冻结库存
                pfSkuStock.setFrozenStock(pfSkuStock.getFrozenStock() + pfBorderItem.getQuantity());
                if (pfSkuStockService.updateByIdAndVersions(pfSkuStock) != 1) {
                    throw new BusinessException("(平台发货)排队订单增加冻结量失败");
                }
            } else {
                PfUserSkuStock parentSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(pfBorder.getUserPid(), pfBorderItem.getSkuId());
                //上级合伙人库存不足，排单处理
                if (pfBorder.getSendType() == 1 && (parentSkuStock.getStock() - parentSkuStock.getFrozenStock() < pfBorderItem.getQuantity())) {
                    pfBorder.setOrderStatus(BOrderStatus.MPS.getCode());//排队订单
                    pfBorderMapper.updateById(pfBorder);
                }
                //增加平台冻结库存
                parentSkuStock.setFrozenStock(parentSkuStock.getFrozenStock() + pfBorderItem.getQuantity());
                if (pfUserSkuStockService.updateByIdAndVersions(parentSkuStock) != 1) {
                    throw new BusinessException("(代理发货)排队订单增加冻结量失败");
                }
            }
        }
        //拿货方式(0未选择1平台代发2自己发货)
        if (pfBorder.getSendType() == 1 && pfBorder.getOrderStatus() == BOrderStatus.accountPaid.getCode()) {
            //处理平台发货类型订单
            saveBOrderSendType(pfBorder);
        }
    }

    /**
     * 处理平台拿货类型订单
     *
     * @author ZhaoLiang
     * @date 2016/3/30 14:33
     * 操作详情：
     * <1>减少发货方库存 如果用户id是0操作平台库存
     * <2>增加收货方库存
     * <3>订单完成处理
     */
    public void saveBOrderSendType(PfBorder pfBorder) throws Exception {
        for (PfBorderItem pfBorderItem : pfBorderItemMapper.selectAllByOrderId(pfBorder.getId())) {
            log.info("<1>减少发货方库存和冻结库存 如果用户id是0操作平台库存");
            if (pfBorder.getUserPid() == 0) {
                PfSkuStock pfSkuStock = pfSkuStockService.selectBySkuId(pfBorderItem.getSkuId());
                if (pfSkuStock.getStock() < pfBorderItem.getQuantity()) {
                    throw new BusinessException("库存不足，操作失败");
                }
                if (pfSkuStock.getFrozenStock() < pfBorderItem.getQuantity()) {
                    throw new BusinessException("库存冻结不足，操作失败");
                }
                pfSkuStockService.updateSkuStockWithLog(pfBorderItem.getQuantity(), pfSkuStock, pfBorder.getId(), SkuStockLogType.downAgent);
            } else {
                PfUserSkuStock parentSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(pfBorder.getUserPid(), pfBorderItem.getSkuId());
                if (parentSkuStock.getStock() < pfBorderItem.getQuantity()) {
                    throw new BusinessException("库存不足，操作失败");
                }
                if (parentSkuStock.getFrozenStock() < pfBorderItem.getQuantity()) {
                    throw new BusinessException("库存冻结不足，操作失败");
                }
                pfUserSkuStockService.updateUserSkuStockWithLog(pfBorderItem.getQuantity(), parentSkuStock, pfBorder.getId(), UserSkuStockLogType.downAgent);
            }
            log.info("<2>增加收货方库存");
            PfUserSkuStock pfUserSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(pfBorder.getUserId(), pfBorderItem.getSkuId());
            pfUserSkuStockService.updateUserSkuStockWithLog(pfBorderItem.getQuantity(), pfUserSkuStock, pfBorder.getId(), UserSkuStockLogType.agent);
        }
        log.info("<3>订单完成处理");
        pfBorder.setShipStatus(BOrderShipStatus.Receipt.getCode());
        pfBorder.setShipTime(new Date());
        pfBorder.setIsShip(1);
        completeBOrder(pfBorder);
    }

    /**
     * 订单完成处理统一入口
     *
     * @author ZhaoLiang
     * @date 2016/4/9 11:22
     */
    @Transactional
    public void completeBOrder(PfBorder pfBorder) throws Exception {
        if (pfBorder == null) {
            throw new BusinessException("订单为空对象");
        }
        if (pfBorder.getPayStatus() != 1) {
            throw new BusinessException("订单还未支付怎么能完成呢？");
        }
        //拿货方式(0未选择1平台代发2自己发货)
        if (pfBorder.getSendType() == 1) {
            if (!pfBorder.getOrderStatus().equals(BOrderStatus.accountPaid.getCode()) && !pfBorder.getOrderStatus().equals(BOrderStatus.MPS.getCode())) {
                throw new BusinessException("订单状态异常:" + pfBorder.getOrderStatus() + ",应是" + BOrderStatus.accountPaid.getCode());
            }
        } else if (pfBorder.getSendType() == 2) {
            if (!pfBorder.getOrderStatus().equals(BOrderStatus.Ship.getCode())) {
                throw new BusinessException("订单状态异常:" + pfBorder.getOrderStatus() + ",应是" + BOrderStatus.Ship.getCode());
            }
        } else {
            throw new BusinessException("订单拿货方式异常");
        }
        pfBorder.setOrderStatus(BOrderStatus.Complete.getCode());//订单完成
        pfBorder.setShipStatus(BOrderShipStatus.Receipt.getCode());//已收货
        pfBorder.setIsReceipt(1);
        pfBorder.setReceiptTime(new Date());//收货时间
        pfBorderMapper.updateById(pfBorder);
        //添加订单日志
        bOrderOperationLogService.insertBOrderOperationLog(pfBorder, "订单完成");
        //订单类型(0代理1补货2拿货)
        if (pfBorder.getOrderType() == 0 || pfBorder.getOrderType() == 1) {
            comUserAccountService.countingByOrder(pfBorder);
        }
    }

    /**
     * 获取证书编码
     *
     * @author ZhaoLiang
     * @date 2016/3/31 11:26
     */
    private String getCertificateCode(PfUserCertificate certificateInfo) throws Exception {
        String certificateCode = "";
        String value = "";
        StringBuffer Code = new StringBuffer("MASIIS");
        value = DateUtil.Date2String(certificateInfo.getBeginTime(), "yyyy", null).substring(2);//时间
        String value1 = certificateInfo.getAgentLevelId().toString();
        String value2 = String.format("%04d", certificateInfo.getSkuId());
        String value3 = String.format("%05d", certificateInfo.getUserId());
        certificateCode = Code.append(value1).append(value2).append(value).append(value3).toString();
        return certificateCode;
    }

    /**
     * 给jpg添加文字并上传
     *
     * @param filePath        原图的物理路径
     * @param fontPath        字体地址
     * @param certificateCode 证书编号
     * @param userName        用户名称
     * @param levelName       代理等级名称
     * @param skuName         商品名称
     * @param skuEName        商品英文名称
     * @param idCard          身份证号
     * @param mobile          手机号
     * @param wxId            微信号
     * @param beginDate       开始日期
     * @param endDate         结束日期
     * @return
     */
    private String uploadFile(String filePath,
                              String fontPath,
                              String certificateCode,
                              String userName,
                              String levelName,
                              String skuName,
                              String skuEName,
                              String idCard,
                              String mobile,
                              String wxId,
                              String beginDate,
                              String endDate) {
        DrawPicUtil drawPicUtil = new DrawPicUtil();
        BufferedImage bufferedImage = drawPicUtil.loadImageLocal(filePath);
        //宋体
        Font simsun_font = null;
        //微软雅黑
        Font msyh_font = null;
        try {
            simsun_font = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath + "simsun.ttc"));
            msyh_font = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath + "msyh.ttc"));
        } catch (Exception e) {
            throw new BusinessException("创建字体异常");
        }
        Font msyh_font_25 = msyh_font.deriveFont(Font.PLAIN, 25);
        Font msyh_font_30 = msyh_font.deriveFont(Font.PLAIN, 30);
        Font msyh_font_100 = msyh_font.deriveFont(Font.PLAIN, 100);
        Font simsun_font_30 = simsun_font.deriveFont(Font.PLAIN, 30);
        Font simsun_font_45 = simsun_font.deriveFont(Font.PLAIN, 45);
        Font simsun_font_50 = simsun_font.deriveFont(Font.PLAIN, 50);
        List<DrawPicUtil.DrawPicParam> drawPicParams = new ArrayList<>();
        //授权书编号
        DrawPicUtil.DrawPicParam drawPicParam1 = drawPicUtil.getDrawPicParam();
        drawPicParam1.setX(90);
        drawPicParam1.setY(200);
        drawPicParam1.setContent(certificateCode);
        drawPicParam1.setFont(msyh_font_30);
        drawPicParams.add(drawPicParam1);
        //代理等级名称
        DrawPicUtil.DrawPicParam drawPicParam5 = drawPicUtil.getDrawPicParam();
        drawPicParam5.setY(200);
        drawPicParam5.setContent(levelName);
        drawPicParam5.setFont(msyh_font_100);
        drawPicParam5.setColor(Color.WHITE);
        drawPicParams.add(drawPicParam5);
        //代理人姓名
        DrawPicUtil.DrawPicParam drawPicParam3 = drawPicUtil.getDrawPicParam();
        drawPicParam3.setX(900);
        drawPicParam3.setY(565);
        drawPicParam3.setContent(userName);
        drawPicParam3.setFont(simsun_font_50);
        drawPicParams.add(drawPicParam3);
        //英文介绍
        DrawPicUtil.DrawPicParam drawPicParam4 = drawPicUtil.getDrawPicParam();
        drawPicParam4.setY(690);
        drawPicParam4.setContent("Has the right to sell the product of Beijing Masiis Biotech Co., Ltd.(" + skuEName + ")");
        drawPicParam4.setFont(msyh_font_25);
        drawPicParams.add(drawPicParam4);
        DrawPicUtil.DrawPicParam drawPicParam11 = drawPicUtil.getDrawPicParam();
        drawPicParam11.setY(725);
        drawPicParam11.setContent("via both e-commerce channel and physical store. ");
        drawPicParam11.setFont(msyh_font_25);
        drawPicParams.add(drawPicParam11);
        //代理商品名称
        DrawPicUtil.DrawPicParam drawPicParam9 = drawPicUtil.getDrawPicParam();
        drawPicParam9.setY(770);
        drawPicParam9.setContent(skuName);
        drawPicParam9.setFont(simsun_font_30);
        drawPicParam9.setColor(Color.decode("#FF4B21"));
        drawPicParams.add(drawPicParam9);
        //证件号等
        DrawPicUtil.DrawPicParam drawPicParam6 = drawPicUtil.getDrawPicParam();
        drawPicParam6.setY(850);
        drawPicParam6.setContent("证件号：" + idCard + "，手机：" + mobile + "，微信：" + wxId);
        drawPicParam6.setFont(simsun_font_45);
        drawPicParams.add(drawPicParam6);
        //授权期限
        DrawPicUtil.DrawPicParam drawPicParam2 = drawPicUtil.getDrawPicParam();
        drawPicParam2.setY(920);
        drawPicParam2.setContent("授权期限：" + beginDate + "至" + endDate);
        drawPicParam2.setFont(simsun_font_45);
        drawPicParams.add(drawPicParam2);
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        try {
            ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(drawPicUtil.modifyImage(bufferedImage, drawPicParams), "png", imOut);
        } catch (Exception ex) {
            throw new BusinessException("生成证书异常");
        }
        InputStream is = new ByteArrayInputStream(bs.toByteArray());
        String pname = getRandomFileName();
        OSSObjectUtils.uploadFile("static/user/certificate/" + pname + ".jpg", is);
        return pname;
    }

    /**
     * 生成随机文件名：当前年月日时分秒+五位随机数
     *
     * @return
     */
    private String getRandomFileName() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String str = simpleDateFormat.format(date);
        Random random = new Random();
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        return rannum + str;// 当前时间
    }

    /**
     * 支付完成推送消息
     */
    private void payEndPushMessage(PfBorderPayment pfBorderPayment) {
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(pfBorderPayment.getPfBorderId());
        NumberFormat rmbFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);
        SimpleDateFormat timeFormart = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        ComUser comUser = comUserMapper.selectByPrimaryKey(pfBorder.getUserId());
        ComUser pComUser = null;
        if (pfBorder.getUserPid() > 0) {
            pComUser = comUserMapper.selectByPrimaryKey(pfBorder.getUserPid());
        }
        List<PfBorderItem> pfBorderItems = pfBorderItemMapper.selectAllByOrderId(pfBorder.getId());
        ComAgentLevel comAgentLevel = comAgentLevelMapper.selectByPrimaryKey(pfBorderItems.get(0).getAgentLevelId());
        log.info("****************************处理推送通知***********************************************");
        if (pfBorder.getOrderStatus() == BOrderStatus.MPS.getCode()) {
            //排单推送通知
            String[] param = new String[5];
            param[0] = pfBorderItems.get(0).getSkuName();
            param[1] = pfBorder.getOrderAmount().toString();
            param[2] = pfBorderItems.get(0).getQuantity().toString();
            param[3] = BOrderType.getByCode(pfBorder.getOrderType()).getDesc();
            param[4] = BOrderStatus.getByCode(pfBorder.getOrderStatus()).getDesc();
            WxPFNoticeUtils.getInstance().orderInQueue(comUser, param);
            MobileMessageUtil.getInitialization("B").joinQueueOrder(comUser.getMobile(), pfBorder.getOrderCode());
            if (pfBorder.getUserPid() != 0) {
                String[] paramIn = new String[2];
                paramIn[0] = pfBorder.getOrderCode();
                paramIn[1] = timeFormart.format(pfBorder.getCreateTime());
                String url = PropertiesUtils.getStringValue("web.domain.name.address") + "/borderManage/borderDetils.html?id=" + pfBorder.getId();
                WxPFNoticeUtils.getInstance().newOrderNotice(pComUser, paramIn, url, false);
                MobileMessageUtil.getInitialization("B").haveNewLowerOrder(pComUser.getMobile(), pfBorder.getOrderStatus());
            }
        } else {
            //订单类型(0代理1补货2拿货)
            if (pfBorder.getOrderType() == 0) {
                //合伙人申请成功提示
                String[] params = new String[4];
                params[0] = rmbFormat.format(pfBorder.getPayAmount());
                params[1] = pfBorderPayment.getPayTypeName();
                params[2] = pfBorderItems.get(0).getSkuName() + "-" + comAgentLevel.getName();
                params[3] = timeFormart.format(pfBorder.getPayTime());
                WxPFNoticeUtils.getInstance().partnerApplySuccessNotice(comUser, params);
                MobileMessageUtil.getInitialization("B").partnerApplicationSuccess(comUser.getMobile(), pfBorderItems.get(0).getSkuName(), comAgentLevel.getName());
                //给上级推送
                if (pComUser != null) {
                    WxPFNoticeUtils.getInstance().partnerJoinNotice(pComUser, comUser, timeFormart.format(pfBorder.getCreateTime()));
                    MobileMessageUtil.getInitialization("B").haveNewLowerOrder(pComUser.getMobile(), pfBorder.getOrderStatus());
                }
            } else if (pfBorder.getOrderType() == 1) {
                //拿货方式(0未选择1平台代发2自己发货)
                if (pfBorder.getSendType() == 1) {
                    String[] param = new String[4];
                    param[0] = pfBorderItems.get(0).getSkuName();
                    param[1] = rmbFormat.format(pfBorder.getOrderAmount());
                    param[2] = pfBorderItems.get(0).getQuantity().toString();
                    param[3] = BOrderStatus.getByCode(pfBorder.getOrderType()).getDesc();
                    WxPFNoticeUtils.getInstance().replenishmentByPlatForm(comUser, param);
                    MobileMessageUtil.getInitialization("B").addStockSuccess(comUser.getMobile(), pfBorder.getSendType(), pfBorderItems.get(0).getQuantity().toString());
                } else if (pfBorder.getSendType() == 2) {
                    String[] param = new String[4];
                    param[0] = pfBorderItems.get(0).getSkuName();
                    param[1] = rmbFormat.format(pfBorder.getOrderAmount());
                    param[2] = pfBorderItems.get(0).getQuantity().toString();
                    param[3] = BOrderStatus.getByCode(pfBorder.getOrderType()).getDesc();
                    WxPFNoticeUtils.getInstance().replenishmentBySelf(comUser, param);
                    MobileMessageUtil.getInitialization("B").addStockSuccess(comUser.getMobile(), pfBorder.getSendType(), pfBorderItems.get(0).getQuantity().toString());
                    if (pfBorder.getUserPid() != 0) {
                        String[] paramIn = new String[2];
                        paramIn[0] = pfBorder.getOrderCode();
                        paramIn[1] = timeFormart.format(pfBorder.getCreateTime());
                        String url = PropertiesUtils.getStringValue("web.domain.name.address") + "/borderManage/borderDetils.html?id=" + pfBorder.getId();
                        WxPFNoticeUtils.getInstance().newOrderNotice(pComUser, paramIn, url, true);
                        MobileMessageUtil.getInitialization("B").haveNewLowerOrder(pComUser.getMobile(), pfBorder.getOrderStatus());
                    }
                }
            }
        }
    }


}
