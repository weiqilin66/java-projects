package org.wayne.base.config.security.meta;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @Description: 根据SecurityMetadataSourceFilter返回的角色, 判断当前用户是否有权限
 * @author: LinWeiQi
 */
@Component
public class CustomUrlDecisionManager implements AccessDecisionManager {
    /**
     * @params  Authentication:存储登录信息  Collection<ConfigAttribute>:MyFilter返回的collection]
     * @date 2020/1/31
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : collection) {

            String needRole = configAttribute.getAttribute();
            if ("ROLE_LOGIN".equals(needRole)) {
                // 未登录
                if (authentication instanceof AnonymousAuthenticationToken) {
                    throw new AccessDeniedException("请先登录");
                } else {
                    // 已登录且菜单所需角色是LOGIN
                    return;
                }
            }
            // 当前登录用户具备的roles
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            // 策略-> 具备的角色和其中所需角色之一匹配即可
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(needRole)) {
                    return;
                }
            }
        }
        // 不是LOGIN role也匹配不上
        throw new AccessDeniedException("非法请求!");
        // 如果未登录 抛完异常不会返回前台,而是 .loginPage("/login") 尝试访问/login 又会进入SecurityMetaDataResourceFilter
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
