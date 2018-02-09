package com.lzdn.manage.exception;

public class BusinessException extends BaseException {

	private static final long serialVersionUID = 1L;

	public BusinessException() {
	}

	public BusinessException(String messageKey, Object arg) {
		this(messageKey, new Object[] { arg });
	}

	public BusinessException(String messageKey, Object[] args) {
		ExceptionCause cause = new ExceptionCause(messageKey, args);
		addCause(cause);
	}

	public BusinessException(String messageKey, boolean isResource) {
		ExceptionCause cause = new ExceptionCause(messageKey, isResource);
		addCause(cause);
	}
}
