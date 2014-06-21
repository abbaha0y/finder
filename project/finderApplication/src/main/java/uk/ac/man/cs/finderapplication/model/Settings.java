/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.man.cs.finderapplication.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class Settings {
    
    final String DEFUALT_LOGO = this.getClass().getClassLoader().getResource("defualt_Logo.jpg").toString();//"./resources/defualt_Logo.jpg";
    final String DEFUALT_ICON = this.getClass().getClassLoader().getResource("defualt_Icon.png").toString();
    
    String ontologyLocation,logoLocation,iconLocation;
    String fileName;
    
    String homefilepath = System.getProperty("user.home")+"/FinderApplication";
    
    public Settings(){
        PrepSettings();
    }
    
    public Settings(String ontLocation){
        PrepSettings();
        this.ontologyLocation = ontLocation;
        bulidConfigFile();
    }
    
    public Settings(String ontLocation, String logoLocation, String iconLocation){
        this.ontologyLocation = ontLocation;
        this.logoLocation = logoLocation;
        this.iconLocation = iconLocation;
        bulidConfigFile();
    }
    
    private void PrepSettings(){
        ontologyLocation ="";
        logoLocation ="";
        iconLocation ="";
        
        try {
            DocumentBuilderFactory docFactory;
            DocumentBuilder docBuilder = null;
            Document doc;
            docFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docFactory.newDocumentBuilder();
            System.out.println(new File(homefilepath+"/settings.xml").exists());
            doc = docBuilder.parse(homefilepath+"/settings.xml");
            Node root = doc.getFirstChild();
            Node ont = doc.getElementsByTagName("Ontology").item(0);
            NamedNodeMap attr = ont.getAttributes();
            ontologyLocation = attr.getNamedItem("OntLocation").getTextContent();
            
            Node uiIcon = doc.getElementsByTagName("UI").item(0);
            NamedNodeMap attrIcon = uiIcon.getAttributes();
            
            Node uiLogo = doc.getElementsByTagName("UI").item(0);
            NamedNodeMap attrLogo = uiLogo.getAttributes();
            iconLocation = attrIcon.getNamedItem("IconLocation").getTextContent();
            logoLocation = attrLogo.getNamedItem("LogoLocation").getTextContent();
            
            
        }catch (SAXException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IOException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }catch (ParserConfigurationException ex) {
                Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void bulidConfigFile(){
        //////////////////////////////////////
        /// Fix setting file location      ///
        //////////////////////////////////////
        //
        
        //////////////////////////////////////
        
        DocumentBuilderFactory docFactory;
        DocumentBuilder docBuilder = null;
        docFactory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
        Document doc = docBuilder.newDocument();
	Element rootElement = doc.createElement("Settings");
	doc.appendChild(rootElement);
        
        Element ontLocation = doc.createElement("Ontology");
	rootElement.appendChild(ontLocation);
        Attr attrOntologyLocation = doc.createAttribute("OntLocation");
	attrOntologyLocation.setValue(homefilepath+"/"+ new File(ontologyLocation).getName());
	ontLocation.setAttributeNode(attrOntologyLocation);
        
        Element ui = doc.createElement("UI");
        rootElement.appendChild(ui);
        Attr attrLogo = doc.createAttribute("LogoLocation");
        attrLogo.setValue(homefilepath+"/"+ new File(logoLocation).getName());
        ui.setAttributeNode(attrLogo);
        Attr attrIcon = doc.createAttribute("IconLocation");
        attrIcon.setValue(homefilepath+"/"+ new File(iconLocation).getName());
        ui.setAttributeNode(attrIcon);
        
        // write the content into xml file
	TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
	DOMSource source = new DOMSource(doc);
        //System.out.println(Settings.class.getResource("").toString());
	StreamResult result = new StreamResult(new File(homefilepath+"/settings.xml"));
        try {
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            Logger.getLogger(Settings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getOnologyLocation(){
        return ontologyLocation;
    }
    
    public String getLogoLocation(){
        return logoLocation;
    }
    
    public String getIconLocation(){
        return iconLocation;
    }
    
}
