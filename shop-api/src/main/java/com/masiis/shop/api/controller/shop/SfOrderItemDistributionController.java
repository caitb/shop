package com.masiis.shop.api.controller.shop;

import com.masiis.shop.api.bean.base.ShopApiConstant;
import com.masiis.shop.api.bean.base.ShopApiResModel;
import com.masiis.shop.api.bean.shop.DistributionRecord;
import com.masiis.shop.api.bean.shop.SfDistribution;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.service.shop.SfOrderItemDistributionService;
import com.masiis.shop.api.service.shop.SfShopService;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.beans.order.SfDistributionPerson;
import com.masiis.shop.dao.beans.order.SfDistributionRecord;
import com.masiis.shop.dao.po.SfShop;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 小铺分销记录
 * Created by wangbingjian on 2016/4/22.
 */
@Controller
@RequestMapping(value = "/distribution")
public class SfOrderItemDistributionController extends BaseController {

    private static final Logger logger = Logger.getLogger(SfOrderItemDistributionController.class);
    @Autowired
    private SfOrderItemDistributionService sfOrderItemDistributionService;
    @Autowired
    private SfShopService sfShopService;

    /**
     * 分校记录首页
     * @param userId    用户id
     * @param yDate      日期字符串精确到月份 格式为"yyyyMM"
     * @author:wbj
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/home.do",method = RequestMethod.GET)
    public ShopApiResModel itemDistributionHome(@RequestParam(value = "userId",required = true) Long userId,
                                                @RequestParam(value = "yDate",required = true) String yDate,
                                                @RequestParam(value = "currentPage",required = true) Integer currentPage,
                                                @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){
        logger.info("分销记录查询");
        logger.info("userCode="+userId);
        logger.info("yDate="+yDate);
        logger.info("currentPage="+currentPage);
        ShopApiResModel responseModel = new ShopApiResModel();
        if (userId == null){
            responseModel.setResCode(ShopApiConstant.PARAMETER_ERROR + "");
            responseModel.setResMsg("userId");
            return responseModel;
        }
        if (!StringUtils.isNotBlank(yDate)){
            responseModel.setResCode(ShopApiConstant.PARAMETER_ERROR + "");
            responseModel.setResMsg("yDate");
            return responseModel;
        }
        if (currentPage < 1){
            responseModel.setResCode(ShopApiConstant.PARAMETER_ERROR + "");
            responseModel.setResMsg("currentPage不大于0");
            return responseModel;
        }
        Date date = DateUtil.String2Date(yDate);
        Date start = DateUtil.getFirstTimeInMonth(date);
        Date end = DateUtil.getLastTimeInMonth(date);
        Integer totalCount = sfOrderItemDistributionService.findCountSfDistributionRecordLimit(userId,start,end);
        if (totalCount == 0){
            responseModel.setResCode(ShopApiConstant.DATA_NOT_EXIST + "");
            responseModel.setResMsg("noData");
            return responseModel;
        }
        try {
            SfDistributionRecord sfCount = sfOrderItemDistributionService.findCountSfDistributionRecord(userId);
            SfShop sfShop = sfShopService.getSfShopByUserId(userId);
            DistributionRecord distributionRecord = new DistributionRecord();
            Integer totalPage = 0;
            if (sfCount.getCount() == 0){
                distributionRecord.setDistributionAmount(new BigDecimal(0));
                distributionRecord.setSumLevel(0);
                distributionRecord.setSaleAmount(sfShop == null?new BigDecimal(0):sfShop.getSaleAmount());
            }else {
                //默认设置每页显示10条
                totalPage = totalCount%pageSize == 0 ? pageSize/10 : pageSize/10 + 1;
                List<SfDistributionRecord> sfDistributionRecords = sfOrderItemDistributionService.findListSfDistributionRecordLimit(userId,start,end,currentPage,pageSize);
                List<SfDistribution> sfDistributions = new ArrayList<>();
                distributionRecord.setDistributionAmount(sfCount.getDistributionAmount());
                distributionRecord.setSaleAmount(sfShop == null?new BigDecimal(0):sfShop.getSaleAmount());
                distributionRecord.setSumLevel(sfCount.getSumLevel());
                SfDistribution sfDistribution;
                BigDecimal personTotalAmount;
                for(int i = 0; i < sfDistributionRecords.size(); i++){
                    sfDistribution = new SfDistribution();
                    sfDistribution.setSumLevel(sfDistributionRecords.get(i).getSumLevel());
                    sfDistribution.setCount(sfDistributionRecords.get(i).getCount());
                    sfDistribution.setCreateTime(sfDistributionRecords.get(i).getCreateTime());
                    sfDistribution.setItemId(sfDistributionRecords.get(i).getItemId());
                    sfDistribution.setLevel(sfDistributionRecords.get(i).getLevel());
                    sfDistribution.setOrderAmount(sfDistributionRecords.get(i).getOrderAmount());
                    sfDistribution.setOrderId(sfDistributionRecords.get(i).getOrderId());
                    sfDistribution.setSkuId(sfDistributionRecords.get(i).getSkuId());
                    sfDistribution.setSkuName(sfDistributionRecords.get(i).getSkuName());
                    sfDistribution.setWxNkName(sfDistributionRecords.get(i).getWxNkName());
                    List<SfDistributionPerson> persons = sfOrderItemDistributionService.findListSfDistributionPerson(sfDistributionRecords.get(i).getItemId());
                    personTotalAmount = new BigDecimal(0);
                    for (SfDistributionPerson sfDistributionPerson : persons){
                        personTotalAmount.add(sfDistributionPerson.getAmount());
                    }
                    sfDistribution.setTotalAmount(personTotalAmount);
                    sfDistribution.setSfDistributionPersons(persons);
                    sfDistributions.add(sfDistribution);
                }
                distributionRecord.setSfDistributions(sfDistributions);
            }
            responseModel.setData(distributionRecord);
            responseModel.setResCode(ShopApiConstant.SUCCESS + "");
            responseModel.setResMsg(SUCCESS);
            responseModel.setCurrentPage(currentPage);
            responseModel.setTotalCount(totalCount);
            responseModel.setTotalPage(totalPage);
            responseModel.setData(distributionRecord);
        }catch (Exception e){
            e.printStackTrace();
            responseModel.setResCode(ShopApiConstant.ERROR + "");
            responseModel.setResMsg(ERROR);
            return responseModel;
        }
        return responseModel;
    }


//    public static void main(String[] args){
//        try{
//            byte[] info ="待签名信息".getBytes();
//
//            //产生RSA密钥对(myKeyPair)
//            KeyPairGenerator myKeyGen= KeyPairGenerator.getInstance("RSA");
//            myKeyGen.initialize(1024);
//            KeyPair myKeyPair = myKeyGen.generateKeyPair();
//            System.out.println( "得到RSA密钥对    "+myKeyPair);
//
//            //产生Signature对象,用私钥对信息(info)签名.
//            Signature mySig = Signature.getInstance("SHA1WithRSA");  //用指定算法产生签名对象
//            mySig.initSign(myKeyPair.getPrivate());  //用私钥初始化签名对象
//            mySig.update(info);  //将待签名的数据传送给签名对象(须在初始化之后)
//            byte[] sigResult = mySig.sign();  //返回签名结果字节数组
//            System.out.println("签名后信息: "+ new String(sigResult) );
//
//            //用公钥验证签名结果
//            mySig.initVerify(myKeyPair.getPublic());  //使用公钥初始化签名对象,用于验证签名
//            System.out.println("########"+myKeyPair.getPublic());
//            mySig.update(info); //更新签名内容
//            boolean verify= mySig.verify(sigResult); //得到验证结果
//            System.out.println( "签名验证结果: " +verify);
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//    }
}
