package ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

import objects.*;

public class GUI implements ActionListener {
	JFrame frame;
	JPanel panel;
	JButton button;
	JTextArea input;
	JTextPane output;
	String[] options = {"removeEps", "removeUnits", "removeMixed", "removeLong", "CNF"};;
	JComboBox<String> comboBox;

	String inputDefault = "ABCDE\nabcde\nA->BC\nB->CD\nC->DE";
	public static void main(String args[]) {
		GUI gui = new GUI();
		ContextFreeGrammar testCFG = gui.testGrammarCreator();
		gui.dispGrammar(testCFG);
	}
	//"<html> &#949 <html>" EPSILON

	public GUI() {
		frame = new JFrame();
		//panel = new JPanel(new GridLayout(2,2,10,10));
		panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		button = new JButton("Generate");
		comboBox = new JComboBox<String>(options);
		comboBox.setMaximumSize( comboBox.getPreferredSize() );
		input = new JTextArea(5,5);
		output = new JTextPane();
		output.setContentType("text/html");
		

		layout.setHorizontalGroup(
	    	layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	    		.addGroup(layout.createSequentialGroup()
	    				.addComponent(comboBox)
	                    .addComponent(button))
	    		.addGroup(layout.createSequentialGroup()
	                    .addComponent(input)
	                    .addComponent(output))
	    );

	        // Vertical group
	    layout.setVerticalGroup(
	    	layout.createSequentialGroup()
	    		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
	    				.addComponent(comboBox)
	                    .addComponent(button))
	    		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	                    .addComponent(input)
	                    .addComponent(output))
	    );
		
	
		
		output.setEditable(false);
		output.setBorder(new LineBorder(Color.BLACK));
		input.setBorder(new LineBorder(Color.BLACK));
		input.setText(inputDefault);
		comboBox.addActionListener(this);
		button.addActionListener(this);
		
		frame.setSize(800,5000);

		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("CFG");

		panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));

		frame.pack();
		frame.setVisible(true);
	}
	
	public void dispGrammar(ContextFreeGrammar CFG) {
		StringBuilder bldr = new StringBuilder("<html> ");
		for (ProductionRule rule : CFG.getRuleSet()) {
			bldr.append(rule.getNonTerminal());
			bldr.append(" -> ");
			bldr.append(rule.productionToString());
			bldr.append("<br>");
		}
		bldr.append(" <html>");
		output.setText(bldr.toString());
		//output.setText("<html> &#949 <html>");
	}
	
	public ContextFreeGrammar testGrammarCreator() {
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule("A", new String[] {"B", "C", "D", "E", "F"});
		ProductionRule B = new ProductionRule("A", new String[] {"G", "H", "I", "J", "K"});
		char[] terminals = {'a', 'b', 'c'};
		String[] nonTerminals = {"A", "B", "C", "D", "E", "F", "G"};
		ProductionRuleSet set = new ProductionRuleSet();
		CFG.setRuleSet(set);
		CFG.getRuleSet().addRule(A);
		CFG.getRuleSet().addRule(B);
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		ContextFreeGrammar output = CFG.removeLong();
		return output;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == button) {
			ContextFreeGrammar generated = CFGFromText();
			ContextFreeGrammar processed = generated;
			String selected = comboBox.getSelectedItem().toString();
			if (selected.equals("removeEps")) {
				processed = generated;
			} else if (selected.equals("removeUnits")) {
				processed = generated.removeUnits();
			} else if (selected.equals("removeMixed")) {
				processed = generated.removeMixed();
			} else if (selected.equals("removeLong")) {
				processed = generated.removeLong();
			} else if (selected.equals("CNF")) {
				processed = generated.convertToCNF();
			}
			dispGrammar(processed);
		}
	}
	
	public ContextFreeGrammar CFGFromText() {
		ContextFreeGrammar generatedCFG = new ContextFreeGrammar(input.getText());
		return generatedCFG;
	}
	

}
