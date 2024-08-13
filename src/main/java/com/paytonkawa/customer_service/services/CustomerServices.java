package com.paytonkawa.customer_service.services;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.paytonkawa.customer_service.entity.Customer;
import com.paytonkawa.customer_service.repo.CustomerRepo;

@Service
public class CustomerServices {

	private CustomerRepo customerRepo;
	
	public CustomerServices(CustomerRepo customerRepo) {
		this.customerRepo = customerRepo;
	}
	
	public ResponseEntity<Map<String, String>> createCustomer(Customer customer){
		try {
			System.out.println("hada customer=>"+customer);
			this.customerRepo.save(customer);
			return ResponseEntity.ok(Map.of("message","customer with email "+customer.getEmail()+" saved succefully"));
		} catch (Exception e) {
			return  ResponseEntity.badRequest().build();
		}
	}
	
	public ResponseEntity<Map<String, String>> updateCustomer(int customerId,Customer customer){
			try {
				Customer customerToUpdate = this.customerRepo.findById(customerId).get();
				if(customer.getFirstname()!=null) {
					customerToUpdate.setFirstname(customer.getFirstname());
				}
				if(customer.getLastname()!=null) {
					customerToUpdate.setLastname(customer.getLastname());
				}
				if(customer.getEmail()!=null) {
					customerToUpdate.setEmail(customer.getEmail());
				}
				if(customer.getAdress()!=null) {
					customerToUpdate.setAdress(customer.getAdress());
				}
				this.customerRepo.save(customerToUpdate);
				return ResponseEntity.ok(Map.of("message","customer with email "+customer.getEmail()+" updated succefully"));
				
			} catch (Exception e) {
				System.out.println("error => "+e.getMessage());
				return  ResponseEntity.badRequest().build();
			}	
	}
	
	public ResponseEntity<Map<String,String>> deleteCustomer(int customerId){
		try {
			Customer customer = this.customerRepo.findById(customerId).get();
			this.customerRepo.delete(customer);
			return ResponseEntity.ok(Map.of("message","customer with email "+customer.getEmail()+" deleted succefully"));
			
		} catch (Exception e) {
			System.out.println("error => "+e.getMessage());
			return  ResponseEntity.badRequest().build();
		}
	}
	
	public ResponseEntity<Customer> CustomerById(int customerId){
		try {
			Customer customer = this.customerRepo.findById(customerId).get();
			return ResponseEntity.ok(customer);
			
		} catch (Exception e) {
			System.out.println("error => "+e.getMessage());
			return  ResponseEntity.badRequest().build();
		}
	}
}
