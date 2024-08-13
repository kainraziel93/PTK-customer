package com.paytonkawa.customer_service.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotBlank(message = "firstname needed for customer")
	@Size(min=2,message="firstname should be at least 2 characters long")
	private String fistname;
	@NotBlank(message = "lastname needed for customer")
	@Size(min=2,message="lastname should be at least 2 characters long")
	private String lastname;
	@NotBlank(message="email cannot be empty")
	@Email(message = "not a valid email adress ")
	private String email;
	@OneToOne(mappedBy = "customer")
	private Adress adress;
}
