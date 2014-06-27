/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.man.cs.finderapplication.gui;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class MyMutableTreeNode extends DefaultMutableTreeNode implements Comparable{
    
    boolean type;
    Object obj;
    
    public MyMutableTreeNode(Object obj, boolean type){
        super(obj);
        this.obj = obj;
        this.type = type;
    }

    public MyMutableTreeNode(MyMutableTreeNode filter, boolean b) {
        super(filter);
        this.type = type;
    }
    
    public boolean getType(){
        return type;
    }
    
    public void setType(boolean d){
        this.type = d;
    }
    
    @Override
    public int compareTo(Object o) {
        return obj.toString().compareTo(o.toString());
    }
}
