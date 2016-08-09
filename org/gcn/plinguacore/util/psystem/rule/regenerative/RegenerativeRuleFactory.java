package org.gcn.plinguacore.util.psystem.rule.regenerative;

import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.rule.IKernelRule;
import org.gcn.plinguacore.util.psystem.rule.LeftHandRule;
import org.gcn.plinguacore.util.psystem.rule.RightHandRule;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.rule.simplekernel.DivisionKernelLikeRule;
import org.gcn.plinguacore.util.psystem.rule.simplekernel.EvolutionCommunicationKernelLikeRule;
import org.gcn.plinguacore.util.psystem.rule.simplekernel.InputOutputKernelLikeRule;
import org.gcn.plinguacore.util.psystem.rule.simplekernel.KernelRuleFactory;
import org.gcn.plinguacore.util.psystem.rule.simplekernel.KernelRuleTypes;

public class RegenerativeRuleFactory extends KernelRuleFactory {

	@Override
	public IKernelRule createKernelRule(boolean dissolves,
			LeftHandRule leftHandRule, RightHandRule rightHandRule, Guard guard, byte ruleType) {
		try{
			switch(ruleType){
				case(RegenerativeRuleTypes.DIVISION_BYTE):
				return createDivisionLikeRule(leftHandRule, rightHandRule,
						guard);
				case(RegenerativeRuleTypes.GEMMATION_BYTE):
				return createGemmationLikeRule(leftHandRule, rightHandRule,
						guard);
				case(RegenerativeRuleTypes.LINKING_BYTE):
				return createLinkingLikeRule(leftHandRule, rightHandRule, guard);
				case(RegenerativeRuleTypes.BUDDING_BYTE):
				return createBuddingLikeRule(leftHandRule, rightHandRule, guard);
				case(RegenerativeRuleTypes.UNLINKING_BYTE):
				return createUnlinkingLikeRule(leftHandRule, rightHandRule,
						guard);
				default:
				return createCommunicationLikeRule(leftHandRule, rightHandRule,
						guard);
			}
		}
		catch(PlinguaCoreException e){
			throw new UnsupportedOperationException(e.getMessage());
		}
			
	}

	protected IKernelRule createCommunicationLikeRule(
			LeftHandRule leftHandRule, RightHandRule rightHandRule, Guard guard)
			throws PlinguaCoreException {
		return new CommunicationRegenerativeLikeRule(leftHandRule, rightHandRule, guard);
	}

	protected IKernelRule createUnlinkingLikeRule(LeftHandRule leftHandRule,
			RightHandRule rightHandRule, Guard guard)
			throws PlinguaCoreException {
		return new UnlinkingRegenerativeLikeRule(leftHandRule, rightHandRule, guard);
	}

	protected IKernelRule createBuddingLikeRule(LeftHandRule leftHandRule,
			RightHandRule rightHandRule, Guard guard)
			throws PlinguaCoreException {
		return new BuddingRegenerativeLikeRule(leftHandRule, rightHandRule, guard);
	}

	protected IKernelRule createLinkingLikeRule(LeftHandRule leftHandRule,
			RightHandRule rightHandRule, Guard guard)
			throws PlinguaCoreException {
		return new LinkingRegenerativeLikeRule(leftHandRule, rightHandRule, guard);
	}

	protected IKernelRule createGemmationLikeRule(LeftHandRule leftHandRule,
			RightHandRule rightHandRule, Guard guard)
			throws PlinguaCoreException {
		return new GemmationRegenerativeLikeRule(leftHandRule, rightHandRule, guard);
	}

	protected IKernelRule createDivisionLikeRule(LeftHandRule leftHandRule,
			RightHandRule rightHandRule, Guard guard)
			throws PlinguaCoreException {
		return new DivisionRegenerativeLikeRule(leftHandRule, rightHandRule, guard);
	}
	
	

}
