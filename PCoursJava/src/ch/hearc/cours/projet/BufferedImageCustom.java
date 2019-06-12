
package ch.hearc.cours.projet;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class BufferedImageCustom implements Serializable
	{

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/
	public void setImage(BufferedImage image)
		{
		this.image = image;
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/
	public BufferedImage getImage()
		{
		return image;
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*		Serialisation			*|
	\*------------------------------*/

	private void writeObject(ObjectOutputStream out) throws IOException
		{
		ImageIO.write(this.image, "jpeg", out);
		}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
		{
		this.image = ImageIO.read(in);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	//Input
	private BufferedImage image;

	}
