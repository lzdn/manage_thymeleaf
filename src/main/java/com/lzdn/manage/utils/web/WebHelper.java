package com.lzdn.manage.utils.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lzdn.manage.utils.StringExtention;

public class WebHelper {

	// final Logger logger = LoggerFactory.getLogger(WebHelper.class);

	/* private RedisTemplate redisTemplate; */

	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String getCookie(HttpServletRequest request, String cookieName) {
		Cookie[] cookie = request.getCookies();
		String cookieValue = null;
		if (cookie != null && cookie.length > 0) {
			for (int i = 0; i < cookie.length; i++) {
				if (cookie[i].getName().equals(cookieName)) {
					cookieValue = cookie[i].getValue();
					break;
				}
			}
		}
		return cookieValue;
	}

	public static void setCookieOfExpiry(HttpServletResponse response, String cookieName, String value,
			Integer expiry) {
		setCookie(response, cookieName, value, expiry, null, null);
	}

	public static void setCookie(HttpServletResponse response, String cookieName, String value) {
		setCookie(response, cookieName, value, null);
	}

	public static void setCookie(HttpServletResponse response, String cookieName, String value, String domain) {
		setCookie(response, cookieName, value, null, domain, null);
	}

	public static void delCookie(HttpServletResponse response, String cookieName) {
		Cookie cookie = new Cookie(cookieName, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public static void delCookie(HttpServletResponse response, String cookieName, String domain) {
		Cookie cookie = new Cookie(cookieName, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		if (StringExtention.isNotEmpty(domain)) {
			cookie.setDomain(domain);
		}
		response.addCookie(cookie);
	}

	public static void setCookie(HttpServletResponse response, String cookieName, String value, Integer expiry,
			String domain, String path) {
		try {
			Cookie cookie;
			cookie = new Cookie(cookieName, URLEncoder.encode(value, "UTF-8"));
			if (expiry != null) {
				cookie.setMaxAge(expiry);
			}
			if (domain != null && !domain.equals("")) {
				// if (!domain.startsWith(".")) {
				// domain = "." + domain;
				// }
				cookie.setDomain(domain);
			}
			if (path != null) {
				cookie.setPath(path);
			} else {
				cookie.setPath("/");
			}
			response.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			// logger.("cookie 编码错误", e);
			System.out.println("cookie 编码错误:" + e);
		}
	}

	/**
	 * 
	 * @Title: getUseAgent @Description: 获取useragent @param request @return @author
	 *         yinqiang @throws
	 */
	public static String getUseAgent(HttpServletRequest request) {
		String userAgent = request.getHeader("user-agent");
		String regex = ";\\s?(\\S*?\\s?\\S*?)\\s?(Build)?/";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(userAgent);
		String agent = null;
		if (matcher.find()) {
			agent = matcher.group(1).trim();
		}
		return agent;
	}

	/**
	 * 过滤脚本注入
	 */
	public static String encode(String value) {
		if (value != null) {
			return value.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;")
					.replaceAll("\"", "&quot;").replaceAll("'", "&acute;");
		} else {
			return null;
		}
	}

	public static String filterHtml(String str) {
		String regxpForHtml = "<([^>]*)>"; // 过滤所有以<开头以>结尾的标签
		Pattern pattern = Pattern.compile(regxpForHtml);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

}
