/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.man.cs.finderapplication.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import uk.ac.man.cs.finderapplication.selection.Selectable;
import uk.ac.man.cs.finderapplication.selection.SelectionListener;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class FiltersPanel extends JPanel implements ActionListener{
    OWLOntology ontology;
    Set<OWLClass> filters;
    ArrayList<JCheckBox> btnFilterArray;
    ArrayList<JLabel> lblFilterArray;
    JPanel titlePanel;
    JPanel filterPanel;
    JLabel lblTitle;
    
    private ArrayList selectionListeners = new ArrayList();
    
    public FiltersPanel(Set<OWLClass> filters, OWLOntology ontology){
        this.filters = filters;
        this.ontology = ontology;
        btnFilterArray = new ArrayList<JCheckBox>(filters.size());
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
            btnFilterArray.add(new JCheckBox(getFilterName(iterator.next())));
            //adding actionlisteners to checkboxes
            btnFilterArray.get(i).addActionListener(this);
            btnFilterArray.get(i).setActionCommand(""+i);
            /*btnFilterArray.get(i).addItemListener(new ItemListener(){
                @Override
                public void itemStateChanged(ItemEvent e) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
                
            });*/
            
            //adding components to the filterPanel
            filterPanel.add(btnFilterArray.get(i));
        }

        SpringUtilities.makeCompactGrid(filterPanel, filters.size(), 1, 3, 3, 3, 3);
        
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(filterPanel, BorderLayout.CENTER);
    }
    
    private String getFilterName(OWLClass filterClass){
        String filterName="";
        for(OWLAnnotation a:filterClass.getAnnotations(ontology)){
            if(a.getProperty().toString().contains("label")){
                filterName = a.getValue().toString().replace("\"", "");
            }
        }
        return filterName;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
    }
}
