/**
 * 
 */
package org.jack.sbJpa.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.jack.sbJpa.exception.PersonNotFoundException;
import org.jack.sbJpa.model.Image;
import org.jack.sbJpa.model.Person;
import org.jack.sbJpa.service.RepositoryPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
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
	private RepositoryPersonService rps;
	
	@Autowired
	private ServletContext servletContext;

	
	@RequestMapping("/")
	public @ResponseBody String createPerson() throws IOException {
		
		String[] fileArr = {"bean_green.jpg","elephant_blue.jpg","rabbit_blue.jpg",
				"rabbit_red.jpg","smile_rainbow.jpg"};
		
		for(String element : fileArr){
			System.out.println(element);
			saveInitImage(element);
		}
		return "Initialize person completed.";
		
	}
	public void saveInitImage(String fileName) throws IOException{
		InputStream inputStream = null;
		String fName = "/WEB-INF/resources/images/"+fileName;
		//Otherwise you are splitting on the regex ., which means "any character".
		String[] namePostfixArr = fileName.split("\\.");
		String fullName = namePostfixArr[0];
		String[] nameArr = fullName.split("_");
		String firstName = nameArr[0];
		String lastName = nameArr[1];
		String imageName = firstName+"_pic";
        try {
        	
            inputStream = servletContext.getResourceAsStream(fName);
            // Prepare buffered image.
            BufferedImage img = ImageIO.read(inputStream);

            // Create a byte array output stream.
            ByteArrayOutputStream bao = new ByteArrayOutputStream();

            // Write to output stream
            ImageIO.write(img, "jpg", bao);
            
            byte[] imageByte = bao.toByteArray();
            
            Image image = new Image();
            image.setBytes(imageByte);
            image.setContentType("image/jpg");
            image.setFileName(imageName);
              
            Person person = new Person();
            
            person.setFirstName(firstName);
            person.setLastName(lastName);
            person.setImage(image);
            person.setCreationTime(new Date());
            
            rps.create(person);
            
        } finally {
            if (inputStream != null) {
               inputStream.close();
            }
        }
	}

	@RequestMapping("/findAllPerson")
	public ResponseEntity<List<Person>> findAllPerson() {
		List<Person> persons = rps.findAll();
		
		return new ResponseEntity<List<Person>>(persons,HttpStatus.OK);
	}
	
	@RequestMapping("/findOne")
	public ResponseEntity<Person> findOne(@RequestParam(value="id" , required=true)Long id) {
		Person person =  rps.findById(id);
		return new ResponseEntity<Person>(person,HttpStatus.OK);
	}
	
	/*Query Creation from Method Name*/
	@RequestMapping("/findByFirstName")
	public ResponseEntity<List<Person>> findByFirstName(@RequestParam(value="firstName" , required=true)String firstName) {
		List<Person> persons = rps.findByFirstName(firstName);
		return new ResponseEntity<List<Person>>(persons,HttpStatus.OK);
	}
	
	@RequestMapping("/findByLastName")
	public ResponseEntity<List<Person>> findByLastName(@RequestParam(value="lastName" , required=true)String lastName) {
		List<Person> persons = rps.findByLastName(lastName);
		return new ResponseEntity<List<Person>>(persons,HttpStatus.OK);
	}
	
	@RequestMapping("/findByLastNameStartingWith")
	public ResponseEntity<List<Person>> findByLastNameStartingWith(@RequestParam(value="lastName" , required=true)String lastName) {
		List<Person> persons = rps.findByLastNameStartingWith(lastName);
		return new ResponseEntity<List<Person>>(persons,HttpStatus.OK);
	}
	
	@RequestMapping("/findByCreationTimeBefore")
	public ResponseEntity<List<Person>> findByCreationTimeBefore(){
		List<Person> persons = rps.findByCreationTimeBefore(new Date());
		return new ResponseEntity<List<Person>>(persons,HttpStatus.OK);
	}
	
	@RequestMapping("/findPersonPage")
	public ResponseEntity<Page<Person>> findPersonPage(@RequestParam(value="pageNumber" , required=true)int pageNumber){
		Page<Person> persons = rps.findPersonPage(pageNumber);
		
		return new ResponseEntity<Page<Person>>(persons,HttpStatus.OK);
	}

	@RequestMapping("/update")
	public ResponseEntity<Person> update(@RequestBody List<Map> personList) throws PersonNotFoundException {
		
		String getId = (String) personList.get(0).get("id");
		Long id = Long.valueOf(getId);
		String firstName = (String) personList.get(0).get("firstName");
		String lastName = (String) personList.get(0).get("lastName");
		
		Person person = rps.findById(id);
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setId(id);
		
		rps.update(person);
		
		
		return new ResponseEntity<Person>(person,HttpStatus.OK);
	}
	
	@RequestMapping("/delete")
	public @ResponseBody String delete(@RequestParam(value="id" , required=true)Long id) throws PersonNotFoundException {
		rps.delete(id);
		return "delete successs";
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
