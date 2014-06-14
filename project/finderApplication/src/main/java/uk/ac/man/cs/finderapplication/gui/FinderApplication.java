/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.man.cs.finderapplication.gui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.io.File;
import java.util.Collection;
import javax.swing.JFrame;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import uk.ac.man.cs.finderapplication.model.ChoiceModel;
import uk.ac.man.cs.finderapplication.model.FinderOntology;
import uk.ac.man.cs.finderapplication.model.Settings;
import uk.ac.man.cs.finderapplication.selection.Selectable;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class FinderApplication extends JFrame{
    
    FinderOntology ontology;
    FinderPanel finderPanel;
    LogoPanel logoPanel;
    Settings setting;
    
    Selectable selectable;
    ResultsPanel r;
    //private ChoiceModel choiceModel;
    
    public FinderApplication(File ontologyFile){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(900, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("The Manchester Finder");
        
        setting = new Settings();
        finderPanel = new FinderPanel();
        ontology = new FinderOntology();
        //choiceModel = new ChoiceModel(ontology);
        setupLogoPanel();
        setupChooserPanel(ontology);
        setupQueryPanel(ontology);
        setupResultsPanel(ontology);
        
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(finderPanel);
        
    }
    
    private void setupLogoPanel(){
        logoPanel = new LogoPanel(Toolkit.getDefaultToolkit().getImage(setting.getLogoLocation()));
        finderPanel.setLogoComponent(logoPanel);
    }
    
    private void setupChooserPanel(FinderOntology ont){
        ChooserPanel p = new ChooserPanel(ont, this);
        finderPanel.setButtomLeftComponent(p);
        setSelectable(p.getSelectable());
    }
    
    private void setupQueryPanel(FinderOntology ont){
        //pass the selectable to the queryPanel
        finderPanel.setButtomCenterComponent(new QueryPanel(ont,this,getSelecatable()));
    }
    
    private void setupResultsPanel(FinderOntology ont){
        r =  new ResultsPanel(ont, this);
        finderPanel.setButtomRightComponent(r);
    }
    
    private void setSelectable(Selectable selectable){
        this.selectable = selectable;
    }
    
    private Selectable getSelecatable(){
        return selectable;
    }
    
    public void showResultsPanel(Collection<OWLClass> results) {
		r.setPizzas(results);
    }
    
    // Testing
    public static void main(String[] arg){

        FinderApplication f = new FinderApplication(null);
        f.setVisible(true);
    }
}
