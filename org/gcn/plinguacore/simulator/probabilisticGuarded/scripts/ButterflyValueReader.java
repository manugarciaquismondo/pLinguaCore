package org.gcn.plinguacore.simulator.probabilisticGuarded.scripts;

import org.gcn.plinguacore.util.MultiSet;

public interface ButterflyValueReader {
	public void restartValues();
	public int getKLValue(MultiSet<String> multiSet);
	public int getKFValue(MultiSet<String> multiSet);
}
