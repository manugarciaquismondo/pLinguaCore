package org.gcn.plinguacore.simulator.probabilisticGuarded;

import java.util.List;

import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.Pair;
import org.gcn.plinguacore.util.psystem.Label;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;

public class ProbabilisticGuardedSimplifiedSimulator extends
		ProbabilisticGuardedSimulator {

	public ProbabilisticGuardedSimplifiedSimulator(Psystem psystem) {
		super(psystem);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void selectBlockCardinalities(Pair<Label, Guard> labelGuardPair) {
		Label label = labelGuardPair.getFirst();
		Guard guard = labelGuardPair.getSecond();		
		Membrane membrane = getMembraneByLabel(label, getCurrentConfig().getMembraneStructure());
		flagMapping.remove(label);		
		if(!compliesGuard(membrane.getMultiSet(), guard)) return;		
		dynamicTable.calculateMaximumApplications(membrane, labelGuardPair);
		selectBlocksMaximally(labelGuardPair, label, guard, membrane);
		// TODO Auto-generated method stub
		
	}

}
