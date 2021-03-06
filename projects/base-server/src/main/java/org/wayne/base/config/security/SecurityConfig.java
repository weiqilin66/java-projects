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
 * @Description: SpringSecurity??????3???configure(..)
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

    //????????????
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * ???????????? ???admin????????????user?????????
     */
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return hierarchy;
    }
    // ????????????->?????????
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
        /*auth.inMemoryAuthentication()
                .withUser("javaboy.org")
                .password("123").roles("admin").and().withUser(...;*///and ????????????????????? XML ??????????????????
    }

    // http??????
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //.anyRequest().authenticated()  //????????????

                // ??????????????????
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
                .loginProcessingUrl("/api/doLogin") // ?????????????????????url
                .loginPage("/api/login") // ?????????????????????????????????url???????????????????????? authenticationEntryPoint???????????????
                //?????????????????????
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        Hr hr = (Hr) authentication.getPrincipal();
                        hr.setPassword(null);
                        RespBeanQ ok = RespBeanQ.ok("????????????", hr);
                        String s = new ObjectMapper().writeValueAsString(ok);
                        out.write(s);
                        out.flush();
                        out.close();
                    }
                })
                // ?????????????????????
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        RespBeanQ error = RespBeanQ.error("????????????");
                        if (e instanceof LockedException) {
                            error.setMessage("???????????????,??????????????????");
                        } else if (e instanceof CredentialsExpiredException) {
                            error.setMessage("????????????,??????????????????");
                        } else if (e instanceof AccountExpiredException) {
                            error.setMessage("????????????,??????????????????");
                        } else if (e instanceof DisabledException) {
                            error.setMessage("???????????????,??????????????????");
                        } else if (e instanceof BadCredentialsException) {
                            error.setMessage("????????????????????????,???????????????");
                        }

                        String s = new ObjectMapper().writeValueAsString(error);
                        out.write(s);
                        out.flush();
                        out.close();

                    }
                })
                //?????? ?????????
                .permitAll()
                .and()
                .logout()//????????????????????????"/logout"
                // ???????????????
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        out.write(new ObjectMapper().writeValueAsString(RespBeanQ.ok("????????????")));
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and()
                // postman????????????csrf
                .csrf().disable()
                .exceptionHandling()
                // ??????????????????, ????????????????????????localhost:8081/login???resp,??????????????????
                // postman?????????????????????,???????????????????????????????????? ????????????8081?????????????????????,??????8080?????????????????????????????????node??????
                // ?????????,????????????????????????
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        RespBeanQ error = RespBeanQ.error("????????????????????????,????????????!");
                        error.setStatus(401);
                        out.write(new ObjectMapper().writeValueAsString(error));
                        out.flush();
                        out.close();
                    }
                });
    }

    // SecurityMetadataSourceFilter???????????????????????????/doLogin ??????????????????????????????/login,login????????????,??????????????????/login?????????
    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/login","/js/**", "/css/**","/images/**","/image/**","/photo/**"
                ,"/swagger-ui/**","/sagger-resources/**","/v2/api-docs","**/index.html","/swagger-ui.html"
        );
    }
}
