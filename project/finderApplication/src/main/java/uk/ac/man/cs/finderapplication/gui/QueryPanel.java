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
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import org.semanticweb.owlapi.model.OWLClass;
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

	private MainFinderApplication application;
        
        private Selectable selectable;
        
        private JButton btnGet;

    public QueryPanel(FinderOntology ontology, MainFinderApplication application, Selectable selectable) {
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
            //ingPanel = new IngPanel(ontology);
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
        btnGet = new JButton();
        JPanel queryPanel = new JPanel();
        queryPanel.setLayout(new BorderLayout());
        queryPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        
        //String[] s = {"Sushi","Sushi Dish"};
        //final JComboBox templates = new JComboBox(s);
        //JLabel lblTemplates = new JLabel("Templates: ");
        //JPanel templatesPanel = new JPanel();
        //templatesPanel.setLayout(new BorderLayout());
        //templatesPanel.add(lblTemplates,BorderLayout.WEST);
        //templatesPanel.add(templates,BorderLayout.CENTER);
        /*Action templatesAction = new AbstractAction(){
            public void actionPerformed(ActionEvent e) {
                   btnGet.setText("Get "+templates.getSelectedItem().toString());
            }
        };
        templates.setAction(templatesAction);*/
        
        
        Action queryReasonerAction = new AbstractAction("Get "+ontology.render(ontology.getBaseClass())) {
            public void actionPerformed(ActionEvent e) {
                Collection c = ontology.getPizzas(choiceModel.getIncluded(), choiceModel.getExcluded());
                //Collection c = ontology.getFacetedFood(choiceModel.getIncluded(), choiceModel.getExcluded(), )
                application.showResultsPanel(c);
            }
        };
        
        
        btnGet.setAction(queryReasonerAction);
        //queryPanel.add(templatesPanel,BorderLayout.WEST);
        queryPanel.add(btnGet, BorderLayout.EAST);
        add(queryPanel, BorderLayout.SOUTH);
    }
    
    public Set<OWLClass> getIncluded(){
        return choiceModel.getIncluded();
    }
    public Set<OWLClass> getExcluded(){
        return choiceModel.getExcluded();
    }
    
    public JList getIncList(){
        return includeListPanel.getList();
    }
    
    public JList getExcList(){
        return excludeListPanel.getList();
    }
    
    public void refershResult(){
        Collection c = ontology.getPizzas(choiceModel.getIncluded(), choiceModel.getExcluded());
        application.showResultsPanel(c);
    }
    
    
    public void setIncAddBtn(String s){
        includeListPanel.setTextAddBtn(s);
    }
    public void setIncRemBtn(String s){
        includeListPanel.setTextRemoveBtn(s);
    }
    
    public void setExcAddBtn(String s){
        excludeListPanel.setTextAddBtn(s);
    }
    public void setExcRemBtn(String s){
        excludeListPanel.setTextRemoveBtn(s);
    }
    
    public void setLabelInc(String s){
        includeListPanel.setLabelIng(s);
    }
    public void setLabelExc(String s){
        excludeListPanel.setLabelIng(s);
    }
    
    public void setGetBtn(String s){
        btnGet.setText(s);
    }
    
}


