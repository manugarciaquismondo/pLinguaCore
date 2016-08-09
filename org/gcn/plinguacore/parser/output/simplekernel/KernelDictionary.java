package org.gcn.plinguacore.parser.output.simplekernel;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.gcn.plinguacore.util.psystem.rule.RulesSet;

public class KernelDictionary {
	private Map<String, RulesSet> ruleSetMapping;
	private Map<String, Integer> numberOfRulesPerMembrane;
	private Map<String, Integer> membraneMapping;
	private Map<String, Integer> objectMapping;
	private Map<Integer, String> inverseMembraneMapping;
	private Map<Integer, String> inverseObjectMapping;
	public KernelDictionary() {
		super();
		ruleSetMapping = new HashMap<String, RulesSet>();
		numberOfRulesPerMembrane = new HashMap<String, Integer>();
		membraneMapping = new HashMap<String, Integer>();
		objectMapping = new HashMap<String, Integer>();
		inverseMembraneMapping = new HashMap<Integer, String>();
		inverseObjectMapping = new HashMap<Integer, String>();
	}
	
	public void createRuleSet(String string){
		ruleSetMapping.put(string, new RulesSet());
	}
	
	public void putRuleSet(String label, RulesSet ruleSet){
		this.ruleSetMapping.put(label, ruleSet);
	}
	
	public void putMembrane(String label, Integer membraneID){
		this.membraneMapping.put(label, membraneID);
		this.inverseMembraneMapping.put(membraneID, label);
	}
	
	public void putObject(String id, Integer objectID){
		this.objectMapping.put(id, objectID);
		this.inverseObjectMapping.put(objectID, id);
		
	}
	
	
	public void putNumberOfRules(String membraneID, Integer numberOfRules){
		this.numberOfRulesPerMembrane.put(membraneID, numberOfRules);
	}

	public Integer getObjectID(String string){
		return objectMapping.get(string);
	}
	
	public Integer getMembraneID(String string){
		return membraneMapping.get(string);
	}
	
	
	public RulesSet getRuleSet(String string){
		return ruleSetMapping.get(string);
	}
	
	public String getMembrane(Integer index){
		return inverseMembraneMapping.get(index);
	}
	
	public String getObject(Integer index){
		return inverseObjectMapping.get(index);
	}
	
	
	public Integer getNumberOfRules(String string){
		return numberOfRulesPerMembrane.get(string);
	}
	
	
	public Set<String> membranes(){
		return this.membraneMapping.keySet();
	}
	
	public Set<String> rules(){
		return this.ruleSetMapping.keySet();
	}
	
	public Set<String> objects(){
		return this.objectMapping.keySet();
	}
	
	public int numberOfObjects(){
		return this.objectMapping.keySet().size();
	}
	
	public int numberOfMembranes(){
		return this.membraneMapping.keySet().size();
	}
	
	
}
