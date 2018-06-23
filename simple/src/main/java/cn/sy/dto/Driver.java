package cn.sy.dto;

public class Driver {
	private String id;
	private String name;
	private String company;
	private String phoneNo;
	private String status;
	private String passwd;
	
	public Driver() {
		super();
	}
	
	public Driver(String id, String name, String company,
			String phoneNo, String status, String passwd) {
		super();
		this.id = id;
		this.name = name;
		this.company = company;
		this.phoneNo = phoneNo;
		this.status = status;
		this.passwd = passwd;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	@Override
	public String toString() {
		return "Driver: id=" + id
			+ " name=" + name
			+ " company=" + company
			+ " phoneNo=" + phoneNo
			+ " status=" + status
			+ " passwd=" + passwd;
	}

}
