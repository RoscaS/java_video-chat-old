
package ch.hearc.cours.projet.gui.tools;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class JFrameComponent extends JFrame
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JFrameComponent(JComponent jComponent, int width, int height)
		{
		this.jComponent = jComponent;

		geometry();
		control();
		appearance(width, height);
		}

	public JFrameComponent(JComponent jComponent)
		{
		this(jComponent, 800, 800);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		icon = MagasinImage.ICON_WEBCAM;
		setLayout(new BorderLayout());

		add(jComponent, BorderLayout.CENTER);
		}

	private void control()
		{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		}

	private void appearance(int width, int height)
		{
		try
			{
			setIconImage(icon.getImage());
			}
		catch (Exception e)
			{
			System.err.println(e.toString());
			}
		setSize(width, height);
		setLocationRelativeTo(null);
		setVisible(true);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	//Input
	private JComponent jComponent;

	//Tools
	private ImageIcon icon;

	}
