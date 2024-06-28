package objects;


public class ProductionRule implements Comparable<ProductionRule>{
	
	//variables
	String nonTerminal;
	
	String[] production;
	
	//constructors
	public ProductionRule(String nonTerminal, String[] production) {
		setNonTerminal(nonTerminal);
		setProduction(production);
	}
	
	public ProductionRule(ProductionRule PR) {
		setNonTerminal(PR.getNonTerminal());
		setProduction(PR.getProduction());
	}
	
	public ProductionRule(String s) { //This accepts rules of the form A->BCD...
		setNonTerminal(s.substring(0,1));
		setProduction(s.substring(3).split(""));
	}
	
	//methods
	public boolean equalTo(ProductionRule rule) {
		if (!this.getNonTerminal().equals(rule.getNonTerminal())) //if nonterminals are not equal
			return false;
		if (this.getProduction().length != rule.getProduction().length)//if numb of productions are not equal
			return false;
		for (int i = 0; i < this.getProduction().length; i++) {
			if (!this.getProduction()[i].equals(rule.getProduction()[i]))
				return false;
		}
        return true;
    }
	
	public boolean isUnitProduction(char[] terminals, String[] nonTerminals) {
		for (String currNonTerm : nonTerminals) {
			if (this.getProduction().length != 1)
				continue;				
			if(currNonTerm.equals(this.getProduction()[0])) 
				return true;			
		}
		return false;
	}
	
	public String productionToString() {
		StringBuilder bldr = new StringBuilder();
		for (String nt : this.getProduction()) {
			bldr.append(nt + "");
		}
		return bldr.toString();
	}
	
	//getter and setter methods
	public String getNonTerminal() {
		return this.nonTerminal;
	}
	
	public void setNonTerminal(String nonTerminal) {
		this.nonTerminal = nonTerminal;
	}
	
	public String[] getProduction() {
		return this.production;
	}
	
	public void setProduction(String[] production) {
		this.production = production;
	}

	@Override
	public int compareTo(ProductionRule rule) {
		// TODO Auto-generated method stub
		return this.getNonTerminal().compareTo(rule.getNonTerminal());
	}
}
