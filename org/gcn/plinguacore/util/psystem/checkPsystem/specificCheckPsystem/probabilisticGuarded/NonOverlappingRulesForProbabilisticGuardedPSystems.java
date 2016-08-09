package org.gcn.plinguacore.util.psystem.checkPsystem.specificCheckPsystem.probabilisticGuarded;

import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.checkPsystem.CheckPsystem;
import org.gcn.plinguacore.util.psystem.checkPsystem.DecoratorCheckPsystem;
import org.gcn.plinguacore.util.psystem.probabilisticGuarded.ProbabilisticGuardedPsystem;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRuleBlockTable;

public class NonOverlappingRulesForProbabilisticGuardedPSystems extends
		DecoratorCheckPsystem {

	protected ProbabilisticGuardedRuleBlockTable blockTable;
	
	public NonOverlappingRulesForProbabilisticGuardedPSystems() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NonOverlappingRulesForProbabilisticGuardedPSystems(CheckPsystem cr) {
		super(cr);
		// TODO Auto-generated constructor stub
	}


	
	@Override
	protected boolean checkSpecificPart(Psystem r) {
		if(!getBlockTable(r)) return false;
		return blockTable.checkBlockForRuleOverlapping();
	}

	protected boolean getBlockTable(Psystem r) {
		if(! (r instanceof ProbabilisticGuardedPsystem)) return false;
		this.blockTable = ((ProbabilisticGuardedPsystem)r).getBlockTable();
		return true;
	}

	@Override
	protected String getSpecificCause() {
		// TODO Auto-generated method stub
		return blockTable.getErrorCause();
	}

}
