package com.tekworks.microservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tekworks.microservice.entity.Employee;
import com.tekworks.microservice.exception.NoEmployeeFoundException;
import com.tekworks.microservice.exception.ServiceUnavailableException;
import com.tekworks.microservice.feigncleints.DepartmentFeignCLient;
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

	@Transactional(rollbackForClassName = "java.lang.Exception")
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    
    
    
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "rateLimiterDepartmentFallBack")
    @CircuitBreaker(name = "departmentBreaker", fallbackMethod = "departmentFallBack")
    @Retry(name = "departmentRetry", fallbackMethod = "retryDepartmentFallBack")
    public EmployeeResponse getEmployeeById(Integer id) throws NoEmployeeFoundException {
        Optional<Employee> employee = employeeRepository.findById(id);

        if (employee.isPresent()) {
            Employee employeeData = employee.get();
            
            // Fetch department via Feign Client
            Department department = departmentFeignCLient.getDepartmentById(employeeData.getDeptId());

            EmployeeResponse response = new EmployeeResponse();
            response.setEmp(employeeData);
            response.setDept(department);
            return response;
        }
        throw new NoEmployeeFoundException("No Employee Found With id: " + id);
    }

   
    public EmployeeResponse departmentFallBack(Integer id, Exception ex) {
        throw new ServiceUnavailableException("Department Service is down. Please try again later.");
    }

   
    public EmployeeResponse retryDepartmentFallBack(Integer id, Exception ex) {
        throw new ServiceUnavailableException("Temporary issue, please try again after some time.");
    }

   
    public EmployeeResponse rateLimiterDepartmentFallBack(Integer id, Exception ex) {
        throw new ServiceUnavailableException("Too many requests. Please wait and try again.");
    }
}
