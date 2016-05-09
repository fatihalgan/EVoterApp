package com.evoter.exceptions;

/**
 * @author db2admin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MyVoteNotFoundException extends Exception {

	/**
	 * Constructor for MyVoteNotFoundException.
	 */
	public MyVoteNotFoundException() {
		super();
	}

	/**
	 * Constructor for MyVoteNotFoundException.
	 * @param arg0
	 */
	public MyVoteNotFoundException(String arg0) {
		super(arg0);
	}

	/**
	 * Constructor for MyVoteNotFoundException.
	 * @param arg0
	 * @param arg1
	 */
	public MyVoteNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * Constructor for MyVoteNotFoundException.
	 * @param arg0
	 */
	public MyVoteNotFoundException(Throwable arg0) {
		super(arg0);
	}

}
