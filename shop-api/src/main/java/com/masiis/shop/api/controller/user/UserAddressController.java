package com.masiis.shop.api.controller.user;

import com.masiis.shop.api.bean.user.ComUserAddressReq;
import com.masiis.shop.api.bean.user.ComUserAddressRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.service.user.UserAddressService;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAddress;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hanzengzhi on 2016/3/7.
 * 用户地址
 */
@Controller
@RequestMapping("/userAddress")
public class UserAddressController extends BaseController {

    @Resource
    private UserAddressService userAddressService;

    //管理地址跳转到选择地址界面
    private static  final  int managePageToChooseAddressPageTag = 0;
    //管理地址跳转到个人中心界面
    private static  final  int managePageToPersonalInfoPageTag = 1;

    //新增地址增加完跳转到订单界面
    public static final int addAddressPageToOrderPage = 0;
    //新增地址增加完跳转到管理地址界面
    public static final int getAddAddressPageToPersonalInfoPage =1;


    /**
     * 新增地址
     *
     * @author hanzengzhi
     * @date 2016/3/7 23:27
     */
    @RequestMapping("/addOrUpdateAddress.do")
    @ResponseBody
    @SignValid(paramType = ComUserAddressReq.class)
    public ComUserAddressRes addOrUpdateAddress(HttpServletRequest request,
                                     ComUserAddressReq addressReq,
                                     ComUser comUser ) {
        ComUserAddressRes addressRes = new ComUserAddressRes();
        try{
            ComUserAddress address = new ComUserAddress();
            addressReq.setUserId(comUser.getId());
            String s = null;
            if (comUser != null) {
                if (addressReq!=null&&isValidateParam(addressReq,address)){
                    addressReq.setUserId(comUser.getId());
                    addressReq.setCreateTime(new Date());
                    s = userAddressService.addOrUpdateAddress(request,addressReq.getId(),addressReq.getIsDefault(),address,addressReq.getOperateType(),10000);
                }else{
                    addressRes.setResCode(SysResCodeCons.RES_CODE_REQ_STRUCT_INVALID);
                    addressRes.setResMsg(SysResCodeCons.RES_CODE_REQ_STRUCT_INVALID_MSG);
                }
            } else {
                addressRes.setResCode(SysResCodeCons.RES_CODE_REQ_STRUCT_INVALID);
                addressRes.setResMsg(SysResCodeCons.RES_CODE_REQ_STRUCT_INVALID_MSG);
            }
            if (s!=null&&!s.equals("false")){
                addressRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
                addressRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
            }
        }catch (Exception ex){
            addressRes.setResCode(SysResCodeCons.RES_CODE_ADDRESS_ADD_FAIL);
            addressRes.setResMsg(ex.getMessage());
        }
        return addressRes;
    }

    /**
     * 获得用户地址
     *
     * @author hanzengzhi
     * @date 2016/3/7 23:26
     */
    @RequestMapping("/getUserAddressByUserId.do")
    @ResponseBody
    @SignValid(paramType = ComUserAddressReq.class)
    public ComUserAddressRes getUserAddressByUserId(HttpServletRequest request,
                                         ComUserAddressReq addressReq,
                                         ComUser comUser) {
        ComUserAddressRes addressRes = new ComUserAddressRes();
        try{
            if (comUser == null) {
                addressRes.setResCode(SysResCodeCons.RES_CODE_REQ_STRUCT_INVALID);
                addressRes.setResMsg(SysResCodeCons.RES_CODE_REQ_STRUCT_INVALID_MSG);
                return addressRes;
            }
            ComUserAddress comUserAddress = new ComUserAddress();
            comUserAddress.setUserId(comUser.getId());
            List<ComUserAddress> comUserAddressList = userAddressService.queryComUserAddressesByParam(comUserAddress);
            addressRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            addressRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
            addressRes.setAddresses(comUserAddressList);
        }catch (Exception ex){
            addressRes.setResCode(SysResCodeCons.RES_CODE_ADDRESS_QUERY_FAIL);
            addressRes.setResMsg(ex.getMessage());
        }
        return addressRes;
    }
    /**
     * 查询某个地址
     *
     * @author hanzengzhi
     * @date 2016/3/9 18:14
     */
    @RequestMapping("/getAddressById.do")
    @ResponseBody
    @SignValid(paramType = ComUserAddressReq.class)
    public ComUserAddressRes getAddressById(HttpServletRequest request,
                                 ComUserAddressReq addressReq,
                                 ComUser comUser
                                ) throws Exception {
        ComUserAddressRes addressRes = new ComUserAddressRes();
        ComUserAddress comUserAddress = null;
        List<ComUserAddress> addresses = new ArrayList<>();
        if (addressReq==null||addressReq.getId()==null){
            addressRes.setResCode(SysResCodeCons.RES_CODE_REQ_STRUCT_INVALID);
            addressRes.setResMsg(SysResCodeCons.RES_CODE_REQ_STRUCT_INVALID_MSG);
            return addressRes;
        }else{
            comUserAddress = userAddressService.getUserAddressById(addressReq.getId());
            addresses.add(comUserAddress);
        }
        addressRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
        addressRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        addressRes.setAddresses(addresses);
        return addressRes;
    }
    /**
     * 删除地址
     *
     * @author hanzengzhi
     * @date 2016/3/9 15:21
     */
    @RequestMapping("/deleteUserAddressById.do")
    @ResponseBody
    @SignValid(paramType = ComUserAddressReq.class)
    public ComUserAddressRes deleteUserAddressById(HttpServletRequest request,
                                      HttpServletResponse response,
                                      ComUserAddressReq addressReq,
                                      ComUser comUser)  {
        ComUserAddressRes addressRes = new ComUserAddressRes();
        try{
            if (addressReq!=null&&addressReq.getId()!=null&&comUser!=null&&comUser.getId()!=null){
                Long i = userAddressService.deleteUserAddressById(addressReq.getId(), comUser.getId(), Long.parseLong(addressReq.getIsDefault()+""));
                if (i == 0) {
                    addressRes.setResCode(SysResCodeCons.RES_CODE_ADDRESS_DELETE_FAIL);
                    addressRes.setResCode(SysResCodeCons.RES_CODE_ADDRESS_DELETE_FAIL_MEG);
                } else {
                    addressRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
                    addressRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
                }
            }else{
                addressRes.setResCode(SysResCodeCons.RES_CODE_REQ_STRUCT_INVALID);
                addressRes.setResMsg(SysResCodeCons.RES_CODE_REQ_STRUCT_INVALID_MSG);
            }
        }catch (Exception ex){
            addressRes.setResCode(SysResCodeCons.RES_CODE_ADDRESS_DELETE_FAIL);
            addressRes.setResCode(ex.getMessage());
        }
        return addressRes;
    }

    /**
     * 设置地址为默认地址
     *
     * @author hanzengzhi
     * @date 2016/3/9 16:26
     */
    @RequestMapping("/settingDefaultAddress.do")
    @ResponseBody
    @SignValid(paramType = ComUserAddressReq.class)
    public ComUserAddressRes settingDefaultAddress(HttpServletRequest request,
                                         ComUserAddressReq addressReq,
                                         ComUser comUser) {
        ComUserAddressRes addressRes = new ComUserAddressRes();
        try{
            if (addressReq!=null&&addressReq.getId()!=null&&comUser!=null&&comUser.getId()!=null){
                Boolean bl = userAddressService.settingDefaultAddress(addressReq.getId(), comUser.getId());
                if (bl){
                    addressRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
                    addressRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
                }else{
                    addressRes.setResCode(SysResCodeCons.RES_CODE_ADDRESS_DEFAULT_FAIL);
                    addressRes.setResMsg(SysResCodeCons.RES_CODE_ADDRESS_DEFAULT_FAIL_MEG);
                }
            }else{
                addressRes.setResCode(SysResCodeCons.RES_CODE_REQ_STRUCT_INVALID);
                addressRes.setResMsg(SysResCodeCons.RES_CODE_REQ_STRUCT_INVALID_MSG);
            }
        }catch (Exception ex){
            addressRes.setResCode(SysResCodeCons.RES_CODE_ADDRESS_DEFAULT_FAIL);
            addressRes.setResMsg(ex.getMessage());
        }
        return addressRes;
    }
    /**
     * 判断参数是否合法
     * @author hanzengzhi
     * @date 2016/5/19 17:57
     */
    private Boolean isValidateParam(ComUserAddressReq addressReq,ComUserAddress address){
        if (addressReq.getId()==null&&addressReq.getOperateType().equals("edit")){
            return false;
        }
        if (addressReq.getUserId()==null){
            return false;
        }
        if (StringUtils.isEmpty(addressReq.getName())){
            return false;
        }
        if (StringUtils.isEmpty(addressReq.getZip())){
            return false;
        }
        if (addressReq.getProvinceId()==null){
            return false;
        }
        if (StringUtils.isEmpty(addressReq.getProvinceName())){
            return false;
        }
        if (addressReq.getCityId()==null){
            return false;
        }
        if (StringUtils.isEmpty(addressReq.getCityName())){
            return false;
        }
        if (addressReq.getRegionId()==null){
            return false;
        }
        if (StringUtils.isEmpty(addressReq.getRegionName())){
            return false;
        }
        if (StringUtils.isEmpty(addressReq.getAddress())){
            return false;
        }
        if (StringUtils.isEmpty(addressReq.getMobile())){
            return false;
        }
        addressReq.setCreateTime(new Date());
        cloneAddressReqToAddress(addressReq,address);
        return true;
    }
    /**
     * address接口参数转化为address实体类
     * @author hanzengzhi
     * @date 2016/5/19 17:57
     */
    private ComUserAddress cloneAddressReqToAddress(ComUserAddressReq addressReq,ComUserAddress address){
        address.setId(addressReq.getId());
        address.setUserId(addressReq.getUserId());
        address.setName(addressReq.getName());
        address.setZip(addressReq.getZip());
        address.setProvinceId(addressReq.getProvinceId());
        address.setProvinceName(addressReq.getProvinceName());
        address.setCityId(addressReq.getCityId());
        address.setCityName(addressReq.getCityName());
        address.setRegionId(addressReq.getRegionId());
        address.setRegionName(addressReq.getRegionName());
        address.setAddress(addressReq.getAddress());
        address.setMobile(addressReq.getMobile());
        address.setCreateTime(addressReq.getCreateTime());
        if (addressReq.getIsDefault()!=null){
            address.setIsDefault(addressReq.getIsDefault());
        }else{
            address.setIsDefault(0);
        }
        return address;
    }
}
