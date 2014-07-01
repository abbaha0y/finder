/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.man.cs.finderapplication.model;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class Facet {
    private OWLClass facetClass;
    private OWLObjectProperty facetProperty;
    
    public Facet(OWLClass facetClass, OWLObjectProperty facetProperty){
        this.facetClass = facetClass;
        this.facetProperty = facetProperty;
    }
    
    public OWLClass getFacetClass(){
        return facetClass;
    }
    
    public OWLObjectProperty getFacetProperty(){
        return facetProperty;
    }
}
