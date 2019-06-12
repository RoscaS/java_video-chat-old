
package ch.hearc.cours.projet.gui.tools;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class JDecoratorMarge extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JDecoratorMarge(JComponent jComponent, int marge)
		{
		this.jComponent = jComponent;
		this.dimension = new Dimension(marge, marge);

		geometry();
		control();
		appearance();
		}

	public JDecoratorMarge(JComponent jComponent)
		{
		this(jComponent, MARGE);
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		// JComponent : Instanciation
		jPanel1 = new JPanel();
		jPanel2 = new JPanel();
		jPanel3 = new JPanel();
		jPanel4 = new JPanel();

		// Layout : Specification
			{
			BorderLayout borderlayout = new BorderLayout();
			setLayout(borderlayout);
			}

		// JComponent : add
		this.add(jComponent, BorderLayout.CENTER);
		this.add(jPanel1, BorderLayout.NORTH);
		this.add(jPanel2, BorderLayout.SOUTH);
		this.add(jPanel3, BorderLayout.EAST);
		this.add(jPanel4, BorderLayout.WEST);
		}

	private void control()
		{
		// rien
		}

	private void appearance()
		{
		setSizeConstante(this.jPanel1, this.dimension);
		setSizeConstante(this.jPanel2, this.dimension);
		setSizeConstante(this.jPanel3, this.dimension);
		setSizeConstante(this.jPanel4, this.dimension);
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static void setSizeConstante(JComponent jComponent, Dimension dimension)
		{
		jComponent.setPreferredSize(dimension);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	//Input
	private Dimension dimension;
	private JComponent jComponent;

	// Tools
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel3;
	private JPanel jPanel4;

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static final int MARGE = 20;

	}
