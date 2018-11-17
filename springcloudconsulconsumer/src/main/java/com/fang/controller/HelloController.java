package com.fang.controller;

import com.fang.Impl.HelloRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: fwj
 * @Description:
 * @Date: Created in 2018/10/13 11:26
 * @Modified by:
 */
@RestController
public class HelloController {
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private HelloRemote helloRemote;

    @RequestMapping("/call")
    public String call() {
        ServiceInstance service = loadBalancerClient.choose("producer-service");
        System.out.println("服务地址：" + service.getUri());
        System.out.println("服务名称：" + service.getServiceId());
        String result = new RestTemplate().getForObject(service.getUri().toString() + "/hello", String.class);
        return result;
    }

    @RequestMapping("/call2")
    public  String call2() {
        return helloRemote.hello2();
    }
}
