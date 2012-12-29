package org.boooks.datageneration;

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
	@Autowired private MainCommentGenerator mainCommentGen;
	@Autowired private SubCommentGenerator subCommentGen;
	
	@Test
	public void generateAll() throws Exception {
		
		System.out.println(subCommentGen.create(dg));
		
	}

}
