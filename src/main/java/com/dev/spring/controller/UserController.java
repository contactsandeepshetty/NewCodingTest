package com.dev.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dev.spring.exception.BaseAppException;
import com.dev.spring.exception.ExceptionConstants;
import com.dev.spring.exception.ExceptionVO;
import com.dev.spring.model.User;
import com.dev.spring.service.IUserService;
import com.dev.spring.util.UserRestURIConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


/** 
* This class handles requests for the User service.
* @author  Sandeep Shetty
* @version 1.0
* @since   2017-09-09
*/
@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);	
	
	@Autowired
	IUserService userService;

	/**
	 * This method provides a REST end point for creating new user
	 * @param user
	 * @return
	 */
	@RequestMapping(value = UserRestURIConstants.CREATE_USER, method = RequestMethod.POST)
	public ResponseEntity<String> createUser(@RequestBody User user) {
		logger.info("Start createUser.");	
		try{
		userService.createUser(user);
		}catch(BaseAppException e){			
			logger.error("Exception occured in UserController.createUser(): " + e.getMessage(), e);
			return new ResponseEntity<String>(handleException(e),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(convertObjToJSON(user.getId()), HttpStatus.OK);
	}
	
	/**
	 * This method provides a REST end point for getting login link for a user
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = UserRestURIConstants.CREATE_USER_LOGIN_LINK, method = RequestMethod.GET)
	public ResponseEntity<String> getLoginLink(@PathVariable("userId") String userId) {
		logger.info("Start getLoginLink");	
		try{
			userService.getLoginLink(userId);
		}catch(BaseAppException e){			
				logger.error("Exception occured in UserController.createUser(): " + e.getMessage(), e);
				return new ResponseEntity<String>(handleException(e),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("", HttpStatus.OK);
	}
	
	/**
	 * This method provides a REST end point for getting user details by sessionTokenId
	 * @param sessionTokenId
	 * @return
	 */
	@RequestMapping(value = UserRestURIConstants.GET_USER_BY_SESSIONTOKENID, method = RequestMethod.GET)
	public ResponseEntity<String> getUserBySessionTokenId(@PathVariable("sessionTokenId") String sessionTokenId) {
		logger.info("Start getUserBySessionTokenId");	
		User user = null;
		try{		
			user = userService.getUserBySessionTokenId(sessionTokenId);
		}catch(BaseAppException e){			
			logger.error("Exception occured in UserController.createUser(): " + e.getMessage(), e);
			return new ResponseEntity<String>(handleException(e),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(convertObjToJSON(user), HttpStatus.OK);
	}
	
	
	/**
	 * This method converts a instance of POJO into JSON string. 
	 * @param obj
	 * @return
	 */
	private String convertObjToJSON(Object obj){
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = "";
		try{
		jsonInString = mapper.writeValueAsString(obj);
		}catch(Exception e)
		{
			logger.error("Exception occured in UserController.convertObjToJSON(): " + e.getMessage(), e);
		}
		return jsonInString;
	}
	
	/**
	 * This method converts a instance of Throwable into JSON string. The JSON string returned shall include erroCode and errorDescription 
	 * @param e
	 * @return
	 */
	private String handleException(Throwable e)
	{	String jsonErrorString = null;	
		ObjectMapper mapper = new ObjectMapper();
		if (e instanceof BaseAppException)
		{
			BaseAppException bae = (BaseAppException)e;
			ObjectNode object = mapper.createObjectNode();
			object.put("errorCode", bae.getErrorCode());
			object.put("errorMessage",bae.getErrorMessage());
			jsonErrorString = object.toString();
		}
		else
		{
			ExceptionVO exceptionVO = ExceptionConstants.getExceptionInfo(ExceptionConstants.UNKNOWN_ERROR);
			ObjectNode object = mapper.createObjectNode();
			object.put("errorCode", exceptionVO.getErrorCode());
			object.put("errorMessage",exceptionVO.getErrorMessage());
			jsonErrorString = object.toString();
		}		
		return jsonErrorString;
	}
	
}
