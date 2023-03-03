package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import customException.DbConnectionException;
import customException.UserLoginException;
import service.TeacherService;
import service.Validation;
import serviceimplementation.StudentValidationImpl;
import serviceimplementation.TeacherServiceImpl;
import serviceimplementation.TeacherValidationImpl;


public class StudentMarksManager {
	public static void main(String[] args) throws UserLoginException,DbConnectionException{
		
			Scanner scanner = new Scanner(System.in);
			TeacherService teacherService = new TeacherServiceImpl();
			Logger logger = LogManager.getLogger(StudentMarksManager.class);
			int choice = 0;
			do {
			
				System.out.println("\nChoose your Designation: ");
				System.out.println("1.Teacher\t"+"2.Student\t"+"3.Exit");
				choice = scanner.nextInt();
				
				switch(choice) {
				case 1:
						
						try {
							Validation teacherValidation = new TeacherValidationImpl();
							ResultSet result = teacherValidation.doUserValidation();
							if(result.next()) {
								System.out.println("WELCOME "+result.getString("name"));
								teacherService.invokeService();
							}
							else {
								throw new UserLoginException("name and ID did not match\n"+"Please enter the valid Name and ID");
							}
							
						}
						
						catch( SQLException exception) {
							logger.error("Database connection failed "+exception);
							throw new DbConnectionException(exception);
						}
						catch(InputMismatchException exception) {
							System.out.println("Please enter the number ");
							exception.printStackTrace();
						}
						
						catch(Exception e) {
							e.printStackTrace();
						}
						break;
				case 2:
							
					try {
						Validation studentValidation = new StudentValidationImpl();
						ResultSet res = studentValidation.doUserValidation();
						if(res.next()) {
							
							System.out.println("WELCOME "+res.getString("name"));
							System.out.println("---------------------------");
							teacherService.printMarks(res.getInt("roll_number"));
						}
							
						else {
						throw new UserLoginException("name and rollnumber did not match.\n"+"Please enter the valid rollnumber and name");
					} 
					}
					catch(SQLException e) {
						logger.error("DataBase connection Failed "+e);
						throw new DbConnectionException(e);
					}
				   catch(InputMismatchException exception) {
					System.out.println("Please enter the number ");
					exception.printStackTrace();
			    	}
					catch(Exception e) {
						e.printStackTrace();
					}   
					    
						 break;
				    case 3:
				    		System.out.println("EXIT");
				    		
				    		break;
					default:
							System.out.print("Invalid Option");
						
				}
			}while(choice!=3);
		}
	}
