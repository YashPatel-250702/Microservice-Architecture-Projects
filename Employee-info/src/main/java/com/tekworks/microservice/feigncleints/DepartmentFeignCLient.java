package com.tekworks.microservice.feigncleints;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tekworks.microservice.model.Department;

@FeignClient("DEPARTMENT-INFO")
public interface DepartmentFeignCLient {

	
	@GetMapping("/department/getDepartmentById/{id}")
	public  Department getDepartmentById(@PathVariable Integer id) ;
}
