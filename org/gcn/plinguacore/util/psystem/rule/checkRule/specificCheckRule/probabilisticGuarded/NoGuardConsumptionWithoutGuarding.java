package org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.probabilisticGuarded;

import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.CheckRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.DecoratorCheckRule;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveGuard;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRule;

public class NoGuardConsumptionWithoutGuarding extends ProbabilisticGuardedCheckRule{

	@Override
	protected boolean checkSpecificPart(IRule r) {

		ProbabilisticGuardedRule rule =checkProbabilisticGuardedRule(r);
		if(rule==null) return false;
		String consumedFlag = rule.consumedFlag();
		if(consumedFlag==null) return true;
		String guardedFlag = ((RestrictiveGuard)rule.getGuard()).getObj();
		if((guardedFlag==null)||!guardedFlag.equals(consumedFlag)){
			cause= "Rules cannot consume flags without guarding them. However, rule "+rule.toString()+" consumes flag "+consumedFlag+" and "+getGuardMessage(guardedFlag);
			return false;
		}
		
		return true;
	}




	public NoGuardConsumptionWithoutGuarding() {
		super();
		// TODO Auto-generated constructor stub
	}




	public NoGuardConsumptionWithoutGuarding(CheckRule cr) {
		super(cr);
		// TODO Auto-generated constructor stub
	}




	private String getGuardMessage(String guardedFlag) {
		if(guardedFlag==null)
			return "does not guard any flag";
		return "guards flag "+guardedFlag;
	}


}
