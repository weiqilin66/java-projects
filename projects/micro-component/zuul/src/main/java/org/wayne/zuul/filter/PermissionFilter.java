package org.wayne.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Description: zuul过滤器
 * @author: LinWeiQi
 */
@Component
public class PermissionFilter extends ZuulFilter {
    /**
     * 过滤器类型
     * pre 前置
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    //顺序,多个过滤器 越小越大
    @Override
    public int filterOrder() {
        return 0;
    }

    //是否过滤
    @Override
    public boolean shouldFilter() {
//        return false;
        //举例过滤post请求
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String methodType = RequestMethod.OPTIONS.name();
        if (methodType.equals(request.getMethod())) {
            //...
            return false;
        }
        return true;//全部过滤
    }

    //过滤的操作
    @Override
    public Object run() throws ZuulException {
        //获取请求信息
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        //权限验证
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //------------token示例
        Map<String, String> headers = ctx.getZuulRequestHeaders();
        String userToken = headers.get("userToken");
        System.out.println("userToken:"+userToken);
        // 设置
        HttpServletResponse response = ctx.getResponse();
        response.setHeader("content-type","text/html;charset=utf-8");
        //---放行
        ctx.setSendZuulResponse(true);

        return null;
    }
}
