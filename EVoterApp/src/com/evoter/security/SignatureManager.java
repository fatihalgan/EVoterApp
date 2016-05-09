package com.evoter.security;

import java.io.Serializable;
import java.security.*;
import javax.crypto.*;
import com.evoter.util.*;
import com.evoter.security.util.*;
import java.util.*;
/**
 * @author db2admin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SignatureManager implements Serializable {

	protected PrivateKey privateKey;
	protected Hashtable publicKeyList;
	protected int myVoterId;
	/**
	 * Constructor for SignatureManager.
	 */
	public SignatureManager() {
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
	
	public byte[] sign(MessageWithDESKey plainText) {
		return SignatureUtil.sign(plainText.getMessage(), this.getPrivateKey());	
	}
	
	public boolean verify(MessageWithDESKey plainText, byte[] signature) {
		return SignatureUtil.verify(plainText.getMessage(), signature, this.getMyPublicKey());
	} 

}
