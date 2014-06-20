/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.man.cs.finderapplication.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.tree.TreeModel;
import org.semanticweb.owlapi.model.OWLClass;
import uk.ac.man.cs.finderapplication.gui.ChooserPanel;
import uk.ac.man.cs.finderapplication.model.TreeFilterModel;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class TreeFiltersController {
    private TreeFilterModel filterModel;
    private ChooserPanel chooserPanel;
    private ActionListener actionListener;
    ArrayList<JCheckBox> checkBoxes;
    ArrayList<OWLClass> filters;
    
    public TreeFiltersController(TreeFilterModel filterModel, ChooserPanel chooserPanel){
        this.filterModel = filterModel;
        this.chooserPanel = chooserPanel;
        filters = chooserPanel.getFilterPanel().getFilters();
    }
    
    public void contol(){        
        actionListener = new ActionListener() {
              public void actionPerformed(ActionEvent actionEvent) {                  
                  linkCheckBoxesAndTree(Integer.parseInt(actionEvent.getActionCommand()), actionEvent);
              }
        };
        checkBoxes = chooserPanel.getFilterPanel().getCheckBoxes();
        for(int i=0;i<checkBoxes.size();i++){
            checkBoxes.get(i).addActionListener(actionListener);
            checkBoxes.get(i).setActionCommand(""+i);
        }  
    }
    
    private void linkCheckBoxesAndTree(int index, ActionEvent e){
        TreeModel model;
        if(checkBoxes.get(Integer.parseInt(e.getActionCommand())).isSelected()){
            //disable and uncheck all others
            for(int i=0;i<filters.size();i++){
                if(Integer.parseInt(e.getActionCommand()) != i){
                    checkBoxes.get(i).setEnabled(false);
                    checkBoxes.get(i).setSelected(false);
                }
                else{
                    //selectedFilter = filters.get(i);
                }
            }
            filterModel.applyFilter(index); 
        }
        else
        {
            //enable in case of uncheck
            for(int i=0;i<filters.size();i++){
                if(Integer.parseInt(e.getActionCommand()) != i){
                    checkBoxes.get(i).setEnabled(true);
                    //btnFilterArray.get(i).setSelected(true);
                }
            }
            filterModel.removeFilter();
        }
        //filterModel.applyFilter(index);                
        chooserPanel.getIngPanel().setTreeModel(filterModel.getTreeModel());
    }  
}
