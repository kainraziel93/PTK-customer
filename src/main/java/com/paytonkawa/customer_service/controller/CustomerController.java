package com.paytonkawa.customer_service.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paytonkawa.customer_service.entity.Customer;
import com.paytonkawa.customer_service.services.CustomerServices;

@RestController
@RequestMapping("customer")
public class CustomerController {

	private CustomerServices customerServices;
	
	public CustomerController(CustomerServices customerServices) {
		this.customerServices = customerServices;
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable int id){
		return this.customerServices.CustomerById(id);
	}
	
	@PostMapping()
	public ResponseEntity<Map<String, String>> createCustomer(@Valid Customer customer){
		return this.customerServices.createCustomer(customer);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Map<String,String>> updateCustomer(@PathVariable int id,Customer customerToUpdate){
		return this.customerServices.updateCustomer(id, customerToUpdate);
	}
}
