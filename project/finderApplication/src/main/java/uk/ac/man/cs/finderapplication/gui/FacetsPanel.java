/*
 * This class define the facetes within the ontology
 */
package uk.ac.man.cs.finderapplication.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
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
import uk.ac.man.cs.finderapplication.model.FinderOntology;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class FacetsPanel extends JPanel {

    FinderOntology ontology;
    JScrollPane scrollPanel;
    JPanel[] panels;
    Box box;

    public FacetsPanel(FinderOntology ontology) {
        this.ontology = ontology;
        createUI();
    }

    private void createUI() {
        box = new Box(BoxLayout.Y_AXIS);
        //box.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());
        scrollPanel = new JScrollPane();

        Map<OWLClass, ArrayList<OWLClass>> hashMap = ontology.getFacets();
        panels = new JPanel[hashMap.size()];
        Iterator it = hashMap.entrySet().iterator();
        
        for(int i=0 ;i< panels.length ; i++){
            panels[i] = new JPanel();
            panels[i].setLayout(new SpringLayout());
        }
        //System.out.println(hashMap.size());
        for (JPanel panel : panels) {
            Map.Entry pairs = (Map.Entry) it.next();
            //panel = new JPanel();
            //panel.setLayout(new SpringLayout());
            panel.setBorder(BorderFactory.createTitledBorder(ontology.render((OWLClass) pairs.getKey())));
            ArrayList<OWLClass> facets = (ArrayList<OWLClass>) pairs.getValue();
            ButtonGroup grbFacet = new ButtonGroup();
            for (OWLClass facet : facets) {
                JRadioButton rbtnFacet = new JRadioButton(ontology.render(facet));
                grbFacet.add(rbtnFacet);
                panel.add(rbtnFacet);
            }
            SpringUtilities.makeCompactGrid(panel, facets.size(), 1, 3, 3, 3, 3);
            box.add(panel);
        }
        scrollPanel.add(box);
        this.add(new JScrollPane(box));
    }

}
