package org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded;

import org.gcn.plinguacore.util.psystem.rule.HandRule;
import org.gcn.plinguacore.util.psystem.rule.IConstantRule;
import org.gcn.plinguacore.util.psystem.rule.LeftHandRule;
import org.gcn.plinguacore.util.psystem.rule.RightHandRule;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveGuard;
import org.gcn.plinguacore.util.psystem.rule.simplekernel.EvolutionCommunicationKernelLikeRule;

public class ProbabilisticGuardedRule extends
		EvolutionCommunicationKernelLikeRule implements IConstantRule{
	
	private float constant;

	protected ProbabilisticGuardedRule(boolean dissolves, LeftHandRule leftHandRule, RightHandRule rightHandRule,
			RestrictiveGuard guard, byte ruleType, float constant) {
		super(dissolves, leftHandRule, rightHandRule, guard, ruleType);
		checkAndSetProbability(constant);
		
		
		// TODO Auto-generated constructor stub
	}

	protected void checkAndSetProbability(float constant){
		
		this.constant=constant;
	}

	protected ProbabilisticGuardedRule(boolean dissolves, LeftHandRule leftHandRule, RightHandRule rightHandRule, float constant) {
		super(dissolves, leftHandRule, rightHandRule);
		this.guard = new RestrictiveGuard();
		checkAndSetProbability(constant);
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public float getConstant() {
		return constant;
	}

	/**
	 * @see org.gcn.plinguacore.util.psystem.rule.cellLike.CellLikeRule#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + " :: " + constant;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Float.floatToIntBits(constant);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) return false;
		ProbabilisticGuardedRule other = (ProbabilisticGuardedRule) obj;
		if (Float.floatToIntBits(constant) != Float
				.floatToIntBits(other.constant))
			return false;
		return true;
	}


	protected boolean isAFlag(String generatedObject) {
		return ((RestrictiveGuard)guard).isAFlag(generatedObject);
	}
	
	public String consumedFlag(){
		return affectedFlag(getLeftHandRule());
	}
	
	public String generatedFlag(){
		return affectedFlag(getRightHandRule());
	}
	
	public String affectedFlag(HandRule handRule) {
		// TODO Auto-generated method stub
		for (String consumedObject : handRule.getOuterRuleMembrane().getMultiSet()) {
			if(isAFlag(consumedObject))
				return consumedObject;
		}
		return null;
	}

}
