
package ch.hearc.cours.projet.webcam;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JComboBox;

import com.github.sarxos.webcam.Webcam;

import ch.hearc.cours.projet.rmi.RemoteRMI;

public class JComboBoxListWebcam extends JComboBox
	{

	/*------------------------------------------------------------------*\
	|*							Constructeurs							*|
	\*------------------------------------------------------------------*/
	public JComboBoxListWebcam()
		{
		super(func());
		addChangeListener();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public void startSelectedWebcam()
		{
		new Thread(new Runnable()
			{

			@Override
			public void run()
				{
				Webcam webcam = Utils.getWebcamList().get(getSelectedIndex()); //Récupère la webcam correspondant à la webcam sélectionné dans la liste
				try
					{
					RemoteRMI.getInstance().setWebcamLocal(webcam);
					}
				catch (UnknownHostException | RemoteException | MalformedURLException e)
					{
					// TODO Auto-generated catch block
					e.printStackTrace();
					} //Définis la webcam a utiliser
				}
			}).start();
		}

	/*------------------------------------------------------------------*\
	|*							Methodes Private						*|
	\*------------------------------------------------------------------*/

	private void addChangeListener()
		{
		this.addActionListener(new ActionListener()
			{

			@Override
			public void actionPerformed(ActionEvent e)
				{
				startSelectedWebcam();
				}
			});
		}

	/*------------------------------*\
	|*			  Static			*|
	\*------------------------------*/

	private static Object[] func()
		{
		List listWebcam = Utils.getWebcamList();
		Object[] arrayWebcam = new Object[listWebcam.size()];
		for(int i = 0; i < listWebcam.size(); ++i)
			{
			arrayWebcam[i] = listWebcam.get(i).toString();
			}
		return arrayWebcam;
		}

	}
