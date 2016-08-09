package org.gcn.plinguacore.parser.output.simplekernel;

import org.gcn.plinguacore.parser.output.probabilisticGuarded.ProbabilisticGuardedAuxiliaryWriter;
import org.gcn.plinguacore.parser.output.simplekernel.KernelDictionary;
import org.gcn.plinguacore.parser.output.simplekernel.KernelHeaderWriter;
import org.gcn.plinguacore.util.psystem.AlphabetObject;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.OuterRuleMembrane;
import org.gcn.plinguacore.util.psystem.rule.RulesSet;
import org.gcn.plinguacore.util.psystem.simplekernel.SimpleKernelLikePsystem;

public class KernelMapper {
	
	SimpleKernelLikePsystem psystem;

	private KernelDictionary dictionary;
	
	public KernelMapper(SimpleKernelLikePsystem psystem) {
		super();
		this.psystem = psystem;
		dictionary = new KernelDictionary();

		
	}

	
	public void createMappings(){
		createMaxNumberOfRulesMapping();
		createMembraneMapping();
		createRuleMapping();
		createObjectMapping();
	}

	private void createRuleMapping(){
		for(Membrane membrane : psystem.getMembraneStructure().getAllMembranes())
			dictionary.createRuleSet(membrane.getLabel());
		createRuleMappingFromRules();

	}

	private void createRuleMappingFromRules() {
		RulesSet ruleSet = psystem.getRules();
		for (IRule rule : ruleSet) {
			String ruleLabel = getRuleLabel(rule);
			RulesSet mappingRuleSet = dictionary.getRuleSet(ruleLabel);
			if(mappingRuleSet==null) continue;
			mappingRuleSet.add(rule);
			dictionary.putRuleSet(ruleLabel, mappingRuleSet);
		}
	}
	
	public String getRuleLabel(IRule rule) {
		OuterRuleMembrane ruleMembrane = rule.getLeftHandRule().getOuterRuleMembrane(); 
		return ruleMembrane.getLabel();
	}
	
	private void createMaxNumberOfRulesMapping() {
		for(Membrane membrane : psystem.getMembraneStructure().getAllMembranes()){
			dictionary.putNumberOfRules(membrane.getLabel(), ProbabilisticGuardedAuxiliaryWriter.getNumberOfRules(membrane, psystem));
		}
		
	}
	
	private void createMembraneMapping() {
		int membraneIndex=0;
		for (Membrane membrane : psystem.getMembraneStructure().getAllMembranes()) {
			this.dictionary.putMembrane(membrane.getLabel(), membraneIndex++);			
		}
	}
	
	private void createObjectMapping() {
		// TODO Auto-generated method stub
		int objectIndex=0;
		for (AlphabetObject object : psystem.getAlphabet()) {
			this.dictionary.putObject(object.toString(), objectIndex++);
		}
	}


	public Integer getObjectID(String object){
		return dictionary.getObjectID(object);
	}
	
	public Integer getMembraneID(String label){
		return dictionary.getMembraneID(label);
	}
	
	
	public RulesSet getRuleSet(String label){
		return dictionary.getRuleSet(label);
	}
	
	public Integer getNumberOfRules(String string){
		return dictionary.getNumberOfRules(string);
	}


	public KernelDictionary getDictionary() {
		// TODO Auto-generated method stub
		return dictionary;
	}
	



}
