package com.fang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author: fwj
 * @Description:
 * @Date: Created in 2018/5/7 16:26
 * @Modified by:
 */
@SpringBootApplication
@EnableEurekaServer
public class SpringCloudEurekaApplication {
    public static void main(String[] args){
        SpringApplication.run(SpringCloudEurekaApplication.class, args);
    }
}
