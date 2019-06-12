
package ch.hearc.cours.projet.webcam;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ch.hearc.cours.projet.rmi.WebcamRMI_I;

public class JPanelWebcamDistant extends JPanel implements Serializable
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelWebcamDistant()
		{

		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void resize()
		{
		double rat = (double)this.getHeight() / this.getWidth();
		if (rat >= RATIO_FullHD)
			{
			//centrage horizontal -> bandes verticales
			width = this.getWidth();
			height = ((int)(width * RATIO_FullHD));

			int marge = this.getHeight() - height;
			pointWebcam.get().setLocation(new Point(0, marge / 2));
			System.out.println("ratio : " + rat + " > full");
			}
		else
			{
			//centrage vertical -> bandes horizontales
			height = this.getHeight();
			width = ((int)(height / RATIO_FullHD));

			int marge = this.getWidth() - width;
			pointWebcam.get().setLocation(new Point(marge / 2, 0));
			System.out.println("ratio : " + rat + " < full ");
			}
		}

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/

	public void setStartWebcam(WebcamRMI_I webcam)
		{
		this.webcam = webcam;
		Thread thread = new Thread(new Runnable()
			{

			@Override
			public void run()
				{
				while(!Thread.interrupted())
					{
					actualizeImage();
					}
				}
			});
		thread.setName("webcam local");
		thread.start();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	@Override
	protected void paintComponent(Graphics g)
		{
		super.paintComponent(g);
		g2d = (Graphics2D)g;
		dessiner();
		}

	protected void dessiner()
		{

		g2d.drawImage(image, pointWebcam.get().x, pointWebcam.get().y, width, height, null);

		}

	protected void actualizeImage()
		{
		try
			{
			image = webcam.getTabBytesImageNormal().getImage();
			repaint();
			}
		catch (IOException e)
			{
			Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
			for(Thread thread:threadSet)
				{
				if (thread.getName().toLowerCase().contains("webcam"))
					{
					thread.interrupt();// Permet d'empêcher les getImage continu même lors d'une interruption
					}
				}

			JOptionPane.showMessageDialog(null, "Connexion lost", "connexion error", JOptionPane.ERROR_MESSAGE);

			((JFrame)getTopLevelAncestor()).dispose();
			ch.hearc.cours.projet.gui.main.JGlobal.frame.setVisible(true);
			System.exit(1);

			}
		}

	private void geometry()
		{
		this.pointWebcam = new AtomicReference<Point>();
		this.pointWebcam.set(new Point(0, 0));
		this.setSize(new Dimension(width, height));
		this.setPreferredSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));
		}

	private void control()
		{

		this.addComponentListener(new ComponentAdapter()
			{

			@Override
			public void componentResized(ComponentEvent e)
				{
				resize();
				}
			});
		}

	private void appearance()
		{
		// rien
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	// Input
	protected WebcamRMI_I webcam;
	protected int width;
	protected int height;
	protected Graphics2D g2d;
	protected BufferedImage image;

	// Tools
	private static final double RATIO_FullHD = 0.5625; // 1080/1920 - H/W
	private AtomicReference<Point> pointWebcam;

	}
