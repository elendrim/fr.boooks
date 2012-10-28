package org.boooks.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.books.utils.BoooksDataFactory;
import org.boooks.db.dao.IBookDAO;
import org.boooks.db.dao.IMainCommentDAO;
import org.boooks.db.entity.*;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public interface IMainCommentService {

	@Transactional
	List<MainComment> getByBook(Book book);
	
	@Transactional
	List<MainComment> getByUser(UserEntity user);
	
	@Transactional
	MainComment save(MainComment mainComment);
	
	@Transactional
	MainComment update(MainComment mainComment);

	@Transactional
	MainComment createDummyMainComment(UserEntity user, Book book, BoooksDataFactory df)
			throws Exception;
	
}
