package com.lzdn.manage.utils;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class SecurityHelper {
	

	// 解密数据
	public static String desDecrypt(String message, String key) {
		try {
			message = URLEncoder.encode(message, "utf-8");
			byte[] bytesrc = convertHexString(message);
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));

			cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

			byte[] retByte = cipher.doFinal(bytesrc);
			return new String(retByte);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}

	public static String desEncrypt(String message, String key) throws Exception {

		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

		return URLDecoder.decode(toHexString(cipher.doFinal(message.getBytes("UTF-8"))), "utf-8");
	}

	private static byte[] convertHexString(String ss) {
		byte digest[] = new byte[ss.length() / 2];
		for (int i = 0; i < digest.length; i++) {
			String byteString = ss.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}

		return digest;
	}

	private static String toHexString(byte b[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2)
				plainText = "0" + plainText;
			hexString.append(plainText);
		}

		return hexString.toString();
	}

	/**
	 * MD5加密
	 * 
	 * @param 要进行md5加密的字符串
	 * @return 返回加密后的md5串 如果异常则返回null
	 */
	public static String MD5(String md5Str) {
		return Digest(md5Str, DigestEnum.MD5);
	}

	/**
	 * SHA1加密 默认全部转换成小写 如需大写请使用重载传参数
	 * 
	 * @param sha1Str
	 * @return
	 */
	public static String SHA1(String sha1Str) {
		return SHA1(sha1Str, true);
	}

	/**
	 * SHA1加密 根据指定是否将结果转换为小写
	 * 
	 * @param sha1Str
	 * @param isToLower
	 *            是否转换小写
	 * @return
	 */
	public static String SHA1(String sha1Str, boolean isToLower) {
		String digeStr = Digest(sha1Str, DigestEnum.SHA1);
		if (digeStr != null && isToLower) {
			digeStr = digeStr.toLowerCase();
		}
		return digeStr;
	}

	enum DigestEnum {
		MD5, SHA1
	}

	/**
	 * 生成数据摘要
	 * 
	 * @param digStr
	 * @param type
	 * @return
	 */
	private static String Digest(String digStr, DigestEnum type) {
		final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = digStr.getBytes();
			// 获取md5摘要获取对象
			MessageDigest md5Dig = null;// MessageDigest.getInstance("MD5");
			// DigestEnum de=//DigestEnum.SHA1;
			if (type == DigestEnum.MD5) {
				md5Dig = MessageDigest.getInstance("MD5");
			} else if (type == DigestEnum.SHA1) {
				md5Dig = MessageDigest.getInstance("SHA-1");
			}
			byte[] md5Bytes = md5Dig.digest(btInput);
			StringBuilder sb = new StringBuilder();
			for (byte b : md5Bytes) {
				char c = hexDigits[(b >>> 4) & 0xf];
				sb.append(c);
				c = hexDigits[b & 0xf];
				sb.append(c);

			}
			return sb.toString();

		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	// 静态方法，便于作为工具类
	public static String getMd5(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			// 32位加密
			return buf.toString();
			// 16位的加密
			// return buf.toString().substring(8, 24);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static void main(String[] args) {
		try {
			String s = "13000001001";
			System.out.println(s);
			s = SecurityHelper.desEncrypt(s, "IvB2#u3$");
			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getFileMD5(File file) {
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());
		return bigInt.toString(16);
	}


	byte[] iv = { 0x30, 0x31, 0x30, 0x32, 0x30, 0x33, 0x30, 0x34, 0x30, 0x35, 0x30, 0x36, 0x30, 0x37, 0x30, 0x38 };


	/**
	 * 加密方法
	 *
	 * @param content 要加密的字符串
	 * @param keyBytes 加密密钥
	 * @return
	 */
	public byte[] aesEncrypt(byte[] content, byte[] keyBytes) {
		byte[] encryptedText = null;
		// 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
		int base = 16;
		if (keyBytes.length % base != 0) {
			int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
			byte[] temp = new byte[groups * base];
			Arrays.fill(temp, (byte) 0);
			System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
			keyBytes = temp;
		}
		// 初始化
		Security.addProvider(new BouncyCastleProvider());
		// 转化成JAVA的密钥格式
		Key key = new SecretKeySpec(keyBytes, "AES");
		try {
			// 初始化cipher  
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
			cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
			encryptedText = cipher.doFinal(content);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedText;
	}

	/**
	 * 解密方法
	 *
	 * @param encryptedData 要解密的字符串
	 * @param keyBytes 解密密钥
	 * @return
	 */
	public byte[] aesDecrypt(byte[] encryptedData, byte[] keyBytes) {
		byte[] encryptedText = null;
		// 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
		int base = 16;
		if (keyBytes.length % base != 0) {
			int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
			byte[] temp = new byte[groups * base];
			Arrays.fill(temp, (byte) 0);
			System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
			keyBytes = temp;
		}
		// 初始化
		Security.addProvider(new BouncyCastleProvider());
		// 转化成JAVA的密钥格式
		Key key = new SecretKeySpec(keyBytes, "AES");
		try {
			// 初始化cipher
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
			cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
			encryptedText = cipher.doFinal(encryptedData);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("IV：" + new String(iv));
		return encryptedText;
	}


}
