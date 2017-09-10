package com.dev.spring.model;

import java.io.Serializable;
import java.util.Date;

/** 
* This class represents user session
* @author  Sandeep Shetty
* @version 1.0
* @since   2017-09-09
*/
public class UserSession implements Serializable{

	private static final long serialVersionUID = -6085580649294982513L;
	private String id;
	private User user;
	private Date expireDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}	
}
