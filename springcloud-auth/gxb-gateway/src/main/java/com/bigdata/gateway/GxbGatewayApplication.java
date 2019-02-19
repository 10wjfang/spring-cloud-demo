package com.bigdata.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 网关启动类
 *
 * @author fwj
 * @date 2018-11-19 15:08
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class GxbGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GxbGatewayApplication.class, args);
	}

}

