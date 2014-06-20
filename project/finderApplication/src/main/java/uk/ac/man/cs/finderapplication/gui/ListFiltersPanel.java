/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.man.cs.finderapplication.gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class ListFiltersPanel extends JPanel{
    
    private JTextField filterField;
    
    public ListFiltersPanel(){
        this.setLayout(new BorderLayout());
        this.add(filterField = new JTextField(20));
    }
    
    public JTextField getFilter(){
        return filterField;
    }
}
