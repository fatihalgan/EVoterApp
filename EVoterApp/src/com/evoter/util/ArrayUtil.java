package com.evoter.util;

/**
 * @author db2admin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ArrayUtil {
	
	public static byte[] concat(byte[] array1, byte[] array2) {
		
		byte[] resultArray = new byte[array1.length + array2.length];
		
		for(int i = 0; i < array1.length; i++) {
			resultArray[i] = array1[i]; 	
		}
		
		for(int i = array1.length; i < array1.length + array2.length; i++) {
			resultArray[i] = array2[i - array1.length];
		}
		return resultArray;	
	}
	
	public static byte[] substring(int beginindex, byte[] sourceArray) {
		
		byte[] result = new byte[sourceArray.length - beginindex];
		
		for(int i = beginindex; i < sourceArray.length; i++) {
			result[i - beginindex] = sourceArray[i];	
		}
		return result;	
	}
	
	public static byte[] substring(int beginindex, int endindex, byte[] sourceArray) {
		
		byte[] result = new byte[endindex - beginindex];
		
		for(int i = 0; i < endindex - beginindex; i++) {
			result[i] = sourceArray[beginindex + i];	
		}
		return result;	
	}
	
	public static byte[] clone(byte[] sourceArray) {
		byte[] result = new byte[sourceArray.length];
		
		for(int i = 0; i < sourceArray.length; i++) {
			result[i] = sourceArray[i];	
		}
		return result;	
	}
	
	
	public static boolean equals(byte[] array1, byte[] array2) {
		if(array1.length != array2.length) return false;
		
		for(int i = 0; i < array1.length; i++) {
			if(array1[i] != array2[i]) return false;	
		}
		
		return true;	
	}
	
	public static void main(String[] args) {
		byte[] array1 = "Yes".getBytes();
		byte[] array2 = "Sir".getBytes();
		byte[] array3 = ArrayUtil.concat(array1, array2);
		System.out.println(new String(array3));		
	}

}
