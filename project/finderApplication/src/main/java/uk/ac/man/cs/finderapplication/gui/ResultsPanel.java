/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.man.cs.finderapplication.gui;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.semanticweb.owlapi.model.OWLClass;
import uk.ac.man.cs.finderapplication.controller.FacetsController;
import uk.ac.man.cs.finderapplication.model.Annotations;
import uk.ac.man.cs.finderapplication.model.FacetsModel;
import uk.ac.man.cs.finderapplication.model.FinderOntology;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Oct 6, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class ResultsPanel extends JPanel {

    private FinderOntology ontology;

    private MainFinderApplication application;

    private Box box;

    private PizzaPanel[] panels;

    private JSplitPane split;

    private FacetsController facetController;
    private FacetsModel facetModel;

    public ResultsPanel(FinderOntology ontology, MainFinderApplication application) {
        this.ontology = ontology;
        this.application = application;

        createUI();
    }

    private void createUI() {

        split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setDividerSize(0);
        split.setBorder(null);

        setLayout(new BorderLayout(7, 7));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        Action backAction = new AbstractAction("Back") {
            public void actionPerformed(ActionEvent e) {
                application.showIngPanel();
            }
        };
        buttonPanel.add(new JButton(backAction));
        add(buttonPanel, BorderLayout.SOUTH);
        box = new Box(BoxLayout.Y_AXIS);
        box.setBackground(Color.WHITE);
        split.setRightComponent(new JScrollPane(box));
        add(split);
        //Annotations a = new Annotations(ontology.getOntology());
        //if (a.checkFacetsExist()) {
            setupFacets();
        //}
    }

    public void setPizzas(Collection<OWLClass> pizzas) {
        setPizzaPanels(createPizzaPanels(pizzas));
    }

    /**
     * Creates pizza panels according to returned result back from
     * reasoner(Collection, e.g Pepproni pizza, SeaFoof Pizza) it creates one
     * pizza panel for each result (Named pizza in Ontology) and display the
     * pizza toppings)
     *
     * @param pizzas
     * @return
     */
    private PizzaPanel[] createPizzaPanels(Collection<OWLClass> pizzas) {
        panels = new PizzaPanel[pizzas.size()];
        int counter = 0;
        for (Iterator it = pizzas.iterator(); it.hasNext(); counter++) {
            OWLClass cls = (OWLClass) it.next();
            panels[counter] = new PizzaPanel(ontology, cls);
        }
        return panels;
    }

    protected void setPizzaPanels(PizzaPanel[] panels) {
        box.removeAll();
        for (int i = 0; i < panels.length; i++) {
            box.add(panels[i]);
            box.add(Box.createVerticalStrut(10));
        }
        revalidate();
    }

    public PizzaPanel[] getPanels() {
        return panels;
    }

    private void setupFacets() {
        FacetsPanel fp = new FacetsPanel(ontology);
        facetModel = new FacetsModel(application, ontology);
        facetController = new FacetsController(facetModel, fp);
        facetController.control();
        split.setLeftComponent(fp);
    }

}
