
package ch.hearc.cours.projet.rmi;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import ch.hearc.cours.projet.CryptString;

public interface TextRMI_I extends Remote
	{

	public void insertMessageDistant(CryptString message) throws RemoteException, IOException;
	}
