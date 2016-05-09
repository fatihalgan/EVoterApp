package com.evoter.events;

import java.rmi.*;

/**
 * @author db2admin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface EventService extends Remote {
	
	public void addVoterNodeListener(VoterNodeListener listener) throws RemoteException;
	
	public void removeVoterNodeListener(VoterNodeListener listener) throws RemoteException;
	
	public void fireVoterNodeInitializedEvent(VoterNodeInitializedEvent evt) throws RemoteException;
	
	public void fireFirstPassEncryptionCompleteEvent(FirstPassEncryptionCompleteEvent evt)
		throws RemoteException;
		
	public void fireFirstPassDecryptionCompleteEvent(FirstPassDecryptionCompleteEvent evt)
		throws RemoteException;
		
	public void fireSecondPassDecryptionStartEvent(SecondPassDecryptionStartEvent evt)
		throws RemoteException;
		
	public void fireResultsReadyEvent(ResultsReadyEvent evt) throws RemoteException;

}
