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
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import uk.ac.man.cs.finderapplication.controller.ListFiltersController;
import uk.ac.man.cs.finderapplication.controller.TreeFiltersController;
import uk.ac.man.cs.finderapplication.model.ChoiceModel;
import uk.ac.man.cs.finderapplication.model.FinderOntology;
import uk.ac.man.cs.finderapplication.model.ListFilterModel;
import uk.ac.man.cs.finderapplication.model.TreeFilterModel;
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
    
    private JSplitPane splitPane;
    
    private TreeFiltersPanel filterPanel;
    
    TreeFilterModel filterModel;
    TreeFiltersController controller;
    
    private boolean view;
    
    ListFilterModel listFilterModel;
    ListFiltersController listFiltersController;
    private ListFiltersPanel listFilterPanel;
    

    public ChooserPanel(FinderOntology ontology, FinderApplication application, boolean view) {
	this.view = view;	
            this.ontology = ontology;
		this.application = application;
		choiceModel = new ChoiceModel(ontology);
                //this.choiceModel = choiceModel;
		createUI();
	}

	protected void createUI() {
		setLayout(new BorderLayout());

        //add size Panel here
                splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
                //splitPane.setDividerLocation(200);
                splitPane.setDividerSize(0);
                
		ingPanel = new IngPanel(ontology,view);
                splitPane.setBottomComponent(ingPanel);
                
                if(ontology.FilterExists() && view){
                    filterPanel = new TreeFiltersPanel(ontology.getFilters(), ontology);
                    splitPane.setTopComponent(filterPanel);
                    filterModel = new TreeFilterModel(filterPanel.getFilters(), ontology.getOntology(), ingPanel.getTreeModel());
                    
                    controller = new TreeFiltersController(filterModel, this);
                    controller.contol();
                }
                if(!view){
                    listFilterPanel = new ListFiltersPanel();
                    splitPane.setTopComponent(listFilterPanel);
                    listFilterModel = new ListFilterModel((DefaultListModel) ingPanel.getListModel(),listFilterPanel.getFilter());
                    
                    
                    listFiltersController = new ListFiltersController(listFilterModel, this);
                    listFiltersController.contol();
                }
		add(splitPane);
                
		//setupQueryPanel();
    }


    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }
    
   public Selectable getSelectable(){
       return ingPanel;
   }
   
   public IngPanel getIngPanel(){
       return ingPanel;
   }
   
   public TreeFiltersPanel getFilterPanel(){
       return filterPanel;
   }
   
   public ListFiltersPanel getListFilterPanel(){
       return listFilterPanel;
   }
   
   public JTree getTree(){
       return ingPanel.getTree();
   }
   
   public JList getList(){
       return ingPanel.getList();
   }
   
   public void refreshFilters(){
        filterPanel = new TreeFiltersPanel(ontology.getFilters(), ontology);
        splitPane.setTopComponent(filterPanel);
        filterModel = new TreeFilterModel(filterPanel.getFilters(), ontology.getOntology(), ingPanel.getTreeModel());
                    
        controller = new TreeFiltersController(filterModel, this);
        controller.contol();
   }
   
}

