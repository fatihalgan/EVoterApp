package com.evoter.security.util;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import com.evoter.util.*;
/**
 * @author db2admin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class RSAUtil {

	public static KeyPair generateKeyPair() {
		try {
			KeyPairGenerator RSAKeyGen = KeyPairGenerator.getInstance("RSA");
			RSAKeyGen.initialize(512);
			KeyPair RSAKeyPair = RSAKeyGen.generateKeyPair();
			return RSAKeyPair;
		} catch(NoSuchAlgorithmException nsae) {
			Debug.println("Error: Cannot Locate Algorithm RSA");
		}	
		return null;
	}
	
	public static byte[] encrypt(Key plainDESKey, Key RSAKey) {
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, RSAKey);
			byte[] plainText = plainDESKey.getEncoded();
			byte[] cipherDESKey = cipher.doFinal(plainText);
			return cipherDESKey;
		} catch(NoSuchAlgorithmException nsae) {
			Debug.println("Error: Cannot Locate RSA Encryption Algorithm!");	
		} catch(NoSuchPaddingException nspe) {
			Debug.println("Error: Cannot Locate Padding Type RSA/ECB/PKCS1Padding!");	
		} catch(InvalidKeyException ike) {
			Debug.println("Error: Invalid RSA Key!");	
		} catch(BadPaddingException bpe) {
			Debug.println("Error: Bad Padding in RSA Encryption!");	
		} catch(IllegalBlockSizeException ibse) {
			Debug.println("Error: Illegal Block Size in RSA Encryption!");	
		}
		return null; 	
	}
	
	public static Key decrypt(byte[] cipherDESKey, Key RSAKey) {
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, RSAKey);
			byte[] plainText = cipher.doFinal(cipherDESKey);
			return new SecretKeySpec(plainText, "DES");
		} catch(NoSuchAlgorithmException nsae) {
			Debug.println("Error: Cannot Locate RSA Encryption Algorithm!");	
		} catch(NoSuchPaddingException nspe) {
			Debug.println("Error: Cannot Locate Padding Type RSA/ECB/PKCS1Padding!");	
		} catch(InvalidKeyException ike) {
			Debug.println("Error: Invalid RSA Key!");	
		} catch(BadPaddingException bpe) {
			Debug.println("Error: Bad Padding in RSA Encryption!" + bpe.getMessage());	
		} catch(IllegalBlockSizeException ibse) {
			Debug.println("Error: Illegal Block Size in DES Encryption!");	
		}	
		return null;
	}
	
	public static void main(String[] args) {
		try {
		KeyPair keyPair = RSAUtil.generateKeyPair();
		Key publicKey = keyPair.getPublic();
		Key privateKey = keyPair.getPrivate();
		//Key DESKey = DESUtil.generateKey();
		KeyGenerator DESKeyGen = KeyGenerator.getInstance("DES");
		DESKeyGen.init(56);
		Key DESKey = DESKeyGen.generateKey();
		System.out.println(new String(DESKey.getEncoded()));
		
		byte[] cipherText = RSAUtil.encrypt(DESKey, publicKey);
		//Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		//cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		//byte[] plainText = DESKey.getEncoded();
		//byte[] cipherText = cipher.doFinal(plainText);
		
		Key plainDESKey = RSAUtil.decrypt(cipherText, privateKey);
		//Cipher cipher2 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		//cipher2.init(Cipher.DECRYPT_MODE, privateKey);
		//plainText = cipher2.doFinal(new String(cipherText).getBytes());
		System.out.println(new String(plainDESKey.getEncoded()));
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} 		
	}

}
