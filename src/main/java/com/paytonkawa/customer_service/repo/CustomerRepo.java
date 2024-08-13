package com.paytonkawa.customer_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paytonkawa.customer_service.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

}
