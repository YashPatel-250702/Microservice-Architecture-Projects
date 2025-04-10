package com.tekworks.microservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tekworks.microservice.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	List<Employee> findByDeptId(Integer deptId);
}
