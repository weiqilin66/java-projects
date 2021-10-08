package org.wayne.swagger.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.wayne.api.entity.WindowPathVo;
import org.wayne.common.utils.PoiUtilQ;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: excel相关操作
 * @author: lwq
 */
@Api(tags = "excel操作")
@Slf4j
@RestController
@RequestMapping("/common/poi")
public class CommonPoiController {

    /**
     * 路径语法问题只能放json
     * @return
     */
    @ApiOperation(value = "excel生成建表语句",notes = "填写文件路径参数excelPath")
    @PostMapping("/excel2ddl/path")
    public String excel2ddl(@RequestBody WindowPathVo vo){
        String path = vo.getPath();
        if (StringUtils.isEmpty(path)) {
            path="C://Users//1//Desktop//帅奇的excel2mysql.xlsx";
        }
        log.info("文件路径:{}",path);
        if (!StringUtils.isEmpty(vo.getSheetName())) {
            return PoiUtilQ.excel2ddl(path,vo.getSheetName());
        }else {
            return PoiUtilQ.excel2ddl(path,0);
        }
    }
    /**
     * 文件上传方式 @bug
     * @return
     */
    @ApiOperation(value = "excel生成建表语句",notes = "文件上传方式")
    @PostMapping(value = "/excel2ddl/stream")
    public String excel2ddl(@RequestPart MultipartFile file){
        return PoiUtilQ.excel2ddl(file,null);
    }

}
