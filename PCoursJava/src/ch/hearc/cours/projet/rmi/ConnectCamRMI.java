
package ch.hearc.cours.projet.rmi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

import ch.hearc.cours.projet.Settings;

import com.bilat.tools.reseau.rmi.RmiTools;
import com.bilat.tools.reseau.rmi.RmiURL;

public class ConnectCamRMI implements ConnectCamRMI_I
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public ConnectCamRMI()
		{
		rmiURL = new RmiURL(Settings.ID_CONNECTCAM, ch.hearc.cours.projet.Settings.getPortToShare());
		shareObject();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	@Override
	public void getMyCam() throws RemoteException, IOException
		{
		RemoteRMI.getInstance().connectWebcam(Settings.getIpToConnect());

		}
	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void shareObject()
		{
		try
			{
			RmiTools.shareObject(this, this.rmiURL);
			}
		catch (RemoteException | MalformedURLException e)
			{
			System.err.println("[ConnectCamRMI] : shareObject() : Impossible de Partage le connectcam");
			}
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	RmiURL rmiURL;

	}
