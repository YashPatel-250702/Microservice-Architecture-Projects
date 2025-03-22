package com.tekworks.address.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tekworks.address.entity.Address;
import com.tekworks.address.service.AddressService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/address")
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@PreAuthorize("hasAuthority('Admin')")
	@PostMapping("/saveAddress")
	public ResponseEntity<?> saveAddress(@Valid @RequestBody Address address ){
		try {
			Address saveAddress = addressService.saveAddress(address);
			if(saveAddress==null) {
				throw new RuntimeException("Error while saving department");
			}
			return ResponseEntity.status(HttpStatus.OK).body("Address Information Saved Successfully");
		}catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		} 
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
		}
	}
	
	//@PreAuthorize("hasAuthority('Admin') || hasAuthority('SCOPE_internal')")
	@GetMapping("/getAddressById/{id}")
	public ResponseEntity<?> getAddressById(@PathVariable Integer id){
		try {
			 Address address = addressService.getAddressById(id);
			 return ResponseEntity.status(HttpStatus.OK).body(address);
		}catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		} 
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
		}
		
	}

}
