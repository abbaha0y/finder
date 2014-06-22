/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.man.cs.finderapplication.gui;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class ConfigruationPanel extends JPanel{
    
    JSplitPane splitPane;
    JPanel menuPanel,framePanel; 
    
    public ConfigruationPanel(){
        buildUI();
    }
    
    private void buildUI(){
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(450);
        splitPane.setDividerSize(0);
        splitPane.setLeftComponent(setupMenuPanel());
        splitPane.setRightComponent(setupFramePanel());
    }
    
    private JPanel setupMenuPanel(){
        menuPanel = new JPanel();
        
        return menuPanel;
    }
    
    private JPanel setupFramePanel(){
        framePanel = new JPanel();
        
        return framePanel;
    }
}
