package org.gcn.plinguacore.parser.output.probabilisticGuarded;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.HTMLReader.BlockAction;

import org.gcn.plinguacore.parser.output.simplekernel.KernelOutputParser;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.Pair;
import org.gcn.plinguacore.util.Triple;
import org.gcn.plinguacore.util.psystem.Label;
import org.gcn.plinguacore.util.psystem.probabilisticGuarded.ProbabilisticGuardedPsystem;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.RulesSet;
import org.gcn.plinguacore.util.psystem.rule.guard.GuardTypes;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveGuard;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRule;
import org.gcn.plinguacore.util.psystem.rule.simplekernel.DivisionKernelLikeRule;
import org.gcn.plinguacore.util.psystem.simplekernel.SimpleKernelLikePsystem;

public class BlockMapper {

	protected Map<Triple<Label, RestrictiveGuard, MultiSet<String>>, List<Integer>> ruleIDs;
	protected Map<Triple<Label, RestrictiveGuard, MultiSet<String>>, Pair<String, String>> blockGuardAssociations;
	protected Map<Triple<Label, RestrictiveGuard, MultiSet<String>>, Integer> blockIndexes;
	protected Map<ProbabilisticGuardedRule, Pair<Integer,Integer>> ruleBlockMapping;
	protected int blockIndex, ruleIndex;
	
	public BlockMapper() {
		ruleIDs= new HashMap<Triple<Label, RestrictiveGuard, MultiSet<String>>, List<Integer>>();
		ruleBlockMapping= new HashMap<ProbabilisticGuardedRule, Pair<Integer,Integer>>();
		blockIndexes= new HashMap<Triple<Label, RestrictiveGuard, MultiSet<String>>, Integer>();
		blockGuardAssociations = new HashMap<Triple<Label, RestrictiveGuard, MultiSet<String>>, Pair<String, String>>();
		blockIndex=0;
		ruleIndex=0;
		// TODO Auto-generated constructor stub
	}
	
	protected void fillInBlockMapping(DivisionKernelLikeRule rule) {
		
		

		Label label =rule.getLeftHandRule().getOuterRuleMembrane().getLabelObj();
		RestrictiveGuard guard = getGuard((RestrictiveGuard) rule.getGuard());
		MultiSet<String> multiSet =rule.getLeftHandRule().getOuterRuleMembrane().getMultiSet();
		Triple<Label, RestrictiveGuard, MultiSet<String>> keyTriple= 
				new Triple<Label, RestrictiveGuard, MultiSet<String>>(label, guard, multiSet);
		if(!blockGuardAssociations.containsKey(keyTriple)){
			ProbabilisticGuardedRule probabilisticRule= (ProbabilisticGuardedRule)rule;
			Pair<String, String> guardPair=new Pair<String, String>(getConsumedFlagFromRule(probabilisticRule), getGeneratedFlagFromRule(probabilisticRule));
			blockGuardAssociations.put(keyTriple, guardPair);
		}
		int localBlockIndex = getBlockIndex(keyTriple);
		ruleBlockMapping.put((ProbabilisticGuardedRule) rule, new Pair<Integer,Integer>(ruleIndex, localBlockIndex));
		registerRuleID(keyTriple);
		
		
	}
	
	protected int getBlockIndex(
			Triple<Label, RestrictiveGuard, MultiSet<String>> keyTriple) {
		int localBlockIndex;
		if(blockIndexes.containsKey(keyTriple)){
			localBlockIndex=blockIndexes.get(keyTriple);
		}
		else{
			localBlockIndex=blockIndex;
			blockIndexes.put(keyTriple, localBlockIndex);
			blockIndex++;
		}
		return localBlockIndex;
	}
	
	private RestrictiveGuard getGuard(RestrictiveGuard guard) {
		// TODO Auto-generated method stub
		if(guard.getType()==GuardTypes.UNARY_UNIT_RESTRICTIVE)
			return guard;
		else
			return new RestrictiveGuard();
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


	protected void registerRuleID(Triple<Label, RestrictiveGuard, MultiSet<String>> keyTriple) {
		List<Integer> idList = new LinkedList<Integer>();
		if(ruleIDs.containsKey(keyTriple))
			idList=ruleIDs.get(keyTriple);
		idList.add(ruleIndex);
		ruleIDs.put(keyTriple, idList);
	}
	
	public void fillInBlockMapping(RulesSet rules) {
		ruleIndex=0;
		blockIndex=0;
		for(IRule rule: rules){
			fillInBlockMapping((DivisionKernelLikeRule) rule);
			ruleIndex++;
		}
		ruleIndex=0;
		
	}
	
	public int getRuleID(DivisionKernelLikeRule rule){
		return ruleBlockMapping.get(rule).getFirst();
	}
	
	public int getRuleBlockID(DivisionKernelLikeRule rule){
		return ruleBlockMapping.get(rule).getSecond();
	}
	
	public int getBlockID(Triple<Label, RestrictiveGuard, MultiSet<String>> block){
		return blockIndexes.get(block);
	}
	
	public String getConsumedGuard(Triple<Label, RestrictiveGuard, MultiSet<String>> block){
		return blockGuardAssociations.get(block).getFirst();
	}
	
	public String getGeneratedGuard(Triple<Label, RestrictiveGuard, MultiSet<String>> block){
		return blockGuardAssociations.get(block).getSecond();
	}
	
	public List<Integer> getRuleIDs(Triple<Label, RestrictiveGuard, MultiSet<String>> block){
		return ruleIDs.get(block);
	}
	
	public int getNumberOfBlocks(){
		return ruleIDs.keySet().size();
	}
	
	public Set<Triple<Label, RestrictiveGuard, MultiSet<String>>> getBlocks(){
		return ruleIDs.keySet();
	}
	
	

}
