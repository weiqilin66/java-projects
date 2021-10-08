package org.wayne.base.service;

import cn.hutool.log.StaticLog;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.wayne.base.entity.Hr;
import org.wayne.base.entity.Menu;
import org.wayne.base.mapper.MenuMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * 1. SecurityContextHolder中取出登录的用户ID
 * 2. 缓存问题
 *
 * @author: LinWeiQi
 */
@Service
public class MenuService {

    @Resource
    MenuMapper menuMapper;

    public List<Menu> getMenuById() {
        // SecurityContextHolder中取出登录的用户ID
        return menuMapper.getMenuById(((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    }

    // 每次拦截请求都要访问一次数据库开销太大,加入缓存,查询出所有的url权限的所有role,再去做role匹配
    @Cacheable(cacheNames = "c3") // key是形参 value为参数值,当参数值不变时,使用缓存,不重新查询
    public List<Menu> getMenuWithRoles() {
        return menuMapper.getMenuWithRoles();
    }

    /**
     * @Description 缓存的删除和更新
     * @date 2020/1/31
     */

    @CacheEvict(cacheNames = "c1")  // 删除的时候也删除查询里面相关的缓存,确保加了缓存的查询,不会查询出已被删除的数据 cacheNames来分组不同的增删改查
    public void deleteMenu(int id) {
        StaticLog.info("删除menu缓存成功");
    }
    @CachePut(cacheNames = "c1", key="#menu.id") // 更新的时候也更新缓存中的数据 key参数可以指定cache的key,查询和删除的也是以key为准
    public void updateMenu(Menu menu) {

    }

}
