package com.epam.exceptions;

public class BookAlreadyPresent extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BookAlreadyPresent(String message) {
		super(message);
	}

}
