package org.boooks.service.impl;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.boooks.service.IPaypalFunctions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaypalFunctions implements IPaypalFunctions {
	
	@Value("${paypal.apiusername}") 
	private String gv_APIUserName;
	
	@Value("${paypal.apipassword}")
	private String gv_APIPassword;
	
	@Value("${paypal.apisignature}")
	private String gv_APISignature;

	@Value("${paypal.apiendpoint}")
	private String gv_APIEndpoint;
	
	@Value("${paypal.version}")
	private String gv_Version;
	
	@Value("${paypal.url}")
	private String gv_PaypalUrl;
	
	@Value("${paypal.dgurl}")
	private String gv_PaypalDgUrl;

	public PaypalFunctions() {
	}

	/*********************************************************************************
	 * SetExpressCheckout: Function to perform the SetExpressCheckout API call
	 * 
	 * Inputs: paymentAmount: Total value of the purchase currencyCodeType:
	 * Currency code value the PayPal API paymentType: 'Sale' for Digital Goods
	 * returnURL: the page where buyers return to after they are done with the
	 * payment review on PayPal cancelURL: the page where buyers return to when
	 * they cancel the payment review on PayPal
	 * 
	 * Output: Returns a HashMap object containing the response from the server.
	 * @throws UnsupportedEncodingException 
	 *********************************************************************************/
	@Override
	public Map<String,String> setExpressCheckout(String paymentAmount, String returnURL,
			String cancelURL, Map<String,String> item) throws UnsupportedEncodingException {

		/*
		 * '------------------------------------ ' The currencyCodeType and
		 * paymentType ' are set to the selections made on the Integration
		 * Assistant '------------------------------------
		 */

		String currencyCodeType = "EUR";
		String paymentType = "Sale";

		/*
		 * Construct the parameter string that describes the PayPal payment the
		 * varialbes were set in the web form, and the resulting string is
		 * stored in $nvpstr
		 */
		String nvpstr = "&PAYMENTREQUEST_0_AMT=" + paymentAmount
				+ "&PAYMENTREQUEST_0_PAYMENTACTION=" + paymentType
				+ "&RETURNURL=" + URLEncoder.encode(returnURL, "UTF-8") + "&CANCELURL="
				+ URLEncoder.encode(cancelURL, "UTF-8")
				+ "&PAYMENTREQUEST_0_CURRENCYCODE=" + currencyCodeType
				+ "&REQCONFIRMSHIPPING=0" + "&NOSHIPPING=1"
				+ "&L_PAYMENTREQUEST_0_NAME0=" + item.get("name")
				+ "&L_PAYMENTREQUEST_0_NUMBER0=" + item.get("itemid")
				+ "&L_PAYMENTREQUEST_0_AMT0=" + item.get("amt")
				+ "&L_PAYMENTREQUEST_0_QTY0=" + item.get("qty")
				+ "&L_PAYMENTREQUEST_0_ITEMCATEGORY0=Digital";

		/*
		 * Make the call to PayPal to get the Express Checkout token If the API
		 * call succeded, then redirect the buyer to PayPal to begin to
		 * authorize payment. If an error occured, show the resulting errors
		 */

		Map<String, String> nvp = httpcall("SetExpressCheckout", nvpstr);

		String strAck = nvp.get("ACK");
		if ("Success".equalsIgnoreCase(strAck)) {
			return nvp;
		} else if ( "Failure".equalsIgnoreCase(strAck) ){
			String message = nvp.get("L_LONGMESSAGE0");
			throw new RuntimeException("Failed to SetExpressCheckout :"+ message);
		}

		return null;
	}

	/*********************************************************************************
	 * GetShippingDetails: Function to perform the GetExpressCheckoutDetails API
	 * call
	 * 
	 * Inputs: None
	 * 
	 * Output: Returns a HashMap object containing the response from the server.
	 *********************************************************************************/
	@Override
	public Map<String, String> getPaymentDetails(String token) {
		/*
		 * Build a second API request to PayPal, using the token as the ID to
		 * get the details on the payment authorization
		 */

		String nvpstr = "&TOKEN=" + token;

		/*
		 * Make the API call and store the results in an array. If the call was
		 * a success, show the authorization details, and provide an action to
		 * complete the payment. If failed, show the error
		 */

		Map<String, String> nvp = httpcall("GetExpressCheckoutDetails", nvpstr);
		String strAck = nvp.get("ACK");
		
		if (strAck != null
				&& (strAck.equalsIgnoreCase("Success") || strAck
						.equalsIgnoreCase("SuccessWithWarning"))) {
		
			return nvp;
		}
		return null;
	}

	/*********************************************************************************
	 * ConfirmPayment: Function to perform the DoExpressCheckoutPayment API call
	 * 
	 * Inputs: None
	 * 
	 * Output: Returns a HashMap object containing the response from the server.
	 *********************************************************************************/
	@Override
	public Map<String, String> confirmPayment(String token, 
			String payerID, 
			String finalPaymentAmount, 
			String serverName) {

		/*
		 * '------------------------------------ ' The currencyCodeType and
		 * paymentType ' are set to the selections made on the Integration
		 * Assistant '------------------------------------
		 */
		String currencyCodeType = "EUR";
		String paymentType = "Sale";

		/*
		 * '----------------------------------------------------------------------------
		 * '---- Use the values stored in the session from the previous SetEC
		 * call
		 * '----------------------------------------------------------------------------
		 */
		String nvpstr = "&TOKEN=" + token + "&PAYERID=" + payerID
				+ "&PAYMENTREQUEST_0_PAYMENTACTION=" + paymentType + "&PAYMENTREQUEST_0_AMT="
				+ finalPaymentAmount;

		nvpstr = nvpstr + "&PAYMENTREQUEST_0_CURRENCYCODE=" + currencyCodeType + "&IPADDRESS="
				+ serverName;

		/*
		 * Make the call to PayPal to finalize payment If an error occured, show
		 * the resulting errors
		 */
		Map<String, String> nvp = httpcall("DoExpressCheckoutPayment", nvpstr);
		String strAck = nvp.get("ACK");
		if (strAck != null
				&& (strAck.equalsIgnoreCase("Success") || strAck
						.equalsIgnoreCase("SuccessWithWarning"))) {
			return nvp;
		}
		return null;

	}

	/*********************************************************************************
	 * httpcall: Function to perform the API call to PayPal using API signature @
	 * methodName is name of API method. @ nvpStr is nvp string. returns a NVP
	 * string containing the response from the server.
	 *********************************************************************************/
	private Map<String, String> httpcall(String methodName, String nvpStr) {

		String version = "2.3";
		String agent = "Mozilla/4.0";
		String respText = "";
		Map<String, String> nvp = null; // lhuynh not used?

		// deformatNVP( nvpStr );
		String encodedData = "METHOD=" + methodName + "&VERSION=" + gv_Version
				+ "&PWD=" + gv_APIPassword + "&USER=" + gv_APIUserName
				+ "&SIGNATURE=" + gv_APISignature + nvpStr;

		try {
			URL postURL = new URL(gv_APIEndpoint);
			HttpURLConnection conn = (HttpURLConnection) postURL
					.openConnection();

			// Set connection parameters. We need to perform input and output,
			// so set both as true.
			conn.setDoInput(true);
			conn.setDoOutput(true);

			// Set the content type we are POSTing. We impersonate it as
			// encoded form data
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("User-Agent", agent);

			// conn.setRequestProperty( "Content-Type", type );
			conn.setRequestProperty("Content-Length",
					String.valueOf(encodedData.length()));
			conn.setRequestMethod("POST");

			// get the output stream to POST to.
			DataOutputStream output = new DataOutputStream(
					conn.getOutputStream());
			output.writeBytes(encodedData);
			output.flush();
			output.close();

			// Read input from the input stream.
			DataInputStream in = new DataInputStream(conn.getInputStream());
			int rc = conn.getResponseCode();
			if (rc != -1) {
				BufferedReader is = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				String _line = null;
				while (((_line = is.readLine()) != null)) {
					respText = respText + _line;
				}
				nvp = deformatNVP(respText);
			}
			return nvp;
		} catch (IOException e) {
			// handle the error here
			return null;
		}
	}

	/*********************************************************************************
	 * deformatNVP: Function to break the NVP string into a HashMap pPayLoad is
	 * the NVP string. returns a HashMap object containing all the name value
	 * pairs of the string.
	 * @throws UnsupportedEncodingException 
	 *********************************************************************************/
	private Map<String, String> deformatNVP(String pPayload) throws UnsupportedEncodingException {
		Map<String, String> nvp = new HashMap<String, String>();
		StringTokenizer stTok = new StringTokenizer(pPayload, "&");
		while (stTok.hasMoreTokens()) {
			StringTokenizer stInternalTokenizer = new StringTokenizer(
					stTok.nextToken(), "=");
			if (stInternalTokenizer.countTokens() == 2) {
				String key = URLDecoder.decode(stInternalTokenizer.nextToken(), "UTF-8");
				String value = URLDecoder.decode(stInternalTokenizer.nextToken(), "UTF-8");
				nvp.put(key.toUpperCase(), value);
			}
		}
		return nvp;
	}

	@Override
	public String getPaypalDgUrl() {
		return gv_PaypalDgUrl;
	}

	// end class
}