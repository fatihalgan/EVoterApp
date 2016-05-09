package com.evoter.events;

import com.evoter.security.util.*;
import com.evoter.security.*;
import java.util.*;

/**
 * @author db2admin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FirstPassDecryptionCompleteEvent extends EventObject {

	private Vector votesList;
	private int voterId;
	/**
	 * Constructor for FirstPassDecryptionCompleteEvent.
	 * @param arg0
	 */
	public FirstPassDecryptionCompleteEvent(Object source, Vector votesList, int voterId) {
		super(source);
		this.votesList = votesList;
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

}
