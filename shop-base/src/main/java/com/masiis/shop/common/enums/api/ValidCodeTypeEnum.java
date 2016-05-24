package com.masiis.shop.common.enums.api;

/**
 * @Date 2016/5/24
 * @Auther lzh
 */
public enum ValidCodeTypeEnum {
    LOGIN_VCODE{
        public Integer getCode() {
            return 1;
        }
    },
    // 绑定手机号验证码类型
    BIND_VCODE{
        public Integer getCode() {
            return 2;
        }
    };

    public abstract Integer getCode();
    public static ValidCodeTypeEnum getByCode(Integer code){
        ValidCodeTypeEnum[] vs = ValidCodeTypeEnum.values();
        for(ValidCodeTypeEnum v:vs){
            if(v.getCode().intValue() == code.intValue()){
                return v;
            }
        }
        return null;
    }
}
