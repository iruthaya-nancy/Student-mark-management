package serviceimplementation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import customException.BizServiceException;
import customException.DbConnectionException;
import pojo.Student;
import service.Validation;
import util.DataBaseConnectivity;

public  class StudentValidationImpl implements Validation {
	
	public ResultSet doUserValidation() throws BizServiceException{
		Logger logger = LogManager.getLogger(StudentValidationImpl.class);
		ResultSet studentLoginResult  = null;
		try {
		
		    Scanner scanner = new Scanner(System.in);
		    Student student = new Student();
			System.out.println("Enter your roll number and name: ");
			student.setRollnumber(scanner.nextInt());
			student.setName(scanner.next());
			Connection connection = DataBaseConnectivity.getdbConnectivity();
			Statement statement = connection.createStatement();
			String sql = "select roll_number,name from student where roll_number='"+student.getRollnumber()+"' and name='"+student.getName()+"' ";
		    studentLoginResult = statement.executeQuery(sql);
		    
		}
		
		catch(SQLException e) {
			logger.error("DataBase connection Failed "+e);
			throw new BizServiceException (e);
		}
	   catch(InputMismatchException exception) {
		System.out.println("Please enter the number ");
		exception.printStackTrace();
    	}
		catch(Exception e) {
			e.printStackTrace();
		}
		return studentLoginResult;
	
}
	
}
