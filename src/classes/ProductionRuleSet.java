package classes;
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
		getSet().add(rule);
	}
	
	public void deleteRule(ProductionRule rule) {
		int i = 0;
		for (ProductionRule currRule : set) {
			if (currRule.equalTo(rule))
				this.getSet().remove(i);
			i++;
		}
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
