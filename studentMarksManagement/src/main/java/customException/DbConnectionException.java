package customException;


public class DbConnectionException extends Exception  
{  
    
	/**
	 * 
	 */
	
	public DbConnectionException(Exception exception) {
		super(exception);
	}
    
    
}
