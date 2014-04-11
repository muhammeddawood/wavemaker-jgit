package com.wavemaker.repo.exception;

public class RepositoryException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RepositoryException(Exception e) {
		super(e);  
	}

}
