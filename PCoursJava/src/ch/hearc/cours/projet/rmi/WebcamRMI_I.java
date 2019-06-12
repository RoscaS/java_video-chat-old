
package ch.hearc.cours.projet.rmi;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import ch.hearc.cours.projet.BufferedImageCustom;

public interface WebcamRMI_I extends Remote
	{

	public BufferedImageCustom getTabBytesImageNormal() throws RemoteException, IOException;

	public double getFPS() throws RemoteException, IOException;

	public int getWidth() throws RemoteException, IOException;

	public int getHeight() throws RemoteException, IOException;
	}
