package com.tekworks.address.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tekworks.address.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer>{

}
