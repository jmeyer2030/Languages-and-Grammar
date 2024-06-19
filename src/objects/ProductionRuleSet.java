package objects;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class ProductionRuleSet implements Iterable<ProductionRule> {
	
	LinkedList<ProductionRule> set = new LinkedList<ProductionRule>();
	
	//Constructors
	public ProductionRuleSet(ProductionRuleSet ruleSet) {
		for (ProductionRule PR : ruleSet.getSet()) {
			addRule(new ProductionRule(PR));
		}		
	}
	
	public ProductionRuleSet() {}
	
	


	//methods
	public void addRule(ProductionRule rule) {
		for (ProductionRule currRule : set) {//check if rule to be added is already there
			if (currRule.equalTo(rule))
				return;
		}
		getSet().add(new ProductionRule(rule));
	}
	
	public void deleteRule(ProductionRule rule) {
		
		boolean fullclear = false;
		while (!fullclear && this.getSet().size() != 0) {
			int i = 0;
			for (ProductionRule currRule : this.getSet()) {
				if (currRule.equalTo(rule)) {
					this.getSet().remove(i);
					break;
				}
				i++;
				if (i == this.getSet().size())
					fullclear = true;
			}
		}
	}
	
	public void sort() {
		Collections.sort(this.getSet());
	}
	
	public String printRules() {
		StringBuilder str = new StringBuilder();
		for (ProductionRule currRule : this.getSet()) {
			str.append(currRule.getNonTerminal() + "->" + currRule.getProduction() + "  ");
		}
		if (str.length() > 0) {
			str.setLength(str.length() - 2);
		}
		return str.toString();
	}
	
	public boolean contains(ProductionRule pr) {
		for (ProductionRule rule : this.getSet()) {
			if (pr.getNonTerminal() == rule.getNonTerminal() && pr.getProduction().equals(rule.getProduction()))
				return true;
		}
		return false;
		
	}
	
	//getter and setter methods
	public LinkedList<ProductionRule> getSet() {
		return set;
	}


	public void setSet(LinkedList<ProductionRule> set) {
		this.set = set;
	}
	
	
	//implements Iterable
	@Override
	public Iterator<ProductionRule> iterator() {
		// TODO Auto-generated method stub
		return set.iterator();
	}
}
