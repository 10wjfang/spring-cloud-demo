package com.fang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: fwj
 * @Description:
 * @Date: Created in 2018/11/9 10:46
 * @Modified by:
 */
@RestController
public class CallController {
    @Autowired
    RemoteController remoteController;

    @RequestMapping("/call")
    public String call(@RequestParam("name") String name) {
        return remoteController.say2(name);
    }
}
