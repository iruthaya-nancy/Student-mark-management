package serviceimplementation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import customException.BizServiceException;
import customException.DbConnectionException;
import pojo.Teacher;
import service.Validation;
import util.DataBaseConnectivity;

public class TeacherValidationImpl implements Validation {
	public  ResultSet doUserValidation() throws  BizServiceException {
		Logger logger = LogManager.getLogger(StudentValidationImpl.class);
		Scanner scanner = new Scanner(System.in);
		ResultSet teacherLoginResult  = null;
		try {
			    Teacher teacher = new Teacher();
			    System.out.println("Enter your id and name: ");
				teacher.setTeacherID(scanner.nextInt());
				teacher.setTeacherName(scanner.next());
				Connection connection = DataBaseConnectivity.getdbConnectivity();
				Statement statement = connection.createStatement();
				String sql = "select ID,name from teacher where ID ='"+teacher.getTeacherID()+"' and name ='"+teacher.getTeacherName()+"'";
				teacherLoginResult = statement.executeQuery(sql);
		}

		catch (SQLException | DbConnectionException exception) {
			logger.error("Database connection failed \n " + exception);
			throw new BizServiceException(exception);
			
		}
		return teacherLoginResult;
		
	}

}
