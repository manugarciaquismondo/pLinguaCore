package org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.probabilisticGuarded;

import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.rule.checkRule.CheckRule;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRule;

public class NoMultipleGenerationOfGuards extends NoMultipleGuards {

	
	
	@Override
	protected MultiSet<String> getCheckedMultiSet(ProbabilisticGuardedRule rule) {
		return rule.getRightHandRule().getOuterRuleMembrane().getMultiSet();

	}

	public NoMultipleGenerationOfGuards() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NoMultipleGenerationOfGuards(CheckRule cr) {
		super(cr);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String operationName() {
		// TODO Auto-generated method stub
		return "generate";
	}


}
