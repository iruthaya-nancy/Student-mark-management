package service;

import java.sql.ResultSet;

import customException.BizServiceException;
import customException.DbConnectionException;

public interface Validation {
     /**
      * Method for login
     * @throws BizServiceException 
      */
	public ResultSet doUserValidation() throws BizServiceException;
}
