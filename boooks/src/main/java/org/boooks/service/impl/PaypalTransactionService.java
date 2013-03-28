package org.boooks.service.impl;

import org.boooks.db.dao.IPaypalTransactionDAO;
import org.boooks.db.entity.PaypalTransaction;
import org.boooks.service.IPaypalTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PaypalTransactionService implements IPaypalTransactionService {

	@Autowired
	private IPaypalTransactionDAO paypalTransactionDAO;
	

	@Override
	@Transactional(readOnly=false)
	public PaypalTransaction save(PaypalTransaction paypalTransaction) {
		return paypalTransactionDAO.save(paypalTransaction);
	}

}
