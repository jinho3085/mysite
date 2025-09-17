package com.bit2025.mysite.exception;

public class GusetbookRepositoryException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public GusetbookRepositoryException() {
		super("GusetbookRepositoryExcepiton Occurs");
	}
	
	public GusetbookRepositoryException(String message) {
		super(message);
	}
}
