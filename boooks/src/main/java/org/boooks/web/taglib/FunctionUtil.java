package org.boooks.web.taglib;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class FunctionUtil {
	
	public static String urlEncode(String value) throws UnsupportedEncodingException {
	    return URLEncoder.encode(value, "UTF-8");
	}
}

