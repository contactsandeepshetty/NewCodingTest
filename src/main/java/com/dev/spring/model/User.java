package com.dev.spring.model;

import java.io.Serializable;

/** 
* This class represents user
* @author  Sandeep Shetty
* @version 1.0
* @since   2017-09-09
*/
public class User implements Serializable{

	private static final long serialVersionUID = 8302814808655232829L;
	private String id;
	private String name;
	private String email;
	private String pincode;
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email
				+ ", pincode=" + pincode + "]";
	}		
	
}
