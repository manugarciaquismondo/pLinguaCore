package org.gcn.plinguacore.simulator.probabilisticGuarded.scripts;

import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.PlinguaCoreException;

public class ButterflyEmigrationPsystemScriptG3 extends
		ButterflyEmigrationPsystemScriptBase {

	public ButterflyEmigrationPsystemScriptG3() throws PlinguaCoreException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void updateMembraneValues(MultiSet<String> multiSet) {
		float mu=calculateMu(multiSet);
		calculatePyValues(multiSet, mu);
		float omega=1.0f-pyValues[0]-pyValues[1];
		removeEmigratingLarvae(multiSet, omega, mu);
				
	}

}
