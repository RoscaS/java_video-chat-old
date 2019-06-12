
package ch.hearc.cours.projet.rmi;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ConnectCamRMI_I extends Remote
	{

	public void getMyCam() throws RemoteException, IOException;
	}
