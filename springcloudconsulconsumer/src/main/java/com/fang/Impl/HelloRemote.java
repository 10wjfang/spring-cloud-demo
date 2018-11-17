package com.fang.Impl;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: fwj
 * @Description:
 * @Date: Created in 2018/10/13 11:34
 * @Modified by:
 */
@FeignClient(name = "producer-service")
public interface HelloRemote {
    @RequestMapping("/hello")
    public String hello2();
}
