
package ch.hearc.cours.projet.gui.main;

import java.awt.Color;
import java.awt.GridLayout;
import java.io.IOException;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ch.hearc.cours.projet.rmi.RemoteRMI;
import ch.hearc.cours.projet.webcam.JPanelWebcamDistant;
import ch.hearc.cours.projet.webcam.JPanelWebcamLocal;

public class JGlobal extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JGlobal(JFrame jFrame) throws RemoteException, IOException
		{
		frame = jFrame;
		geometry();
		control();
		appearance();

		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry() throws RemoteException, IOException
		{
		//TODO Lancement d'exceptions propre
		webcamPanel2 = new JPanelWebcamDistant();
		RemoteRMI.initJPanelWebCamDistant(webcamPanel2);
		webcamPanel1 = new JPanelWebcamLocal();
		RemoteRMI.initJPanelWebcamLocal(webcamPanel1);
		chat = new Chat();
		connectionState = new WebcamConfigurationBar();

		mainArea = new MainArea(webcamPanel2, connectionState);
		sideBar = new SideBar(webcamPanel1, chat, (int)(getWidth() * 0.3), getHeight());

		gridLayout = new GridLayout(1, -1);
		this.setLayout(gridLayout);

		add(mainArea);
		add(sideBar);
		}

	private void control()
		{
		//Rien
		}

	private void appearance()
		{
		webcamPanel1.setBackground(Color.WHITE);
		webcamPanel1.setBorder(BorderFactory.createLineBorder(Color.black));

		webcamPanel2.setBackground(Color.LIGHT_GRAY);

		connectionState.setSize(connectionState.getPreferredSize().width, 50);
		connectionState.setBackground(Color.GRAY);

		gridLayout.setHgap(10);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	//Tools
	private WebcamConfigurationBar connectionState;
	private JPanelWebcamLocal webcamPanel1;
	private JPanelWebcamDistant webcamPanel2;
	private Chat chat;

	private MainArea mainArea;
	private SideBar sideBar;

	private GridLayout gridLayout;
	public static JFrame frame;
	}
