package com.paytonkawa.customer_service.entity;

import javax.validation.constraints.NotBlank;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Adress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotBlank(message="zip code cannot be blank")
	private int zipCode;
	@NotBlank(message=" adress cannot be blank")
	private String adress;
	
	
	public Adress() {

	}


	public Adress( @NotBlank(message = "zip code cannot be blank") int zipCode,
			@NotBlank(message = " adress cannot be blank") String adress) {
		super();
		this.zipCode = zipCode;
		this.adress = adress;
	}


	public int getZipCode() {
		return zipCode;
	}


	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}


	public String getAdress() {
		return adress;
	}


	public void setAdress(String adress) {
		this.adress = adress;
	}


	public int getId() {
		return id;
	}


	@Override
	public String toString() {
		return "Adress [id=" + id + ", zipCode=" + zipCode + ", adress=" + adress + "]";
	}
	
	
	
	
	
}
