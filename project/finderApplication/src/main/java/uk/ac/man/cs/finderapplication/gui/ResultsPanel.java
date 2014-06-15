/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.man.cs.finderapplication.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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
import org.semanticweb.owlapi.model.OWLClass;
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

	private FinderApplication application;

	private Box box;


	public ResultsPanel(FinderOntology ontology, FinderApplication application) {
		this.ontology = ontology;
		this.application = application;
		createUI();
	}

	private void createUI() {
		setLayout(new BorderLayout(7, 7));
		box = new Box(BoxLayout.Y_AXIS);
		box.setBackground(Color.WHITE);
		add(new JScrollPane(box));
	}

	public void setPizzas(Collection<OWLClass> pizzas) {
		setPizzaPanels(createPizzaPanels(pizzas));
	}

    /**
     * Creates pizza panels according to returned result back from reasoner(Collection, e.g Pepproni pizza, SeaFoof Pizza)
     * it creates one pizza panel for each result (Named pizza in Ontology) and display the pizza toppings)
     * @param pizzas
     * @return
     */
	private PizzaPanel [] createPizzaPanels(Collection<OWLClass> pizzas) {
		PizzaPanel [] panels = new PizzaPanel[pizzas.size()];
		int counter = 0;
		for(Iterator it = pizzas.iterator(); it.hasNext(); counter++) {
			OWLClass cls = (OWLClass) it.next();
			panels[counter] = new PizzaPanel(ontology, cls);
		}
		return panels;
	}

	private void setPizzaPanels(PizzaPanel [] panels) {
		box.removeAll();
		for(int i = 0; i < panels.length; i++) {
			box.add(panels[i]);
			box.add(Box.createVerticalStrut(10));
		}
		revalidate();
	}
}

