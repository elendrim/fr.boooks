package org.boooks.web.taglib;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FunctionUtil {
	
	public static String urlEncode(String value) throws UnsupportedEncodingException {
	    return URLEncoder.encode(value, "UTF-8");
	}
	
	public static String md5(String plainText) throws NoSuchAlgorithmException {
		
		MessageDigest mdAlgorithm = MessageDigest.getInstance("MD5");
		mdAlgorithm.update(plainText.getBytes());

		byte[] digest = mdAlgorithm.digest();
		StringBuffer hexString = new StringBuffer();

		for (int i = 0; i < digest.length; i++) {
		    plainText = Integer.toHexString(0xFF & digest[i]);

		    if (plainText.length() < 2) {
		        plainText = "0" + plainText;
		    }

		    hexString.append(plainText);
		}

		return hexString.toString();
	}
}

