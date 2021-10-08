package org.wayne.roboot.entity;

import lombok.Data;
import lombok.Setter;

/**
 * @Description:
 * @author: lwq
 */
@Data
public class Content {
    private String text;

    public Content(String text) {
        this.text = text;
    }

    public Content() {
    }
}
