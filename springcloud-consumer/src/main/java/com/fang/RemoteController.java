package com.fang;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: fwj
 * @Description:
 * @Date: Created in 2018/11/9 10:43
 * @Modified by:
 */
@FeignClient(name = "producer-service", fallback = FallBackHystrix.class)
public interface RemoteController {
    @RequestMapping("/say")
    String say2(@RequestParam("name") String name);
}
