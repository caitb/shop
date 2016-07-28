package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.util.CCPRestSmsSDK;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.user.CountGroup;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.platform.product.*;
import com.masiis.shop.dao.platform.user.*;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * 团队统计
 * @author muchaofeng
 * @date 2016/6/7 10:59
 */

@Service
public class CountGroupService {
    @Resource
    private CountGroupMapper countGroupMapper;

    /**
     * 统计团队人数、销售额、订单总数
     * @author muchaofeng
     * @date 2016/6/7 11:06
     */
    public CountGroup countGroupInfo(String treeCode){
        return countGroupMapper.countGroup(treeCode);
    }

}
