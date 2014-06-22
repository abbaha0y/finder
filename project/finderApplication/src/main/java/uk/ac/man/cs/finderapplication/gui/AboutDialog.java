package uk.ac.man.cs.finderapplication.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * The way to locate dialog in the middel
 * of the screen has been modified: Jun 22, 2014
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
 **/

public class AboutDialog extends JDialog{

    public AboutDialog(Frame owner) {
        super(owner, "Finder Application -- About",true);
        createUI();
    }
    
    protected void createUI() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(new AboutPanel());
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton button = new JButton(new AbstractAction("OK") {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPanel.add(button);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 12));
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		pack();
		//Dimension screenSize = getToolkit().getScreenSize();
		this.setLocationRelativeTo(null);
                //setLocation(screenSize.width / 2 - getWidth() / 2, screenSize.height / 2 - getHeight() / 2);
		this.setResizable(false);
	}
    
}
