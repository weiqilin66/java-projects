package org.wayne.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author LinWeiQi
 * @since 2020-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Shop implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String switchUrl;

    private String ps4Url;

    private String ps5Url;

    private String xboxUrl;

    private String xbox2Url;

    private String switch2Url;

    /**
     * 1启用0禁用
     */
    private Integer enable;


}
