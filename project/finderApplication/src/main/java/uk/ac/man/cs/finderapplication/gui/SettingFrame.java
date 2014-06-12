package uk.ac.man.cs.finderapplication.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import uk.ac.man.cs.finderapplication.model.Settings;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class SettingFrame extends JFrame implements ActionListener, DocumentListener{
    JPanel nsp;
    JPanel ysp;
    JPanel mainPanel;
    // yes settings variables
    JLabel lblYMessege,lblYLogo,lblYOntologyLocation,lblYAppIcon;
    JTextField txtYLogo,txtYOntologyLocation,txtYAppIcon;
    JButton btnYModify,btnYOK,btnYCancel;
    JPanel pYMessege,pYSettings,pYButton;
    // yes settings variables
    // No settings variables
    JLabel lblNMessege,lblNLogo,lblNOntologyLocation,lblNAppIcon;
    JTextField txtNLogo,txtNOntologyLocation,txtNAppIcon;
    JButton btnNLogo,btnNOntologyLocation,btnNAppIcon,btnNOK,btnNCancel;
    JPanel pNMessege,pNSettings,pNButton;
    JFileChooser fc;
    // No settings variables
    FinderApplication finder;
    
    Settings setting;    
    CardLayout card;
    
    public SettingFrame(){
        bulidUI();
    }
    
    private void setupNoSettingsPanel(){
        nsp = new JPanel();
        
        lblNMessege = new JLabel("There is no stored settings");
        lblNLogo = new JLabel("Application Logo:",JLabel.TRAILING);
        lblNAppIcon = new JLabel("Application Icon:",JLabel.TRAILING);
        lblNOntologyLocation = new JLabel("Ontology Location:",JLabel.TRAILING);
        
        txtNLogo = new JTextField(20);
        txtNAppIcon = new JTextField(20);
        txtNOntologyLocation = new JTextField(20);
        
        txtNOntologyLocation.getDocument().addDocumentListener(this);
        
        txtNLogo.setEditable(false);
        txtNAppIcon.setEditable(false);
        txtNOntologyLocation.setEditable(false);
                
        lblNLogo.setLabelFor(txtNLogo);
        lblNAppIcon.setLabelFor(txtNAppIcon);
        lblNOntologyLocation.setLabelFor(txtNOntologyLocation);
        
        btnNLogo = new JButton("Browse for Logo");
        btnNAppIcon = new JButton("Browse for Icon");
        btnNOntologyLocation = new JButton("Browse for Ontology");
        btnNOK = new JButton("OK");
        btnNCancel = new JButton("Cancel");
        
        btnNLogo.addActionListener(this);
        btnNAppIcon.addActionListener(this);
        btnNOntologyLocation.addActionListener(this);
        btnNOK.addActionListener(this);
        btnNCancel.addActionListener(this);
        
        btnNOK.setEnabled(false);
        
        pNMessege = new JPanel(new FlowLayout());
        pNMessege.add(lblNMessege);
        
        pNSettings = new JPanel(new SpringLayout());
        pNSettings.add(lblNLogo);
        pNSettings.add(txtNLogo);
        pNSettings.add(btnNLogo);
        
        pNSettings.add(lblNAppIcon);
        pNSettings.add(txtNAppIcon);
        pNSettings.add(btnNAppIcon);
        
        pNSettings.add(lblNOntologyLocation);
        pNSettings.add(txtNOntologyLocation);
        pNSettings.add(btnNOntologyLocation);
        
        SpringUtilities.makeCompactGrid(pNSettings, 3, 3, 6, 6, 10, 10);
        
        pNButton = new JPanel(new FlowLayout());
        pNButton.add(btnNOK);
        pNButton.add(btnNCancel);
        
        nsp.setLayout(new BorderLayout());
        nsp.add(pNMessege, BorderLayout.NORTH);
        nsp.add(pNSettings, BorderLayout.CENTER);
        nsp.add(pNButton, BorderLayout.SOUTH);
        
    }
    
    private void setupYesSettingsPanel(){
        Settings set = new Settings();
        ysp = new JPanel();
        
        lblYMessege = new JLabel("There are stored settings");
        lblYLogo = new JLabel("Application Logo:",JLabel.TRAILING);
        lblYAppIcon = new JLabel("Application Icon:",JLabel.TRAILING);
        lblYOntologyLocation = new JLabel("Ontology Location:",JLabel.TRAILING);
        
        txtYLogo = new JTextField(20);
        txtYAppIcon = new JTextField(20);
        txtYOntologyLocation = new JTextField(20);
        
        txtYLogo.setText(set.getLogoLocation());
        txtYAppIcon.setText(set.getIconLocation());
        txtYOntologyLocation.setText(set.getOnologyLocation());
        
        txtYLogo.setEditable(false);
        txtYAppIcon.setEditable(false);
        txtYOntologyLocation.setEditable(false);
                
        lblYLogo.setLabelFor(txtYLogo);
        lblYAppIcon.setLabelFor(txtYAppIcon);
        lblYOntologyLocation.setLabelFor(txtYOntologyLocation);
        
        btnYOK = new JButton("OK");
        btnYCancel = new JButton("Cancel");
        btnYModify = new JButton("Modify Settings");
        
        btnYOK.addActionListener(this);
        btnYCancel.addActionListener(this);
        btnYModify.addActionListener(this);
        
        pYMessege = new JPanel(new FlowLayout());
        pYMessege.add(lblYMessege);
        
        pYSettings = new JPanel(new SpringLayout());
        pYSettings.add(lblYLogo);
        pYSettings.add(txtYLogo);
        
        pYSettings.add(lblYAppIcon);
        pYSettings.add(txtYAppIcon);
        
        pYSettings.add(lblYOntologyLocation);
        pYSettings.add(txtYOntologyLocation);
        
        SpringUtilities.makeCompactGrid(pYSettings, 3, 2, 6, 6, 10, 10);
        
        pYButton = new JPanel(new FlowLayout());
        pYButton.add(btnYOK);
        pYButton.add(btnYModify);
        pYButton.add(btnYCancel);
        
        ysp.setLayout(new BorderLayout());
        ysp.add(pYMessege, BorderLayout.NORTH);
        ysp.add(pYSettings, BorderLayout.CENTER);
        ysp.add(pYButton, BorderLayout.SOUTH);  
        
    }
    
    private void bulidUI(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600, 200);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Finder Settings");
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("/defualt_Icon.png"));
        
        setupNoSettingsPanel();
        if(checkSettingsExists()){
            setupYesSettingsPanel();
            mainPanel = new JPanel(new CardLayout());
            this.getContentPane().setLayout(new BorderLayout());
            mainPanel.add(nsp,"nsp");
            mainPanel.add(ysp,"ysp");
        }else{
            mainPanel = new JPanel(new CardLayout());
        this.getContentPane().setLayout(new BorderLayout());
            mainPanel.add(nsp,"nsp");
        }
        
        
        
        this.getContentPane().add(mainPanel);
        
        card = (CardLayout) mainPanel.getLayout();
        if(checkSettingsExists()){
            card.show(mainPanel, "ysp");
        }
        else{
            card.show(mainPanel, "nsp"); 
        }
    }  
    
    public boolean checkSettingsExists(){
        //System.out.println(getClass().getResource("/settings.xml").toString());
        File f = new File("./src/main/resources/settings.xml");
        if(f.exists()){
            return true;
	}else
        {
            return false;
	}
    }
    
    private void saveFile(File f) throws IOException{
        File file = new File("./src/main/resources/"+f.getName());
        
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

    @Override
    public void insertUpdate(DocumentEvent e) {
        if(!txtNOntologyLocation.getText().trim().equals("")){
            btnNOK.setEnabled(true);
        }
        else{
            btnNOK.setEnabled(false);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if(!txtNOntologyLocation.getText().trim().equals("")){
            btnNOK.setEnabled(true);
        }
        else{
            btnNOK.setEnabled(false);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if(!txtNOntologyLocation.getText().trim().equals("")){
            btnNOK.setEnabled(true);
        }
        else{
            btnNOK.setEnabled(false);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnNLogo){
            fc = new JFileChooser();
            fc.setCurrentDirectory(new File(System.getProperty("user.home")));
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fc.setAcceptAllFileFilterUsed(false);
            fc.setFileFilter(new FileNameExtensionFilter("*.gif", "GIF"));
            fc.setFileFilter(new FileNameExtensionFilter("*.jpg", "JPG")); 
            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                txtNLogo.setText(file.getAbsolutePath());
            }
        }
        else if(e.getSource() == btnNAppIcon){
            fc = new JFileChooser();
            fc.setCurrentDirectory(new File(System.getProperty("user.home")));
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fc.setAcceptAllFileFilterUsed(false);
            fc.setFileFilter(new FileNameExtensionFilter("*.png", "PNG"));
            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                txtNAppIcon.setText(file.getAbsolutePath());
            }
        }
        else if(e.getSource() == btnNOntologyLocation){
            fc = new JFileChooser();
            fc.setCurrentDirectory(new File(System.getProperty("user.home")));
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fc.setAcceptAllFileFilterUsed(false);
            fc.setFileFilter(new FileNameExtensionFilter("*.owl", "OWL"));
            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                txtNOntologyLocation.setText(file.getAbsolutePath());
            }
        }
        else if(e.getSource() == btnNOK){
            String logo,icon,ontologyLocation;
            logo = txtNLogo.getText().trim();
            icon = txtNAppIcon.getText().trim();
            ontologyLocation = txtNOntologyLocation.getText().trim();
            setting = new Settings(ontologyLocation,logo,icon);
            
            try {
                saveFile(new File(ontologyLocation));
                if(!logo.equals(""))
                    saveFile(new File(logo));
                if(!icon.equals(""))
                    saveFile(new File(icon));
            } catch (IOException ex) {
                Logger.getLogger(SettingFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.dispose();
            finder = new FinderApplication(new File(ontologyLocation));
            finder.setVisible(true);
            
        }
        else if(e.getSource() == btnNCancel){
            System.exit(0);
        }
        else if(e.getSource() == btnYOK){
            this.dispose();
            finder = new FinderApplication(new File(txtYOntologyLocation.getText().trim()));
            finder.setVisible(true);
        }
        else if(e.getSource() == btnYModify){
            card.show(mainPanel,"nsp");
            lblNMessege.setText("Modify the settings...");
        }
        else if(e.getSource() == btnYCancel){
            System.exit(0);
        }
    }
    
    public static void main(String[] arg){
            new SettingFrame().setVisible(true);
    }
}
