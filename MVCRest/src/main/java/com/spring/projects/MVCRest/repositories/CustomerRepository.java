package com.spring.projects.MVCRest.repositories;

import com.spring.projects.MVCRest.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
