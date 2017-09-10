package com.dev.spring.util;

import java.util.UUID;

/** 
* This class acts as a ID generator.
* @author  Sandeep Shetty
* @version 1.0
* @since   2017-07-18 
*/

public class IDGenerator {

	/**
	 * This method returns a unique UUID
	 * @return String
	 */
	public static synchronized String generateUUID() {
	    return UUID.randomUUID().toString().replace("-", "");
	}
}
