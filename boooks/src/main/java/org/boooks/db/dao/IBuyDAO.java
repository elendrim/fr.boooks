package org.boooks.db.dao;

import org.boooks.db.entity.Buy;
import org.boooks.db.entity.BuyPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBuyDAO extends JpaRepository<Buy, BuyPK> { 
	
}