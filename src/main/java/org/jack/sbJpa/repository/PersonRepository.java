/**
 * 
 */
package org.jack.sbJpa.repository;

import java.util.Date;
import java.util.List;

import org.jack.sbJpa.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author jackho
 *
 */
public interface PersonRepository extends JpaRepository<Person,Long>{

	/* Query Creation from Method Name */
	public List<Person> findByLastName(String lastName);
	
	public List<Person> findByFirstName(String firstName);
	
	public List<Person> findByLastNameStartingWith(String lastName);
	
	public List<Person> findByCreationTimeBefore(Date date);
	
	
	
	/* @Query Annotation 
	@Query("SELECT p FROM Person p WHERE LOWER(p.lastName) = LOWER(:lastName)")
	public List<Person> lastName(@Param("lastName")String lastName);

	@Query("SELECT p FROM Person p WHERE LOWER(p.firstName) = LOWER(:firstName)")
	public List<Person> firstName(@Param("firstName")String firstName);
	*/
}
