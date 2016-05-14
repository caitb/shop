package com.masiis.shop.admin.service.wx;

import com.masiis.shop.admin.beans.wx.WxPayUserBeanReq;
import com.masiis.shop.admin.beans.wx.WxPayUserBeanRes;
import com.masiis.shop.admin.utils.WxBeanUtils;
import com.masiis.shop.common.constant.wx.WxConsSF;
import com.masiis.shop.common.enums.SfUserExtractAuditTypeEnum;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.*;
import com.masiis.shop.dao.mall.user.SfUserAccountMapper;
import com.masiis.shop.dao.mall.user.SfUserAccountRecordMapper;
import com.masiis.shop.dao.mall.user.SfUserExtractApplyMapper;
import com.masiis.shop.dao.mall.user.SfUserExtractPaymentMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.ComWxUserMapper;
import com.masiis.shop.dao.po.*;
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
    @Resource
    private SfUserAccountRecordMapper accountRecordMapper;

    /**
     * 小铺端用户提现,企业微信给用户打款method
     *
     * @param apply     提现申请记录
     * @param handler   后台操作人
     */
    @Transactional
    public void payUserByExtractApply(SfUserExtractApply apply, String handler, String rootPath){
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
            ComWxUser wxUser = wxUserMapper.selectByUnionidAndAppid(user.getWxUnionid(), WxConsSF.APPID);
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
            if(account.getAppliedFee().compareTo(apply.getExtractFee()) < 0){
                // 账户可提现额度(可提现金额减去已经申请金额)小于申请提现金额
                log.error("账户申请提现额度小于申请提现金额");
                throw new BusinessException("账户申请提现额度小于申请提现金额");
            }
            if(account.getExtractableFee().compareTo(apply.getExtractFee()) < 0){
                // 账户可提现额度(可提现金额减去已经申请金额)小于申请提现金额
                log.error("账户可提现额度小于申请提现金额");
                throw new BusinessException("账户可提现额度小于申请提现金额");
            }

            // 小铺用户可提现额度减少
            account.setExtractableFee(account.getExtractableFee().subtract(apply.getExtractFee()));
            account.setAppliedFee(account.getAppliedFee().subtract(apply.getExtractFee()));
            int affectNums = accountMapper.updateByIdAndVersion(account);
            if(affectNums == 0){
                // update冲突,再重新查询检查一次
                log.error("account更新失败");
                throw new BusinessException("update冲突,请再次重试");
            }

            // 组织请求数据
            WxPayUserBeanReq req = createWxPayUserBeanReqBy(apply, wxUser);
            req.setSign(WxBeanUtils.toSignString(req));
            System.out.println("证书路径:" + rootPath + WxConsSF.PATH_CERT);
            WxPayUserBeanRes result = new HttpsUtils(WxConsSF.WX_PAY_MCHID, new File(rootPath + WxConsSF.PATH_CERT))
                    .sendPostByXMLWithParse(WxConsSF.WX_PAY_URL_USER, req, WxPayUserBeanRes.class);
            if(result == null){
                throw new BusinessException("发送https请求或解析xml结果报错!");
            }
            if(!"SUCCESS".equals(result.getReturn_code())){
                log.error("result:" + result);
                log.error(result.getReturn_msg() + ",用户id:" + user.getId());
                throw new BusinessException(result.getReturn_msg());
            }
            if(!"SUCCESS".equals(result.getResult_code())){
                log.error("result:" + result);
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
            throw new BusinessException(e.getMessage());
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
        // account变动记录
        SfUserAccountRecord record = createSfUserAccountRecord(account, apply, handler);
        accountRecordMapper.insert(record);
        // 修改申请记录的状态
        apply.setAuditType(SfUserExtractAuditTypeEnum.ALREADY_PAY.getCode());
        applyMapper.updateByPrimaryKey(apply);
        // 插入提现申请支付记录
        SfUserExtractPayment payment = createSfExtractPayment(req, result, apply, handler);
        paymentMapper.insert(payment);
    }

    private SfUserAccountRecord createSfUserAccountRecord(SfUserAccount account, SfUserExtractApply apply, String handler) {
        SfUserAccountRecord record = new SfUserAccountRecord();

        record.setComUserId(account.getUserId());
        record.setFeeType(2);
        record.setHandleFee(apply.getExtractFee());
        record.setHandleSerialNum(SysBeanUtils.createSfAccountRecordSerialNum());
        record.setHandleType(0);
        record.setHandleTime(new Date());
        record.setSfUserAccountId(account.getId());
        record.setSourceId(apply.getId());
        record.setHandler(handler);
        record.setPrevFee(account.getExtractableFee().add(apply.getExtractFee()));
        record.setNextFee(account.getExtractableFee());

        return record;
    }

    /**
     * 根据微信打款用户请求结果和提现申请记录来创建支付记录
     *
     * @param req
     * @param res
     * @param apply
     * @param handler
     * @return
     */
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
        req.setMch_appid(WxConsSF.APPID);
        req.setMchid(WxConsSF.WX_PAY_MCHID);
        req.setNonce_str(WxBeanUtils.createGenerateStr());
        req.setPartner_trade_no(SysBeanUtils.createSfUserExtractPaySerialNum());
        // 目前先用不校验
        req.setCheck_name("NO_CHECK");
        req.setSpbill_create_ip(LocalInetAddressUtil.getHostIp());

        return req;
    }
}
