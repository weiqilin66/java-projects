package org.wayne.api.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * cards
 * @author 
 */
@Data
public class Card implements Serializable {
    private Integer cardId;

    /**
     * 平台
     */
    private String platform;

    /**
     * 卡名
     */
    private String cardName;

    private static final long serialVersionUID = 1L;
}