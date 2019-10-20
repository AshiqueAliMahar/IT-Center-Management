package beans;

import java.sql.Timestamp;

public class CourseBean {
	private String name;
	private long fees;
	private Timestamp startDate;
	public CourseBean() {
		// TODO Auto-generated constructor stub
	}
	
	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public CourseBean(String name, long fees, Timestamp startDate) {
		super();
		this.name = name;
		this.fees = fees;
		this.startDate = startDate;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getFees() {
		return fees;
	}
	public void setFees(long fees) {
		this.fees = fees;
	}
	
}
