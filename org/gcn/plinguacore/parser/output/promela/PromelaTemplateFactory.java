package org.gcn.plinguacore.parser.output.promela;

import java.util.*;

import org.gcn.plinguacore.parser.output.promela.PromelaTemplateFactory;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

public class PromelaTemplateFactory {
	
	private STGroup template;
	private static PromelaTemplateFactory instance = null;
	
	public static PromelaTemplateFactory getInstance(String stGroupFileName) {
		return instance != null ? instance : (instance = new PromelaTemplateFactory(stGroupFileName));
	}
	
	private PromelaTemplateFactory(String stGroupFileName) {
		template = new STGroupFile(stGroupFileName);
	}
	
	public ST buildMainStructure(int maxStepCount, Collection<String> symbols, String symbolsMapping, String procRunner, Collection<String> membraneProcTypes, String initialMultiSets) {
		return template.getInstanceOf("MainStructure").add("maxStepCount", maxStepCount).add("symbols", normalizeSymbols(symbols)).add("symbolsMapping", symbolsMapping)
				.add("procRunner", procRunner).add("membraneProcTypes", membraneProcTypes).add("initialMultiSets", initialMultiSets);
	}
	
	public ST buildSymbolsMapping(Collection<String> symbols, Collection<Integer> indices, int symbolCount) {
		return template.getInstanceOf("SymbolsMapping").add("symbols", normalizeSymbols(symbols)).add("indices", indices)
				.add("symbolCount", symbolCount);
	}
	
	public ST buildProcRunner(Collection<Integer> membraneTypes, Collection<String> membraneLabels) {
		return template.getInstanceOf("ProcRunner").add("membraneTypes", membraneTypes).add("membraneLabels",  membraneLabels);
	}
	
	public ST buildMembraneProcType(String label, String guardComputations, String rewritingAndCommRules, String divisionRules) {
		return template.getInstanceOf("MembraneProcType").add("label", label).add("guardComputations",  guardComputations)
				.add("rewritingAndCommRules", rewritingAndCommRules).add("divisionRules", divisionRules);
	}
	
	public ST buildInitialMultiSets(Collection<Integer> indices, Collection<Integer> membraneTypes, 
			Collection<Collection<String>> symbolsList, Collection<Collection<Long>> countsList, int instanceCount) {
		return template.getInstanceOf("InitialMultiSets").add("indices", indices).add("membraneTypes", membraneTypes).add("symbolsList", normalizeSymbolsList(symbolsList)).
				add("countsList", countsList).add("instanceCount", instanceCount);
	}
	
	public ST buildSymbolTempate(String symbol) {
		return template.getInstanceOf("Symbol").add("symbol", normalizeSymbol(symbol));
	}
	
	public ST buildOrJoinedTemplate(List<String> items) {
		return template.getInstanceOf("OrJoined").add("items", items);
	}
	
	public ST buildAndJoinedTemplate(List<String> items) {
		return template.getInstanceOf("AndJoined").add("items", items);
	}
	
	public ST buildRewAndCommRules(List<Integer> ruleIndices, List<String> ruleLhsList, List<String> ruleRhsList) {
		return template.getInstanceOf("RewritingAndCommRules").add("ruleIndices", ruleIndices).add("ruleLhsList", ruleLhsList)
				.add("ruleRhsList", ruleRhsList);
	}
	
	public ST builDivisionRules(List<Integer> ruleIndices, List<String> ruleLhsList, List<String> ruleRhsList) {
		return template.getInstanceOf("DivisionRules").add("ruleIndices", ruleIndices).add("ruleLhsList", ruleLhsList)
				.add("ruleRhsList", ruleRhsList);
	}
	
	public ST buildRuleLhs(Collection<String> symbols, Collection<Long> counts) {
		return template.getInstanceOf("RuleLhs").add("symbols", normalizeSymbols(symbols)).add("counts", counts);
	}
	
	public ST buildGuardComputations(List<Integer> ruleIndices, List<String> guardEvals, List<Boolean> guardIfs) {
		return template.getInstanceOf("GuardComputations").add("ruleIndices", ruleIndices).add("guardEvals", guardEvals)
				.add("guardIfs", guardIfs);
	}
	
	public ST buildRewAndCommRuleRhs(Collection<String> lhsSymbols, Collection<Long> lhsCounts, Collection<String> rhsRewSymbols, Collection<Long> rhsRewCounts,
			Collection<Integer> rhsCommMembraneTypes, Collection<String> rhsCommSymbols, Collection<Long> rhsCommCounts) {
		return template.getInstanceOf("RuleRewAndCommRhs").add("lhsSymbols", normalizeSymbols(lhsSymbols)).add("lhsCounts", lhsCounts)
				.add("rhsRewSymbols", normalizeSymbols(rhsRewSymbols)).add("rhsRewCounts", rhsRewCounts).add("rhsCommMembraneTypes", rhsCommMembraneTypes).
				add("rhsCommSymbols", normalizeSymbols(rhsCommSymbols)).add("rhsCommCounts", rhsCommCounts);
	}
	
	public ST buildDivisionRhs(Collection<String> lhsSymbols, Collection<Long> lhsCounts, Collection<String> divisions) {
		return template.getInstanceOf("RuleDivisionRhs").add("lhsSymbols", normalizeSymbols(lhsSymbols)).add("lhsCounts", lhsCounts).add("divisions", divisions);
	}
	
	public ST buildMembraneDivision(int childCount, Collection<String> rhsList) {
		return template.getInstanceOf("MembraneDivision").add("childCount", childCount).add("rhsList", rhsList);
	}
	
	public ST buildMembraneCreation(int targetType, int childCount, Collection<String> rhsList) {
		return template.getInstanceOf("MembraneCreation").add("targetType", targetType).add("childCount", childCount).add("rhsList", rhsList);
	}
	
	public ST buildRhsForDivision(int offset, Collection<String> symbols, Collection<Long> symbolCounts) {
		return template.getInstanceOf("RhsForDivision").add("offset", offset).add("symbols", normalizeSymbols(symbols)).add("counts", symbolCounts);
	}
	
	public ST buildDissolution() {
		return template.getInstanceOf("Dissolution");
	}
	
	public ST buildRuleRhs(Collection<String> symbols, Collection<Long> symbolCounts) {
		return template.getInstanceOf("RuleRhs").add("symbols", normalizeSymbols(symbols)).add("counts", symbolCounts);
	}
	
	public ST buildRelOpTemplate(String op, String left, String right) {
		ST relOpTemplate = null;
		if (op.equals(">"))
			relOpTemplate = template.getInstanceOf("Gt");
		else if (op.equals(">="))
			relOpTemplate = template.getInstanceOf("Ge");
		else if (op.equals("<"))
			relOpTemplate = template.getInstanceOf("Lt");
		else if (op.equals("<="))
			relOpTemplate = template.getInstanceOf("Le");
		else if (op.equals("=="))
			relOpTemplate = template.getInstanceOf("Eq");
		else
			relOpTemplate = template.getInstanceOf("Ne");
				
		return relOpTemplate.add("left", left).add("right", right);
	}
	
	private String normalizeSymbol(String symbol) {
		return symbol.replace('{', '_').replace(',', '_').replace("}", "");
	}
	
	private Collection<String> normalizeSymbols(Collection<String> symbols) {
		List<String> symbolList = new ArrayList<String>();
		for(String symbol : symbols) {
			symbolList.add(normalizeSymbol(symbol));
		}
		
		return symbolList;
	}
	
	private Collection<Collection<String>> normalizeSymbolsList(Collection<Collection<String>> symbolsList) {
		List<Collection<String>> symbolsArrayList = new ArrayList<Collection<String>>();
		for(Collection<String> symbols : symbolsList) {
			symbolsArrayList.add(normalizeSymbols(symbols));
		}
		
		return symbolsArrayList;
	}
}
