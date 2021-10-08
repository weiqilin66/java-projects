package org.wayne.base.controller.system.basic;

import org.wayne.base.entity.Department;
import org.wayne.base.mapper.DepartmentMapper;
import org.springframework.web.bind.annotation.*;
import org.wayne.api.entity.RespBeanQ;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @author: LinWeiQi
 */
@RestController
@RequestMapping("/system/basic/dep")
public class DepController {

    @Resource
    DepartmentMapper departmentMapper;

    /**
     * @param -1 最上级的id
     * @Description 根据父ID递归 获取所有部门
     * @date 2020/2/13
     */
    @GetMapping("/")
    public List<Department> getAllDepByParentId() {
        return departmentMapper.getAllDepByParentId(-1);
    }

    /**
     * @Description 调用存储过程批量执行sql
     * 优点 : 当分开部署时 数据库一个服务器,应用多次调用sql 存在网络问题
     * 缺点 : 数据库耦合 不好做数据库迁移
     * @date 2020/2/13
     */
    @PostMapping("/")
    public RespBeanQ addDepByProcess(@RequestBody Department dep) {
        dep.setEnabled(true);
        departmentMapper.addDepByProcess(dep);
        if (dep.getResult() == 1) {

            return RespBeanQ.ok("添加成功", dep);
        }
        return RespBeanQ.error("添加失败");
    }

    @DeleteMapping("/{id}")
    public RespBeanQ delByProcess(@PathVariable int id) {
        // 存储过程out的值存在实体中
        Department dep = new Department();
        dep.setId(id);
        departmentMapper.delByProcess(dep);
        if (dep.getResult() == -2) {
            return RespBeanQ.error("该部门下有子部门删除失败");
        } else if (dep.getResult() == -1) {
            return RespBeanQ.error("该部门下有员工删除失败!");
        } else if (dep.getResult() == 1) {
            return RespBeanQ.ok("删除成功");
        }
        return RespBeanQ.error();// 由全局异常捕捉返回失败信息
    }
}
