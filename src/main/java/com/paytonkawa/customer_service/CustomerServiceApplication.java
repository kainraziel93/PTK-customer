package com.paytonkawa.customer_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.paytonkawa.customer_service.entity.Adress;
import com.paytonkawa.customer_service.entity.Customer;
import com.paytonkawa.customer_service.repo.CustomerRepo;

@SpringBootApplication
@EnableDiscoveryClient
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner start(CustomerRepo repo) {
		return args->{
			try {
				System.out.println("starting saving...");
				Customer c1 = new  Customer("adam","achahbar","achahbar@gmail.com",new Adress( 32100, "rue du colonel paul paillole"));
				repo.save(c1);
				System.out.println("save completed");
			} catch (Exception e) {
				System.out.println("something went wrong");
			}
		
		};
	}

}