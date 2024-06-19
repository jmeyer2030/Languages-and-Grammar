package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import objects.*;

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
		Assertions.assertEquals("B->ab  A->ab", output.getRuleSet().printRules());
	}
	
	/*
	 * A -> B
	 * B -> C
	 * C -> D
	 * D -> a
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	*/
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
		Assertions.assertEquals("D->a  C->a  A->a  B->a", output.getRuleSet().printRules());
	}
	
	@Test
	public void testRemoveUnitProductions5() {//A -> a A->ab 
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule('S', "XY");
		ProductionRule B = new ProductionRule('X', "A");
		ProductionRule C = new ProductionRule('A', "B");
		ProductionRule D = new ProductionRule('A', "a");
		ProductionRule E = new ProductionRule('B', "b");
		ProductionRule F = new ProductionRule('Y', "T");
		ProductionRule G = new ProductionRule('T', "Y");
		ProductionRule H = new ProductionRule('T', "c");
		char[] terminals = {'a', 'b'};
		char[] nonTerminals = {'S', 'X', 'A', 'T', 'B', 'Y'};
		ProductionRuleSet set = new ProductionRuleSet();
		CFG.setRuleSet(set);
		CFG.getRuleSet().addRule(A);
		CFG.getRuleSet().addRule(B);
		CFG.getRuleSet().addRule(C);
		CFG.getRuleSet().addRule(D);
		CFG.getRuleSet().addRule(E);
		CFG.getRuleSet().addRule(F);
		CFG.getRuleSet().addRule(G);
		CFG.getRuleSet().addRule(H);
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		
		ContextFreeGrammar output = CFG.removeUnits();
		Assertions.assertEquals("S->XY  A->a  B->b  T->c  X->a  A->b  Y->c  X->b", output.getRuleSet().printRules());
	}
	@Test
	public void removeMixed1() {
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule('A', "Bb");
		ProductionRule B = new ProductionRule('B', "a");
		char[] terminals = {'a', 'b'};
		char[] nonTerminals = {'A', 'B'};
		ProductionRuleSet set = new ProductionRuleSet();
		CFG.setRuleSet(set);
		CFG.getRuleSet().addRule(A);
		CFG.getRuleSet().addRule(B);
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		ContextFreeGrammar output = CFG.removeMixed();
		Assertions.assertEquals("B->a  0->a  1->b  A->B1", output.getRuleSet().printRules());
		
	}
	
	@Test
	public void removeMixed2() {
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule('A', "Bb");
		ProductionRule B = new ProductionRule('B', "a");
		ProductionRule C = new ProductionRule('A', "abc");
		char[] terminals = {'a', 'b', 'c'};
		char[] nonTerminals = {'A', 'B'};
		ProductionRuleSet set = new ProductionRuleSet();
		CFG.setRuleSet(set);
		CFG.getRuleSet().addRule(A);
		CFG.getRuleSet().addRule(B);
		CFG.getRuleSet().addRule(C);
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		ContextFreeGrammar output = CFG.removeMixed();
		Assertions.assertEquals("B->a  0->a  1->b  2->c  A->B1  A->012", output.getRuleSet().printRules());
	}
	
	@Test
	public void removeLong1() {
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule('A', "BCD");
		char[] terminals = {'a', 'b', 'c'};
		char[] nonTerminals = {'A', 'B', 'C', 'D'};
		ProductionRuleSet set = new ProductionRuleSet();
		CFG.setRuleSet(set);
		CFG.getRuleSet().addRule(A);
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		ContextFreeGrammar output = CFG.removeLong();
		Assertions.assertEquals("A->BN  N->CD", output.getRuleSet().printRules());
	}
	
	@Test
	public void removeLong2() {
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule('A', "BCDE");
		char[] terminals = {'a', 'b', 'c'};
		char[] nonTerminals = {'A', 'B', 'C', 'D', 'E'};
		ProductionRuleSet set = new ProductionRuleSet();
		CFG.setRuleSet(set);
		CFG.getRuleSet().addRule(A);
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		ContextFreeGrammar output = CFG.removeLong();
		Assertions.assertEquals("A->BN  N->CO  O->DE", output.getRuleSet().printRules());
	}
	
	@Test
	public void removeLong3() {
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule('A', "BCDEFG");
		char[] terminals = {'a', 'b', 'c'};
		char[] nonTerminals = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
		ProductionRuleSet set = new ProductionRuleSet();
		CFG.setRuleSet(set);
		CFG.getRuleSet().addRule(A);
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		ContextFreeGrammar output = CFG.removeLong();
		Assertions.assertEquals("A->BN  N->CO  O->DP  P->EQ  Q->FG", output.getRuleSet().printRules());
	}
	
	@Test
	public void removeLong4() {
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule('A', "BCDEFG");
		ProductionRule B = new ProductionRule('B', "BCD");
		char[] terminals = {'a', 'b', 'c'};
		char[] nonTerminals = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
		ProductionRuleSet set = new ProductionRuleSet();
		CFG.setRuleSet(set);
		CFG.getRuleSet().addRule(A);
		CFG.getRuleSet().addRule(B);
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		ContextFreeGrammar output = CFG.removeLong();
		Assertions.assertEquals("A->BN  N->CO  O->DP  P->EQ  Q->FG  B->BR  R->CD", output.getRuleSet().printRules());
	}
	
	@Test
	public void convertToCNF1() {
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule('D', "aACa");
		ProductionRule B = new ProductionRule('D', "aAa");
		ProductionRule C = new ProductionRule('D', "aCa");
		ProductionRule D = new ProductionRule('D', "aa");
		ProductionRule E = new ProductionRule('A', "B");
		ProductionRule F = new ProductionRule('A', "a");
		ProductionRule G = new ProductionRule('B', "C");
		ProductionRule H = new ProductionRule('B', "c");
		ProductionRule I = new ProductionRule('C', "cC");
		ProductionRule J = new ProductionRule('C', "c");
		char[] terminals = {'a', 'b', 'c'};
		char[] nonTerminals = {'A', 'B', 'C', 'D'};
		ProductionRuleSet set = new ProductionRuleSet();
		CFG.setRuleSet(set);
		CFG.getRuleSet().addRule(A);
		CFG.getRuleSet().addRule(B);
		CFG.getRuleSet().addRule(C);
		CFG.getRuleSet().addRule(D);
		CFG.getRuleSet().addRule(E);
		CFG.getRuleSet().addRule(F);
		CFG.getRuleSet().addRule(G);
		CFG.getRuleSet().addRule(H);
		CFG.getRuleSet().addRule(I);
		CFG.getRuleSet().addRule(J);
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		ContextFreeGrammar output = CFG.convertToCNF();
		output.getRuleSet().sort();
		Assertions.assertEquals("0->a  1->b  2->c  A->a  A->c  A->2C  B->c  B->2C  C->c  C->2C  D->00  D->0N  D->0P  D->0Q  N->AO  O->C0  P->A0  Q->C0", output.getRuleSet().printRules());
	}
	
	@Test
	public void sortPrint() {
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule('A', "a");
		ProductionRule B = new ProductionRule('C', "a");
		ProductionRule C = new ProductionRule('B', "a");
		ProductionRule D = new ProductionRule('E', "a");
		ProductionRule E = new ProductionRule('D', "a");
		char[] terminals = {'a', 'b', 'c'};
		char[] nonTerminals = {'A', 'B', 'C', 'D', 'E'};
		ProductionRuleSet set = new ProductionRuleSet();
		CFG.setRuleSet(set);
		CFG.getRuleSet().addRule(A);
		CFG.getRuleSet().addRule(B);
		CFG.getRuleSet().addRule(C);
		CFG.getRuleSet().addRule(D);
		CFG.getRuleSet().addRule(E);
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		CFG.getRuleSet().sort();
		Assertions.assertEquals("A->a  B->a  C->a  D->a  E->a", CFG.getRuleSet().printRules());
	}
	
	
	
	
	
	

}
