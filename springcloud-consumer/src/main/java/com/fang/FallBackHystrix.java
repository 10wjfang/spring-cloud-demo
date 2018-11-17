package com.fang;

import org.springframework.stereotype.Component;

/**
 * @Author: fwj
 * @Description:
 * @Date: Created in 2018/11/9 11:12
 * @Modified by:
 */
@Component
public class FallBackHystrix implements RemoteController {
    @Override
    public String say2(String name) {
        return "service is down";
    }
}
