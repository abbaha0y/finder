/*
 * This class define the facetes within the ontology
 */
package uk.ac.man.cs.finderapplication.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import org.semanticweb.owlapi.model.OWLClass;

import uk.ac.man.cs.finderapplication.model.Facet;
import uk.ac.man.cs.finderapplication.model.FinderOntology;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class FacetsPanel extends JPanel {

    FinderOntology ontology;
    ArrayList<Facet> facetArrayList;
    ArrayList<JRadioButton> btnArrayList;
    JScrollPane scrollPanel;
    JPanel[] panels;
    JPanel mainPanel;
    Box box;
    ButtonGroup grbFacet;

    public FacetsPanel(FinderOntology ontology) {
        this.ontology = ontology;
        createUI();
    }

    private void createUI() {
        box = new Box(BoxLayout.Y_AXIS);

        this.setLayout(new BorderLayout());
        scrollPanel = new JScrollPane();

        facetArrayList = new ArrayList<>();
        btnArrayList = new ArrayList();

        ArrayList<OWLClass> arrayList = ontology.getFacets();

        mainPanel = new JPanel(new SpringLayout());
        /*mainPanel.add(new JRadioButton("All Result"));
         SpringUtilities.makeCompactGrid(mainPanel, 1, 1, 3, 3, 3, 3);
         mainPanel.setBorder(BorderFactory.createTitledBorder("All result"));
         btnArrayList.add(new JRadioButton("All Result"));
         box.add(mainPanel);*/

        mainPanel.setBorder(BorderFactory.createTitledBorder("Facets"));
        grbFacet = new ButtonGroup();
        JRadioButton rbtnFacet;

        JRadioButton allResultBtn = new JRadioButton("All Results");
        mainPanel.add(allResultBtn);
        grbFacet.add(allResultBtn);
        btnArrayList.add(allResultBtn);
        allResultBtn.setSelected(true);
        facetArrayList.add(null);

        for (OWLClass facet : arrayList) {
            rbtnFacet = new JRadioButton(ontology.render(facet));
            grbFacet.add(rbtnFacet);
            mainPanel.add(rbtnFacet);
            btnArrayList.add(rbtnFacet);
            //System.out.println(ontology.getFacetProperty(facet));
            facetArrayList.add(new Facet(facet, ontology.getFacetProperty(facet)));
        }

        SpringUtilities.makeCompactGrid(mainPanel, arrayList.size() + 1, 1, 3, 3, 3, 3);
        box.add(mainPanel);
        scrollPanel.add(box);
        this.add(new JScrollPane(box));
    }

    public ArrayList<JRadioButton> getFacetsBtns() {
        return btnArrayList;
    }

    public ArrayList<Facet> getFacets() {
        return facetArrayList;
    }
}
