package com.paytonkawa.customer_service.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paytonkawa.customer_service.dto.CustomerResponse;
import com.paytonkawa.customer_service.dto.LoginRequest;
import com.paytonkawa.customer_service.entity.Customer;
import com.paytonkawa.customer_service.services.CustomerServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("customer")
public class CustomerController {

	private CustomerServices customerServices;
	
	public CustomerController(CustomerServices customerServices) {
		this.customerServices = customerServices;
	}
	
	@Operation(summary = "get  a customer with id", description = "get a customer with the provided id if it exist in the database")
	@ApiResponse(responseCode = "200", description = "the customer if found")
	@GetMapping("{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable int id){
		return this.customerServices.CustomerById(id);
	}
	 
	@Operation(summary = "Create a new customer", description = "Create a new customer with the provided details")
	 @ApiResponse(responseCode = "200", description = "Customer saved successfully")
	@PostMapping()
	public ResponseEntity<Map<String, String>> createCustomer(@Valid @RequestBody Customer customer){
		return this.customerServices.createCustomer(customer);
	}
	
    @Operation(summary = "update a customer", description = "update a customer the provided id  and details if it exist in the database")
	@PutMapping("{id}")
	public ResponseEntity<Map<String,String>> updateCustomer(@PathVariable int id,@RequestBody Customer customerToUpdate){
		return this.customerServices.updateCustomer(id, customerToUpdate);
	}
    
    @Operation(summary = "delete a customer", description = "delte a customer the provided id  and details if it exist in the database")
	@DeleteMapping("{id}")
	public ResponseEntity<Map<String,String>> deleteCustomer(@PathVariable int id){
		return this.customerServices.deleteCustomer(id);
	}
    
    @Operation(summary = "check a customer by email ", description = "return customer if email exist ")
    @GetMapping("email/{email}")
    public ResponseEntity<Customer> customerByEmail(@PathVariable String email){
    	return this.customerServices.customerByEmail(email);
    }
    @Operation(summary = "check a customer with email and password", description = "return customer if credentials are correct else return bad request with ")
    @PostMapping("customer_by_credentials")
    public ResponseEntity<CustomerResponse> getCustomerByCredentials(@RequestBody LoginRequest loginRequest){
    	return this.customerServices.getCustomerByCredentials(loginRequest);
    }

    
}

