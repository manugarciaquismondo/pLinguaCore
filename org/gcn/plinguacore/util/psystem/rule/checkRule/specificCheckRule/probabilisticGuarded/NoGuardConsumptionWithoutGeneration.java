package org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.probabilisticGuarded;

import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.CheckRule;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.rule.guard.GuardTypes;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRule;

public class NoGuardConsumptionWithoutGeneration extends OnlyRestrictiveGuard {

	protected String consumedFlag;
	protected String generatedFlag;
	
	public NoGuardConsumptionWithoutGeneration() {
		super();
		consumedFlag="";
		generatedFlag="";
		// TODO Auto-generated constructor stub
	}
	public NoGuardConsumptionWithoutGeneration(CheckRule cr) {
		super(cr);
		consumedFlag="";
		generatedFlag="";
		// TODO Auto-generated constructor stub
	}
	@Override
	protected boolean checkSpecificPart(IRule r) {
		// TODO Auto-generated method stub
		
		if(! (r instanceof ProbabilisticGuardedRule)) return false;
		ruleName=r.toString();
		ProbabilisticGuardedRule probabilisticGuardedRule = (ProbabilisticGuardedRule) r;
		consumedFlag = probabilisticGuardedRule.consumedFlag();
		if(consumedFlag==null) return true;
		generatedFlag= probabilisticGuardedRule.generatedFlag();
		return generatedFlag!=null;
		
	}
	@Override
	protected String getSpecificCause() {
		// TODO Auto-generated method stub
		return "Every rule which consumes a flag must generate another flag. However, rule "+ruleName+" consumes flag "+consumedFlag+" but does not generate any flag\n";
	}
	
	

}
