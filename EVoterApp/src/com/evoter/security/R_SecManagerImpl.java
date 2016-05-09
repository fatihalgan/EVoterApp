package com.evoter.security;

import java.security.*;
import javax.crypto.*;
import com.evoter.util.*;
import com.evoter.security.util.*; 
import java.util.*;
import java.io.Serializable;
/**
 * @author db2admin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class R_SecManagerImpl extends AbstractSecurityManager implements Serializable {

	/**
	 * Constructor for R_SecManagerImpl.
	 */
	public R_SecManagerImpl() {
		super();
	}
	
	public MessageWithDESKey encrypt(MessageWithDESKey plainText) {
		for(int i = 0; i < publicKeyList.size(); i++) {
			plainText = new MessageWithDESKey(plainText);
			plainText.getInnerNode().setMessage(generateRandomString(
			plainText.getInnerNode().getMessage()));
			plainText.encrypt((Key)(publicKeyList.get(new Integer(i))));
			if(i == myVoterId) EncSnapShot.REncSnapShotView = plainText.getMessage();
		}
		return plainText;
	}
	
	private byte[] generateRandomString(byte[] plainText) {
		byte[] buf = new byte[10];
		for(int i = 0; i < 10; i++) {
			short c = (short)(Math.random() * 255);
			buf[i] = (byte)((char)c);
		}
		plainText = ArrayUtil.concat(plainText, buf);
		return plainText;
	}
	
	public MessageWithDESKey decrypt(MessageWithDESKey cipherText) {
			cipherText = cipherText.decrypt(privateKey);
			cipherText.setMessage(ArrayUtil.substring(0, cipherText.getMessage().length - 10, 
			cipherText.getMessage())); 
			return cipherText;	
	}
	
	public static void main(String[] args) {
		R_SecManagerImpl rSecMan = new R_SecManagerImpl();
		MessageWithDESKey msgWithKey = new MessageWithDESKey("Yes".getBytes());
		msgWithKey = rSecMan.encrypt(msgWithKey);
		msgWithKey = rSecMan.decrypt(msgWithKey);
	}

}
