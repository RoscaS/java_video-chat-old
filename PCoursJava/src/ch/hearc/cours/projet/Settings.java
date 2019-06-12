
package ch.hearc.cours.projet;

import java.io.Serializable;
import java.util.prefs.Preferences;

public class Settings implements Serializable
	{

	/*------------------------------*\
	|*				Set				*|
	\*------------------------------*/
	public static void setIpToConnect(String ip)
		{
		SETTINGS.put("ipToConnect", ip);
		}

	public static void setPortToConnect(int port)
		{
		SETTINGS.putInt("portToConnect", port);
		}

	public static void setPseudo(String pseudo)
		{
		SETTINGS.put("pseudo", pseudo);
		}

	public static void setPortToShare(int port)
		{
		SETTINGS.putInt("portToShare", port);
		}

	/*------------------------------*\
	|*				Get				*|
	\*------------------------------*/
	public static String getIpLocal()
		{
		//TODO retourne l'ip locale
		return "IP local";
		}

	public static String getIpToConnect()
		{
		return SETTINGS.get("ipToConnect", "157.26.106.141");
		}

	public static int getPortToConnect()
		{
		return SETTINGS.getInt("portToConnect", 50885);
		}

	public static String getPseudo()
		{
		return SETTINGS.get("pseudo", "anonyme");
		}

	public static int getPortToShare()
		{
		return SETTINGS.getInt("portToShare", 50885);
		}

	/*------------------------------------------------------------------*\
	|*							Attributs Private						*|
	\*------------------------------------------------------------------*/
	private static final Preferences SETTINGS = Preferences.userNodeForPackage(Settings.class);

	public static final String ID_CHAT = "CHAT";
	public static final String ID_WEBCAM = "WEBCAM";
	public static final String ID_KEY = "KEY";
	public static final String ID_CONNECTCAM = "CONNECTCAM";
	}
