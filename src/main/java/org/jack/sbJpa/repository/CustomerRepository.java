package org.jack.sbJpa.repository;

import java.util.List;

import org.jack.sbJpa.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);
}
