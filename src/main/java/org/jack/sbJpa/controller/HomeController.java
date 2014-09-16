/**
 * 
 */
package org.jack.sbJpa.controller;

import java.util.List;

import org.jack.sbJpa.model.Customer;
import org.jack.sbJpa.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jackho
 */
@Controller
public class HomeController {

	@Autowired
    CustomerRepository cr ;
	
	@RequestMapping("/")
	public String start(){
		return "redirect:/views/person/person.html";
	}
	
	@RequestMapping("/createCustomer")
	public void createCustomer(){
		// save a couple of customers
		cr.save(new Customer("Jack", "Bauer"));
		cr.save(new Customer("Chloe", "O'Brian"));
		cr.save(new Customer("Kim", "Bauer"));
		cr.save(new Customer("David", "Palmer"));
		cr.save(new Customer("Michelle", "Dessler"));
	}
	@RequestMapping("/findAllCustomer")
	public void findAllCustomer() {
		List<Customer> customers = cr.findAll();
		System.out.println(customers.size());
		System.out.println(customers.get(0).toString());
		
//		Iterable<Customer> customers = cr.findAll();
//        System.out.println("Customers found with findAll():");
//        System.out.println("-------------------------------");
//        for (Customer customer : customers) {
//            System.out.println(customer);
//        }
	}
}
