package org.boooks.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface IPaypalFunctions {

	Map<String, String> setExpressCheckout(String paymentAmount, String returnURL, String cancelURL, Map<String, String> item) throws UnsupportedEncodingException;

	Map<String, String> getPaymentDetails(String token);

	Map<String, String> confirmPayment(String token, String payerId, String finalPaymentAmount, String serverName);
	
	String getPaypalDgUrl();

}
