/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.man.cs.finderapplication.model;

import javax.swing.JButton;
import javax.swing.JLabel;
import uk.ac.man.cs.finderapplication.gui.ChooserPanel;
import uk.ac.man.cs.finderapplication.gui.QueryPanel;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class UIConfigurationModel {
    //private final JButton btnAdd,btnRemove,btnGet;
    //private final JLabel lblFilter,lblInc,lblExc;
    private ChooserPanel cp;
    private QueryPanel qp;
    
    public UIConfigurationModel(ChooserPanel cp,QueryPanel qp){
        this.cp = cp;
        this.qp = qp;
    }
    
    public void doChages(String add,String rem, String get,String filter,String inc,String exc){
        cp.setFilterLabel(filter);
        qp.setExcAddBtn(exc);
        qp.setIncAddBtn(add);
        qp.setIncRemBtn(rem);
        qp.setExcAddBtn(add);
        qp.setExcRemBtn(rem);
        qp.setGetBtn(get);
        qp.setLabelInc(inc);
        qp.setLabelExc(exc);
    }
}
