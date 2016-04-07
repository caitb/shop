package com.masiis.shop.web.mall.constants;

/**
 * Created by hzz on 2016/4/2.
 */
public enum  AuditStatusEnum {
    NOAUDIT("未认证", 0), AUDITING("审核中", 1), AUDITSUCCESS("已认证", 2), AUDITFAIL("已拒绝", 3);
    // 成员变量
    private String name;
    private int index;

    // 构造方法
    private AuditStatusEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }
    // 普通方法
    public static String getName(int index) {
        for (AuditStatusEnum c : AuditStatusEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    public static AuditStatusEnum getAuditStatusEnum(int index){
        for (AuditStatusEnum c : AuditStatusEnum.values()) {
            if (c.getIndex() == index) {
                return c;
            }
        }
        return null;
    }
    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}
