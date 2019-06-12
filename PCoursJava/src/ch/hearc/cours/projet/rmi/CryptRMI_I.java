
package ch.hearc.cours.projet.rmi;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.PublicKey;

public interface CryptRMI_I extends Remote
	{

	public PublicKey getPublicKey() throws RemoteException, IOException;
	}
