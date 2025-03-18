package com.tekworks.address.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tekworks.address.entity.Address;
import com.tekworks.address.repository.AddressRepository;

@Service
public class AddressService {
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Transactional(rollbackForClassName = "java.lang.Exception")
	public Address saveAddress(Address address) {
		return addressRepository.save(address);
		
	}
	
	public Address getAddressById(Integer id) {
		return addressRepository.findById(id).orElseThrow(()->new RuntimeException("Address not found with id: "+id));
	}

}
