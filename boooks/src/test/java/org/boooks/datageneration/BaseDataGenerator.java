package org.boooks.datageneration;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.fluttercode.datafactory.impl.DataFactory;

//from http://www.andygibson.net/blog/article/generate-test-data-with-datafactory/
//copy some functions from : https://github.com/swung/datafactory

/**
 * The goal of this class is to have basic data generation
 * The class DataFactory is final, which don't fit my goals
 * (To have more complex gererations datas)
 */
public class BaseDataGenerator {

	public BaseDataGenerator(){
		this(false);
	}

	protected DataFactory df = new DataFactory();


	public BaseDataGenerator(boolean randomize){
		if(randomize){
			df.randomize((new java.util.Random()).nextInt());
		}
	}


	//functions from DataFactory, because it don't accept inheritance
	public void toto(){   } 
	public <T> T getItem(List<T> items) { return df.getItem(items); }
	public <T> T getItem(List<T> items, int probability) { return df.getItem(items, probability) ;}
	public <T> T getItem(List<T> items, int probability, T defaultItem) { return df.getItem(items, probability, defaultItem); }
	public int getItem(int[] items) { return df.getItem( ArrayUtils.toObject(items) ); }
	public int getItem(int[] items, int probability) { return df.getItem( ArrayUtils.toObject(items), probability); } 
	public int getItem(int[] items, int probability, int defaultItem) { return df.getItem( ArrayUtils.toObject(items), probability, defaultItem); } 
	public <T> T getItem(T[] items) { return df.getItem(items); }
	public <T> T getItem(T[] items, int probability) { return df.getItem(items, probability); } 
	public <T> T getItem(T[] items, int probability, T defaultItem) { return df.getItem(items, probability, defaultItem); } 
	public boolean chance(int chance) { return df.chance(chance); }
	public String getStreetName() { return df.getStreetName(); }
	public String getStreetSuffix() {  return df.getStreetSuffix(); }
	public String getCity() { return df.getCity(); }
	public char getRandomChar() { return df.getRandomChar(); } 
	public String getRandomChars(int length) { return df.getRandomChars(length); }
	public String getRandomChars(int minLength, int maxLength) { return df.getRandomChars(minLength, maxLength); }
	public Date getDate(int year, int month, int day) { return df.getDate(year, month, day); }
	public Date getDate(Date baseDate, int minDaysFromDate, int maxDaysFromDate) { return df.getDate(baseDate, minDaysFromDate, maxDaysFromDate); }
	public Date getDateBetween(Date minDate, Date maxDate) { return df.getDateBetween(minDate, maxDate); }
	public Date getBirthDate() { return df.getBirthDate(); }
	public String getFirstName(){ return df.getFirstName(); }
	public String getName(){ return df.getName(); }
	public String getLastName(){ return df.getLastName(); }
	public String getAddress(){ return df.getAddress(); }
	public String getAddressLine2(int probability){ return df.getAddressLine2(probability); }
	public String getAddressLine2(int probability, String defaultValue){ return df.getAddressLine2(probability, defaultValue); }
	public String getAddressLine2(){ return df.getAddressLine2(); }
	public int getNumber(){ return df.getNumber(); }
	public int getNumberUpTo(int max){ return df.getNumberUpTo( max); }
	public int getNumberBetween(int min, int max){ return df.getNumberBetween( min,  max); }
	public String getRandomText(int length){ return df.getRandomText( length); }
	public String getRandomText(int minLength, int maxLength){ return df.getRandomText( minLength,  maxLength); }
	public String getRandomWord(){ return df.getRandomWord(); }
	public String getRandomWord(int length){ return df.getRandomWord( length); }
	public String getRandomWord(int length, boolean exactLength){ return df.getRandomWord( length,  exactLength); }
	public String getRandomWord(int minLength, int maxLength){ return df.getRandomWord( minLength,  maxLength); }
	public String getSuffix(int chance){ return df.getSuffix(chance); }
	public String getPrefix(int chance){ return df.getPrefix(chance); }
	public String getNumberText(int digits){ return df.getNumberText(digits); }
	public String getBusinessName(){ return df.getBusinessName(); }
	public String getEmailAddress(){ return df.getEmailAddress(); }

	public String getRandomUnicode() {
		byte[] str = new byte[2];
		int chr = df.getNumberBetween(0x51A5, 0x51A5 + 0x4E00);
		str[1] = (byte) ((chr & 0xFF00) >> 8);
		str[0] = (byte) (chr & 0xFF);
		try {
			return new String(str, "UTF-16LE");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public String getRandomUnicodes(int length) { return this.getRandomUnicodes(length, length); }
	public String getRandomUnicodes(int minLength, int maxLength) {
		StringBuilder sb = new StringBuilder(maxLength);

		int length = minLength;
		if (maxLength != minLength) {
			length = df.getNumberBetween(minLength, maxLength);
		}
		while (length > 0) {
			sb.append(getRandomUnicode());
			length--;
		}
		return sb.toString();
	}

	
	public byte[] getRandomByte(int min, int max){
		return getRandomByte( getNumberBetween(min, max) );
	}	
	
	public byte[] getRandomByte(int size){
		byte[] res = new byte[size];
		(new java.util.Random()).nextBytes(res);
		return res;
	}

	public byte getRandomByte(){
		return getRandomByte(1)[0];
	}

	public int getNumberAround(int nb_comments, int percent) {
		int min = (nb_comments * (100 - percent)) / 100;
		int max = (nb_comments * (100 + percent)) / 100;
		
		return this.getNumberBetween(min, max);
	}

}
