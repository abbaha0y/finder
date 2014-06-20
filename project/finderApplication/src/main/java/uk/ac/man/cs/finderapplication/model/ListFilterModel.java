/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.man.cs.finderapplication.model;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListModel;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class ListFilterModel extends AbstractListModel{
    private ArrayList filteredModel,originalModel;
    JTextField filter;

    public ListFilterModel(DefaultListModel model, JTextField filter){
        this.originalModel = new ArrayList(Arrays.asList(model.toArray()));
        this.filteredModel = new ArrayList();
        this.filter = filter;
    }
    
    public void addElement(Object o){
        originalModel.add(o);
        refilter();
    }
    
    public void refilter(){
        filteredModel.clear();
        String term = filter.getText();
        for(int i=0; i<originalModel.size();i++){
            //System.out.println(getOWLClassName(originalModel.get(i).toString()));
            if(getOWLClassName(originalModel.get(i).toString()).indexOf(term,0) != -1){
                filteredModel.add(originalModel.get(i));
            }
        }
        //list.setSelectedIndex(-1);
        fireContentsChanged(this, 0, getSize());
    }
    
    public ListModel getListModel(){
        DefaultListModel listModel = new DefaultListModel();
        for(int i=0;i<filteredModel.size();i++){
            listModel.addElement(filteredModel.get(i));
        }
        
        return listModel;
    }

    @Override
    public int getSize() {
        return filteredModel.size();
    }

    @Override
    public Object getElementAt(int index) {
        if(index < filteredModel.size()){
            return filteredModel.get(index);
        }
        else{
            return null;
        }
    }
    
    private String getOWLClassName(String s){
        return s.substring(s.indexOf("#")+1, s.indexOf(">"));
    }
}
