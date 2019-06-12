
package ch.hearc.cours.projet.rmi;

import java.io.Serializable;
import java.net.SocketException;

import ch.hearc.cours.projet.gui.tools.JFrameComponent;

import com.bilat.tools.reseau.rmi.NetworkTools;

public class UseText implements Serializable
	{
	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static void main(String[] args)
		{
		try
			{
			System.setProperty("java.rmi.server.hostname", NetworkTools.localhost().entrySet().iterator().next().getValue().getHostAddress());//Défini notre addresse IP à la JVM (patch linux ou si plusieurs interfaces)
			}
		catch (SocketException e)
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		main();
		}

	public static void main()
		{
		JpanelTesttext jpanelTesttext = new JpanelTesttext();
		new JFrameComponent(jpanelTesttext);

		}
	}
