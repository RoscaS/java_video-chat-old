
package ch.hearc.cours.projet.gui.main;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ch.hearc.cours.projet.Settings;
import ch.hearc.cours.projet.rmi.RemoteRMI;
import ch.hearc.cours.projet.webcam.JComboBoxListWebcam;

public class WebcamConfigurationBar extends Box
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public WebcamConfigurationBar()
		{
		super(BoxLayout.X_AXIS);

		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{

		jComboBoxListWebcam = new JComboBoxListWebcam();

		pnlCBXs = new JPanel();
		pnlCBXs.setLayout(new GridLayout(-1, 1));
		cbxBlackWhite = new JCheckBox(WEBCAM_GREY);
		cbxUseWebcam = new JCheckBox(WEBCAM_ENABLE);
		cbxUseWebcam.setSelected(true);

		pnlControl = new JPanel();
		pnlControl.setLayout(new GridLayout(-1, 1));
		labelIpDistante = new JLabel(CONNECTED_TO + Settings.getIpToConnect(), SwingConstants.CENTER);
		btnDisconnect = new JButton(DISCONNECT);

		add(jComboBoxListWebcam);

		pnlCBXs.add(cbxUseWebcam);
		pnlCBXs.add(cbxBlackWhite);
		add(pnlCBXs);

		add(Box.createHorizontalGlue());

		pnlControl.add(labelIpDistante);
		pnlControl.add(btnDisconnect);
		add(pnlControl);
		jComboBoxListWebcam.startSelectedWebcam();
		}

	private void control()
		{
		cbxBlackWhite.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				try
					{
					RemoteRMI.getInstance().setIsGrayScaled(cbxBlackWhite.isSelected());
					}
				catch (UnknownHostException | RemoteException | MalformedURLException e1)
					{
					// TODO Auto-generated catch block
					e1.printStackTrace();
					}
				}
			});
		cbxUseWebcam.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				jComboBoxListWebcam.setEnabled(cbxUseWebcam.isSelected());
				cbxBlackWhite.setEnabled(cbxUseWebcam.isSelected());
				if (cbxUseWebcam.isSelected())
					{
					jComboBoxListWebcam.startSelectedWebcam();
					}
				else
					{
					try
						{
						RemoteRMI.getInstance().stopSharingWebcam();
						}
					catch (UnknownHostException | RemoteException | MalformedURLException e1)
						{
						e1.printStackTrace();
						}
					}
				}
			});
		btnDisconnect.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
				for(Thread thread:threadSet)
					{
					if (thread.getName().toLowerCase().contains("webcam"))
						{
						thread.interrupt();// Permet d'empêcher les getImage continu même lors d'une interruption
						}
					}
				System.exit(1);
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

	// Tools
	private JComboBoxListWebcam jComboBoxListWebcam;

	private JPanel pnlCBXs;
	private static final String WEBCAM_GREY = "Gris";
	private static final String WEBCAM_ENABLE = "Activé";
	private JCheckBox cbxBlackWhite;
	private JCheckBox cbxUseWebcam;

	private JPanel pnlControl;
	private JLabel labelIpDistante;
	private static final String CONNECTED_TO = "Connecté à : ";
	private JButton btnDisconnect;
	private static final String DISCONNECT = "Déconnexion";
	}
