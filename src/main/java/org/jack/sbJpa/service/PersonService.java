/**
 * 
 */
package org.jack.sbJpa.service;

import java.util.Date;
import java.util.List;

import org.jack.sbJpa.exception.PersonNotFoundException;
import org.jack.sbJpa.model.Person;

/**
 * @author jackho
 *
 */
/**
 * Declares methods used to obtain and modify person information.
 * @author Petri Kainulainen
 */
public interface PersonService {
 
   
    public Person create(Person created);
 

    public Person delete(Long personId) throws PersonNotFoundException;
 

    public List<Person> findAll();
 

    public Person findById(Long id);
 

    public Person update(Person updated) throws PersonNotFoundException;
    
    
    public List<Person> findByLastName(String lastName);
    
    
	public List<Person> findByFirstName(String firstName);
	
	
	public List<Person> findByLastNameStartingWith(String lastName);
	
	
	public List<Person> findByCreationTimeBefore(Date date);
}
