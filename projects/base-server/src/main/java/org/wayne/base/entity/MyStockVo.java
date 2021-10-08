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
 * @since 2020-06-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MyStockVo implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    private String title;

    private Float price;

    private Integer stock;

    private String comment;
    private String label;

    private String kw;

    private float price2;
    private float diff;


}
