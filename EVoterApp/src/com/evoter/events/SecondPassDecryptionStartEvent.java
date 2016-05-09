package com.evoter.events;

import java.util.EventObject;
import java.util.Vector;

/**
 * @author db2admin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SecondPassDecryptionStartEvent extends EventObject {

	private Vector votesList;
	private int voterId;
	private Vector signatureList;
	/**
	 * Constructor for SecondPassDecryptionStartEvent.
	 * @param arg0
	 */
	public SecondPassDecryptionStartEvent(Object arg0, Vector votesList, Vector signatureList, int voterId) {
		super(arg0);
		this.votesList = votesList;
		this.signatureList = signatureList;
		this.voterId = voterId;
	}

	/**
	 * Returns the votesList.
	 * @return Vector
	 */
	public Vector getVotesList() {
		return votesList;
	}

	/**
	 * Sets the votesList.
	 * @param votesList The votesList to set
	 */
	public void setVotesList(Vector votesList) {
		this.votesList = votesList;
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
	 * Returns the signatureList.
	 * @return Vector
	 */
	public Vector getSignatureList() {
		return signatureList;
	}

	/**
	 * Sets the signatureList.
	 * @param signatureList The signatureList to set
	 */
	public void setSignatureList(Vector signatureList) {
		this.signatureList = signatureList;
	}

}
