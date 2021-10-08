package org.wayne.base.controller.system.basic;

import org.wayne.base.entity.Joblevel;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.wayne.api.entity.RespBeanQ;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @author: LinWeiQi
 */
@RequestMapping("/system/basic/jl")
@RestController
public class JobLevelController {

    @Resource
    org.wayne.base.mapper.joblevelMapper joblevelMapper;

    @GetMapping("/")
    public List<Joblevel> getJobLevel() {

        return joblevelMapper.selectAll();
    }

    @PostMapping("/")
    @Transactional
    public RespBeanQ addJobLevel(@RequestBody Joblevel jl) {
        jl.setCreateDate(new Date());
        jl.setEnabled(true);
        int res = joblevelMapper.insert(jl);
        if (res != 1) {
            return RespBeanQ.error("添加失败");
        }
        return RespBeanQ.ok("添加成功");
    }

    @PutMapping("/")
    @Transactional
    public RespBeanQ updateJobLevel(@RequestBody Joblevel jl) {
        int res = joblevelMapper.updateById(jl);
        if (res != 1) {
            return RespBeanQ.error("修改失败");
        }
        return RespBeanQ.ok("修改成功");
    }

    @DeleteMapping("/{id}")
    @Transactional
    public RespBeanQ deleteById(@PathVariable int id) {
        int res = joblevelMapper.deleteById(id);
        if (res != 1) {
            return RespBeanQ.error("删除失败");

        }
        return RespBeanQ.ok("删除成功");
    }

    @DeleteMapping("/")
    public RespBeanQ batchDelById(@Param("ids") int[] ids) {
        ArrayList<Serializable> idList = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            idList.add(ids[i]);
        }
        joblevelMapper.deleteBatchIds(idList);
        return RespBeanQ.ok("批量删除成功");
    }
}
