package com.fang.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: fwj
 * @Description:
 * @Date: Created in 2018/8/27 9:44
 * @Modified by:
 */
@RestController
public class HelloController {
    @Value("${fang.hello}")
    private String hello;

    @GetMapping("/hello")
    public String sayHello() {
        return hello;
    }
}
