package com.evoter.security.util;

import java.io.*;
import java.security.*;
import javax.crypto.spec.*;
import com.evoter.security.util.*;
import com.evoter.util.*;
/**
 * @author db2admin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MessageWithDESKey implements Serializable {

	private byte[] message = null;
	private int keyStartPos = 0;
	private MessageWithDESKey innerNode = null;
	/**
	 * Constructor for MessageWithDESKey.
	 */
	public MessageWithDESKey(byte[] message) {
		this.message = message;
	}
	
	public MessageWithDESKey(MessageWithDESKey node) {
		this.innerNode = node;
	}
	
	public void encrypt(Key RSAKey) {
		Key DESKey = DESUtil.generateKey();
		if(this.innerNode != null) {
			this.message = DESUtil.encrypt(this.innerNode.getMessage(), DESKey);
			this.innerNode.setMessage(null);
		} else {
			this.message = DESUtil.encrypt(this.getMessage(), DESKey);
		}
		
		byte[] strDESKey = RSAUtil.encrypt(DESKey, RSAKey);
		
		this.keyStartPos = this.message.length;
		this.message = ArrayUtil.concat(this.message, strDESKey);
		
		return;	
	}
	
	public MessageWithDESKey decrypt(Key RSAKey) {
		
		Key DESKey = RSAUtil.decrypt(ArrayUtil.substring(keyStartPos, this.message), RSAKey);
		
		byte[] strMessage = DESUtil.decrypt(ArrayUtil.substring(0, keyStartPos, this.message), DESKey);
		
		if(this.innerNode != null) {
			this.innerNode.setMessage(strMessage);
			return this.innerNode;
		} else {
			this.setMessage(strMessage);
			return this;
		}
	}
	
	public byte[] getMessage() {
		return this.message;	
	}
	
	public void setMessage(byte[] message) {
		this.message = message;	
	}
	
	/**
	 * Returns the innerNode.
	 * @return MessageWithDESKey
	 */
	public MessageWithDESKey getInnerNode() {
		return innerNode;
	}
	
	public static void main(String[] args) {
		
		KeyPair keyPair = RSAUtil.generateKeyPair();
		Key publicKey = keyPair.getPublic();
		Key privateKey = keyPair.getPrivate();
		MessageWithDESKey msgwithkey = new MessageWithDESKey("Yes".getBytes());
		msgwithkey.encrypt(publicKey);
		System.out.println(new String(msgwithkey.getMessage()));
		msgwithkey.decrypt(privateKey);
		System.out.println(new String(msgwithkey.getMessage()));	
	}

}
