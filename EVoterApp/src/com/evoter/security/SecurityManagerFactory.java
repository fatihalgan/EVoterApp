package com.evoter.security;

import com.evoter.util.Debug;

public class SecurityManagerFactory {
	
	public static AbstractSecurityManager getSecurityManager(String managerType, int voterId) {
		try {
			AbstractSecurityManager manager = (AbstractSecurityManager)Class.forName(
				managerType).newInstance();
			manager.init(voterId);	
			return manager;
		} catch(ClassNotFoundException cne) {
			Debug.println("Error! Class Not Found " + managerType + ".");	
		} catch(IllegalAccessException iae) {
			Debug.println("Illegal Access Exception while creating SecurityManager: " 
				+ iae.getMessage());
		} catch(InstantiationException ie) {
			Debug.println("Error while instantiating SecurityManager: " + ie.getMessage());
		}
		return null;
			
	}

}

