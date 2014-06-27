/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.man.cs.finderapplication.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import uk.ac.man.cs.finderapplication.controller.UIConfigurationController;
import uk.ac.man.cs.finderapplication.model.UIConfigurationModel;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class ConfigruationDialog extends JDialog {
    
    JButton button;
    ConfigruationPanel cp;
    UIConfigurationModel uiModel;
    ChooserPanel chp;
    QueryPanel qp;
    JFrame f;
    UIConfigurationController uicc;
            
    public ConfigruationDialog(Frame owner, ChooserPanel chp, QueryPanel qp) {
        super(owner, "Finder Application -- Configuration", true);
        this.f = (JFrame) owner;
        this.chp = chp;
        this.qp = qp;
        createUI();
    }

    public void createUI() {
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        cp = new ConfigruationPanel();
        contentPane.add(cp);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        button = new JButton("OK");
        uiModel = new UIConfigurationModel(chp, qp);
        uicc = new UIConfigurationController(uiModel,this);
        uicc.control();
        /*button = new JButton(new AbstractAction("OK") {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });*/
        buttonPanel.add(button);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 12));
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }
    
    public JButton getOKBtn(){
        return button;
    }
    public String getFilterText(){
        return cp.getFilterText();
    }
    public String getIncText(){
        return cp.getIncText();
    }
    public String getExcText(){
        return cp.getExcText();
    }
    public String getAddTxet(){
        return cp.getAddTxet();
    }
    public String getRemoveTxet(){
        return cp.getRemoveTxet();
    }
    public String getGetTxet(){
        return cp.getGetTxet();
    }
}
