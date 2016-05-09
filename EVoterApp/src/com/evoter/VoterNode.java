package com.evoter;

import java.rmi.*;
import java.io.*;
import java.security.*;
import com.evoter.events.*;
/**
 * @author db2admin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface VoterNode extends Remote, Serializable {

	public Key getEPublicKey() throws RemoteException;
	
	public Key getRPublicKey() throws RemoteException;
	
	public void setEventService(EventService eventService) throws RemoteException;
}
