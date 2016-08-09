package org.gcn.plinguacore.simulator.probabilisticGuarded;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gcn.plinguacore.simulator.ISimulator;
import org.gcn.plinguacore.simulator.simplekernel.SimpleKernelSimulator;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.Pair;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.Triple;
import org.gcn.plinguacore.util.psystem.Label;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;
import org.gcn.plinguacore.util.psystem.probabilisticGuarded.ProbabilisticGuardedPsystem;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.LeftHandRule;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.rule.guard.GuardTypes;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveGuard;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRule;

public class DynamicSelectionProbabilisticGuardedTable {
	Map<Pair<Label, Guard>, Map<MultiSet<String>, Map<String, Float>>> staticTable;
	Map<Pair<Label, Guard>, Map<MultiSet<String>, Map<String, Float>>> dynamicTable;
	ProbabilisticGuardedTableNormalizer normalizer;
	ProbabilisticGuardedSimulator simulator;
	ProbabilisticGuardedPsystem psystem;
	
	public DynamicSelectionProbabilisticGuardedTable(ProbabilisticGuardedPsystem psystem, ISimulator simulator) {
		super();
		this.staticTable = new HashMap<Pair<Label, Guard>, Map<MultiSet<String>, Map<String, Float>>>();
		this.dynamicTable = new HashMap<Pair<Label, Guard>, Map<MultiSet<String>, Map<String, Float>>>();
		this.psystem =psystem;		
		constructTables();
		this.simulator = (ProbabilisticGuardedSimulator) simulator;
		this.normalizer = new ProbabilisticGuardedTableNormalizer(this, simulator);
		// TODO Auto-generated constructor stub
	}

	private void constructTables() {
		for (IRule rule : psystem.getRules()) {
			ProbabilisticGuardedRule probabilisticRule = (ProbabilisticGuardedRule) rule;
			addLeftHandSide(probabilisticRule);
		}
	}


	private void addLeftHandSide(ProbabilisticGuardedRule probabilisticRule) {
		Pair<Label,Guard> labelGuardKey= new Pair<Label, Guard>(getRuleLabel(probabilisticRule), getNullCheckedGuard(probabilisticRule));
		Map<MultiSet<String>, Map<String, Float>> multiSetMapping = new HashMap<MultiSet<String>, Map<String, Float>>();
		if(staticTable.containsKey(labelGuardKey))
			multiSetMapping = staticTable.get(labelGuardKey);
		addMultiSet(labelGuardKey, multiSetMapping, getMultiSet(probabilisticRule));
		
		
	}


	private void addMultiSet(Pair<Label,Guard> labelGuardKey,
			Map<MultiSet<String>, Map<String, Float>> multiSetMapping,
			MultiSet<String> multiSet) {
		if(!multiSetMapping.containsKey(multiSetMapping)){
			multiSetMapping.put(multiSet, createMultiSetMap(multiSet));
			staticTable.put(labelGuardKey, multiSetMapping);
			dynamicTable.put(labelGuardKey, cloneMultiSetMap(multiSetMapping));
		}
		
	}



	private Map<MultiSet<String>, Map<String, Float>> cloneMultiSetMap(
			Map<MultiSet<String>, Map<String, Float>> multiSetMapping) {
		// TODO Auto-generated method stub
		Map<MultiSet<String>, Map<String, Float>> clonedMap = 
				new HashMap<MultiSet<String>, Map<String, Float>>();
		for (MultiSet<String> multiSet : multiSetMapping.keySet()) {
			clonedMap.put(multiSet, cloneStringMap(multiSetMapping.get(multiSet)));			
		}
		return clonedMap;
	}

	private Map<String, Float> cloneStringMap( Map<String, Float> map) {
		Map<String, Float> clonedMap = new HashMap<String, Float>();
		for(String object: map.keySet()){
			clonedMap.put(object,map.get(object));
		}
		return clonedMap;
	}
	
	public static Map<Pair<Label, Guard>, Map<MultiSet<String>, Map<String, Float>>> cloneTable
	(Map<Pair<Label, Guard>, Map<MultiSet<String>, Map<String, Float>>> table){
		Map<Pair<Label, Guard>, Map<MultiSet<String>, Map<String, Float>>> clonedTable = 
				new HashMap<Pair<Label, Guard>, Map<MultiSet<String>, Map<String, Float>>>();
		for (Pair<Label, Guard> labelGuardKey : table.keySet()) {
			Map<MultiSet<String>, Map<String, Float>> clonedMap = 
					new HashMap<MultiSet<String>, Map<String, Float>>();
			for(MultiSet<String> multiSet: table.get(labelGuardKey).keySet())
				clonedMap.put(multiSet, new HashMap<String, Float>(table.get(labelGuardKey).get(multiSet)));
			clonedTable.put(labelGuardKey, clonedMap);
		}
		return clonedTable;
	}

	private Map<String, Float> createMultiSetMap(MultiSet<String> multiSet) {
		// TODO Auto-generated method stub
		Map<String, Float> multiSetMap = new HashMap<String, Float>();
		for (String string : multiSet) {
			multiSetMap.put(string, 1.0f/((float)multiSet.count(string)));
		}
		return multiSetMap;
	}

	private MultiSet<String> getMultiSet(
			ProbabilisticGuardedRule probabilisticRule) {
		// TODO Auto-generated method stub
		return probabilisticRule.getLeftHandRule().getOuterRuleMembrane().getMultiSet();
	}

	public RestrictiveGuard getNullCheckedGuard(ProbabilisticGuardedRule probabilisticRule) {
		// TODO Auto-generated method stub
		RestrictiveGuard guard = (RestrictiveGuard) probabilisticRule.getGuard();
		if(guard==null)
			return new RestrictiveGuard();
		return guard;
	}

	protected Label getRuleLabel(ProbabilisticGuardedRule probabilisticRule) {
		return probabilisticRule.getLeftHandRule().getOuterRuleMembrane().getLabelObj();
	}
	
	public Map<MultiSet<String>, Map<String, Float>> getDynamicTable(Pair<Label, Guard> labelGuardKey){
		return dynamicTable.get(labelGuardKey);
	}
	

	public void normalizeTables(MembraneStructure structure){
		try {
			this.normalizer.normalizeTables(structure.getAllMembranes());
		} catch (PlinguaCoreException e) {
			// TODO Auto-generated catch block
			simulator.abort();
			simulator.getInfoChannel().append((e.getMessage()));
		}
	}
	
	public void normalizeTable(Membrane membrane, Pair<Label, Guard> labelGuardKey){
		try {
			this.normalizer.normalizeTable(membrane, labelGuardKey);
		} catch (PlinguaCoreException e) {
			// TODO Auto-generated catch block
			simulator.abort();
			System.err.println(e.getMessage());
		}
	}
	
	public void calculateMaximumApplications(Membrane membrane, Pair<Label, Guard> labelGuardKey){
		this.normalizer.initializeNormalizationAndCalculateMinimumWeightedCardinalities(membrane, labelGuardKey);

	}
	
	public void calculateMaximumApplications(MembraneStructure structure){
		this.normalizer.initializeNormalizationAndCalculateMinimumWeightedCardinalities(structure.getAllMembranes());
	}
	
	public List<Pair<MultiSet<String>, Float>> getAccumulatedProbabilities(Label label, Guard guard){
		return normalizer.getAccumulatedProbabilities(label, guard);
	}
	
	public Map<MultiSet<String>, Float> getNormalizedValues(Label label, Guard guard){
		return normalizer.getNormalizedValues(label, guard);
	}
	
	
	

}
