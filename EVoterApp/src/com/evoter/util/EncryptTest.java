package com.evoter.util;

import com.evoter.security.*;
import com.evoter.security.util.*;
import java.security.*;
import javax.crypto.*;

/**
 * @author db2admin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class EncryptTest {


	public static void main(String[] args) {
		
		MessageWithDESKey myVote = new MessageWithDESKey("Yes".getBytes());
		E_SecManagerImpl ESecManager = new E_SecManagerImpl();
		R_SecManagerImpl RSecManager = new R_SecManagerImpl();
		myVote = ESecManager.encrypt(myVote);
		System.out.println("E - Encrypted Message: " + new String(myVote.getMessage()));
		myVote = RSecManager.encrypt(myVote);
		System.out.println("R - Encrypted Message: " + myVote);
		myVote = RSecManager.decrypt(myVote);
		System.out.println("R - Decrypted Message: " + myVote);
		myVote = ESecManager.decrypt(myVote);
		System.out.println("E - Decrypted Message: " + new String(myVote.getMessage()));
		 
	}
}
