package org.gcn.plinguacore.util.psystem.probabilisticGuarded;

import org.gcn.plinguacore.util.psystem.rule.AbstractRuleFactory;
import org.gcn.plinguacore.util.psystem.rule.LeftHandRule;
import org.gcn.plinguacore.util.psystem.rule.RightHandRule;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveUnaryUnitGuard;

public class StrongDummyMode extends DummyMode {
	
	@Override
	protected void setUpRuleForGuard(AbstractRuleFactory ruleFactory) {
		if(consumesFlag(returnedRule)&&!generatesFlag(returnedRule))
		rightHandRule = (RightHandRule) addDummyFlag(returnedRule.getRightHandRule(), false);
		returnedRule = ruleFactory.createProbabilisticGuardedRule(
				returnedRule.dissolves(), 
				leftHandRule, rightHandRule,
				previousGuard, returnedRule.getRuleType(), returnedRule.getConstant());
	}

	@Override
	protected void setUpRuleForNoGuard(AbstractRuleFactory ruleFactory,
			RestrictiveUnaryUnitGuard dummyGuard) {
		if(generatesFlag(returnedRule))
			leftHandRule = (LeftHandRule) addDummyFlag(returnedRule.getLeftHandRule(), true);
		returnedRule = ruleFactory.createProbabilisticGuardedRule(
				returnedRule.dissolves(), 
				leftHandRule, rightHandRule,
				dummyGuard, returnedRule.getRuleType(), returnedRule.getConstant());
	}
	
	@Override
	public byte getMode(){
		return DummyMarkers.STRONG_DUMMY_MODE;
	}

}
