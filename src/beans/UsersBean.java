package beans;

import java.io.InputStream;

public class UsersBean {
	private String email;
	private String name;
	private String password;
	private String cnic;
	private byte [] pic;
	public UsersBean() {
		// TODO Auto-generated constructor stub
	}
	public UsersBean(String email, String name, String password, String cnic, byte [] pic) {
		super();
		this.email = email;
		this.name = name;
		this.password = password;
		this.cnic = cnic;
		this.pic = pic;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCnic() {
		return cnic;
	}
	public void setCnic(String cnic) {
		this.cnic = cnic;
	}
	public byte[] getPic() {
		return pic;
	}
	public void setPic(byte[] pic) {
		this.pic = pic;
	}
	
}
