package org.boooks.db.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="PAYPAL_TRANSACTION")
public class PaypalTransaction {

	@Id  
    @GeneratedValue(strategy = GenerationType.AUTO)  
	@Column(name="PAYPAL_TRANSACTION_ID", nullable=false, unique=true)
	private Long id;
	
	
	// Unique transaction ID of the payment.
	// Note: If the PaymentAction of the request was Authorization or Order, this value
	// is your AuthorizationID for use with the Authorization & Capture APIs.
	@Column(name="TRANSACTION_ID", nullable=false)
	private String transactionId;

	
	// The type of transaction Possible values:
	// l cart l express-checkout
	@Column(name="TRANSACTION_TYPE")
	private String transactionType; 
																
	// Indicates whether the payment is instant or delayed.
	// Possible values:
	// l none l echeck l instant
	@Column(name="PAYMENT_TYPE")
	private String paymentType;
	
	// Time/date stamp of payment
	@Column(name="ORDER_TIME")
	private String orderTime;
											
	// The final amount charged, including any shipping and taxes from your Merchant Profile.
	@Column(name="AMT")
	private String amt; 
											
	// A three-character currency code for one of the currencies listed in
	// PayPay-Supported Transactional Currencies. Default:
	// USD.
	@Column(name="CURRENCY_CODE")
	private String currencyCode; 
													
	// PayPal fee amount charged for the transaction
	@Column(name="FEE_AMT")
	private String feeAmt; 
													
	// Tax charged on the transaction.
	@Column(name="TAX_AMT")
	private String taxAmt; 
	
	/*
	 * ' Status of the payment: 'Completed: The payment has been
	 * completed, and the funds have been added successfully to your
	 * account balance. 'Pending: The payment is pending. See the
	 * PendingReason element for more information.
	 */
	@Column(name="PAYMENT_STATUS")
	private String paymentStatus;

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

	@Column(name="PENDING_REASON")
	private String pendingReason;

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

	@Column(name="REASON_CODE")
	private String reasonCode;
	
	/**
	 * Final Payement Amount
	 */
	@Column(name="FINAL_PAYMENT_AMOUNT")
	private String finalPaymentAmount;
	
	/**
	 * Final Payement item id
	 */
	@Column(name="FINAL_PAYMENT_ITEMID")
	private String finalPaymentItemId;
	
	@Column(name="DT_CRE", nullable=false)
	private Date dtCre;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID", nullable=false)
	private UserEntity userEntity;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(String feeAmt) {
		this.feeAmt = feeAmt;
	}

	public String getTaxAmt() {
		return taxAmt;
	}

	public void setTaxAmt(String taxAmt) {
		this.taxAmt = taxAmt;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPendingReason() {
		return pendingReason;
	}

	public void setPendingReason(String pendingReason) {
		this.pendingReason = pendingReason;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	
	public Date getDtCre() {
		return dtCre;
	}

	public void setDtCre(Date dtCre) {
		this.dtCre = dtCre;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public String getFinalPaymentAmount() {
		return finalPaymentAmount;
	}

	public void setFinalPaymentAmount(String finalPaymentAmount) {
		this.finalPaymentAmount = finalPaymentAmount;
	}

	public String getFinalPaymentItemId() {
		return finalPaymentItemId;
	}

	public void setFinalPaymentItemId(String finalPaymentItemId) {
		this.finalPaymentItemId = finalPaymentItemId;
	}
	
	
}
