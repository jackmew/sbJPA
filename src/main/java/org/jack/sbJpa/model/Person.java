/**
 * 
 */
package org.jack.sbJpa.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * @author jackho
 *
 */
@Entity
@Table(name = "persons")
public class Person {
     
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
     
    @Column(name = "creation_time", nullable = false)
    private Date creationTime;
     
    @Column(name = "first_name", nullable = false)
    private String firstName;
 
    @Column(name = "last_name", nullable = false)
    private String lastName;@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "person_image",
    joinColumns = {@JoinColumn(name= "person_id" , referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "image_id" , referencedColumnName = "id")})
    private Image image;
     
    @Column(name = "modification_time", nullable = false)
    private Date modificationTime;
     
    @Version
    private long version = 0;
    
    
    
 




	/**
	 * @return the creationTime
	 */
	public Date getCreationTime() {
		return creationTime;
	}


	/**
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}


	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}


	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	/**
	 * @return the modificationTime
	 */
	public Date getModificationTime() {
		return modificationTime;
	}


	/**
	 * @param modificationTime the modificationTime to set
	 */
	public void setModificationTime(Date modificationTime) {
		this.modificationTime = modificationTime;
	}


	/**
	 * @return the version
	 */
	public long getVersion() {
		return version;
	}


	/**
	 * @param version the version to set
	 */
	public void setVersion(long version) {
		this.version = version;
	}


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


	/**
     * 
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }
    

    /**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}


	/**
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
	}
    
    /**
     * for update person
     * 
     * @author jackho
     */
    public void update(String firstName , String lastName){
    	this.firstName = firstName;
    	this.lastName = lastName;
    }
    
    
   @PreUpdate
   public void preUpdate() {
       modificationTime = new Date();
   }
    
   @PrePersist
   public void prePersist() {
       Date now = new Date();
       creationTime = now;
       modificationTime = now;
   }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Person [id=" + id + ", creationTime=" + creationTime
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", modificationTime=" + modificationTime + ", version=" + version
				+ "]";
	}
   
   
}
