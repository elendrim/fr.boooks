package org.boooks.datageneration;

import java.util.ArrayList;
import java.util.List;

import org.boooks.db.entity.Book;
import org.boooks.db.entity.MainComment;
import org.boooks.db.entity.SubComment;
import org.boooks.db.entity.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*
 * Launch this class with JUnit to fill the database
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/application-context-gen.xml" })
public class Generate {

	private DataGenerator dg = new DataGenerator(true);

	
	@Autowired private UserEntityGenerator userGen;
	@Autowired private BookGenerator bookGen;
	@Autowired private MainCommentGenerator mainCommentGen;
	@Autowired private SubCommentGenerator subCommentGen;
	
	
	
	@Test
	public void generateOne() throws Exception {
		
		UserEntity u = userGen.create(dg);
		Book b = bookGen.create(dg, u, null, null);
		MainComment mc = mainCommentGen.create(dg, u, b);
		SubComment sc = subCommentGen.create(dg, u, mc);
		
	}
	
	
	@Test
	public void generateAll() throws Exception {
		
		int nb_books = 20;
		int nb_users = 1000;
		int nb_authors = 10;
		int nb_comments = 10; 
		
		List<UserEntity> users = new ArrayList<UserEntity>(nb_users);
		for(int i = 0; i < nb_users ; i ++) 	users.add(userGen.create(dg));
			
		List<UserEntity> authors = new ArrayList<UserEntity>(nb_authors);
		for(int i = 0; i < nb_authors ; i ++) 	authors.add(userGen.create(dg));
		
		List<Book> books = new ArrayList<Book>(nb_books);
		for(int i = 0; i < nb_books ; i ++)
			books.add(bookGen.create(dg, dg.getItem(authors), null, null));

		List<MainComment> mainComments = new ArrayList<MainComment>();
		for(Book b : books){
			int cur_main = dg.getNumberAround(nb_comments, 20);
			for(int i = 0; i < cur_main ; i ++){
				mainComments.add(mainCommentGen.create(dg, dg.getItem(users), b));
				
			}
		}
		
		List<SubComment> subComment = new ArrayList<SubComment>();
		for(MainComment m : mainComments){
			int cur_sub = dg.getNumberAround(nb_comments / 2, 70);
			for(int i = 0 ; i < cur_sub ; i++){
				subComment.add(subCommentGen.create(dg, dg.getItem(users), m));
			}
		}
		
	}

}
