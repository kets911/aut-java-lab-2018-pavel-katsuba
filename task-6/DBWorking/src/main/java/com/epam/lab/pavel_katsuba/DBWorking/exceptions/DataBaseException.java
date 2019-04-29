package com.epam.lab.pavel_katsuba.DBWorking.exceptions;

public class DataBaseException extends RuntimeException {

	public DataBaseException() {
		super();
		
	}


	public DataBaseException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public DataBaseException(String message) {
		super(message);
		
	}

	public DataBaseException(Throwable cause) {
		super(cause);
	}
	
	
}
