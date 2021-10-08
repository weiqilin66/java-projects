package org.wayne.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author LinWeiQi
 * @since 2020-03-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Goods implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String etlDate;

    private String etlTime;

    /**
     * 店铺名
     */
    private String shop;

    /**
     * 关键词
     */
    private String kw;

    /**
     * 宝贝
     */
    private String title;

    /**
     * 价格
     */
    private Float price;

    /**
     * 销量
     */
    private int sales;

    /**
     * 运费
     */
    private Float freight;

    private String comment;

    private String imgUrl;

    private String detailUrl;


}
