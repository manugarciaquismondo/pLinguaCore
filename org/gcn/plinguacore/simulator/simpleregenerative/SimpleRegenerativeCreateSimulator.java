package org.gcn.plinguacore.simulator.simpleregenerative;

import org.gcn.plinguacore.simulator.regenerative.RegenerativeCreateSimulator;
import org.gcn.plinguacore.util.PlinguaCoreException;

public class SimpleRegenerativeCreateSimulator extends
		RegenerativeCreateSimulator {

	public SimpleRegenerativeCreateSimulator(String model)
			throws PlinguaCoreException {
		super(model);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getRoute() {
		// TODO Auto-generated method stub
		return "simple_regenerative_psystems";
	}
}
