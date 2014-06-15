/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.man.cs.finderapplication.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Hani Al Abbas - hani.alabbas@postgrad.manchester.ac.uk
 */
public class Test extends JFrame implements ActionListener{
    JPanel rightPanel,animationPanel;
    JButton btnDown,btnUp;
    AnimatedSplitPane animatedSplitPane;
    
    public Test(){
        this.setTitle("Testing Aimation");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,600);
        this.getContentPane().setLayout(new BorderLayout());
        
        rightPanel = new JPanel(new BorderLayout());
        animationPanel = new JPanel(new BorderLayout());
        
        btnDown = new JButton("Down");
        btnDown.addActionListener(this);
        btnUp = new JButton("UP");
        btnUp.addActionListener(this);
        
        rightPanel.add(btnDown, BorderLayout.NORTH);
        rightPanel.add(btnUp, BorderLayout.SOUTH);
        
        animatedSplitPane = new AnimatedSplitPane(300);
        animationPanel.add(animatedSplitPane);
        
        this.add(animationPanel,BorderLayout.CENTER);
        this.add(rightPanel, BorderLayout.EAST);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    public static void main (String[] args){
        Test testFrame = new Test();
        testFrame.setVisible(true);
    } 
}
