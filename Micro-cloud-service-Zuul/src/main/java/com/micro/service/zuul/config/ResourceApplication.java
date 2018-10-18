package com.micro.service.zuul.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;

/**
 * to use share jdbc to store token and authenticate token
 */
@Configuration
@EnableResourceServer
public class ResourceApplication extends ResourceServerConfigurerAdapter {

    // 方式一： 采用共享数据库进行token校验
    @Autowired
    public DataSource dataSource;

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated();
        //.antMatchers(HttpMethod.POST, "/foo").hasAuthority("FOO_WRITE");
        //you can implement it like this, but I show method invocation security on write
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        System.out.println("==========================Configuring ResourceServerSecurityConfigurer ");
        resources.resourceId("oauth2-resource").tokenStore(tokenStore());
    }

}
