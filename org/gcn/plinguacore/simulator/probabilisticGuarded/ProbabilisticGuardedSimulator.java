package org.gcn.plinguacore.simulator.probabilisticGuarded;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gcn.plinguacore.simulator.AbstractSelectionExecutionSimulator;
import org.gcn.plinguacore.simulator.simplekernel.SimpleKernelSimulator;
import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.Pair;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.RandomNumbersGenerator;
import org.gcn.plinguacore.util.Triple;
import org.gcn.plinguacore.util.psystem.Label;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;
import org.gcn.plinguacore.util.psystem.probabilisticGuarded.ProbabilisticGuardedPsystem;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.RulesSet;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.rule.guard.GuardTypes;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveGuard;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRule;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRuleBlockTable;
import org.gcn.plinguacore.util.psystem.rule.simplekernel.DivisionKernelLikeRule;
import org.gcn.plinguacore.util.psystem.simplekernel.membrane.SimpleKernelLikeMembraneStructure;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembrane;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembraneStructure;


public class ProbabilisticGuardedSimulator extends ProbabilisticGuardedScriptedSimulator {

	protected static final int MAX_ITERATIONS = 3;
	protected Map<Triple<Label, Guard, MultiSet<String>>, Long> maxBlockApplications;
	protected Map<Triple<Label, Guard, MultiSet<String>>, Long> blockApplications;
	protected Map<Triple<Label, Guard, MultiSet<String>>, Map<ProbabilisticGuardedRule, Long>> ruleApplications;
	protected Map<Pair<Label, Guard>, Set<MultiSet<String>>> chosenMultiSets;
	protected ProbabilisticGuardedRuleBlockTable blockTable;
	protected DynamicSelectionProbabilisticGuardedTable dynamicTable;
	protected Map<Label, String> flagMapping;
	protected Map<Label, String> flagConsumption;
	Set<Triple<Label, Guard, MultiSet<String>>> blocks;
	Map<Pair<Label, Guard>, Set<MultiSet<String>>> labelGuardPairs;
	Map<Label, MultiSet<String>> notApplicableBlocks;
	protected Set<String> flags;
	RandomNumbersGenerator generator;
	protected boolean abort;

	public ProbabilisticGuardedSimulator(Psystem psystem) {
		super(psystem);
		if(!(psystem instanceof ProbabilisticGuardedPsystem))
			throw new IllegalArgumentException("The input parameter for the constructor of a Probabilistic Guarded Simulator should be a Probabilistic Guarded P System");
		// TODO Auto-generated constructor stub
		maxBlockApplications = new HashMap<Triple<Label, Guard, MultiSet<String>>, Long>();
		blockApplications = new HashMap<Triple<Label, Guard, MultiSet<String>>, Long>();
		ruleApplications = new HashMap<Triple<Label, Guard, MultiSet<String>>, Map<ProbabilisticGuardedRule, Long>>();
		dynamicTable = new DynamicSelectionProbabilisticGuardedTable((ProbabilisticGuardedPsystem)psystem, this);
		blockTable = ((ProbabilisticGuardedPsystem)psystem).getBlockTable();
		blocks = blockTable.getBlocks();
		flagMapping = new HashMap<Label, String>();
		flagConsumption = new HashMap<Label, String>();
		labelGuardPairs= createLabelGuardPairs();		
		chosenMultiSets = new HashMap<Pair<Label, Guard>, Set<MultiSet<String>>>();
		flags = ((ProbabilisticGuardedPsystem)psystem).getFlags();
		generator = RandomNumbersGenerator.getInstance();
		notApplicableBlocks = new HashMap<Label, MultiSet<String>>();
		abort=false;
	}
	
	
	private Map<Pair<Label, Guard>, Set<MultiSet<String>>> createLabelGuardPairs() {
		// TODO Auto-generated method stub
		Map<Pair<Label, Guard>, Set<MultiSet<String>>> labelGuardPairs= new HashMap<Pair<Label, Guard>, Set<MultiSet<String>>>();
		for (Triple<Label, Guard, MultiSet<String>> block : blocks) {
			Pair<Label, Guard> keyLabelGuard= new Pair<Label, Guard>(block.getFirst(), block.getSecond());
			Set<MultiSet<String>> setOfMultiSets = new HashSet<MultiSet<String>>();
			if(labelGuardPairs.containsKey(keyLabelGuard))
				setOfMultiSets = labelGuardPairs.get(keyLabelGuard);
			setOfMultiSets.add(block.getThird());			
			labelGuardPairs.put(keyLabelGuard, setOfMultiSets);			
		}
		return labelGuardPairs;
	}





	protected void microStepSelectRules() {
		clearSimulationStructures();
		calculateMaximumApplicationsPerBlock();
		dynamicTable.normalizeTables(getCurrentConfig().getMembraneStructure());
		selectRules();
		
		// TODO Auto-generated method stub

	}


	protected void selectRules() {
		for (Pair<Label, Guard> labelGuardPair : labelGuardPairs.keySet()) {
			Label label = labelGuardPair.getFirst();
			Guard guard = labelGuardPair.getSecond();
			if(!compliesGuard(getMembraneByLabel(label, 
					getCurrentConfig().getMembraneStructure()).getMultiSet(), guard)) continue;
			selectBlockCardinalities(labelGuardPair);
			selectRuleCardinalities(labelGuardPair);
		}
	}



	private void selectRuleCardinalities(Pair<Label, Guard> labelGuardPair) {
		if(chosenMultiSets.containsKey(labelGuardPair))
		for(MultiSet<String> multiSet:
			chosenMultiSets.get(labelGuardPair)){
			selectRuleCardinalities(labelGuardPair.getFirst(), labelGuardPair.getSecond(), multiSet);
		}
		
	}


	protected void selectRuleCardinalities(Label label, Guard guard,
			MultiSet<String> multiSet) {
		Triple<Label, Guard, MultiSet<String>> labelGuardMultiSetTriple = 
				new Triple<Label, Guard, MultiSet<String>>
		(label, guard, multiSet);
		List<ProbabilisticGuardedRule> rulesList = new LinkedList<ProbabilisticGuardedRule>
		(blockTable.get(labelGuardMultiSetTriple));
		MultiSet<String> assignedMultiSet = multiplyMultiSet(multiSet,
				labelGuardMultiSetTriple);
		selectRulesNonMaximally(labelGuardMultiSetTriple, rulesList,
				assignedMultiSet);
		selectRulesMaximally(labelGuardMultiSetTriple, assignedMultiSet);
		
	}


	protected void selectRulesMaximally(
			Triple<Label, Guard, MultiSet<String>> labelGuardMultiSetTriple,
			MultiSet<String> assignedMultiSet) {
		if(!assignedMultiSet.isEmpty()){
			selectRules(assignedMultiSet, new LinkedList<ProbabilisticGuardedRule>
			(blockTable.get(labelGuardMultiSetTriple)), labelGuardMultiSetTriple, true);
		}
	}


	protected void selectRulesNonMaximally(
			Triple<Label, Guard, MultiSet<String>> labelGuardMultiSetTriple,
			List<ProbabilisticGuardedRule> rulesList,
			MultiSet<String> assignedMultiSet) {
		for(int i=0; i<MAX_ITERATIONS&&!assignedMultiSet.isEmpty(); i++){			
			selectRules(assignedMultiSet, rulesList, labelGuardMultiSetTriple, false);
		}
	}


	protected MultiSet<String> multiplyMultiSet(MultiSet<String> multiSet,
			Triple<Label, Guard, MultiSet<String>> labelGuardMultiSetTriple) {
		MultiSet<String> assignedMultiSet = new HashMultiSet<String>();
		assignedMultiSet.addAll(multiSet, this.blockApplications.get(labelGuardMultiSetTriple));
		return assignedMultiSet;
	}


	protected void selectRules(MultiSet<String> assignedMultiSet, List<ProbabilisticGuardedRule> rulesList,
			Triple<Label, Guard, MultiSet<String>> labelGuardMultiSetTriple,
			boolean maximality) {
		MultiSet<String> multiSet = labelGuardMultiSetTriple.getThird();
		ProbabilisticGuardedRule rule = (ProbabilisticGuardedRule)getRandomElement(rulesList);
		long maxApplications = assignedMultiSet.countSubSets(multiSet);
		long applications = calculateApplications(multiSet,
				maxApplications, maximality, rule.getConstant());
		if(applications>0)
			registerRuleApplications(assignedMultiSet, labelGuardMultiSetTriple, rule, applications);

	}

	protected void registerRuleApplications(
			MultiSet<String> assignedMultiSet, Triple<Label, Guard, MultiSet<String>> labelGuardBlockKey,
			ProbabilisticGuardedRule rule, long applications) {
		Map<ProbabilisticGuardedRule, Long> applicationMap = new HashMap<ProbabilisticGuardedRule, Long>();
		if(ruleApplications.containsKey(labelGuardBlockKey))
			applicationMap = ruleApplications.get(labelGuardBlockKey);
		putRuleApplications(rule, applications, applicationMap);
		ruleApplications.put(labelGuardBlockKey, applicationMap);
		assignedMultiSet.subtraction(labelGuardBlockKey.getThird(), applications);
		selectRule(rule, (ChangeableMembrane)getMembraneByLabel(labelGuardBlockKey.getFirst(), getCurrentConfig().getMembraneStructure()), applications);
		// TODO Auto-generated method stub
		
	}



	protected void putRuleApplications(ProbabilisticGuardedRule rule,
			long applications,
			Map<ProbabilisticGuardedRule, Long> applicationMap) {
		long totalApplications = applications;
		if(applicationMap.containsKey(rule))
			totalApplications+=applicationMap.get(rule);
		applicationMap.put(rule, totalApplications);
	}

	protected long calculateApplications(
			MultiSet<String> multiSet, long maxApplications, boolean maximality, float probability) {
		// TODO Auto-generated method stub
		if(maxApplications<=0||probability<=0.0f)
			return 0;
		if(maximality||isSimilarTo1(probability))
			return maxApplications;
		else{
			long applications = 1+generator.nextLongBi(maxApplications-1, probability);
			return applications;
		}
	}


	private boolean isSimilarTo1(float probability) {
		// TODO Auto-generated method stub
		return probability>=0.999;
	}


	protected Membrane getMembraneByLabel(Label label,
			MembraneStructure structure) {
		for (Membrane membrane : structure.getAllMembranes()) {
			if(membrane.getLabelObj().equals(label))
				return membrane;
			
		}
		return null;
		
	}


	protected void selectBlockCardinalities(Pair<Label, Guard> labelGuardPair) {
		Label label = labelGuardPair.getFirst();
		Guard guard = labelGuardPair.getSecond();		
		Membrane membrane = getMembraneByLabel(label, getCurrentConfig().getMembraneStructure());
		flagMapping.remove(label);		
		if(!compliesGuard(membrane.getMultiSet(), guard)) return;		
		List<MultiSet<String>> nonChosenMultiSets=getNonChosenMultiSets(labelGuardPair, membrane);
		dynamicTable.normalizeTable(membrane, labelGuardPair);
		selectBlocksNonMaximally(labelGuardPair, label, guard, membrane,
				nonChosenMultiSets);
		selectBlocksMaximally(labelGuardPair, label, guard, membrane);
		// TODO Auto-generated method stub
		
	}


	protected void selectBlocksMaximally(Pair<Label, Guard> labelGuardPair,
			Label label, Guard guard, Membrane membrane) {
		List<MultiSet<String>> multiSetsForMaximality=getNonChosenMultiSets(labelGuardPair, membrane);
		while(!multiSetsForMaximality.isEmpty()&&consumableObjectsLeft(labelGuardPair, this.getMembraneByLabel(label, getCurrentConfig().getMembraneStructure()).getMultiSet())){
			selectBlock(multiSetsForMaximality, dynamicTable.getNormalizedValues(label, 
					guard), labelGuardPair, true);
		}
	}


	protected void selectBlocksNonMaximally(Pair<Label, Guard> labelGuardPair,
			Label label, Guard guard, Membrane membrane,
			List<MultiSet<String>> nonChosenMultiSets) {
		for(int i=0; i<MAX_ITERATIONS&&!nonChosenMultiSets.isEmpty(); i++){
			selectBlock(nonChosenMultiSets, dynamicTable.getNormalizedValues(label, 
					guard), labelGuardPair, false);
			calculateMaximumApplicationsPerBlock();
			dynamicTable.normalizeTable(membrane, labelGuardPair);
		}
	}




	private boolean consumableObjectsLeft(Pair<Label, Guard> labelGuardPair, MultiSet<String> multiSetToCheck) {
		// TODO Auto-generated method stub
		if(multiSetToCheck.isEmpty()) return false;
		for(MultiSet<String> multiSetToRemove :labelGuardPairs.get(labelGuardPair)){
			if(multiSetToCheck.countSubSets(multiSetToRemove)>0)
				return true;
		}
		return false;
	}


	protected List<MultiSet<String>> getNonChosenMultiSets(Pair<Label, Guard> labelGuardPair, Membrane membrane) {
		List<MultiSet<String>> nonChosenMultiSetsList = new LinkedList<MultiSet<String>>();
		for (MultiSet<String> multiSet : labelGuardPairs.get(labelGuardPair)) {
			Triple<Label, Guard, MultiSet<String>> labelGuardPairKey = 
					new Triple<Label, Guard, MultiSet<String>>
			(labelGuardPair.getFirst(),
					labelGuardPair.getSecond(), multiSet);
		
			if(isValidBlock(labelGuardPairKey, membrane.getMultiSet())){

				nonChosenMultiSetsList.add(multiSet);
			}
			
		}
		return nonChosenMultiSetsList;
		
	}


	protected boolean isValidBlock(
			Triple<Label, Guard, MultiSet<String>> labelGuardPairKey, MultiSet<String> multiSet) {
		return consumesValidGuard(multiSet, labelGuardPairKey)&&compliesGuard(multiSet, labelGuardPairKey.getSecond())&&generatesValidGuard(labelGuardPairKey)&&this.maxBlockApplications.get(labelGuardPairKey)>0;
	}


	private boolean consumesValidGuard(MultiSet<String> multiSet,
			Triple<Label, Guard, MultiSet<String>> labelGuardPairKey) {
		// TODO Auto-generated method stub
		Label label=labelGuardPairKey.getFirst();
		Guard guard=labelGuardPairKey.getSecond();
		if(guard.getType()==GuardTypes.SIMPLE_RESTRICTIVE) return true;
		String flag = ((RestrictiveGuard)guard).getObj();
		if(!multiSet.contains(flag)) return true;
		return !notApplicableBlocks.containsKey(label);
	}


	private boolean registerFlagMapping(Label checkedLabel, String generatedFlag){
		if(generatedFlag==null) return true;
		if(!this.flagMapping.containsKey(checkedLabel)){
			flagMapping.put(checkedLabel, generatedFlag);
			return true;
		}
		else
			return false;
	}


	private boolean generatesValidGuard(
			Triple<Label, Guard, MultiSet<String>> labelGuardPairKey) {
		if(blockTable.get(labelGuardPairKey).isEmpty())
			return false;
		ProbabilisticGuardedRule rule = blockTable.get(labelGuardPairKey).iterator().next();
		String generatedFlag= rule.generatedFlag();
		if(generatedFlag==null) return true;
		Label checkedLabel = labelGuardPairKey.getFirst();
		if(!this.flagMapping.containsKey(checkedLabel)){
			return true;
		}
		else{
			return flagMapping.get(checkedLabel).equals(generatedFlag);
		}
	}


	private void selectBlock(
			List<MultiSet<String>> nonSelectedMultiSetsList, 
			Map<MultiSet<String>, Float> blockProbabilities, 
			Pair<Label, Guard> labelGuardPair, boolean maximality) {
		if(nonSelectedMultiSetsList.isEmpty()) return;
		Label label = labelGuardPair.getFirst();
		Guard guard = labelGuardPair.getSecond();
		MultiSet<String> multiSet = (MultiSet<String>)getRandomElement(nonSelectedMultiSetsList);
		removeNonValidBlocks(labelGuardPair, multiSet, nonSelectedMultiSetsList);
		long maxApplications = maxBlockApplications.get(new Triple<Label, Guard, MultiSet<String>>(label, guard, multiSet));
		long applications = calculateApplications(multiSet,
				maxApplications, maximality, blockProbabilities.get(multiSet));
		if(applications>0)
			registerBlockSelection(nonSelectedMultiSetsList, labelGuardPair, label,
					guard, multiSet, applications);
	}





	private void removeNonValidBlocks(Pair<Label, Guard> labelGuardPair, MultiSet<String> multiSet, List<MultiSet<String>> nonSelectedMultiSetsList) {
		Label label = labelGuardPair.getFirst();
		Guard guard = labelGuardPair.getSecond();
		Iterator<MultiSet<String>> multiSetIterator = nonSelectedMultiSetsList.listIterator();
		while(multiSetIterator.hasNext()){
			MultiSet<String> iteratedMultiSet = multiSetIterator.next();
			Triple<Label, Guard, MultiSet<String>> checkedTriple = new Triple<Label, Guard, MultiSet<String>>(label, guard, iteratedMultiSet);
			if(!generatesValidGuard(checkedTriple)){
				multiSetIterator.remove();
				multiSetIterator=nonSelectedMultiSetsList.listIterator();
			}
		}
				
				
		// TODO Auto-generated method stub
		
	}


	protected void registerBlockSelection(
			List<MultiSet<String>> selectedMultiSetsList,
			Pair<Label, Guard> labelGuardPair, Label label, Guard guard,
			MultiSet<String> multiSet, long applications) {
		Triple<Label, Guard, MultiSet<String>> labelGuardBlockKey = new Triple<Label, Guard, MultiSet<String>>(label, guard, multiSet);
		selectedMultiSetsList.remove(multiSet);
		if(consumeObjects(multiSet, applications, labelGuardBlockKey)){
			putBlockApplications(labelGuardBlockKey, applications);
			registerMultiSet(labelGuardPair, multiSet);
			registerFlagMapping(label, getGeneratedGuard(labelGuardBlockKey));
			
		}
	}





	private String getGeneratedGuard(
			Triple<Label, Guard, MultiSet<String>> labelGuardBlockKey) {
		// TODO Auto-generated method stub
		Set<ProbabilisticGuardedRule> ruleSet=blockTable.get(labelGuardBlockKey);
		if(ruleSet==null||ruleSet.isEmpty()) return null;
		return ruleSet.iterator().next().generatedFlag();
	}


	private void putBlockApplications(
			Triple<Label, Guard, MultiSet<String>> labelGuardBlockKey,
			long applications) {
		long applicationsToPut=applications;
		if(blockApplications.containsKey(labelGuardBlockKey))
			applicationsToPut+=blockApplications.get(labelGuardBlockKey);
		blockApplications.put(labelGuardBlockKey, applicationsToPut);
		
	}


	protected Object getRandomElement(
			List<?> list) {
		return list.get(generator.nextInt(list.size()));
	}


	protected void registerMultiSet(Pair<Label, Guard> labelGuardPair,
			MultiSet<String> multiSet) {
		Set<MultiSet<String>> setOfMultiSets = new HashSet<MultiSet<String>>();
		if(chosenMultiSets.containsKey(labelGuardPair))
			setOfMultiSets= chosenMultiSets.get(labelGuardPair);
		setOfMultiSets.add(multiSet);
		chosenMultiSets.put(labelGuardPair, setOfMultiSets);
		registerNotApplicableBlock(multiSet, labelGuardPair);
	}


	private void registerNotApplicableBlock(MultiSet<String> multiSet,
			Pair<Label, Guard> labelGuardPair) {
		RestrictiveGuard restrictiveGuard= (RestrictiveGuard) labelGuardPair.getSecond();
		if(restrictiveGuard.getType()==GuardTypes.SIMPLE_RESTRICTIVE) return;
		String flag=restrictiveGuard.getObj();
		if(multiSet.contains(flag))
			notApplicableBlocks.put(labelGuardPair.getFirst(), multiSet);
		
	}


	private boolean consumeObjects(MultiSet<String> multiSet, long applications, Triple<Label, Guard, MultiSet<String>> labelGuardBlockKey) {
		MultiSet<String> consumedMultiSet = new HashMultiSet<String>(multiSet);
		Membrane membrane = this.getMembraneByLabel(labelGuardBlockKey.getFirst(), getCurrentConfig().getMembraneStructure());
		RestrictiveGuard guard = (RestrictiveGuard) labelGuardBlockKey.getSecond();
		boolean result=membrane.getMultiSet().countSubSets(consumedMultiSet)>=applications;	
		if(result){
			markGuardForConsumption(labelGuardBlockKey, consumedMultiSet,
				guard);		
			membrane.getMultiSet().subtraction(consumedMultiSet, applications);
		}
		return result;
	}


	protected void markGuardForConsumption(
			Triple<Label, Guard, MultiSet<String>> labelGuardBlockKey,
			MultiSet<String> consumedMultiSet, RestrictiveGuard restrictiveGuard) {
		if(restrictiveGuard.getType()==GuardTypes.UNARY_UNIT_RESTRICTIVE){
			String flag = consumedFlag(consumedMultiSet);
			if(flag!=null){
				consumedMultiSet.remove(flag);
				this.flagConsumption.put(labelGuardBlockKey.getFirst(), flag);
			}
		}
	}






	private String consumedFlag(MultiSet<String> consumedMultiSet) {
		// TODO Auto-generated method stub
		for (String object : consumedMultiSet) {
			if(flags.contains(object))
				return object;
		}
		return null;
	}


	protected void clearSimulationStructures() {
		maxBlockApplications.clear();
		blockApplications.clear();
		ruleApplications.clear();
		chosenMultiSets.clear();
		flagMapping.clear();
		flagConsumption.clear();
		notApplicableBlocks.clear();
	}

	private void calculateMaximumApplicationsPerBlock() {
		Set<Triple<Label, Guard, MultiSet<String>>> blocks = ((ProbabilisticGuardedPsystem)getPsystem()).getBlockTable().getBlocks();
		for(Triple<Label, Guard, MultiSet<String>> block: blocks){
			long applications = countApplications(block);
			maxBlockApplications.put(block, applications);
		}
	}

	@Override
	public void microStepExecuteRules() {
		applyRules();
		executeScripts();
	}


	private void executeScripts() {
		scriptManager.executeScripts();
		
	}


	protected void applyRules() {
		for(Triple<Label, Guard, MultiSet<String>> ruleApplication: ruleApplications.keySet()){
			Map<ProbabilisticGuardedRule, Long> ruleMap  = ruleApplications.get(ruleApplication);
			for (ProbabilisticGuardedRule ruleToApply : ruleMap.keySet()) {
				applyRule(ruleToApply, ruleMap.get(ruleToApply));
			}
			consumeFlag(ruleApplication.getFirst());
		}
	}


	protected void consumeFlag(
			Label label) {
		if(flagConsumption.containsKey(label)){
			Membrane membrane = getMembraneByLabel(label, getCurrentConfig().getMembraneStructure());			
			membrane.getMultiSet().remove(flagConsumption.get(label));
		}
	}
	
	protected void applyRule(ProbabilisticGuardedRule ruleToApply, Long applications) {
		MultiSet<String> multiSet = getMultiSetToGenerate(ruleToApply);
		Label label = getAffectedMembrane(ruleToApply);
		Membrane membrane = getMembraneByLabel(label, getCurrentConfig().getMembraneStructure());
		addMultiSet(applications, multiSet, membrane, ruleToApply.generatedFlag());
		
	}


	protected void addMultiSet(Long applications, MultiSet<String> multiSet,
			Membrane membrane, String flag) {
		MultiSet<String> addedMultiSet = new HashMultiSet<String>(multiSet);
		MultiSet<String> targetMultiSet = membrane.getMultiSet();
		if(flag==null)
			targetMultiSet.addAll(addedMultiSet, applications);
		else{
			addedMultiSet.remove(flag);
			targetMultiSet.addAll(addedMultiSet, applications);
			if(!targetMultiSet.contains(flag))
				targetMultiSet.add(flag);
		}
	}

	protected Label getAffectedMembrane(ProbabilisticGuardedRule ruleToApply) {
		// TODO Auto-generated method stub
		return ruleToApply.getRightHandRule().getOuterRuleMembrane().getLabelObj();
	}

	protected MultiSet<String> getMultiSetToGenerate(
			ProbabilisticGuardedRule ruleToApply) {
		return ruleToApply.getRightHandRule().getOuterRuleMembrane().getMultiSet();
	}

	protected long countApplications(Triple<Label, Guard, MultiSet<String>> block) {
		Membrane membrane= getMembraneByLabel(block.getFirst(), getCurrentConfig().getMembraneStructure());
		return countApplications(membrane, block);		
	}

	protected ProbabilisticGuardedRule getFirstRule(
			Triple<Label, Guard, MultiSet<String>> block) {
		return blockTable.get(block).iterator().next();
	}

	protected long countApplications(Membrane membrane,
			Triple<Label, Guard, MultiSet<String>> block) {
		MultiSet<String> multiSet = membrane.getMultiSet();
		if(!compliesGuard(multiSet, block.getSecond()))
			return 0;
		return multiSet.countSubSets(block.getThird());
	}

	protected boolean compliesGuard(MultiSet<String> multiSet, Guard guard) {

		if(guard.getType()==GuardTypes.SIMPLE_RESTRICTIVE)
			return(!containsFlags(multiSet));
		return(multiSet.contains(((RestrictiveGuard)guard).getObj()));
	}

	private boolean containsFlags(MultiSet<String> multiSet) {
		// TODO Auto-generated method stub
		for (String string : flags) {
			if(multiSet.contains(string))
				return true;
		};
		return false;
	}


	@Override
	protected boolean hasSelectedRules() {
		// TODO Auto-generated method stub
		return !this.ruleApplications.isEmpty()&&!abort;
	}


	public void abort() {
		abort=true;
	}

	@Override
	protected void microStepSelectRules(ChangeableMembrane membrane,
			ChangeableMembrane tempMembrane) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		super.reset();
		abort=false;
	}
	

}
