package com.fang.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: fwj
 * @Description:
 * @Date: Created in 2018/11/5 11:20
 * @Modified by:
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "hello world!";
    }
}
