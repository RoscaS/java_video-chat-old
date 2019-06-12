
package ch.hearc.cours.projet.rmi;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.security.PublicKey;

import javax.swing.JTextArea;

import ch.hearc.cours.projet.CryptString;
import ch.hearc.cours.projet.Settings;

import com.bilat.tools.reseau.rmi.RmiTools;
import com.bilat.tools.reseau.rmi.RmiURL;

public class TextRMI implements TextRMI_I ,Serializable
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/
	public TextRMI(JTextArea jTextArea)
		{
		this.jTextArea = jTextArea;
		rmiURL = new RmiURL(Settings.ID_CHAT, ch.hearc.cours.projet.Settings.getPortToShare());
		shareObject();

		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/
	@Override
	public void insertMessageDistant(CryptString cryptString)
		{
		insertMessageLocal(cryptString.getMessage());
		}

	public void insertMessageLocal(String message)
		{
		jTextArea.append(message + "\n");
		}

	public void shareObject()
		{
		try
			{
			RmiTools.shareObject(this, this.rmiURL);
			}
		catch (RemoteException | MalformedURLException e)
			{
			System.err.println("[MessageRMI] : shareObject() : Impossible de Partage le textArea");
			}
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	private RmiURL rmiURL;
	private JTextArea jTextArea;
	private PublicKey publicKey;
	}
