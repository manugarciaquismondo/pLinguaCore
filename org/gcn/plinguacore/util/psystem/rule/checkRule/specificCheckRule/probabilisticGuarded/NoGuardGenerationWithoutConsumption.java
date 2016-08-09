package org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.probabilisticGuarded;

import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.CheckRule;
import org.gcn.plinguacore.util.psystem.rule.guard.GuardTypes;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveGuard;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRule;

public class NoGuardGenerationWithoutConsumption extends
		ProbabilisticGuardedCheckRule {

	@Override
	protected boolean checkSpecificPart(IRule r) {
		// TODO Auto-generated method stub
		ProbabilisticGuardedRule rule =this.checkProbabilisticGuardedRule(r);
		if(((RestrictiveGuard)rule.getGuard()).getType()==GuardTypes.SIMPLE_RESTRICTIVE) return true;		
		String generatedFlag = rule.generatedFlag();
		if(generatedFlag==null)
			return true;
		if(rule.consumedFlag()==null){
			cause = "Rules which generate a guard should consume a guard. However, rule "+r+" generates guard "+generatedFlag+" but does not consume any guard";
			return false;
		}
		return true;
	}

	public NoGuardGenerationWithoutConsumption() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NoGuardGenerationWithoutConsumption(CheckRule cr) {
		super(cr);
		// TODO Auto-generated constructor stub
	}

}
