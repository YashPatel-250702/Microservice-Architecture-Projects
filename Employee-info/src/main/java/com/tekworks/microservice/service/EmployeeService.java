package com.tekworks.microservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tekworks.microservice.entity.Employee;
import com.tekworks.microservice.exception.NoEmployeeFoundException;
import com.tekworks.microservice.feigncleints.DepartmentFeignCLient;
import com.tekworks.microservice.model.Department;
import com.tekworks.microservice.repository.EmployeeRepository;
import com.tekworks.microservice.response.EmployeeResponse;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	
	
	@Autowired 
	private EmployeeResponse employeeResponse;
	
	  @Autowired
	  private DepartmentFeignCLient departmentFeignCLient;
	
	@Transactional(rollbackForClassName = "java.lang.Exception")
	public Employee saveEmployee(Employee employee) {
		
	
			Department departmentById = departmentFeignCLient.getDepartmentById(employee.getDeptId());
	
		
		return employeeRepository.save(employee);
	}
	
	public EmployeeResponse getEmployeeById(Integer id) throws NoEmployeeFoundException {
		Optional<Employee> employee = employeeRepository.findById(id);
		
		if(employee.isPresent()) {
			
			
			 Employee employee2 = employee.get();
			 
				//Department department = restTemplate.getForObject("http://DEPARTMENT-INFO/department/getDepartmentById/"+employee2.getDeptId(), Department.class);
				Department departmentById = departmentFeignCLient.getDepartmentById(id);
				System.out.println("Departmnet data is "+departmentById);
		        employeeResponse.setEmp(employee2);
		        employeeResponse.setDept(departmentById);
		        return employeeResponse;
		}
		
		throw new NoEmployeeFoundException("No Employee Found With id: "+id);
	}
	
	
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	
	@Transactional(rollbackForClassName = "java.lang.Exception")
	public void deleteEmployeeById(Integer id) {
		employeeRepository.deleteById(id);
	}

}
