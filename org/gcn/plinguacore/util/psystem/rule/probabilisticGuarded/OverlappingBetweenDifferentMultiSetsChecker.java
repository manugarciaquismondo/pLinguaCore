package org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded;

import java.util.Set;

import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.Triple;
import org.gcn.plinguacore.util.psystem.Label;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;

public class OverlappingBetweenDifferentMultiSetsChecker extends ProbabilisticRuleBlockTableChecker {
	


	public OverlappingBetweenDifferentMultiSetsChecker(ProbabilisticGuardedRuleBlockTable blockTable) {
		super(blockTable);
	}

	/* (non-Javadoc)
	 * @see org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticRuleBlockTableChecker#checkIntersectionCondition(org.gcn.plinguacore.util.MultiSet, org.gcn.plinguacore.util.MultiSet, org.gcn.plinguacore.util.psystem.Label, org.gcn.plinguacore.util.psystem.rule.guard.Guard, java.lang.String)
	 */
	@Override
	public boolean checkIntersectionCondition(MultiSet<String> multiSetForDifferenceCheck,
			MultiSet<String> multiSetsUsedForDifference, Label checkedLabel, Guard checkedGuard, Set<ProbabilisticGuardedRule> checkedRuleSet) {
		if(!multiSetsUsedForDifference.equals(multiSetForDifferenceCheck)){
			MultiSet<String> clonedMultiSetForDifference =removeFlags(multiSetsUsedForDifference, checkedGuard);
			MultiSet<String> clonedMultiSetForDifferenceCheck =removeFlags(multiSetForDifferenceCheck, checkedGuard);
			MultiSet<String> differenceCheck=(MultiSet<String>)clonedMultiSetForDifference.clone();
			differenceCheck.retainAll(clonedMultiSetForDifferenceCheck);
			if(!differenceCheck.isEmpty()){
				Set<ProbabilisticGuardedRule> contraryRuleSet = blockTable.get(new Triple<Label, Guard, MultiSet<String>>(checkedLabel, checkedGuard, clonedMultiSetForDifference));
				errorCause = "Two different rules from different blocks which overlap must not have the same guard and the same label.\n However, there is an overlapping on the left hand side multisets for label "+checkedLabel+" and guard "+checkedGuard+" for multisets ["+multiSetForDifferenceCheck+"] and ["+multiSetsUsedForDifference+"].\n These multisets belong to rules in the sets "+checkedRuleSet+" and "+contraryRuleSet;
				return false;
			}
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticRuleBlockTableChecker#getCause()
	 */
	@Override
	public String getCause(){
		return errorCause;
	}

}
