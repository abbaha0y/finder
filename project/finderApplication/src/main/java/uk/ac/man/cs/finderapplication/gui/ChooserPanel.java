/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.man.cs.finderapplication.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Collection;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import uk.ac.man.cs.finderapplication.model.ChoiceModel;
import uk.ac.man.cs.finderapplication.model.FinderOntology;
import uk.ac.man.cs.finderapplication.selection.Selectable;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Oct 5, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class ChooserPanel extends JPanel {

    private FinderOntology ontology;

    private ChoiceModel choiceModel;

    private IngPanel ingPanel;

    private FinderApplication application;

    public ChooserPanel(FinderOntology ontology, FinderApplication application) {
		this.ontology = ontology;
		this.application = application;
		choiceModel = new ChoiceModel(ontology);
                //this.choiceModel = choiceModel;
		createUI();
	}

	protected void createUI() {
		setLayout(new BorderLayout());

        //add size Panel here

		ingPanel = new IngPanel(ontology);

		add(ingPanel);
		//setupQueryPanel();
    }


    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }
    
   public Selectable getSelectable(){
       return ingPanel;
   }

}

