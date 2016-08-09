package org.gcn.plinguacore.parser.output.probabilisticGuarded;

import java.util.Set;

import org.gcn.plinguacore.parser.output.simplekernel.KernelMembraneWriter;
import org.gcn.plinguacore.parser.output.simplekernel.KernelOutputParser;
import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.probabilisticGuarded.ProbabilisticGuardedPsystem;
import org.gcn.plinguacore.util.psystem.simplekernel.SimpleKernelLikePsystem;

public class ProbabilisticGuardedMembraneWriter extends KernelMembraneWriter {

	private ProbabilisticGuardedPsystem probabilisticGuardedPsystem;

	public ProbabilisticGuardedMembraneWriter(SimpleKernelLikePsystem psystem,
			KernelOutputParser parser) {
		super(psystem, parser);
		probabilisticGuardedPsystem =(ProbabilisticGuardedPsystem)psystem;
		
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void addMembraneContent(Membrane membrane) {
		// TODO Auto-generated method stub
		writeMembraneHeader(membrane);
		MultiSet<String> multiSet = psystem.getInitialMultiSets().get(membrane.getLabel());
		objectWriter.writeObjectSequence(ProbabilisticGuardedAuxiliaryWriter.removeFlag(multiSet, probabilisticGuardedPsystem.getFlags()), psystemDescription);
		writeFlag(multiSet);
		psystemDescription.append(";\n");
	}

	private void writeFlag(MultiSet<String> multiSet) {
		// TODO Auto-generated method stub
		String flag = getFlag(multiSet);
		ProbabilisticGuardedAuxiliaryWriter.writeFlag(flag, psystemDescription, objectWriter, false, false);
	}

	public String getFlag(MultiSet<String> multiSet) {
		return ProbabilisticGuardedAuxiliaryWriter.getFlag(multiSet, probabilisticGuardedPsystem.getFlags());
	}



}
