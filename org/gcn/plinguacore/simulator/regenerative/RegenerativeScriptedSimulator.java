package org.gcn.plinguacore.simulator.regenerative;

import org.gcn.plinguacore.simulator.probabilisticGuarded.scripts.ProbabilisticGuardedPsystemScriptProvider;
import org.gcn.plinguacore.simulator.regenerative.scripts.RegenerativePsystemScriptProvider;
import org.gcn.plinguacore.simulator.scripts.PsystemScriptProvider;
import org.gcn.plinguacore.simulator.scripts.scripthandler.ScriptManager;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Configuration;
import org.gcn.plinguacore.util.psystem.Psystem;

public class RegenerativeScriptedSimulator extends RegenerativeSimulator {

	protected boolean localeHaltDetected;
	protected ScriptManager scriptManager;
	
	public RegenerativeScriptedSimulator(Psystem psystem)
			throws PlinguaCoreException {
		super(psystem);
		scriptManager=new ScriptManager(psystem, this) {
			
			@Override
			public PsystemScriptProvider getScriptProvider()
					throws PlinguaCoreException {
				// TODO Auto-generated method stub
				return RegenerativePsystemScriptProvider.getInstance(getInfoChannel());
			}
		};
		setHaltingConditionMode(true);
		RegenerativePsystemScriptProvider.clearPreviousMembraneStructure();
		haltDetected=false;
		localeHaltDetected=false;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void microStepSelectRules(Configuration cnf, Configuration tmpCnf) {
		// TODO Auto-generated method stub
		haltDetected=localeHaltDetected;
		if(!isFinished())
			super.microStepSelectRules(cnf, tmpCnf);
	}
	
	@Override
	public void microStepExecuteRules() {
		super.microStepExecuteRules();
		executeScripts();
	}

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return super.isFinished()||(isHaltingConditionMode()&&localeHaltDetected);
	}

	@Override
	public void reset(){
		super.reset();
		haltDetected=false;
		localeHaltDetected=false;
		RegenerativePsystemScriptProvider.clearPreviousMembraneStructure();
	}
	private void executeScripts() {
		scriptManager.executeScripts();
		haltDetected=RegenerativePsystemScriptProvider.isEquivalentStructure();
		localeHaltDetected=haltDetected;
		
	}

}
