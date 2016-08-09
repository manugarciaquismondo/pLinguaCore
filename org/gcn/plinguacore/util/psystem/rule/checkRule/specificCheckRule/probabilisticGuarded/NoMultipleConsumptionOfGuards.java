package org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.probabilisticGuarded;

import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.rule.checkRule.CheckRule;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRule;

public class NoMultipleConsumptionOfGuards extends NoMultipleGuards {



	public NoMultipleConsumptionOfGuards() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NoMultipleConsumptionOfGuards(CheckRule cr) {
		super(cr);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected MultiSet<String> getCheckedMultiSet(ProbabilisticGuardedRule rule) {
		// TODO Auto-generated method stub
		return rule.getLeftHandRule().getOuterRuleMembrane().getMultiSet();
	}

	@Override
	protected String operationName() {
		// TODO Auto-generated method stub
		return "consume";
	}

}
