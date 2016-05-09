package com.evoter.events;

import java.rmi.registry.*;
import java.rmi.*;

import com.evoter.*;
/**
 * @author db2admin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class StartVoterNode extends Thread{
	
	private VoterNodeImpl voterNode = null;
	EventService remoteService = null; 
	public StartVoterNode() {
		try {
			String rmiEventServer = "rmi://193.140.248.175:1099/EventService";
			remoteService = (EventService)Naming.lookup(rmiEventServer);
			voterNode = new VoterNodeImpl("Yes");
			voterNode.setEventService(remoteService);
			remoteService.addVoterNodeListener(voterNode);
		} catch(Exception e) {
			e.printStackTrace();	
		}
			
	}
	
	public void run() {
		try {
			sleep(30000);
			voterNode.start();
		} catch(RemoteException re) {
			System.out.println(re.getMessage());	
		} catch(Exception e) {
			e.printStackTrace();	
		}	
	}
	
	public static void main(String[] args) {
		StartVoterNode start = new StartVoterNode();
		start.start();	
	}

}
