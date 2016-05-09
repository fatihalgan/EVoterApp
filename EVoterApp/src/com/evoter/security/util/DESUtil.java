package com.evoter.security.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

import com.evoter.util.Debug;
/**
 * @author db2admin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class DESUtil {

	public static Key generateKey() {
		try {
			KeyGenerator DESKeyGen = KeyGenerator.getInstance("DES");
			DESKeyGen.init(56);
			return DESKeyGen.generateKey();
		} catch(NoSuchAlgorithmException nsae) {
			Debug.println("Error: Cannot Locate DES Encryption Algorithm!");		
		}
		return null;
	}
	
	public static byte[] encrypt(byte[] plainText, Key DESKey) {
		try {
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, DESKey);
			byte[] cipherText = cipher.doFinal(plainText);
			return cipherText;
		} catch(NoSuchAlgorithmException nsae) {
			Debug.println("Error: Cannot Locate DES Encryption Algorithm!");	
		} catch(NoSuchPaddingException nspe) {
			Debug.println("Error: Cannot Locate Padding Type DES/ECB/PKCS5Padding!");	
		} catch(InvalidKeyException ike) {
			Debug.println("Error: Invalid DES Key!");	
		} catch(BadPaddingException bpe) {
			Debug.println("Error: Bad Padding in DES Encryption!");	
		} catch(IllegalBlockSizeException ibse) {
			Debug.println("Error: Illegal Block Size in DES Encryption!");	
		}
		return null;	
	}
	
	public static byte[] decrypt(byte[] cipherText, Key DESKey) {
		try {
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, DESKey);
			byte[] plainText = cipher.doFinal(cipherText);
			return plainText;
		} catch(NoSuchAlgorithmException nsae) {
			Debug.println("Error: Cannot Locate DES Decryption Algorithm!");	
		} catch(NoSuchPaddingException nspe) {
			Debug.println("Error: Cannot Locate Padding Type DES/ECB/PKCS5Padding!");	
		} catch(InvalidKeyException ike) {
			Debug.println("Error: Invalid DES Key!");	
		} catch(BadPaddingException bpe) {
			Debug.println("Error: Bad Padding in DES Decryption!");	
		} catch(IllegalBlockSizeException ibse) {
			Debug.println("Error: Illegal Block Size in DES Decryption!");	
		}
		return null;	
	}
	
	public static void main(String[] args) {
		byte[] msg = "Yes".getBytes();
		Key key = DESUtil.generateKey();
		byte[] cipherText = DESUtil.encrypt(msg, key); 
		System.out.println(new String(cipherText));
		byte[] plainText = DESUtil.decrypt(cipherText, key);
		System.out.println(new String(plainText));
	}
	
}
