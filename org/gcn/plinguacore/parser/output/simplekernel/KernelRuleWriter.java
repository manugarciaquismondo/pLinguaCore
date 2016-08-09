package org.gcn.plinguacore.parser.output.simplekernel;

import org.gcn.plinguacore.parser.output.simplekernel.KernelMapper;
import org.gcn.plinguacore.parser.output.simplekernel.KernelNumberWriter;
import org.gcn.plinguacore.parser.output.simplekernel.KernelObjectWriter;
import org.gcn.plinguacore.parser.output.simplekernel.KernelOutputParser;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.simplekernel.DivisionKernelLikeRule;
import org.gcn.plinguacore.util.psystem.simplekernel.SimpleKernelLikePsystem;

public class KernelRuleWriter {
	protected SimpleKernelLikePsystem psystem;

	protected KernelMapper mapper;

	
	protected KernelObjectWriter objectWriter;
	protected KernelNumberWriter numberWriter;
	protected int ruleIndex;
	protected StringBuffer psystemDescription;

	
	public KernelRuleWriter(KernelOutputParser parser, SimpleKernelLikePsystem psystem) {
		super();
		this.numberWriter = new KernelNumberWriter();
		this.psystem = psystem;
		this.objectWriter = parser.objectWriter;
		this.mapper = parser.getMapper();
		numberWriter.setMapper(mapper);
	}





	public void addRules(StringBuffer psystemDescription) {
		this.psystemDescription = psystemDescription;
		ruleIndex=0;
		for (Membrane membrane : psystem.getMembraneStructure().getAllMembranes()) {
			writeMembraneRules(membrane);			
			psystemDescription.append("\n");	
		}
		psystemDescription.append(";\n");
		
	}

	private void writeMembraneRules(Membrane membrane) {
		
		for (IRule rule : mapper.getRuleSet(membrane.getLabel())) {			
			writeRule((DivisionKernelLikeRule) rule);
			ruleIndex++;
			
		}
		
	}

	protected void writeRule(DivisionKernelLikeRule rule) {
		writeRuleContent(rule);
		psystemDescription.append(";\n");
		// TODO Auto-generated method stub
		
	}





	protected void writeRuleContent(DivisionKernelLikeRule rule) {
		writeRuleHeader(rule);
		writeLeftHandSide(rule);
		writeDestinationLabel(rule);
		writeRightHandSide(rule);
	}
	
	protected String getRuleLabel(IRule rule) {
		return mapper.getRuleLabel(rule);
	}
	


	protected void writeRightHandSide(DivisionKernelLikeRule rule) {
		objectWriter.writeObjectSequence(rule.getRightHandRule().getOuterRuleMembrane().getMultiSet(), psystemDescription);
		
	}

	private void writeDestinationLabel(DivisionKernelLikeRule rule) {
		psystemDescription.append(":");
		numberWriter.writeNumber(mapper.getMembraneID(rule.getRightHandRule().getOuterRuleMembrane().getLabel()), psystemDescription);
		// TODO Auto-generated method stub
		
	}

	protected void writeLeftHandSide(DivisionKernelLikeRule rule) {
		objectWriter.writeObjectSequence(rule.getLeftHandRule().getOuterRuleMembrane().getMultiSet(), psystemDescription);
		
	}

	protected void writeRuleHeader(DivisionKernelLikeRule rule) {
		numberWriter.writeNumber(ruleIndex, psystemDescription);
		numberWriter.writeNumber(mapper.getMembraneID(getRuleLabel(rule)), psystemDescription);
		numberWriter.writeGuard(rule.getGuard(), psystemDescription);

		
	}


	

	




}
