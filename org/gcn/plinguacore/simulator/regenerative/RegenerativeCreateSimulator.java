package org.gcn.plinguacore.simulator.regenerative;

import org.gcn.plinguacore.simulator.CreateSimulator;
import org.gcn.plinguacore.util.PlinguaCoreException;

public class RegenerativeCreateSimulator extends CreateSimulator {

	public RegenerativeCreateSimulator(String model)
			throws PlinguaCoreException {
		super(model);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getRoute() {
		// TODO Auto-generated method stub
		return "regenerative_psystems";
	}

}
