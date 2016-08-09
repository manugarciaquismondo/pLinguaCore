/* 
 * pLinguaCore: A JAVA library for Membrane Computing
 *              http://www.p-lingua.org
 *
 * Copyright (C) 2009  Research Group on Natural Computing
 *                     http://www.gcn.us.es
 *                      
 * This file is part of pLinguaCore.
 *
 * pLinguaCore is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * pLinguaCore is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with pLinguaCore.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.gcn.plinguacore.parser.output.promela;


import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.*;

import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.AlphabetObject;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.rule.IKernelRule;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.OuterRuleMembrane;
import org.gcn.plinguacore.util.psystem.rule.RightHandRule;
import org.gcn.plinguacore.util.psystem.rule.guard.IGuardVisitor;
import org.gcn.plinguacore.util.psystem.rule.simplekernel.*;
import org.gcn.plinguacore.util.psystem.simplekernel.SimpleKernelLikePsystem;

import org.gcn.plinguacore.parser.output.promela.BasePromelaOutputParser;
import org.gcn.plinguacore.parser.output.promela.PromelaGuardVisitor;
import org.gcn.plinguacore.parser.output.promela.PromelaTemplateFactory;

/**
 * This class translates a P system into a promela code file
 * 
 *  @author Research Group on Natural Computing (http://www.gcn.us.es)
 * 
 */
public class PromelaOutputParser extends BasePromelaOutputParser {

	private SimpleKernelLikePsystem kPsystem;
	private Set<String> alphabetSymbols;
	private Map<String, Integer> activeMembraneTypesByLabel;
	private Map<String, Integer> membraneTypesByLabel;
	private Map<String, List<IKernelRule>> rulesByMembraneLabel;
	private PromelaTemplateFactory templateFactory;
	
	@Override
	public final boolean parse(Psystem psystem, OutputStream stream) {
		throw new UnsupportedOperationException();
	}

	@Override
	public final boolean parse(Psystem psystem, Writer stream) {
		if (!psystem.getAbstractPsystemFactory().getModelName().equals("simple_kernel_psystems"))
			return false;
		else {
			
			templateFactory = PromelaTemplateFactory.getInstance(getClass().getPackage().getName().replace('.', '/') + "/templates/sKPSystem.stg");
			kPsystem = (SimpleKernelLikePsystem) psystem;
			alphabetSymbols = new TreeSet<String>();
			activeMembraneTypesByLabel = new HashMap<String, Integer>();
			membraneTypesByLabel = new HashMap<String, Integer>();
			rulesByMembraneLabel = new HashMap<String, List<IKernelRule>>();
			
			// group rules by the corresponding membrane labels
			for(IRule rule : kPsystem.getRules()) {
				String membraneLabel = rule.getLeftHandRule().getOuterRuleMembrane().getLabel();
				boolean isAlreadyCreated = rulesByMembraneLabel.containsKey(membraneLabel);
				List<IKernelRule> ruleList = isAlreadyCreated ? rulesByMembraneLabel.get(membraneLabel) : new ArrayList<IKernelRule>();
				ruleList.add((IKernelRule) rule);
				if(!isAlreadyCreated) rulesByMembraneLabel.put(membraneLabel, ruleList);
			}
			
			// assign numeric types to membrane labels
			int membraneType = 1;
			for(String membraneLabel : rulesByMembraneLabel.keySet()) 
			{
				membraneTypesByLabel.put(membraneLabel, membraneType);
				activeMembraneTypesByLabel.put(membraneLabel, membraneType++);
			}
			
			// complete the process of numeric membrane type assignment for membranes having no rules
			for(Membrane m : kPsystem.getMembraneStructure().getAllMembranes()) 
			{
				if(!activeMembraneTypesByLabel.containsKey(m.getLabel()))
				{
					membraneTypesByLabel.put(m.getLabel(), membraneType++);
				}
			}
			
			// extract alphabet symbols
			kPsystem.getFirstConfiguration(); // <- needed only for correct alphabet preprocessing
			for(AlphabetObject obj : kPsystem.getAlphabet()) {
				alphabetSymbols.add(obj.toString().trim());
			}
			
			//((TissueLikeMembrane)kPsystem.getMembraneStructure().getMembrane(1)).getMultiSet().;
			//rulesByMembraneLabel.get("2").get(2).getRightHandRule().getAffectedMembranes()
			//kPsystem.getRules().iterator("21", 0).next()

			try {
				stream.write(buildMainStructure());
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
		return true;
	}
	
	protected String buildMainStructure() {
		return templateFactory.buildMainStructure(
				getSimulationStepCount(),
				alphabetSymbols,
				buildSymbolsMapping(),
				buildProcRunner(),
				buildMembraneProcTypes(),
				buildInitialMultiSets()).render();
	}
	
	protected String buildSymbolsMapping() {
		 return templateFactory.buildSymbolsMapping(alphabetSymbols, getRange(0, alphabetSymbols.size() - 1), alphabetSymbols.size()).render();
		
	}
	
	protected String buildProcRunner() {
		return templateFactory.buildProcRunner(activeMembraneTypesByLabel.values(), activeMembraneTypesByLabel.keySet()).render();
	}
	
	protected String buildInitialMultiSets() {
		Map<String, MultiSet<String>> intialMultiSets = kPsystem.getInitialMultiSets();
		List<Integer> membraneTypes = new ArrayList<Integer>();
		List<Collection<String>> symbolsList = new ArrayList<Collection<String>>();
		List<Collection<Long>> countsList = new ArrayList<Collection<Long>>();
		
		Collection<? extends Membrane> initialMembranes = kPsystem.getMembraneStructure().getAllMembranes();
		for(Membrane m : initialMembranes) {
			//build membrane type list
			membraneTypes.add(membraneTypesByLabel.get(m.getLabel()));
			
			MultiSet<String> multiSet = intialMultiSets.get(m.getLabel());
			if(multiSet != null && multiSet.size() > 0) {
				//build symbols list
				symbolsList.add(multiSet.entrySet());
				
				//build symbols multiplicities
				List<Long> counts = new ArrayList<Long>();
				for(String symbol : multiSet.entrySet()) {
					counts.add(multiSet.count(symbol));
				}
				countsList.add(counts);
			}
			else {
				symbolsList.add(new ArrayList<String>());
				countsList.add(new ArrayList<Long>());
			}
		}
			
		return templateFactory.buildInitialMultiSets(getRange(0, initialMembranes.size() - 1), membraneTypes, symbolsList, countsList, initialMembranes.size()).render();
	}
	
	protected List<String> buildMembraneProcTypes() {	
		List<String> membraneProcTypes = new ArrayList<String>();
		for(String membraneLabel : activeMembraneTypesByLabel.keySet()) {
			membraneProcTypes.add(templateFactory.buildMembraneProcType( 
					membraneLabel, 
					buildGuardComputations(membraneLabel), 
					buildRewritingAndCommRules(membraneLabel), 
					buildDivisionRules(membraneLabel)).render());
		}
		
		return membraneProcTypes;	
	}
	
	protected String buildGuardComputations(String membraneLabel) {	
		List<IKernelRule> rules = rulesByMembraneLabel.get(membraneLabel);

		return templateFactory.buildGuardComputations(getRange(1, rules.size()), buildGuardEvals(rules), buildGuardIfs(rules)).render();	
	}
	
	protected List<String> buildGuardEvals(List<IKernelRule> rules) {	
		List<String> guardEvals = new ArrayList<String>();
		IGuardVisitor guardVisitor = new PromelaGuardVisitor(templateFactory);
		for(IKernelRule r : rules) {
			guardEvals.add(r.getGuard() != null ? r.getGuard().accept(guardVisitor) : "true");
		}
		
		return guardEvals;	
	}
	
	protected List<Boolean> buildGuardIfs(List<IKernelRule> rules) {	
		List<Boolean> guardIfs = new ArrayList<Boolean>();
		for(IKernelRule r : rules) {
			guardIfs.add(r.getGuard() != null ? true : false);
		}
		
		return guardIfs;	
	}
	
	protected String buildRewritingAndCommRules(String membraneLabel) {	
		List<IKernelRule> rules = rulesByMembraneLabel.get(membraneLabel);
		List<Integer> indices = new ArrayList<Integer>();
		List<String> lefts = new ArrayList<String>();
		List<String> rights = new ArrayList<String>();
		
		for(int i = 0; i < rules.size(); i++) {
			IKernelRule rule = rules.get(i);
			if(rule.getRuleType() == KernelRuleTypes.EVOLUTION || rule.getRuleType() == KernelRuleTypes.INPUT_OUTPUT) {
				indices.add(i + 1);
				lefts.add(buildRuleLhs(rule));
				rights.add(buildRuleRhs((EvolutionCommunicationKernelLikeRule)rule));
			}
		}

		return indices.size() > 0 ? templateFactory.buildRewAndCommRules(indices, lefts, rights).render() : "";	
	}
	
	protected String buildDivisionRules(String membraneLabel) {	
		List<IKernelRule> rules = rulesByMembraneLabel.get(membraneLabel);
		List<Integer> indices = new ArrayList<Integer>();
		List<String> lefts = new ArrayList<String>();
		List<String> rights = new ArrayList<String>();
		
		for(int i = 0; i < rules.size(); i++) {
			IKernelRule rule = rules.get(i);
			if(rule.getRuleType() == KernelRuleTypes.DIVISION) {
				indices.add(i + 1);
				lefts.add(buildRuleLhs(rule));
				rights.add(buildRuleRhs((DivisionKernelLikeRule)rule));
			}
		}
		
		return indices.size() > 0 ? templateFactory.builDivisionRules(indices, lefts, rights).render() : "";	
	}
	
	protected String buildRuleLhs(IKernelRule rule) {
		// build left hand side symbols and multiplicity
		MultiSet<String> multiSet = rule.getLeftHandRule().getOuterRuleMembrane().getMultiSet();
		Set<String> symbols = multiSet.entrySet();
		List<Long> counts = new ArrayList<Long>();
		
		for(Object o : symbols) {
			counts.add(multiSet.count(o));
		}
	
		return templateFactory.buildRuleLhs(symbols, counts).render();
	}
	
	protected String buildRuleRhs(EvolutionCommunicationKernelLikeRule rule) {
		// build left hand side symbols and multiplicity
		String lhsLabel = rule.getLeftHandRule().getOuterRuleMembrane().getLabel();
		MultiSet<String> lhsMultiSet = rule.getLeftHandRule().getOuterRuleMembrane().getMultiSet();
		Set<String> lhsSymbols = lhsMultiSet.entrySet();
		List<Long> lhsCounts = new ArrayList<Long>();
		
		for(Object o : lhsSymbols) {
			lhsCounts.add(lhsMultiSet.count(o));
		}
		
		// build right hand side symbols, multiplicity and communication parameters
		List<String> rhsRewSymbols = new ArrayList<String>();
		List<Long> rhsRewCounts = new ArrayList<Long>();
		List<Integer> rhsCommMembraneTypes = new ArrayList<Integer>();
		List<String> rhsCommSymbols = new ArrayList<String>();
		List<Long> rhsCommCounts = new ArrayList<Long>();
		
		OuterRuleMembrane orm = rule.getRightHandRule().getOuterRuleMembrane();
		if(orm != null) {
			MultiSet<String> multiSet = rule.getRightHandRule().getOuterRuleMembrane().getMultiSet();
			if(multiSet != null)
				if(orm.getLabel().equals(lhsLabel)) {
					// build rewriting parameters
					rhsRewSymbols.addAll(multiSet.entrySet());
				
					for(Object o : multiSet.entrySet()) {
						rhsRewCounts.add(multiSet.count(o));
					}
				}
				else {
					// build communication parameters
					rhsCommMembraneTypes.add(membraneTypesByLabel.get(orm.getLabel()));
					rhsCommSymbols.addAll(multiSet.entrySet());
					
					for(Object o : multiSet.entrySet()) {
						rhsCommCounts.add(multiSet.count(o));
				}					
			}
		}
		
		List<OuterRuleMembrane> affectedOrms = rule.getRightHandRule().getAffectedMembranes();
		if(affectedOrms != null) {
			for(OuterRuleMembrane affectedOrm : affectedOrms) {
				MultiSet<String> multiSet = affectedOrm.getMultiSet();
				
				if(multiSet != null) {
					if(affectedOrm.getLabel().equals(lhsLabel)) {
						// build rewriting parameters
						rhsRewSymbols.addAll(multiSet.entrySet());
					
						for(Object o : multiSet.entrySet()) {
							rhsRewCounts.add(multiSet.count(o));
						}
					}
					else {
						// build communication parameters
						rhsCommMembraneTypes.add(membraneTypesByLabel.get(affectedOrm.getLabel()));
						rhsCommSymbols.addAll(multiSet.entrySet());
						
						for(Object o : multiSet.entrySet()) {
							rhsCommCounts.add(multiSet.count(o));
						}					
					}
				}
			}
		}
		
		return templateFactory.buildRewAndCommRuleRhs(lhsSymbols, lhsCounts, rhsRewSymbols, rhsRewCounts, rhsCommMembraneTypes, rhsCommSymbols, rhsCommCounts).render();
	}
	
	protected String buildRuleRhs(DivisionKernelLikeRule rule) {
		// build left hand side symbols and multiplicity
		String lhsLabel = rule.getLeftHandRule().getOuterRuleMembrane().getLabel();
		MultiSet<String> lhsMultiSet = rule.getLeftHandRule().getOuterRuleMembrane().getMultiSet();
		Set<String> lhsSymbols = lhsMultiSet.entrySet();
		List<Long> lhsCounts = new ArrayList<Long>();
		
		for(Object o : lhsSymbols) {
			lhsCounts.add(lhsMultiSet.count(o));
		}
		
		// build right hand side symbols, multiplicity and communication parameters
		List<String> divisions = new ArrayList<String>();
		Map<String, List<OuterRuleMembrane>> groupedOrms = groupOuterRuleMembranesByLabel(rule.getRightHandRule());
		boolean isDissolving = false;
		
		// build membrane divisions in case the rule multiplies the current compartment
		if(groupedOrms.containsKey(lhsLabel)) {
			divisions.add(buildMembraneDivision(groupedOrms.get(lhsLabel)));
		}
		else {
			isDissolving = true;
			divisions.add(templateFactory.buildDissolution().render());
		}
		
		// build membrane creations in case the rule creates another compartment types than the current one's
		for(String membraneLabel : groupedOrms.keySet()) {
			if(!membraneLabel.equals(lhsLabel)) {
				divisions.add(buildMembraneCreation(groupedOrms.get(membraneLabel), isDissolving));
			}
		}
		
		return templateFactory.buildDivisionRhs(lhsSymbols, lhsCounts, divisions).render();
	}
	
	private Map<String, List<OuterRuleMembrane>> groupOuterRuleMembranesByLabel(RightHandRule rightHandRule) {
		Map<String, List<OuterRuleMembrane>> groupedOrms = new HashMap<String, List<OuterRuleMembrane>>();
		List<OuterRuleMembrane> orms = new ArrayList<OuterRuleMembrane>();
		
		orms.add(rightHandRule.getOuterRuleMembrane());
		for(OuterRuleMembrane orm : rightHandRule.getAffectedMembranes()) {
			orms.add(orm);
		}
		
		for(OuterRuleMembrane orm : orms) {
			boolean exists = groupedOrms.containsKey(orm.getLabel());
			List<OuterRuleMembrane> currentOrms = exists ? groupedOrms.get(orm.getLabel()) : new ArrayList<OuterRuleMembrane>();
			currentOrms.add(orm);
			if(!exists) {
				groupedOrms.put(orm.getLabel(), currentOrms);
			}
		}
		
		return groupedOrms;
	}
	
	private String buildMembraneDivision(List<OuterRuleMembrane> orms) {
		List<String> rhsList = new ArrayList<String>();
		int offset= orms.size();
		
		for(OuterRuleMembrane orm : orms) {
			MultiSet<String> multiSet = orm.getMultiSet();
			if(multiSet != null) {
				Set<String> symbols = multiSet.entrySet();
				List<Long> symbolCounts = new ArrayList<Long>();
				
				for(Object o : multiSet.entrySet()) {
					symbolCounts.add(multiSet.count(o));
				}
				
				if(offset == orms.size()) {
					// in case of division, the initial membrane remains undissolved
					rhsList.add(templateFactory.buildRuleRhs(symbols, symbolCounts).render());
					offset--;
				}
				else {
					rhsList.add(templateFactory.buildRhsForDivision(offset--, symbols, symbolCounts).render());
				}
			}
		}
		
		return templateFactory.buildMembraneDivision( orms.size(), rhsList).render();
	}
	
	private String buildMembraneCreation(List<OuterRuleMembrane> orms, boolean isDissolving) {
		List<String> rhsList = new ArrayList<String>();
		int offset= orms.size();
		
		for(OuterRuleMembrane orm : orms) {
			MultiSet<String> multiSet = orm.getMultiSet();
			if(multiSet != null) {
				Set<String> symbols = multiSet.entrySet();
				List<Long> symbolCounts = new ArrayList<Long>();
				
				for(Object o : multiSet.entrySet()) {
					symbolCounts.add(multiSet.count(o));
				}
				
				rhsList.add(templateFactory.buildRhsForDivision(offset--, symbols, symbolCounts).render());
			}
		}
		
		return templateFactory.buildMembraneCreation(membraneTypesByLabel.get(orms.get(0).getLabel()), orms.size(), rhsList).render();
	}
	
	private List<Integer> getRange(int min, int max) {
		List<Integer> values = new ArrayList<Integer>();
		for(int i = min; i <= max; i++) {
			values.add(i);
		}
		
		return values;
	}
}
