/**
 * 
 */
package org.jack.sbJpa.service;

import java.util.Date;
import java.util.List;

import org.jack.sbJpa.exception.PersonNotFoundException;
import org.jack.sbJpa.model.Person;
import org.jack.sbJpa.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author jackho
 *
 */
@Service
public class RepositoryPersonService implements PersonService{

	@Autowired
	private PersonRepository personRepository;
	
	@Override
	public Person create(Person created) {
		
		//Person person = Person.getBuilder(created.getFirstName(), created.getLastName()).build();
		return personRepository.save(created);
	}

	@Override
	public Person delete(Long personId) throws PersonNotFoundException {
		Person deleted = findById(personId);
		
		if (deleted == null) {
			System.out.println("No person found with id: "+ personId);
			throw new PersonNotFoundException();
		}
		personRepository.delete(deleted);
		return deleted;
		
	}

	@Override
	public List<Person> findAll() {
		return personRepository.findAll();
	}

	@Override
	public Person findById(Long id) {
		return personRepository.findOne(id);
	}

	@Override
	public Person update(Person updated) throws PersonNotFoundException {
		
		Person person = findById(updated.getId());
		
		if (person == null) {
			System.out.println("No person found with id: "+updated.getId());
			throw new PersonNotFoundException();
		}
		person.update(updated.getFirstName(), updated.getLastName());
		
		return personRepository.save(person);
	}

	/*Query Creation from Method Name*/
	@Override
	public List<Person> findByLastName(String lastName) {
		return personRepository.findByLastName(lastName);
	}	
	@Override
	public List<Person> findByFirstName(String firstName) {
		return personRepository.findByFirstName(firstName);
	}

	@Override
	public List<Person> findByLastNameStartingWith(String lastName) {
		return personRepository.findByLastNameStartingWith(lastName);
	}

	@Override
	public List<Person> findByCreationTimeBefore(Date date) {
		return personRepository.findByCreationTimeBefore(date);
	}
	
	/* @Query Annotation 
	@Override
	public List<Person> findByLastName(String lastName) {
		return personRepository.lastName(lastName);
	}
	@Override
	public List<Person> findByFirstName(String firstName) {
		return personRepository.firstName(firstName);
	}
	*/

}
