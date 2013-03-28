package org.boooks.service.impl;

import org.boooks.db.dao.IBuyDAO;
import org.boooks.db.entity.Buy;
import org.boooks.db.entity.BuyPK;
import org.boooks.service.IBuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BuyService implements IBuyService {

	@Autowired
	private IBuyDAO buyDAO;
	

	@Override
	@Transactional(readOnly=false)
	public Buy save(Buy buy) {
		return buyDAO.save(buy);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Buy getById(BuyPK id) {
		return buyDAO.findOne(id);
	}

}
