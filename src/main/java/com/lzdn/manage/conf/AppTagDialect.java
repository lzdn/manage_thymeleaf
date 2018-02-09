package com.lzdn.manage.conf;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

import com.lzdn.manage.utils.web.EmptyTag;
import com.lzdn.manage.utils.web.NotEmptyTag;
import com.lzdn.manage.utils.web.SecurityTag;
import com.lzdn.manage.utils.web.SelectTag;

@Component
public class AppTagDialect extends AbstractDialect {

	private static final String TAG_NAME = "app";
	private static final String TAG_NAME_SECURITY = "security";// 标签名
	private static final String TAG_NAME_EMPTY = "empty";// 标签名
	private static final String TAG_NAME_NOT_EMPTY = "notempty";// 标签名
	private static final String TAG_NAME_SELECT = "select";// 标签名
	private static final String TAG_NAME_EQUAL = "equal";// 标签名
	private static final String TAG_NAME_NOT_EQUAL = "notequal";// 标签名
	private static final String TAG_NAME_TRANS_CODE = "transcode";// 标签名
	private static final String TAG_NAME_TOKEN = "token";// 标签名
	
	@Override
	public String getPrefix() {
		return TAG_NAME;
	}
	
	@Override
    public Set<IProcessor> getProcessors() {
        final Set<IProcessor> processors = new HashSet<>();
        processors.add(new SecurityTag(TAG_NAME_SECURITY));
        processors.add(new EmptyTag(TAG_NAME_EMPTY));
        processors.add(new NotEmptyTag(TAG_NAME_NOT_EMPTY));
        processors.add(new SelectTag(TAG_NAME_SELECT));
        return processors;
    }
}
