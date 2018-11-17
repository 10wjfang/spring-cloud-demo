package com.fang.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: fwj
 * @Description:
 * @Date: Created in 2018/11/9 10:37
 * @Modified by:
 */
@RestController
public class WebContoller {
    @RequestMapping("/say")
    public String say(@RequestParam("name") String name) {
        return "Hello, " + name;
    }
}
