
package ch.hearc.cours.projet.rmi;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ch.hearc.cours.projet.CryptString;
import ch.hearc.cours.projet.Settings;

public class JpanelTesttext extends JPanel
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/

	public JpanelTesttext()
		{
		dateFormat = new SimpleDateFormat(HOUR_FORMAT);

		geometry();
		control();
		appearance();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

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

	private void geometry()
		{
		setLayout(new BorderLayout());
		button = new JButton("send");
		text = new JTextArea();
		RemoteRMI.initTextArea(text);
		try
			{
			cryptString = new CryptString();
			}
		catch (IOException e)
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		field = new JTextField();
		field.setPreferredSize(new Dimension(20, 20));

		add(button, BorderLayout.WEST);
		add(text, BorderLayout.CENTER);
		add(field, BorderLayout.EAST);

		}

	private void control()
		{
		button.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				try
					{
					String timeSend = getCurrentTimeUsingDate();
					String formattedMessage = timeSend + " [" + Settings.getPseudo() + "] " + field.getText();
					cryptString.setMessage(formattedMessage);
					RemoteRMI.getInstance().getJTextAreaDistant().insertMessageDistant(cryptString);
					RemoteRMI.getInstance().getJTextAreaLocal().insertMessageLocal(formattedMessage);
					}
				catch (IOException e1)
					{
					// TODO Auto-generated catch block
					e1.printStackTrace();
					}

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
	JTextArea text;
	JButton button;
	JTextField field;
	CryptString cryptString;

	private static final String HOUR_FORMAT = "hh:mm:ss";
	private static DateFormat dateFormat;

	}
