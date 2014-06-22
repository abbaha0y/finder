/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.man.cs.finderapplication.model;

import java.util.ArrayList;
import javax.swing.JRadioButtonMenuItem;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class LanguageModel {
    
    FinderOntology ontology;
    
    public LanguageModel(FinderOntology ontology){
        this.ontology = ontology;
    }
    
    public void displayLanguage(String lang){
        ontology.setupShortFormProvider(lang);
        //System.out.print(lang);
    }
}
