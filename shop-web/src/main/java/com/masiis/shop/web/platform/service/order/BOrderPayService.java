package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.common.enums.BOrder.BOrderShipStatus;
import com.masiis.shop.common.enums.BOrder.BOrderStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.MobileMessageUtil;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.mall.shop.SfShopMapper;
import com.masiis.shop.dao.mall.shop.SfShopSkuMapper;
import com.masiis.shop.dao.platform.order.PfBorderItemMapper;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.platform.order.PfBorderPaymentMapper;
import com.masiis.shop.dao.platform.product.ComAgentLevelMapper;
import com.masiis.shop.dao.platform.product.PfSkuStatisticMapper;
import com.masiis.shop.dao.platform.product.PfSkuStockMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.utils.ApplicationContextUtil;
import com.masiis.shop.web.platform.utils.wx.WxPFNoticeUtils;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
    private PfSkuStockMapper pfSkuStockMapper;
    @Resource
    private PfUserSkuStockMapper pfUserSkuStockMapper;
    @Resource
    private PfUserCertificateMapper pfUserCertificateMapper;
    @Resource
    private ComAgentLevelMapper comAgentLevelMapper;
    @Resource
    private BOrderService bOrderService;
    @Resource
    private SfShopMapper sfShopMapper;
    @Resource
    private SfShopSkuMapper sfShopSkuMapper;
    @Resource
    private PfSupplierBankService supplierBankService;
    @Resource
    private BOrderOperationLogService bOrderOperationLogService;

    /**
     * 订单支付回调入口
     *
     * @param pfBorderPayment 订单支付信息表数据
     * @param outOrderId      第三方支付订单号
     * @param rootPath        项目相对路径用户获取数据
     * @throws Exception
     */
    @Transactional
    public void mainPayBOrder(PfBorderPayment pfBorderPayment, String outOrderId, String rootPath) {
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
        //支付完成推送消息
        payEndPushMessage(pfBorderPayment);
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
    @Transactional
    public void payBOrderTypeI(PfBorderPayment pfBorderPayment, String outOrderId, String rootPath) {
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
            sfShop.setShipAmount(BigDecimal.ZERO);
            sfShop.setShoutNum(0l);
            sfShop.setVersion(0l);
            sfShop.setRemark("");
            sfShopMapper.insert(sfShop);
        }
//        log.info("<6>初始化分销关系");
//        SfUserRelation sfUserRelation = sfUserRelationMapper.getSfUserRelationByUserId(comUser.getId());
//        if (sfUserRelation == null) {
//            sfUserRelation = new SfUserRelation();
//            sfUserRelation.setCreateTime(new Date());
//            sfUserRelation.setUserPid(0l);
//            sfUserRelation.setUserId(comUser.getId());
//            sfUserRelation.setLevel(1);
//            sfUserRelation.setRemark("代理人初始分销关系");
//            sfUserRelationMapper.insert(sfUserRelation);
//        }
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
                } else {
                    thisUS.setPid(parentUS.getId());
                    thisUS.setUserPid(parentUS.getUserId());
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
                pfUserCertificate.setBeginTime(new Date());
                Calendar calendar = Calendar.getInstance();
                Date date = new Date(System.currentTimeMillis());
                calendar.setTime(date);
                calendar.add(Calendar.YEAR, 2);
                date = calendar.getTime();
                pfUserCertificate.setEndTime(date);
                pfUserCertificate.setAgentLevelId(pfBorderItem.getAgentLevelId());
                pfUserCertificate.setStatus(1);
                pfUserCertificate.setRemark("");
                String code = getCertificateCode(pfUserCertificate);
                thisUS.setCode(code);
                pfUserSkuMapper.insert(thisUS);
                pfUserCertificate.setPfUserSkuId(thisUS.getId());
                pfUserCertificate.setCode(code);
                String name = comUser.getRealName();//申请人
                String beginTime = DateUtil.Date2String(pfUserCertificate.getBeginTime(), "yyyy-MM-dd", null);
                String endTime = DateUtil.Date2String(pfUserCertificate.getEndTime(), "yyyy-MM-dd", null);
                String value1 = "授权书编号：" + pfUserCertificate.getCode() + "，手机：" + pfUserCertificate.getMobile() + "，微信：" + pfUserCertificate.getWxId();
                String value2 = "授权期限：" + beginTime + "至" + endTime;
                ComAgentLevel comAgentLevel = comAgentLevelMapper.selectByPrimaryKey(pfUserCertificate.getAgentLevelId());
                String picName = uploadFile(rootPath + "/static/images/certificate/" + comAgentLevel.getImgUrl(), new String[]{name, value1, value2});
                pfUserCertificate.setImgUrl(picName + ".jpg");
                pfUserCertificateMapper.insert(pfUserCertificate);
            }
            log.info("<9>修改代理人数(如果是代理类型的订单增加修改sku代理人数)");
            if (pfBorder.getOrderType() == 0) {
                pfSkuStatisticMapper.updateAgentNumBySkuId(pfBorderItem.getSkuId());
            }
            log.info("<10>初始化库存");
            PfUserSkuStock pfUserSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(pfBorder.getUserId(), pfBorderItem.getSkuId());
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
                pfUserSkuStockMapper.insert(pfUserSkuStock);
            }
            log.info("<11>增加冻结库存");
            if (pfBorder.getUserPid() == 0) {
                PfSkuStock pfSkuStock = pfSkuStockMapper.selectBySkuId(pfBorderItem.getSkuId());
                //如果可售库存不足或者排单开关打开的情况下 订单进入排单
                if (pfSkuStock.getIsQueue() == 1 || pfSkuStock.getStock() - pfSkuStock.getFrozenStock() < pfBorderItem.getQuantity()) {
                    //平台库存不足，排单处理
                    pfBorder.setOrderStatus(BOrderStatus.MPS.getCode());//排队订单
                    pfBorderMapper.updateById(pfBorder);
                }
                //增加平台冻结库存
                pfSkuStock.setFrozenStock(pfSkuStock.getFrozenStock() + pfBorderItem.getQuantity());
                if (pfSkuStockMapper.updateByIdAndVersion(pfSkuStock) != 1) {
                    throw new BusinessException("(平台发货)排队订单增加冻结量失败");
                }
            } else {
                PfUserSkuStock parentSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(pfBorder.getUserPid(), pfBorderItem.getSkuId());
                //上级合伙人库存不足，排单处理
                if (pfBorder.getSendType() == 1 && (parentSkuStock.getStock() - parentSkuStock.getFrozenStock() < pfBorderItem.getQuantity())) {
                    pfBorder.setOrderStatus(BOrderStatus.MPS.getCode());//排队订单
                    pfBorderMapper.updateById(pfBorder);
                }
                //增加平台冻结库存
                parentSkuStock.setFrozenStock(parentSkuStock.getFrozenStock() + pfBorderItem.getQuantity());
                if (pfUserSkuStockMapper.updateByIdAndVersion(parentSkuStock) != 1) {
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
    public void payBOrderTypeII(PfBorderPayment pfBorderPayment, String outOrderId, String rootPath) {
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
                PfSkuStock pfSkuStock = pfSkuStockMapper.selectBySkuId(pfBorderItem.getSkuId());
                //如果可售库存不足或者排单开关打开的情况下 订单进入排单
                if (pfSkuStock.getIsQueue() == 1 || pfSkuStock.getStock() - pfSkuStock.getFrozenStock() < pfBorderItem.getQuantity()) {
                    //平台库存不足，排单处理
                    pfBorder.setOrderStatus(BOrderStatus.MPS.getCode());//排队订单
                    pfBorderMapper.updateById(pfBorder);
                }
                //增加平台冻结库存
                pfSkuStock.setFrozenStock(pfSkuStock.getFrozenStock() + pfBorderItem.getQuantity());
                if (pfSkuStockMapper.updateByIdAndVersion(pfSkuStock) != 1) {
                    throw new BusinessException("(平台发货)排队订单增加冻结量失败");
                }
            } else {
                PfUserSkuStock parentSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(pfBorder.getUserPid(), pfBorderItem.getSkuId());
                //上级合伙人库存不足，排单处理
                if (pfBorder.getSendType() == 1 && (parentSkuStock.getStock() - parentSkuStock.getFrozenStock() < pfBorderItem.getQuantity())) {
                    pfBorder.setOrderStatus(BOrderStatus.MPS.getCode());//排队订单
                    pfBorderMapper.updateById(pfBorder);
                }
                //增加平台冻结库存
                parentSkuStock.setFrozenStock(parentSkuStock.getFrozenStock() + pfBorderItem.getQuantity());
                if (pfUserSkuStockMapper.updateByIdAndVersion(parentSkuStock) != 1) {
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
    private void saveBOrderSendType(PfBorder pfBorder) {
        for (PfBorderItem pfBorderItem : pfBorderItemMapper.selectAllByOrderId(pfBorder.getId())) {
            log.info("<1>减少发货方库存和冻结库存 如果用户id是0操作平台库存");
            if (pfBorder.getUserPid() == 0) {
                PfSkuStock pfSkuStock = pfSkuStockMapper.selectBySkuId(pfBorderItem.getSkuId());
                //减少平台库存
                pfSkuStock.setStock(pfSkuStock.getStock() - pfBorderItem.getQuantity());
                if (pfSkuStock.getStock() < 0) {
                    throw new BusinessException("库存不足，操作失败");
                }
                //减少冻结库存
                pfSkuStock.setFrozenStock(pfSkuStock.getFrozenStock() - pfBorderItem.getQuantity());
                if (pfSkuStockMapper.updateByIdAndVersion(pfSkuStock) != 1) {
                    throw new BusinessException("减少平台库存失败");
                }
            } else {
                PfUserSkuStock parentSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(pfBorder.getUserPid(), pfBorderItem.getSkuId());
                if (parentSkuStock.getStock() < 0) {
                    throw new BusinessException("库存不足，操作失败");
                }
                //减少上级合伙人库存
                parentSkuStock.setStock(parentSkuStock.getStock() - pfBorderItem.getQuantity());
                //减少上级合伙人冻结库存
                parentSkuStock.setFrozenStock(parentSkuStock.getFrozenStock() - pfBorderItem.getQuantity());
                if (pfUserSkuStockMapper.updateByIdAndVersion(parentSkuStock) != 1) {
                    throw new BusinessException("减少上级合伙人平台库存失败");
                }
            }
            log.info("<2>增加收货方库存");
            PfUserSkuStock pfUserSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(pfBorder.getUserId(), pfBorderItem.getSkuId());
            //如果还没有库存信息直接初始化库存
            pfUserSkuStock.setStock(pfUserSkuStock.getStock() + pfBorderItem.getQuantity());
            if (pfUserSkuStockMapper.updateByIdAndVersion(pfUserSkuStock) != 1) {
                throw new BusinessException("增加用户平台库存失败");
            }
        }
        log.info("<3>订单完成处理");
        pfBorder.setShipStatus(BOrderShipStatus.Receipt.getCode());
        pfBorder.setShipTime(new Date());
        pfBorder.setIsShip(1);
        pfBorder.setReceiptTime(new Date());
        bOrderService.completeBOrder(pfBorder);
    }

    /**
     * 获取证书编码
     *
     * @author ZhaoLiang
     * @date 2016/3/31 11:26
     */
    private String getCertificateCode(PfUserCertificate certificateInfo) {
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
     * @author ZhaoLiang
     * @date 2016/3/31 11:26
     */
    private String uploadFile(String filePath, String[] markContent) {
        String pname = getRandomFileName();
        ImageIcon imgIcon = new ImageIcon(filePath);
        Image theImg = imgIcon.getImage();
        int width = theImg.getWidth(null) == -1 ? 200 : theImg.getWidth(null);
        int height = theImg.getHeight(null) == -1 ? 200 : theImg.getHeight(null);
        BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bimage.createGraphics();
        g.setColor(Color.black);
        g.setBackground(Color.red);
        g.drawImage(theImg, 0, 0, null);
        int fs = 40;
        g.setFont(new Font("华文细黑", Font.BOLD, fs)); //字体、字型、字号
        //画文字
        if (markContent[0].length() == 3) {
            g.drawString(markContent[0], width / 2 - fs * 3 / 2, height / 2 + 40);//740 625
        } else {
            g.drawString(markContent[0], width / 2 - fs, height / 2 + 40);
        }
        g.setColor(Color.gray);
        g.setFont(new Font("华文细黑", Font.BOLD, 18)); //字体、字型、字号
        g.drawString(markContent[1], 150, 490);
        g.drawString(markContent[2], 180, 520);
        g.dispose();
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        try {
            ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(bimage, "png", imOut);
        } catch (Exception ex) {
            throw new BusinessException("生成证书异常");
        }
        InputStream is = new ByteArrayInputStream(bs.toByteArray());
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
        SimpleDateFormat timeFormart = new SimpleDateFormat("yyyy年MM月dd日 H:m:s");
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
            String[] param = {};
            param[0] = pfBorderItems.get(0).getSkuName();
            param[1] = pfBorder.getOrderAmount().toString();
            param[2] = pfBorderItems.get(0).getQuantity().toString();
            param[3] = BOrderStatus.getByCode(pfBorder.getOrderType()).getDesc();
            WxPFNoticeUtils.getInstance().orderInQueue(comUser, param);
            MobileMessageUtil.joinQueueOrder(comUser.getMobile(), pfBorder.getOrderCode());
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
                MobileMessageUtil.partnerApplicationSuccess(comUser.getMobile(), pfBorderItems.get(0).getSkuName(), comAgentLevel.getName());
                //给上级推送
                if (pComUser != null) {
                    WxPFNoticeUtils.getInstance().partnerJoinNotice(pComUser, comUser, timeFormart.format(pfBorder.getCreateTime()));
                    MobileMessageUtil.haveNewLowerOrder(pComUser.getMobile());
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
                    MobileMessageUtil.addStockByPlatform(comUser.getMobile(), pfBorderItems.get(0).getQuantity().toString());
                } else if (pfBorder.getSendType() == 2) {
                    String[] param = new String[4];
                    param[0] = pfBorderItems.get(0).getSkuName();
                    param[1] = rmbFormat.format(pfBorder.getOrderAmount());
                    param[2] = pfBorderItems.get(0).getQuantity().toString();
                    param[3] = BOrderStatus.getByCode(pfBorder.getOrderType()).getDesc();
                    WxPFNoticeUtils.getInstance().replenishmentBySelf(comUser, param);
                    MobileMessageUtil.addStockByUserself(comUser.getMobile());
                    if (pfBorder.getUserPid() != 0) {
                        String[] paramIn = new String[2];
                        paramIn[0] = pfBorder.getOrderCode();
                        paramIn[1] = timeFormart.format(pfBorder.getCreateTime());
                        String url = PropertiesUtils.getStringValue("web.domain.name.address") + "/borderManage/borderDetils.html?id=" + pfBorder.getId();
                        WxPFNoticeUtils.getInstance().newOrderNotice(comUser, paramIn, url);
                        MobileMessageUtil.haveNewLowerOrder(pComUser.getMobile());
                    }
                }
            }
        }
    }

    /**
     * 线下支付
     *
     * @author hanzengzhi
     * @date 2016/4/25 14:46
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Map<String, Object> offinePayment(Long bOrderId) {
        Map<String, Object> map = null;
        PfBorder pfBorder = updateOrderStatus(BOrderStatus.offLineNoPay.getCode(), bOrderId);
        if (pfBorder != null) {
            //插入订单支付表
            insertOrderPayment(pfBorder);
            bOrderOperationLogService.insertBOrderOperationLog(pfBorder, "订单线下已支付");
            PfSupplierBank supplierBank = getDefaultBack();
            List<PfBorderItem> orderItems = pfBorderItemMapper.selectAllByOrderId(pfBorder.getId());
            if (orderItems != null && orderItems.size() != 0) {
                map = new LinkedHashMap<String, Object>();
                map.put("latestTime", DateUtil.addDays(SysConstants.OFFINE_PAYMENT_LATEST_TIME));
                map.put("supplierBank", supplierBank);
                map.put("orderItem", orderItems.get(0));
                map.put("border", pfBorder);
            } else {
                throw new BusinessException("线下支付失败:查询子帐单为null");
            }
        }
        return map;
    }

    /**
     * 修改订单的状态
     *
     * @author hanzengzhi
     * @date 2016/4/25 14:49
     */
    private PfBorder updateOrderStatus(Integer status, Long bOrderId) {
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(bOrderId);
/*        if (pfBorder == null||!pfBorder.getOrderStatus().equals(BOrderStatus.NotPaid.getCode())){
            throw new BusinessException("订单状态不是未支付状态，线下支付失败");
        }*/
        if (pfBorder == null) {
            throw new BusinessException("线下支付失败:查询订单信息失败");
        }
        pfBorder.setOrderStatus(status);
        int i = pfBorderMapper.updateById(pfBorder);
        if (i != 1) {
            throw new BusinessException("线下支付更新订单状态失败");
        }
        return pfBorder;
    }

    /**
     * 插入订单支付表
     *
     * @author hanzengzhi
     * @date 2016/4/27 14:19
     */
    private void insertOrderPayment(PfBorder pfBorder) {
        PfBorderPayment orderPayment = new PfBorderPayment();
        orderPayment.setPayTypeId(1);
        orderPayment.setPayTypeName("线下支付");
        orderPayment.setPfBorderId(pfBorder.getId());
        orderPayment.setIsEnabled(0);
        orderPayment.setAmount(pfBorder.getOrderAmount());
        orderPayment.setCreateTime(new Date());
        orderPayment.setOutOrderId("");
        orderPayment.setPaySerialNum(UUID.randomUUID().toString());
        orderPayment.setRemark("线下支付插入");
        PfBorderPayment _orderPayment = pfBorderPaymentMapper.selectByOrderIdAndPayTypeIdAndIsEnabled(pfBorder.getId(), 1, 0);
        if (_orderPayment == null) {
            log.info("线下支付:订单支付表中没有输入，插入数据----start");
            int i = pfBorderPaymentMapper.insert(orderPayment);
            if (i != 1) {
                throw new BusinessException("线下支付往订单支付表插入失败");
            }
            log.info("线下支付:订单支付表中没有输入，插入数据----end");
        }
    }

    /**
     * 获得运营商的默认银行卡信息
     *
     * @author hanzengzhi
     * @date 2016/4/25 14:50
     */
    private PfSupplierBank getDefaultBack() {
        return supplierBankService.getDefaultBank();
    }
}
