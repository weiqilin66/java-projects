package org.wayne.api.entity;

/**
 * @Description 枚举类示例
 */
public enum EnumExample {
    SUCCESS("01", "成功"), FAIL("02", "失败"), IN_HANDLE("03", "处理中"), YET_GWHG("04", "已高危回购结清"), YET_SPHG("05", "已SP回购结清"), YESTERDAY_SUCCESS("06", "非当日扣款成功"), YET_DK_AQHK("07", "已代客还月供");

    /**
     * 值
     */
    private String value;

    /**
     * 描述
     */
    private String desc;

    EnumExample(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
