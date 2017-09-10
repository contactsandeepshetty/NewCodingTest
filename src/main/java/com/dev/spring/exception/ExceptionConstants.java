package com.dev.spring.exception;

/**
* Exception constants is a class that keeps a collection of all user defined exceptions, error codes and error descriptions. 
*
* @author  Sandeep Shetty
* @version 1.0
* @since   2017-07-18 
*/
public enum ExceptionConstants {
	
	UNKNOWN_ERROR,
	USER_SESSION_EXPIRED;

	private static String SERVERRORCODE001= "Unknown error.";
	private static String SERVERRORCODE002= "User session expired.";
	
	/**
	 * For a given friendly name of exception enum, receive all error & resolution information
	 * 
	 * @param ec error code
	 * @return ExceptionVO with all information required to log or send error above layers
	 */
	public static ExceptionVO getExceptionInfo(ExceptionConstants ec) {
		
		switch(ec) {
		
		case USER_SESSION_EXPIRED: return new ExceptionVO("SERVERRORCODE002",SERVERRORCODE002);
			
 		default: return new ExceptionVO("SERVERRORCODE001",SERVERRORCODE001);
 		
		}
	}
}



