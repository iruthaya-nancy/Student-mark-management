package serviceimplementation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.apache.logging.log4j.Logger;

import customException.BizServiceException;
import customException.DbConnectionException;
import pojo.Mark;
import pojo.Student;
import service.TeacherService;
import util.DataBaseConnectivity;

import org.apache.logging.log4j.LogManager;

public class TeacherServiceImpl implements TeacherService {
	Logger logger = LogManager.getLogger(TeacherServiceImpl.class);

	public void printMarks(int rollnumber) throws BizServiceException {

		try {
			Scanner scanner = new Scanner(System.in);
			int id = 0;
			int testNumber = 0;
			Connection connection = DataBaseConnectivity.getdbConnectivity();
			Statement statement = connection.createStatement();
			String getIdFromRollNumber = "SELECT id FROM student WHERE roll_number = '" + rollnumber + "'";
			ResultSet idRes = statement.executeQuery(getIdFromRollNumber);
			if (idRes.next()) {
				id = idRes.getInt("id");
			
			    System.out.println("Enter the test number: ");
			    testNumber = scanner.nextInt();
			    String studentDetails = "SELECT student.roll_number,student.name,mark.test_number,mark.english,mark.maths,mark.chemistry,mark.physics,mark.os,mark.compiler,mark.total FROM student INNER JOIN mark ON student.id = mark.student_id WHERE student.id ='"
					+ id + "' AND mark.test_number = '" + testNumber + "'";
			    ResultSet result = statement.executeQuery(studentDetails);
			    if (result.next()) {
			    		System.out.print("student Rollnumber: " + result.getInt("roll_number") + "\n" + "Student name: "
						+ result.getString("name") + "\n" + "Test Id: " + result.getInt("test_number") + "\n"
						+ "English: " + result.getInt("english") + "\n" + "Maths: " + result.getInt("maths") + "\n"
						+ "Chemistry: " + result.getInt("chemistry") + "\n" + "Physics: " + result.getInt("physics")
						+ "\n" + "OS: " + result.getInt("os") + "\n" + "Compiler: " + result.getInt("compiler") + "\n"
						+ "Total: " + result.getInt("total") + "\n");
			    }
			    else {
			    	System.out.println("TEST DOES NOT EXIST ");
			    }
			}
			else {
				System.out.println("ROLL NUMBER DOES NOT EXIST ");
			}
			connection.close();
		} catch (InputMismatchException exception) {
			System.out.println("PLEASE ENTER THE NUMBER");
		} catch (SQLException | DbConnectionException exception) {
			logger.error("Database connection failed \n " + exception);
			throw new BizServiceException(exception);
			
		}
	}

	public void invokeService() {
		Scanner scanner = new Scanner(System.in);
		try {
			Student student = new Student();
			int choice = 0;
			do {
				System.out.println("------------------------------------------");
				System.out.println("Choose your choice: ");
				System.out.println("1.To insert student details" + "\n" + "2.To insert student's  marks" + "\n"
						+ "3.To get student details" + "\n" + "4.To get students marks");
				System.out.println("5.To search a student" + "\n" + "6.To update marks" + "\n" + "7.To get top scorers"
						+ "\n" + "8.To delete a student" + "\n" + "9.To get students list of class");
				System.out.println("10.To get the failures list" + "\n" + "11.To display the entire students details "
						+ "\n" + "12.Logout\n");
				choice = scanner.nextInt();
				switch (choice) {
				case 1:
					insertStudents();
					break;
				case 2:
					calculateTotal();
					break;
				case 3:
					System.out.println("Enter the roll number: ");
					student.setRollnumber(scanner.nextInt());
					printStudents(student.getRollnumber());
					break;
				case 4:
					System.out.println("Enter student's rollnumber : ");
					int rollnumber = scanner.nextInt();
					printMarks(rollnumber);
					break;
				case 5:
					searchStudentById();
					break;
				case 6:
					updateStudentMarks();
					break;
				case 7:
					getRankHolders();
					break;
				case 8:
					deleteStudent();
					break;
				case 9:
					getStudentsfromClass();
					break;
				case 10:
					getFailList();
					break;
				case 11:
					getAllStudentDetails();
					break;
				case 12:
					System.out.println("Logout");
					break;
				default:
					System.out.println("Invalid Option");
				}
			} while (choice != 12);
		} catch (InputMismatchException exception) {
			System.out.println("PLEASE ENTER THE NUMBER ");
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	private void insertStudents() throws BizServiceException {
		Scanner scanner = new Scanner(System.in);
		try {
			Student student = new Student();
			System.out.println("Enter the rollnumber for the student: ");
			student.setRollnumber(scanner.nextInt());
			System.out.println("Enter the  student's name: ");
			student.setName(scanner.next());
			System.out.println("Enter the class room where the student belongs to: ");
			student.setStudentclass(scanner.next());
			System.out.println("Enter the  student's age: ");
			student.setAge(scanner.nextInt());
			System.out.println("Enter the  student's city: ");
			student.setCity(scanner.next());

			Connection connection = DataBaseConnectivity.getdbConnectivity();
			Statement statement = connection.createStatement();
			String sql = "INSERT INTO student (name,class,age,city,roll_number) VALUES('"+student.getName()+"','"+student.getStudentclass()+"','"+student.getAge()+"','"+student.getCity()+"','"+student.getRollnumber()+"')";
			statement.executeUpdate(sql);
			System.out.println("STUDENT DETAILS WERE INSERTED SUCCESSFULLY");
			connection.close();
		} catch (SQLException | DbConnectionException exception) {
			logger.error("Database connection failed \n " + exception);
			throw new BizServiceException(exception);
			
		}
	}

	private void calculateTotal() throws BizServiceException {
		Scanner scanner = new Scanner(System.in);
		Mark mark = new Mark();
		Student student = new Student();
		System.out.println("Enter the student's rollnumber: ");
		student.setRollnumber(scanner.nextInt());
		System.out.println("Enter the test number: ");
		mark.setTestId(scanner.nextInt());
		System.out.println("Enter the english mark: ");
		mark.setEnglish(scanner.nextInt());
		System.out.println("Enter the maths mark: ");
		mark.setMaths(scanner.nextInt());
		System.out.println("Enter the chemistry mark: ");
		mark.setChemistry(scanner.nextInt());
		System.out.println("Enter the physics mark: ");
		mark.setPhysics(scanner.nextInt());
		System.out.println("Enter the OS mark: ");
		mark.setOs(scanner.nextInt());
		System.out.println("Enter the compiler mark: ");
		mark.setCompiler(scanner.nextInt());
		

		try {
		
			Connection connection = DataBaseConnectivity.getdbConnectivity();
			Statement statement = connection.createStatement();
			String studentIdLogin = "SELECT id FROM student WHERE roll_number = '"+student.getRollnumber()+"'";
			ResultSet idRes = statement.executeQuery(studentIdLogin);
			if(idRes.next()) {
				
				String sql = "INSERT INTO mark (student_id,test_number,english,maths,physics,chemistry,os,compiler) VALUES ('"+idRes.getInt("id")+"','"+mark.getTestId()+"','"+mark.getEnglish()+"','"+mark.getMaths()+"','"+mark.getPhysics()+"','"+mark.getChemistry()+"','"+mark.getOs()+"','"+mark.getCompiler()+"')";
				statement.executeUpdate(sql);
				System.out.println("MARKS WERE INSERTED SUCCESSFULLY ");
				connection.close();
            }
			else {
				System.out.println("ROLLNUMBER DOES NOT EXIST");
			}
		}
		catch (SQLException | DbConnectionException exception) {
			logger.error("Database connection failed \n " + exception);
			throw new BizServiceException(exception);
			
		}
		

	}

	private void printStudents(int id) throws  BizServiceException {
		try {
			Connection connection = DataBaseConnectivity.getdbConnectivity();
			Statement statement = connection.createStatement();
			String sql = "SELECT * FROM student  WHERE roll_number = '" + id + "'";
			ResultSet result = statement.executeQuery(sql);
			if (result.next()) {
				System.out.println("Student Roll number: " + result.getInt("roll_number") + "\n" + "Student ID: "
						+ result.getInt("ID") + "\n" + "Student Name: " + result.getString("name") + "\n"
						+ "Student Class: " + result.getString("class") + "\n" + "Student City: "
						+ result.getString("city") + "\n" + "Student Age: " + result.getInt("age"));

			} else {
				System.out.println("STUDENT DOES NOT EXIST");

			}
			connection.close();

		} catch (SQLException | DbConnectionException exception) {
			logger.error("Database connection failed \n " + exception);
			throw new BizServiceException(exception);
			
		}

	}

	private void searchStudentById() throws  BizServiceException {
		Scanner scanner = new Scanner(System.in);
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		Student student = new Student();
		try {

			Connection connection = DataBaseConnectivity.getdbConnectivity();
			Statement statement = connection.createStatement();
			String sql = "SELECT roll_number ,name FROM student";
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				map.put(result.getInt(1), result.getString(2));

			}
			connection.close();

		} catch (SQLException | DbConnectionException exception) {
			logger.error("Database connection failed \n " + exception);
			throw new BizServiceException(exception);
			
		}
		if (map.isEmpty()) {
			System.out.println("STUDENT DOES NOT EXIST FOR THE GIVEN ROLL NUMBER ");
		} else {
			System.out.print("Enter the student's roll number : ");
			student.setRollnumber(scanner.nextInt());
			if (map.containsKey(student.getRollnumber())) {
				System.out.println("Student Name: " + map.get(student.getRollnumber()));
			}
		}

	}

	private void updateStudentMarks() throws  BizServiceException {
		Scanner scanner = new Scanner(System.in);
		Student student = new Student();
		Mark mark = new Mark();
		try {
			Connection connection = DataBaseConnectivity.getdbConnectivity();
			Statement statement = connection.createStatement();
			String sql = null;
			int choice = 0, id = 0;
			System.out.println("Enter the student's rollnumber: ");
			student.setRollnumber(scanner.nextInt());
			String studentIdLogin = "SELECT id FROM student WHERE roll_number = '" + student.getRollnumber() + "'";
			ResultSet idResult = statement.executeQuery(studentIdLogin);
			if (idResult.next()) {
				id = idResult.getInt("id");
				System.out.println("Enter the test Number: ");
				mark.setTestId(scanner.nextInt());
				System.out.println("Enter the subject to update: ");
				System.out.println("-------------------------------------------------------------------------");
				System.out.println("1 to English" + "," + "2 to Maths" + "," + "3 to Chemistry" + "," + "4 to Physics" + ","
					+ "5 to OS" + "," + "6 to Compiler");
				System.out.println("Enter your Choice: ");
				choice = scanner.nextInt();
				System.out.println("Enter the mark: ");
				int passMark = scanner.nextInt();

				switch (choice) {
					case 1:
						sql = "UPDATE mark SET english = '" + passMark + "' WHERE student_id  = '" + id + "' AND test_number = '"
								+ mark.getTestId() + "'";
						break;
					case 2:
		
						sql = "UPDATE mark SET maths = '" + passMark + "'   WHERE student_id = '" + id + "' AND test_number = '"
								+ mark.getTestId() + "'";
						break;
					case 3:
		
						sql = "UPDATE mark SET chemistry = '" + passMark + "' WHERE student_id = '" + id + "'and test_number = '"
								+ mark.getTestId() + "'";
						break;
					case 4:
						sql = "UPDATE mark  SET physics = '" + passMark + "' WHERE student_id = '" + id + "' AND test_number ='"
								+ mark.getTestId() + "'";
						break;
					case 5:
		
						sql = "UPDATE mark SET os = '" + passMark + "'  WHERE student_id = '" + id + "' AND test_number='"
								+ mark.getTestId() + "'";
						break;
					case 6:
		
						sql = "UPDATE mark SET  compiler = '" + passMark + "' WHERE student_id = '" + id + "' AND test_number ='"
								+ mark.getTestId() + "'";
						break;
					case 7:
						System.out.println("Exit");
					default:
						System.out.print("Invalid option");
				}
			statement.executeUpdate(sql);
			System.out.println("UPDATED SUCCESSFULLY");
			}
			else {
				System.out.println("ROLLNUMBER DOES NOT EXIST ");
			}
		} catch (SQLException | DbConnectionException exception) {
			logger.error("Database connection failed \n " + exception);
			throw new BizServiceException(exception);
			
		}

	}

	private void getRankHolders() throws BizServiceException {
		Scanner scanner = new Scanner(System.in);
		StringBuilder studentDetails = new StringBuilder();
		try {
			Mark mark = new Mark();
			System.out.println("Enter the test number: ");
			mark.setTestId(scanner.nextInt());
			System.out.println("Enter the count: ");
			int limit = scanner.nextInt();
			Connection connection = DataBaseConnectivity.getdbConnectivity();
			Statement statement = connection.createStatement();
			String sql = "SELECT name,total FROM student INNER JOIN mark ON student.ID =mark.student_id WHERE mark.test_number = '"
					+ mark.getTestId() + "' ORDER BY mark.total DESC LIMIT " + limit + "";
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				studentDetails.append(result.getString(1) + "---" + result.getInt(2) + "\n");
			}
			if (studentDetails.isEmpty()) {
				System.out.println("TEST DOES NOT EXIST");
			} else {
				System.out.println("-:The High scorers are :-");
				System.out.print(studentDetails);
				connection.close();
			}
		} catch (SQLException | DbConnectionException exception) {
			logger.error("Database connection Failed \n" + exception);
			throw new BizServiceException(exception);

		} 

	}

	private void deleteStudent() throws BizServiceException{
		Scanner scanner = new Scanner(System.in);
		Student student = new Student();
		try {
			System.out.print("Enter Student's roll number To Delete: ");
			student.setRollnumber(scanner.nextInt());
			Connection connection = DataBaseConnectivity.getdbConnectivity();
			Statement statement = connection.createStatement();
			String sql = "DELETE FROM student WHERE roll_number = '" + student.getRollnumber() + "'";
			statement.executeUpdate(sql);
			System.out.println("STUDENT DETAILS AND MARKS DELETED SUCCESSFULLY");
			connection.close();

		} catch (SQLException | DbConnectionException exception) {
			logger.error("Database connection failed \n " + exception);
			throw new BizServiceException(exception);
			
		}

	}

	private void getStudentsfromClass() throws BizServiceException {
		Scanner scanner = new Scanner(System.in);
		Student student = new Student();
		ArrayList<String> list = new ArrayList<>();
		try {
			System.out.print("Enter the Class Name: ");
			student.setStudentclass(scanner.next());
			Connection connection = DataBaseConnectivity.getdbConnectivity();
			Statement statement = connection.createStatement();
			String sql = "SELECT name FROM student WHERE class ='" + student.getStudentclass() + "'";
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				list.add(result.getString(1));
			}
			if (list.isEmpty()) {
				System.out.println("CLASS IS EMPTY");
			} else {
				list.stream().sorted().forEach(System.out::println);
				System.out.println("Total student in: '"+student.getStudentclass()+"'"+"\n"+list.size());

			}

		} catch (SQLException | DbConnectionException exception) {
			logger.error("Database connection failed \n " + exception);
			throw new BizServiceException(exception);
			
		}
	}

	private void getFailList() throws BizServiceException{
		Scanner scanner = new Scanner(System.in);
		Mark mark = new Mark();
		ArrayList<String> list = new ArrayList<>();
		try {
			System.out.println("Enter the Test number: ");
			mark.setTestId(scanner.nextInt());
			System.out.println("Enter the Subject name: ");
			System.out.println("-------------------------------------------------------------------------");
			System.out.println(
					"English" + "," + "Maths" + "," + "Chemistry" + "," + "Physics" + "," + "OS" + "," + "Compiler");
			String subject = scanner.next();
			System.out.println("Enter the pass mark: ");
			int passmark = scanner.nextInt();
			Connection connection = DataBaseConnectivity.getdbConnectivity();
			Statement statement = connection.createStatement();
			String sql = null;
			switch (subject) {

			case "English":
				sql = "SELECT name FROM student INNER JOIN mark ON student.ID = mark.student_id WHERE test_number = '"
						+ mark.getTestId() + "' AND english < '" + passmark + "'";
				break;
			case "Maths":
				sql = "SELECT name FROM student INNER JOIN mark ON student.ID = mark.student_id WHERE test_number = '"
						+ mark.getTestId() + "' AND maths < '" + passmark + "'";
				break;
			case "Chemistry":
				sql = "SELECT name FROM student INNER JOIN mark ON student.ID = mark.student_id WHERE test_number  = '"
						+ mark.getTestId() + "' AND chemistry <'" + passmark + "'";
				break;
			case "Physics":
				sql = "SELECT name  FROM student INNER JOIN mark ON student.ID = mark.student_id WHERE  test_number  ='"
						+ mark.getTestId() + "' AND physics <'" + passmark + "'";
				break;
			case "OS":
				sql = "SELECT name FROM student INNER JOIN mark ON student.ID = mark.student_id WHERE test_number = '"
						+ mark.getTestId() + "' AND os < '" + passmark + "'";
				break;
			case "Compiler":
				sql = "SELECT name FROM student INNER JOIN mark ON student.ID = mark.student_id  WHERE test_number = '"
						+ mark.getTestId() + "' AND compiler<'" + passmark + "'";
				break;
			default:
				System.out.println("Invalid option");

			}
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				list.add(result.getString(1));
			}

			if (list.isEmpty()) {
				System.out.println("NO STUDENTS WERE LESS THAN " + passmark + " IN " + subject);
			} else {
				System.out.println("Failures in " + "----->" + subject);
				list.stream().forEach(System.out::println);
				System.out.println("Total Failures: " + list.size());
			}
		} catch (SQLException | DbConnectionException exception) {
			logger.error("Database connection failed \n " + exception);
			throw new BizServiceException(exception);
			
		}

	}

	// to display entire table
	private void getAllStudentDetails() throws BizServiceException {
		try {
			Connection connection = DataBaseConnectivity.getdbConnectivity();
			Statement statement = connection.createStatement();
			String sql = "SELECT  * FROM student";
			ResultSet studentResult= statement.executeQuery(sql);
			if (studentResult.next()) {
				System.out.printf("%5s %10s %9s %10s %10s", "Rollnumber", "Name", "Class", "City", "Age" + "\n");
				System.out.println("-----------------------------------------------------");
				do {
					System.out.printf("%5s %15s %9s %13s %6s", studentResult.getInt("roll_number"),
							studentResult.getString("name"), studentResult.getString("class"), studentResult.getString("city"),
							studentResult.getInt("age") + "\n");
				} while (studentResult.next());
			} else {
				System.out.println("TABLE IS EMPTY");
			}

		} catch (SQLException | DbConnectionException exception) {
			logger.error("Database connection failed \n " + exception);
			throw new BizServiceException(exception);
			
		}
	}

}
