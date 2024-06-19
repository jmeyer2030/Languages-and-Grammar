package objects;

import java.util.ArrayList;

public class ContextFreeGrammar {
	//variables
	ArrayList<Character> nonTerminals;
	char[] terminals;
	char start;
	ProductionRuleSet ruleSet;
	
	//constructors
	public ContextFreeGrammar(char[] nonTerminals, char[] terminals, char start, ProductionRuleSet ruleSet) {
		setNonTerminals(nonTerminals);
		setTerminals(terminals);
		setStart(start);
		setRuleSet(ruleSet);
	}
	public ContextFreeGrammar() {
		
	}
	
	public ContextFreeGrammar(ContextFreeGrammar CFG) {
		setNonTerminals(CFG.getNonTerminals());
		setTerminals(CFG.getTerminals());
		setStart(CFG.getStart());
		setRuleSet(new ProductionRuleSet(CFG.getRuleSet()));
		
	}
	
	//Convert to CNF
	public ContextFreeGrammar convertToCNF() {
		ContextFreeGrammar CNF = new ContextFreeGrammar(this);
		CNF = CNF.removeUnits();
		CNF = CNF.removeMixed();
		CNF = CNF.removeLong();
		
		return CNF;
	}	
	
	public ContextFreeGrammar removeUnits() {
		ContextFreeGrammar gPrime = new ContextFreeGrammar(this);
		ProductionRuleSet removedRules = new ProductionRuleSet();
		while (true) {
			ProductionRule unitProduction = this.findUnit(gPrime);
			if (unitProduction == null)
				return gPrime;
			String unitProductionInput = Character.toString(unitProduction.getNonTerminal());
			String unitProductionOutput = unitProduction.getProduction();
			removedRules.addRule(unitProduction);
			gPrime.getRuleSet().deleteRule(unitProduction);
			removeUnitsRebuild(gPrime, removedRules, unitProductionInput, unitProductionOutput);
			
		}
	}
	
	
	//helper for unit productions
	public ProductionRule findUnit(ContextFreeGrammar CFG){
		for (ProductionRule pr : CFG.getRuleSet()) {
			if (CFG.isUnitProduction(pr))
				return pr;
		}
		return null;
	}
	
	public void removeUnitsRebuild(ContextFreeGrammar cfg, ProductionRuleSet removedRules, String unitProductionInput, String unitProductionOutput) {
		int length = cfg.getRuleSet().getSet().size();
		
		while (length > 0) {//for each rule in the set
			ProductionRule rule = cfg.getRuleSet().getSet().get(length - 1);
			if (Character.toString(rule.getNonTerminal()).equals(unitProductionOutput)) {//if rule matches and it is 
				if (!removedRules.contains(new ProductionRule(unitProductionInput.charAt(0), rule.getProduction())))
					cfg.getRuleSet().addRule(new ProductionRule(unitProductionInput.charAt(0), rule.getProduction()));
			}
			length--;
		}
		
	}
	
	
	//removes mixed productions
	//Create a new rule for each terminal, then switch each instance of that terminal in rules with length greater than 1 for the new nonterminal.
	/*
	 *  For each terminal:
	 * 		add new rule
	 * 		for each rule
	 * 			if length greater than 1 and 
	 * */
	public ContextFreeGrammar removeMixed() {
		ContextFreeGrammar removeMixed = new ContextFreeGrammar(this);
		int i = 48;
		for (char term : this.getTerminals()) {
			removeMixed.getNonTerminals().add((char) i);
			removeMixed.getRuleSet().addRule(new ProductionRule((char) i, Character.toString(term)));
			
			ContextFreeGrammar temp = new ContextFreeGrammar(removeMixed);
			for (ProductionRule rule : temp.getRuleSet()) {
				if (rule.getProduction().length() > 1) {//if production is longer than 1
						//then we replace each character in that string
					removeMixed.getRuleSet().deleteRule(rule);
					StringBuilder str = new StringBuilder();
					for (char prodChar : rule.getProduction().toCharArray()) {
						if (prodChar == term) {
							str.append((char) i);
						} else {
							str.append(prodChar);
						}
							
					}
						
					removeMixed.getRuleSet().addRule(new ProductionRule(rule.getNonTerminal(), str.toString()));
					
				}
			}
			i++;
		}

		//for (ProductionRule rule : )
		return removeMixed;
	}
	
	
	
	//removes long productions
	public ContextFreeGrammar removeLong() {
		ContextFreeGrammar removeLong = new ContextFreeGrammar(this);
		int NTAscii = 77;
		for (ProductionRule rule : this.getRuleSet()) {
			if (rule.getProduction().length() < 3)
				continue;
			removeLong.getRuleSet().deleteRule(rule);
			char[] ruleProd = rule.getProduction().toCharArray();
			
			removeLong.getRuleSet().addRule(new ProductionRule(rule.getNonTerminal(), "" + ruleProd[0] + (char) ++NTAscii)); //add first rule
			
			for (int i = 1; i < ruleProd.length - 2; i++) {//for each character that it is longer than 2. i.e: if 3, we do this once
				removeLong.getNonTerminals().add((char) NTAscii);
				removeLong.getRuleSet().addRule(new ProductionRule((char) NTAscii, "" + ruleProd[i] + (char) ++NTAscii));
				 
			}
			
			removeLong.getRuleSet().addRule(new ProductionRule((char) NTAscii, "" + ruleProd[ruleProd.length - 2] + ruleProd[ruleProd.length - 1]));
			
			
			
		}
		
		
		return removeLong;
		
	}
	
	//helper methods
	public boolean isUnitProduction(ProductionRule pr) {
		for (char currNonTerm : this.getNonTerminals()) {//for each nonterminal
			if (Character.toString(currNonTerm).equals(pr.getProduction())) //if this production is a non terminal
				return true;
		}
		return false;
	}
	
	//getter and setter methods
	public ArrayList<Character> getNonTerminals() {
		return nonTerminals;
	}


	public void setNonTerminals(ArrayList<Character> nonTerminals) {
		this.nonTerminals = nonTerminals;
	}
	
	public void setNonTerminals(char[] nonTerminals) {
		ArrayList<Character> list = new ArrayList<Character>();
		for (char nt : nonTerminals) {
			list.add(nt);
		}
		this.nonTerminals = list;
	}


	public char[] getTerminals() {
		return terminals;
	}


	public void setTerminals(char[] terminals) {
		this.terminals = terminals;
	}


	public char getStart() {
		return start;
	}


	public void setStart(char start) {
		this.start = start;
	}


	public ProductionRuleSet getRuleSet() {
		return ruleSet;
	}


	public void setRuleSet(ProductionRuleSet ruleSet) {
		this.ruleSet = ruleSet;
	}
}
