package com.lzdn.manage.conf;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Order(-5)
//@Configuration
public class LogAspect {

	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

	/**
	 * 定义一个切入点. 解释下：
	 *
	 * ~ 第一个 * 代表任意修饰符及任意返回值. ~ 第二个 * 定义在web包或者子包 ~ 第三个 * 任意方法 ~ .. 匹配任意数量的参数.
	 */
	/*
	 * @Pointcut("execution(* com.wedo.stream.service..*.*(..))") public void
	 * logPointcut() { }
	 * 
	 * @org.aspectj.lang.annotation.Around("logPointcut()") public Object
	 * doAround(ProceedingJoinPoint joinPoint) throws Throwable { long start =
	 * System.currentTimeMillis(); try { Object result = joinPoint.proceed(); long
	 * end = System.currentTimeMillis(); logger.error("+++++around " + joinPoint +
	 * "\tUse time : " + (end - start) + " ms!"); return result;
	 * 
	 * } catch (Throwable e) { long end = System.currentTimeMillis();
	 * logger.error("+++++around " + joinPoint + "\tUse time : " + (end - start) +
	 * " ms with exception : " + e.getMessage()); throw e; }
	 * 
	 * }
	 */

	/**
	 * 定义一个切入点. 解释下：
	 *
	 * ~ 第一个 * 代表任意修饰符及任意返回值. ~ 第二个 * 任意包名 ~ 第三个 * 代表任意方法. ~ 第四个 * 定义在web包或者子包 ~ 第五个 * 任意方法 ~ .. 匹配任意数量的参数.
	 * 
	 */
	@Pointcut("execution(public * com.lzdn.manage.service..*.*(..))")
	public void webLog() {
	}

	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) {
		// 接收到请求，记录请求内容
		logger.info("WebLogAspect.doBefore()");
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		// 记录下请求内容
		logger.info("URL : " + request.getRequestURL().toString());
		logger.info("HTTP_METHOD : " + request.getMethod());
		logger.info("IP : " + request.getRemoteAddr());
		logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
				+ joinPoint.getSignature().getName());
		logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
		// 获取所有参数方法一：
		Enumeration<String> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			System.out.println(paraName + ": " + request.getParameter(paraName));
		}
		//Signature signature = joinPoint.getSignature();
		//MethodSignature methodSignature = (MethodSignature) signature;  
		//Method method = methodSignature.getMethod();  
		//method.getAnnotation(annotationClass);
	}

	@AfterReturning("webLog()")
	public void doAfterReturning(JoinPoint joinPoint) {
		// 处理完请求，返回内容
		logger.info("WebLogAspect.doAfterReturning()");
	}
}
