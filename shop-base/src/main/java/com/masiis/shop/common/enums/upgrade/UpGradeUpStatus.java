package com.masiis.shop.common.enums.upgrade;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2016/6/16
 * @Author lzh
 */
public enum UpGradeUpStatus {
    /**
     * 上级未处理
     */
    UP_STATUS_Untreated {
        public Integer getCode() {
            return 0;
        }

        public String getMessage() {
            return "未处理";
        }
    },
    /**
     * 上级暂不升级
     */
    UP_STATUS_NotUpgrade {
        public Integer getCode() {
            return 1;
        }

        public String getMessage() {
            return "暂不升级";
        }
    },
    /**
     * 我要升级
     */
    UP_STATUS_Upgrade {
        public Integer getCode() {
            return 2;
        }

        public String getMessage() {
            return "我要升级";
        }
    },
    /**
     * 我要升级
     */
    UP_STATUS_Complete {
        public Integer getCode() {
            return 3;
        }

        public String getMessage() {
            return "处理完成";
        }
    };

    public abstract Integer getCode();

    public abstract String getMessage();


    public static final Map<Integer,String> upStatusPickList = new HashMap<>();

    static {
        for (UpGradeStatus s : EnumSet.allOf(UpGradeStatus.class)){
            upStatusPickList.put(s.getCode(),s.getMessage());
        }
    }

}
