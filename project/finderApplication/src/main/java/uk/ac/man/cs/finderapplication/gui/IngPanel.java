/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.man.cs.finderapplication.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.reasoner.NodeSet;
import uk.ac.man.cs.finderapplication.model.FinderOntology;
import uk.ac.man.cs.finderapplication.selection.Selectable;
import uk.ac.man.cs.finderapplication.selection.SelectionEvent;
import uk.ac.man.cs.finderapplication.selection.SelectionListener;
import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Oct 5, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class IngPanel extends JPanel implements Selectable {

	private JTree tree;
        
        private JList list;
        
        private CardLayout card;
        
        private JPanel p;
        // determine the view if is a tree or list
        // 1 = tree, 0 = list
        private boolean view;

	private FinderOntology ontology;

	private HashMap clsNodeMap;
        

	public IngPanel(FinderOntology ontology, boolean view) {
            this.view = view;
            this.ontology = ontology;
            clsNodeMap = new HashMap();
            createUI();
            //System.out.println(getTreeModel().getRoot());
	}

	protected void createUI() {
            card = new CardLayout();
            p = new JPanel(card);
            
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        tree = buildTree();
        list = buildList();

        tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				fireSelectionChangedEvent();
			}
		});
        
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                fireSelectionChangedEvent();
            }
        });
        
        p.add("Tree", tree);
        p.add("List",list);
        add(new JScrollPane(p));
		if(view){
                    card.show(p, "Tree");
                }
                else{
                    card.show(p, "List");
                }
            
	}

	protected JTree buildTree()  {
            //MutableTreeNode rootNode = new DefaultMutableTreeNode("Ingredient");
            //Getting the root node from the ontology itself
            MutableTreeNode rootNode = new DefaultMutableTreeNode(ontology.getIngClass());
            for (OWLClassExpression owlClassExpression : ontology.getIngredientsCategories()) {
                OWLClass cls = (OWLClass) owlClassExpression;
                addClsToTree(cls, rootNode);
                //System.out.println(cls);
            }

            JTree t = new JTree(rootNode);
            t.setShowsRootHandles(true);
            t.setCellRenderer(new OWLClassTreeCellRenderer());
            return t;
	}
        
        protected JList buildList(){
            DefaultListModel model = new DefaultListModel();
            
            for (OWLClassExpression owlClassExpression : ontology.getIngredientsCategories()) {
                OWLClass cls = (OWLClass) owlClassExpression;
                addClsToList(cls, model);
            }
            JList ls = new JList(model);
            
            
            ArrayList arrayList =  new ArrayList(Arrays.asList(model.toArray()));
            
            Set<String> s=new TreeSet<String>();
            s.addAll(arrayList);
            arrayList.clear();
            Iterator it=s.iterator();
            while (it.hasNext())  
            {  
                arrayList.add(it.next());
            } 
            
            Collections.sort(arrayList);
            
            //System.out.println(arrayList.size());
            model.removeAllElements();
            for(int i=0;i<arrayList.size();i++){
                //System.out.println(arrayList.get(i).toString());
                model.addElement(arrayList.get(i));
            }
            ls.setCellRenderer(new OWLClassListCellRenderer());
            return ls;
        }
        
        protected void addClsToList(OWLClass cls, DefaultListModel model){
            // add class and its subclasses
            //System.out.println(cls);
            MutableTreeNode childNode = new MyDefaultMutableTreeNode(cls);
            clsNodeMap.put(cls, childNode);
            //model.add(0,childNode);
            model.addElement(childNode);

            // Iterator it = cls.getInferredSubclasses().iterator();
            NodeSet<OWLClass> subClasses = ontology.getReasoner().getSubClasses(cls, true);
            if (!subClasses.containsEntity(ontology.getOntology().getOWLOntologyManager().getOWLDataFactory().getOWLNothing())) {
                for(OWLClass curCls : subClasses.getFlattened()) {
                    addClsToList(curCls, model);
                }
            }
        }

	protected void addClsToTree(OWLClass cls, MutableTreeNode treeNode) {
		// add class and its subclasses
		MutableTreeNode childNode = new MyDefaultMutableTreeNode(cls);
		clsNodeMap.put(cls, childNode);
		treeNode.insert(childNode, 0);

       // Iterator it = cls.getInferredSubclasses().iterator();
        NodeSet<OWLClass> subClasses = ontology.getReasoner().getSubClasses(cls, true);
        if (!subClasses.containsEntity(ontology.getOntology().getOWLOntologyManager().getOWLDataFactory().getOWLNothing())) {
            for(OWLClass curCls : subClasses.getFlattened()) {
                addClsToTree(curCls, childNode);
            }
        }
    }

        // for test
	/*public static void main(String [] args) {
		IngPanel panel = new IngPanel(new FinderOntology());
		JFrame frm = new JFrame();
		frm.setSize(300, 400);
		frm.getContentPane().setLayout(new BorderLayout());
		frm.getContentPane().add(panel);
		frm.getContentPane().add(new JLabel("Toppings!"), BorderLayout.NORTH);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.show();
	}*/

	/////////////////////////////////////////////////////////////////////////////
	//
	// An inner class that renders tree nodes.  If the user object
	// is an OWLClass the name fragment of the class is renderered,
	// otherwise, the toString method of the user object is called.
	//
	/////////////////////////////////////////////////////////////////////////////

	public class OWLClassTreeCellRenderer extends DefaultTreeCellRenderer {

		private Icon icon;

		public OWLClassTreeCellRenderer() {
			icon = Icons.getIngIcon();
		}

		public Component getTreeCellRendererComponent(JTree tree,
		                                              Object value,
		                                              boolean sel,
		                                              boolean expanded,
		                                              boolean leaf,
		                                              int row,
		                                              boolean hasFocus) {
                    
			JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
			Object obj = ((DefaultMutableTreeNode)value).getUserObject();
			
                        if(obj instanceof OWLClass) {
                            
                            label.setText(ontology.render(((OWLClass) obj)));
                            //System.out.println(ontology.render(((OWLClass) obj)));
			}
			else {
                            label.setText(value.toString());
                            //System.out.println(value.toString());
			}
			return label;
		}


		public Icon getOpenIcon() {
			return icon;
		}


		public Icon getClosedIcon() {
			return icon;
		}


		public Icon getLeafIcon() {
			return icon;
		}


	}
        ////////////////////////////////////////////////////////////////////////////
        public class OWLClassListCellRenderer extends DefaultListCellRenderer {

		private Icon icon;

		public OWLClassListCellRenderer() {
			icon = Icons.getIngIcon();
		}

                @Override
		public Component getListCellRendererComponent(JList list,
		                                              Object value,
		                                              int index,
		                                              boolean sel,
		                                              boolean hasFocus) {
                    
			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, sel, hasFocus);
			Object obj = ((DefaultMutableTreeNode)value).getUserObject();
			
                        if(obj instanceof OWLClass) {
				label.setText(ontology.render(((OWLClass) obj)));
			}
			else {
				label.setText(value.toString());
			}
                        label.setIcon(icon);
			return label;
		}


	}



	/////////////////////////////////////////////////////////////////////////////
	//
	// Implementation of Selectable
	//
	/////////////////////////////////////////////////////////////////////////////


	public Object getSelection() {
            if(view){
		TreePath treePath = tree.getSelectionPath();
		if(treePath != null) {
			DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)treePath.getLastPathComponent();
			//System.out.println(treeNode.getUserObject());
                        return treeNode.getUserObject();
		}
		
            }
            else{
                //System.out.println(list.getSelectedValue());
                DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)list.getSelectedValue();
                //if(treeNode.getUserObject() != null)
                if(treeNode != null)
                return treeNode.getUserObject();
                else{
                    
                }
            }
            return null;
	}


	public void setSelection(Object obj) {
            if(view){
		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)clsNodeMap.get(obj);
		if(treeNode != null) {
                    tree.setSelectionPath(new TreePath(treeNode.getPath()));
		}
            }
            else{
                if(obj != null){
                    list.setSelectedValue(obj, true);
                }
            }
	}

	private ArrayList selectionListeners = new ArrayList();

	public void addSelectionListener(SelectionListener lsnr) {
		selectionListeners.add(lsnr);
	}


	public void removeSelectionListener(SelectionListener lsnr) {
		selectionListeners.remove(lsnr);
	}

	protected void fireSelectionChangedEvent() {
		Iterator it = selectionListeners.iterator();
		SelectionEvent e = new SelectionEvent(this);
		while(it.hasNext()) {
			((SelectionListener)it.next()).selectionChanged(e);
		}
	}
        
        public void setTreeModel(TreeModel model){
            tree.setModel(model);
        }
        
        public TreeModel getTreeModel(){
            return tree.getModel();
        }
        
        public void setListModel(ListModel model){
            list.setModel(model);
        }
        
        public ListModel getListModel(){
            return list.getModel();
        }
        
        public JList getList(){
            return list;
        }
        
        public JTree getTree(){
            return tree;
        }
}

class MyDefaultMutableTreeNode extends DefaultMutableTreeNode implements Comparable{

    Object obj;
    
    public MyDefaultMutableTreeNode(Object obj){
        super(obj);
        this.obj = obj;
    }
    
    @Override
    public int compareTo(Object o) {
        return obj.toString().compareTo(o.toString());
    }
    
}