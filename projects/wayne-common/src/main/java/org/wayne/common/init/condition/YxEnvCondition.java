package org.wayne.common.init.condition;

import cn.hutool.core.net.NetUtil;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @Description:
 * @author: lwq
 */

public class YxEnvCondition implements Condition {
    private final String yxHost = "172.16.101.46";

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        return yxHost.equals(NetUtil.getLocalhostStr());
    }
}
