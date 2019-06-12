
package ch.hearc.cours.projet.gui.connection;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ch.hearc.cours.projet.Settings;
import ch.hearc.cours.projet.gui.main.JGlobal;
import ch.hearc.cours.projet.gui.tools.JDecoratorMarge;
import ch.hearc.cours.projet.gui.tools.JFrameComponent;
import ch.hearc.cours.projet.rmi.RemoteRMI;

public class JPanelConnect extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JPanelConnect()
		{
		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry()
		{
		labelIpDistante = new JLabel(IP_DISTANTE);
		labelPseudo = new JLabel(PSEUDO);
		labelIpLocal = new JLabel(IP_LOCAL + getLocalIp());

		labelPortSource = new JLabel(PORT_SOURCE);
		labelPortDestination = new JLabel(PORT_DESTINATION);

		fieldIpDistante = new JTextField(Settings.getIpToConnect());
		fieldPseudo = new JTextField(Settings.getPseudo());
		fieldPortSource = new JTextField(String.valueOf(Settings.getPortToShare()));
		fieldPortDestination = new JTextField(String.valueOf(Settings.getPortToConnect()));

		buttonConnect = new JButton(CONNECTION);

		gLayout = new GridLayout(-1, 2);
		this.setLayout(gLayout);

		add(labelIpDistante);
		add(fieldIpDistante);
		add(labelPseudo);
		add(fieldPseudo);

		add(labelPortSource);
		add(fieldPortSource);
		add(labelPortDestination);
		add(fieldPortDestination);
		add(labelIpLocal);
		add(buttonConnect);
		}

	private void control()
		{
		buttonConnect.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				buttonConnect.setEnabled(false);
				String errorMessage = "";
				if (!isIpValid())
					{
					System.err.println("IP non valide");
					errorMessage += "- Adresse IP saisie non valide.";
					}
				if (!isPseudoValid())
					{
					System.err.println("Pseudo non valids");
					errorMessage += "- Le pseudo n'est pas valide.\n";
					}
				if (!isPortsValid())
					{
					System.err.println("Ports non valides");
					errorMessage += "- Les ports entrés ne sont pas valides.\n";
					}
				if (!errorMessage.equalsIgnoreCase(""))
					{
					JOptionPane.showMessageDialog(null, errorMessage, "Saisie incorrecte", JOptionPane.ERROR_MESSAGE);
					return;
					}

				//Mise à jour des settings
				Settings.setIpToConnect(fieldIpDistante.getText());
				Settings.setPseudo(fieldPseudo.getText());
				Settings.setPortToShare(Integer.parseInt(fieldPortSource.getText()));
				Settings.setPortToConnect(Integer.parseInt(fieldPortDestination.getText()));

				//Lancement de la nouvelle fenêtre
				buttonConnect.setText(CONNECTING_TO); //TODO SI ECHEC, REMETTRE -CONNECTION-
				new Thread(new Runnable()
					{

					@Override
					public void run()
						{
						try
							{
							Dimension sizeOfFrame = getSizeForFrame();
							new JFrameComponent(new JDecoratorMarge(new JGlobal((JFrame)getTopLevelAncestor()), 20), (int)sizeOfFrame.getWidth(), (int)sizeOfFrame.getHeight()).setTitle("Chat Video");
							getTopLevelAncestor().setVisible(false);
							}
						catch (IOException e)
							{
							RemoteRMI.cleanInstance();
							JOptionPane.showMessageDialog(null, "Connexion error please restart", "connexion error", JOptionPane.ERROR_MESSAGE);
							System.exit(1);
							}
						}
					}).start();
				//TODO Fermeture de la fenêtre
				}
			});
		}

	private void appearance()
		{
		gLayout.setHgap(10);
		}

	private boolean isIpValid()
		{
		String ip = fieldIpDistante.getText();
		return Pattern.matches(IP_REGEX, ip);
		}

	private boolean isPseudoValid()
		{
		String pseudo = fieldIpDistante.getText();
		return !pseudo.isEmpty();
		}

	private boolean isPortsValid()
		{
		return (Pattern.matches(PORT_REGEX, fieldPortSource.getText()) && Pattern.matches(PORT_REGEX, fieldPortDestination.getText()));
		}

	private String getLocalIp()
		{
		return System.getProperty("java.rmi.server.hostname");
		}

	private Dimension getSizeForFrame()
		{
		double ratio = 0.9;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		dim.setSize(dim.getWidth() * ratio, dim.getHeight() * ratio);
		return dim;
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	//Tools
	private JLabel labelIpDistante;
	private static final String IP_DISTANTE = "Adresse IP distante";
	private JLabel labelPseudo;
	private static final String PSEUDO = "Pseudo";
	private JLabel labelIpLocal;

	private JLabel labelPortSource;
	private static final String PORT_SOURCE = "Port Source ";
	private JLabel labelPortDestination;
	private static final String PORT_DESTINATION = "Port Destination ";

	private JTextField fieldIpDistante;
	private static final String IP_REGEX = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
	private JTextField fieldPseudo;
	private JTextField fieldPortSource;
	private JTextField fieldPortDestination;
	private static final String PORT_REGEX = "([0-9]{2,5})";

	private static final String IP_LOCAL = "Ip local ";
	private static final String CONNECTION = "Connexion";
	private static final String CONNECTING_TO = "Connexion en cours...";
	private JButton buttonConnect;

	private GridLayout gLayout;

	}
