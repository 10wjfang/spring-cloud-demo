package com.fang.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: fwj
 * @Description:
 * @Date: Created in 2018/11/5 11:27
 * @Modified by:
 */

@FeignClient(name = "spring-cloud-producer", fallback = HelloRemoteHystrix.class)
public interface HelloRemote {
    @GetMapping("/hello")
    String hello();
}
