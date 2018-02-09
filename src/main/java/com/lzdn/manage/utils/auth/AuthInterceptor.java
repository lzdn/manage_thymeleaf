package com.lzdn.manage.utils.auth;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lzdn.manage.conf.WebProperties;
import com.lzdn.manage.domain.core.User;
import com.lzdn.manage.utils.SecurityHelper;
import com.lzdn.manage.utils.SpringUtil;
import com.lzdn.manage.utils.web.Result;
import com.lzdn.manage.utils.web.WebHelper;
import com.lzdn.manage.web.base.BaseController;

/**
 * 权限拦截
 * 
 * @author lzdn
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

	final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
	
	WebProperties webProperties = null;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Integer currentMenu = -1;
		ActiveMenu activeMenu = null;
		AuthMethod authMethod = null;// 方法的注解
		HandlerMethod handlerMethod = (HandlerMethod) handler;// 获取方法上的注解
		authMethod = handlerMethod.getMethodAnnotation(AuthMethod.class);
		Object controllerhandler = handlerMethod.getBean();
		if (controllerhandler instanceof BaseController) {
			activeMenu = ((BaseController) handlerMethod.getBean()).getClass().getAnnotation(ActiveMenu.class);
		}
		
		webProperties = webProperties == null ? SpringUtil.getBean("webProperties"):webProperties;
		
		
		String path = request.getServletPath();
		if ("/error".equals(path)) {
			return true;
		}
		
		if (authMethod != null && !authMethod.mustLogin()) {
			// 不需要校验权限
			return true;
		} else {
			boolean ipChange = false;
			boolean browserChange = false;
			Result result = null;
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(webProperties.getSessionName());
			if (user == null) {
				String cookie = WebHelper.getCookie(request, webProperties.getCookieName());
				if (cookie != null && !cookie.equals("")) {
					cookie = SecurityHelper.desDecrypt(cookie, webProperties.getSecurityKey());
					if (cookie != null && !cookie.equals("")) {
						String[] cookies = cookie.split("\\|");
						// 根据IP和UserAgent判断是否为伪造的Cookie
						if (cookies.length == 3) {
							if (!WebHelper.getIp(request).equals(cookies[1])) {
								// ip有变化
								ipChange = true;
							} else if (!request.getHeader("user-agent").equals(cookies[2])) {
								// 浏览器或浏览器版本有变化
								browserChange = true;
							} else {
								user = AuthHelper.getLoginUser(request);
								AuthHelper.setLoginUser(request, response, user);
							}
						}
					}
				}
			}
			
			if(activeMenu != null) {
				currentMenu = activeMenu.menuId();
				AuthHelper.setActiveMenu(request, currentMenu);
			}
			
			if (user == null) {
				// 跳转到登录页面
				response.sendRedirect("/login");
				return true;
			}
			/*
			 * 校验用户当前状态 略
			 */
			if (ipChange) {
				result = new Result(-1, "系统检测到您的网络环境发生变化,请重新登录");
			}
			if (browserChange) {
				result = new Result(-1, "系统检测到您的浏览器或版本发生变化,请重新登录");
			}
			if (result!=null && isAjax(request, response)) {
				writeJSON(response, result);
			}  
			/* 校验权限 */

		
		}
		return true;

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {//在视图渲染之前
		request.setAttribute("basePath", webProperties.getBasePath());
	}

	protected boolean isAjax(HttpServletRequest request, HttpServletResponse response) {
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			return true;
		} else {
			return false;
		}
	}

	protected void writeJSON(HttpServletResponse response, Object obj) throws IOException {
		response.reset();
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue));
	}

}
