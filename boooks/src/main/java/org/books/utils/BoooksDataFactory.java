package org.books.utils;

import org.fluttercode.datafactory.impl.DataFactory;

public class BoooksDataFactory {

	public BoooksDataFactory(){
		this(true);
	}

	public DataFactory df = new DataFactory();


	public BoooksDataFactory(boolean randomize){
		if(randomize){
			df.randomize((new java.util.Random()).nextInt());
		}
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
