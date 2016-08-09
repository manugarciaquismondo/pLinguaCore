package org.gcn.plinguacore.parser.output.probabilisticGuarded;

import org.gcn.plinguacore.parser.output.simplekernel.KernelMembraneWriter;
import org.gcn.plinguacore.parser.output.simplekernel.KernelOutputParser;
import org.gcn.plinguacore.util.psystem.probabilisticGuarded.DummyMarkers;
import org.gcn.plinguacore.util.psystem.probabilisticGuarded.ProbabilisticGuardedPsystem;
import org.gcn.plinguacore.util.psystem.simplekernel.SimpleKernelLikePsystem;

public class ProbabilisticGuardedFlagWriter extends KernelMembraneWriter{

	private final String FLAGS = "flags";
	
	public ProbabilisticGuardedFlagWriter(SimpleKernelLikePsystem psystem,
			KernelOutputParser parser) {
		super(psystem, parser);
		// TODO Auto-generated constructor stub
	}

	public void writeFlags(StringBuffer psystemDescription) {
		ProbabilisticGuardedPsystem probabilisticGuardedPsystem = (ProbabilisticGuardedPsystem)psystem;
		this.psystemDescription = psystemDescription;
		this.psystemDescription.append(FLAGS+" : ");
		objectWriter.writeSet(probabilisticGuardedPsystem.getFlags(), this.psystemDescription);
		this.psystemDescription.append(" : ");
		writeMode(psystemDescription, probabilisticGuardedPsystem);
		numberWriter.writeNumber(((ProbabilisticGuardedPsystem)psystem).getDummyMode(), psystemDescription);
		this.psystemDescription.append(";\n");
		// TODO Auto-generated method stub
		
	}

	protected void writeMode(StringBuffer psystemDescription,
			ProbabilisticGuardedPsystem probabilisticGuardedPsystem) {
		if(probabilisticGuardedPsystem.getDummyMode()!=DummyMarkers.NO_DUMMY_MODE){
			objectWriter.writeObject(DummyMarkers.DUMMY_FLAG, psystemDescription);
			this.psystemDescription.append(" : ");
		}
		else
			numberWriter.writeNumber(-1, psystemDescription);
	}

}
