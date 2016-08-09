package org.gcn.plinguacore.simulator.regenerative;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.gcn.plinguacore.simulator.simplekernel.SimpleKernelSimulator;
import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Configuration;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.regenerative.RegenerativePsystem;
import org.gcn.plinguacore.util.psystem.regenerative.membrane.RegenerativeMembrane;
import org.gcn.plinguacore.util.psystem.regenerative.membrane.RegenerativeMembraneStructure;
import org.gcn.plinguacore.util.psystem.rule.IKernelRule;
import org.gcn.plinguacore.util.psystem.rule.IPriorityRule;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.LeftHandRule;
import org.gcn.plinguacore.util.psystem.rule.RulesSet;
import org.gcn.plinguacore.util.psystem.rule.simplekernel.DivisionKernelLikeRule;
import org.gcn.plinguacore.util.psystem.simplekernel.membrane.SimpleKernelLikeMembraneStructure;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembraneStructure;

public class RegenerativeSimulator extends SimpleKernelSimulator {

	public RegenerativeSimulator(Psystem psystem) throws PlinguaCoreException {
		super(psystem);
		
		checkInitialMultiSetsAndStructureForLinkingObjects(psystem);
		// TODO Auto-generated constructor stub
	}

	protected void checkInitialMultiSetsAndStructureForLinkingObjects(Psystem psystem) throws PlinguaCoreException {
		Map<String, MultiSet<String>> initialMultiSets=psystem.getInitialMultiSets();
		SimpleKernelLikeMembraneStructure structure=(SimpleKernelLikeMembraneStructure)getPsystem().getMembraneStructure();
		Set<String> linkObjects=((RegenerativePsystem)psystem).getLinkObjects();
		checkInitialMultiSetsForLinkObjects(initialMultiSets, structure,
				linkObjects);
		checkStructureForLinkObjects(initialMultiSets, structure);
		
	}

	@Override
	protected Iterator<IRule> getApplicableRules(ChangeableMembrane temp) {
		// TODO Auto-generated method stub
		Set<IRule> originalRuleSet=filterRules(super.getApplicableRules(temp), temp);
		int minPriority = calculateMinPriority(temp, originalRuleSet);
		Set<IRule> bufferRuleSet = addRulesWithPriorityHigherThanMinimum(temp,
				originalRuleSet, minPriority);
		return bufferRuleSet.iterator();
		
		
	}

	@Override
	protected void microStepInit() {
		super.microStepInit();
		updateMembraneStructureOnRules();
	}

	@Override
	protected void microStepSelectRules(Configuration cnf, Configuration tmpCnf) {
		// TODO Auto-generated method stub
		setHalted(false);
		super.microStepSelectRules(cnf, tmpCnf);
	}

	protected void updateMembraneStructureOnRules() {
		structure=(SimpleKernelLikeMembraneStructure)getCurrentConfig().getMembraneStructure();
		for(IRule rule: getPsystem().getRules()){
			((DivisionKernelLikeRule)rule).setMembraneStructure(structure);
		}
	}

	protected Set<IRule> addRulesWithPriorityHigherThanMinimum(ChangeableMembrane temp,
			Set<IRule> originalRuleSet, int minPriority) {
		Set<IRule> bufferRuleSet= new HashSet<IRule>();
		for(IRule rule: originalRuleSet){
			if(rule.isExecutable(temp)&&((IPriorityRule)rule).getPriority()>=minPriority){
				bufferRuleSet.add(rule);
			}
		}
		checkAllRulesWithSamePriority(bufferRuleSet);
		return bufferRuleSet;
	}



	private void checkAllRulesWithSamePriority(Set<IRule> bufferRuleSet) {
		int standardPriority=Integer.MIN_VALUE;
		for(IRule rule:bufferRuleSet){
			int localePriority=((IPriorityRule)rule).getPriority();
			if(standardPriority==Integer.MIN_VALUE)
				standardPriority=localePriority;
			else{
				if(localePriority!=standardPriority){
					System.err.println("All priorities are not the same");
				}
			}
			
		}
		
	}

	protected int calculateMinPriority(ChangeableMembrane temp,
			Set<IRule> originalRuleSet) {
		int minPriority=Integer.MIN_VALUE;
		for(IRule rule: originalRuleSet){
			if(rule.isExecutable(temp)){
				minPriority=Math.max(((IPriorityRule)rule).getPriority(), minPriority);
			}
		}
		return minPriority;
	}






	@Override
	protected void printMembraneContent(ChangeableMembrane membrane) {
		// TODO Auto-generated method stub
		super.printMembraneContent(membrane);
		getInfoChannel().println("    Connections: " + printLinkedMembranes(membrane));
	}

	protected String printLinkedMembranes(ChangeableMembrane membrane) {
		return ((RegenerativeMembrane)membrane).getLinkedMembranes()+"";
	}

	protected void checkStructureForLinkObjects(
			Map<String, MultiSet<String>> initialMultiSets,
			SimpleKernelLikeMembraneStructure structure)
			throws PlinguaCoreException {
		for(Membrane membrane: structure.getAllMembranes()){
			String membraneLabel=membrane.getLabel();
			if(isNonEnvironmentalLabelWithInitialMultiset(initialMultiSets, structure,
					membraneLabel)){
				constructInvalidMultisetForLinksErrorMessage(membraneLabel,
						new HashMultiSet<String>(), new HashSet<String>(),
						1, null);
			}
		}
	}

	protected boolean isNonEnvironmentalLabelWithInitialMultiset(
			Map<String, MultiSet<String>> initialMultiSets,
			SimpleKernelLikeMembraneStructure structure, String membraneLabel) {
		return !isEnvironmentLabel(structure, membraneLabel)&&!initialMultiSets.containsKey(membraneLabel);
	}

	protected void checkInitialMultiSetsForLinkObjects(
			Map<String, MultiSet<String>> initialMultiSets,
			SimpleKernelLikeMembraneStructure structure, Set<String> linkObjects)
			throws PlinguaCoreException {
		for(Entry<String, MultiSet<String>> entry: initialMultiSets.entrySet()){
			String membraneLabel=entry.getKey();
			MultiSet<String> membraneMultiSet=entry.getValue();
			if(isNonEnvironmentalLabelInInitialStructure(structure, membraneLabel)){
				checkLinkObjectOnNonEnvironmentalMembrane(structure, linkObjects,
						membraneLabel, membraneMultiSet);
			}
			getPsystem().setMembraneStructure(structure);
			
		}
	}

	protected boolean isNonEnvironmentalLabelInInitialStructure(
			SimpleKernelLikeMembraneStructure structure, String membraneLabel) {
		return !isEnvironmentLabel(structure, membraneLabel)&&structure.iterator(membraneLabel).hasNext();
	}

	protected void checkLinkObjectOnNonEnvironmentalMembrane(
			SimpleKernelLikeMembraneStructure structure,
			Set<String> linkObjects, String membraneLabel,
			MultiSet<String> membraneMultiSet) throws PlinguaCoreException {
		Set<String> bufferMultiSet=new HashMultiSet<String>(membraneMultiSet).entrySet();
		RegenerativeMembrane membrane= (RegenerativeMembrane)structure.iterator(membraneLabel).next();
		bufferMultiSet.retainAll(linkObjects);
		int expectedLinkObjects=0;
		String linkObject=membrane.getLinkObject();
		if(linkObject==null||linkObject.isEmpty())
			expectedLinkObjects++;
		if(bufferMultiSet.size()!=expectedLinkObjects){
			constructInvalidMultisetForLinksErrorMessage(membraneLabel,
					membraneMultiSet, bufferMultiSet,
					expectedLinkObjects, linkObject);
			
		}
		setLinkObjectsAndAddInitialLinks(structure, linkObjects,
				bufferMultiSet, membrane);
	}

	protected void setLinkObjectsAndAddInitialLinks(
			SimpleKernelLikeMembraneStructure structure,
			Set<String> linkObjects, Set<String> bufferMultiSet,
			RegenerativeMembrane membrane) {
		String linkObject;
		linkObject=bufferMultiSet.iterator().next();
		structure.remove(membrane);
		membrane.setLinkObject(linkObject, linkObjects);
		addInitialLinks(membrane);
		structure.add(membrane);
	}

	private void addInitialLinks(RegenerativeMembrane membrane) {
		for(Integer membraneId:((RegenerativePsystem)getPsystem()).getLinkedMembranes(membrane.getId())){
			membrane.addLink(membraneId);
		}
		
	}

	protected void constructInvalidMultisetForLinksErrorMessage(String membraneLabel,
			MultiSet<String> membraneMultiSet, Set<String> bufferMultiSet,
			int expectedLinkObjects, String linkObject) throws PlinguaCoreException {
		String errorMessage= "Membrane "+membraneLabel+" with initial multiset "+membraneMultiSet+" and link object "+linkObject+" has the erroneous link objects "+bufferMultiSet+" where it should have "+expectedLinkObjects;
		getInfoChannel().println(errorMessage);
		throw new PlinguaCoreException(errorMessage);
	}

	protected boolean isEnvironmentLabel(
			SimpleKernelLikeMembraneStructure structure, String membraneLabel) {
		return membraneLabel.equals(structure.getEnvironmentLabel());
		
	}

}
