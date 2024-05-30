package classes;

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
	
	//removes unit productions
	public ContextFreeGrammar removeUnits() {
		ContextFreeGrammar removeUnits = new ContextFreeGrammar(this);
		
		//iterate to find a unit production, remember the output. Iterate to find productions, inputing the previous output. 
		for (ProductionRule rule1 : this.getRuleSet()) {
			if (this.isUnitProduction(rule1)) {
				removeUnits.getRuleSet().deleteRule(rule1);
				//String production = rule1.getProduction();
				//ProductionRule tempRule = new ProductionRule(rule1);
				for (ProductionRule rule2 : this.getRuleSet()) {
					if (Character.toString(rule2.getNonTerminal()).equals(rule1.getProduction())) {
						removeUnits.getRuleSet().addRule(new ProductionRule(rule1.getNonTerminal(), rule2.getProduction()));
					}
				}
				break;
			}	
		}
		/*
		 *  Loop(main cfg PRs)
		 *  	if (unit)
		 *  		Loop(mainCFGPRs)
		 *  			if PR has nt as production of outer, del from 
		 * 
		 * 
		 * 
		 * 
		 */

		return removeUnits;
	}
	
	//removes mixed productions
	public ContextFreeGrammar removeMixed() {
		ContextFreeGrammar removeMixed = new ContextFreeGrammar(this);
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
