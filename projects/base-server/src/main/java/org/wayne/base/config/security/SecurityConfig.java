package org.wayne.base.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.wayne.base.config.security.meta.CustomUrlDecisionManager;
import org.wayne.base.config.security.meta.SecurityMetadataSourceFilter;
import org.wayne.base.entity.Hr;
import org.wayne.base.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.wayne.api.entity.RespBeanQ;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description: SpringSecurity配置3个configure(..)
 * @author: lwq
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;
    @Autowired
    SecurityMetadataSourceFilter securityMetadataSourceFilter;
    @Autowired
    CustomUrlDecisionManager customUrlDecisionManager;

    //密码策略
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 角色继承 让admin拥有所有user的权限
     */
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return hierarchy;
    }
    // 用户策略->数据库
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
        /*auth.inMemoryAuthentication()
                .withUser("javaboy.org")
                .password("123").roles("admin").and().withUser(...;*///and 符号相当于就是 XML 标签的结束符
    }

    // http配置
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //.anyRequest().authenticated()  //所有请求

                // 路径权限配置
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(customUrlDecisionManager);
                        o.setSecurityMetadataSource(securityMetadataSourceFilter);
                        return o;
                    }
                })
                .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/api/doLogin") // 处理登录请求的url
                .loginPage("/api/login") // 如果未登录尝试访问其他url都会跳转到此路径 authenticationEntryPoint配置后失效
                //登录成功处理器
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        Hr hr = (Hr) authentication.getPrincipal();
                        hr.setPassword(null);
                        RespBeanQ ok = RespBeanQ.ok("登录成功", hr);
                        String s = new ObjectMapper().writeValueAsString(ok);
                        out.write(s);
                        out.flush();
                        out.close();
                    }
                })
                // 登录失败处理器
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        RespBeanQ error = RespBeanQ.error("登录失败");
                        if (e instanceof LockedException) {
                            error.setMessage("账户被锁定,请联系管理员");
                        } else if (e instanceof CredentialsExpiredException) {
                            error.setMessage("密码过期,请联系管理员");
                        } else if (e instanceof AccountExpiredException) {
                            error.setMessage("账户过期,请联系管理员");
                        } else if (e instanceof DisabledException) {
                            error.setMessage("账户被禁用,请联系管理员");
                        } else if (e instanceof BadCredentialsException) {
                            error.setMessage("用户名或密码错误,请重新输入");
                        }

                        String s = new ObjectMapper().writeValueAsString(error);
                        out.write(s);
                        out.flush();
                        out.close();

                    }
                })
                //除此 都提交
                .permitAll()
                .and()
                .logout()//默认注销处理路径"/logout"
                // 注销处理器
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        out.write(new ObjectMapper().writeValueAsString(RespBeanQ.ok("注销成功")));
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and()
                // postman测试关闭csrf
                .csrf().disable()
                .exceptionHandling()
                // 未登录认证时, 前端会收到跳转到localhost:8081/login的resp,导致跨域问题
                // postman测试不出该问题,因为是直接访问的后端接口 端口就是8081所以不存在跨域,前端8080收到后端的跳转时没经过node代理
                // 自定义,未认证的返回方式
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        RespBeanQ error = RespBeanQ.error("登录过期或未登录,请先登录!");
                        error.setStatus(401);
                        out.write(new ObjectMapper().writeValueAsString(error));
                        out.flush();
                        out.close();
                    }
                });
    }

    // SecurityMetadataSourceFilter会拦截所有请求除了/doLogin 第一次拦截完会跳转到/login,login会被拦截,之后又跳转到/login死循环
    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/login","/js/**", "/css/**","/images/**","/image/**","/photo/**"
                ,"/swagger-ui/**","/sagger-resources/**","/v2/api-docs","**/index.html","/swagger-ui.html"
        );
    }
}
