package org.gcn.plinguacore.simulator.probabilisticGuarded;

import org.gcn.plinguacore.simulator.CreateSimulator;
import org.gcn.plinguacore.util.PlinguaCoreException;

public class OverlappableProbabilisticGuardedCreateSimulator extends
		CreateSimulator {

	public OverlappableProbabilisticGuardedCreateSimulator(String model)
			throws PlinguaCoreException {
		super(model);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getRoute() {
		// TODO Auto-generated method stub
		return "overlappable_probabilistic_guarded_psystems";
	}

}
