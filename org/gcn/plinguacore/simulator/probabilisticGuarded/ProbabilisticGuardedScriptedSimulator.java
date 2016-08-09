package org.gcn.plinguacore.simulator.probabilisticGuarded;

import org.gcn.plinguacore.simulator.probabilisticGuarded.scripts.ProbabilisticGuardedPsystemScriptProvider;
import org.gcn.plinguacore.simulator.scripts.PsystemScriptProvider;
import org.gcn.plinguacore.simulator.scripts.scripthandler.InfoPrinterSimulator;
import org.gcn.plinguacore.simulator.scripts.scripthandler.ScriptManager;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Psystem;

public abstract class ProbabilisticGuardedScriptedSimulator extends InfoPrinterSimulator {

	protected ScriptManager scriptManager;
	
	public ProbabilisticGuardedScriptedSimulator(Psystem psystem) {
		super(psystem);
		scriptManager=new ScriptManager(psystem, this) {
			
			@Override
			public PsystemScriptProvider getScriptProvider()
					throws PlinguaCoreException {
				// TODO Auto-generated method stub
				return ProbabilisticGuardedPsystemScriptProvider.getInstance(getInfoChannel());
			}
		};
		// TODO Auto-generated constructor stub
	}
	

}
