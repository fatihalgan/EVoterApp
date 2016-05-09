package com.evoter.security;

import com.evoter.security.*;
import com.evoter.security.util.*;
import com.evoter.util.*;

import java.util.*;
import java.security.*;
/**
 * @author db2admin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class Test {

	private AbstractSecurityManager E_SecManager = null;
		
	private AbstractSecurityManager R_SecManager = null;
	
	private MessageWithDESKey myVote;
	
	private static int myVoterId = 1;
	/**
	 * Constructor for Test.
	 */
	public Test(String myVote) {
		super();
		E_SecManager = 	SecurityManagerFactory.getSecurityManager(
		SecurityHelper.E_Security_Manager_Class, 1);
		R_SecManager = SecurityManagerFactory.getSecurityManager(
		SecurityHelper.R_Security_Manager_Class, 1);
		this.myVote = new MessageWithDESKey(myVote.getBytes());
		KeyPair keyPair = RSAUtil.generateKeyPair();
		E_SecManager.addPublicKey(0, keyPair.getPublic());
		KeyPair keyPair2 = RSAUtil.generateKeyPair();
		R_SecManager.addPublicKey(0, keyPair.getPublic());
		this.myVote = E_SecManager.encrypt(this.myVote);
		System.out.println("E_Encrypted Message: ");
		System.out.println(new String(this.myVote.getMessage()));
		this.myVote = R_SecManager.encrypt(this.myVote);
		System.out.println("R_Encrypted Message: ");
		System.out.println(new String(this.myVote.getMessage()));
		this.myVote = R_SecManager.decrypt(this.myVote);
		System.out.println("R_Decrypted Message: ");
		System.out.println(new String(this.myVote.getMessage()));
		
	}

	public static void main(String[] args) {
		Test aTest = new Test("Yes");
	}
}
