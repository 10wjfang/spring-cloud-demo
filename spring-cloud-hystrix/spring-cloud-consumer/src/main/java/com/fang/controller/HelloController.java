package com.fang.controller;

import com.fang.remote.HelloRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: fwj
 * @Description:
 * @Date: Created in 2018/11/5 11:40
 * @Modified by:
 */
@RestController
public class HelloController {
    @Autowired
    private HelloRemote helloRemote;

    @GetMapping("/hi")
    public String hello() {
        return helloRemote.hello();
    }
}
