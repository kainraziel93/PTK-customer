package com.paytonkawa.customer_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paytonkawa.customer_service.services.CustomerServices;

@RestController
@RequestMapping("customer")
public class CustomerController {

	private CustomerServices customerServices;
	
	public CustomerController(CustomerServices customerServices) {
		this.customerServices = customerServices;
	}
	
}
