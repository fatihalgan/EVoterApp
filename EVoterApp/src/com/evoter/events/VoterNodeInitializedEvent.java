package com.evoter.events;

import java.util.EventObject;
import java.security.*;

/**
 * @author db2admin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class VoterNodeInitializedEvent extends EventObject {
	
	private Key EPublicKey;
	private Key RPublicKey;
	private Key signatureKey;
	private int voterId;
	
	public VoterNodeInitializedEvent(Object source, Key EPublicKey, Key RPublicKey, Key signatureKey,int voterId) {
		super(source);
		this.EPublicKey = EPublicKey;
		this.RPublicKey = RPublicKey;
		this.signatureKey = signatureKey;
		this.voterId = voterId;	
	}
	
	
	/**
	 * Returns the publicKey.
	 * @return Key
	 */
	public Key getEPublicKey() {
		return EPublicKey;
	}

	/**
	 * Sets the publicKey.
	 * @param publicKey The publicKey to set
	 */
	public void setEPublicKey(Key publicKey) {
		this.EPublicKey = publicKey;
	}

	/**
	 * Returns the voterId.
	 * @return int
	 */
	public int getVoterId() {
		return voterId;
	}

	/**
	 * Sets the voterId.
	 * @param voterId The voterId to set
	 */
	public void setVoterId(int voterId) {
		this.voterId = voterId;
	}

	/**
	 * Returns the rPublicKey.
	 * @return Key
	 */
	public Key getRPublicKey() {
		return RPublicKey;
	}

	/**
	 * Sets the rPublicKey.
	 * @param rPublicKey The rPublicKey to set
	 */
	public void setRPublicKey(Key rPublicKey) {
		RPublicKey = rPublicKey;
	}

	/**
	 * Returns the signatureKey.
	 * @return Key
	 */
	public Key getSignatureKey() {
		return signatureKey;
	}

	/**
	 * Sets the signatureKey.
	 * @param signatureKey The signatureKey to set
	 */
	public void setSignatureKey(Key signatureKey) {
		this.signatureKey = signatureKey;
	}

}
