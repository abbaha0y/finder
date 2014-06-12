/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.man.cs.finderapplication.gui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.JFrame;
import uk.ac.man.cs.finderapplication.model.FinderOntology;
import uk.ac.man.cs.finderapplication.model.Settings;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class FinderApplication extends JFrame{
    
    FinderOntology ontology;
    FinderPanel finderPanel;
    LogoPanel logoPanel;
    Settings setting;
    
    public FinderApplication(File file){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(900, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("The Manchester Finder");
        
        setting = new Settings();
        finderPanel = new FinderPanel();
        setupLogoPanel();
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(finderPanel);
        
    }
    
    private void setupLogoPanel(){
        logoPanel = new LogoPanel(Toolkit.getDefaultToolkit().getImage(setting.getLogoLocation()));
        finderPanel.setLogoComponent(logoPanel);
    }
    // Testing
    public static void main(String[] arg){
        FinderApplication f = new FinderApplication(null);
        f.setVisible(true);
    }
}
