package org.gcn.plinguacore.simulator.regenerative.scripts;

import org.gcn.plinguacore.simulator.scripts.PsystemScript;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Configuration;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;
import org.gcn.plinguacore.util.psystem.regenerative.membrane.RegenerativeMembraneStructure;
import org.gcn.plinguacore.util.psystem.tissueLike.TissueLikeConfiguration;

public class CheckAndCompareMembraneStructureScript implements PsystemScript {

	
	public CheckAndCompareMembraneStructureScript() {
		super();
		RegenerativePsystemScriptProvider.clearPreviousMembraneStructure();
		RegenerativePsystemScriptProvider.setIfIsEquivalentStructure(false);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Configuration getNextConfiguration(Configuration inputConfiguration,
			int membraneID) throws PlinguaCoreException {
		// TODO Auto-generated method stub
		checkIfisEquivalentToPreviousStructure((RegenerativeMembraneStructure)inputConfiguration.getMembraneStructure());
		return inputConfiguration;
		
	}

	private void checkIfisEquivalentToPreviousStructure(RegenerativeMembraneStructure membraneStructure) {
		// TODO Auto-generated method stub
		boolean isEquivalentToPreviousStructure=RegenerativePsystemScriptProvider.isEquivalentToPreviousMembraneStructure(membraneStructure);
		RegenerativePsystemScriptProvider.setIfIsEquivalentStructure(isEquivalentToPreviousStructure);
		RegenerativePsystemScriptProvider.updatePreviousMembraneStructure((RegenerativeMembraneStructure)membraneStructure.clone());
	}
	

}
