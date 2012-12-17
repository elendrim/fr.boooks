package org.boooks.web.controller;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.paypal.dg.PaypalFunctions;

@Controller
@RequestMapping("paypal")
public class PaypalController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PaypalController.class);
	
	@RequestMapping(value="/checkout", method = RequestMethod.POST)
	public String checkout(Model model, Principal principal) throws UnsupportedEncodingException {
		
		// Use "request" to read incoming HTTP headers (e.g. cookies)
		// and HTML form data (e.g. data the user entered and submitted)

		// Use "response" to specify the HTTP response line and headers
		// (e.g. specifying the content type, setting cookies).

		/*
		 *  The paymentAmount is the total value of ' the purchase. ' ' 
		 * TODO:  Enter the total Payment Amount within the quotes. ' example :
		 * paymentAmount = "15.00"; 
		 */
		String paymentAmount = "1.00";

		/*
		 * '------------------------------------ ' The returnURL is the location
		 * where buyers return to when a ' payment has been succesfully
		 * authorized. ' ' This is set to the value entered on the Integration
		 * Assistant '------------------------------------
		 */

		String returnURL = "http://localhost:8082/boooks/orderconfirm";

		/*
		 * '------------------------------------ ' The cancelURL is the location
		 * buyers are sent to when they hit the ' cancel button during
		 * authorization of payment during the PayPal flow ' ' This is set to
		 * the value entered on the Integration Assistant
		 * '------------------------------------
		 */
		String cancelURL = "http://localhost:8082/boooks/paypal/cancel.htm";

		/*
		 * '------------------------------------ ' The items hashmap contains
		 * the details of each item '------------------------------------
		 * TODO: change "item name" to desired item name
		 */

		Map<String,String> item = new HashMap<String,String>();
		item.put("name", "item name");
		item.put("amt", paymentAmount);
		item.put("qty", "1");

		/*
		 * '------------------------------------ ' Calls the SetExpressCheckout
		 * API call ' ' The SetExpressCheckout function is defined in the file
		 * PayPalFunctions.java,
		 * '-------------------------------------------------
		 */
		PaypalFunctions ppf = new PaypalFunctions();
		Map<String, String> nvp = ppf.setExpressCheckout(paymentAmount, returnURL, cancelURL, item);
		String strAck = nvp.get("ACK").toString();
		if (strAck != null && strAck.equalsIgnoreCase("Success")) {

			// ' Redirect to paypal.com
			String redirectURL = "https://www.sandbox.paypal.com/incontext?token="+ nvp.get("TOKEN").toString();
			return "redirect:" + redirectURL;
			
		} else {
			// Display a user friendly Error on the page using any of the
			// following error information returned by PayPal

			String errorCode = nvp.get("L_ERRORCODE0");
			String errorShortMsg = nvp.get("L_SHORTMESSAGE0");
			String errorLongMsg = nvp.get("L_LONGMESSAGE0");
			String errorSeverityCode = nvp.get("L_SEVERITYCODE0");
			model.addAttribute("errorCode", errorCode);
			model.addAttribute("errorShortMsg", errorShortMsg);
			model.addAttribute("errorLongMsg", errorLongMsg);
			model.addAttribute("errorSeverityCode", errorSeverityCode);
			return "paypal/errorpage";
		}
		
	}
	
	
	@RequestMapping(value="/cancel", method = RequestMethod.GET)
	public String cancel(@RequestParam String token, Model model, Principal principal) throws UnsupportedEncodingException {
		LOGGER.debug("paypal token:"+ token);
		return "paypal/cancel";
	}
	
	
	@RequestMapping(value="/orderconfirm", method = RequestMethod.GET)
	public String orderconfirm(@RequestParam(value="token") String token, @RequestParam(value="PayerID") String payerId, Model model, Principal principal, ServletRequest request) {
		/*
		 * '------------------------------------ ' This is the landing page
		 * after authorizing payment on PayPal lightbox ' Get the token
		 * parameter from Querystring '------------------------------------
		 */
		
	
		if (token != null) {
	
			// IMPORTANT NOTE: Please import Class paypalfunctions if not in the
			// same package level.
			// import paypalfunctions;
	
			/*
			 * '------------------------------------ ' this step is required to
			 * get ToatalPaymentAmount to make DoExpressCheckout API call, '
			 * this step is required only if you are not storing the
			 * SetExpressCheckout API call's request values in you database. '
			 * ------------------------------------
			 */
			PaypalFunctions ppf = new PaypalFunctions();
			Map<String, String> nvp = ppf.getPaymentDetails(token);
		
			String strAck = nvp.get("ACK").toString();
			String finalPaymentAmount = null;
			if (strAck != null
					&& (strAck.equalsIgnoreCase("Success") || strAck
							.equalsIgnoreCase("SuccessWithWarning"))) {
				/*
				 * '------------------------------------ ' The paymentAmount is
				 * the total value of the purchase '
				 */
				finalPaymentAmount = nvp.get("AMT").toString();
			}
			String serverName = request.getServerName();
	
			/*
			 * '------------------------------------ ' Calls the
			 * DoExpressCheckoutPayment API call ' ' The ConfirmPayment function
			 * is defined in the file PayPalFunctions.java ' that should be
			 * included at the top of this file.
			 * '-------------------------------------------------
			 */
	
			nvp = ppf.confirmPayment(token, payerId, finalPaymentAmount, serverName);
			
			
			strAck = nvp.get("ACK").toString();
			
			
			if (strAck != null
					&& (strAck.equalsIgnoreCase("Success") || strAck
							.equalsIgnoreCase("SuccessWithWarning"))) {
				/*
				 * TODO: Proceed with desired action after the payment (ex:
				 * start download, start streaming, Add coins to the game.. etc)
				 * '
				 * *************************************************************
				 * ******************************************************* ' '
				 * THE PARTNER SHOULD SAVE THE KEY TRANSACTION RELATED
				 * INFORMATION LIKE ' transactionId & orderTime ' IN THEIR OWN
				 * DATABASE ' AND THE REST OF THE INFORMATION CAN BE USED TO
				 * UNDERSTAND THE STATUS OF THE PAYMENT '
				 * '**********************
				 * ***************************************
				 * *******************************************************
				 */
	
				// Unique transaction ID of the payment.
				// Note: If the PaymentAction of the request was Authorization or Order, this value
				// is your AuthorizationID for use with the Authorization & Capture APIs.
				String transactionId = nvp.get("PAYMENTINFO_0_TRANSACTIONID");

				
				// The type of transaction Possible values:
				// l cart l express-checkout
				String transactionType = nvp.get("PAYMENTINFO_0_TRANSACTIONTYPE"); 
																			
				// Indicates whether the payment is instant or delayed.
				// Possible values:
				// l none l echeck l instant
				String paymentType = nvp.get("PAYMENTINFO_0_PAYMENTTYPE");
				
				// Time/date stamp of payment
				String orderTime = nvp.get("PAYMENTINFO_0_ORDERTIME");
														
				// The final amount charged, including any shipping and taxes from your Merchant Profile.
				String amt = nvp.get("PAYMENTINFO_0_AMT"); 
														
				// A three-character currency code for one of the currencies listed in
				// PayPay-Supported Transactional Currencies. Default:
				// USD.
				String currencyCode = nvp.get("PAYMENTINFO_0_CURRENCYCODE"); 
																
				// PayPal fee amount charged for the transaction
				String feeAmt = nvp.get("PAYMENTINFO_0_FEEAMT"); 
																
				// Tax charged on the transaction.
				String taxAmt = nvp.get("PAYMENTINFO_0_TAXAMT"); 
				
				/*
				 * ' Status of the payment: 'Completed: The payment has been
				 * completed, and the funds have been added successfully to your
				 * account balance. 'Pending: The payment is pending. See the
				 * PendingReason element for more information.
				 */
	
				String paymentStatus = nvp.get("PAYMENTINFO_0_PAYMENTSTATUS");
	
				/*
				 * 'The reason the payment is pending: ' none: No pending reason
				 * ' address: The payment is pending because your customer did
				 * not include a confirmed shipping address and your Payment
				 * Receiving Preferences is set such that you want to manually
				 * accept or deny each of these payments. To change your
				 * preference, go to the Preferences section of your Profile. '
				 * echeck: The payment is pending because it was made by an
				 * eCheck that has not yet cleared. ' intl: The payment is
				 * pending because you hold a non-U.S. account and do not have a
				 * withdrawal mechanism. You must manually accept or deny this
				 * payment from your Account Overview. ' multi-currency: You do
				 * not have a balance in the currency sent, and you do not have
				 * your Payment Receiving Preferences set to automatically
				 * convert and accept this payment. You must manually accept or
				 * deny this payment. ' verify: The payment is pending because
				 * you are not yet verified. You must verify your account before
				 * you can accept this payment. ' other: The payment is pending
				 * for a reason other than those listed above. For more
				 * information, contact PayPal customer service.
				 */
	
				String pendingReason = nvp.get("PAYMENTINFO_0_PENDINGREASON");
	
				/*
				 * 'The reason for a reversal if TransactionType is reversal: '
				 * none: No reason code ' chargeback: A reversal has occurred on
				 * this transaction due to a chargeback by your customer. '
				 * guarantee: A reversal has occurred on this transaction due to
				 * your customer triggering a money-back guarantee. '
				 * buyer-complaint: A reversal has occurred on this transaction
				 * due to a complaint about the transaction from your customer.
				 * ' refund: A reversal has occurred on this transaction because
				 * you have given the customer a refund. ' other: A reversal has
				 * occurred on this transaction due to a reason not listed
				 * above.
				 */
	
				String reasonCode = nvp.get("PAYMENTINFO_0_REASONCODE");
	
				// Add javascript to close Digital Goods frame. You may want to
				// add more javascript code to
				// display some info message indicating status of purchase in
				// the parent window
				return "paypal/orderconfirm";
				
			} else {
				// Display a user friendly Error on the page using any of the
				// following error information returned by PayPal
	
				String errorCode = nvp.get("L_ERRORCODE0");
				String errorShortMsg = nvp.get("L_SHORTMESSAGE0");
				String errorLongMsg = nvp.get("L_LONGMESSAGE0");
				String errorSeverityCode = nvp.get("L_SEVERITYCODE0");
	
				model.addAttribute("errorCode", errorCode);
				model.addAttribute("errorShortMsg", errorShortMsg);
				model.addAttribute("errorLongMsg", errorLongMsg);
				model.addAttribute("errorSeverityCode", errorSeverityCode);
				
				return "paypal/errorpage";
			}
		}
		
		return null;
	}
}
