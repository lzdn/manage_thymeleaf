package com.lzdn.manage.utils.web;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.element.AbstractElementProcessor;

public class SelectTag extends AbstractElementProcessor {

	private static final int PRECEDENCE = 10000;// 优先级

	public SelectTag(String elementName) {
		super(elementName);
	}

	@Override
	protected ProcessorResult processElement(final Arguments arguments, final Element element) {
		return ProcessorResult.OK;
	}

	@Override
	public int getPrecedence() {
		return PRECEDENCE;
	}

}
