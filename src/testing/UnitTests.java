package testing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import objects.*;

class UnitTests{

	@BeforeEach
	void setUp() throws Exception {

	}
	
	
	//ProductionRule
	@Test
	public void testEquals() {
		ProductionRule A = new ProductionRule("A", new String[] {"a", "b", "c"});
		ProductionRule B = new ProductionRule("A", new String[] {"a", "b", "c"});
		Assertions.assertTrue(A.equalTo(B));
	}
	
	@Test
	public void testProduction() {
		ProductionRule A = new ProductionRule("A", new String[] {"a", "b", "c"});
		Assertions.assertTrue(A.getProduction()[0] == "a" && A.getProduction()[1] == "b" && A.getProduction()[2] == "c");
	}
	
	@Test
	public void testNonTerminal() {
		ProductionRule A = new ProductionRule("A", new String[] {"a", "b", "c"});
		Assertions.assertTrue(A.getNonTerminal().equals("A"));
	}
	
	//ProductionRuleSet
	@Test
	public void testSetAdd() {
		ProductionRuleSet testSet = new ProductionRuleSet();
		ProductionRule A = new ProductionRule("A", new String[] {"a", "b", "c"});
		testSet.addRule(A);
		Assertions.assertEquals(1, testSet.getSet().size());
	}
	
	public void testSetAdd2() {
		ProductionRuleSet testSet = new ProductionRuleSet();
		ProductionRule A = new ProductionRule("A", new String[] {"a", "b", "c"});
		ProductionRule B = new ProductionRule("A", new String[] {"a", "b", "c"});
		testSet.addRule(A);
		testSet.addRule(B);
		Assertions.assertEquals(1, testSet.getSet().size());
	}
	
	//CFG
	@Test
	public void isUnitProduction1() {
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule("A", new String[] {"A"});
		char[] terminals = {'a', 'b', 'c'};
		String[] nonTerminals = {"A"};
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		Assertions.assertEquals(true, CFG.isUnitProduction(A));
	}
	
	@Test
	public void isUnitProduction2() {
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule("A", new String[] {"a"});
		char[] terminals = {'a', 'b', 'c'};
		String[] nonTerminals = {"A"};
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		Assertions.assertEquals(false, CFG.isUnitProduction(A));
	}
	
	public void isUnitProduction3() {
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule("A", new String[] {"a", "b", "c"});
		char[] terminals = {'a', 'b', 'c'};
		String[] nonTerminals = {"A"};
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		Assertions.assertEquals(false, CFG.isUnitProduction(A));
	}
	
	@Test
	public void testSetDelete() {
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRuleSet set = new ProductionRuleSet();
		CFG.setRuleSet(set);
		ProductionRule A = new ProductionRule("A", new String[] {"A"});
		ProductionRule B = new ProductionRule("A", new String[] {"a", "b"});
		char[] terminals = {'a', 'b', 'c'};
		String[] nonTerminals = {"A"};
		
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		CFG.getRuleSet().addRule(A);
		CFG.getRuleSet().addRule(B);
		CFG.getRuleSet().deleteRule(A);
		
		Assertions.assertEquals(1, CFG.getRuleSet().getSet().size());
	}
	
	@Test
	public void testRemoveUnitProductions1() {//A -> a A->ab 
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule("A", new String[] {"a"});
		ProductionRule B = new ProductionRule("A", new String[] {"a", "b"});
		char[] terminals = {'a', 'b', 'c'};
		String[] nonTerminals = {"A"};
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
		ProductionRule A = new ProductionRule("A", new String[] {"A"});
		ProductionRule B = new ProductionRule("A", new String[] {"a", "b"});
		char[] terminals = {'a', 'b', 'c'};
		String[] nonTerminals = {"A", "B"};
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
		ProductionRule A = new ProductionRule("A", new String[] {"B"});
		ProductionRule B = new ProductionRule("B", new String[] {"a", "b"});
		char[] terminals = {'a', 'b', 'c'};
		String[] nonTerminals = {"A", "B"};
		ProductionRuleSet set = new ProductionRuleSet();
		CFG.setRuleSet(set);
		CFG.getRuleSet().addRule(A);
		CFG.getRuleSet().addRule(B);
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		
		ContextFreeGrammar output = CFG.removeUnits();
		Assertions.assertEquals("B->ab  A->ab", output.getRuleSet().printRules());
	}
	
	@Test
	public void testRemoveUnitProductions4() {//A -> a A->ab 
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule("A", new String[] {"B"});
		ProductionRule B = new ProductionRule("B", new String[] {"C"});
		ProductionRule C = new ProductionRule("C", new String[] {"D"});
		ProductionRule D = new ProductionRule("D", new String[] {"a"});
		char[] terminals = {'a', 'b', 'c', 'D'};
		String[] nonTerminals = {"A", "B", "C", "D"};
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
		ProductionRule A = new ProductionRule("S", new String[] {"X", "Y"});
		ProductionRule B = new ProductionRule("X", new String[] {"A"});
		ProductionRule C = new ProductionRule("A", new String[] {"B"});
		ProductionRule D = new ProductionRule("A", new String[] {"a"});
		ProductionRule E = new ProductionRule("B", new String[] {"b"});
		ProductionRule F = new ProductionRule("Y", new String[] {"T"});
		ProductionRule G = new ProductionRule("T", new String[] {"Y"});
		ProductionRule H = new ProductionRule("T", new String[] {"c"});
		char[] terminals = {'a', 'b'};
		String[] nonTerminals = {"S", "X", "A", "T", "B", "Y"};
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
		ProductionRule A = new ProductionRule("A", new String[] {"B", "b"});
		ProductionRule B = new ProductionRule("B", new String[] {"a"});
		char[] terminals = {'a', 'b'};
		String[] nonTerminals = {"A", "B"};
		ProductionRuleSet set = new ProductionRuleSet();
		CFG.setRuleSet(set);
		CFG.getRuleSet().addRule(A);
		CFG.getRuleSet().addRule(B);
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		ContextFreeGrammar output = CFG.removeMixed();
		Assertions.assertEquals("B->a  T<sub>a<sub>->a  T<sub>b<sub>->b  A->BT<sub>b<sub>", output.getRuleSet().printRules());
		
	}
	
	@Test
	public void removeMixed2() {
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule("A", new String[] {"B", "b"});
		ProductionRule B = new ProductionRule("B", new String[] {"a"});
		ProductionRule C = new ProductionRule("A", new String[] {"a", "b", "c"});
		char[] terminals = {'a', 'b', 'c'};
		String[] nonTerminals = {"A", "B"};
		ProductionRuleSet set = new ProductionRuleSet();
		CFG.setRuleSet(set);
		CFG.getRuleSet().addRule(A);
		CFG.getRuleSet().addRule(B);
		CFG.getRuleSet().addRule(C);
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		ContextFreeGrammar output = CFG.removeMixed();
		Assertions.assertEquals("B->a  T<sub>a<sub>->a  T<sub>b<sub>->b  T<sub>c<sub>->c  A->BT<sub>b<sub>  A->T<sub>a<sub>T<sub>b<sub>T<sub>c<sub>", output.getRuleSet().printRules());
	}
	
	@Test
	public void removeLong1() {
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule("A", new String[] {"B", "C", "D"});
		char[] terminals = {'a', 'b', 'c'};
		String[] nonTerminals = {"A", "B", "C", "D"};
		ProductionRuleSet set = new ProductionRuleSet();
		CFG.setRuleSet(set);
		CFG.getRuleSet().addRule(A);
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		ContextFreeGrammar output = CFG.removeLong();
		Assertions.assertEquals("A->BA<sub>1<sub>  A<sub>1<sub>->CD", output.getRuleSet().printRules());
	}
	
	@Test
	public void removeLong2() {
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule("A", new String[] {"B", "C", "D", "E"});
		char[] terminals = {'a', 'b', 'c'};
		String[] nonTerminals = {"A", "B", "C", "D", "E"};
		ProductionRuleSet set = new ProductionRuleSet();
		CFG.setRuleSet(set);
		CFG.getRuleSet().addRule(A);
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		ContextFreeGrammar output = CFG.removeLong();
		Assertions.assertEquals("A->BA<sub>1<sub>  A<sub>1<sub>->CA<sub>2<sub>  A<sub>2<sub>->DE", output.getRuleSet().printRules());
	}
	
	@Test
	public void removeLong3() {
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule("A", new String[] {"B", "C", "D", "E", "F", "G"});
		char[] terminals = {'a', 'b', 'c'};
		String[] nonTerminals = {"A", "B", "C", "D", "E", "F", "G"};
		ProductionRuleSet set = new ProductionRuleSet();
		CFG.setRuleSet(set);
		CFG.getRuleSet().addRule(A);
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		ContextFreeGrammar output = CFG.removeLong();
		Assertions.assertEquals("A->BA<sub>1<sub>  A<sub>1<sub>->CA<sub>2<sub>  A<sub>2<sub>->DA<sub>3<sub>  A<sub>3<sub>->EA<sub>4<sub>  A<sub>4<sub>->FG", output.getRuleSet().printRules());
	}

	
	@Test
	public void removeLong4() {
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule("A", new String[] {"B", "C", "D", "E", "F", "G"});
		ProductionRule B = new ProductionRule("B", new String[] {"B", "C", "D"});
		char[] terminals = {'a', 'b', 'c'};
		String[] nonTerminals = {"A", "B", "C", "D", "E", "F", "G"};
		ProductionRuleSet set = new ProductionRuleSet();
		CFG.setRuleSet(set);
		CFG.getRuleSet().addRule(A);
		CFG.getRuleSet().addRule(B);
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		ContextFreeGrammar output = CFG.removeLong();
		Assertions.assertEquals("A->BA<sub>1<sub>  A<sub>1<sub>->CA<sub>2<sub>  A<sub>2<sub>->DA<sub>3<sub>  A<sub>3<sub>->EA<sub>4<sub>  A<sub>4<sub>->FG  B->BB<sub>1<sub>  B<sub>1<sub>->CD", output.getRuleSet().printRules());
	}
	
	@Test
	public void removeLong5() {
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule("A", new String[] {"B", "C", "D", "E", "F", "G"});
		ProductionRule B = new ProductionRule("A", new String[] {"B", "C", "D"});
		char[] terminals = {'a', 'b', 'c'};
		String[] nonTerminals = {"A", "B", "C", "D", "E", "F", "G"};
		ProductionRuleSet set = new ProductionRuleSet();
		CFG.setRuleSet(set);
		CFG.getRuleSet().addRule(A);
		CFG.getRuleSet().addRule(B);
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		ContextFreeGrammar output = CFG.removeLong();
		Assertions.assertEquals("A->BA<sub>1<sub>  A<sub>1<sub>->CA<sub>2<sub>  A<sub>2<sub>->DA<sub>3<sub>  A<sub>3<sub>->EA<sub>4<sub>  A<sub>4<sub>->FG  A->BA<sub>1<sub><sup>1</sup>  A<sub>1<sub><sup>1</sup>->CD", output.getRuleSet().printRules());
	}
	
	@Test
	public void removeLong6() {
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule("A", new String[] {"B", "C", "D", "E", "F"});
		ProductionRule B = new ProductionRule("A", new String[] {"G", "H", "I", "J", "K"});
		char[] terminals = {'a', 'b', 'c'};
		String[] nonTerminals = {"A", "B", "C", "D", "E", "F", "G"};
		ProductionRuleSet set = new ProductionRuleSet();
		CFG.setRuleSet(set);
		CFG.getRuleSet().addRule(A);
		CFG.getRuleSet().addRule(B);
		CFG.setNonTerminals(nonTerminals);
		CFG.setTerminals(terminals);
		ContextFreeGrammar output = CFG.removeLong();
		Assertions.assertEquals("A->BA<sub>1<sub>  A<sub>1<sub>->CA<sub>2<sub>  A<sub>2<sub>->DA<sub>3<sub>  A<sub>3<sub>->EF  A->GA<sub>1<sub><sup>1</sup>  A<sub>1<sub><sup>1</sup>->HA<sub>2<sub><sup>1</sup>  A<sub>2<sub><sup>1</sup>->IA<sub>3<sub><sup>1</sup>  A<sub>3<sub><sup>1</sup>->JK", output.getRuleSet().printRules());
	}
	
	@Test
	public void convertToCNF1() {
		ContextFreeGrammar CFG = new ContextFreeGrammar();
		ProductionRule A = new ProductionRule("S", new String[] {"a", "A", "C", "a"});
		ProductionRule B = new ProductionRule("S", new String[] {"a", "A", "a"});
		ProductionRule C = new ProductionRule("S", new String[] {"a", "C", "a"});
		ProductionRule D = new ProductionRule("S", new String[] {"a", "a"});
		ProductionRule E = new ProductionRule("A", new String[] {"B"});
		ProductionRule F = new ProductionRule("A", new String[] {"a"});
		ProductionRule G = new ProductionRule("B", new String[] {"C"});
		ProductionRule H = new ProductionRule("B", new String[] {"c"});
		ProductionRule I = new ProductionRule("C", new String[] {"c", "C"});
		ProductionRule J = new ProductionRule("C", new String[] {"c"});
		char[] terminals = {'a', 'b', 'c'};
		String[] nonTerminals = {"A", "B", "C", "S"};
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
		Assertions.assertEquals("T<sub>a<sub>->a  T<sub>b<sub>->b  T<sub>c<sub>->c  A->a  A->c  A->T<subc<sub>C  B->c  B->T<sub>c<sub>C  C->c  C->T<sub>c<sub>C  D->T<sub>a<sub>T<sub>a<sub>  D->T<sub>a<sub>D<sub>1<sub>  D->T<sub>a<sub>D<sub>1<sub>  D->T<sub>a<sub>Q  N->AO  O->C0  P->A0  Q->C0", output.getRuleSet().printRules());
	}
	/*
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
	*/
}
