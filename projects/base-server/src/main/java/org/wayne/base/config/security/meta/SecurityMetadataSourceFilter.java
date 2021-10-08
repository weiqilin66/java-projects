package org.wayne.base.config.security.meta;

import org.wayne.base.entity.Menu;
import org.wayne.base.entity.Role;
import org.wayne.base.service.MenuService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 控制角色访问权限
 * 根据用户请求的url 分析出需要的role
 * @author: LinWeiQi
 */
@Component
public class SecurityMetadataSourceFilter implements FilterInvocationSecurityMetadataSource {

    @Resource
    MenuService menuService;
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        // 请求的路径
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        List<Menu> menus = menuService.getMenuWithRoles();
        for (Menu menu : menus) {
            if (antPathMatcher.match(menu.getUrl(), requestUrl)) {// 如果匹配上menu中的路径,该路径是需要权限的
                // 获取该权限的roles
                List<Role> roles = menu.getRoles();
                String[] roleNames = new String[roles.size()];
                for (int i = 0; i < roles.size(); i++) {
                    roleNames[i] = roles.get(i).getName();
                }
                return SecurityConfig.createList(roleNames);
            }
        }
        // 没匹配到菜单中的权限url,其余路径登录了就能访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    // 返回true
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
