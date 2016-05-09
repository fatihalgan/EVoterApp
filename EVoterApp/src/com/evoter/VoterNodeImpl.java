package com.evoter;

import java.util.*;
import java.security.*;
import javax.crypto.*;
import java.rmi.server.*;
import java.rmi.*;
import java.io.Serializable;

import com.evoter.events.*;
import com.evoter.security.*;
import com.evoter.security.util.*;
import com.evoter.util.*;
import com.evoter.exceptions.*;
/**
 * @author db2admin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class VoterNodeImpl extends UnicastRemoteObject implements VoterNodeListener, Serializable {

	private static ResourceBundle voterConfig = ResourceBundle.getBundle("evoter_config");
	
	private static int myVoterId = Integer.parseInt(voterConfig.getString("MY_VOTER_ID"));
	
	private static int totalVoterCount = Integer.parseInt(voterConfig.getString("VOTER_COUNT"));
	
	private Hashtable votersList = new Hashtable();
	
	private Vector votesList = new Vector();
	
	private AbstractSecurityManager E_SecManager = null;
		
	private AbstractSecurityManager R_SecManager = null;
	
	private EventService eventService;
	
	private MessageWithDESKey myVote;
	
	private SignatureManager sigManager;
	
	private Hashtable signatureList = new Hashtable();
	
	private void initVotersList() {
		votersList = new Hashtable();
		String strVoters = voterConfig.getString("OTHER_VOTERS_IPS");
		StringTokenizer tok = new StringTokenizer(strVoters, ";");
		int i = 0;
		while(tok.hasMoreTokens()) {
			
			if(i != Integer.parseInt(voterConfig.getString("MY_VOTER_ID"))) {
				String IPAddr = tok.nextToken();
				votersList.put(new Integer(i), IPAddr);	
			} 
			i++;
		}
	}
	
	public VoterNodeImpl(String myVote) throws RemoteException {
		initVotersList();
		E_SecManager = 	SecurityManagerFactory.getSecurityManager(
		SecurityHelper.E_Security_Manager_Class, myVoterId);
		R_SecManager = SecurityManagerFactory.getSecurityManager(
		SecurityHelper.R_Security_Manager_Class, myVoterId);
		this.myVote = new MessageWithDESKey(myVote.getBytes());
		sigManager = new SignatureManager();
		sigManager.init(myVoterId);
	}
	
	public void receivePublicKeys(VoterNodeInitializedEvent evt) throws RemoteException {
		if(evt.getVoterId() == myVoterId) return;
		Debug.println("Received Public Keys Of Voter: " + evt.getVoterId());
		E_SecManager.addPublicKey(evt.getVoterId(), evt.getEPublicKey());
		R_SecManager.addPublicKey(evt.getVoterId(), evt.getRPublicKey());
		sigManager.addPublicKey(evt.getVoterId(), evt.getSignatureKey());
		Debug.println("Received Signature Keys Of Voter: " + evt.getVoterId());
		if((E_SecManager.getPublicKeyList().size() == totalVoterCount) &&
			(R_SecManager.getPublicKeyList().size() == totalVoterCount)) {
			MessageWithDESKey tempVote = new MessageWithDESKey(ArrayUtil.clone(myVote.getMessage()));
			tempVote = E_SecManager.encrypt(tempVote);
			tempVote = R_SecManager.encrypt(tempVote);
			eventService.fireFirstPassEncryptionCompleteEvent(
				new FirstPassEncryptionCompleteEvent(this, tempVote, myVoterId));		
		}
	
	}
	
	public Integer getVoterId() throws RemoteException {
		return new Integer(myVoterId);	
	}
	
	public void start() throws RemoteException {
		VoterNodeInitializedEvent evt = new VoterNodeInitializedEvent(this,
		E_SecManager.getMyPublicKey(), R_SecManager.getMyPublicKey(), 
		sigManager.getMyPublicKey(), myVoterId);
		Debug.println("My Public Keys Are Ready!..Voter ID = " + evt.getVoterId());
		eventService.fireVoterNodeInitializedEvent(evt);
	}
	
	 public void setEventService(EventService eventService) throws RemoteException {
	 	this.eventService = eventService;	
	 }
	 
	 public void receiveFirstPassEncryptedVote(FirstPassEncryptionCompleteEvent evt) 
	 	throws RemoteException {
		Debug.println("Received First Pass Encrypted Vote Of Voter " + evt.getVoterId());
		votesList.add(evt.getMessage());
		if(votesList.size() == totalVoterCount) {
			for(int i = 0; i < votesList.size(); i++) {
				MessageWithDESKey msg = (MessageWithDESKey)votesList.get(i);
				System.out.println("Voter ID: " + i);
				System.out.println("Encrypted Vote: " + new String(msg.getMessage()) + "/n/n/n");
			}
			try {
				firstPassCheckForMyVote();
			} catch(MyVoteNotFoundException mvnfe) {
				Debug.println(mvnfe.getMessage());
				System.exit(0);
			}
			firstPassDecrypt();
		}	 		
	 }
	
	private void firstPassCheckForMyVote() throws MyVoteNotFoundException {
		boolean control = false;
		Enumeration e = votesList.elements();
		while(e.hasMoreElements()) {
			MessageWithDESKey msg = (MessageWithDESKey)(e.nextElement());
			if(ArrayUtil.equals(msg.getMessage() ,EncSnapShot.REncSnapShotView)) {
				control = true;
				break;
			}	
		}
		if(!control) throw new MyVoteNotFoundException("The vote of Voter " + myVoterId +
		" is not found.");
		return;	
	}
	
	
	private void secondPassCheckForMyVote() throws MyVoteNotFoundException {
		boolean control = false;
		Enumeration e = votesList.elements();
		while(e.hasMoreElements()) {
			MessageWithDESKey msg = (MessageWithDESKey)(e.nextElement());
			if(ArrayUtil.equals(msg.getMessage(), EncSnapShot.EEncSnapShotView)) {
				control = true;
				break;
			}
		}
		if(!control) throw new MyVoteNotFoundException("The vote of Voter " + myVoterId +
		" is not found.");
		return;	
	}
	
	public void firstPassDecrypt() throws RemoteException {
		Vector v = new Vector();
		Enumeration e = votesList.elements();
		while(e.hasMoreElements()) {
			MessageWithDESKey msg = (MessageWithDESKey)(e.nextElement());
			msg = R_SecManager.decrypt(msg);
			v.add(msg);	
		}
		eventService.fireFirstPassDecryptionCompleteEvent(
			new FirstPassDecryptionCompleteEvent(this, v, myVoterId));	
	}
	
	public void receiveFirstPassDecryptedVote(FirstPassDecryptionCompleteEvent evt) 
		throws RemoteException {
		votesList = evt.getVotesList();
		Debug.println("Received First Pass Decrypted Vote Of Voter " + evt.getVoterId());
		Vector v = new Vector();
		
		try {
				firstPassCheckForMyVote();
		} catch(MyVoteNotFoundException mvnfe) {
				Debug.println(mvnfe.getMessage());
				System.exit(0);
		}
		Enumeration e = votesList.elements();
		while(e.hasMoreElements()) {
			MessageWithDESKey msg = (MessageWithDESKey)(e.nextElement());
			msg = R_SecManager.decrypt(msg);
			Debug.println("Decrypted Message Of Voter: " + new String(msg.getMessage()));
			Debug.println("\n\n");
			v.add(msg);
		}
		votesList.clear();
		votesList = v;
		if(myVoterId == 0) {
			v = new Vector();
			e = votesList.elements();
			while(e.hasMoreElements()) {
				MessageWithDESKey msg = (MessageWithDESKey)(e.nextElement());
				Debug.println("E-Encrypted Message with Public Key Of a Voter is: ");
				Debug.println(new String(msg.getMessage()));
				byte[] sig = sigManager.sign(msg);
				Debug.println("Signature Of The E-Encrypted Message is: ");
				Debug.println(new String(sig));
				v.add(sig); 
			}
			eventService.fireSecondPassDecryptionStartEvent(
				new SecondPassDecryptionStartEvent(this, votesList, v, myVoterId));
		} else {
			eventService.fireFirstPassDecryptionCompleteEvent(
				new FirstPassDecryptionCompleteEvent(this, votesList, myVoterId));
		}	
	}
	
	public void receiveSecondPassDecryptedVote(SecondPassDecryptionStartEvent evt)
		throws RemoteException {
		
		signatureList.put(new Integer(evt.getVoterId()), evt.getSignatureList());
				
		if(evt.getVotesList() != null) {
			
			votesList = evt.getVotesList();
			Debug.println("Received Second Pass Decrypted Vote Of Voter " + evt.getVoterId());
			try {
				secondPassCheckForMyVote();
			} catch(MyVoteNotFoundException mvnfe) {
				Debug.println(mvnfe.getMessage());
				System.exit(0);
			}
		
		
			Vector v = new Vector();
			Enumeration e = votesList.elements();
			while(e.hasMoreElements()) {
				MessageWithDESKey msg = (MessageWithDESKey)e.nextElement();
				msg = E_SecManager.decrypt(msg);
				Debug.println("Decrypted Message Of Voter: " + new String(msg.getMessage()));
				Debug.println("\n\n");
				v.add(msg);
			}
			votesList.clear();
			votesList = v;
			if(myVoterId != 0) {
				v = new Vector();
				e = votesList.elements();
				while(e.hasMoreElements()) {
					MessageWithDESKey msg = (MessageWithDESKey)(e.nextElement());
					byte[] sig = sigManager.sign(msg);
					v.add(sig); 
				}
			
				eventService.fireSecondPassDecryptionStartEvent(
					new SecondPassDecryptionStartEvent(this, votesList, v, myVoterId));
			}
		}
		
		if((signatureList.size() == totalVoterCount) && (myVoterId == 0)) {
			eventService.fireResultsReadyEvent(new ResultsReadyEvent(
				this, votesList, myVoterId));	
		}
	}
	
	public void receiveResults(ResultsReadyEvent evt) throws RemoteException {
		votesList = evt.getVotesList();
		Debug.println("Received the published results...");
		try {
			checkResults();
		} catch(SignatureCheckFailedException scfe) {
			Debug.println(scfe.getMessage());
			System.exit(0);
		}
		Iterator i = votesList.iterator();
		while(i.hasNext()) {
			MessageWithDESKey vote = (MessageWithDESKey)(i.next());
			Debug.println("Result: " + vote.getMessage());
		}			
	}
	
	private void checkResults() throws SignatureCheckFailedException {
		
		for(int i = 0; i < E_SecManager.getPublicKeyList().size(); i++ ) {
			myVote = ((E_SecManagerImpl)E_SecManager).encryptResults(myVote, i);
			boolean control = false;
			Vector signatures = (Vector)(signatureList.get(new Integer(myVoterId)));	
			for(int k = 0; k < signatures.size(); k++) {
				byte[] signature = (byte[])(signatures.elementAt(k));
				control = sigManager.verify(myVote, signature);
			}
			if(!control) throw new SignatureCheckFailedException("The last signature " +
			"check failed... Somebody has changed my Vote During Second Pass");	
		}
		return;
		
	}
	
}
