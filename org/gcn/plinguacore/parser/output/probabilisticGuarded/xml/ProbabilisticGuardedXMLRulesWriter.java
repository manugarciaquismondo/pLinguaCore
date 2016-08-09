package org.gcn.plinguacore.parser.output.probabilisticGuarded.xml;

import org.gcn.plinguacore.parser.output.probabilisticGuarded.BlockMapper;
import org.gcn.plinguacore.parser.output.probabilisticGuarded.ProbabilisticGuardedAuxiliaryWriter;
import org.gcn.plinguacore.parser.output.simplekernel.KernelMapper;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.Triple;
import org.gcn.plinguacore.util.psystem.Label;
import org.gcn.plinguacore.util.psystem.probabilisticGuarded.ProbabilisticGuardedPsystem;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveGuard;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRule;
import org.jdom.Element;

public class ProbabilisticGuardedXMLRulesWriter {

	ProbabilisticGuardedPsystem psystem;
	KernelMapper mapper;
	BlockMapper blockMapper;
	ProbabilisticGuardedXMLObjectsWriter objectsWriter;
	
	public ProbabilisticGuardedXMLRulesWriter(
			ProbabilisticGuardedPsystem psystem, KernelMapper mapper) {
		this.psystem = psystem;
		this.mapper = mapper;
		blockMapper = new BlockMapper();
		objectsWriter = new ProbabilisticGuardedXMLObjectsWriter();
	}

	public void addRulesAndBlocksElements(Element e) {
		fillInBlockMappings();
		addRulesElements(e);
		addBlocksElements(e);
		
		
	}

	private void addBlocksElements(Element e) {
		Element blocksElement = new Element("Blocks");
		for(Triple<Label, RestrictiveGuard, MultiSet<String>> block : blockMapper.getBlocks()){
			blocksElement.addContent(getBlockElement(block, getBlockID(block)));
		}
		e.addContent(blocksElement);
		
	}
	
	private Element getBlockElement(
			Triple<Label, RestrictiveGuard, MultiSet<String>> block,
			Integer blockID) {
		Element blockElement = new Element("Block");
		setBlockHeaderElement(block, blockID, blockElement);
		addBlockObjects(block, blockElement);
		addRules(block, blockElement);
		return blockElement;
		
	}

	private void addRules(
			Triple<Label, RestrictiveGuard, MultiSet<String>> block,
			Element blockElement) {
		Element rulesElement = new Element("Rules");
		for(int ruleID:  blockMapper.getRuleIDs(block)){
			Element ruleElement = new Element("R");
			ruleElement.setAttribute("ID", ""+ruleID);
			rulesElement.addContent(ruleElement);
		}
		blockElement.addContent(rulesElement);
		
	}

	protected void addBlockObjects(
			Triple<Label, RestrictiveGuard, MultiSet<String>> block,
			Element blockElement) {
		Element objectsElement = new Element("Objects");
		addObjects(objectsElement, ProbabilisticGuardedAuxiliaryWriter.removeFlag(block.getThird(), psystem.getFlags()));
		blockElement.addContent(objectsElement);
	}

	protected void setBlockHeaderElement(
			Triple<Label, RestrictiveGuard, MultiSet<String>> block,
			Integer blockID, Element blockElement) {

		blockElement.setAttribute("ID", blockID+"");
		blockElement.setAttribute("L", ""+mapper.getMembraneID(block.getFirst().toString()));
		blockElement.setAttribute("F", getFlag(block.getSecond().getObj()));
		blockElement.setAttribute("C", getFlag(blockMapper.getConsumedGuard(block)));
		blockElement.setAttribute("G", getFlag(blockMapper.getGeneratedGuard(block)));
	}

	protected Integer getBlockID(
			Triple<Label, RestrictiveGuard, MultiSet<String>> block) {
		return blockMapper.getBlockID(block);
	}

	private void addRulesElements(Element e) {
		Element rulesElement = new Element("Rules");
		for(IRule rule: psystem.getRules()){
			rulesElement.addContent(getRuleElement((ProbabilisticGuardedRule) rule));
		}
		e.addContent(rulesElement);
		
	}

	private Element getRuleElement(ProbabilisticGuardedRule rule) {
		
		Element ruleElement=new Element("Rule");
		setAttributes(rule, ruleElement);
		setLeftHandSide(rule, ruleElement);
		setRightHandSide(rule, ruleElement);
		return ruleElement;
		
	}

	private void setRightHandSide(ProbabilisticGuardedRule rule,
			Element ruleElement) {
		Element leftHandSideElement = new Element("RHS");
		addObjects(leftHandSideElement, ProbabilisticGuardedAuxiliaryWriter.removeFlag(rule.getRightHandRule().getOuterRuleMembrane().getMultiSet(), psystem.getFlags()));
		ruleElement.addContent(leftHandSideElement);
		
	}

	private void setLeftHandSide(ProbabilisticGuardedRule rule,
			Element ruleElement) {
		Element leftHandSideElement = new Element("LHS");
		addObjects(leftHandSideElement, ProbabilisticGuardedAuxiliaryWriter.removeFlag(rule.getLeftHandRule().getOuterRuleMembrane().getMultiSet(), psystem.getFlags()));
		ruleElement.addContent(leftHandSideElement);
		// TODO Auto-generated method stub
		
	}

	private void addObjects(Element element,
			MultiSet<String> multiSet) {
		objectsWriter.writeObjects(multiSet, element, mapper);
		
	}

	protected void setAttributes(ProbabilisticGuardedRule rule,
			Element ruleElement) {
		ruleElement.setAttribute("ID", ""+blockMapper.getRuleID(rule));
		ruleElement.setAttribute("P", ""+rule.getConstant());
		ruleElement.setAttribute("S", ""+mapper.getMembraneID(mapper.getRuleLabel(rule)));
		ruleElement.setAttribute("D", ""+mapper.getMembraneID(getDestinationLabel(rule)));
		ruleElement.setAttribute("F", getFlag(((RestrictiveGuard)rule.getGuard()).getObj()));
		ruleElement.setAttribute("C", getFlag(rule.consumedFlag()));
		ruleElement.setAttribute("G", getFlag(rule.generatedFlag()));
		ruleElement.setAttribute("B", ""+blockMapper.getRuleBlockID(rule));
	}
	
	private String getDestinationLabel(ProbabilisticGuardedRule rule) {
		// TODO Auto-generated method stub
		return rule.getRightHandRule().getOuterRuleMembrane().getLabel();
	}

	String getFlag(String flag){
		if(flag==null||flag.equals("#"))
			return "#";
		else
			return ""+mapper.getObjectID(flag);
	}

	protected void fillInBlockMappings() {
		blockMapper.fillInBlockMapping(psystem.getRules());
	}

}
