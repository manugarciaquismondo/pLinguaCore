package org.gcn.plinguacore.simulator.probabilisticGuarded.scripts;

import org.gcn.plinguacore.util.MultiSet;

public class SimpleButterflyValueReader implements ButterflyValueReader {

	protected float yearWeights[][];
	
	@Override
	public int getKLValue(MultiSet<String> multiSet) {
		// TODO Auto-generated method stub
		return ButterflyEmigrationPsystemScriptBase.kl;
	}

	@Override
	public int getKFValue(MultiSet<String> multiSet) {
		// TODO Auto-generated method stub
		return ButterflyEmigrationPsystemScriptBase.kf;
	}

	@Override
	public void restartValues() {
		
		
	}




}
