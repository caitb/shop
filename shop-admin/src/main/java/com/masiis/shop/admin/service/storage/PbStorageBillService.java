package com.masiis.shop.admin.service.storage;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.SysBeanUtils;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.storage.PbStorageBillItemMapper;
import com.masiis.shop.dao.platform.storage.PbStorageBillMapper;
import com.masiis.shop.dao.platform.storage.PbStorageBillOperationLogMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2016/7/19
 * @Author lzh
 */
@Service
public class PbStorageBillService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private PbStorageBillMapper pbStorageBillMapper;
    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private PfUserSkuMapper pfUserSkuMapper;
    @Resource
    private ComSkuMapper comSkuMapper;
    @Resource
    private PbStorageBillItemMapper pbStorageBillItemMapper;
    @Resource
    private PbStorageBillOperationLogMapper billOpLogMapper;


    /**
     * 库存变更单列表
     * @param pageNo
     * @param pageSize
     * @param sortName
     * @param sortOrder
     * @param conditionMap
     * @return
     */
    public Map<String, Object> storagechangeList(Integer pageNo, Integer pageSize, String sortName, String sortOrder, Map<String, Object> conditionMap){
        /*String sort = "pb.create_time desc";
        if (sortName != null) sort = sortName + " " + sortOrder;
        PageHelper.startPage(pageNo, pageSize, sort);*/

        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> bOrderMaps = pbStorageBillMapper.storagechangeList(conditionMap);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(bOrderMaps);
        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", bOrderMaps);
        return pageMap;
    }

    public List<Map<String, Object>> getStorageItemDetailList(Long storageId){
        return pbStorageBillMapper.getStorageItemDetailList(storageId);
    }

    public PbStorageBill createBillByUserAndTypeAndSkusAndReason(Long userId, Integer billType,
                                                                 Integer[] skuIds, String reason,
                                                                 Integer[] nums, String[] remarks,
                                                                 PbUser pbUser) {
        PbStorageBill bill = null;
        try {
            ComUser user = comUserMapper.selectByPrimaryKey(userId);
            if(user == null || user.getIsAgent().intValue() == 0){
                log.error("变更人查找不到或不是合伙人");
                throw new BusinessException("变更人查找不到或不是合伙人");
            }
            List<PfUserSku> pfUserSkus = pfUserSkuMapper.selectByUserId(userId);

            for(Integer skuId:skuIds){
                if(!querySkuIsInPfUserSkuList(skuId, pfUserSkus)){
                    log.error("有选择的商品不是该变更人的代理商品,skuId:" + skuId);
                    throw new BusinessException("有选择的商品不是该变更人的代理商品");
                }
            }

            Integer totalNums = 0;
            for(Integer num:nums){
                if(num != null) {
                    totalNums += num;
                }
            }

            bill = createStorageBill(userId, billType, pbUser, reason, totalNums);
            // 插入变更单
            pbStorageBillMapper.insert(bill);

            for(int i = 0; i < skuIds.length; i++){
                ComSku sku = comSkuMapper.selectById(skuIds[i]);
                PbStorageBillItem item = createStorageBillItem(sku, remarks[i], nums[i], bill.getId());
                pbStorageBillItemMapper.insert(item);
            }

            // 增加库存变更单操作日志
            PbStorageBillOperationLog blog = new PbStorageBillOperationLog();
            blog.setCreateTime(new Date());
            blog.setHandleMan(pbUser.getId());
            blog.setHandleType(1);
            blog.setPbStorageBillId(bill.getId());
            blog.setPrevStatus(0);
            blog.setNextStatus(0);
            blog.setRemark("创建库存入库变更单");
            billOpLogMapper.insert(blog);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }

        return bill;
    }

    private PbStorageBillItem createStorageBillItem(ComSku sku, String remark, Integer num, Integer billId){
        PbStorageBillItem item = new PbStorageBillItem();

        item.setCreateTime(new Date());
        item.setSkuId(sku.getId());
        item.setSkuName(sku.getName());
        item.setRemark(remark);
        item.setQuantity(num);
        item.setPbStorageBillId(billId);

        return item;
    }


    private PbStorageBill createStorageBill(Long userId, Integer billType, PbUser pbUser, String reason, Integer totalNums){
        PbStorageBill bill = new PbStorageBill();

        bill.setUserId(userId);
        bill.setType(billType);
        bill.setCreateTime(new Date());
        bill.setCreateMan(pbUser.getId());
        bill.setBillReason(reason);
        bill.setProductQuantity(totalNums);
        bill.setStatus(0);
        bill.setCode(SysBeanUtils.createStorageBillCode());

        return bill;
    }

    private boolean querySkuIsInPfUserSkuList(Integer skuId, List<PfUserSku> list){
        if(list == null || list.size() <= 0){
            return false;
        }
        for(PfUserSku pfUserSku:list){
            if(pfUserSku.getSkuId().intValue() == skuId.intValue()){
                return true;
            }
        }

        return false;
    }
}
