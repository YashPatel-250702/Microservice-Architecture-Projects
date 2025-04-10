package com.tekworks.microservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tekworks.microservice.entity.Employee;
import com.tekworks.microservice.exception.NoEmployeeFoundException;
import com.tekworks.microservice.exception.ServiceUnavailableException;
import com.tekworks.microservice.feigncleints.AddressFiegnClient;
import com.tekworks.microservice.feigncleints.DepartmentFeignCLient;
import com.tekworks.microservice.model.Address;
import com.tekworks.microservice.model.Department;
import com.tekworks.microservice.repository.EmployeeRepository;
import com.tekworks.microservice.response.EmployeeResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private DepartmentFeignCLient departmentFeignCLient;

	@Autowired
	private AddressFiegnClient addressFiegnClient;

	@Transactional(rollbackForClassName = "java.lang.Exception")
	public Employee saveEmployee(Employee employee) throws Exception {
		try {
		
			try {
				Department department = departmentFeignCLient.getDepartmentById(employee.getDeptId());
			} catch (Exception e) {
				throw new Exception("Department not found with id: "+employee.getDeptId());
			}
			
			
			try {
				Address address = addressFiegnClient.getAddressById(employee.getAddressId());
			} catch (Exception e) {
				throw new Exception("Address not found with id: "+employee.getAddressId());
			}
			
			return employeeRepository.save(employee);
		} catch (Exception e) {
			 throw new Exception(e.getMessage());
		}
	}

	@RateLimiter(name = "userRateLimiter", fallbackMethod = "retryDepartmentAddressFallBack")
	@CircuitBreaker(name = "departmentAddressBreaker", fallbackMethod = "retryDepartmentAddressFallBack")
	@Retry(name = "departmentAddressRetry", fallbackMethod = "retryDepartmentAddressFallBack")
	public EmployeeResponse getEmployeeById(Integer id) throws NoEmployeeFoundException {

		Optional<Employee> employee = employeeRepository.findById(id);

		if (employee.isPresent()) {
			Employee employeeData = employee.get();

			// Fetching department using Feign Client
			Department department = departmentFeignCLient.getDepartmentById(employeeData.getDeptId());

			// Fetching address using deign client
			Address address = addressFiegnClient.getAddressById(employeeData.getAddressId());

			EmployeeResponse response = new EmployeeResponse();
			response.setEmp(employeeData);
			response.setDept(department);
			response.setAddress(address);
			return response;
		}
		throw new NoEmployeeFoundException("No Employee Found With id: " + id);

	}

	public EmployeeResponse retryDepartmentAddressFallBack(Integer id, Exception ex) {
		ex.printStackTrace();
		throw new ServiceUnavailableException("Temporary issue, please try again after some time.");
	}
	
	public List<EmployeeResponse> getEmployeeByDepartmnetId(Integer departmentId) throws Exception { 
		List<EmployeeResponse> empoyeesResponse=new ArrayList<>();
		Department department;
		try {
			 department = departmentFeignCLient.getDepartmentById(departmentId);
			
		} catch (Exception e) {
			
			throw new Exception("Department not found with id: "+departmentId+"\n"+e.getMessage());
		}
		List<Employee> employees = employeeRepository.findByDeptId(departmentId);
		
		if(employees.isEmpty()) {
			throw new NoEmployeeFoundException("No Employee Found In Department");
		}
		
		for(Employee emp:employees) {
			try {
				EmployeeResponse response=new EmployeeResponse();
				Address address = addressFiegnClient.getAddressById(emp.getAddressId());
				response.setEmp(emp);
				response.setAddress(address);
				response.setDept(department);
				empoyeesResponse.add(response);
			} catch (Exception e) {
				continue;
			}
		}
		return empoyeesResponse;
		
		
	}

}
