package com.dev.spring.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dev.spring.model.User;
import com.dev.spring.model.UserSession;

/** 
* This class acts as in memory database.
* @author  Sandeep Shetty
* @version 1.0
* @since   2017-09-09 
*/
@Component
public class DataStore {

	/**
	 * Map to store users and user sessions, ideally we should use database
	 */
	private static	Map<String, User> usrData = new HashMap<String, User>();
	private static	Map<String, UserSession> usrSessionData = new HashMap<String, UserSession>();

	/**
	 * This method provides user data
	 * @return Map<String, User>
	 */
	public Map<String, User> getUsrData() {
		return usrData;
	}
	/**
	 * This method provides user session data
	 * @return Map<String, UserSession>
	 */
	public Map<String, UserSession> getUsrSessionData() {
		return usrSessionData;
	}
	
}
