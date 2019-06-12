
package ch.hearc.cours.projet.gui.connection;

import java.net.SocketException;

import ch.hearc.cours.projet.gui.tools.JDecoratorMarge;
import ch.hearc.cours.projet.gui.tools.JFrameComponent;

import com.bilat.tools.reseau.rmi.NetworkTools;

public class UseGuiConnection
	{
	/*------------------------------------------------------------------*\
	|*							Methodes Public							*|
	\*------------------------------------------------------------------*/

	public static void main(String[] args)
		{
		main();
		}

	public static void main()
		{
		try
			{
			System.setProperty("java.rmi.server.hostname", NetworkTools.localhost().entrySet().iterator().next().getValue().getHostAddress());//Défini notre addresse IP à la JVM (patch linux ou si plusieurs interfaces)
			System.out.println(System.getProperty("java.rmi.server.hostname"));
			}
		catch (SocketException e)
			{
			e.printStackTrace();
			}
		new JFrameComponent(new JDecoratorMarge(new JPanelConnect(), 20), 400, 200).setTitle("Chat vidéo");
		}
	}
