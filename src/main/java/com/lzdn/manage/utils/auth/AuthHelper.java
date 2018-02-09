package com.lzdn.manage.utils.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lzdn.manage.conf.WebProperties;
import com.lzdn.manage.domain.core.User;
import com.lzdn.manage.service.UserService;
import com.lzdn.manage.utils.SecurityHelper;
import com.lzdn.manage.utils.web.WebHelper;

@Component
public class AuthHelper {

	private static UserService userService;

	private static WebProperties webProperties;

	@Autowired
	public void setWebProperties(WebProperties webProperties) {
		AuthHelper.webProperties = webProperties;
	}

	@Autowired
	public void setUserService(UserService userService) {
		AuthHelper.userService = userService;
	}

	public static User getLoginUser(HttpServletRequest request) throws Exception {
		User user = null;
		HttpSession session = request.getSession();
		if (session.getAttribute(webProperties.getSessionName()) == null) {
			String cookie = WebHelper.getCookie(request, webProperties.getCookieName());
			if (cookie != null && !cookie.equals("")) {
				cookie = SecurityHelper.desDecrypt(cookie, webProperties.getSecurityKey());
				if (cookie != null && !cookie.equals("")) {
					// 根据IP和UserAgent判断是否为伪造的Cookie
					String[] cookies = cookie.split("\\|");
					if (isWeiXinAPPAccess(request)) {// 微信登录
						if (cookies.length > 1) {
							// 根据cookie中的帐号查询用户
							user = userService.getUserRightByUserId(new Integer(cookies[0]));
						}
					} else {
						if (cookies.length == 3) {
							if (!WebHelper.getIp(request).equals(cookies[1])
									|| !request.getHeader("user-agent").equals(cookies[2])) {
								String agent = "用户ID:" + cookies[0] + "\t当前IP:" + WebHelper.getIp(request)
										+ "\tCookie中IP:" + cookies[1] + "\t当前user-agent:"
										+ request.getHeader("user-agent") + "\tCookie中user-agent:" + cookies[2];
								// iLog.addLog(LogType.LoginFail, String.format("伪造Cookie登录！%s", agent),
								// WebHelper.getIp(request));
								System.out.println("异常登录状态，非法操作:" + agent);
								// throw new BusinessException("异常登录状态，非法操作，您的操作已经被记录");
							} else {
								// RequestHead requestHead = new RequestHead();
								// requestHead.setUserIp(WebHelper.getIp(request));
								// employee = iEmployee.getEmpRoleRightById(Integer.parseInt(cookies[0]));
								// employee = iEmployee.getEmpAllRightById(Integer.parseInt(cookies[0]));
								user = userService.getUserRightByUserId(new Integer(cookies[0]));
							}
						}
					}
				}
			}
		} else {
			user = (User) session.getAttribute(webProperties.getSessionName());
		}

		return user;
	}

	public static void setLoginUser(HttpServletRequest request, HttpServletResponse response, User user)
			throws Exception {
		if (user != null) {
			String cookieValue = String.format("%s|%s|%s", user.getUserId(), WebHelper.getIp(request),
					request.getHeader("user-agent"));

			cookieValue = SecurityHelper.desEncrypt(cookieValue, webProperties.getSecurityKey());

			//WebHelper.setCookie(response, "zhanglin-com", cookieValue, 360 * 24 * 60 * 60, webProperties.getCookieDomain(),
			// 		null);
			WebHelper.setCookie(response, webProperties.getCookieName(), cookieValue, 60 * 60, webProperties.getCookieDomain(),
			 		null);
			//WebHelper.setCookie(response, "LoginUser", cookieValue, 60 * 60, null, null);
			HttpSession session = request.getSession();

			session.setAttribute(webProperties.getSessionName(), user);
			//session.setAttribute("menuGroup", user.getMenuGroups());
		}
	}
	public static void rememberMe(HttpServletRequest request , HttpServletResponse response , String account) {
		try {
			String rememberMe = request.getParameter("rememberMe");
			if (StringUtils.isNotEmpty(rememberMe) && StringUtils.isNotEmpty(account)&&"rememberMe".equals(rememberMe)) {
				WebHelper.setCookie(response, "rememberMe", SecurityHelper.desEncrypt(account, webProperties.getSecurityKey()));
			} else {
				WebHelper.delCookie(response, "rememberMe");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void delLoginUser(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.removeAttribute(webProperties.getSessionName());
		WebHelper.delCookie(response, webProperties.getCookieName());
	}
	
	public static void setActiveMenu(HttpServletRequest request,Integer currentMenu) {
		request.setAttribute("currentMenu", currentMenu);
		
	}
	// 判断是否微信客户端访问
	public static boolean isWeiXinAPPAccess(HttpServletRequest httpRequest) {
		return httpRequest.getHeader("User-Agent") != null
				&& httpRequest.getHeader("User-Agent").toLowerCase().contains("micromessenger");
	}

	// 获取操作系统类型 0 未知 1IOS 2ANDROID
	public static int getSystemType(HttpServletRequest httpRequest) {
		if (httpRequest.getHeader("User-Agent") != null) {
			String userAgent = httpRequest.getHeader("User-Agent").toLowerCase();
			if (userAgent.contains("iphone") || userAgent.contains("mac os")) {
				return 1;
			} else if (userAgent.contains("android")) {
				return 2;
			}
		}
		return 0;
	}
}
