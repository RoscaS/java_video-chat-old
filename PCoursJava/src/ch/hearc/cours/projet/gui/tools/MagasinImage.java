
package ch.hearc.cours.projet.gui.tools;

import javax.swing.ImageIcon;

/**
* Les images doivent se trouver dans un jar, et le jar dans le classpth!
* Le jar doit contenir le folder ressources. A l'interieur du folder ressource doit se trouver les images aux formats (jpg, voir mieux png pour la transparance)
*/
public class MagasinImage
	{

	/*------------------------------------------------------------------*\
	|*		 Version Synchrone (bloquant)								*|
	\*------------------------------------------------------------------*/

	public static final ImageIcon ICON_WEBCAM = ImageLoader.loadSynchroneJar("ressources/open-laptop.png");
	public static final ImageIcon ICON_NOWEBCAM = ImageLoader.loadAsynchroneJar("ressources/nocamera.jpg");

	/*------------------------------------------------------------------*\
	|*		Version Assynchrone	(non bloquant)							*|
	\*------------------------------------------------------------------*/

	//public static final ImageIcon icon = ImageLoader.loadAsynchroneJar("ressources/open-laptop.png");
	}
