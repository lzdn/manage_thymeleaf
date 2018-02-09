package com.lzdn.manage.utils.web;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.util.CollectionUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.NestableNode;
import org.thymeleaf.dom.Node;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.element.AbstractElementProcessor;
import org.thymeleaf.spring4.context.SpringWebContext;

import com.lzdn.manage.conf.WebProperties;
import com.lzdn.manage.domain.core.Role;
import com.lzdn.manage.domain.core.User;
/**
 * 权限校验Tag
 * @author lzdn
 */
public class SecurityTag extends AbstractElementProcessor {

	private static final int PRECEDENCE = 10000;// 优先级

	public SecurityTag(String elementName) {
		super(elementName);
	}

	@Override
	protected ProcessorResult processElement(final Arguments arguments, final Element element) {
		final boolean visible = checkSecurity(arguments, element);
		final NestableNode parent = element.getParent();
		if (!visible) {
			element.clearChildren();
			parent.removeChild(element);
			return ProcessorResult.OK;
		}else {
			final List<Node> nodeList = element.getChildren();
			parent.removeChild(element);
			if(!CollectionUtils.isEmpty(nodeList)) {
				nodeList.forEach(n->{
					parent.addChild(n);
				});
			}
			return ProcessorResult.OK;
		}
	}

	@Override
	public int getPrecedence() {
		return PRECEDENCE;
	}

	protected boolean checkSecurity(final Arguments arguments, final Element element) {
		String value = element.getAttributeValue("value");
		SpringWebContext swc = (SpringWebContext) arguments.getContext();
		HttpSession session = swc.getHttpServletRequest().getSession();
		WebProperties webProperties = (WebProperties)swc.getApplicationContext().getBean("webProperties");
		if(session.getAttribute(webProperties.getSessionName())!=null) {
			User user = (User) session.getAttribute(webProperties.getSessionName());
			List<Role> roles = user.getRoles();
			if(!CollectionUtils.isEmpty(roles)) {
				Iterator<Role> role = roles.iterator();
				while(role.hasNext()) {
					Role r = role.next();
					if(!CollectionUtils.isEmpty(r.getRights())) {
						if(r.getRights().stream().anyMatch((rt)->rt.getRightUrl().equals(value))) {
							return true;
						}
					}
					
				}
			}
		}
		return false;
	}

}
