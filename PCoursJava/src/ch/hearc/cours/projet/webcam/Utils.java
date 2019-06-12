
package ch.hearc.cours.projet.webcam;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.List;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

public class Utils implements Serializable
	{

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/
	public static Webcam createWebcam()
		{
		return configureWebcam(Webcam.getDefault());
		}

	public static Webcam configureWebcam(Webcam webcam)
		{
		Dimension resolutionVoulue = new Dimension(1920, 1080);
		Dimension[] tabResolutionAlternative = new Dimension[] { resolutionVoulue, WebcamResolution.HD720.getSize(), WebcamResolution.VGA.getSize() };
		webcam.setCustomViewSizes(tabResolutionAlternative);

		webcam.setViewSize(resolutionVoulue);
		webcam.open();
		return webcam;
		}

	public static BufferedImage grayScaleImage(BufferedImage image)
		{
		//Source : https://gist.github.com/boolean444/899d6fa2b18f6b8f0f56bf98ad15d5a8
		for(int h = 0; h < image.getHeight(); ++h)
			{
			for(int w = 0; w < image.getWidth(); ++w)
				{
				Color color = new Color(image.getRGB(w, h));
				int grayScale = (color.getRed() + color.getBlue() + color.getGreen()) / 3;
				image.setRGB(w, h, new Color(grayScale, grayScale, grayScale).getRGB());
				}
			}
		return image;
		}

	public static BufferedImage reverseImage(BufferedImage bufferedImage)
		{
		int width = bufferedImage.getWidth() - 1;
		BufferedImage bufferedImageReverse = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		for(int h = 0; h < bufferedImage.getHeight(); ++h)
			{
			for(int w = 0; w < bufferedImage.getWidth(); ++w)
				{
				bufferedImageReverse.setRGB(width - w, h, bufferedImage.getRGB(w, h));
				}
			}
		return bufferedImageReverse;
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/

	public static List<Webcam> getWebcamList()
		{
		return Webcam.getWebcams();
		}

	}
