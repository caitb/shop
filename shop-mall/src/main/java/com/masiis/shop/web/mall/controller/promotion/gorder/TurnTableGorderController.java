package com.masiis.shop.web.mall.controller.promotion.gorder;

import com.masiis.shop.web.promotion.cpromotion.service.gorder.TurnTableGorderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by hzz on 2016/8/2.
 */
@Controller
@RequestMapping("/turnTableGorder")
public class TurnTableGorderController {

    @Resource
    private TurnTableGorderService turnTableGorderService;
}
