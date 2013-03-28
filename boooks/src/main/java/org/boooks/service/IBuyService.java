package org.boooks.service;

import org.boooks.db.entity.Buy;
import org.boooks.db.entity.BuyPK;
import org.springframework.transaction.annotation.Transactional;

public interface IBuyService {

	@Transactional
	Buy save(Buy buy);

	@Transactional
	Buy getById(BuyPK id);

}
