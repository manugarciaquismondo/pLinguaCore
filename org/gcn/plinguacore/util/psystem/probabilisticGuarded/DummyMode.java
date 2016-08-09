package org.gcn.plinguacore.util.psystem.probabilisticGuarded;

import java.util.Set;

import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.rule.AbstractRuleFactory;
import org.gcn.plinguacore.util.psystem.rule.HandRule;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.LeftHandRule;
import org.gcn.plinguacore.util.psystem.rule.OuterRuleMembrane;
import org.gcn.plinguacore.util.psystem.rule.RightHandRule;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveGuard;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveUnaryUnitGuard;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRule;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRuleFactory;

public class DummyMode {
	
	Set<String> flags;
	protected RestrictiveGuard previousGuard;
	protected LeftHandRule leftHandRule;
	protected RightHandRule rightHandRule;
	protected ProbabilisticGuardedRule returnedRule;
	
	public IRule dummyRule(ProbabilisticGuardedRule r, Set<String> flags) {
		returnedRule = r;
		this.flags = flags;
		leftHandRule = returnedRule.getLeftHandRule();
		rightHandRule = returnedRule.getRightHandRule();
		AbstractRuleFactory ruleFactory = new ProbabilisticGuardedRuleFactory();
		previousGuard = (RestrictiveGuard) r.getGuard();
		RestrictiveUnaryUnitGuard dummyGuard = new RestrictiveUnaryUnitGuard(DummyMarkers.DUMMY_FLAG);
		if(previousGuard==null||previousGuard.getMultiSet().isEmpty()){
			
			setUpRuleForNoGuard(ruleFactory, dummyGuard);
		}
		else{
			setUpRuleForGuard(ruleFactory);
				
		}
			
		// TODO Auto-generated method stub
		
		return returnedRule;
	}

	protected void setUpRuleForGuard(AbstractRuleFactory ruleFactory) {
		
	}

	protected void setUpRuleForNoGuard(AbstractRuleFactory ruleFactory,
			RestrictiveUnaryUnitGuard dummyGuard) {
		returnedRule = ruleFactory.createProbabilisticGuardedRule(
				returnedRule.dissolves(), 
				leftHandRule, rightHandRule,
				dummyGuard, returnedRule.getRuleType(), returnedRule.getConstant());
	}
	
	protected boolean consumesFlag(IRule rule) {
		// TODO Auto-generated method stub
		return hasFlag(rule.getLeftHandRule().getOuterRuleMembrane().getMultiSet());
	}

	protected boolean generatesFlag(IRule rule){
		return hasFlag(rule.getRightHandRule().getOuterRuleMembrane().getMultiSet());
		
	}
	
	private boolean hasFlag(MultiSet<String> multiSet) {
		// TODO Auto-generated method stub
		for(String object: multiSet.entrySet())
			if(flags.contains(object)) return true;
		return false;
	}

	protected HandRule addDummyFlag(HandRule handRule, boolean left) {
		MultiSet<String> multiSet = new HashMultiSet<String>(handRule.getOuterRuleMembrane().getMultiSet());
		multiSet.add(DummyMarkers.DUMMY_FLAG);
		OuterRuleMembrane outerRuleMembrane = new OuterRuleMembrane
				(handRule.getOuterRuleMembrane().getLabelObj(), 
						handRule.getOuterRuleMembrane().getCharge(), 
						multiSet);
		if(left)
			return new LeftHandRule(outerRuleMembrane, handRule.getMultiSet());
		else
			return new RightHandRule(outerRuleMembrane, handRule.getMultiSet());

		
	}
	
	public byte getMode(){
		return DummyMarkers.WEAK_DUMMY_MODE;
	}

}
