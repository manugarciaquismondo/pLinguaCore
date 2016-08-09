package org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded;

import java.util.Set;

import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.Triple;
import org.gcn.plinguacore.util.psystem.Label;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.rule.guard.GuardTypes;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveGuard;

public class RuleConsistencyChecker extends
		ProbabilisticRuleBlockTableChecker {
	
	private String checkedFlag;
	private String contraryFlag;
	private ProbabilisticGuardedRule checkedRule;
	private ProbabilisticGuardedRule contraryRule;
	

	public RuleConsistencyChecker(ProbabilisticGuardedRuleBlockTable blockTable) {
		super(blockTable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean checkIntersectionCondition(
			MultiSet<String> multiSetForDifferenceCheck,
			MultiSet<String> multiSetsUsedForDifference, Label checkedLabel,
			Guard checkedGuard, Set<ProbabilisticGuardedRule> checkedRuleSet) {
		// TODO Auto-generated method stub
		
		MultiSet<String> clonedMultiSetForDifference =removeFlags(multiSetsUsedForDifference, checkedGuard);
		MultiSet<String> clonedMultiSetForDifferenceCheck =removeFlags(multiSetForDifferenceCheck, checkedGuard);
		clonedMultiSetForDifference.retainAll(clonedMultiSetForDifferenceCheck);
		if(!clonedMultiSetForDifference.isEmpty()){			
			Set<ProbabilisticGuardedRule> contraryRuleSet = blockTable.get(new Triple<Label, Guard, MultiSet<String>>(checkedLabel, checkedGuard, multiSetsUsedForDifference));
			if(generatesDifferentFlags(checkedRuleSet, contraryRuleSet)){
				errorCause = "Two different rules from different blocks which overlap must be consistent.\n That is, if one of them generates a flag, the other one must not generate a different flag.\n However, rule "+checkedRule+" generates flag "+checkedFlag+" whereas rule "+contraryRule+" generates flag "+contraryFlag+".\n These rules share label "+checkedLabel+" and guard "+checkedGuard+", and have overlapping multisets ["+multiSetForDifferenceCheck+"] and ["+multiSetsUsedForDifference+"].\n These multisets belong to rules in the sets "+checkedRuleSet+" and "+contraryRuleSet+", respectively";
				return false;
			}
		}
		return true;
	}

	

	private boolean generatesDifferentFlags(
			Set<ProbabilisticGuardedRule> checkedRuleSet,
			Set<ProbabilisticGuardedRule> contraryRuleSet) {
		// TODO Auto-generated method stub
		for(ProbabilisticGuardedRule rule: checkedRuleSet){
			checkedRule = rule;
			checkedFlag = checkedRule.generatedFlag();
			if(checkedFlag!=null){
				for(ProbabilisticGuardedRule contraryRule: contraryRuleSet){
					this.contraryRule = contraryRule;
					contraryFlag = this.contraryRule.generatedFlag();
					if(contraryFlag!=null)
						if(!checkedFlag.equals(contraryFlag))
							return true;
				}
					
			}
				
		}
		return false;
		
	}


}
