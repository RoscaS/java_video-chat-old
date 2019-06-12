
package ch.hearc.cours.projet.gui.main;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class MainArea extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public MainArea(JComponent centralComponent, JComponent southComponent)
		{
		this.centralComponent = centralComponent;
		this.SouthComponent = southComponent;

		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		this.setLayout(new BorderLayout());

		add(centralComponent, BorderLayout.CENTER);
		add(SouthComponent, BorderLayout.SOUTH);
		}

	private void control()
		{
		//Rien
		}

	private void appearance()
		{
		//rien
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	//Input
	private JComponent centralComponent;
	private JComponent SouthComponent;
	}
