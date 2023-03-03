package pojo;

import java.util.Objects;

public class Student {
	
	   private int rollNumber;
	   private String name;
	   private int age;
	   private String studentClass;
	   private int id;
	   private String city;
	   
	   @Override
	public int hashCode() {
		return Objects.hash(name, rollNumber);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(name, other.name) && rollNumber == other.rollNumber;
	}



	public int getRollnumber() {
		return rollNumber;
	}



	public void setRollnumber(int rollnumber) {
		this.rollNumber = rollnumber;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public int getAge() {
		return age;
	}



	public void setAge(int age) {
		this.age = age;
	}



	public String getStudentclass() {
		return studentClass;
	}



	public void setStudentclass(String studentclass) {
		studentClass = studentclass;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}

   public Student() {
	   
   }




     
}

