/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.man.cs.finderapplication.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.SpringLayout;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class ConfigruationPanel extends JPanel {

    JPanel lblPanel;
    JPanel btnPanel;
    //------------
    JLabel lblFilter,lblInc, lblExc;
    JTextField txtFilter,txtInc, txtExc;
    //------------
    JLabel lblAdd,lblRemove, lblGet;
    JTextField txtAdd,txtRemove, txtGet;
    //------------
    JSplitPane spiltPane;

    public ConfigruationPanel() {
        buildUI();
    }

    private void buildUI() {
        this.setLayout(new BorderLayout());
        
        spiltPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        spiltPane.setDividerSize(0);
        
        setupLabelPanel();
        setupButtonPanel();
        spiltPane.setLeftComponent(lblPanel);
        spiltPane.setRightComponent(btnPanel);
        this.add(spiltPane);

    }
    
    private void setupLabelPanel(){
        lblPanel = new JPanel(new SpringLayout());
        lblPanel.setBorder(BorderFactory.createTitledBorder("Change Labels"));
        
        lblFilter = new JLabel("Filters:", JLabel.TRAILING);
        lblInc = new JLabel("Includes:", JLabel.TRAILING);
        lblExc = new JLabel("Excludes:", JLabel.TRAILING);
        
        txtFilter = new JTextField(20);
        txtInc = new JTextField(20);
        txtExc = new JTextField(20);
        
        lblPanel.add(lblFilter);
        lblPanel.add(txtFilter);
        lblPanel.add(lblInc);
        lblPanel.add(txtInc);
        lblPanel.add(lblExc);
        lblPanel.add(txtExc);
        
        SpringUtilities.makeCompactGrid(lblPanel, 3, 2, 6, 6, 10, 10);
    }
    
    public void setupButtonPanel(){
        btnPanel = new JPanel(new SpringLayout());
        btnPanel.setBorder(BorderFactory.createTitledBorder("Change Buttons"));
        
        
        lblAdd = new JLabel("Add:", JLabel.TRAILING);
        lblRemove = new JLabel("Remove:", JLabel.TRAILING);
        lblGet = new JLabel("Get:", JLabel.TRAILING);
        
        txtAdd = new JTextField(20);
        txtRemove = new JTextField(20);
        txtGet = new JTextField(20);
        
        btnPanel.add(lblAdd);
        btnPanel.add(txtAdd);
        btnPanel.add(lblRemove);
        btnPanel.add(txtRemove);
        btnPanel.add(lblGet);
        btnPanel.add(txtGet);
        
        SpringUtilities.makeCompactGrid(btnPanel, 3, 2, 6, 6, 10, 10);
    }
    
    public String getFilterText(){
        return txtFilter.getText().trim();
    }
    public String getIncText(){
        return txtInc.getText().trim();
    }
    public String getExcText(){
        return txtExc.getText().trim();
    }
    public String getAddTxet(){
        return txtAdd.getText().trim();
    }
    public String getRemoveTxet(){
        return txtRemove.getText().trim();
    }
    public String getGetTxet(){
        return txtGet.getText().trim();
    }
    
    public static void main(String[] args) {
        JFrame f = new JFrame("Test");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        ConfigruationPanel cp = new ConfigruationPanel();
        f.getContentPane().add(cp);
        f.pack();
        f.setVisible(true);
    }
}
