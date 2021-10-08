package org.wayne.base.controller.system.hr;

import org.wayne.base.entity.Hr;
import org.wayne.base.entity.Role;
import org.wayne.base.mapper.HrMapper;
import org.wayne.base.mapper.HrRoleMapper;
import org.wayne.base.mapper.RoleMapper;
import org.wayne.base.utils.HrUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.wayne.api.entity.RespBeanQ;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @Description: 操作员管理
 * @author: LinWeiQi
 */
@RestController
@RequestMapping("/system/hr")
public class HrController {
    @Resource
    HrMapper hrMapper;
    @Resource
    RoleMapper roleMapper;
    @Resource
    HrRoleMapper hrRoleMapper;

    // 获取除自己之外的管理员
    @GetMapping("/")
    public List<Hr> getAllHrs() {
        return hrMapper.getAllHrs(HrUtils.getCurrentHr().getId());
    }

    // 状态修改
    @PutMapping("/")
    public RespBeanQ updateStatus(@RequestBody Hr hr) {
        int res = hrMapper.updateById(hr);
        if (res != 1) {
            return RespBeanQ.error();
        }
        if (hr.isEnabled()) {
            return RespBeanQ.ok("启用成功");
        }
        return RespBeanQ.ok("禁用成功");
    }

    // 获取所拥有角色
    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleMapper.getRoles();
    }

    // 更新所拥有的角色
    @PutMapping("/roles")
    @Transactional
    public RespBeanQ updateRoles(int hrid, int[] rids) {
        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("hrid", hrid);
        hrRoleMapper.deleteByMap(columnMap);
        int res = hrRoleMapper.addRoles(hrid, rids);
        if (res < 1) {
            return RespBeanQ.error();
        }
        return RespBeanQ.ok("更新角色成功");
    }

    // 删除用户
    @DeleteMapping("/{hrid}")
    public RespBeanQ del(@PathVariable int hrid) {
        Hr hr = new Hr();
        hr.setId(hrid);
        int res = hrMapper.deleteById(hr);
        HashMap<String, Object> columnMap = new HashMap<>();
        columnMap.put("hrid", hrid);
        hrRoleMapper.deleteByMap(columnMap);
        if (res != 1) {
            return RespBeanQ.error();
        }
        return RespBeanQ.ok("删除角色成功");
    }

}
