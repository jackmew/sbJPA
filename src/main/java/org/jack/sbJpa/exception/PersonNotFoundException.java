/**
 * 
 */
package org.jack.sbJpa.exception;

/**
 * @author jackho
 *
 */
public class PersonNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5718401132395439686L;
	
	public PersonNotFoundException(){
		
	}
	
	public PersonNotFoundException(String message) {
		super(message);
	}
	
	public PersonNotFoundException(Throwable cause) {
		super(cause);
	}
	
	public PersonNotFoundException(String message , Throwable cause) {
		super(message , cause);
	}
	
//	public PersonNotFoundException(String message , Throwable cause , 
//			boolean enableSuppression , boolean writableStackTrace) {
//		super(message , cause , enableSuppression , writableStackTrace);
//		
//	}
}
