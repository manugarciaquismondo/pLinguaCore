package org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded;

import org.gcn.plinguacore.util.psystem.rule.AbstractRuleFactory;
import org.gcn.plinguacore.util.psystem.rule.IConstantRule;
import org.gcn.plinguacore.util.psystem.rule.IDoubleCommunicationRule;
import org.gcn.plinguacore.util.psystem.rule.IKernelRule;
import org.gcn.plinguacore.util.psystem.rule.IPriorityRule;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.IStochasticRule;
import org.gcn.plinguacore.util.psystem.rule.LeftHandRule;
import org.gcn.plinguacore.util.psystem.rule.RightHandRule;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveGuard;

public class ProbabilisticGuardedRuleFactory implements AbstractRuleFactory {

	@Override
	public IRule createBasicRule(boolean dissolves, LeftHandRule leftHandRule,
			RightHandRule rightHandRule) {
		// TODO Auto-generated method stub
		return new ProbabilisticGuardedRule(dissolves, leftHandRule, rightHandRule, 1.0f);
	}

	@Override
	public IPriorityRule createPriorityRule(boolean dissolves,
			LeftHandRule leftHandRule, RightHandRule rightHandRule, int priority) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Priority rules are not allowed");
	}

	@Override
	public IConstantRule createConstantRule(boolean dissolves,
			LeftHandRule leftHandRule, RightHandRule rightHandRule,
			float constant) {
		// TODO Auto-generated method stub
		return new ProbabilisticGuardedRule(dissolves, leftHandRule, rightHandRule, constant);
	}

	@Override
	public IStochasticRule createStochasticRule(boolean dissolves,
			LeftHandRule leftHandRule, RightHandRule rightHandRule,
			float constant) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Stochastic rules are not allowed");
	}

	@Override
	public IKernelRule createKernelRule(boolean dissolves,
			LeftHandRule leftHandRule, RightHandRule rightHandRule,
			Guard guard, byte ruleType) {
		// TODO Auto-generated method stub
		if(!(guard instanceof RestrictiveGuard))
			throw new UnsupportedOperationException("Guards for Probabilistic Guarded Models should be Restrictive");
		return new ProbabilisticGuardedRule(dissolves, leftHandRule, rightHandRule, (RestrictiveGuard)guard, ruleType, 1.0f);
	}

	@Override
	public ProbabilisticGuardedRule createProbabilisticGuardedRule(boolean dissolves,
			LeftHandRule leftHandRule, RightHandRule rightHandRule,
			RestrictiveGuard guard, byte ruleType, float probability) {
		// TODO Auto-generated method stub
		return new ProbabilisticGuardedRule(dissolves, leftHandRule, rightHandRule, guard, ruleType, probability);
	}

	@Override
	public IDoubleCommunicationRule createDoubleCommunicationRule(
			boolean dissolves, LeftHandRule leftHandRule,
			RightHandRule rightHandRule) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Double Communication rules are not allowed");
	}

}
