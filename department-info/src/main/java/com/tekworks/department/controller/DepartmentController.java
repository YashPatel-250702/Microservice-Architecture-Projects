package com.tekworks.department.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tekworks.department.entity.Department;
import com.tekworks.department.service.DepartmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/department")
public class DepartmentController {
	
	@Autowired
	private DepartmentService departmentService;
	

	@PostMapping("/saveDepartment")
	public ResponseEntity<Object> saveDepartment(@Valid @RequestBody Department department){
		
		try {
			
			Department saveDepartment = departmentService.saveDepartment(department);
			if(saveDepartment==null) {
				throw new Exception("Error while saving department");
			}
			return ResponseEntity.status(HttpStatus.OK).body("Departmnet Information Saved Successfully");
			
			
		}catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		} 
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
		}
	}

	@GetMapping("/getDepartmentById/{id}")
	public ResponseEntity<Object> getDepartmentById(@PathVariable Integer id){
		try {
			if(id==null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please Provide valid id to get departmnet");
			}
			return ResponseEntity.status(HttpStatus.OK).body(departmentService.getDepartmentById(id));			 
		}catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
