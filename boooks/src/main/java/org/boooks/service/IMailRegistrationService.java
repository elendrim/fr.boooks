package org.boooks.service;

import org.boooks.db.entity.TempKey;
import org.boooks.db.entity.UserEntity;

public interface IMailRegistrationService {
	
	void register(final UserEntity user, final TempKey tempkey);

}
