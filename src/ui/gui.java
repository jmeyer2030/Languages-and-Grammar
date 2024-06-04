package ui;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import objects.*;
import javax.swing.*;

public class gui implements ActionListener {
	JFrame frame;
	JPanel panel;
	JButton button;
	String[] options = {"removeEps", "removeUnits", "removeMixed", "removeLong", "CNF"};;
	JComboBox<String> comboBox;
	
	public static void main(String args[]) {
		new gui();
	}
	
	public gui() {
		frame = new JFrame();
		panel = new JPanel(new GridLayout(2,2,10,10));
		button = new JButton("Generate");
		comboBox = new JComboBox<String>(options);
		JTextField field = new JTextField(20);
		
		comboBox.addActionListener(this);
		
		frame.setSize(800,5000);

		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("CFG");

		panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
		panel.setLayout(new GridLayout());
		
	
		
		panel.add(comboBox);
		panel.add(button);
		frame.pack();
		frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == comboBox) {
			
		}
	}
}
