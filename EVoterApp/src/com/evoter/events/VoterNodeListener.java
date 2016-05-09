package com.evoter.events;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.EventListener;

/**
 * @author db2admin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface VoterNodeListener extends EventListener, Remote {
	
	public void receivePublicKeys(VoterNodeInitializedEvent evt) throws RemoteException;
	
	public Integer getVoterId() throws RemoteException;
	
	public void receiveFirstPassEncryptedVote(FirstPassEncryptionCompleteEvent evt) 
	 	throws RemoteException;
	 	
	public void receiveFirstPassDecryptedVote(FirstPassDecryptionCompleteEvent evt) 
		throws RemoteException;
		
	public void receiveSecondPassDecryptedVote(SecondPassDecryptionStartEvent evt) 
		throws RemoteException;
		
	public void receiveResults(ResultsReadyEvent evt) throws RemoteException; 
	
}
