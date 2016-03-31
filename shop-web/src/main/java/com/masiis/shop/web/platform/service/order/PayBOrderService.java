package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.dao.beans.certificate.CertificateInfo;
import com.masiis.shop.dao.platform.certificate.CertificateMapper;
import com.masiis.shop.dao.platform.order.PfBorderItemMapper;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.platform.order.PfBorderOperationLogMapper;
import com.masiis.shop.dao.platform.order.PfBorderPaymentMapper;
import com.masiis.shop.dao.platform.product.ComAgentLevelMapper;
import com.masiis.shop.dao.platform.product.PfSkuStatisticMapper;
import com.masiis.shop.dao.platform.product.PfSkuStockMapper;
import com.masiis.shop.dao.platform.user.*;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.service.user.ComUserAccountService;
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
import java.io.File;
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
    ComUserAccountMapper accountMapper;
    @Resource
    PfUserBillItemMapper itemMapper;
    @Resource
    ComUserAccountRecordMapper recordMapper;
    @Resource
    private CertificateMapper certificateMapper;
    @Resource
    private PfUserCertificateMapper pfUserCertificateMapper;
    @Resource
    private ComAgentLevelMapper comAgentLevelMapper;

    @Transactional
    public void mainPayBOrder(PfBorderPayment pfBorderPayment, String outOrderId, String rootPath) throws Exception {
        if (pfBorderPayment == null) {
            throw new BusinessException("pfBorderPayment为空");
        }
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(pfBorderPayment.getPfBorderId());
        //拿货方式(0未选择1平台代发2自己发货)
        if (pfBorder.getSendType() == 1) {
            payBOrderI(pfBorderPayment, outOrderId, rootPath);
        } else if (pfBorder.getSendType() == 2) {
            patBOrderII(pfBorderPayment, outOrderId, rootPath);
        }
    }

    /**
     * 平台代理订单支付成功回调(平台代发货)
     *
     * @author ZhaoLiang
     * @date 2016/3/30 14:33
     * 操作详情：
     * <1>修改订单支付信息
     * <2>修改订单数据
     * <3>添加订单日志
     * <4>修改合伙人商品关系状态
     * <5>修改用户sku代理关系数据
     * <6>修改代理人数(如果是代理类型的订单增加修改sku代理人数)
     * <7>减少发货方库存 如果用户id是0操作平台库存
     * <8>增加收货方库存
     * <9>增加保证金
     * <10>订单完成,根据订单来计算结算和总销售额,并创建对应的账单子项
     */
    private void payBOrderI(PfBorderPayment pfBorderPayment, String outOrderId, String rootPath) throws Exception {
        log.info("<1>修改订单支付信息");
        pfBorderPayment.setOutOrderId(outOrderId);
        pfBorderPayment.setIsEnabled(1);//设置为有效
        pfBorderPaymentMapper.updateById(pfBorderPayment);
        BigDecimal payAmount = pfBorderPayment.getAmount();
        Long bOrderId = pfBorderPayment.getPfBorderId();
        log.info("<2>修改订单数据");
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(bOrderId);
        if (pfBorder.getSendType() != 1) {
            throw new BusinessException("订单拿货类型错误：为" + pfBorder.getSendType() + ",应为1.");
        }
        pfBorder.setReceivableAmount(pfBorder.getReceivableAmount().subtract(payAmount));
        pfBorder.setPayAmount(pfBorder.getPayAmount().add(payAmount));
        pfBorder.setPayTime(new Date());
        pfBorder.setPayStatus(1);//已付款
        pfBorder.setOrderStatus(1);//已付款
        pfBorderMapper.updateById(pfBorder);
        log.info("<3>添加订单日志");
        PfBorderOperationLog pfBorderOperationLog = new PfBorderOperationLog();
        pfBorderOperationLog.setCreateMan(pfBorder.getUserId());
        pfBorderOperationLog.setCreateTime(new Date());
        pfBorderOperationLog.setPfBorderStatus(1);
        pfBorderOperationLog.setPfBorderId(bOrderId);
        pfBorderOperationLog.setRemark("订单已支付");
        pfBorderOperationLogMapper.insert(pfBorderOperationLog);
        log.info("<4>修改合伙人商品关系状态");
        ComUser comUser = comUserMapper.selectByPrimaryKey(pfBorder.getUserId());
        if (comUser.getIsAgent() == 0) {
            comUser.setIsAgent(1);
            comUserMapper.updateByPrimaryKey(comUser);
        }
        for (PfBorderItem pfBorderItem : pfBorderItemMapper.selectAllByOrderId(bOrderId)) {
            log.info("<5>修改用户sku代理关系数据");
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
                pfUserCertificateMapper.updateById(pfUserCertificate);
                pfUserSku.setCode(pfUserCertificate.getCode());
                pfUserSkuMapper.updateByPrimaryKey(pfUserSku);
            }

            log.info("<6>修改代理人数(如果是代理类型的订单增加修改sku代理人数)");
            if (pfBorder.getOrderType() == 0) {
                pfSkuStatisticMapper.updateAgentNumBySkuId(pfBorderItem.getSkuId());
            }
            log.info("<7>减少发货方库存 如果用户id是0操作平台库存");
            if (pfBorder.getUserPid() == 0) {
                PfSkuStock pfSkuStock = pfSkuStockMapper.selectBySkuId(pfBorderItem.getSkuId());
                if (pfSkuStock.getStock() - pfSkuStock.getFrozenStock() < pfBorderItem.getQuantity()) {
                    //平台库存不足，排单处理
                    pfBorder.setOrderStatus(6);//排队订单
                    pfBorderMapper.updateById(pfBorder);
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
                } else {
                    //减少上级合伙人平台库存
                    parentSkuStock.setStock(parentSkuStock.getStock() - pfBorderItem.getQuantity());
                    if (pfUserSkuStockMapper.updateByIdAndVersion(parentSkuStock) == 0) {
                        throw new BusinessException("减少上级合伙人平台库存失败");
                    }
                }
            }
            log.info("<8>增加收货方库存");
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
        log.info("<9>增加保证金");
        if (pfBorder.getBailAmount().compareTo(BigDecimal.ZERO) > 0) {
            ComUserAccount accountS = accountMapper.findByUserId(pfBorder.getUserId());
            ComUserAccountRecord recordS = comUserAccountService.createAccountRecordByBail(pfBorder.getBailAmount(), accountS, pfBorder.getId());
            // 保存修改前的金额
            recordS.setPrevFee(accountS.getBailFee());
            accountS.setBailFee(accountS.getBailFee().add(pfBorder.getBailAmount()));
            // 保存修改后的金额
            recordS.setNextFee(accountS.getBailFee());
            recordMapper.insert(recordS);
            int typeS = accountMapper.updateByIdWithVersion(accountS);
            if (typeS == 0) {
                throw new BusinessException("修改进货方成本账户失败!");
            }
        }
        log.info("<10>订单完成,根据订单来计算结算和总销售额,并创建对应的账单子项");
        comUserAccountService.countingByOrder(pfBorder);
    }

    /**
     * @author ZhaoLiang
     * @date 2016/3/30 20:39
     * 操作详情：
     * <1>修改订单支付信息
     * <2>修改订单数据
     * <3>添加订单日志
     * <4>修改合伙人商品关系状态
     * <5>修改用户sku代理关系支付状态
     * <6>修改代理人数(如果是代理类型的订单增加修改sku代理人数)
     * <7>订单完成,根据订单来计算结算和总销售额,并创建对应的账单子项
     */
    private void patBOrderII(PfBorderPayment pfBorderPayment, String outOrderId, String rootPath) throws Exception {
        log.info("<1>修改订单支付信息");
        pfBorderPayment.setOutOrderId(outOrderId);
        pfBorderPayment.setIsEnabled(1);//设置为有效
        pfBorderPaymentMapper.updateById(pfBorderPayment);
        BigDecimal payAmount = pfBorderPayment.getAmount();
        Long bOrderId = pfBorderPayment.getPfBorderId();
        log.info("<2>修改订单数据");
        PfBorder pfBorder = pfBorderMapper.selectByPrimaryKey(bOrderId);
        if (pfBorder.getSendType() != 1) {
            throw new BusinessException("订单拿货类型错误：为" + pfBorder.getSendType() + ",应为1.");
        }
        pfBorder.setReceivableAmount(pfBorder.getReceivableAmount().subtract(payAmount));
        pfBorder.setPayAmount(pfBorder.getPayAmount().add(payAmount));
        pfBorder.setPayTime(new Date());
        pfBorder.setPayStatus(1);//已付款
        pfBorder.setOrderStatus(1);//已付款
        pfBorderMapper.updateById(pfBorder);
        log.info("<3>添加订单日志");
        PfBorderOperationLog pfBorderOperationLog = new PfBorderOperationLog();
        pfBorderOperationLog.setCreateMan(pfBorder.getUserId());
        pfBorderOperationLog.setCreateTime(new Date());
        pfBorderOperationLog.setPfBorderStatus(1);
        pfBorderOperationLog.setPfBorderId(bOrderId);
        pfBorderOperationLog.setRemark("订单已支付");
        pfBorderOperationLogMapper.insert(pfBorderOperationLog);
        log.info("<4>修改合伙人商品关系状态");
        ComUser comUser = comUserMapper.selectByPrimaryKey(pfBorder.getUserId());
        if (comUser.getIsAgent() == 0) {
            comUser.setIsAgent(1);
            comUserMapper.updateByPrimaryKey(comUser);
        }
        for (PfBorderItem pfBorderItem : pfBorderItemMapper.selectAllByOrderId(bOrderId)) {
            log.info("<5>修改用户sku代理关系数据");
            PfUserSku pfUserSku = pfUserSkuMapper.selectByOrderIdAndUserIdAndSkuId(bOrderId, comUser.getId(), pfBorderItem.getSkuId());
            //订单类型(0代理1补货2拿货)
            if (pfUserSku != null && pfBorder.getOrderType() == 0) {
                pfUserSku.setIsCertificate(1);
                pfUserSku.setIsPay(1);
                pfUserSku.setBail(pfBorderItem.getBailAmount());
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
                pfUserCertificateMapper.updateById(pfUserCertificate);
                pfUserSku.setCode(pfUserCertificate.getCode());
                pfUserSkuMapper.updateByPrimaryKey(pfUserSku);
            }

            log.info("<6>修改代理人数(如果是代理类型的订单增加修改sku代理人数)");
            if (pfBorder.getOrderType() == 0) {
                pfSkuStatisticMapper.updateAgentNumBySkuId(pfBorderItem.getSkuId());
            }
        }
        log.info("<10>订单完成,根据订单来计算结算和总销售额,并创建对应的账单子项");
        comUserAccountService.countingByOrder(pfBorder);
    }

    private String getCertificateCode(PfUserCertificate certificateInfo) throws Exception {
        String certificateCode = null;
        String value = "";
        StringBuffer Code = new StringBuffer("MASIIS");
        value = DateUtil.Date2String(certificateInfo.getBeginTime(), "yyyy", null).substring(2);//时间
        String value1 = certificateInfo.getAgentLevelId().toString();
        String value2 = String.format("%04d", certificateInfo.getSkuId());
        String value3 = String.format("%04d", certificateInfo.getId());
        certificateCode = Code.append(value1).append(value2).append(value).append(value3).toString();
        return certificateCode;
    }

    //给jpg添加文字并上传
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
        OSSObjectUtils.uploadFile("mmshop", "static/user/certificate/" + pname + ".jpg", is);
        return pname;
    }

    /**
     * 生成随机文件名：当前年月日时分秒+五位随机数
     *
     * @return
     */
    private String getRandomFileName() {

        SimpleDateFormat simpleDateFormat;

        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        Date date = new Date();

        String str = simpleDateFormat.format(date);

        Random random = new Random();

        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数

        return rannum + str;// 当前时间
    }
}
