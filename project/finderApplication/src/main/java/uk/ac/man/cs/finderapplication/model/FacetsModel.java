/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.man.cs.finderapplication.model;

import java.util.Collection;
import uk.ac.man.cs.finderapplication.gui.MainFinderApplication;


/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class FacetsModel {
    private MainFinderApplication application;
    private FinderOntology ontology;
    
    public FacetsModel(MainFinderApplication application, FinderOntology ontology){
        this.application = application;
        this.ontology = ontology;
    }
    
    public void applyFacet(Facet facet){
        Collection c = ontology.getFacetedFood(application.getQueryPanel().getIncluded(), application.getQueryPanel().getExcluded(), facet);
        application.showResultsPanel(c);
    }
    
    public void applyFacet(){
        Collection c = ontology.getPizzas(application.getQueryPanel().getIncluded(), application.getQueryPanel().getExcluded());
        application.showResultsPanel(c);
    }
}
