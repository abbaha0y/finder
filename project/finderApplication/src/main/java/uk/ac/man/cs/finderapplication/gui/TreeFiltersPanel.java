/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.man.cs.finderapplication.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import uk.ac.man.cs.finderapplication.model.FinderOntology;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class TreeFiltersPanel extends JPanel{
    OWLOntology ontology;
    ArrayList<OWLClass> filters;
    ArrayList<JCheckBox> btnFilterArray;
    ArrayList<JLabel> lblFilterArray;
    JPanel titlePanel;
    JPanel filterPanel;
    JLabel lblTitle;
    OWLClass selectedFilter;
    FinderOntology o;
    
    
    public TreeFiltersPanel(ArrayList<OWLClass> filters, FinderOntology o){
        this.filters = filters;
        //this.ontology = ontology;
        btnFilterArray = new ArrayList<>(filters.size());
        this.o = o;
        setupFilterPanel();
    }
    
    private void setupFilterPanel(){
        lblTitle = new JLabel("Filters");
        lblTitle.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,16));
        
        titlePanel = new JPanel(new FlowLayout());
        filterPanel = new JPanel(new SpringLayout());
        
        titlePanel.add(lblTitle);
        
        this.setLayout(new BorderLayout());
        
        Iterator<OWLClass> iterator = filters.iterator();
        for(int i=0;i<filters.size();i++){
            //adding checkboxes and filters names as labels to arrayLists
            btnFilterArray.add(new JCheckBox(getFilterName(filters.get(i))));
 
            //adding components to the filterPanel
            filterPanel.add(btnFilterArray.get(i));
        }

        SpringUtilities.makeCompactGrid(filterPanel, filters.size(), 1, 3, 3, 3, 3);
        
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(filterPanel, BorderLayout.CENTER);
    }
    
    private String getFilterName(OWLClass filterClass){
        String filterName="";
        /*for(OWLAnnotation a:filterClass.getAnnotations(ontology)){
            if(a.getProperty().toString().contains("label")){
                filterName = a.getValue().toString().replace("\"", "");
            }
        }*/
        filterName=o.render(filterClass);
        //System.out.println(filterName);
        return filterName;
    }
    
    public ArrayList<JCheckBox> getCheckBoxes(){
        return btnFilterArray;
    }
    
    public ArrayList<OWLClass> getFilters(){
        return filters;
    }
    
    public void setFilterLabel(String s){
        lblTitle.setText(s);
    }
}
