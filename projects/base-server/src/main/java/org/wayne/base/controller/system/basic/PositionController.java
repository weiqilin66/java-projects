package org.wayne.base.controller.system.basic;

import org.wayne.base.entity.Position;
import org.wayne.base.mapper.PositionMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;
import org.wayne.api.entity.RespBeanQ;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: rest风格接口 get del都是url传参 post put 是传递的JSON
 * @author: LinWeiQi
 */
@RestController
@RequestMapping("/system/basic/pos")
public class PositionController {

    @Resource
    PositionMapper positionMapper;

    @GetMapping("/")
    public List<Position> queryAll() {
        return positionMapper.queryAll();
    }

    @PostMapping("/")
    public RespBeanQ add(@RequestBody Position position) {
        position.setEnabled(true);
        position.setCreateDate(new Date());
        if (positionMapper.insert(position) != 1) {
            return RespBeanQ.error("添加失败");
        }
        return RespBeanQ.ok("添加成功");
    }

    @PutMapping("/")
    public RespBeanQ update(@RequestBody Position position) {
        int res = positionMapper.updateById(position);
        if (res != 1) {
            return RespBeanQ.error("更新失败");
        }
        return RespBeanQ.ok("更新成功");
    }

    @DeleteMapping("/{id}")
    public RespBeanQ del(@PathVariable int id) {
        int res = positionMapper.deleteById(id);
        if (res != 1) {
            return RespBeanQ.error("删除失败");
        }
        return RespBeanQ.ok("删除成功");
    }

    /**
     * @param [ids] 传入的id数组
     * @Description 批量删除
     * @date 2020/2/4
     */
    @DeleteMapping("/")
    public RespBeanQ delByIds(@Param("ids") int[] ids) {
        List<Integer> idsList = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            idsList.add(ids[i]);
        }
        int res = positionMapper.deleteBatchIds(idsList);
        if (res != idsList.size()) {
            return RespBeanQ.error();
        }
        return RespBeanQ.ok("批量删除成功");
    }
}
