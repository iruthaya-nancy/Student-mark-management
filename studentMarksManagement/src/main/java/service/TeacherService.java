package service;

import customException.BizServiceException;
import customException.DbConnectionException;

public interface TeacherService {
	/**
	 * 
	 * @param rollnumbers
	 * @throws DbConnectionException
	 */
	  public void printMarks(int rollnumber) throws  BizServiceException;
	  public void invokeService();
}