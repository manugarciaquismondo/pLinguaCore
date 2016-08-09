package org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded;

import java.util.Set;

import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.Label;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.rule.guard.GuardTypes;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveGuard;

public abstract class ProbabilisticRuleBlockTableChecker {

	protected String errorCause;
	protected ProbabilisticGuardedRuleBlockTable blockTable;
	
	public ProbabilisticRuleBlockTableChecker(
			ProbabilisticGuardedRuleBlockTable blockTable) {
		super();
		this.blockTable = blockTable;
		errorCause="";
	}

	public abstract boolean checkIntersectionCondition(
			MultiSet<String> multiSetForDifferenceCheck,
			MultiSet<String> multiSetsUsedForDifference, Label checkedLabel,
			Guard checkedGuard, Set<ProbabilisticGuardedRule> checkedRuleSet);

	public String getCause(){
		return errorCause;
	}
	
	protected MultiSet<String> removeFlags(MultiSet<String> multiSet,
			Guard checkedGuard) {
		MultiSet<String> clonedMultiSetForDifference = new HashMultiSet<String>((MultiSet<String>)multiSet.clone());
		if(checkedGuard!=null&&checkedGuard.getType()==GuardTypes.UNARY_UNIT_RESTRICTIVE){
			String object= ((RestrictiveGuard)checkedGuard).getObj();
			if(clonedMultiSetForDifference.contains(object))
				clonedMultiSetForDifference.remove(object);
		}
		return clonedMultiSetForDifference;
			
		
	}

}