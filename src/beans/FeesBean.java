package beans;

import java.sql.Timestamp;

public class FeesBean {
	private long id;
	private String rollNo;
	private Timestamp payDate;
	private long amount;
	public FeesBean() {
		// TODO Auto-generated constructor stub
	}

	public FeesBean(long id, String rollNo, Timestamp payDate, long amount) {
		super();
		this.id = id;
		this.rollNo = rollNo;
		this.payDate = payDate;
		this.amount = amount;
	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public Timestamp getPayDate() {
		return payDate;
	}

	public void setPayDate(Timestamp payDate) {
		this.payDate = payDate;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}
	
}
