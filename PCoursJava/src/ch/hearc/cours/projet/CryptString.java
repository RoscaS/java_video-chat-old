
package ch.hearc.cours.projet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.rmi.RemoteException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import ch.hearc.cours.projet.rmi.RemoteRMI;

public class CryptString implements Serializable
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/
	public CryptString() throws RemoteException, IOException
		{

		this.publicKey = RemoteRMI.getInstance().getCryptDistant().getPublicKey();

		}
	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setMessage(String message)
		{
		this.message = message;
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public String getMessage()
		{
		return this.message;
		}
	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*		Serialisation			*|
	\*------------------------------*/

	private void writeObject(ObjectOutputStream out) throws IOException
		{
		out.writeObject((crypt(message)));
		}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
		{
		message = decrypt((byte[])in.readObject());
		}

	private byte[] crypt(String data)
		{
		byte[] tabByteSource = data.getBytes(Charset.forName("UTF-8"));
		byte[] encryptedMessage = null;
		Cipher cipher = null;
		try
			{
			cipher = Cipher.getInstance("RSA");
			}
		catch (NoSuchAlgorithmException | NoSuchPaddingException e)
			{
			System.err.println("[MessageRMI] : crypt() : Impossible de generer le rsa");
			}
		try
			{
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			}
		catch (InvalidKeyException e)
			{
			System.err.println("[MessageRMI] : crypt() : Impossible d'initialiser les cles");
			}
		try
			{
			encryptedMessage = cipher.doFinal(tabByteSource);
			}
		catch (IllegalBlockSizeException | BadPaddingException e)
			{
			System.err.println("[MessageRMI] : crypt() : Impossible de crypter");
			}
		return encryptedMessage;
		}

	private String decrypt(byte[] data)
		{
		Cipher cipher = null;
		byte[] decryptedMessage = null;
		try
			{
			cipher = Cipher.getInstance("RSA");
			}
		catch (NoSuchAlgorithmException | NoSuchPaddingException e)
			{
			System.err.println("[MessageRMI] : decrypt() : Impossible de cr�er chiper");
			}
		try
			{
			try
				{
				cipher.init(Cipher.DECRYPT_MODE, RemoteRMI.getInstance().getCryptLocal().getPrivatKey());
				}
			catch (UnknownHostException | RemoteException | MalformedURLException e)
				{
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
			}
		catch (InvalidKeyException e)
			{
			System.err.println("[MessageRMI] : decrypt() : Impossible d'initialiser les cles");
			}
		try
			{
			decryptedMessage = cipher.doFinal(data);
			}
		catch (IllegalBlockSizeException | BadPaddingException e)
			{
			System.err.println("[MessageRMI] : decrypt() : Impossible de decrypter");
			}
		return new String(decryptedMessage, Charset.forName("UTF-8"));
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	private String message;
	private PublicKey publicKey;
	}
