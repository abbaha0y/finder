/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.man.cs.finderapplication.model;

import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class TreeFilterModel {
    ArrayList<OWLClass> filters;
    OWLOntology ontology;
    TreeModel originalModel, filteredModel;
    
    public TreeFilterModel(ArrayList<OWLClass> filters, OWLOntology ontology, TreeModel originalModel){
        this.filters = filters;
        this.ontology = ontology;
        this.originalModel = originalModel;
    }
    
    public void applyFilter(int filterIndex){
        DefaultMutableTreeNode node = getMatchingNode(((DefaultMutableTreeNode)originalModel.getRoot()),new DefaultMutableTreeNode((filters.get(filterIndex))));
        applyTreeFilter(node);
    }
    
    private void applyTreeFilter(DefaultMutableTreeNode filter){
        int count = originalModel.getChildCount(filter);
        //System.out.println(count);
        for(int i=0;i<count;i++){
            DefaultMutableTreeNode currnetNode = (DefaultMutableTreeNode) originalModel.getChild(filter, i);
            if(currnetNode.isLeaf()){
		filter.add(currnetNode);
                //System.out.println(currnetNode);
            }
            else{
		filter.add(currnetNode);
                //System.out.println(currnetNode);
		applyTreeFilter(currnetNode);
            }
        }
	filteredModel = new DefaultTreeModel(filter);
    }
    
    public void removeFilter(){
        filteredModel = originalModel;
    }
    
    private String getFilterName(OWLClass filterClass){
        String filterName = null;
        for(OWLAnnotation a:filterClass.getAnnotations(ontology)){
            if(a.getProperty().toString().equals("label")){
                filterName = a.getValue().toString().replace("\"", "");
            }
        }
        return filterName;
    }
    
    public TreeModel getTreeModel(){
        return filteredModel;
    }
    
    public DefaultMutableTreeNode getMatchingNode(DefaultMutableTreeNode root ,DefaultMutableTreeNode filterName){
        DefaultMutableTreeNode filterNode = null;
        int count = originalModel.getChildCount(root);
        for(int i=0;i<count;i++){
            DefaultMutableTreeNode currnetNode = (DefaultMutableTreeNode) originalModel.getChild(root, i);

            if((currnetNode.getUserObject()).equals(filterName.getUserObject())){
                return currnetNode;
            }
            else{

		filterNode = getMatchingNode(currnetNode, filterName);
            }
        }
        //System.out.println(count);
        return filterNode;
    }
}
