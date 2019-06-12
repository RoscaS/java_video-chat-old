
package ch.hearc.cours.projet.gui.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ch.hearc.cours.projet.CryptString;
import ch.hearc.cours.projet.Settings;
import ch.hearc.cours.projet.rmi.RemoteRMI;

public class Chat extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public Chat() throws RemoteException, IOException
		{
		dateFormat = new SimpleDateFormat(HOUR_FORMAT);

		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/**
	 * Provient de : https://dzone.com/articles/getting-current-date-time-in-java
	 * @return heure sous forme hh:mm:ss
	 */
	public static String getCurrentTimeUsingDate()
		{
		Date date = new Date();
		return dateFormat.format(date);

		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void geometry() throws RemoteException, IOException
		{
		messageArea = new JTextArea();
		RemoteRMI.initTextArea(messageArea);

		cryptString = new CryptString();

		messageField = new JTextField("");
		buttonSend = new JButton("send");
		boxSender = new Box(BoxLayout.X_AXIS);

		this.setLayout(new BorderLayout());

		boxSender.add(messageField);
		boxSender.add(buttonSend);

		add(messageArea, BorderLayout.CENTER);
		add(boxSender, BorderLayout.SOUTH);
		}

	private void control()
		{
		messageArea.setEditable(false);

		ActionListener alSender = new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				sendMessage();
				}
			};

		buttonSend.addActionListener(alSender);
		messageField.addActionListener(alSender);
		}

	private void appearance()
		{
		messageField.setMinimumSize(new Dimension(120, messageField.getHeight()));
		}

	private void sendMessage()
		{
		new Thread(new Runnable()
			{

			@Override
			public void run()
				{
				try
					{
					String timeSend = getCurrentTimeUsingDate();
					String formattedMessage = timeSend + " [" + Settings.getPseudo() + "] " + messageField.getText();
					cryptString.setMessage(formattedMessage);
					RemoteRMI.getInstance().getJTextAreaDistant().insertMessageDistant(cryptString);
					RemoteRMI.getInstance().getJTextAreaLocal().insertMessageLocal(formattedMessage);
					messageField.setText("");
					}
				catch (IOException e1)
					{
					JOptionPane.showMessageDialog(null, "Connexion lost", "connexion error", JOptionPane.ERROR_MESSAGE);
					try
						{
						RemoteRMI.getInstance();
						RemoteRMI.cleanInstance();
						}
					catch (UnknownHostException | RemoteException | MalformedURLException e)
						{
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
					((JFrame)getTopLevelAncestor()).dispose();
					ch.hearc.cours.projet.gui.main.JGlobal.frame.setVisible(true);
					System.exit(1);
					}

				}
			}).start();

		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/

	//Tools
	private JTextArea messageArea;
	private JTextField messageField;
	private JButton buttonSend;

	private Box boxSender;

	private CryptString cryptString;

	private static final String HOUR_FORMAT = "hh:mm:ss";
	private static DateFormat dateFormat;
	}
