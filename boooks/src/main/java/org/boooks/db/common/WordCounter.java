package org.boooks.db.common;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.BreakIterator;
import java.util.Locale;

import org.apache.commons.io.IOUtils;
import org.apache.tika.Tika;
import org.apache.tika.language.LanguageIdentifier;
import org.apache.tika.metadata.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WordCounter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WordCounter.class);
	
	public static int countWord(byte[] buf) {
		int count = 0;
		InputStream input = null;
		try {
			input = new ByteArrayInputStream(buf);
			Metadata metadata = new Metadata();
			
			String content = new Tika().parseToString(input, metadata);
			
			LanguageIdentifier li = new LanguageIdentifier(content);
			Locale locale = null;
		    if (li.isReasonablyCertain()) {
		    	locale = new Locale(li.getLanguage());
		    } else {
		    	locale = Locale.US;
		    }
			
			BreakIterator boundary = BreakIterator.getWordInstance(locale);
			count = countWords(content, boundary);
			
		} catch(Exception e) {
			LOGGER.error("Error during Tika Metadata content count", e);
		} finally {
			IOUtils.closeQuietly(input);
		}
		
		return count;
		
	}
	
	private static int countWords(String target, BreakIterator wordIterator) {

	    wordIterator.setText(target);
	    int start = wordIterator.first();
	    int end = wordIterator.next();

	    int i = 0;
	    while (end != BreakIterator.DONE) {
	        String word = target.substring(start,end);
	        if (Character.isLetterOrDigit(word.charAt(0))) {
//	            System.out.println(word);
	        	i++;
	        }
	        start = end;
	        end = wordIterator.next();
	    }
	    
	    return i;
	}

}
