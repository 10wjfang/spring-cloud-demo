package com.fang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: fwj
 * @Description:
 * @Date: Created in 2018/8/21 11:03
 * @Modified by:
 */
@RestController
public class HelloController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable(name = "name") String name) {
        return restTemplate.getForObject("http://SPRING-CLOUD-PRODUCER/hello?name="+name, String.class);
    }
}
