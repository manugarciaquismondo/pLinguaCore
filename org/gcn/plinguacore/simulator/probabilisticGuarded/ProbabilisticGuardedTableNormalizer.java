package org.gcn.plinguacore.simulator.probabilisticGuarded;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gcn.plinguacore.parser.input.probabilisticGuarded.Probabilistic_Guarded_Simulator_Parser.psystem_return;
import org.gcn.plinguacore.simulator.ISimulator;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.Pair;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Label;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.rule.guard.GuardTypes;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveGuard;

public class ProbabilisticGuardedTableNormalizer {
	
	
	private Map<Pair<Label, Guard>, Map<MultiSet<String>, Map<String, Float>>> dynamicTable;
	private Map<Pair<Label, Guard>, Map<MultiSet<String>, Map<String, Float>>> staticTable;
	private Map<Pair<Label, Guard>, Float> sumOfWeightedCardinalitiesTable;
	private Set<String> flags;
	private Map<Pair<Label, Guard>, Map<MultiSet<String>, Float>> normalizedWeightedCardinalities;
	private Map<Pair<Label, Guard>, List<Pair<MultiSet<String>, Float>>> accumulatedProbabilities;
	ISimulator simulator;
	
	public ProbabilisticGuardedTableNormalizer(DynamicSelectionProbabilisticGuardedTable table, ISimulator simulator){
		super();
		this.simulator=simulator;
		staticTable = table.staticTable;
		dynamicTable = table.dynamicTable;
		flags = table.psystem.getFlags();
		sumOfWeightedCardinalitiesTable = new HashMap<Pair<Label, Guard>, Float>();
		normalizedWeightedCardinalities = new HashMap<Pair<Label, Guard>, Map<MultiSet<String>, Float>>();
		accumulatedProbabilities = new HashMap<Pair<Label, Guard>, List<Pair<MultiSet<String>, Float>>>();
	}
	
	public void normalizeTable(Membrane membrane, Pair<Label, Guard> labelGuardKey) throws PlinguaCoreException{
		initializeNormalizationAndCalculateMinimumWeightedCardinalities(
				membrane, labelGuardKey);
		normalizeMinimumWeightedCardinalities(labelGuardKey, membrane);
		calculateAccumulatedProbabilities(labelGuardKey, membrane);
	}

	public void initializeNormalizationAndCalculateMinimumWeightedCardinalities(
			Membrane membrane, Pair<Label, Guard> labelGuardKey) {
		initializeNormalization();
		calculateMinimumWeightedCardinalities(labelGuardKey, membrane);
	}
	
	public void normalizeTables(Collection<? extends Membrane> membranes) throws PlinguaCoreException{
		initializeNormalizationAndCalculateMinimumWeightedCardinalities(membranes);
		normalizeMinimumWeightedCardinalities(membranes);
		calculateAccumulatedProbabilities(membranes);
	}

	public void initializeNormalizationAndCalculateMinimumWeightedCardinalities(
			Collection<? extends Membrane> membranes) {
		initializeNormalization();
		calculateMinimumWeightedCardinalities(membranes);
	}

	protected void initializeNormalization() {
		dynamicTable = DynamicSelectionProbabilisticGuardedTable.cloneTable(staticTable);
		clearStructures();
	}
	

	private void clearStructures(Pair<Label, Guard> labelGuardKey) {
		if(sumOfWeightedCardinalitiesTable.containsKey(labelGuardKey))
			sumOfWeightedCardinalitiesTable.put(labelGuardKey,0.0f);
		if(accumulatedProbabilities.containsKey(labelGuardKey))
			accumulatedProbabilities.get(labelGuardKey).clear();
		if(normalizedWeightedCardinalities.containsKey(labelGuardKey))
			normalizedWeightedCardinalities.get(labelGuardKey).clear();
		
	}
	
	private void clearStructures() {
		sumOfWeightedCardinalitiesTable.clear();
		accumulatedProbabilities.clear();
		normalizedWeightedCardinalities.clear();
		
	}

	public Map<MultiSet<String>, Float> getNormalizedValues(Label label, Guard guard){
		return normalizedWeightedCardinalities.get(new Pair<Label, Guard>(label, guard));
	}
	
	public List<Pair<MultiSet<String>, Float>> getAccumulatedProbabilities(Label label, Guard guard){
		return accumulatedProbabilities.get(new Pair<Label, Guard>(label, guard));
	}
	
	private void calculateAccumulatedProbabilities(
			Collection<? extends Membrane> membranes) {
		for(Pair<Label, Guard> labelGuardKey:normalizedWeightedCardinalities.keySet()){
			Membrane membrane = getMembraneByLabel(membranes, labelGuardKey.getFirst());
			calculateAccumulatedProbabilities(labelGuardKey, membrane);
		}
		
	}

	protected void calculateAccumulatedProbabilities(
			Pair<Label, Guard> labelGuardKey, Membrane membrane) {
		if((compliesGuard(membrane.getMultiSet(), labelGuardKey.getSecond()))){
		calculateAccumulatedProbabilities(normalizedWeightedCardinalities.get(labelGuardKey));
		}
	}

	private void calculateAccumulatedProbabilities(
			Map<MultiSet<String>, Float> map) {
		List<Pair<MultiSet<String>, Float>> accumulatedProbabilitiesList= new LinkedList<Pair<MultiSet<String>, Float>>();
		float previousValue=0.0f;
		for (MultiSet<String> multiSet : map.keySet()) {
			previousValue+=map.get(multiSet);
			accumulatedProbabilitiesList.add(new Pair<MultiSet<String>, Float>(multiSet, previousValue));			
		}
		// TODO Auto-generated method stub
		
	}


	private void normalizeMinimumWeightedCardinalities(
			Collection<? extends Membrane> membranes) throws PlinguaCoreException {
		calculateNormalizedMinimumWeightedCardinalities(membranes);
		checkNormalizedMinimumWeightedCardinalities(membranes);
		
	}
	
	private void normalizeMinimumWeightedCardinalities(
			Pair<Label, Guard> labelGuardKey, Membrane membrane) throws PlinguaCoreException {
		calculateNormalizedMinimumWeightedCardinalities(membrane, labelGuardKey);
		checkNormalizedMinimumWeightedCardinalities(labelGuardKey, membrane);
		
	}

	
	
	private void checkNormalizedMinimumWeightedCardinalities(
			Collection<? extends Membrane> membranes) throws PlinguaCoreException{
		for(Pair<Label, Guard> labelGuardKey:normalizedWeightedCardinalities.keySet()){
			Membrane membrane = getMembraneByLabel(membranes, labelGuardKey.getFirst());
			checkNormalizedMinimumWeightedCardinalities(labelGuardKey, membrane);
		}
		
	}

	protected void checkNormalizedMinimumWeightedCardinalities(
			Pair<Label, Guard> labelGuardKey, Membrane membrane)
			throws PlinguaCoreException {
		if((compliesGuard(membrane.getMultiSet(), labelGuardKey.getSecond()))){
		Map<MultiSet<String>, Float> normalizedMinimumWeightedMultiSetCardinalities = normalizedWeightedCardinalities.get(labelGuardKey);
		float normalizedMinimumWeightedMultiSetCardinalitiesSum= calculateNormalizedMinimumWeightedMultiSetCardinalitiesSum(normalizedMinimumWeightedMultiSetCardinalities);
		if(differentFrom1(normalizedMinimumWeightedMultiSetCardinalitiesSum))
			throw new PlinguaCoreException("An execution error ocurred while evaluating blocks in "+labelGuardKey+". The sum of block probabilities in "+normalizedMinimumWeightedMultiSetCardinalities+" is "+normalizedMinimumWeightedMultiSetCardinalitiesSum+" instead of 1.0");
		}
	}

	private boolean differentFrom1(
			float number) {
		// TODO Auto-generated method stub
		return number<0.999||number>1.001;
	}

	private float calculateNormalizedMinimumWeightedMultiSetCardinalitiesSum(
			Map<MultiSet<String>, Float> normalizedMinimumWeightedMultiSetCardinalities) {
		float sum=0;
		for (Float weightedCardinality : normalizedMinimumWeightedMultiSetCardinalities.values()) {
			sum+=weightedCardinality;
		}
		return sum;
	}

	protected void calculateNormalizedMinimumWeightedCardinalities(
			Collection<? extends Membrane> membranes) {
		for(Pair<Label, Guard> labelGuardKey:normalizedWeightedCardinalities.keySet()){
			Membrane membrane = getMembraneByLabel(membranes, labelGuardKey.getFirst());
			calculateNormalizedMinimumWeightedCardinalities(membrane,
					labelGuardKey);
		}
	}

	protected void calculateNormalizedMinimumWeightedCardinalities(
			Membrane membrane,
			Pair<Label, Guard> labelGuardKey) {
		if(!(compliesGuard(membrane.getMultiSet(), labelGuardKey.getSecond())))
			return;
		normalizedWeightedCardinalities.put(labelGuardKey, 
				normalizeWeightedCardinalities(normalizedWeightedCardinalities.
						get(labelGuardKey), sumOfWeightedCardinalitiesTable.
						get(labelGuardKey)));
	}

	private Map<MultiSet<String>, Float> normalizeWeightedCardinalities(Map<MultiSet<String>, Float> map, Float sum) {
		Map<MultiSet<String>, Float> normalizedMap = new HashMap<MultiSet<String>, Float>();
		for (MultiSet<String> multiSet : map.keySet()) {
			normalizedMap.put(multiSet, map.get(multiSet)/sum);					
		}
		return normalizedMap;
		
	}

	protected void calculateMinimumWeightedCardinalities(
			Collection<? extends Membrane> membranes) {
		for(Pair<Label, Guard> labelGuardKey:dynamicTable.keySet()){
			Membrane membrane = getMembraneByLabel(membranes, labelGuardKey.getFirst());
			calculateMinimumWeightedCardinalities(labelGuardKey, membrane);
		}
	}

	protected void calculateMinimumWeightedCardinalities(
			Pair<Label, Guard> labelGuardKey, Membrane membrane) {
		if((compliesGuard(membrane.getMultiSet(), labelGuardKey.getSecond()))){
			Map<MultiSet<String>, Map<String, Float>> multiSetMap = dynamicTable.get(labelGuardKey); 
			for(MultiSet<String> multiSetKey: multiSetMap.keySet()){
				weightCardinality(membrane.getMultiSet(), labelGuardKey, dynamicTable.get(labelGuardKey).get(multiSetKey), multiSetKey);
			}			
		}
	}

	private boolean compliesGuard(MultiSet<String> multiSet, Guard guard) {
		if(guard.getType()==GuardTypes.SIMPLE_RESTRICTIVE)
			return multiSet.countSubSets(flags)==0;
		return multiSet.contains(((RestrictiveGuard)guard).getObj());
	}

	private Membrane getMembraneByLabel(
			Collection<? extends Membrane> membranes, Label first) {
		// TODO Auto-generated method stub
		for(Membrane membrane: membranes){
			if(membrane.getLabelObj().equals(first))
				return membrane;
		}
		return null;
	}

	private void weightCardinality(MultiSet<String> multiSet, Pair<Label, Guard> labelGuardKey, Map<String, Float> stringMap, MultiSet<String> multiSetKey) {
		for (String object : stringMap.keySet()) {
			stringMap.put(object, stringMap.get(object)*multiSet.count(object));
			
		}
		float minCardinality = getMin(stringMap.values());
		putWeightedCardinality(labelGuardKey, multiSet, minCardinality, multiSetKey);
	}

	private float getMin(Collection<Float> values) {
		// TODO Auto-generated method stub
		Float minValue =Float.MAX_VALUE;
		for (Float value : values) {
			minValue = Math.min(minValue, value);			
		}
		return minValue;
	}

	private void putWeightedCardinality(
			Pair<Label, Guard> labelGuardKey,
			MultiSet<String> multiSet, float minCardinality, MultiSet<String> multiSetKey) {
		Map<MultiSet<String>, Float> normalizedMap = new HashMap<MultiSet<String>, Float>();
		if(normalizedWeightedCardinalities.containsKey(labelGuardKey))
			normalizedMap = normalizedWeightedCardinalities.get(labelGuardKey);
		normalizedMap.put(multiSetKey, minCardinality);
		normalizedWeightedCardinalities.put(labelGuardKey, normalizedMap);
		addCardinalityToSumsPerObject(labelGuardKey, minCardinality);
	}

	private void addCardinalityToSumsPerObject(Pair<Label, Guard> labelGuardKey, float minCardinality) {
		Float sum=minCardinality;
		if(sumOfWeightedCardinalitiesTable.containsKey(labelGuardKey))
			sum += sumOfWeightedCardinalitiesTable.get(labelGuardKey);
		sumOfWeightedCardinalitiesTable.put(labelGuardKey, sum);		
	}
}
