package com.tekworks.microservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

	private Integer id;

	private String country;

	private String state;

	private String city;

	private String houseNo;

}
