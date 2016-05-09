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
public class ResultsReadyEvent extends EventObject {

	private Vector votesList;
	private int voterId;
	
	/**
	 * Constructor for ResultsReadyEvent.
	 * @param arg0
	 */
	public ResultsReadyEvent(Object arg0, Vector votesList, int voterId) {
		super(arg0);
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
