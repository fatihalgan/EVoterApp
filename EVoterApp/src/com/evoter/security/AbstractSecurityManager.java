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
public abstract class AbstractSecurityManager implements Serializable {

	protected PrivateKey privateKey;
	protected Hashtable publicKeyList;
	protected int myVoterId; 
	/**
	 * Constructor for AbstractSecurityManager.
	 */
	public AbstractSecurityManager() {
		super();
	}
	public void init(int voterId) {
		this.myVoterId = voterId;
		KeyPair keyPair = RSAUtil.generateKeyPair();
		this.publicKeyList = new Hashtable();
		this.publicKeyList.put(new Integer(voterId), keyPair.getPublic());
		this.privateKey = keyPair.getPrivate();
	}

	/**
	 * Returns the privateKey.
	 * @return PrivateKey
	 */
	public PrivateKey getPrivateKey() {
		return privateKey;
	}
	
	public void addPublicKey(int voterId, Key publicKey) {
		publicKeyList.put(new Integer(voterId), publicKey);
	}
	
	public void removePublicKey(int voterId) {
		publicKeyList.remove(new Integer(voterId));	
	}
	
	public MessageWithDESKey encrypt(MessageWithDESKey plainText) {
		
		for(int i = 0; i < publicKeyList.size(); i++) {
			if(i != 0) plainText = new MessageWithDESKey(plainText);
			
			plainText.encrypt((Key)(publicKeyList.get(new Integer(i))));
			if(i == myVoterId) EncSnapShot.EEncSnapShotView = plainText.getMessage();
			Debug.println("Encrypting with Public Key Of Voter: " + i);
		}
		return plainText;
	}
	
	public MessageWithDESKey decrypt(MessageWithDESKey cipherText) {
		cipherText = cipherText.decrypt(privateKey);
		return cipherText;
	}
	
	public Key getMyPublicKey() {
		return (Key)(publicKeyList.get(new Integer(myVoterId)));	
	}
	

	/**
	 * Returns the publicKeyList.
	 * @return Hashtable
	 */
	public Hashtable getPublicKeyList() {
		return publicKeyList;
	}

}
