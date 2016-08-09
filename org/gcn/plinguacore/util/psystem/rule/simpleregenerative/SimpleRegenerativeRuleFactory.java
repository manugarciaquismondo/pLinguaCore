package org.gcn.plinguacore.util.psystem.rule.simpleregenerative;

import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.rule.AbstractRuleFactory;
import org.gcn.plinguacore.util.psystem.rule.IKernelRule;
import org.gcn.plinguacore.util.psystem.rule.LeftHandRule;
import org.gcn.plinguacore.util.psystem.rule.RightHandRule;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.rule.regenerative.RegenerativeRuleFactory;

public class SimpleRegenerativeRuleFactory extends RegenerativeRuleFactory{

	@Override
	protected IKernelRule createDivisionLikeRule(LeftHandRule leftHandRule,
			RightHandRule rightHandRule, Guard guard)
			throws PlinguaCoreException {
		// TODO Auto-generated method stub
		return new SimpleDivisionRegenerativeLikeRule(leftHandRule, rightHandRule, guard);
	}

	@Override
	protected IKernelRule createUnlinkingLikeRule(LeftHandRule leftHandRule,
			RightHandRule rightHandRule, Guard guard)
			throws PlinguaCoreException {
		// TODO Auto-generated method stub
		return new SimpleUnlinkingRegenerativeLikeRule(leftHandRule, rightHandRule, guard);
	}

	@Override
	protected IKernelRule createCommunicationLikeRule(
			LeftHandRule leftHandRule, RightHandRule rightHandRule, Guard guard)
			throws PlinguaCoreException {
		// TODO Auto-generated method stub
		return new SimpleCommunicationRegenerativeLikeRule(leftHandRule, rightHandRule, guard);
	}

	@Override
	protected IKernelRule createLinkingLikeRule(LeftHandRule leftHandRule,
			RightHandRule rightHandRule, Guard guard)
			throws PlinguaCoreException {
		// TODO Auto-generated method stub
		return new SimpleLinkingRegenerativeLikeRule(leftHandRule, rightHandRule, guard);
	}

	@Override
	protected IKernelRule createGemmationLikeRule(LeftHandRule leftHandRule,
			RightHandRule rightHandRule, Guard guard)
			throws PlinguaCoreException {
		// TODO Auto-generated method stub
		return new SimpleGemmationRegenerativeLikeRule(leftHandRule, rightHandRule, guard);
	}
}
