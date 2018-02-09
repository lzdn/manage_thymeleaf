package com.lzdn.manage.exception;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private List<ExceptionCause> causeList = new ArrayList<ExceptionCause>();

	public BaseException() {
	}

	public BaseException(String message) {
		super(message);
	}

	public void addCause(ExceptionCause exceptionCause) {
		this.causeList.add(exceptionCause);
	}

	public List<ExceptionCause> getCauseList() {
		return this.causeList;
	}
}
