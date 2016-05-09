package com.evoter.events;

import java.util.EventObject;

import com.evoter.security.util.MessageWithDESKey;

/**
 * @author db2admin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FirstPassEncryptionCompleteEvent extends EventObject {
	
	private MessageWithDESKey message;
	private int voterId;
	
	public FirstPassEncryptionCompleteEvent(Object source, MessageWithDESKey message, int voterId) {
		super(source);
		this.message = message;
		this.voterId = voterId;
	}
	
	

	/**
	 * Returns the message.
	 * @return MessageWithDESKey
	 */
	public MessageWithDESKey getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 * @param message The message to set
	 */
	public void setMessage(MessageWithDESKey message) {
		this.message = message;
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

}
