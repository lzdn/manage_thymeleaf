package com.lzdn.manage.utils.web;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.NestableNode;
import org.thymeleaf.dom.Node;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.element.AbstractElementProcessor;

public class NotEmptyTag extends AbstractElementProcessor {

	private static final int PRECEDENCE = 10000;// 优先级

	public NotEmptyTag(String elementName) {
		super(elementName);
	}

	@Override
	protected ProcessorResult processElement(final Arguments arguments, final Element element) {
		final boolean visible = isNotEmpty(arguments, element);
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

	protected boolean isNotEmpty(final Arguments arguments, final Element element) {
		final String attributeValue = element.getAttributeValue("value");
		if (StringUtils.isNotEmpty(attributeValue)) {
			return true;
		}
		return false;
	}

}
