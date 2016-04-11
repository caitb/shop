package com.masiis.shop.admin.service.wx;

import com.masiis.shop.admin.beans.wx.WxPayUserBeanReq;
import com.masiis.shop.admin.beans.wx.WxPayUserBeanRes;
import com.masiis.shop.admin.constants.wx.WxPayUserCons;
import com.masiis.shop.admin.utils.WxBeanUtils;
import com.masiis.shop.common.enums.SfUserExtractAuditTypeEnum;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.HttpsUtils;
import com.masiis.shop.common.util.LocalInetAddressUtil;
import com.masiis.shop.common.util.SysBeanUtils;
import com.masiis.shop.dao.mall.user.SfUserAccountMapper;
import com.masiis.shop.dao.mall.user.SfUserExtractApplyMapper;
import com.masiis.shop.dao.mall.user.SfUserExtractPaymentMapper;
import com.masiis.shop.dao.platform.user.ComUserAccountMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.ComWxUserMapper;
import com.masiis.shop.dao.po.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Date;

/**
 * @Date:2016/4/9
 * @auth:lzh
 */
@Service
public class WxPayUserService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private SfUserExtractApplyMapper applyMapper;
    @Resource
    private ComUserMapper userMapper;
    @Resource
    private ComWxUserMapper wxUserMapper;
    @Resource
    private SfUserAccountMapper accountMapper;
    @Resource
    private SfUserExtractPaymentMapper paymentMapper;

    /**
     * 小铺端用户提现,企业微信给用户打款method
     *
     * @param apply     提现申请记录
     * @param handler   后台操作人
     */
    @Transactional
    public void payUserByExtractApply(SfUserExtractApply apply, String handler){
        log.info("小铺端用户提现微信打款操作开始...");

        try{
            // 校验apply记录状态
            apply = applyMapper.selectByPrimaryKey(apply.getId());
            if(apply == null){
                log.error("小铺用户提现记录错误,该记录不存在!");
                throw new BusinessException("小铺用户提现记录错误,该记录不存在!");
            }
            if(apply.getAuditType().intValue() !=
                    SfUserExtractAuditTypeEnum.IN_PAYING.getCode().intValue()){
                log.error("小铺用户提现记录审核状态不正确,非" + SfUserExtractAuditTypeEnum.IN_PAYING.getDesc() + "状态");
                throw new BusinessException("小铺用户提现记录审核状态非"
                        + SfUserExtractAuditTypeEnum.IN_PAYING.getDesc() + "状态");
            }

            // 获取用户openid
            ComUser user = userMapper.selectByPrimaryKey(apply.getComUserId());
            if(StringUtils.isBlank(user.getWxUnionid())){
                log.error("该用户未绑定微信,comUserId:" + user.getId());
                throw new BusinessException("该用户未绑定微信!");
            }
            ComWxUser wxUser = wxUserMapper.selectByUnionidAndAppid(user.getWxUnionid(), WxPayUserCons.APPID);
            if(wxUser == null){
                log.error("微信账户错误,请联系管理员");
                throw new BusinessException("数据错误,请联系管理员");
            }

            // 校验当前可提现额度与申请提现额度比较
            SfUserAccount account = accountMapper.selectByUserId(user.getId());
            if(apply.getExtractWay().intValue() != 1
                    || apply.getExtractFee().compareTo(new BigDecimal(1)) < 0){
                throw new BusinessException("提现方式不正确或者提现金额不正确!");
            }
            if(account.getExtractableFee().compareTo(apply.getExtractFee()) < 0){
                // 账户可提现额度小于申请提现金额
                log.error("账户可提现额度小于申请提现金额");
                throw new BusinessException("账户可提现额度小于申请提现金额");
            }

            // 组织请求数据
            WxPayUserBeanReq req = createWxPayUserBeanReqBy(apply, wxUser);
            WxPayUserBeanRes result = new HttpsUtils(WxPayUserCons.APP_MCHID, new File("F:/VS/cert01/apiclient_cert.p12"))
                    .sendPostByXMLWithParse(WxPayUserCons.URL_PAY_USER, req, WxPayUserBeanRes.class);
            if(result == null){
                throw new BusinessException("发送https请求或解析xml结果报错!");
            }
            if(!"SUCCESS".equals(result.getReturn_code())){
                log.error(result.getReturn_msg() + ",用户id:" + user.getId());
                throw new BusinessException(result.getReturn_msg());
            }
            if(!"SUCCESS".equals(result.getResult_code())){
                log.error(result.getErr_code_des() + ",用户id:" + user.getId());
                throw new BusinessException(result.getErr_code_des());
            }
            if(!req.getNonce_str().equals(result.getNonce_str())
                    || !req.getPartner_trade_no().equals(result.getPartner_trade_no())){
                log.error("请求随机串或者系统订单号与响应的不一致,但支付成功!");
            }

            log.info("微信打款成功,操作流水号:" + result.getPartner_trade_no());

            // 打款成功后的操作,加入事务
            handleAfterPayUser(account, apply, req, result, handler);

        } catch (Exception e) {
            log.error("用户提现打款出错," + e.getMessage());
        }

    }

    /**
     * 微信付款给用户成功后的操作
     *
     * @param account
     * @param apply
     * @param req
     * @param result
     * @param handler
     */
    @Transactional
    private void handleAfterPayUser(SfUserAccount account, SfUserExtractApply apply, WxPayUserBeanReq req,
                                    WxPayUserBeanRes result, String handler) {
        // 小铺用户可提现额度减少
        account.setExtractableFee(account.getExtractableFee().subtract(apply.getExtractFee()));
        accountMapper.updateByPrimaryKey(account);
        // account变动记录

        // 修改申请记录的状态
        apply.setAuditType(SfUserExtractAuditTypeEnum.ALREADY_PAY.getCode());
        applyMapper.updateByPrimaryKey(apply);
        // 插入提现申请支付记录
        SfUserExtractPayment payment = createSfExtractPayment(req, result, apply, handler);
        paymentMapper.insert(payment);
    }

    private SfUserExtractPayment createSfExtractPayment(WxPayUserBeanReq req, WxPayUserBeanRes res,
                                                        SfUserExtractApply apply, String handler) {
        SfUserExtractPayment payment = new SfUserExtractPayment();

        payment.setAmont(apply.getExtractFee());
        payment.setCreateTime(new Date());
        payment.setExtractApplyId(apply.getId());
        payment.setExtractSerialNum(req.getPartner_trade_no());
        payment.setExtractWay(apply.getExtractWay());
        payment.setPayTime(DateUtil.String2Date(res.getPayment_time(), "yyyy-MM-dd HH:mm:ss"));
        payment.setOutOrderId(res.getPayment_no());
        payment.setIsSuccess(0);

        return payment;
    }

    public static void main(String[] ars) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        WxPayUserBeanReq req = new WxPayUserBeanReq();
        req.setOpenid("ojLdDwksB_M2b5R9ZNIk3UdmfaA8");
        // 请求的金额是分为单位
        req.setAmount(100);
        req.setDesc("用户提现");
        req.setMch_appid(WxPayUserCons.APPID);
        req.setMchid(WxPayUserCons.APP_MCHID);
        req.setNonce_str(WxBeanUtils.createGenerateStr());
        req.setPartner_trade_no(SysBeanUtils.createSfUserExtractPaySerialNum());
        // 目前先用不校验
        req.setCheck_name("NO_CHECK");
        req.setSpbill_create_ip(LocalInetAddressUtil.getHostIp());
        req.setSign(WxBeanUtils.toSignString(req));
        String res = new HttpsUtils(WxPayUserCons.APP_MCHID, new File("F:/VS/cert01/apiclient_cert.p12"))
                .sendPostByXML(WxPayUserCons.URL_PAY_USER, req);
        System.out.println(res);
    }

    /**
     * 根据提现申请记录创建微信付款请求对象
     *
     * @param apply
     * @return
     */
    private WxPayUserBeanReq createWxPayUserBeanReqBy(SfUserExtractApply apply, ComWxUser wxUser) {
        WxPayUserBeanReq req = new WxPayUserBeanReq();

        req.setOpenid(wxUser.getOpenid());
        // 请求的金额是分为单位
        req.setAmount(apply.getExtractFee().multiply(new BigDecimal(100)).intValue());
        req.setDesc("用户提现");
        req.setMch_appid(WxPayUserCons.APPID);
        req.setMchid(WxPayUserCons.APP_MCHID);
        req.setNonce_str(WxBeanUtils.createGenerateStr());
        req.setPartner_trade_no(SysBeanUtils.createSfUserExtractPaySerialNum());
        // 目前先用不校验
        req.setCheck_name("NO_CHECK");
        req.setSpbill_create_ip(LocalInetAddressUtil.getHostIp());

        return req;
    }
}
