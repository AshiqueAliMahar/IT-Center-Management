package beans;

import java.sql.Date;
import java.sql.Timestamp;

public class StudentBean {
	private String rollNo;
	private String name;
	private Date dob;
	private Timestamp admsnDate;
	private String batchName;
	private String userEmail;
	public StudentBean() {
		// TODO Auto-generated constructor stub
	}
	public StudentBean(String rollNo, String name, Date dob, Timestamp admsnDate, String batchName, String userEmail) {
		super();
		this.rollNo = rollNo;
		this.name = name;
		this.dob = dob;
		this.admsnDate = admsnDate;
		this.batchName = batchName;
		this.userEmail = userEmail;
	}
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public Timestamp getAdmsnDate() {
		return admsnDate;
	}
	public void setAdmsnDate(Timestamp admsnDate) {
		this.admsnDate = admsnDate;
	}
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
}
