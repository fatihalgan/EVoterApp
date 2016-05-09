package com.evoter.security.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

import com.evoter.util.Debug;

/**
 * @author db2admin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SignatureUtil {
	
	public static byte[] sign(byte[] plainText, Key RSAKey) {
		try {
			Signature sig = Signature.getInstance("MD5WithRSA");
			sig.initSign((PrivateKey)RSAKey);
			sig.update(plainText);
			byte[] signature = sig.sign();
			return signature;
		} catch(NoSuchAlgorithmException nsae) {
			Debug.println("Error: Cannot Locate Algorithm MD5WithRSA");	
		} catch(InvalidKeyException ike) {
			Debug.println("Error: Invalid Key...");	
		} catch(SignatureException se) {
			Debug.println("Error: Signature Exception...");	
		}
		return null;
	}
	
	public static boolean verify(byte[] plainText, byte[] signature, Key RSAKey) {
		try {
			Signature sig = Signature.getInstance("MD5WithRSA");
			sig.initVerify((PublicKey)RSAKey);
			try {
				sig.update(plainText);
				if(sig.verify(signature)) return true;
			} catch(SignatureException se) {
				Debug.println("Signature Failed...");
			}
		} catch(NoSuchAlgorithmException nsae) {
			Debug.println("Error: Cannot Locate Algorithm MD5WithRSA");
		} catch(InvalidKeyException ike) {
			Debug.println("Error: Invalid Key...");	
		}
		return false;
	}

}
