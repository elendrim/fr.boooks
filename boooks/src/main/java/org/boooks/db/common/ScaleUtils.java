package org.boooks.db.common;

public class ScaleUtils {
	
	public static double determineImageScale(int sourceWidth, int sourceHeight, int targetWidth, int targetHeight) {
		double scalex = (double) targetWidth / sourceWidth;
		double scaley = (double) targetHeight / sourceHeight;
		return Math.min(scalex, scaley);
	}
	
	public static double imageScale(int source, int target) {
		return (double) target / source;
	}

}
