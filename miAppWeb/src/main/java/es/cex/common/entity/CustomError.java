/**
 *
 */
package es.cex.common.entity;

import com.google.gson.Gson;

/**
 * Custom Error codes and errorMessage
 *
 */

public class CustomError {

	private String errorMessage;
	private String errorCode;


	public CustomError() {
		super();
	}

	/**
	 * @param errorMessage
	 * @param errorCode
	 */
	public CustomError(String errorMessage, String errorCode) {
		super();
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}

	public String getJson() {
		return new Gson().toJson(this);
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}



}
