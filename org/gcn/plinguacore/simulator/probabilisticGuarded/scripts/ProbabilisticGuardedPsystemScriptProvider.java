package org.gcn.plinguacore.simulator.probabilisticGuarded.scripts;

import java.io.PrintStream;

import org.gcn.plinguacore.simulator.scripts.PsystemScriptProvider;
import org.gcn.plinguacore.util.PlinguaCoreException;

public class ProbabilisticGuardedPsystemScriptProvider extends PsystemScriptProvider {

	
	private static String scriptNames[]={
		"carryingCapacityButterfly1",
		"carryingCapacityButterfly2",
		"carryingCapacityLarvae1",
		"carryingCapacityLarvae2"
	};
	
	public ProbabilisticGuardedPsystemScriptProvider(PrintStream infoChannel) throws PlinguaCoreException {
		super(infoChannel);
	}

	@Override
	protected String[] getScriptNames() {
		// TODO Auto-generated method stub
		return scriptNames;
	}

	@Override
	protected void associateScriptsAndNames() throws PlinguaCoreException {
		scripts.put(reader.getObject(scriptNames[0]), new ButterflyEmigrationPsystemScript());
		scripts.put(reader.getObject(scriptNames[1]), new ButterflyEmigrationPsystemScriptG2());
		scripts.put(reader.getObject(scriptNames[2]), new ButterflyEmigrationPsystemScriptG3());
		scripts.put(reader.getObject(scriptNames[3]), new ButterflyEmigrationPsystemScriptG4());

	}
	
	public static PsystemScriptProvider getInstance(PrintStream infoChannel) throws PlinguaCoreException{
		if(provider==null||! (provider instanceof ProbabilisticGuardedPsystemScriptProvider))
			provider= new ProbabilisticGuardedPsystemScriptProvider(infoChannel);
		return provider;
	}

	@Override
	protected String getScriptsFileName() {
		// TODO Auto-generated method stub
		return "probabilistic_guarded.xml";
	}

}
