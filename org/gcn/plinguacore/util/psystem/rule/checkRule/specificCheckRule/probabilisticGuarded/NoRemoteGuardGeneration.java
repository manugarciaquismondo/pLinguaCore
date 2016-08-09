package org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.probabilisticGuarded;

import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.CheckRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.DecoratorCheckRule;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRule;

public class NoRemoteGuardGeneration extends ProbabilisticGuardedCheckRule {

	private String cause;
	
	@Override
	protected boolean checkSpecificPart(IRule r) {

		ProbabilisticGuardedRule rule = checkProbabilisticGuardedRule(r);
		if(rule==null) return false;
		String generatedFlag = rule.generatedFlag();
		if(generatedFlag==null) return true;
		if (equalLabels(rule)) return true;
		return false;
	}

	public NoRemoteGuardGeneration() {
		super();
		cause="";
		// TODO Auto-generated constructor stub
	}

	public NoRemoteGuardGeneration(CheckRule cr) {
		super(cr);
		// TODO Auto-generated constructor stub
	}

	private boolean equalLabels(ProbabilisticGuardedRule rule) {
		// TODO Auto-generated method stub
		String leftHandSideLabel = rule.getLeftHandRule().getOuterRuleMembrane().getLabel();
		String rightHandSideLabel = rule.getRightHandRule().getOuterRuleMembrane().getLabel();
		if (leftHandSideLabel.equals(rightHandSideLabel)) return true;
		cause= "Rules cannot generate flags in remote membranes. However, rule "+rule.toString()+" is applied to membrane "+leftHandSideLabel+", affects membrane "+rightHandSideLabel+" and generates flag "+rule.generatedFlag();
		return false;
	}


}
