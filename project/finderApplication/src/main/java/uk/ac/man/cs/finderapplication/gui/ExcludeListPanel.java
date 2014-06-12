/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.man.cs.finderapplication.gui;

import java.awt.event.ActionEvent;
import java.util.Collection;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.semanticweb.owlapi.model.OWLClass;
import uk.ac.man.cs.finderapplication.model.ChoiceModel;
import uk.ac.man.cs.finderapplication.model.FinderOntology;
import uk.ac.man.cs.finderapplication.selection.Selectable;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Oct 6, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class ExcludeListPanel extends ListPanel {

    public static final String TITLE = "Excluded toppings:";

    private ChoiceModel choiceModel;

    private Selectable selectable;

    private Action addAction = new AbstractAction("Add") {
        public void actionPerformed(ActionEvent e) {
            Object selObj = selectable.getSelection();
            if (selObj != null && selObj instanceof OWLClass) {
                choiceModel.addExcluded((OWLClass) selObj);
            }
        }
    };

    private Action removeAction = new AbstractAction("Rem") {
        public void actionPerformed(ActionEvent e) {
            removeExcluded();
        }
    };

    public ExcludeListPanel(final FinderOntology ontology,
                                   final Selectable selectable,
                                   final ChoiceModel choiceModel) {
        super(ontology, TITLE, selectable, choiceModel);
        this.selectable = selectable;
        this.choiceModel = choiceModel;
        createUI();
    }

    protected Collection getListItems() {
        return choiceModel.getExcluded();
    }

    private void removeExcluded() {
        Object selObj = getSelection();
        if (selObj != null && selObj instanceof OWLClass) {
            choiceModel.removeExcluded((OWLClass) selObj);
        }
    }

    protected Action getAddAction() {
        return addAction;
    }

    protected Action getRemoveAction() {
        return removeAction;
    }
}

