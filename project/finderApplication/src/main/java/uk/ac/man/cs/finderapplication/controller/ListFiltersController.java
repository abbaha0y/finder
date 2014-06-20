/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.man.cs.finderapplication.controller;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import uk.ac.man.cs.finderapplication.gui.ChooserPanel;
import uk.ac.man.cs.finderapplication.model.ListFilterModel;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class ListFiltersController {
    private ListFilterModel model;
    private ChooserPanel view;
    private DocumentListener documentListener;
    
    public ListFiltersController(ListFilterModel model, ChooserPanel view){
        this.model = model;
        this.view = view;            
    }
    
    public void contol(){
        documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                linkTextFieldAndList(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                linkTextFieldAndList(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                linkTextFieldAndList(e);
            }
        };                
        view.getListFilterPanel().getFilter().getDocument().addDocumentListener(documentListener);  
    }
    
    private void linkTextFieldAndList(DocumentEvent e){
        model.refilter();
        view.getIngPanel().setListModel(model.getListModel());
        view.getIngPanel().getList().setSelectedIndex(1);
    }
    
}
