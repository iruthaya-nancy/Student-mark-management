package customException;


public class BizServiceException extends Exception  
{  
    
	/**
	 * 
	 */
	
	public BizServiceException(Exception exception) {
		super(exception);
	}
    
    
}
