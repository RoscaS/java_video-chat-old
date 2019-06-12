
package ch.hearc.cours.projet.rmi;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

import javax.swing.JTextArea;

import com.github.sarxos.webcam.Webcam;

import ch.hearc.cours.projet.Settings;
import ch.hearc.cours.projet.webcam.JPanelWebcamDistant;
import ch.hearc.cours.projet.webcam.JPanelWebcamLocal;
import ch.hearc.cours.projet.webcam.Utils;

import com.bilat.tools.reseau.rmi.RmiTools;
import com.bilat.tools.reseau.rmi.RmiURL;

public class RemoteRMI implements Serializable
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/
	private RemoteRMI() throws UnknownHostException, RemoteException, MalformedURLException
		{
		webcamLocal = null;
		start();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void connectWebcam(String ip)
		{
		InetAddress adresseIp;
		try
			{
			adresseIp = InetAddress.getByName(ip);

			int port = Settings.getPortToConnect();

			RmiURL rmiURL = new RmiURL(Settings.ID_WEBCAM, adresseIp, port);

			webcamDistant = (WebcamRMI_I)RmiTools.connectionRemoteObjectBloquant(rmiURL, 500, 20);
			RemoteRMI.jPanelWebcamDistant.setStartWebcam(webcamDistant);

			}
		catch (RemoteException | MalformedURLException | UnknownHostException e)
			{
			System.err.println("[RemoteRMI] : connectWebcam() : Impossible de se connecter � la webCamDisntant");
			}
		}

	public void stopSharingWebcam()
		{
		webcamLocal.close();
		}

	public synchronized static void cleanInstance()
		{
		instance = null;
		}

	public synchronized static RemoteRMI getInstance() throws UnknownHostException, RemoteException, MalformedURLException
		{
		if (instance == null)
			{
			instance = new RemoteRMI();
			}
		return instance;
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	public static void initTextArea(JTextArea jTextArea)
		{
		RemoteRMI.jTextArea = jTextArea;

		}

	public static void initJPanelWebCamDistant(JPanelWebcamDistant jPanelWebcamDistant)
		{
		RemoteRMI.jPanelWebcamDistant = jPanelWebcamDistant;
		}

	public static void initJPanelWebcamLocal(JPanelWebcamLocal jPanelWebcamLocal)
		{
		RemoteRMI.jPanelWebcamLocal = jPanelWebcamLocal;
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/
	public ConnectCamRMI getConnectCamLocal()
		{
		return connectCamRMILocal;
		}

	public ConnectCamRMI_I getConnectCamDistant()
		{
		return connectCamRMIDistant;
		}

	public TextRMI_I getJTextAreaDistant()
		{
		return TextRMIDistant;
		}

	public CryptRMI getCryptLocal()
		{
		return cryptRMILocal;
		}

	public CryptRMI_I getCryptDistant()
		{
		return cryptRMIDistant;
		}

	public TextRMI getJTextAreaLocal()
		{
		return TextRMILocal;
		}

	public WebcamRMI_I getWebcamDistante()
		{
		return webcamDistant;
		}

	public synchronized WebcamRMI getWebcamLocal()
		{
		if (webcamLocal == null)
			{
			webcamLocal = new WebcamRMI(Utils.createWebcam());
			jPanelWebcamLocal.setStartWebcam(webcamLocal);
			}
		return webcamLocal;
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/
	public synchronized void setWebcamLocal(Webcam webcam)
		{
		if (webcamLocal == null)
			{
			Utils.configureWebcam(webcam);
			webcamLocal = new WebcamRMI(webcam);
			}
		else
			{
			webcamLocal.setWebcam(webcam);
			}
		try
			{
			RemoteRMI.getInstance().getConnectCamDistant().getMyCam();
			}
		catch (IOException err)
			{
			// TODO Auto-generated catch block
			err.printStackTrace();
			}
		jPanelWebcamLocal.setStartWebcam(webcamLocal);
		}

	public synchronized void setIsGrayScaled(boolean isGrayScaled)
		{
		webcamLocal.setIsGrayScaled(isGrayScaled);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/
	private void start() throws UnknownHostException, MalformedURLException, RemoteException
		{
		serverSide();
		clientSide();
		}

	private void serverSide()
		{
		cryptRMILocal = CryptRMI.getInstance();
		TextRMILocal = new TextRMI(jTextArea);
		connectCamRMILocal = new ConnectCamRMI();
		}

	private void clientSide() throws RemoteException
		{
		String ip = Settings.getIpToConnect();
		try
			{
			connectCrypt(ip);
			connectChat(ip);
			connectConnectCam(ip);
			}
		catch (UnknownHostException | RemoteException | MalformedURLException e)
			{
			throw new RemoteException();
			}

		}

	private void connectCrypt(String ip) throws UnknownHostException, RemoteException, MalformedURLException
		{
		InetAddress adresseIp;

		adresseIp = InetAddress.getByName(ip);

		int port = Settings.getPortToConnect();

		RmiURL rmiURL = new RmiURL(Settings.ID_KEY, adresseIp, port);

		cryptRMIDistant = (CryptRMI_I)RmiTools.connectionRemoteObjectBloquant(rmiURL, 500, 5);

		}

	private void connectChat(String ip) throws UnknownHostException, RemoteException, MalformedURLException
		{
		InetAddress adresseIp;

		adresseIp = InetAddress.getByName(ip);

		int port = Settings.getPortToConnect();

		RmiURL rmiURL = new RmiURL(Settings.ID_CHAT, adresseIp, port);

		TextRMIDistant = (TextRMI_I)RmiTools.connectionRemoteObjectBloquant(rmiURL, 500, 5);

		}

	private void connectConnectCam(String ip) throws UnknownHostException, RemoteException, MalformedURLException
		{
		InetAddress adresseIp;

		adresseIp = InetAddress.getByName(ip);

		int port = Settings.getPortToConnect();

		RmiURL rmiURL = new RmiURL(Settings.ID_CONNECTCAM, adresseIp, port);

		connectCamRMIDistant = (ConnectCamRMI_I)RmiTools.connectionRemoteObjectBloquant(rmiURL, 500, 5);

		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	private TextRMI TextRMILocal;
	private TextRMI_I TextRMIDistant;
	private CryptRMI cryptRMILocal;
	private CryptRMI_I cryptRMIDistant;
	private WebcamRMI webcamLocal;
	private WebcamRMI_I webcamDistant;
	private ConnectCamRMI_I connectCamRMIDistant;
	private ConnectCamRMI connectCamRMILocal;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static RemoteRMI instance = null;
	private static JTextArea jTextArea;
	private static JPanelWebcamDistant jPanelWebcamDistant;
	private static JPanelWebcamLocal jPanelWebcamLocal;

	}
