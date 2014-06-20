/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.man.cs.finderapplication.gui;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FilteredJList extends JList{
	
	private FilterField filterField;
	private int DEFAULT_FIELD_WIDTH = 20;
	
	public FilteredJList(){
		super();
		this.setModel(new FilterModel());
		this.filterField = new FilterField(DEFAULT_FIELD_WIDTH);
	}
	
	public void setModel(ListModel m){
		if(!(m instanceof FilterModel))
			throw new IllegalArgumentException();
		super.setModel(m);
	}
	
	public void addItem(Object o){
		((FilterModel)getModel()).addElement(o);
	}
	
	public JTextField getFilterField(){
		return filterField;
	}
	
	public static void main (String [] args){
		String[] listItems = {"Hani", "Ahmed", "Houd", "Duaa"};
		JFrame frame = new JFrame("FilteredJList");
		frame.getContentPane().setLayout(new BorderLayout());
		// populate list
		FilteredJList list = new FilteredJList();
		for(int i=0; i<listItems.length; i++){
			list.addItem(listItems[i]);
		}
		// add to gui
		JScrollPane pane = new JScrollPane(list, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(pane, BorderLayout.CENTER);
		frame.getContentPane().add(list.getFilterField(), BorderLayout.NORTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public class FilterModel extends AbstractListModel{
		
		ArrayList items;
		ArrayList filterItems;
		
		public FilterModel(){
			super();
			items = new ArrayList();
			filterItems = new ArrayList();
		}
		
		public void addElement(Object o){
			items.add(o);
			refilter();
		}
		
		public void refilter(){
			filterItems.clear();
			String term = getFilterField().getText();
			for(int i=0; i<items.size(); i++){
				if(items.get(i).toString().indexOf(term, 0) != -1){
					filterItems.add(items.get(i));
				}
			}
			fireContentsChanged(this, 0, getSize());
		}

		@Override
		public int getSize() {
			// TODO Auto-generated method stub
			return filterItems.size();
		}

		@Override
		public Object getElementAt(int index) {
			// TODO Auto-generated method stub
			if(index < filterItems.size()){
				return filterItems.get(index);
			}
			else{
				return null;
			}
		}

	}
	
	public class FilterField extends JTextField implements DocumentListener{
		
		public FilterField(int width){
			super(width);
			this.getDocument().addDocumentListener(this);
		}
		
		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			((FilterModel)getModel()).refilter();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			((FilterModel)getModel()).refilter();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			((FilterModel)getModel()).refilter();
		}

	}
}

