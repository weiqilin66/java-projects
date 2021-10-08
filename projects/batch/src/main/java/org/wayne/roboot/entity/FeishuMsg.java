package org.wayne.roboot.entity;

import lombok.Data;

/**
 * @Description:
 * @author: lwq
 */
@Data
public class FeishuMsg {
    private String msg_type;
    private Content content;

    public FeishuMsg() {
    }

    public FeishuMsg(String msg_type, Content content) {
        this.msg_type = msg_type;
        this.content = content;
    }
}
