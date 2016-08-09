package org.gcn.plinguacore.parser.output.probabilisticGuarded;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gcn.plinguacore.parser.output.simplekernel.KernelObjectWriter;
import org.gcn.plinguacore.parser.output.simplekernel.KernelOutputParser;
import org.gcn.plinguacore.parser.output.simplekernel.KernelRuleWriter;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.Pair;
import org.gcn.plinguacore.util.Triple;
import org.gcn.plinguacore.util.psystem.Label;
import org.gcn.plinguacore.util.psystem.probabilisticGuarded.ProbabilisticGuardedPsystem;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.RulesSet;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.rule.guard.GuardTypes;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveGuard;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRule;
import org.gcn.plinguacore.util.psystem.rule.simplekernel.DivisionKernelLikeRule;
import org.gcn.plinguacore.util.psystem.simplekernel.SimpleKernelLikePsystem;

public class ProbabilisticGuardedRuleWriter extends KernelRuleWriter {

	protected BlockMapper blockMapper;
	protected Set<String> flags;

	public ProbabilisticGuardedRuleWriter(KernelOutputParser parser,
			SimpleKernelLikePsystem psystem) {
		super(parser, psystem);
		blockMapper=new BlockMapper();
		flags=((ProbabilisticGuardedPsystem)psystem).getFlags();
		// TODO Auto-generated constructor stub
	}

	
	public void writeBlocks(){
		for(Triple<Label, RestrictiveGuard, MultiSet<String>> block : blockMapper.getBlocks()){
			writeBlock(block, getBlockID(block));
		}
		psystemDescription.append(";");
	}


	protected Integer getBlockID(
			Triple<Label, RestrictiveGuard, MultiSet<String>> block) {
		return blockMapper.getBlockID(block);
	}
	
	public int getNumberOfBlocks(){
		return blockMapper.getNumberOfBlocks();
	}
	
	public void writeBlock(Triple<Label, RestrictiveGuard, MultiSet<String>> block, int index){
		numberWriter.writeNumber(index, psystemDescription);
		numberWriter.writeNumber(mapper.getMembraneID(block.getFirst().toString()), psystemDescription);
		writeFlag(block.getSecond().getObj(), false, false);
		writeFlag(blockMapper.getConsumedGuard(block), true, false);
		writeFlag(blockMapper.getGeneratedGuard(block), true, false);
		objectWriter.writeObjectSequence(ProbabilisticGuardedAuxiliaryWriter.removeFlag(block.getThird(), flags), psystemDescription);
		writeRulesInBlock(block);
		psystemDescription.append(";\n");
	}


	private void writeRulesInBlock(
			Triple<Label, RestrictiveGuard, MultiSet<String>> block) {
		for(int ruleID:  blockMapper.getRuleIDs(block))
			psystemDescription.append(ruleID+" ");
		
	}


	@Override
	protected void writeRule(DivisionKernelLikeRule rule) {
		// TODO Auto-generated method stub
		ruleIndex=blockMapper.getRuleID(rule);
		writeRuleContent(rule);
		psystemDescription.append(blockMapper.getRuleBlockID(rule)+" : ");
		psystemDescription.append(";\n");
	}




	protected Pair<String, String> getGuardPairFromRule(ProbabilisticGuardedRule rule){
		String consumedFlag=getConsumedFlagFromRule(rule);
		String generatedFlag=getGeneratedFlagFromRule(rule);
		return new Pair<String, String>(consumedFlag, generatedFlag);
	}
	
	private String getConsumedFlagFromRule(ProbabilisticGuardedRule rule) {
		// TODO Auto-generated method stub
		return getFlagFromRule(rule.consumedFlag());

	}
	
	private String getFlagFromRule(String flag) {
		// TODO Auto-generated method stub
		if(flag==null)
			return "#";
		return flag;
	}


	private String getGeneratedFlagFromRule(ProbabilisticGuardedRule rule) {
		// TODO Auto-generated method stub
		return getFlagFromRule(rule.generatedFlag());
	}








	@Override
	protected void writeRuleHeader(DivisionKernelLikeRule rule) {
		numberWriter.writeNumber(blockMapper.getRuleID(rule), psystemDescription);
		numberWriter.writeNumber(((ProbabilisticGuardedRule) rule).getConstant(), psystemDescription);
		numberWriter.writeNumber(mapper.getMembraneID(getRuleLabel(rule)), psystemDescription);
		numberWriter.writeGuard(rule.getGuard(), psystemDescription);
	}
	
	protected void writeHandSide(MultiSet<String> multiSet, String flag){
		Set<String> flags = ((ProbabilisticGuardedPsystem)psystem).getFlags();
		writeFlag(flag, false, false);
		objectWriter.writeObjectSequence(
				ProbabilisticGuardedAuxiliaryWriter.removeFlag(multiSet, flags), psystemDescription);

	}

	@Override
	protected void writeRightHandSide(DivisionKernelLikeRule rule) {
		MultiSet<String> multiSet=rule.getRightHandRule().getOuterRuleMembrane().getMultiSet();
		writeHandSide(multiSet, ((ProbabilisticGuardedRule)rule).generatedFlag());

	}



	@Override
	protected void writeLeftHandSide(DivisionKernelLikeRule rule) {
		// TODO Auto-generated method stub
		MultiSet<String> multiSet=rule.getLeftHandRule().getOuterRuleMembrane().getMultiSet();
		writeHandSide(multiSet, ((ProbabilisticGuardedRule)rule).consumedFlag());
	}

	private void writeFlag(String consumedFlag, boolean prepend, boolean append) {
		ProbabilisticGuardedAuxiliaryWriter.writeFlag(consumedFlag, psystemDescription, objectWriter, prepend, append);
		
	}


	public void fillInBlockMapping(RulesSet rules) {
		blockMapper.fillInBlockMapping(rules);
		
	}



	

	
	

}
