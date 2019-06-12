
package ch.hearc.cours.projet.gui.main;

import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class SideBar extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/
	public SideBar(JComponent component1, JComponent component2, int width, int height)
		{
		this.component1 = component1;
		this.component2 = component2;

		geometry();
		control();
		appearance(width, height);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		gridLayout = new GridLayout(2, -1);

		this.setLayout(gridLayout);

		add(component1);
		add(component2);
		}

	private void control()
		{
		//Rien
		}

	private void appearance(int width, int height)
		{
		setSize(width, height);
		gridLayout.setHgap(10);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	//Input
	private JComponent component1;
	private JComponent component2;

	//Tools
	private GridLayout gridLayout;
	}
