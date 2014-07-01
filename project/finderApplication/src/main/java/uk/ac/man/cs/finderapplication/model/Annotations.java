/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.man.cs.finderapplication.model;

import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class Annotations {

    public static final String BASE_CLASS = "BaseClass";
    public static final String ING_CLASS = "IngClass";
    public static final String PROPERTY = "Property";
    public static final String ANNOTATION_PROPERTY = "hasRole";

    private OWLOntology ontology;
    private OWLDataFactory df;
    private OWLOntologyManager manager;
    

    private File ontologyFile;

    public Annotations(File ontologyFile) {
        this.ontologyFile = ontologyFile;
        loadOntology();
    }
    public Annotations(OWLOntology ontology){
        this.ontology = ontology;
    }

    private void loadOntology() {
        try {
            manager = OWLManager.createOWLOntologyManager();
            df = manager.getOWLDataFactory();
            System.out.println(new Settings().getOnologyLocation());
            ontology = manager.loadOntologyFromOntologyDocument(IRI.create(ontologyFile));
        } catch (final Throwable e) {
            Runnable runnable = new Runnable() {
                public void run() {
                    JOptionPane.showMessageDialog(null,
                            "Could not create the ontology.  (This probably happened\n"
                            + "because the ontology could not be accessed due to network\n"
                            + "problems.)\n"
                            + "[" + e.getMessage() + "]",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
    }

    public boolean checkAnnotationsExist() {
        boolean check = false;
        boolean baseClassFlag = false;
        boolean IngClassFlag = false;
        boolean PropertyFlag = false;
        for (OWLClass c : ontology.getClassesInSignature()) {
            for (OWLAnnotation a : c.getAnnotations(ontology)) {
                if (a.getValue().toString().contains(BASE_CLASS)) {
                    baseClassFlag = true;
                } else if (a.getValue().toString().contains(ING_CLASS)) {
                    IngClassFlag = true;
                }
            }
        }
        for (OWLObjectProperty o : ontology.getObjectPropertiesInSignature()) {
            for (OWLAnnotation a : o.getAnnotations(ontology)) {
                if (a.getValue().toString().contains(PROPERTY)) {
                    PropertyFlag = true;
                }
            }
        }
        if (baseClassFlag && IngClassFlag && PropertyFlag) {
            check = true;
        }
        return check;
    }

    public boolean checkFacetsExist() {
        boolean check = false;
        for (OWLClass c : ontology.getClassesInSignature()) {
            for (OWLAnnotation a : c.getAnnotations(ontology)) {
                if (a.getValue().toString().contains("facet")) {
                    check = true;
                } else {
                    check = false;
                }
            }
        }
        return check;
    }

    public static void main(String[] args) {
        Annotations ant = new Annotations(new File("/Users/machdd/Project/project/finderApplication/src/main/resources/week2-hierarchy-mbaxkha4.owl"));
        System.out.println(ant.checkAnnotationsExist());
    }
}
