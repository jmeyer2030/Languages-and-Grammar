package objects;

import java.util.ArrayList;
import java.util.Scanner;

public class ContextFreeGrammar {
	//variables
	ArrayList<String> nonTerminals;
	char[] terminals;
	String start;
	ProductionRuleSet ruleSet;
	
	//constructors
	public ContextFreeGrammar(String[] nonTerminals, char[] terminals, String start, ProductionRuleSet ruleSet) {
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
	
	public ContextFreeGrammar(String s) {
		Scanner scanner = new Scanner(s);
		int i = 0;
		setRuleSet(new ProductionRuleSet());
		while (scanner.hasNextLine()) {
		  String line = scanner.nextLine();
		  if (i == 0) {
			  setNonTerminals(line.split(""));//splits the string into a string array
		  } else if (i == 1) {
			  setTerminals(line.toCharArray());
		  } else {
			  getRuleSet().addRule(new ProductionRule(line));
		  }
		  // process the line
		  i++;
		}
		scanner.close();
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
			String unitProductionInput = unitProduction.getNonTerminal();
			String unitProductionOutput = unitProduction.getProduction()[0];
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
			if (rule.getNonTerminal().equals(unitProductionOutput)) {//if rule matches and it is 
				if (!removedRules.contains(new ProductionRule(unitProductionInput, rule.getProduction())))
					cfg.getRuleSet().addRule(new ProductionRule(unitProductionInput, rule.getProduction()));
			}
			length--;
		}
		
	}
	
	
	//removes mixed productions
	public ContextFreeGrammar removeMixed() {
		ContextFreeGrammar removeMixed = new ContextFreeGrammar(this);
		for (char terminal : this.getTerminals()) {
			removeMixed.getNonTerminals().add("T<sub>" + terminal + "</sub>");
			removeMixed.getRuleSet().addRule(new ProductionRule("T<sub>" + terminal + "</sub>", new String[] {Character.toString(terminal)}));
			
			ContextFreeGrammar temp = new ContextFreeGrammar(removeMixed);
			for (ProductionRule rule : temp.getRuleSet()) {
				if (rule.getProduction().length > 1) {//if production is longer than 1
						//then we replace each character in that string
					removeMixed.getRuleSet().deleteRule(rule);
					String[] production = new String[rule.getProduction().length];
					int i = 0;
					for (String prodElement : rule.getProduction()) {
						if (prodElement.equals(Character.toString(terminal))) {
							production[i] = ("T<sub>" + terminal + "</sub>");
						} else {
							production[i] = prodElement;
						}
						i++;
					}
					removeMixed.getRuleSet().addRule(new ProductionRule(rule.getNonTerminal(), production));		
				}
			}
		}

		//for (ProductionRule rule : )
		return removeMixed;
	}	
	
	//removes long productions
	public ContextFreeGrammar removeLong() {
		ContextFreeGrammar removeLong = new ContextFreeGrammar(this);
		this.getRuleSet().sort();
		
		
		String prevRuleNT = "";
		int supScript = 0;
		for (ProductionRule rule : this.getRuleSet()) {//for each rule in G
			if (rule.getProduction().length < 3)
				continue;
			String ruleNT = rule.getNonTerminal();
			
			//check if we are creating long productions for the same non terminal multiple times
			//if we are, we need to use a superscript for the new productions so we can keep the NT as the base for new NTs
			if (ruleNT.equals(prevRuleNT)) {
				supScript++;
			} else {
				supScript = 0;
			}
			
			
			int subScript = 1;
			removeLong.getRuleSet().deleteRule(rule);
			String[] ruleProd = rule.getProduction();
			
			String newNT;
			if (prevRuleNT.equals(ruleNT)) {
				newNT = rule.getNonTerminal() + "<sub>" + subScript + "</sub>" + "<sup>" +  supScript +"</sup>";
			} else {
				newNT = rule.getNonTerminal() + "<sub>" + subScript + "</sub>";
			}
			
			
			String[] production = new String[] {ruleProd[0], newNT};
			ProductionRule ruleToAdd = new ProductionRule(ruleNT, production);
			
			removeLong.getRuleSet().addRule(ruleToAdd); //add first rule
			removeLong.getNonTerminals().add(newNT);//add new non terminal
			
			for (int i = 1; i < rule.getProduction().length - 2; i++) {//for each character after first and before the penultimate
				String nextNT;
				if (prevRuleNT.equals(ruleNT)) {
					nextNT = rule.getNonTerminal() + "<sub>" + ++subScript + "</sub>" + "<sup>" +  supScript +"</sup>";
				} else {
					nextNT = rule.getNonTerminal() + "<sub>" + ++subScript + "</sub>";
				}
				
				ruleToAdd = new ProductionRule(newNT, new String[] {ruleProd[i], nextNT});//new rule(S_n -> XS_n+1)
				removeLong.getRuleSet().addRule(ruleToAdd);
				removeLong.getNonTerminals().add(nextNT);
				newNT = nextNT;
			}
			

			removeLong.getRuleSet().addRule(new ProductionRule(newNT, new String[] {ruleProd[ruleProd.length - 2], ruleProd[ruleProd.length - 1]}));//add final rule
			prevRuleNT = ruleNT;
		}		
		return removeLong;		
	}
	
	//helper methods
	public boolean isUnitProduction(ProductionRule pr) {
		if (pr.getProduction().length > 1)
			return false;
		for (String currNonTerm : this.getNonTerminals()) {//for each nonterminal
			if (currNonTerm.equals(pr.getProduction()[0])) //if this production is a non terminal
				return true;
		}
		return false;
	}
	
	//getter and setter methods
	public ArrayList<String> getNonTerminals() {
		return nonTerminals;
	}


	public void setNonTerminals(ArrayList<String> nonTerminals) {
		this.nonTerminals = nonTerminals;
	}
	
	public void setNonTerminals(String[] nonTerminals) {
		ArrayList<String> list = new ArrayList<String>();
		for (String nt : nonTerminals) {
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


	public String getStart() {
		return start;
	}


	public void setStart(String start) {
		this.start = start;
	}


	public ProductionRuleSet getRuleSet() {
		return ruleSet;
	}


	public void setRuleSet(ProductionRuleSet ruleSet) {
		this.ruleSet = ruleSet;
	}
}
