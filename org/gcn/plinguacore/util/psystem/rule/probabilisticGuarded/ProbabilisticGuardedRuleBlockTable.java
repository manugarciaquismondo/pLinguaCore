package org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.Pair;
import org.gcn.plinguacore.util.Triple;
import org.gcn.plinguacore.util.psystem.Label;
import org.gcn.plinguacore.util.psystem.rule.IKernelRule;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.RulesSet;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveGuard;

public class ProbabilisticGuardedRuleBlockTable {
	private Map<Triple<Label, Guard, MultiSet<String>>, Set<ProbabilisticGuardedRule>> blockTable;
	
	private Set<String> flags;
	private Label checkedLabel;
	private Guard checkedGuard;
	private Set<ProbabilisticGuardedRule> checkedRuleSet;
	private ProbabilisticRuleBlockTableChecker checker;
	boolean inconsistencyPreDetected;
	
	private String errorCause="";


	public ProbabilisticGuardedRuleBlockTable(Set<String> flags){
		this.flags = copySet(flags);
		blockTable = new HashMap<Triple<Label, Guard, MultiSet<String>>, Set<ProbabilisticGuardedRule>>();
	}
	
	
	protected Set<String> copySet(Set<String> flags) {
		Set<String> copiedFlags= new HashSet<String>();
		for(String object: flags){
			this.flags.add(object+"");
		}
		return copiedFlags;
	}
	
	public String getErrorCause(){
		return errorCause;
	}
	
	public void setFlags(Set<String> flags){
		this.flags = copySet(flags);
	}
	
	public boolean addRule(ProbabilisticGuardedRule rule){
		Triple<Label, Guard, MultiSet<String>> keyTriple = createKeyTriple(rule);
		Set<ProbabilisticGuardedRule> ruleSet;
		if(!blockTable.containsKey(keyTriple))
			ruleSet = new HashSet<ProbabilisticGuardedRule>();
		else
			ruleSet = blockTable.get(keyTriple);
		if(!ruleSet.add(rule)) return false;
		checker = new RuleConsistencyChecker(this);
		checkedLabel = keyTriple.getFirst();
		checkedGuard = keyTriple.getSecond();
		MultiSet<String> multiSet = keyTriple.getThird();
		blockTable.put(keyTriple, ruleSet);
		inconsistencyPreDetected = !checker.checkIntersectionCondition(multiSet, multiSet, checkedLabel, checkedGuard, ruleSet);
		if(inconsistencyPreDetected)
			this.errorCause = checker.getCause();
		return true;
	}



	


	protected Triple<Label, Guard, MultiSet<String>> createKeyTriple(
			IKernelRule rule) {
		Label label= rule.getLeftHandRule().getOuterRuleMembrane().getLabelObj();
		Guard guard = new RestrictiveGuard();
		((RestrictiveGuard)guard).setFlags(flags);
		if(rule.getGuard()!=null)
					guard =	rule.getGuard();
		MultiSet<String> multiSet=rule.getLeftHandRule().getOuterRuleMembrane().getMultiSet();
		Triple<Label, Guard, MultiSet<String>> keyTriple = new Triple<Label, Guard, MultiSet<String>>(label, guard, multiSet);
		return keyTriple;
	}
	
	public boolean checkBlockCompleteProbabilities(){
		for(Entry<Triple<Label, Guard, MultiSet<String>>, Set<ProbabilisticGuardedRule>> entry: blockTable.entrySet()){
			float totalProbabilitySum=0.0f;
			Set<ProbabilisticGuardedRule> ruleSet = entry.getValue();
			for(ProbabilisticGuardedRule rule: ruleSet)
				totalProbabilitySum+=rule.getConstant();
			if(outOfRange(totalProbabilitySum)){
				errorCause = "The sum of all rule probabilities of a Probabilistic Guarded P system must be equal to 1.\n However, block "+((Triple<Label, Guard, MultiSet<String>>)entry.getKey())+" does not comply with this restriction, as the sum is actually "+totalProbabilitySum+".\n Its rules are "+writeSet(ruleSet);
				return false;
			}
			
		}
		return true;
	}

	protected boolean generateConsistencyCheckMapAndCheckConsistency(ProbabilisticRuleBlockTableChecker modeChecker){
		Map<Pair<Label, Guard>, Set<MultiSet<String>>> consistencyCheckMap = new HashMap<Pair<Label, Guard>, Set<MultiSet<String>>>();
		fillConsistencyCheckMap(consistencyCheckMap);
		this.checker = modeChecker;
		return checkConsistency(consistencyCheckMap);
	}


	public boolean checkBlockForRuleOverlapping(){
		 return generateConsistencyCheckMapAndCheckConsistency(new OverlappingBetweenDifferentMultiSetsChecker(this));
	}
	
	public boolean checkBlockForRuleConsistency(){
		if(inconsistencyPreDetected) return false;
		return generateConsistencyCheckMapAndCheckConsistency(new RuleConsistencyChecker(this));	
	}
	
	


	protected void fillConsistencyCheckMap(
			Map<Pair<Label, Guard>, Set<MultiSet<String>>> consistencyCheckMap) {
		for(Triple<Label, Guard, MultiSet<String>> key: blockTable.keySet()){
			Pair<Label, Guard> consistencyCheckKey= new Pair<Label, Guard>(key.getFirst(), key.getSecond());
			Set<MultiSet<String>> consistencyCheckContent;
			if(consistencyCheckMap.containsKey(consistencyCheckKey))
				consistencyCheckContent = consistencyCheckMap.get(consistencyCheckKey);
			else
				consistencyCheckContent = new HashSet<MultiSet<String>>();
			consistencyCheckContent.add(key.getThird());
			consistencyCheckMap.put(consistencyCheckKey, consistencyCheckContent);
		}
	}
	
	protected boolean checkConsistency(Map<Pair<Label, Guard>, Set<MultiSet<String>>> consistencyCheckMap){
		for(Entry<Pair<Label,Guard>,Set<MultiSet<String>>> entry :consistencyCheckMap.entrySet()){
			checkedLabel = entry.getKey().getFirst();
			checkedGuard = entry.getKey().getSecond();
			
			Set<MultiSet<String>> setOfMultiSets = entry.getValue();
			for(MultiSet<String> multiSet: setOfMultiSets){
				checkedRuleSet = blockTable.get(new Triple<Label, Guard, MultiSet<String>>(checkedLabel, checkedGuard, multiSet));
				if(!checkForIntersectionsAmongMultiSetsFromDifferentBlocks(
						setOfMultiSets, multiSet)) return false;
			}
		}
		return true;
	}


	protected boolean checkForIntersectionsAmongMultiSetsFromDifferentBlocks(
			Set<MultiSet<String>> setOfMultiSets, MultiSet<String> multiSet) {
		MultiSet<String> multiSetForDifferenceCheck = (MultiSet<String>) multiSet.clone();
		Set<MultiSet<String>> allMultiSetsButChecked = copySetOfMultiSets(setOfMultiSets);
		allMultiSetsButChecked.remove(multiSetForDifferenceCheck);
		for(MultiSet<String> multiSetsUsedForDifference: allMultiSetsButChecked){
			
			if(!this.checker.checkIntersectionCondition(multiSetForDifferenceCheck, multiSetsUsedForDifference, checkedLabel, checkedGuard, checkedRuleSet)){
				errorCause = checker.getCause();
				return false;
			}
		}
		return true;
	}





	private Set<MultiSet<String>> copySetOfMultiSets(
			Set<MultiSet<String>> setOfMultiSets) {
		// TODO Auto-generated method stub
		Set<MultiSet<String>> copiedSetOfMultiSets= new HashSet<MultiSet<String>>();
		for(MultiSet<String> multiSet: setOfMultiSets)
			copiedSetOfMultiSets.add((MultiSet<String>)multiSet.clone());
		return copiedSetOfMultiSets;
	}


	protected boolean outOfRange(float totalProbabilitySum) {
		return totalProbabilitySum<0.95||totalProbabilitySum>1.05;
	}

	private String writeSet(Set<ProbabilisticGuardedRule> ruleSet) {
		// TODO Auto-generated method stub
		String result="";
		for(IRule rule: ruleSet)
			result+=rule+"\n";
		return result;
	}

	public int size(){
		return this.blockTable.keySet().size();
	}


	public Set<ProbabilisticGuardedRule> get(
			Triple<Label, Guard, MultiSet<String>> triple) {
		// TODO Auto-generated method stub
		return blockTable.get(triple);
	}


	public Set<Triple<Label, Guard, MultiSet<String>>> getBlocks() {
		// TODO Auto-generated method stub
		return blockTable.keySet();
	}
	


}
