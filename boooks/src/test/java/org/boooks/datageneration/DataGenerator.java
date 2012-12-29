package org.boooks.datageneration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;

public class DataGenerator extends BaseDataGenerator {

	public DataGenerator(){super();}	
	public DataGenerator(boolean b) {super(b);}
	
	public String getUniqueEmailAddress(){
		String random = RandomStringUtils.randomAlphanumeric(5);
		return random + this.getEmailAddress();
	}

	public Boolean getBoolean(){
		return this.chance(50);
	}

	public Date getDate(String s) throws ParseException{
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");

		try {
			return sdf1.parse(s);
		} catch (ParseException e) {
			return sdf2.parse(s);
		}


	}

	//sometimes, we need unig values, everytime
	private static Integer uniqifier;
	private static Long dateRun;

	static {
		 dateRun = new Date().getTime();
		 uniqifier = 0;
	}

	public String getUniqString(){
		uniqifier += 1;
		return dateRun.toString() + uniqifier.toString();
	}

	public Long getUnigLong(){
		uniqifier += 1;
		return (dateRun * 1000) + uniqifier;	
	}
	

	protected String[] action = {
			"danse à",				"et le crime de",				"au mytère de",
			"raconte la guerre de", "se la raconte à",				"au temps de",
			"champion, sauf à",		"prisonier de",					"rentre chez lui :",
			"se retrouve à",		"trouve le bohneur à",			", sa romance à",
			": la grande migration de", ": j'ai trouvé des aliens, ils sont à ",
			"et le monstre de",
	};
	
	
	public String getBoookTitle(){		
		return df.getName() + " " + df.getItem(action) + " " + df.getCity();
	}
	
	protected String[] endSentence = {".", "?", "!", "..."};

	public String getSentence(int nbSentence){
		StringBuilder sb = new StringBuilder();
		for(int numSentence=0;numSentence<nbSentence;numSentence++){
			sb.append(getText(2, 15));
			sb.append( this.df.getItem(endSentence));
		}
		
		return sb.toString();		
	}
	
	
	public String getText(int nbWords){
		StringBuilder sb = new StringBuilder(); 
		for(int i = 0;i<nbWords;i++){
			sb.append( df.getRandomWord() );
			if(i != nbWords - 1) sb.append(" ");
		}
		return sb.toString();
	}
	
	public String getText(int nbWordsMin, int nbWordsMax){
		return getText(this.df.getNumberBetween(nbWordsMin, nbWordsMax));
	}
	
	
	
}
