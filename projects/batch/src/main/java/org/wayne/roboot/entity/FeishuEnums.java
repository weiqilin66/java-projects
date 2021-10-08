package org.wayne.roboot.entity;

/**
 * @Description:
 * @author: lwq
 */
@SuppressWarnings("all")
public enum FeishuEnums {
    // msg_type字段举例 必传
    text ("text", "文本"),
    post ("post", "富文本"),
    image ("image", "图片");

    private String value;

    private String desc;

    FeishuEnums(String value, String desc) {
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
