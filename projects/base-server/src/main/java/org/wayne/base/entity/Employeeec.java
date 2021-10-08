package org.wayne.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

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
 * @since 2020-01-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Employeeec implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 员工编号
     */
    private Integer eid;

    /**
     * 奖罚日期
     */
    @TableField("ecDate")
    private LocalDate ecDate;

    /**
     * 奖罚原因
     */
    @TableField("ecReason")
    private String ecReason;

    /**
     * 奖罚分
     */
    @TableField("ecPoint")
    private Integer ecPoint;

    /**
     * 奖罚类别，0：奖，1：罚
     */
    @TableField("ecType")
    private Integer ecType;

    /**
     * 备注
     */
    private String remark;


}
