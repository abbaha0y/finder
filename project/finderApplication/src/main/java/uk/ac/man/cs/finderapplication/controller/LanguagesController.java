/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.man.cs.finderapplication.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JRadioButtonMenuItem;
import uk.ac.man.cs.finderapplication.gui.MainFinderApplication;
import uk.ac.man.cs.finderapplication.model.LanguageModel;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class LanguagesController {
    
    ArrayList<JRadioButtonMenuItem> arrayListLanguages;
    private ActionListener actionListener;
    MainFinderApplication view;
    LanguageModel model;
    
    public LanguagesController(LanguageModel model, MainFinderApplication view){
        this.view = view;
        this.model = model;
    }
    
    public void control(){
        actionListener = new ActionListener() {
              public void actionPerformed(ActionEvent actionEvent) {                  
                  linkRadioButtonsAndView(Integer.parseInt(actionEvent.getActionCommand()),actionEvent);
              }
        };
        arrayListLanguages = view.getLanguages();
        for(int i=0; i<arrayListLanguages.size();i++){
            arrayListLanguages.get(i).addActionListener(actionListener);
            arrayListLanguages.get(i).setActionCommand(""+i);
        }
    }
    
    private void linkRadioButtonsAndView(int index, ActionEvent e){
        if(arrayListLanguages.get(index).isSelected()){
            if(arrayListLanguages.get(index).getText().equals("English")){
                model.displayLanguage("en");
            }
            else if(arrayListLanguages.get(index).getText().equals("German")){
                model.displayLanguage("de");
            }
            else if(arrayListLanguages.get(index).getText().equals("Spanish")){
                model.displayLanguage("es");
            }
            else if(arrayListLanguages.get(index).getText().equals("French")){
                model.displayLanguage("fr");
            }
            else if(arrayListLanguages.get(index).getText().equals("Portuguese")){
                model.displayLanguage("pt");
            }
            view.refreshIngView();
            
        }
    }
}
