package com.dev.spring.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.spring.dao.DataStore;
import com.dev.spring.exception.BaseAppException;
import com.dev.spring.exception.ExceptionConstants;
import com.dev.spring.exception.ExceptionVO;
import com.dev.spring.model.User;
import com.dev.spring.model.UserSession;
import com.dev.spring.util.EmailClient;
import com.dev.spring.util.IDGenerator;

/** 
* This class provides implementation for the methods of User service.
* @author  Sandeep Shetty
* @version 1.0
* @since   2017-09-09
*/
@Service
public class UserServiceImpl implements IUserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	EmailClient emailClient;
	
	@Autowired
	DataStore dataStore;

	/**
	 * This method creates new user
	 * @param User user
	 * @return String
	 * @throws BaseAppException
	 */
	@Override
	public String createUser(User user) throws com.dev.spring.exception.BaseAppException {
		logger.info("Start createUser");	
		String userId = IDGenerator.generateUUID();
		user.setId(userId);
		dataStore.getUsrData().put(user.getId(),user);
		return user.getId();
	}

	/**
	 * This method returns login link by userId
	 * @param userId
	 * @return
	 * @throws BaseAppException
	 */
	@Override
	public void getLoginLink(String userId) throws com.dev.spring.exception.BaseAppException {
		logger.info("Start getLoginLink");	
		User user = dataStore.getUsrData().get(userId);
		String sessionTokenId = null;		
		sessionTokenId = findActiveUserSessionTokenId(userId);		
		if (sessionTokenId != null) {
			sendEmail(sessionTokenId, user);
		} else {
			UserSession userSession = prepareUserSessionData(user);
			dataStore.getUsrSessionData().put(userSession.getId(), userSession);
			sendEmail(userSession.getId(), user);
		}
	}

	/**
	 * This method populates and returns a UserSession 
	 * @param user
	 * @return UserSession
	 * @throws BaseAppException
	 */
	private UserSession prepareUserSessionData(User user) {
		UserSession userSession = new UserSession();
		String sessionTokenId = IDGenerator.generateUUID();
		userSession.setId(sessionTokenId);
		Date expireDate = prepareSessionExpireDate();			
		userSession.setExpireDate(expireDate);			
		userSession.setUser(user);
		return userSession;
	}

	/**
	 * This method populates and returns a expire date for user session
	 * @param user
	 * @return Date
	 * @throws BaseAppException
	 */
	private Date prepareSessionExpireDate() {
		Date expireDate = new Date( ); 
		Calendar cal = Calendar.getInstance();
		cal.setTime(expireDate);
		cal.add(Calendar.MINUTE, 15);
		expireDate = cal.getTime();
		return expireDate;
	}
	
	/**
	 * This method finds and returns an active user session (if exists)
	 * @param userId
	 * @return String
	 * @throws BaseAppException
	 */
	private String findActiveUserSessionTokenId(String userId) {
		String sessionTokenId = null;
		Date currentDate = new Date();
		for (Map.Entry<String, UserSession> entry : dataStore.getUsrSessionData().entrySet()) {
			if (entry.getValue().getUser().getId().equalsIgnoreCase(userId)&& currentDate.before(entry.getValue().getExpireDate())) {
				sessionTokenId = entry.getValue().getId();
				break;
			}
		}
		return sessionTokenId;
	}
	
	/**
	 * This method sends mails using an email client
	 * @param sessionTokenId
	 * @param user
	 */
	private void sendEmail(String sessionTokenId, User user){	  
       	emailClient.send(sessionTokenId, user);     
    }
	

	/**
	 * This method returns user by SessionTokenId
	 * @param sessionTokenId
	 * @return User
	 * @throws BaseAppException
	 */
	@Override
	public User getUserBySessionTokenId(String sessionTokenId) throws com.dev.spring.exception.BaseAppException {
		logger.info("Start getUserBySessionTokenId");	
		UserSession userSession = dataStore.getUsrSessionData().get(sessionTokenId);
		Date currentDate = new Date();
		if (currentDate.after(userSession.getExpireDate())) {
			ExceptionVO vo = ExceptionConstants.getExceptionInfo(ExceptionConstants.USER_SESSION_EXPIRED);
			throw new BaseAppException(vo.getErrorCode(), vo.getErrorMessage());
		} else {
			User user = userSession.getUser();
			return user;
		}
	}
}
