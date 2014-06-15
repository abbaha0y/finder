/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.man.cs.finderapplication.gui;

import java.awt.Graphics;
import javax.swing.JSplitPane;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class AnimatedSplitPane extends JSplitPane{
    
    int dividerLocation = 0;
    int dividerlimit;
    boolean stop = true;
    
    public AnimatedSplitPane(int dividerlimit){
        this.setOrientation(JSplitPane.VERTICAL_SPLIT);
	this.setDividerLocation(0);
	this.setDividerSize(0);
        this.dividerlimit = dividerlimit;
    }
    
    public void dividerDown() throws InterruptedException{
        while(stop){
            if(dividerLocation<dividerlimit){
                dividerLocation++;
                stop = true;
            }
            else{
                stop = false;
            }
            this.repaint();
            Thread.sleep(10);
        }
        stop = true;
    }
    
    public void dividerUp() throws InterruptedException{
        while(stop){
            if(dividerLocation>0){
                dividerLocation--;
                stop = true;
            }
            else{
                stop = false;
            }
            this.repaint();
            Thread.sleep(10);
        }
        stop = true;
    }
    
    public boolean isDividerDown(){
        return dividerLocation==dividerlimit;
    }
    
    public boolean isDividerUp(){
        return !isDividerDown();
    }
    
    @Override
    public void paint(Graphics g) {
	super.paint(g);
	this.setDividerLocation(dividerLocation);
    }
}
