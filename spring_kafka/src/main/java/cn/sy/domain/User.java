package cn.sy.domain;

public class User {
	protected String id;
	protected String name;
	protected String status;
	protected String password;
	
	public User() {
		
	}
	public User(String id, String name, String status, String password) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
		this.password = password;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
