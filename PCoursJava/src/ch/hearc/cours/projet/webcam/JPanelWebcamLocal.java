
package ch.hearc.cours.projet.webcam;

import java.io.Serializable;

import ch.hearc.cours.projet.rmi.WebcamRMI;

public class JPanelWebcamLocal extends JPanelWebcamDistant implements Serializable
	{

	public JPanelWebcamLocal()
		{
		super();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/
	@Override
	protected void actualizeImage()
		{
		try
			{
			image = Utils.reverseImage(((WebcamRMI)webcam).getImage());
			repaint();
			}
		catch (Exception e)
			{
			e.printStackTrace();
			}
		}
	}
