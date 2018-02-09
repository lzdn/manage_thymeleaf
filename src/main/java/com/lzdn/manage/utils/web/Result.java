package com.lzdn.manage.utils.web;

public class Result {

	private Header header;
	private Body body;
	private int code;
	private String message;

	public Result() {
	}

	public Result(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public Result(Header header, Body body) {
		this.header = header;
		this.body = body;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
