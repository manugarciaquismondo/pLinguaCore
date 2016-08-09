package org.gcn.plinguacore.simulator.probabilisticGuarded;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.Triple;
import org.gcn.plinguacore.util.psystem.Label;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.OuterRuleMembrane;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRule;

public class ProbabilisticGuardedMultinomialSimulator extends
		ProbabilisticGuardedSimplifiedSimulator {
	
	protected Map<ProbabilisticGuardedRule, Float> ruleProbabilities;
	protected Map<Triple<Label, Guard, MultiSet<String>>, List<ProbabilisticGuardedRule>> remainingRules;

	public ProbabilisticGuardedMultinomialSimulator(Psystem psystem) {
		super(psystem);
		ruleProbabilities = new HashMap<ProbabilisticGuardedRule, Float>();
		remainingRules = new HashMap<Triple<Label, Guard, MultiSet<String>>, List<ProbabilisticGuardedRule>>();
		// TODO Auto-generated constructor stub
	}
	
	protected void clearSimulationStructures() {
		super.clearSimulationStructures();
		cleanMultinomialStructures();
		
	}

	protected void cleanMultinomialStructures() {
		for(IRule rule: this.getPsystem().getRules() ){
			ProbabilisticGuardedRule probabilisticRule=(ProbabilisticGuardedRule)rule;
			ruleProbabilities.put(probabilisticRule, probabilisticRule.getConstant());
			OuterRuleMembrane outerMembrane = probabilisticRule.getLeftHandRule().getOuterRuleMembrane();
			Triple<Label, Guard, MultiSet<String>> keyTriple=new Triple<Label, Guard, MultiSet<String>>(outerMembrane.getLabelObj(), probabilisticRule.getGuard(), outerMembrane.getMultiSet());
			List<ProbabilisticGuardedRule> auxList= new LinkedList<ProbabilisticGuardedRule>();
			if(remainingRules.containsKey(keyTriple)){
				auxList=remainingRules.get(keyTriple);
			}
			auxList.add(probabilisticRule);
			remainingRules.put(keyTriple, auxList);
		}
	}
	
	protected void selectRulesNonMaximally(
			Triple<Label, Guard, MultiSet<String>> labelGuardMultiSetTriple,
			List<ProbabilisticGuardedRule> rulesList,
			MultiSet<String> assignedMultiSet) {
		normalizeRuleProbabilities(labelGuardMultiSetTriple, rulesList);
		for(int i=0; i<MAX_ITERATIONS&&!assignedMultiSet.isEmpty()&&!remainingRules.get(labelGuardMultiSetTriple).isEmpty(); i++){			
			selectRules(assignedMultiSet, rulesList, labelGuardMultiSetTriple, false);
		}
	}

	private void normalizeRuleProbabilities(
			Triple<Label, Guard, MultiSet<String>> labelGuardMultiSetTriple,
			List<ProbabilisticGuardedRule> rulesList) {
		List<ProbabilisticGuardedRule> remainingRuleList = this.remainingRules.get(labelGuardMultiSetTriple);
		float ruleSum=0.0f;
		for(ProbabilisticGuardedRule rule:remainingRuleList){
			ruleSum+=rule.getConstant();
		}
		for(ProbabilisticGuardedRule rule:remainingRuleList){
			this.ruleProbabilities.put(rule, rule.getConstant()/ruleSum);
		}
		// TODO Auto-generated method stub
		
	}
	
	
	protected void selectRules(MultiSet<String> assignedMultiSet, List<ProbabilisticGuardedRule> rulesList,
			Triple<Label, Guard, MultiSet<String>> labelGuardMultiSetTriple,
			boolean maximality) {
		MultiSet<String> multiSet = labelGuardMultiSetTriple.getThird();
		long maxApplications = assignedMultiSet.countSubSets(multiSet);
		List<ProbabilisticGuardedRule> listToSelect=remainingRules.get(labelGuardMultiSetTriple);
		if(maximality){
			listToSelect=rulesList;
		}
		ProbabilisticGuardedRule rule = (ProbabilisticGuardedRule)getRandomElement(listToSelect);		
		float probability=ruleProbabilities.get(rule);
		if(maximality){
			probability=rule.getConstant();
		}
		long applications = calculateApplications(multiSet,
				maxApplications, maximality, probability);
		if(applications>0){
			registerRuleApplications(assignedMultiSet, labelGuardMultiSetTriple, rule, applications);
			removeRuleFromLists(rule, labelGuardMultiSetTriple);
		}

	}

	private void removeRuleFromLists(ProbabilisticGuardedRule rule, Triple<Label, Guard, MultiSet<String>> labelGuardMultiSetTriple) {
		ruleProbabilities.put(rule,0.0f);
		remainingRules.get(labelGuardMultiSetTriple).remove(rule);
	}
	
	


}
