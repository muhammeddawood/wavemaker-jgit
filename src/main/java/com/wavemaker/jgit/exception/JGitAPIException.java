package com.wavemaker.jgit.exception;

public class JGitAPIException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public JGitAPIException(Exception e) {
		super(e);
	}

}
