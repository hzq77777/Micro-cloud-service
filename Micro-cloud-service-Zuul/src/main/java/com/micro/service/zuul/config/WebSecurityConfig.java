package com.micro.service.zuul.config;

import com.micro.service.zuul.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by Administrator on 2018/5/3.
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //完成自定义认证实体注入
    @Bean
    UserDetailsService userService() {
        return new UserService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/bower_components/**","/fonts/**","/i18n/**","/images/**",
                        "/javascript/**","/node_modules/**","/styles/**","/template/**","/login").permitAll()
                .antMatchers("/j_spring_security_check/*").permitAll()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()             //其他所有请求必须登陆后访问
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/").failureUrl("/login?error").permitAll()//登录界面，错误界面可以直接访问
                .permitAll()
                .and().rememberMe().tokenValiditySeconds(60 * 60 * 24 * 7);//开启cookie保存用户数据及设置cookie有效期
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService()).passwordEncoder(new BCryptPasswordEncoder()); //user Details Service验证
    }

}
