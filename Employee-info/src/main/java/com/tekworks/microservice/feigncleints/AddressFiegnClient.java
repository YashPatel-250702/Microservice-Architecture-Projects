package com.tekworks.microservice.feigncleints;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties.FeignClientConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tekworks.microservice.model.Address;

@FeignClient(name="ADDRESS-INFO",url="https://microservice-address-service.onrender.com",configuration = FeignClientConfiguration.class)
public interface AddressFiegnClient {
	
	@GetMapping("address/getAddressById/{id}")
	public Address getAddressById( @PathVariable Integer id);

}
