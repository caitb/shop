package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.common.enums.BOrderStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.dao.platform.certificate.CertificateMapper;
import com.masiis.shop.dao.platform.order.*;
import com.masiis.shop.dao.platform.product.ComAgentLevelMapper;
import com.masiis.shop.dao.platform.product.PfSkuStatisticMapper;
import com.masiis.shop.dao.platform.product.PfSkuStockMapper;
import com.masiis.shop.dao.platform.user.*;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.service.user.ComUserAccountService;
import com.masiis.shop.web.platform.service.user.UserAddressService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * payBOrderService
 *
 * @author ZhaoLiang
 * @date 2016/3/30
 */
@Service
public class PayBOrderService {

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
    private PfBorderOperationLogMapper pfBorderOperationLogMapper;
    @Resource
    private PfSkuStatisticMapper pfSkuStatisticMapper;
    @Resource
    private PfSkuStockMapper pfSkuStockMapper;
    @Resource
    private PfUserSkuStockMapper pfUserSkuStockMapper;
    @Resource
    private ComUserAccountService comUserAccountService;
    @Resource
    private ComUserAccountMapper accountMapper;
    @Resource
    private ComUserAccountRecordMapper recordMapper;
    @Resource
    private PfUserCertificateMapper pfUserCertificateMapper;
    @Resource
    private ComAgentLevelMapper comAgentLevelMapper;
    @Resource
    private UserAddressService userAddressService;
    @Resource
    private PfBorderConsigneeMapper pfBorderConsigneeMapper;

    /**
     * 支付回调统一入口
     *
     * @author ZhaoLiang
     * @date 2016/3/31 11:55
     * 操作详情：
     * <1>修改订单支付信息
     * <2>修改订单数据
     * <3>添加订单日志
     * <4>修改用户为已代理
     * <5>增加保证金
     * <6>修改用户sku代理关系数据
     * <7>修改代理人数(如果是代理类型的订单增加修改sku代理人数)
     */
    @Transactional
    public void mainPayBOrder(PfBorderPayment pfBorderPayment, String outOrderId, String rootPath) throws Exception {
        if (pfBorderPayment == null) {
            throw new BusinessException("pfBorderPayment为空");
        }
        if (pfBorderPayment.getIsEnabled() == 1) {
            throw new BusinessException("该支付记录已经被处理成功");
        }
        log.info("<1>修改订单支付信息");
        pfBorderPayment.setOutOrderId(outOrderId);
        pfBorderPayment.setIsEnabled(1);//设置为有效
        pfBorderPaymentMapper.updateById(pfBorderPayment);
        BigDecimal payAmount = pfBorderPayment.getAmount();
        Long bOrderId = pfBorderPayment.getPfBorderId();
        log.info("<2>修改订单数据");
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(bOrderId);
        if (pfBorder.getPayStatus() == 1) {
            throw new BusinessException("订单号:" + pfBorder.getId() + ",已经支付成功.");
        }
        pfBorder.setReceivableAmount(pfBorder.getReceivableAmount().subtract(payAmount));
        pfBorder.setPayAmount(pfBorder.getPayAmount().add(payAmount));
        pfBorder.setPayTime(new Date());
        pfBorder.setPayStatus(1);
        //拿货方式(0未选择1平台代发2自己发货)
        if (pfBorder.getSendType() == 2) {
            pfBorder.setOrderStatus(7);//待发货
        } else {
            pfBorder.setOrderStatus(1);//已付款
        }
        pfBorderMapper.updateById(pfBorder);
        log.info("<3>添加订单日志");
        PfBorderOperationLog pfBorderOperationLog = new PfBorderOperationLog();
        pfBorderOperationLog.setCreateMan(pfBorder.getUserId());
        pfBorderOperationLog.setCreateTime(new Date());
        pfBorderOperationLog.setPfBorderStatus(1);
        pfBorderOperationLog.setPfBorderId(bOrderId);
        pfBorderOperationLog.setRemark("订单已支付");
        pfBorderOperationLogMapper.insert(pfBorderOperationLog);
        log.info("<4>修改用户为已代理");
        ComUser comUser = comUserMapper.selectByPrimaryKey(pfBorder.getUserId());
        if (comUser.getIsAgent() == 0) {
            comUser.setIsAgent(1);
            comUserMapper.updateByPrimaryKey(comUser);
        }
        log.info("<5>增加保证金");
        if (pfBorder.getBailAmount().compareTo(BigDecimal.ZERO) > 0) {
            ComUserAccount accountS = accountMapper.findByUserId(pfBorder.getUserId());
            ComUserAccountRecord recordS = comUserAccountService.createAccountRecordByBail(pfBorder.getBailAmount(), accountS, pfBorder.getId());
            // 保存修改前的金额
            recordS.setPrevFee(accountS.getBailFee());
            accountS.setBailFee(accountS.getBailFee().add(pfBorder.getBailAmount()));
            accountS.setCostFee(pfBorder.getPayAmount());
            // 保存修改后的金额
            recordS.setNextFee(accountS.getBailFee());
            recordMapper.insert(recordS);
            if (accountMapper.updateByIdWithVersion(accountS) == 0) {
                throw new BusinessException("修改进货方成本账户失败!");
            }
        }
        for (PfBorderItem pfBorderItem : pfBorderItemMapper.selectAllByOrderId(bOrderId)) {
            log.info("<6>修改用户sku代理关系数据");
            PfUserSku pfUserSku = pfUserSkuMapper.selectByOrderIdAndUserIdAndSkuId(bOrderId, comUser.getId(), pfBorderItem.getSkuId());
            //订单类型(0代理1补货2拿货)
            if (pfUserSku != null && pfBorder.getOrderType() == 0) {
                pfUserSku.setIsPay(1);
                pfUserSku.setBail(pfBorderItem.getBailAmount());
                pfUserSku.setIsCertificate(1);
                PfUserCertificate pfUserCertificate = pfUserCertificateMapper.selectByUserSkuId(pfUserSku.getId());
                pfUserCertificate.setCreateTime(new Date());
                pfUserCertificate.setStatus(1);
                pfUserCertificate.setBeginTime(new Date());
                Calendar calendar = Calendar.getInstance();
                Date date = new Date(System.currentTimeMillis());
                calendar.setTime(date);
                calendar.add(Calendar.YEAR, 2);
                date = calendar.getTime();
                pfUserCertificate.setEndTime(date);
                pfUserCertificate.setCode(getCertificateCode(pfUserCertificate));
                String name = comUser.getRealName();//申请人
                String beginTime = DateUtil.Date2String(pfUserCertificate.getBeginTime(), "yyyy-MM-dd", null);
                String endTime = DateUtil.Date2String(pfUserCertificate.getEndTime(), "yyyy-MM-dd", null);
                String value1 = "授权书编号：" + pfUserCertificate.getCode() + "，手机：" + pfUserCertificate.getMobile() + "，微信：" + pfUserCertificate.getWxId();
                String value2 = "授权期限：" + beginTime + "至" + endTime;
                ComAgentLevel comAgentLevel = comAgentLevelMapper.selectByPrimaryKey(pfUserCertificate.getAgentLevelId());
                String picName = uploadFile(rootPath + "/static/images/certificate/" + comAgentLevel.getImgUrl(), new String[]{name, value1, value2});
                pfUserCertificate.setImgUrl(picName);
                pfUserCertificateMapper.updateById(pfUserCertificate);
                pfUserSku.setCode(pfUserCertificate.getCode());
                pfUserSkuMapper.updateByPrimaryKey(pfUserSku);
            }

            log.info("<7>修改代理人数(如果是代理类型的订单增加修改sku代理人数)");
            if (pfBorder.getOrderType() == 0) {
                pfSkuStatisticMapper.updateAgentNumBySkuId(pfBorderItem.getSkuId());
            }
        }
        //拿货方式(0未选择1平台代发2自己发货)
        if (pfBorder.getSendType() == 1) {
            //处理平台发货类型订单
            saveBOrderSendType(pfBorder);
        }
    }

    /**
     * 处理平台发货类型订单
     *
     * @author ZhaoLiang
     * @date 2016/3/30 14:33
     * 操作详情：
     * <8>减少发货方库存 如果用户id是0操作平台库存
     * <9>增加收货方库存
     * <10>修改订单状态为已完成
     * <11>订单完成,根据订单来计算结算和总销售额,并创建对应的账单子项
     */
    private void saveBOrderSendType(PfBorder pfBorder) throws Exception {
        for (PfBorderItem pfBorderItem : pfBorderItemMapper.selectAllByOrderId(pfBorder.getId())) {
            log.info("<8>减少发货方库存 如果用户id是0操作平台库存");
            if (pfBorder.getUserPid() == 0) {
                PfSkuStock pfSkuStock = pfSkuStockMapper.selectBySkuId(pfBorderItem.getSkuId());
                if (pfSkuStock.getStock() - pfSkuStock.getFrozenStock() < pfBorderItem.getQuantity()) {
                    //平台库存不足，排单处理
                    pfBorder.setOrderStatus(6);//排队订单
                    pfBorderMapper.updateById(pfBorder);
                    //增加平台冻结库存
                    pfSkuStock.setFrozenStock(pfSkuStock.getFrozenStock() + pfBorderItem.getQuantity());
                    if (pfSkuStockMapper.updateByIdAndVersion(pfSkuStock) == 0) {
                        throw new BusinessException("(平台发货)排队订单增加冻结量失败");
                    }
                    return;
                } else {
                    //减少平台库存
                    pfSkuStock.setStock(pfSkuStock.getStock() - pfBorderItem.getQuantity());
                    if (pfSkuStockMapper.updateByIdAndVersion(pfSkuStock) == 0) {
                        throw new BusinessException("减少平台库存失败");
                    }
                }
            } else {
                PfUserSkuStock parentSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(pfBorder.getUserPid(), pfBorderItem.getSkuId());
                //上级合伙人库存不足，排单处理
                if (parentSkuStock.getStock() - parentSkuStock.getFrozenStock() < pfBorderItem.getQuantity()) {
                    pfBorder.setOrderStatus(6);//排队订单
                    pfBorderMapper.updateById(pfBorder);
                    //增加平台冻结库存
                    parentSkuStock.setFrozenStock(parentSkuStock.getFrozenStock() + pfBorderItem.getQuantity());
                    if (pfUserSkuStockMapper.updateByIdAndVersion(parentSkuStock) == 0) {
                        throw new BusinessException("(代理发货)排队订单增加冻结量失败");
                    }
                    return;
                } else {
                    //减少上级合伙人平台库存
                    parentSkuStock.setStock(parentSkuStock.getStock() - pfBorderItem.getQuantity());
                    if (pfUserSkuStockMapper.updateByIdAndVersion(parentSkuStock) == 0) {
                        throw new BusinessException("减少上级合伙人平台库存失败");
                    }
                }
            }
            log.info("<9>增加收货方库存");
            PfUserSkuStock pfUserSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(pfBorder.getUserId(), pfBorderItem.getSkuId());
            //如果还没有库存信息直接初始化库存
            if (pfUserSkuStock == null) {
                pfUserSkuStock = new PfUserSkuStock();
                pfUserSkuStock.setCreateTime(new Date());
                pfUserSkuStock.setUserId(pfBorder.getUserId());
                pfUserSkuStock.setSpuId(pfBorderItem.getSpuId());
                pfUserSkuStock.setSkuId(pfBorderItem.getSkuId());
                pfUserSkuStock.setStock(pfBorderItem.getQuantity());
                pfUserSkuStock.setFrozenStock(0);
                pfUserSkuStock.setVersion(0);
                pfUserSkuStockMapper.insert(pfUserSkuStock);
            } else {
                pfUserSkuStock.setStock(pfUserSkuStock.getStock() + pfBorderItem.getQuantity());
                if (pfUserSkuStockMapper.updateByIdAndVersion(pfUserSkuStock) == 0) {
                    throw new BusinessException("增加用户平台库存失败");
                }
            }
        }
        log.info("<10>修改订单状态为已完成");
        pfBorder.setOrderStatus(BOrderStatus.Complete.getCode());
        pfBorderMapper.updateById(pfBorder);
        log.info("<11>订单完成,根据订单来计算结算和总销售额,并创建对应的账单子项");
        comUserAccountService.countingByOrder(pfBorder);
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
        String value3 = String.format("%04d", certificateInfo.getId());
        certificateCode = Code.append(value1).append(value2).append(value).append(value3).toString();
        return certificateCode;
    }

    /**
     * 给jpg添加文字并上传
     *
     * @author ZhaoLiang
     * @date 2016/3/31 11:26
     */
    private String uploadFile(String filePath, String[] markContent) throws Exception {
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
        ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
        ImageIO.write(bimage, "png", imOut);
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
     * 支付完成选择拿货方式
     *
     * @author ZhaoLiang
     * @date 2016/4/5 11:44
     */
    @Transactional
    public void updateBOrderSendType(ComUser comUser, Long bOrderId, Integer sendType, Long userAddressId) throws Exception {
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(bOrderId);
        if (pfBorder.getOrderStatus() != 1) {
            throw new BusinessException("订单状态有误！现在为：" + pfBorder.getOrderStatus());
        }
        if (comUser.getSendType() == 0) {
            comUser.setSendType(sendType);
            comUserMapper.updateByPrimaryKey(comUser);
        } else {
            sendType = comUser.getSendType();
        }
        //拿货方式(0未选择1平台代发2自己发货)
        if (sendType == 1) {
            //处理平台发货类型订单
            saveBOrderSendType(pfBorder);
        } else if (sendType == 2) {
            pfBorder.setSendType(sendType);
            pfBorder.setOrderStatus(BOrderStatus.WaitShip.getCode());//待发货
            pfBorderMapper.updateById(pfBorder);
            if (userAddressId != null && userAddressId > 0) {
                ComUserAddress comUserAddress = userAddressService.getUserAddressById(userAddressId);
                if (comUserAddress == null) {
                    throw new BusinessException("用户地址有误");
                }
                PfBorderConsignee pfBorderConsignee = new PfBorderConsignee();
                pfBorderConsignee.setCreateTime(new Date());
                pfBorderConsignee.setPfBorderId(pfBorder.getId());
                pfBorderConsignee.setUserId(comUserAddress.getUserId());
                pfBorderConsignee.setConsignee(comUserAddress.getName());
                pfBorderConsignee.setMobile(comUserAddress.getMobile());
                pfBorderConsignee.setProvinceId(comUserAddress.getProvinceId());
                pfBorderConsignee.setProvinceName(comUserAddress.getProvinceName());
                pfBorderConsignee.setCityId(comUserAddress.getCityId());
                pfBorderConsignee.setCityName(comUserAddress.getCityName());
                pfBorderConsignee.setRegionId(comUserAddress.getRegionId());
                pfBorderConsignee.setRegionName(comUserAddress.getRegionName());
                pfBorderConsignee.setAddress(comUserAddress.getAddress());
                pfBorderConsignee.setZip(comUserAddress.getZip());
                pfBorderConsigneeMapper.insert(pfBorderConsignee);
            } else {
                throw new BusinessException("请选择收货地址");
            }
        } else {
            throw new BusinessException("拿货方式有误");
        }
    }
}
