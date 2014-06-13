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
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import uk.ac.man.cs.finderapplication.model.ChoiceModel;
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
    
    private ChoiceModel choiceModel;
    
    public FinderApplication(File ontologyFile){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(900, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("The Manchester Finder");
        
        setting = new Settings();
        finderPanel = new FinderPanel();
        ontology = new FinderOntology();
        choiceModel = new ChoiceModel(ontology);
        setupLogoPanel();
        setupChooserPanel(ontology);
        
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(finderPanel);
        
    }
    
    private void setupLogoPanel(){
        logoPanel = new LogoPanel(Toolkit.getDefaultToolkit().getImage(setting.getLogoLocation()));
        finderPanel.setLogoComponent(logoPanel);
    }
    
    private void setupChooserPanel(FinderOntology ont){
        finderPanel.setButtomLeftComponent(new ChooserPanel(ont, this,choiceModel));
        finderPanel.setButtomCenterComponent(new QueryPanel(ont,this,choiceModel));
    }
    // Testing
    public static void main(String[] arg){

        FinderApplication f = new FinderApplication(null);
        f.setVisible(true);
    }
}
