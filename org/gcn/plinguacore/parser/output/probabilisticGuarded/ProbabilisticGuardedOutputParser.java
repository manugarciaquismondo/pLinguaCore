package org.gcn.plinguacore.parser.output.probabilisticGuarded;

import org.gcn.plinguacore.parser.output.simplekernel.KernelHeaderWriter;
import org.gcn.plinguacore.parser.output.simplekernel.KernelMembraneWriter;
import org.gcn.plinguacore.parser.output.simplekernel.KernelOutputParser;
import org.gcn.plinguacore.parser.output.simplekernel.KernelRuleWriter;
import org.gcn.plinguacore.util.psystem.rule.IRule;

public class ProbabilisticGuardedOutputParser extends KernelOutputParser {

	@Override
	protected void initializePsystem() {
		// TODO Auto-generated method stub
		super.initializePsystem();
		((ProbabilisticGuardedHeaderWriter)headerWriter).setRuleWriter((ProbabilisticGuardedRuleWriter) ruleWriter);
		fillInBlockMapping();
		//fillInBlockMapping();
	}

	private void fillInBlockMapping() {
		((ProbabilisticGuardedRuleWriter)this.ruleWriter).fillInBlockMapping(psystem.getRules());
		
	}

	@Override
	protected KernelMembraneWriter createMembraneWriter() {
		// TODO Auto-generated method stub
		return new ProbabilisticGuardedMembraneWriter(this.psystem, this);
	}

	@Override
	protected KernelRuleWriter createKernelRuleWriter() {
		// TODO Auto-generated method stub
		return new ProbabilisticGuardedRuleWriter(this, this.psystem);
	}

	@Override
	protected void addFlags() {
		// TODO Auto-generated method stub
		flagWriter.writeFlags(psystemDescription);
	}

	@Override
	protected CharSequence transformToString() {
		// TODO Auto-generated method stub
		super.transformToString();
		addBlocks();
		return psystemDescription;
	}

	private void addBlocks() {
		((ProbabilisticGuardedRuleWriter)ruleWriter).writeBlocks();
		
	}

	@Override
	protected KernelHeaderWriter createHeaderWriter() {
		// TODO Auto-generated method stub
		return new ProbabilisticGuardedHeaderWriter(this.psystem);
	}

}
