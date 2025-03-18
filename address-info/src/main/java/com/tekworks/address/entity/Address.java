package com.tekworks.address.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message =  "Country is required")
	private String country;
	
	@NotBlank( message = "Address is required")
	private String state;
	
	@NotBlank(message = "City is required")
	private String city;
	
	@NotBlank(message = "HouseNo is required")
	private String houseNo;

}
