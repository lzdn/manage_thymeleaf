package com.lzdn.manage.web.base;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lzdn.manage.conf.WebProperties;
import com.lzdn.manage.utils.CustomDateEditor;
import com.lzdn.manage.utils.SpringUtil;
import com.lzdn.manage.utils.web.Body;
import com.lzdn.manage.utils.web.Header;
import com.lzdn.manage.utils.web.Result;
import com.lzdn.manage.utils.web.WebHelper;

public abstract class BaseController {

	private WebProperties webProperties = null;

	@InitBinder
	public void initBinder(WebDataBinder binder) {// "yyyy-MM-dd HH:mm:ss"
		binder.registerCustomEditor(Date.class, new CustomDateEditor());
	}

	protected void writeJSON(HttpServletResponse response, String json) throws IOException {
		response.reset();
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(json);
	}

	protected void writeJSON(HttpServletResponse response, Object obj) throws IOException {
		response.reset();
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue));
	}

	protected void writeJSON(HttpServletRequest request, HttpServletResponse response, Body body) throws IOException {
		webProperties = webProperties == null ? SpringUtil.getBean("webProperties") : webProperties;
		Header header = new Header();
		header.setSessionId(request.getSession().getId());
		header.setCookieValue(WebHelper.getCookie(request, webProperties.getCookieName()));
		Result result = new Result();
		result.setHeader(header);
		result.setBody(body);
		response.reset();
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(JSON.toJSONString(result, SerializerFeature.WriteMapNullValue));
	}

}
