package com.fang.remote;

import org.springframework.stereotype.Component;

/**
 * @Author: fwj
 * @Description:
 * @Date: Created in 2018/11/5 11:31
 * @Modified by:
 */
@Component
public class HelloRemoteHystrix implements HelloRemote {
    @Override
    public String hello() {
        return "hello, this message send failed";
    }
}
