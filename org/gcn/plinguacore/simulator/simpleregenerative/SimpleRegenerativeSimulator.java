package org.gcn.plinguacore.simulator.simpleregenerative;

import java.util.LinkedList;
import java.util.List;

import org.gcn.plinguacore.simulator.regenerative.RegenerativeSimulator;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.regenerative.membrane.RegenerativeMembrane;

public class SimpleRegenerativeSimulator extends RegenerativeSimulator {

	protected SimpleRegenerativeMembranePrinter membranePrinter;
	
	@Override
	protected String printLinkedMembranes(ChangeableMembrane membrane) {
		return membranePrinter.printMembranes((RegenerativeMembrane)membrane);

	}

	public SimpleRegenerativeSimulator(Psystem psystem)
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
