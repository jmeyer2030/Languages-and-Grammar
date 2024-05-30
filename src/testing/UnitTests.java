package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import classes.*;

class UnitTests{
	/*
	char[] terminals = {'a', 'b', 'c'};
	char[] nonTerminals = {'A'};
	ContextFreeGrammar CFG = new ContextFreeGrammar();
	ProductionRule A = new ProductionRule('a', "abc");
	ProductionRule B = new ProductionRule('a', "abc");
	ProductionRule C = new ProductionRule('A', "A");
	ProductionRule D = new ProductionRule('A', "a");
	ProductionRuleSet testSet = new ProductionRuleSet();

	public static boolean setUpDone = false;
	*/
	@BeforeEach
	void setUp() throws Exception {
		/*
		if (!setUpDone) {
			CFG.setNonTerminals(nonTerminals);
			CFG.setTerminals(terminals);
			CFG.setRuleSet(testSet);
			setUpDone = true;
		}
		CFG.getRuleSet().addRule(A);
		CFG.getRuleSet().addRule(B);
		*/
	}
	
	
	//ProductionRule
	@Test
	public void testEquals() {
		ProductionRule A = new ProductionRule('a', "abc");
		ProductionRule B = new ProductionRule('a', "abc");
		Assertions.assertTrue(A.equalTo(B));
	}
	
	@Test
	public void testProduction() {
		ProductionRule A = new ProductionRule('a', "abc");
		Assertions.assertTrue(A.getProduction().equals("abc"));
	}
	
	@Test
	public void testNonTerminal() {
		ProductionRule A = new ProductionRule('a', "abc");
		Assertions.assertTrue(A.getNonTerminal() == ('a'));
	}
	
	//ProductionRuleSet
	@Test
	public void testSetAdd() {
		ProductionRuleSet testSet = new ProductionRuleSet();
		ProductionRule A = new ProductionRule('a', "abc");
		testSet.addRule(A);
		Assertions.assertEquals(1, testSet.getSet().size());
	}
	
	public void testSetAdd2() {
		ProductionRuleSet testSet = new ProductionRuleSet();
		ProductionRule A = new ProductionRule('a', "abc");
		ProductionRule B = new ProductionRule('a', "abc");
		testSet.addRule(A);
		testSet.addRule(B);
		Assertions.assertEquals(1, testSet.getSet().size());
	}
	
	//CFG
	@Test
	public void isUnitProduction1() {
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule('A', "A");
		char[] terminals = {'a', 'b', 'c'};
		char[] nonTerminals = {'A'};
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		Assertions.assertEquals(true, CFG.isUnitProduction(A));
	}
	
	@Test
	public void isUnitProduction2() {
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule('A', "a");
		char[] terminals = {'a', 'b', 'c'};
		char[] nonTerminals = {'A'};
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		Assertions.assertEquals(false, CFG.isUnitProduction(A));
	}
	
	public void isUnitProduction3() {
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule('A', "AB");
		char[] terminals = {'a', 'b', 'c'};
		char[] nonTerminals = {'A'};
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		Assertions.assertEquals(false, CFG.isUnitProduction(A));
	}
	
	@Test
	public void testSetDelete() {
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRuleSet set = new ProductionRuleSet();
		CFG.setRuleSet(set);
		ProductionRule A = new ProductionRule('A', "A");
		ProductionRule B = new ProductionRule('A', "ab");
		char[] terminals = {'a', 'b', 'c'};
		char[] nonTerminals = {'A'};
		
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		CFG.getRuleSet().addRule(A);
		CFG.getRuleSet().addRule(B);
		//CFG.getRuleSet().deleteRule(A);
		
		Assertions.assertEquals(2, CFG.getRuleSet().getSet().size());
	}
	
	@Test
	public void testRemoveUnitProductions1() {//A -> a A->ab 
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule('A', "a");
		ProductionRule B = new ProductionRule('A', "ab");
		char[] terminals = {'a', 'b', 'c'};
		char[] nonTerminals = {'A'};
		ProductionRuleSet set = new ProductionRuleSet();
		CFG.setRuleSet(set);
		CFG.getRuleSet().addRule(A);
		CFG.getRuleSet().addRule(B);
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		
		ContextFreeGrammar output = CFG.removeUnits();
		Assertions.assertEquals("A->a  A->ab", output.getRuleSet().printRules());
	}
	
	@Test
	public void testRemoveUnitProductions2() {//A -> a A->ab 
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule('A', "B");
		ProductionRule B = new ProductionRule('A', "ab");
		char[] terminals = {'a', 'b', 'c'};
		char[] nonTerminals = {'A', 'B'};
		ProductionRuleSet set = new ProductionRuleSet();
		CFG.setRuleSet(set);
		CFG.getRuleSet().addRule(A);
		CFG.getRuleSet().addRule(B);
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		
		ContextFreeGrammar output = CFG.removeUnits();
		Assertions.assertEquals("A->ab", output.getRuleSet().printRules());
	}
	
	@Test
	public void testRemoveUnitProductions3() {//A -> a A->ab 
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule('A', "B");
		ProductionRule B = new ProductionRule('B', "ab");
		char[] terminals = {'a', 'b', 'c'};
		char[] nonTerminals = {'A', 'B'};
		ProductionRuleSet set = new ProductionRuleSet();
		CFG.setRuleSet(set);
		CFG.getRuleSet().addRule(A);
		CFG.getRuleSet().addRule(B);
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		
		ContextFreeGrammar output = CFG.removeUnits();
		Assertions.assertEquals("A -> ab", output.getRuleSet().printRules());
	}
	
	@Test
	public void testRemoveUnitProductions4() {//A -> a A->ab 
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule('A', "B");
		ProductionRule B = new ProductionRule('B', "C");
		ProductionRule C = new ProductionRule('C', "D");
		ProductionRule D = new ProductionRule('D', "a");
		char[] terminals = {'a', 'b', 'c', 'D'};
		char[] nonTerminals = {'A', 'B', 'C', 'D'};
		ProductionRuleSet set = new ProductionRuleSet();
		CFG.setRuleSet(set);
		CFG.getRuleSet().addRule(A);
		CFG.getRuleSet().addRule(B);
		CFG.getRuleSet().addRule(C);
		CFG.getRuleSet().addRule(D);
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		
		ContextFreeGrammar output = CFG.removeUnits();
		Assertions.assertEquals("A -> ab", output.getRuleSet().printRules());
	}
	
	
	
	
	
	
	
	
	
	

}
