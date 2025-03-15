package com.tekworks.microservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tekworks.microservice.entity.Employee;
import com.tekworks.microservice.exception.NoEmployeeFoundException;
import com.tekworks.microservice.feigncleints.DepartmentFeignCLient;
import com.tekworks.microservice.model.Department;
import com.tekworks.microservice.repository.EmployeeRepository;
import com.tekworks.microservice.response.EmployeeResponse;
import com.tekworks.microservice.service.EmployeeService;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceGetTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private DepartmentFeignCLient departmentFeignCLient;

	@InjectMocks
	private EmployeeService employeeService;

	@Test
	public void testGetEmployeeById_Found() throws NoEmployeeFoundException {
		Integer id = 1;
		Employee employee = new Employee();
		employee.setId(id);
		Department department = new Department();
		department.setId(id);

		when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
		when(departmentFeignCLient.getDepartmentById(id)).thenReturn(department);

		EmployeeResponse response = employeeService.getEmployeeById(id);

		assertNotNull(response);
		assertEquals(employee, response.getEmp());
		assertEquals(department, response.getDept());
	}

	@Test
	public void testGetEmployeeById_NotFound() {
		Integer id = 1;

		when(employeeRepository.findById(id)).thenReturn(Optional.empty());


		assertThrows(NoEmployeeFoundException.class, () -> employeeService.getEmployeeById(id));
	}

	@Test
	public void testGetEmployeeById_NullId() {
		Integer id = null;
		assertThrows(NoEmployeeFoundException.class, () -> employeeService.getEmployeeById(id));
	}

	@Test
	public void testGetEmployeeById_DepartmentNotFound() throws NoEmployeeFoundException {
		Integer id = 1;
		Employee employee = new Employee();
		employee.setId(id);

		when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
		when(departmentFeignCLient.getDepartmentById(id)).thenReturn(null);
		assertThrows(NullPointerException.class, () -> employeeService.getEmployeeById(id));
	}
}
