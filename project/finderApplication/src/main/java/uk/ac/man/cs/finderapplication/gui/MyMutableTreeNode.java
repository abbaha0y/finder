/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.man.cs.finderapplication.gui;

import javax.swing.tree.DefaultMutableTreeNode;
//import javax.swing.tree.TreeNode;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class MyMutableTreeNode extends DefaultMutableTreeNode implements Comparable {

    protected boolean type;
    protected Object obj;
    protected boolean enabled;

    public MyMutableTreeNode() {
        this(null, true, false, true);
    }

    public MyMutableTreeNode(Object obj, boolean type) {
        //super(obj);
        this(obj, true, type, true);
        this.obj = obj;
        this.type = type;
    }

    public MyMutableTreeNode(MyMutableTreeNode node, boolean type) {
        super(node);
        //this(node, true, type, true);
        this.type = type;
    }

    public MyMutableTreeNode(Object obj, boolean allowsChildren, boolean type, boolean enabled) {
        super(obj, allowsChildren);
        this.type = type;
        this.enabled = enabled;
    }

    public MyMutableTreeNode(MyMutableTreeNode node, boolean allowsChildren, boolean type) {
        this(node, allowsChildren, type, true);
    }

    public boolean getType() {
        return type;
    }

    public void setType(boolean d) {
        this.type = d;
    }

    public int getChildCount() {
        // make disabled node to have children
        //if (enabled) {
            return super.getChildCount();
        //} else {
        //    return 0;
        //}
    }

    public boolean isLeaf() {
        return (super.getChildCount() == 0);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public int compareTo(Object o) {
        return obj.toString().compareTo(o.toString());
    }
}
