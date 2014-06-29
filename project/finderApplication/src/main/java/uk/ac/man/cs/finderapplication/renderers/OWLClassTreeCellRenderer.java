/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.man.cs.finderapplication.renderers;

import java.awt.Color;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import org.semanticweb.owlapi.model.OWLClass;
import uk.ac.man.cs.finderapplication.gui.Icons;
import uk.ac.man.cs.finderapplication.gui.MyMutableTreeNode;
import uk.ac.man.cs.finderapplication.model.FinderOntology;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Oct 5, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class OWLClassTreeCellRenderer extends DefaultTreeCellRenderer {

    protected Icon icon;
    private FinderOntology ontology;

    protected Icon disabledLeafIcon;
    protected Icon disabledOpenIcon;
    protected Icon disabledClosedIcon;

    public OWLClassTreeCellRenderer(FinderOntology ontology) {
        this(new GraydIcon(Icons.getIngIcon()), new GraydIcon(Icons.getIngIcon()), new GraydIcon(Icons.getIngIcon()));
        icon = Icons.getIngIcon();
        this.ontology = ontology;

    }

    public OWLClassTreeCellRenderer(Icon leafIcon, Icon openIcon, Icon closedIcon) {
        this.disabledLeafIcon = leafIcon;
        this.disabledOpenIcon = openIcon;
        this.disabledClosedIcon = closedIcon;
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree,
            Object value,
            boolean sel,
            boolean expanded,
            boolean leaf,
            int row,
            boolean hasFocus) {

        JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        boolean d = ((MyMutableTreeNode) value).getType();
        Object obj = ((DefaultMutableTreeNode) value).getUserObject();

        OWLClass objClass = (OWLClass) obj;
        String stringValue = ontology.render(objClass);
        //System.out.println(ontology.render(stringValue));
        if (d) {
            label.setOpaque(true);
            
            if (sel) {
                label.setBackground(Color.RED);
                setTextSelectionColor(Color.WHITE);
            }
            else{
                label.setBackground(Color.YELLOW);
            }
        } else {
            label.setOpaque(false);
            label.setBackground(Color.WHITE);
        }

        if (obj instanceof OWLClass) {
            label.setText(ontology.render(((OWLClass) obj)));
        } else {
            label.setText(value.toString());
        }
        if (sel) {
            label.setForeground(getTextSelectionColor());
        } else {
            label.setForeground(getTextNonSelectionColor());
        }

        boolean treeIsEnabled = tree.isEnabled();
        boolean nodeIsEnabled = ((MyMutableTreeNode) value).isEnabled();
        boolean isEnabled = (treeIsEnabled && nodeIsEnabled);
        setEnabled(isEnabled);

        if (isEnabled) {
            selected = sel;
            if (leaf) {
                label.setIcon(getLeafIcon());
            } else if (expanded) {
                label.setIcon(getOpenIcon());
            } else {
                label.setIcon(getClosedIcon());
            }
        } else {
            selected = false;
            if (leaf) {
                if (nodeIsEnabled) {
                    label.setDisabledIcon(getLeafIcon());
                } else {
                    label.setDisabledIcon(disabledLeafIcon);
                }
            } else if (expanded) {
                if (nodeIsEnabled) {
                    label.setDisabledIcon(getOpenIcon());
                } else {
                    label.setDisabledIcon(disabledOpenIcon);
                }
            } else {
                if (nodeIsEnabled) {
                    label.setDisabledIcon(getClosedIcon());
                } else {
                    label.setDisabledIcon(disabledClosedIcon);
                }
            }
        }

        return label;
    }

    @Override
    public Icon getOpenIcon() {
        return icon;
    }

    @Override
    public Icon getClosedIcon() {
        return icon;
    }

    @Override
    public Icon getLeafIcon() {
        return icon;
    }

}
