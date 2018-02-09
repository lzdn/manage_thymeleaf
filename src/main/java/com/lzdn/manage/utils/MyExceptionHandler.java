package com.lzdn.manage.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;

import com.lzdn.manage.exception.BusinessException;
import com.lzdn.manage.exception.PermissionException;

@ControllerAdvice("com.lzdn.manage")
public class MyExceptionHandler {

	/**
	 * 全局异常处理
	 * @param response
	 * @param e
	 */
	@org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
	public void ExceptionHandler(HttpServletResponse response, Exception e) {
		try {
			StringWriter out = new StringWriter();
			e.printStackTrace(new PrintWriter(out)); 
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json;charset=utf-8");
			response.getOutputStream().write(("系统异常：" + out.toString()).getBytes());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 业务异常处理
	 * @param response
	 * @param e
	 */
	@org.springframework.web.bind.annotation.ExceptionHandler(value = BusinessException.class)
	public void BussinessException(HttpServletResponse response, BusinessException e) {
		try {
			StringWriter out = new StringWriter();
			e.printStackTrace(new PrintWriter(out)); 
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json;charset=utf-8");
			response.getOutputStream().write(("业务异常：" + out.toString()).getBytes());
			//response.sendRedirect(location);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * 权限异常处理
	 * @param response
	 * @param e
	 */
	@org.springframework.web.bind.annotation.ExceptionHandler(value = PermissionException.class)
	public void PermissionException(HttpServletResponse response, PermissionException e) {
		try {
			StringWriter out = new StringWriter();
			e.printStackTrace(new PrintWriter(out)); 
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json;charset=utf-8");
			response.getOutputStream().write(("权限异常：" + out.toString()).getBytes());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
