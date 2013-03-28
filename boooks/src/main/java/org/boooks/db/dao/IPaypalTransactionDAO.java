package org.boooks.db.dao;

import org.boooks.db.entity.PaypalTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPaypalTransactionDAO extends JpaRepository<PaypalTransaction, Long> {

}
