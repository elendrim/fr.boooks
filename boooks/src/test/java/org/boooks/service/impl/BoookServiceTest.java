package org.boooks.service.impl;

import static org.junit.Assert.assertEquals;

import org.boooks.db.entity.Book;
import org.boooks.service.IBookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/application-context-test.xml" })
@TransactionConfiguration
@Transactional
public class BoookServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private IBookService bookService;
	
	@Test
	public void testGetBookDbById() {
		
		Book book = bookService.getBookDbById(5l);
		
		assertEquals(5l, book.getId().longValue());
		
	}

}
