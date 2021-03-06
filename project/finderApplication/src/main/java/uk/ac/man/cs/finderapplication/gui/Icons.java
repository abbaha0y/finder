package uk.ac.man.cs.finderapplication.gui;

import javax.swing.*;
import java.net.URL;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Oct 7, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class Icons {
	public static ImageIcon getSpicyIcon() {
		URL url = Icons.class.getResource("/HotIcon.gif");
		return new ImageIcon(url);
	}

	public static ImageIcon getVegetarianIcon() {
		URL url = Icons.class.getResource("/VegetarianIcon.gif");
		return new ImageIcon(url);
	}

	public static ImageIcon getIngIcon() {
		URL url = Icons.class.getResource("/defualt_App_Icon.png");
		return new ImageIcon(url);
	}
        
        public static ImageIcon getAppIcon(){
            URL url = Icons.class.getResource("/defualt_Icon.png");
		return new ImageIcon(url);
        }
}

