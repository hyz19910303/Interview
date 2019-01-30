package com.huyz.exception;

/**
 * Create at 2018年9月11日 下午4:24:38
 *
 * @autuor huyz
 *
 * @version 1.0
 *
 *          ProjectName HealthArchive
 *
 *          Description:
 * 
 */
public class RequestTooManyException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public RequestTooManyException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RequestTooManyException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

}
