/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.man.cs.finderapplication.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import uk.ac.man.cs.finderapplication.gui.FacetsPanel;
import uk.ac.man.cs.finderapplication.model.Facet;
import uk.ac.man.cs.finderapplication.model.FacetsModel;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class FacetsController {

    private ActionListener actionListener;
    private FacetsModel model;
    private FacetsPanel view;
    ArrayList<JButton> arrayListFacetsBtns;
    ArrayList<Facet> arrayListFacets;

    public FacetsController(FacetsModel model, FacetsPanel view) {
        this.model = model;
        this.view = view;
        arrayListFacets = view.getFacets();
    }

    public void control() {
        actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                linkFacetsAndView(Integer.parseInt(actionEvent.getActionCommand()),actionEvent);
                //linkRadioButtonsAndView(Integer.parseInt(actionEvent.getActionCommand()),actionEvent);
            }
        };
        arrayListFacetsBtns = view.getFacetsBtns();
        for (int i = 0; i < arrayListFacetsBtns.size(); i++) {
            arrayListFacetsBtns.get(i).addActionListener(actionListener);
            arrayListFacetsBtns.get(i).setActionCommand("" + i);
            //System.out.println(arrayListFacetsBtns.get(i).getText());
        }
    }

    public void linkFacetsAndView(int index, ActionEvent e) {
        model.applyFacet(arrayListFacets.get(index));
        //System.out.println("here");
    }
}
