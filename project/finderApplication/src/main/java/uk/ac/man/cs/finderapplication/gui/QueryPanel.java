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
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class QueryPanel extends JPanel {

	private FinderOntology ontology;

	private ChoiceModel choiceModel;

	private IngPanel ingPanel;

	private ListPanel includeListPanel;

	private ListPanel excludeListPanel;

	private FinderApplication application;
        
        private Selectable selectable;

    public QueryPanel(FinderOntology ontology, FinderApplication application, Selectable selectable) {
		this.ontology = ontology;
		this.application = application;
		choiceModel = new ChoiceModel(ontology);
                //this.choiceModel = choiceModel;
                this.selectable = selectable;
		createUI();
	}

	protected void createUI() {
            setLayout(new BorderLayout());

            //add size Panel here
            ingPanel = new IngPanel(ontology);
            Box box = new Box(BoxLayout.Y_AXIS);
            includeListPanel = new IncludeListPanel(ontology, selectable, choiceModel);
            box.add(includeListPanel);

            box.add(Box.createVerticalStrut(12));
            excludeListPanel = new ExcludeListPanel(ontology, selectable, choiceModel);
		box.add(excludeListPanel);
            box.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 12));
		add(box);
		setupQueryPanel();
    }


    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }


    protected void setupQueryPanel() {
        final JButton btnGet = new JButton();
        JPanel queryPanel = new JPanel();
        queryPanel.setLayout(new BorderLayout());
        queryPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        
        String[] s = {"Sushi","Sushi Dish"};
        final JComboBox templates = new JComboBox(s);
        JLabel lblTemplates = new JLabel("Templates: ");
        JPanel templatesPanel = new JPanel();
        templatesPanel.setLayout(new BorderLayout());
        templatesPanel.add(lblTemplates,BorderLayout.WEST);
        templatesPanel.add(templates,BorderLayout.CENTER);
        Action templatesAction = new AbstractAction(){
            public void actionPerformed(ActionEvent e) {
                   btnGet.setText("Get "+templates.getSelectedItem().toString());
            }
        };
        templates.setAction(templatesAction);
        
        
        Action queryReasonerAction = new AbstractAction("Get "+templates.getItemAt(0).toString()) {
            public void actionPerformed(ActionEvent e) {
                Collection c = ontology.getPizzas(choiceModel.getIncluded(), choiceModel.getExcluded());
                application.showResultsPanel(c);
            }
        };
        
        
        btnGet.setAction(queryReasonerAction);
        queryPanel.add(templatesPanel,BorderLayout.WEST);
        queryPanel.add(btnGet, BorderLayout.EAST);
        add(queryPanel, BorderLayout.SOUTH);
    }
}


