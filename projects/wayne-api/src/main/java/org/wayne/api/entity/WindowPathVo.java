package org.wayne.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @author: lwq
 */
@Data
@ApiModel(description = "测试对象模型")
public class WindowPathVo {
    @ApiModelProperty(value="excel路径" ,required=true)
    private String path;
    @ApiModelProperty(value="sheet名" ,required=false)
    private String sheetName;
    @ApiModelProperty(value="sheet位置" ,required=false)
    private int sheetNum;


}
