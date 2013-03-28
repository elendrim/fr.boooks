package org.boooks.service;

import org.boooks.db.entity.PaypalTransaction;
import org.springframework.transaction.annotation.Transactional;

public interface IPaypalTransactionService {

	@Transactional
	PaypalTransaction save(PaypalTransaction paypalTransaction);

}
