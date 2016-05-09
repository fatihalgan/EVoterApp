package com.evoter.security;

import com.evoter.security.util.MessageWithDESKey;
import java.io.Serializable;
import java.security.*;
import com.evoter.util.*;
/**
 * @author db2admin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class E_SecManagerImpl extends AbstractSecurityManager implements Serializable {

	/**
	 * Constructor for E_SecManagerImpl.
	 */
	public E_SecManagerImpl() {
		super();
	}
	
	public static void main(String[] args) {
		E_SecManagerImpl eSecMan = new E_SecManagerImpl();
		MessageWithDESKey msgWithKey = new MessageWithDESKey("Yes".getBytes());
		msgWithKey = eSecMan.encrypt(msgWithKey);
		msgWithKey = eSecMan.decrypt(msgWithKey);
		System.out.println(new String(msgWithKey.getMessage()));	
	}
	
	public MessageWithDESKey encryptResults(MessageWithDESKey plainText, int i) {
		plainText.encrypt((Key)(publicKeyList.get(new Integer(i))));
		Debug.println("Encrypting with public Key Of Voter: " + i);
		return plainText;	
	}

}
