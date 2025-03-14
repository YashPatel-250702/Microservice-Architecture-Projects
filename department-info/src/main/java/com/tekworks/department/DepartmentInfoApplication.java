package com.tekworks.department;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;

@SpringBootApplication
public class DepartmentInfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DepartmentInfoApplication.class, args);
	}

}
