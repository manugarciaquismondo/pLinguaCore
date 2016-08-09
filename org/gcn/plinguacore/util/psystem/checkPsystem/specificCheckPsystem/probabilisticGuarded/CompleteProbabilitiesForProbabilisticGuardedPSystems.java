package org.gcn.plinguacore.util.psystem.checkPsystem.specificCheckPsystem.probabilisticGuarded;

import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.checkPsystem.CheckPsystem;
import org.gcn.plinguacore.util.psystem.checkPsystem.DecoratorCheckPsystem;
import org.gcn.plinguacore.util.psystem.probabilisticGuarded.ProbabilisticGuardedPsystem;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRuleBlockTable;

public class CompleteProbabilitiesForProbabilisticGuardedPSystems extends
		NonOverlappingRulesForProbabilisticGuardedPSystems {

	public CompleteProbabilitiesForProbabilisticGuardedPSystems() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CompleteProbabilitiesForProbabilisticGuardedPSystems(CheckPsystem cr) {
		super(cr);
		// TODO Auto-generated constructor stub
	}


	
	@Override
	protected boolean checkSpecificPart(Psystem r) {
		// TODO Auto-generated method stub
		if(!this.getBlockTable(r)) return false;
		return blockTable.checkBlockCompleteProbabilities();
	}


	
	

}
