package com.fang.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: fwj
 * @Description:
 * @Date: Created in 2018/8/27 10:18
 * @Modified by:
 */
@RestController
@RefreshScope
public class HelloController {
    @Value("${fang.hello}")
    private String hello;

    @RequestMapping("/hello")
    public String sayHello() {
        return this.hello;
    }
}
