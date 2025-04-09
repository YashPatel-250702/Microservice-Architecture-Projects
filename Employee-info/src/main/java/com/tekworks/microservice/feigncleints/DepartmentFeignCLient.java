package com.tekworks.microservice.feigncleints;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties.FeignClientConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tekworks.microservice.model.Department;

@FeignClient(name = "DEPARTMENT-INFO", configuration = FeignClientConfiguration.class)
public interface DepartmentFeignCLient {

	@GetMapping("/department/getDepartmentById/{id}")
	Department getDepartmentById(@PathVariable Integer id);
}
