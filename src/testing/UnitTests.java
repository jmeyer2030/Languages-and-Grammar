package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import classes.*;

class UnitTests{

	@BeforeEach
	void setUp() throws Exception {
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
	}

	char[] terminals = {'a', 'b', 'c'};
	char[] nonTerminals = {'A'};
	ContextFreeGrammar CFG = new ContextFreeGrammar();
	ProductionRule A = new ProductionRule('a', "abc");
	ProductionRule B = new ProductionRule('a', "abc");
	ProductionRule C = new ProductionRule('A', "A");
	ProductionRule D = new ProductionRule('A', "a");
	ProductionRuleSet testSet = new ProductionRuleSet();
	
	
	//ProductionRule
	@Test
	public void testEquals() {
		Assertions.assertTrue(A.equalTo(B));
	}
	
	@Test
	public void testProduction() {
		Assertions.assertTrue(A.getProduction().equals("abc"));
	}
	
	@Test
	public void testNonTerminal() {
		Assertions.assertTrue(A.getNonTerminal() == ('a'));
	}
	
	//ProductionRuleSet
	@Test
	public void testSetAdd() {
		testSet.addRule(A);
		testSet.addRule(B);
		Assertions.assertEquals(1, testSet.getSet().size());
	}
	
	@Test
	public void isUnitProduction1() {

		Assertions.assertEquals(true, CFG.isUnitProduction(C));
	}
	
	@Test
	public void isUnitProduction2() {
		Assertions.assertEquals(false, CFG.isUnitProduction(D));
	}
	
	public void isUnitProduction3() {
		Assertions.assertEquals(false, CFG.isUnitProduction(A));
	}
	
	@Test
	public void testSetDelete() {
		testSet.deleteRule(A);
		Assertions.assertEquals(0, testSet.getSet().size());
	}
	
	
	
	
	
	
	
	
	
	

}
