package com.lzdn.manage.exception;

public class PermissionException extends BaseException {

	private static final long serialVersionUID = 1L;
	private String taskCode = "";

	public PermissionException() {
	}

	public PermissionException(String taskCode) {
		super(taskCode);
		this.taskCode = taskCode;
	}

	public String getTaskCode() {
		return this.taskCode;
	}
}
