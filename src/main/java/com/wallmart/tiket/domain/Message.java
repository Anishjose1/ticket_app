package com.wallmart.tiket.domain;

import java.io.Serializable;
/**
 * Userd for rest message
 * @author Anish Jose
 *
 */
public class Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	private String message;
	private boolean status;
	
	
}
