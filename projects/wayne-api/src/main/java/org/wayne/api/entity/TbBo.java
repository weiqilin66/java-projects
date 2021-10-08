package org.wayne.api.entity;

import lombok.Data;

/**
 * @Description:
 * @author: lwq
 */
@Data
@SuppressWarnings("all")
public class TbBo {
    // 店铺名
    private String nick;
    // 标题
    private String raw_title;
    // 价格
    private String view_price;
    // 销量
    private String view_sales;
    // 运费
    private String view_fee;
    // 详情url
    private String detail_url;
    // 图片url
    private String pic_url;


}
