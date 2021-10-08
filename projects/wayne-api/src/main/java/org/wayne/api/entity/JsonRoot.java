package org.wayne.api.entity;

import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @author: lwq
 */
@Data
public class JsonRoot {
    private String postFeeText;

    private String trace;

    private List<Auctions> auctions;

    private List<String> recommendAuctions;

    private boolean isSameStyleView;

    private List<String> sellers;

    private String query;

    private String spmModId;
}
