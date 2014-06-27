/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.man.cs.finderapplication.model;

import java.util.ArrayList;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import uk.ac.man.cs.finderapplication.gui.MyMutableTreeNode;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class TreeFilterModel {
    ArrayList<OWLClass> filters;
    OWLOntology ontology;
    TreeModel originalModel, tempModel;
    JTree tree;
    
    public TreeFilterModel(ArrayList<OWLClass> filters, OWLOntology ontology, JTree tree){
        this.filters = filters;
        this.ontology = ontology;
        this.originalModel = tree.getModel();
        this.tempModel = originalModel;
        this.tree = tree;
        
    }
    
    public void applyFilter(int filterIndex){
        MyMutableTreeNode node = (MyMutableTreeNode) getMatchingNode(((DefaultMutableTreeNode)originalModel.getRoot()),new DefaultMutableTreeNode((filters.get(filterIndex))));
        node.setType(true);
        applyTreeFilter(node);
    }
    
    public void undoFilter(){
        MyMutableTreeNode root = (MyMutableTreeNode) originalModel.getRoot();
        root.setType(false);
        removeFilter(root);
    }
    
    private void applyTreeFilter(MyMutableTreeNode filter){
        //filter
        int count = originalModel.getChildCount(filter);
        for(int i=0;i<count;i++){
            MyMutableTreeNode currnetNode = (MyMutableTreeNode) originalModel.getChild(filter, i);
            currnetNode.setType(true);
            if(currnetNode.isLeaf()){
            }
            else{
		applyTreeFilter(currnetNode);
            }
        }
	tree.setSelectionPath(new TreePath(filter.getPath()));
    }
    
    private void removeFilter(MyMutableTreeNode root){
        int count = root.getChildCount();
        for(int i =0;i<count; i++){
            MyMutableTreeNode currnetNode = (MyMutableTreeNode) originalModel.getChild(root, i);
            currnetNode.setType(false);
            if(!currnetNode.isLeaf()){
                removeFilter(currnetNode);
            }
        }
        //tree.collapsePath(new TreePath(root.getPath()));
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
        return originalModel;
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
