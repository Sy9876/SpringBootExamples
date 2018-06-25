package cn.sy.domain;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4914565329133414543L;
	private String id;
	@NotNull(message="NN")
	private String name;
	@Digits(fraction = 0, integer = 1)
	private String status;
	@Size(min=6, max=15)
	private String password;
	
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
