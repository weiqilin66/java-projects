package org.wayne.base.utils;

import org.wayne.base.entity.Hr;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Description:
 * @author: LinWeiQi
 */
public class HrUtils {
    /**
     * @Description 获取当前登录的用户
     * @date 2020/2/13
     */
    public static Hr getCurrentHr() {
        return ((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
