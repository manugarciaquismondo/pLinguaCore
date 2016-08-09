package org.gcn.plinguacore.simulator.simpleregenerative;

import org.gcn.plinguacore.simulator.regenerative.RegenerativeScriptedSimulator;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.regenerative.membrane.RegenerativeMembrane;

public class SimpleRegenerativeScriptedSimulator extends
		RegenerativeScriptedSimulator {
	
	protected SimpleRegenerativeMembranePrinter membranePrinter;
	
	@Override
	protected String printLinkedMembranes(ChangeableMembrane membrane) {
		return membranePrinter.printMembranes((RegenerativeMembrane)membrane);

	}

	public SimpleRegenerativeScriptedSimulator(Psystem psystem)
			throws PlinguaCoreException {
		super(psystem);
		membranePrinter= new SimpleRegenerativeMembranePrinter();
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void checkInitialMultiSetsAndStructureForLinkingObjects(
			Psystem psystem) throws PlinguaCoreException {
	}

}
