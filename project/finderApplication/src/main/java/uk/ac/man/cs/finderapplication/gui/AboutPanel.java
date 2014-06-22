package uk.ac.man.cs.finderapplication.gui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Labels & about image were modified: Jun 22, 2014
 * By: Al Abbas, Hani
 */

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Oct 9, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */

public class AboutPanel extends JPanel {

    public static final String COPYRIGHT = "Copyright The University Of Manchester";
    public static final String ACK_1 = "Author: Al Abbas, Hani";
    public static final String ACK_2 = "Built based on: The Manchester Pizza Finder"+
            ", written by Matthew Horridge, Afsaneh Maleki Dizaji";
    public static final String NOTE = "This application provides:";
    public static final String NOTE1 = "1- Flexibility in term of configrations, all configurations stored in the ontology";
    public static final String NOTE2 = "2- Notion of Facets search is introduced.";
    public static final String NOTE3 = "3- Different views (Tree view & List View) along with filters";
    public static final String NOTE4 = "4- Different languages with persentage stored in the ontology";
    public static final String NOTE5 = "5- User interface configuration";


	public AboutPanel() {
		setupUI();
	}

	protected void setupUI() {
		setLayout(new BorderLayout(7, 7));
		setupInformationPanel();
	}

	protected void setupInformationPanel() {
		GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
		                                                GridBagConstraints.NONE,
		                                                new Insets(7, 7, 7, 7),
		                                                0, 0);
		JPanel box = new JPanel(new GridBagLayout());
		box.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
		box.add(new ImagePanel(), gbc);
		gbc.gridy = 1;
		box.add(new JLabel(COPYRIGHT) , gbc);
		gbc.gridy = 2;
		box.add(new JLabel(ACK_1), gbc);
		gbc.gridy = 3;
		box.add(new JLabel(ACK_2), gbc);
		gbc.gridy = 4;
		box.add(new JLabel(NOTE), gbc);
		gbc.gridy = 5;
		box.add(new JLabel(NOTE1), gbc);
                gbc.gridy = 6;
		box.add(new JLabel(NOTE2), gbc);
                gbc.gridy = 7;
		box.add(new JLabel(NOTE3), gbc);
                gbc.gridy = 8;
		box.add(new JLabel(NOTE4), gbc);
                gbc.gridy = 9;
		box.add(new JLabel(NOTE5), gbc);
		add(box, BorderLayout.SOUTH);
	}

	protected class ImagePanel extends JPanel {
		private ImageIcon image;

		public ImagePanel() {
			try {
				URL url = getClass().getResource("/FinderAboutBox.png");
				image = new ImageIcon(url);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}

		public Dimension getPreferredSize() {
			return new Dimension(image.getIconWidth(), image.getIconHeight());
		}


		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(image.getImage(), 0, 0, this);
		}
	}
}

