package org.gcn.plinguacore.simulator.probabilisticGuarded;

import org.gcn.plinguacore.simulator.CreateSimulator;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.checkPsystem.BaseCheckPsystem;
import org.gcn.plinguacore.util.psystem.checkPsystem.specificCheckPsystem.probabilisticGuarded.AllInitialMultisetsContainOneFlag;
import org.gcn.plinguacore.util.psystem.checkPsystem.specificCheckPsystem.probabilisticGuarded.CompleteProbabilitiesForProbabilisticGuardedPSystems;
import org.gcn.plinguacore.util.psystem.checkPsystem.specificCheckPsystem.probabilisticGuarded.ConsistentRulesForProbabilisticGuardedPSystems;
import org.gcn.plinguacore.util.psystem.checkPsystem.specificCheckPsystem.probabilisticGuarded.GuardObjectsAreFlags;
import org.gcn.plinguacore.util.psystem.checkPsystem.specificCheckPsystem.probabilisticGuarded.NonOverlappingRulesForProbabilisticGuardedPSystems;

public class OverlappableProbabilisticGuardedPsystemFactory  extends
		ProbabilisticGuardedPsystemFactory {

	private static OverlappableProbabilisticGuardedPsystemFactory singleton;

	protected OverlappableProbabilisticGuardedPsystemFactory() {
		super();
		// TODO Auto-generated constructor stub
		checkPsystem = new CompleteProbabilitiesForProbabilisticGuardedPSystems(new ConsistentRulesForProbabilisticGuardedPSystems(
				new GuardObjectsAreFlags(
								new AllInitialMultisetsContainOneFlag(
				new BaseCheckPsystem()))));
	}
	
	@Override
	public CreateSimulator getCreateSimulator() throws PlinguaCoreException {
		// TODO Auto-generated method stub
		return new OverlappableProbabilisticGuardedCreateSimulator(getModelName());

	}
	
	public static OverlappableProbabilisticGuardedPsystemFactory getInstance() {
		if (singleton == null)
			singleton = new OverlappableProbabilisticGuardedPsystemFactory();
		return singleton;
	}

}
