package objects;

public class ContextFreeGrammar {
	//variables
	char[] nonTerminals;
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
		ContextFreeGrammar CNF = new ContextFreeGrammar();
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
			removeMixed.getRuleSet().addRule(new ProductionRule((char) i, Character.toString(term)));
			i++;
		}
		//for (ProductionRule rule : )
		return removeMixed;
	}
	
	
	
	//removes long productions
	public ContextFreeGrammar removeLong() {
		ContextFreeGrammar removeLong = new ContextFreeGrammar(this);
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
	public char[] getNonTerminals() {
		return nonTerminals;
	}


	public void setNonTerminals(char[] nonTerminals) {
		this.nonTerminals = nonTerminals;
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
