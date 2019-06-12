
package ch.hearc.cours.projet.rmi;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import ch.hearc.cours.projet.Settings;

import com.bilat.tools.reseau.rmi.RmiTools;
import com.bilat.tools.reseau.rmi.RmiURL;

public class CryptRMI implements CryptRMI_I ,Serializable
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/
	private CryptRMI()
		{
		KeyPairGenerator keyGen = null;
		try
			{
			keyGen = KeyPairGenerator.getInstance("RSA");
			}
		catch (NoSuchAlgorithmException e)
			{
			System.err.println("[CryptRMI] : constructeur() : Impossible de créer les cles");
			}
		keyGen.initialize(1024);
		KeyPair pair = keyGen.generateKeyPair();
		privateKey = pair.getPrivate();
		publicKey = pair.getPublic();

		rmiURL = new RmiURL(Settings.ID_KEY, Settings.getPortToShare());
		shareObject();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/
	public synchronized static CryptRMI getInstance()
		{
		if (instance == null)
			{
			instance = new CryptRMI();
			}
		return instance;
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/
	@Override
	public PublicKey getPublicKey()
		{
		return this.publicKey;
		}

	public PrivateKey getPrivatKey()
		{
		return this.privateKey;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/
	public void shareObject()
		{
		try
			{
			RmiTools.shareObject(this, this.rmiURL);
			}
		catch (RemoteException | MalformedURLException e)
			{
			System.err.println("[MessageRMI] : shareObject() : Impossible de Partage le cryptRMI");
			System.out.println(e);
			}
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	RmiURL rmiURL;
	private PrivateKey privateKey;
	private PublicKey publicKey;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static CryptRMI instance = null;

	}
