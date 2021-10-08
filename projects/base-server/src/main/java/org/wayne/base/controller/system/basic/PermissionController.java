package org.wayne.base.controller.system.basic;

import org.wayne.base.entity.Menu;
import org.wayne.base.entity.Role;
import org.wayne.base.mapper.MenuRoleMapper;
import org.wayne.base.mapper.RoleMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.wayne.api.entity.RespBeanQ;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @Description: 角色权限管理
 * @author: LinWeiQi
 */
@RestController
@RequestMapping("/system/basic/ps")
public class PermissionController {

    @Resource
    RoleMapper roleMapper;
    @Resource
    MenuRoleMapper menuRoleMapper;

    @GetMapping("/")
    public List<Role> getPermissions() {
        return roleMapper.getRoles();
    }

    @GetMapping("/menus")
    public List<Menu> getAllMenu() {

        return roleMapper.getAllMenu();
    }

    @GetMapping("/mid/{rid}")
    public List<Integer> getPermissionsId(@PathVariable int rid) {

        List<Integer> list = roleMapper.getPermissionsIdByRid(rid);
        return list;
    }

    // 修改根据前端获取的所有子树节点 删除rid所属mid 在插入选中的
    @PutMapping("/")
    @Transactional
    public RespBeanQ updateByDelAndIns(int rid, int[] mids) {
        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("rid", rid);
        menuRoleMapper.deleteByMap(columnMap);
        // 批量插入
        if (mids == null) {
            return RespBeanQ.ok("更新成功");
        }
        int res = menuRoleMapper.insertBatchId(mids, rid);
        if (res == mids.length) {
            return RespBeanQ.ok("更新成功");
        }
        return RespBeanQ.error();
    }

    @PostMapping("/")
    public RespBeanQ addRoles(@RequestBody Role role) {
        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());
        }
        int res = roleMapper.insert(role);
        if (res != 1) {
            return RespBeanQ.error();
        }
        return RespBeanQ.ok("添加成功");
    }

    @DeleteMapping("/{rid}")
    @Transactional
    public RespBeanQ del(@PathVariable int rid) {

        int res = roleMapper.deleteById(rid);

        if (res != 1) {
            return RespBeanQ.error("删除失败");
        }
        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("rid", rid);
        menuRoleMapper.deleteByMap(columnMap);

        return RespBeanQ.ok("删除成功!");
    }
}
