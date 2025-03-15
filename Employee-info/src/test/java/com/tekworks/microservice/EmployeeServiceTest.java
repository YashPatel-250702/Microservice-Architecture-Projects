package com.tekworks.microservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tekworks.microservice.entity.Employee;
import com.tekworks.microservice.feigncleints.DepartmentFeignCLient;
import com.tekworks.microservice.model.Department;
import com.tekworks.microservice.repository.EmployeeRepository;
import com.tekworks.microservice.service.EmployeeService;

@ExtendWith(MockitoExtension.class) 
class EmployeeApplicationTests {
    
    @Mock  
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentFeignCLient departmentFeignCLient;

    @InjectMocks
    private EmployeeService employeeService;  

    @Test
    public void testSaveEmployee() {
        Employee employee = new Employee();
        employee.setDeptId(1);
        Department department = new Department();
        
        doReturn(department).when(departmentFeignCLient).getDepartmentById(any());
        doReturn(employee).when(employeeRepository).save(any());
    
        Employee savedEmployee = employeeService.saveEmployee(employee);
        assertEquals(employee, savedEmployee);
    }
    
    @Test
    public void testSaveEmployeeInvalidDepartmentId() {
        Employee employee = new Employee();
        employee.setDeptId(1);

        doReturn(null).when(departmentFeignCLient).getDepartmentById(any());

        assertThrows(RuntimeException.class, () -> employeeService.saveEmployee(employee));
    }
    
    @Test
    public void testSaveEmployeeNullDepartmentId() {
        Employee employee = new Employee();
        employee.setDeptId(null);

        assertThrows(IllegalArgumentException.class, () -> employeeService.saveEmployee(employee));
    }
    
    @Test
    public void testSaveEmployeeNullEmployee() {
        assertThrows(NullPointerException.class, () -> employeeService.saveEmployee(null));
    }
}
