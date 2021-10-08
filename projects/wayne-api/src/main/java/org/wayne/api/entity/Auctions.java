package org.wayne.api.entity;

import lombok.Data;

/**
 * @Description:
 * @author: lwq
 */
@Data
@SuppressWarnings("all")
public class Auctions {
    private int p4p;

    private boolean p4pSameHeight;

    private String nid;

    private String category;

    private String pid;

    private String title;

    private String raw_title;

    private String pic_url;

    private String detail_url;

    private String view_price;

    private String view_fee;

    private String item_loc;

    private String view_sales;

    private String comment_count;

    private String user_id;

    private String nick;

    private boolean isHideIM;

    private boolean isHideNick;

    private String comment_url;

    private String shopLink;
}
