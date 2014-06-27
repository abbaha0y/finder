/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.man.cs.finderapplication.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import uk.ac.man.cs.finderapplication.gui.ChooserPanel;
import uk.ac.man.cs.finderapplication.gui.ConfigruationDialog;
import uk.ac.man.cs.finderapplication.gui.QueryPanel;
import uk.ac.man.cs.finderapplication.model.UIConfigurationModel;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class UIConfigurationController {

    private ActionListener actionListener;
    private UIConfigurationModel model;
    private ConfigruationDialog cd;

    public UIConfigurationController(UIConfigurationModel model,
            ConfigruationDialog cd) {
        this.model = model;
        this.cd = cd;
    }

    public void control() {
        actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                linkConfigAndViews();
            }
        };
        cd.getOKBtn().addActionListener(actionListener);
    }

    private void linkConfigAndViews() {
        model.doChages(cd.getAddTxet(),
                cd.getRemoveTxet(),
                cd.getGetTxet(),
                cd.getFilterText(), 
                cd.getIncText(), 
                cd.getExcText());
        cd.dispose();
    }
}
