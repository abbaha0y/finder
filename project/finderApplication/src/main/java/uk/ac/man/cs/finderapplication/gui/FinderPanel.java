package uk.ac.man.cs.finderapplication.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class FinderPanel extends JPanel{
	JSplitPane logoSP,buttomSplit;

	public FinderPanel(){
		logoSP = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		buttomSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		logoSP.setBorder(null);
		buttomSplit.setBorder(null);
		logoSP.setDividerSize(0);
		buttomSplit.setDividerSize(0);
		logoSP.setDividerLocation(100);
		buttomSplit.setDividerLocation(300);

		logoSP.setBottomComponent(buttomSplit);

		setLogoComponent(new JButton("TOP 900 X 100"));
		setButtomLeftComponent(new JButton("Buttom Left"));

		setButtomRightComponent(new JButton("Buttom Right"));

		this.setLayout(new BorderLayout());
		this.add(logoSP, BorderLayout.CENTER);
	}

	public void setLogoComponent(Component comp){
		logoSP.setTopComponent(comp);
	}
	public void setButtomLeftComponent(Component comp){
		buttomSplit.setLeftComponent(comp);
	}

	public void setButtomRightComponent(Component comp){
		buttomSplit.setRightComponent(comp);
	}
	public void setDividerLocationButtom(){
		buttomSplit.setDividerLocation(300);
	}

}
