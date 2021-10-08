package org.wayne.base.controller.stock;

import org.wayne.base.entity.MyFocus;
import org.wayne.base.mapper.MyFocusMapper;
import org.springframework.web.bind.annotation.*;
import org.wayne.api.entity.RespBeanQ;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @author: LinWeiQi
 */
@RequestMapping("/stock/focus")
@RestController
public class FocusController {

    @Resource
    MyFocusMapper myFocusMapper;

    @GetMapping("/")
    public List<MyFocus> getList() {

        return myFocusMapper.queryAll();
    }

    @PutMapping("/")
    public RespBeanQ update(@RequestBody MyFocus myFocus) {

        int res = myFocusMapper.updateById(myFocus);

        return res == 1 ? RespBeanQ.ok() : RespBeanQ.error();
    }

    @PostMapping("/")
    public RespBeanQ add(@RequestBody MyFocus myFocus) {
        int res = myFocusMapper.insert(myFocus);
        return res == 1 ? RespBeanQ.ok() : RespBeanQ.error();
    }

    @DeleteMapping("/{id}")
    public RespBeanQ del(@PathVariable int id) {
        int res = myFocusMapper.deleteById(id);
        return res == 1 ? RespBeanQ.ok() : RespBeanQ.error();
    }
}
