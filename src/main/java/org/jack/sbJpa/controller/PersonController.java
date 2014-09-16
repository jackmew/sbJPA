/**
 * 
 */
package org.jack.sbJpa.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jack.sbJpa.exception.PersonNotFoundException;
import org.jack.sbJpa.model.Image;
import org.jack.sbJpa.model.Person;
import org.jack.sbJpa.repository.ImageRepository;
import org.jack.sbJpa.service.RepositoryPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @author jackho
 *
 */
@RestController
@RequestMapping("/person")
public class PersonController {
	
	@Autowired
	RepositoryPersonService rps;
	
	
	@RequestMapping("/")
	public void createPerson() {
		Person person = new Person();
		person.setFirstName("Ho");
		person.setLastName("Jack");
		person.setCreationTime(new Date());
		person.setModificationTime(new Date());
		rps.create(person);
		
		Person person1 = new Person();
		person1.setFirstName("Meow");
		person1.setLastName("Mew");
		person1.setCreationTime(new Date());
		person1.setModificationTime(new Date());
		rps.create(person1);
		
		Person person2 = new Person();
		person2.setFirstName("c");
		person2.setLastName("Chloe");
		person2.setCreationTime(new Date());
		person2.setModificationTime(new Date());
		rps.create(person2);
		
		Person person3 = new Person();
		person3.setFirstName("Ha");
		person3.setLastName("Cartoon");
		person3.setCreationTime(new Date());
		person3.setModificationTime(new Date());
		rps.create(person3);
		
		Person person4 = new Person();
		person4.setFirstName("Nice");
		person4.setLastName("Mac");
		person4.setCreationTime(new Date());
		person4.setModificationTime(new Date());
		rps.create(person4);
		
		System.out.println("create persons");
	}

	@RequestMapping("/findAllPerson")
	public ResponseEntity<List<Person>> findAllPerson() {
		List<Person> persons = rps.findAll();
		
		return new ResponseEntity<List<Person>>(persons,HttpStatus.OK);
	}
	
	@RequestMapping("/findOne")
	public void findOne(@RequestParam(value="id" , required=true)Long id) {
		Person person =  rps.findById(id);
		
		System.out.println("find one person");
	}
	
	@RequestMapping("/update")
	public void update() throws PersonNotFoundException {
		Person person = new Person();
		person.setFirstName("update");
		person.setLastName("update");
		person.setId(1L);
		
		rps.update(person);
		
		
		System.out.println("update person");
	}
	
	@RequestMapping("/delete")
	public void delete(@RequestParam(value="id" , required=true)Long id) throws PersonNotFoundException {
		rps.delete(id);
	}
	/*Query Creation from Method Name*/
	@RequestMapping("/findByFirstName")
	public void findByFirstName(@RequestParam(value="firstName" , required=true)String firstName) {
		
		List<Person> persons = rps.findByFirstName(firstName);
		System.out.println("find person findByFirstName ");
	}
	
	@RequestMapping("/findByLastName")
	public void findByLastName(@RequestParam(value="lastName" , required=true)String lastName) {
		
		List<Person> persons = rps.findByLastName(lastName);
		System.out.println("find person findByLastName ");
	}
	
	@RequestMapping("/findByLastNameStartingWith")
	public void findByLastNameStartingWith(@RequestParam(value="lastName" , required=true)String lastName) {
		
		List<Person> persons = rps.findByLastNameStartingWith(lastName);
		System.out.println("find person findByLastnameLike ");
	}
	
	@RequestMapping("/findByCreationTimeBefore")
	public void findByCreationTimeBefore(){
		List<Person> persons = rps.findByCreationTimeBefore(new Date());
		System.out.println("findByCreationTimeBefore");
	}

	@RequestMapping(value="/personUpload", method=RequestMethod.POST)
    public @ResponseBody String personUpload(MultipartHttpServletRequest request){
		
		 Iterator<String> itr =  request.getFileNames();
		 MultipartFile mpf = request.getFile(itr.next());
		 
		 
		 String firstName = request.getParameter("firstName");
		 String lastName = request.getParameter("lastName");
		 String fileName = firstName+"-pic";
		 Image image = new Image();
		 try {
			 image.setBytes(mpf.getBytes());
			 image.setContentType(mpf.getContentType());
			 image.setFileName(fileName);
			 
		 } catch (IOException e) {
			 e.printStackTrace();
			 return "You failed to save " + firstName + " => " + e.getMessage();
		 }
		 	 Person person = new Person();
		 	 person.setFirstName(firstName);
		 	 person.setCreationTime(new Date());
		 	 person.setLastName(lastName);
		 	 person.setModificationTime(new Date());
		 	 person.setImage(image);
		 	 
		 	 rps.create(person);

		 	return "You successfully save " + fileName + " into H2 !";
    }
	
	
	/* @Query Annotation 
	@RequestMapping("/firstName")
	public void findByFirstName(@RequestParam(value="firstName" , required=true)String firstName) {
		
		List<Person> persons = rps.findByFirstName(firstName);
		System.out.println("find person findByFirstName ");
	}
	
	@RequestMapping("/lastName")
	public void findByLastName(@RequestParam(value="lastName" , required=true)String lastName) {
		
		List<Person> persons = rps.findByLastName(lastName);
		System.out.println("find person findByLastName ");
	}
	*/
}
