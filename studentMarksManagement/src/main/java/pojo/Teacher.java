package pojo;

public class Teacher {
	
	private int teacherId;
	private String name;
	private String domain;
	private String incharge ;
	
	public Teacher() {
		
	}
	
	public int getTeacherID() {
		return teacherId;
	}


	public void setTeacherID(int teacherID) {
		this.teacherId = teacherID;
	}


	public String getTeacherName() {
		return name;
	}


	public void setTeacherName(String name) {
		this.name = name;
	}


	public String getIncharge() {
		return incharge;
	}



	public void setIncharge(String  incharge) {
		this.incharge = incharge;
	}


	public String getDomain() {
		return domain;
	}


	public void setDomain(String domain) {
		this.domain = domain;
	}

}
