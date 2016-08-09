package org.gcn.plinguacore.util.psystem.checkPsystem.specificCheckPsystem.probabilisticGuarded;

import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.checkPsystem.CheckPsystem;
import org.gcn.plinguacore.util.psystem.checkPsystem.DecoratorCheckPsystem;

public class ConsistentRulesForProbabilisticGuardedPSystems extends NonOverlappingRulesForProbabilisticGuardedPSystems{

	@Override
	protected boolean checkSpecificPart(Psystem r) {
		// TODO Auto-generated method stub
		if(!getBlockTable(r)) return false;
		return blockTable.checkBlockForRuleConsistency();
	}

	public ConsistentRulesForProbabilisticGuardedPSystems() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ConsistentRulesForProbabilisticGuardedPSystems(CheckPsystem cr) {
		super(cr);
		// TODO Auto-generated constructor stub
	}



}
