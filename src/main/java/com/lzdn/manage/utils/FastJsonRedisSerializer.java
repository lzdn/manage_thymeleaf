package com.lzdn.manage.utils;

import java.nio.charset.Charset;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.alibaba.fastjson.JSON;

public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {

	private static final Charset charset = Charset.forName("UTF-8");

	private Class<T> clazz;

	public FastJsonRedisSerializer(Class<T> clazz) {
		this.clazz = clazz;
	}

	public FastJsonRedisSerializer() {
	}

	/**
	 * 序列化
	 *
	 * @param t
	 * @return
	 * @throws SerializationException
	 */
	public byte[] serialize(T t) throws SerializationException {
		if (t == null) {
			return new byte[0];
		}
		return JSON.toJSONString(t).getBytes(charset);// 这里可以根据需要选择相应的方法
	}

	/**
	 * 反序列化
	 *
	 * @param bytes
	 * @return
	 * @throws SerializationException
	 */
	@SuppressWarnings("unchecked")
	public T deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null || bytes.length <= 0) {
			return null;
		}
		String str = new String(bytes, charset);
		if (clazz == null) {
			return (T) JSON.parse(str);
		}
		return (T) JSON.parseObject(str, clazz);
	}

}
