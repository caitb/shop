package com.masiis.shop.admin.controller.gift;

import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.service.gift.ComGiftService;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.dao.platform.gift.ComGiftMapper;
import com.masiis.shop.dao.po.ComGift;
import com.masiis.shop.dao.po.PbUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 奖品管理 Controller
 */
@Controller
@RequestMapping("/gift")
public class ComGiftController {

    @Resource
    private ComGiftService comGiftService;
    @Resource
    private ComGiftMapper comGiftMapper;

    @RequestMapping("list.shtml")
    public String list() {
        return "gift/listGift";
    }

    /**
     * 奖品分页查询
     * @param pageSize
     * @param pageNumber
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param name      奖品名称
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public Object list(Integer pageSize,Integer pageNumber,
                       String beginTime,
                       String endTime,
                       String name) {

        Map<String,Object> conditionMap = new HashMap<>();
        if(StringUtils.isNotEmpty(beginTime)) {
            conditionMap.put("beginTime", beginTime);
        }
        if(StringUtils.isNotEmpty(endTime)) {
            conditionMap.put("endTime", endTime);
        }
        if(StringUtils.isNotEmpty(name)) {
            conditionMap.put("name", name);
        }

        List<ComGift> giftList = comGiftService.listByCondition(pageNumber, pageSize, conditionMap);
        PageInfo<ComGift> giftPageInfo = new PageInfo<>(giftList);

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("total", giftPageInfo.getTotal());
        resultMap.put("rows", giftList);
        return resultMap;
    }

    @RequestMapping("/add.shtml")
    public String addPage(Model model) {
        model.addAttribute("imgBaseUrl", OSSObjectUtils.OSS_GIFT_URL);
        return "gift/addGift";
    }

    @RequestMapping("/edit.shtml")
    public String editPage(Integer id, Model model) {
        ComGift gift = comGiftMapper.selectByPrimaryKey(id);
        model.addAttribute("gift", gift);
        model.addAttribute("imgUrlLink", OSSObjectUtils.OSS_GIFT_URL+gift.getImgUrl());
        model.addAttribute("imgBaseUrl", OSSObjectUtils.OSS_GIFT_URL);
        return "gift/addGift";
    }

    /**
     * 奖品的保存和更新
     * @param comGift
     * @param session
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public Map<String,Object> saveOrUpdate(ComGift comGift, HttpSession session) {
        Map<String,Object> jsonData = new HashMap<>();
        jsonData.put("status", "success");

        try {
            Object obj = session.getAttribute("pbUser");
            PbUser pbUser = (PbUser) obj;
            ComGift returnComGift = comGiftService.saveOrUpdate(comGift, pbUser);
            jsonData.put("data", returnComGift);
        } catch (Exception e) {
            jsonData.put("status", "error");
            e.printStackTrace();
        }

        return jsonData;
    }

}
