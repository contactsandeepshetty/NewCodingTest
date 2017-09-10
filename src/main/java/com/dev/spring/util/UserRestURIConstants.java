package com.dev.spring.util;

/** 
* This class provides all the URI templates
* @author  Sandeep Shetty
* @version 1.0
* @since   2017-09-09
*/
public class UserRestURIConstants {
	
	public static final String CREATE_USER_LOGIN_LINK = "/rest/user/loginlink/{userId}";
	public static final String CREATE_USER = "/rest/user";
	public static final String GET_USER_BY_SESSIONTOKENID = "/rest/user/{sessionTokenId}";
	
	public static final String APPLICATION_LOGIN_LINK = "http://localhost:8080/spring/rest/user/{0}";

}
