package com.masiis.shop.web.mall.controller.promotion.guser;

import com.masiis.shop.dao.beans.promotion.TurnTablelInfo;
import com.masiis.shop.web.promotion.cpromotion.service.guser.TurnTableDetailShowService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hzzh on 2016/8/1.
 */
@Controller
@RequestMapping("/turnTableDetailShow")
public class TurnTableDetailShowController {

    private TurnTableDetailShowService turnTableDetailShowService;

    @RequestMapping("/getTurnTableInfo.html")
    public String getTurnTableInfo(HttpServletRequest request, Model model) {
        List<TurnTablelInfo> turnTablelInfos =  turnTableDetailShowService.getTurnTableInfo();
        return null;
    }
}
