package com.dev.spring.service;

import com.dev.spring.exception.BaseAppException;
import com.dev.spring.model.User;

/** This interface defines the methods of User Service
* @author  Sandeep Shetty
* @version 1.0
* @since   2017-09-09 
*/
public interface IUserService {

	/**
	 * This method creates new user
	 * @param User user
	 * @return String
	 * @throws BaseAppException
	 */
	public String createUser(User user) throws BaseAppException;

	/**
	 * This method returns login link by userId
	 * @param userId
	 * @return
	 * @throws BaseAppException
	 */
	public void getLoginLink(String userId) throws BaseAppException;

	/**
	 * This method returns user by SessionTokenId
	 * @param sessionTokenId
	 * @return User
	 * @throws BaseAppException
	 */
	public User getUserBySessionTokenId(String sessionTokenId) throws BaseAppException;

}
