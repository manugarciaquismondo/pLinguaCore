package org.gcn.plinguacore.parser.output.probabilisticGuarded;

import org.gcn.plinguacore.parser.output.simplekernel.KernelHeaderWriter;
import org.gcn.plinguacore.parser.output.simplekernel.KernelRuleWriter;
import org.gcn.plinguacore.util.psystem.probabilisticGuarded.ProbabilisticGuardedPsystem;
import org.gcn.plinguacore.util.psystem.simplekernel.SimpleKernelLikePsystem;


public class ProbabilisticGuardedHeaderWriter extends KernelHeaderWriter {

	private final String FLAGS_SIZE = "flags_size";
	private final String NUMBER_OF_BLOCKS = "number_of_blocks";
	private ProbabilisticGuardedRuleWriter ruleWriter;
	
	public ProbabilisticGuardedHeaderWriter(SimpleKernelLikePsystem psystem) {
		super(psystem);
		// TODO Auto-generated constructor stub
	}

	public void setRuleWriter(ProbabilisticGuardedRuleWriter ruleWriter){
		this.ruleWriter=ruleWriter;
	}
	
	@Override
	public void addHeader(StringBuffer psystemDescription) {
		// TODO Auto-generated method stub
		super.addHeader(psystemDescription);
		writeField(psystemDescription, FLAGS_SIZE, getFlags());
		if(ruleWriter!=null)
		writeField(psystemDescription, NUMBER_OF_BLOCKS, ((ProbabilisticGuardedPsystem)psystem).getNumberOfBlocks());
	}

	private int getFlags() {
		// TODO Auto-generated method stub
		return ((ProbabilisticGuardedPsystem)psystem).getFlags().size();
	}
	
	
	
	
	

}
