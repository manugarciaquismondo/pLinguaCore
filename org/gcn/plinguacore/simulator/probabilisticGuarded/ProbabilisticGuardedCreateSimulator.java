package org.gcn.plinguacore.simulator.probabilisticGuarded;

import org.gcn.plinguacore.simulator.CreateSimulator;
import org.gcn.plinguacore.util.PlinguaCoreException;

public class ProbabilisticGuardedCreateSimulator extends CreateSimulator {

	public ProbabilisticGuardedCreateSimulator(String modelName) throws PlinguaCoreException {
		super(modelName);
	}

	@Override
	protected String getRoute() {
		// TODO Auto-generated method stub
		return "probabilistic_guarded_psystems";
	}

}
