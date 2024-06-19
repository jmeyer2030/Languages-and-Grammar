package objects;

public class ProductionRule implements Comparable<ProductionRule>{
	
	//variables
	char nonTerminal;
	String production;
	
	//constructors
	public ProductionRule(char nonTerminal, String production) {
		setNonTerminal(nonTerminal);
		setProduction(production);
	}
	
	public ProductionRule(ProductionRule PR) {
		setNonTerminal(PR.getNonTerminal());
		setProduction(PR.getProduction());
	}
	
	//methods
	public boolean equalTo(ProductionRule rule) {
		if (this.getNonTerminal() != rule.getNonTerminal()) {//if nonterminals are not equal
			return false;
		}
        if (!this.getProduction().equals(rule.getProduction())) {//if productions are not equal
        	return false;
        }
        return true;
    }
	
	public boolean isUnitProduction(char[] terminals, char[] nonTerminals) {
		for (char currNonTerm : nonTerminals) {//for each terminal
			if (Character.toString(currNonTerm).equals(this.getProduction())) //if this production is a non terminal
				return true;
		}
		return false;
	}
	
	//getter and setter methods
	public char getNonTerminal() {
		return this.nonTerminal;
	}
	
	public void setNonTerminal(char nonTerminal) {
		this.nonTerminal = nonTerminal;
	}
	
	public String getProduction() {
		return this.production;
	}
	
	public void setProduction(String production) {
		this.production = production;
	}

	@Override
	public int compareTo(ProductionRule rule) {
		// TODO Auto-generated method stub
		return Character.compare(this.getNonTerminal(), rule.getNonTerminal());
	}
}
