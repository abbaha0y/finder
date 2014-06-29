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
import javax.swing.AbstractAction;
import javax.swing.Action;
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
import uk.ac.man.cs.finderapplication.controller.LanguagesController;
import uk.ac.man.cs.finderapplication.model.FinderOntology;
import uk.ac.man.cs.finderapplication.model.LanguageModel;
import uk.ac.man.cs.finderapplication.model.Settings;
import uk.ac.man.cs.finderapplication.selection.Selectable;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class MainFinderApplication extends JFrame implements ActionListener {

    FinderOntology ontology;
    FinderPanel finderPanel;
    LogoPanel logoPanel;
    Settings setting;
    //Configuration c;

    Selectable selectable;
    JFileChooser fc;
    //private ChoiceModel choiceModel;

    ChooserPanel cp;
    QueryPanel qp;
    ResultsPanel rp;

    JMenuBar menuBar;
    JMenu menuFile, menuView, menuConfiguration, menuItemLanguages, menuHelp;
    JMenuItem menuItemImport, menuItemExit, menuItemUIConfig;
    ButtonGroup groupView, groupLanguages;
    ImageIcon importImage;

    JRadioButtonMenuItem rbMenuItemTreeView, rbMenuItemListView;
    private ArrayList<JRadioButtonMenuItem> languages;
    boolean view;

    String homefilepath = System.getProperty("user.dir") + "/Finder_Application";

    private Action aboutAction = new AbstractAction("About") {
        public void actionPerformed(ActionEvent e) {
            AboutDialog dlg = new AboutDialog(MainFinderApplication.this);
            dlg.setVisible(true);
        }
    };
    
    /*private Action uiAction = new AbstractAction("UI Configuration") {
        public void actionPerformed(ActionEvent e) {
            ConfigruationDialog dlg = new ConfigruationDialog(MainFinderApplication.this);
            dlg.setVisible(true);
        }
    };*/

    LanguagesController langController;
    LanguageModel langModel;

    public MainFinderApplication(File ontologyFile, Settings setting) {
        view = true;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(900, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("The Manchester Finder");

        this.setting = setting;
        finderPanel = new FinderPanel();
        ontology = new FinderOntology("en");
        //choiceModel = new ChoiceModel(ontology);
        setupMenuBar();
        setupLogoPanel();
        setupChooserPanel(ontology);
        setupQueryPanel(ontology);
        setupResultsPanel(ontology);
        //ontology.setupShortFormProvider("fr");
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(finderPanel);

    }

    private void setupMenuBar() {
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        menuFile = new JMenu("File");
        menuBar.add(menuFile);

        menuItemImport = new JMenuItem("Import new ontology");
        importImage = new ImageIcon(getClass().getClassLoader().getResource("import.png"));
        menuItemImport.setIcon(importImage);
        menuFile.add(menuItemImport);
        menuItemImport.addActionListener(this);

        menuFile.addSeparator();

        menuItemExit = new JMenuItem("Exit");
        importImage = new ImageIcon(getClass().getClassLoader().getResource("exit.png"));
        menuItemExit.setIcon(importImage);
        menuFile.add(menuItemExit);
        menuItemExit.addActionListener(this);

        groupView = new ButtonGroup();
        menuView = new JMenu("View");

        rbMenuItemTreeView = new JRadioButtonMenuItem("Tree View");
        rbMenuItemTreeView.addActionListener(this);
        rbMenuItemListView = new JRadioButtonMenuItem("List View");
        rbMenuItemListView.addActionListener(this);
        rbMenuItemTreeView.setSelected(true);
        groupView.add(rbMenuItemTreeView);
        menuView.add(rbMenuItemTreeView);
        groupView.add(rbMenuItemListView);
        menuView.add(rbMenuItemListView);
        menuBar.add(menuView);

        menuConfiguration = new JMenu("Configuration");
        menuBar.add(menuConfiguration);

        //groupLanguages = new ButtonGroup();
        menuItemLanguages = new JMenu("Available Languages");
        menuConfiguration.add(menuItemLanguages);

        /*languages = new ArrayList<>(ontology.getLanguages().size());
        
         Iterator it = ontology.getLanguages().entrySet().iterator();
         for(int i=0; i<ontology.getLanguages().size(); i++){
         Map.Entry pairs = (Map.Entry)it.next();
         JRadioButtonMenuItem temp = new JRadioButtonMenuItem(pairs.getKey().toString());
         if(pairs.getKey().toString().equals("English")){
         temp.setSelected(true);
         }
         languages.add(temp);
         languages.get(i).setToolTipText("Represents "+String.format("%.2f",pairs.getValue())+"% of the ontology");
         groupLanguages.add(languages.get(i));
         menuItemLanguages.add(languages.get(i));
         }*/
        setupMenuLanguage();

        langModel = new LanguageModel(ontology);

        langController = new LanguagesController(langModel, this);
        langController.control();

        menuItemUIConfig = new JMenuItem("UI Configuration");
        menuItemUIConfig.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfigruationDialog dlg = new ConfigruationDialog(MainFinderApplication.this, cp,qp);
            dlg.setVisible(true);
            }
            
        });
        importImage = new ImageIcon(getClass().getClassLoader().getResource("uiConfig.png"));
        menuItemUIConfig.setIcon(importImage);
        menuConfiguration.add(menuItemUIConfig);

        menuHelp = new JMenu("Help");
        menuBar.add(menuHelp);
        JMenuItem menuItemAbout = new JMenuItem(aboutAction);
        menuHelp.add(menuItemAbout);
    }

    private void setupLogoPanel() {
        System.out.println(setting.getLogoLocation());
        logoPanel = new LogoPanel(Toolkit.getDefaultToolkit().getImage(setting.getLogoLocation()));
        finderPanel.setLogoComponent(logoPanel);
    }

    private void setupChooserPanel(FinderOntology ont) {
        cp = new ChooserPanel(ont, this, view);
        finderPanel.setDividerLocationButtomLeft();
        finderPanel.setButtomLeftComponent(cp);
        setSelectable(cp.getSelectable());
    }

    private void setupQueryPanel(FinderOntology ont) {
        //pass the selectable to the queryPanel
        qp = new QueryPanel(ont, this, getSelecatable());
        finderPanel.setDividerLocationButtomRight();
        finderPanel.setButtomCenterComponent(qp);
    }

    private void setupResultsPanel(FinderOntology ont) {
        rp = new ResultsPanel(ont, this);
        finderPanel.setButtomRightComponent(rp);
    }

    private void setSelectable(Selectable selectable) {
        this.selectable = selectable;
    }

    private Selectable getSelecatable() {
        return selectable;
    }

    public void showResultsPanel(Collection<OWLClass> results) {
        rp.setPizzas(results);
    }

    // Testing
    /*public static void main(String[] arg){

     MainFinderApplication f = new MainFinderApplication(null);
     f.setVisible(true);
     }*/
    public void setupMenuLanguage() {
        groupLanguages = new ButtonGroup();
        menuItemLanguages.removeAll();
        
        languages = new ArrayList<>(ontology.getLanguages().size());

        Iterator it = ontology.getLanguages().entrySet().iterator();
        for (int i = 0; i < ontology.getLanguages().size(); i++) {
            Map.Entry pairs = (Map.Entry) it.next();
            JRadioButtonMenuItem temp = new JRadioButtonMenuItem(pairs.getKey().toString());
            if (pairs.getKey().toString().equals("English")) {
                temp.setSelected(true);
            }
            languages.add(temp);
            languages.get(i).setToolTipText("Represents " + String.format("%.2f", pairs.getValue()) + "% of the ontology");
            groupLanguages.add(languages.get(i));
            menuItemLanguages.add(languages.get(i));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuItemImport) {
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
                    System.out.println(file.getAbsoluteFile());
                } catch (IOException ex) {
                    Logger.getLogger(MainFinderApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
                Settings s = new Settings(file.getAbsolutePath());

                ontology = new FinderOntology("en");
                setupChooserPanel(ontology);
                setupQueryPanel(ontology);
                setupResultsPanel(ontology);
                //setupMenuBar();
                setupMenuLanguage();
                langModel = new LanguageModel(ontology);
                langController = new LanguagesController(langModel, this);
                langController.control();
                //txtNOntologyLocation.setText(file.getAbsolutePath());
            }
        } else if (e.getSource() == menuItemExit) {
            System.exit(0);
        } else if (e.getSource() == rbMenuItemTreeView) {
            view = true;
            setupChooserPanel(ontology);
            setupQueryPanel(ontology);
            setupResultsPanel(ontology);
        } else if (e.getSource() == rbMenuItemListView) {
            view = false;
            setupChooserPanel(ontology);
            setupQueryPanel(ontology);
            setupResultsPanel(ontology);
        }
    }

    private void saveFile(File f) throws IOException {
        //File file = new File("./src/main/resources/"+f.getName());
        File file = new File(homefilepath + "/" + f.getName());
        if (file.exists() && file.isFile()) {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "The ontology is already exist\nDo you want to replace it?", "Confirmation", dialogButton, 1, Icons.getAppIcon());
            if (dialogResult == 0) {
                Files.delete(file.toPath());
                Files.copy(f.toPath(), file.toPath());
            }
        } else {
            Files.copy(f.toPath(), file.toPath());
        }
    }

    public ArrayList<JRadioButtonMenuItem> getLanguages() {
        return languages;
    }

    public void refreshIngView() {
        cp.getTree().repaint();
        cp.getList().repaint();
        qp.getIncList().repaint();
        qp.getExcList().repaint();
        //rp.setPizzaPanels(rp.getPanels());
        //qp.refershResult();
        setupResultsPanel(ontology);
        //cp.refreshFilters();
        //setupChooserPanel(ontology);
        //setupQueryPanel(ontology);
        //setupResultsPanel(ontology);
    }
    
    public ChooserPanel getChooserPanel(){
        return cp;
    }
    public QueryPanel getQueryPanel(){
        return qp;
    }
}
