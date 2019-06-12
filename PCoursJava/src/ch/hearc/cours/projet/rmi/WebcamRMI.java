
package ch.hearc.cours.projet.rmi;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.concurrent.atomic.AtomicBoolean;

import com.github.sarxos.webcam.Webcam;

import ch.hearc.cours.projet.BufferedImageCustom;
import ch.hearc.cours.projet.Settings;
import ch.hearc.cours.projet.gui.tools.MagasinImage;
import ch.hearc.cours.projet.webcam.Utils;

import com.bilat.tools.reseau.rmi.RmiTools;
import com.bilat.tools.reseau.rmi.RmiURL;

public class WebcamRMI implements WebcamRMI_I ,Serializable
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/
	public WebcamRMI(Webcam webcam)
		{
		this.webcam = webcam;
		isGrayScaled = new AtomicBoolean(false);
		rmiURL = new RmiURL(Settings.ID_WEBCAM, Settings.getPortToShare());
		shareObject();
		image = new BufferedImageCustom();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public synchronized void close()
		{
		webcam.close();
		webcam = null;
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/
	@Override
	public synchronized BufferedImageCustom getTabBytesImageNormal() throws RemoteException, IOException
		{
		image.setImage(getImage());
		return image;
		}

	public synchronized BufferedImage getImage() throws RemoteException, IOException
		{
		if (webcam != null)
			{
			imgGet = webcam.getImage();
			if (isGrayScaled.get()) { return Utils.grayScaleImage(imgGet); }
			return imgGet;

			}
		return BUF_IMG_NOCAMERA;
		}

	@Override
	public double getFPS() throws RemoteException, IOException
		{
		return webcam.getFPS();
		}

	@Override
	public int getWidth() throws RemoteException, IOException
		{
		return webcam.getViewSize().width;
		}

	@Override
	public int getHeight() throws RemoteException, IOException
		{
		return webcam.getViewSize().height;
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/
	public synchronized void setWebcam(Webcam webcam)
		{
		if (this.webcam != null)
			{
			close();
			}
		Utils.configureWebcam(webcam);
		this.webcam = webcam;
		}

	public void setIsGrayScaled(boolean isGrayScaled)
		{
		this.isGrayScaled.set(isGrayScaled);
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
			System.err.println("[WebcamRMI] : shareObject() : Impossible de Partage le webcam");
			System.out.println(e);
			}
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/
	private static BufferedImage getNoCameraImage()
		{
		System.out.println("bla");
		Image nocamera = MagasinImage.ICON_NOWEBCAM.getImage();
		System.out.println("bla2");
		BufferedImage bufferedImage = new BufferedImage(nocamera.getWidth(null), nocamera.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics bg = bufferedImage.getGraphics();
		bg.drawImage(nocamera, 0, 0, null);
		bg.dispose();
		return bufferedImage;
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	private RmiURL rmiURL;
	private Webcam webcam = null;
	private BufferedImageCustom image;
	private BufferedImage imgGet;
	private AtomicBoolean isGrayScaled;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final BufferedImage BUF_IMG_NOCAMERA = getNoCameraImage();
	}
