package com.bigdata.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import java.io.Serializable;

/**
 * 安全配置
 *
 * @author fwj
 * @date 2019-02-12 15:52
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;
    @Value("${auth.permit.patterns}")
    private String[] permitPatterns;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //禁用CSRF
        http.csrf().disable();
        http.authorizeRequests().antMatchers(permitPatterns).permitAll().anyRequest().authenticated().and()
                //禁用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // 解决authenticationManager 无法注入
        return super.authenticationManagerBean();
    }
}
