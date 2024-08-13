package com.paytonkawa.customer_service.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import jakarta.persistence.CascadeType;
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
	@OneToOne(cascade = CascadeType.ALL)
	private Adress adress;
	
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Customer(
			@NotBlank(message = "firstname needed for customer") @Size(min = 2, message = "firstname should be at least 2 characters long") String fistname,
			@NotBlank(message = "lastname needed for customer") @Size(min = 2, message = "lastname should be at least 2 characters long") String lastname,
			@NotBlank(message = "email cannot be empty") @Email(message = "not a valid email adress ") String email,
			Adress adress) {
		super();
		this.fistname = fistname;
		this.lastname = lastname;
		this.email = email;
		this.adress = adress;
	}

	public String getFistname() {
		return fistname;
	}

	public void setFistname(String fistname) {
		this.fistname = fistname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Adress getAdress() {
		return adress;
	}

	public void setAdress(Adress adress) {
		this.adress = adress;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", fistname=" + fistname + ", lastname=" + lastname + ", email=" + email
				+ ", adress=" + adress + "]";
	}
	
	
	
	
	
	
}
