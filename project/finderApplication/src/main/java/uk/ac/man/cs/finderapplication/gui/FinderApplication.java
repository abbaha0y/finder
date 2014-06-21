/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.man.cs.finderapplication.gui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.semanticweb.owlapi.model.OWLClass;
import uk.ac.man.cs.finderapplication.model.FinderOntology;
import uk.ac.man.cs.finderapplication.model.Settings;
import uk.ac.man.cs.finderapplication.selection.Selectable;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class FinderApplication extends JFrame implements ActionListener{
    
    FinderOntology ontology;
    FinderPanel finderPanel;
    LogoPanel logoPanel;
    Settings setting;
    
    Selectable selectable;
    ResultsPanel r;
    JFileChooser fc;
    //private ChoiceModel choiceModel;
    
    ChooserPanel p;
    
    JMenuItem menuItemImport,menuItemExit,menuItemUIConfig;
    ImageIcon importImage;
    
    JRadioButtonMenuItem rbMenuItemTreeView,rbMenuItemListView;
    private ArrayList<JRadioButtonMenuItem> languages;
    boolean view;
    
    String homefilepath = System.getProperty("user.home")+"/FinderApplication";
    
    public FinderApplication(File ontologyFile, Settings setting){
        view = true;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(900, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("The Manchester Finder");
        
        this.setting = setting;
        finderPanel = new FinderPanel();
        ontology = new FinderOntology();
        //choiceModel = new ChoiceModel(ontology);
        setupMenuBar();
        setupLogoPanel();
        setupChooserPanel(ontology);
        setupQueryPanel(ontology);
        setupResultsPanel(ontology);
        
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(finderPanel);
        
    }
    
    private void setupMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu menuFile = new JMenu("File");
	menuBar.add(menuFile);
        
        menuItemImport = new JMenuItem("Import new ontology");
        //importImage = new ImageIcon(getClass().getClassLoader().getResource("./import.png"));
        menuItemImport.setIcon(importImage);
        menuFile.add(menuItemImport);
        menuItemImport.addActionListener(this);
        
        menuFile.addSeparator();
        
        menuItemExit = new JMenuItem("Exit");
        //importImage = new ImageIcon(getClass().getClassLoader().getResource("./exit.png"));
        menuItemExit.setIcon(importImage);
        menuFile.add(menuItemExit);
        menuItemExit.addActionListener(this);
        
        ButtonGroup group = new ButtonGroup();
        JMenu menuView = new JMenu("View");
        
        rbMenuItemTreeView = new JRadioButtonMenuItem("Tree View");
        rbMenuItemTreeView.addActionListener(this);
        rbMenuItemListView = new JRadioButtonMenuItem("List View");
        rbMenuItemListView.addActionListener(this);
        rbMenuItemTreeView.setSelected(true);
        group.add(rbMenuItemTreeView);
        menuView.add(rbMenuItemTreeView);
        group.add(rbMenuItemListView);
        menuView.add(rbMenuItemListView);
	menuBar.add(menuView);
        
        
        JMenu menuConfiguration = new JMenu("Configuration");
	menuBar.add(menuConfiguration);
        
        menuItemUIConfig = new JMenuItem("UI Configuration");
        //importImage = new ImageIcon(getClass().getClassLoader().getResource("./uiConfig.png"));
        menuItemUIConfig.setIcon(importImage);
        menuConfiguration.add(menuItemUIConfig);
        
        ButtonGroup groupLanguages = new ButtonGroup();
        JMenu menuItemLanguages = new JMenu("Languages");
        menuConfiguration.add(menuItemLanguages);
        
        languages = new ArrayList<>(ontology.getLanguages().size());
        
        Iterator it = ontology.getLanguages().entrySet().iterator();
        //System.out.println(ontology.getLanguages().size());
        for(int i=0; i<ontology.getLanguages().size(); i++){
            Map.Entry pairs = (Map.Entry)it.next();
            JRadioButtonMenuItem temp = new JRadioButtonMenuItem(String.format("%.2f",pairs.getValue())+"%"+"\t"+pairs.getKey().toString());
            languages.add(temp);
            groupLanguages.add(languages.get(i));
            menuItemLanguages.add(languages.get(i));
        }
        //System.out.println(ontology.getLanguages());
    }
    
    private void setupLogoPanel(){
        logoPanel = new LogoPanel(Toolkit.getDefaultToolkit().getImage(setting.getLogoLocation()));
        finderPanel.setLogoComponent(logoPanel);
    }
    
    private void setupChooserPanel(FinderOntology ont){
        p = new ChooserPanel(ont, this, view);
        finderPanel.setDividerLocationButtomLeft();
        finderPanel.setButtomLeftComponent(p);
        setSelectable(p.getSelectable());
    }
    
    private void setupQueryPanel(FinderOntology ont){
        //pass the selectable to the queryPanel
        finderPanel.setButtomCenterComponent(new QueryPanel(ont,this,getSelecatable()));
    }
    
    private void setupResultsPanel(FinderOntology ont){
        r =  new ResultsPanel(ont, this);
        finderPanel.setButtomRightComponent(r);
    }
    
    private void setSelectable(Selectable selectable){
        this.selectable = selectable;
    }
    
    private Selectable getSelecatable(){
        return selectable;
    }
    
    public void showResultsPanel(Collection<OWLClass> results) {
	r.setPizzas(results);
    }
    
    // Testing
    //public static void main(String[] arg){

    //    FinderApplication f = new FinderApplication(null);
    //    f.setVisible(true);
    //}

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==menuItemImport){
            fc = new JFileChooser();
            fc.setCurrentDirectory(new File(System.getProperty("user.home")));
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fc.setAcceptAllFileFilterUsed(false);
            fc.setFileFilter(new FileNameExtensionFilter("*.owl", "OWL"));
            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
            
                /*JOptionPane.showMessageDialog(fc, "The application will be"
                +"restarted for the new import to take effect");*/
                File file = fc.getSelectedFile();
                try {
                    saveFile(file);
                } catch (IOException ex) {
                    Logger.getLogger(FinderApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
                Settings s = new Settings(file.getAbsolutePath());
            
                ontology = new FinderOntology();
                setupChooserPanel(ontology);
                setupQueryPanel(ontology);
                setupResultsPanel(ontology);
                //txtNOntologyLocation.setText(file.getAbsolutePath());
            }
        }
        else if(e.getSource()==menuItemExit){
            System.exit(0);
        }
        else if(e.getSource()==rbMenuItemTreeView){
            view = true;
            setupChooserPanel(ontology);
        }
        else if(e.getSource()==rbMenuItemListView){
            view = false;
            setupChooserPanel(ontology);
            setupQueryPanel(ontology);
            setupResultsPanel(ontology);
        }
    }
    
     private void saveFile(File f) throws IOException{
        //File file = new File("./src/main/resources/"+f.getName());
        File file = new File(homefilepath+"/"+f.getName());
        if(file.exists() && file.isFile()){
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "The ontology is already exist\nDo you want to replace it?", "Confirmation",dialogButton,1,Icons.getAppIcon());
            if(dialogResult==0){
                Files.delete(file.toPath());
                Files.copy(f.toPath(), file.toPath());
            }
        }else{
            Files.copy(f.toPath(), file.toPath());
        }
    }
}
