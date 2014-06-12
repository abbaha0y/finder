package uk.ac.man.cs.finderapplication.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class LogoPanel extends JPanel{
    private Image image;
	
	public LogoPanel(Image image){
		this.image = image;
		Dimension size = new Dimension(this.getWidth(), this.getHeight());
		this.setPreferredSize(size);
		this.setMinimumSize(size);
		this.setMaximumSize(size);
		this.setLayout(null);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
    @Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		if(image != null){
			g.drawImage(image,0,0,this.getWidth(), this.getHeight(), this);
		} 
    }
	
    @Override
	public Dimension getPreferredSize() {
        if (image == null) {
             return new Dimension(100,100);
        } else {
           return new Dimension(this.getWidth(), this.getHeight());
       }
    }
}
