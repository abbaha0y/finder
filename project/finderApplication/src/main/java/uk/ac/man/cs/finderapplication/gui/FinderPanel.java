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
	JSplitPane logoSP,buttomLeft,buttomRight;
	
	public FinderPanel(){
		logoSP = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		buttomLeft = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		buttomRight = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		logoSP.setBorder(null);
		buttomLeft.setBorder(null);
		buttomRight.setBorder(null);
		logoSP.setDividerSize(0);
		buttomLeft.setDividerSize(0);
		buttomRight.setDividerSize(0);
		logoSP.setDividerLocation(100);
		buttomLeft.setDividerLocation(300);
		buttomRight.setDividerLocation(300);
		
		buttomLeft.setRightComponent(buttomRight);
		logoSP.setBottomComponent(buttomLeft);
		
		setLogoComponent(new JButton("TOP 900 X 100"));
		setButtomLeftComponent(new JButton("Buttom Left"));
		setButtomCenterComponent(new JButton("Buttom Center"));
		setButtomRightComponent(new JButton("Buttom Right"));
		
		this.setLayout(new BorderLayout());
		this.add(logoSP, BorderLayout.CENTER);
	}
	
	public void setLogoComponent(Component comp){
            logoSP.setTopComponent(comp);
	}
	public void setButtomLeftComponent(Component comp){
            buttomLeft.setLeftComponent(comp);
	}
	public void setButtomCenterComponent(Component comp){
            buttomRight.setLeftComponent(comp);
	}
	public void setButtomRightComponent(Component comp){
            buttomRight.setRightComponent(comp);
	}
        public void setDividerLocationButtomLeft(){
            buttomLeft.setDividerLocation(300);
        }
        public void setDividerLocationButtomRight(){
            buttomRight.setDividerLocation(300);
        }
}
