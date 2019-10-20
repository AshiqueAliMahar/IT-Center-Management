package beans;

import java.sql.Date;
import java.sql.Time;

public class BatchBean {
	private String batchName;
	private String courseName;
	private Time startTime;
	private Time endTime;
	private Date startDate;
	private Date endDate;
	public BatchBean() {
		// TODO Auto-generated constructor stub
	}
	public BatchBean(String batchName, String courseName, Time startTime, Time endTime, Date startDate, Date endDate) {
		super();
		this.batchName = batchName;
		this.courseName = courseName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Time getStartTime() {
		return startTime;
	}
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	public Time getEndTime() {
		return endTime;
	}
	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
