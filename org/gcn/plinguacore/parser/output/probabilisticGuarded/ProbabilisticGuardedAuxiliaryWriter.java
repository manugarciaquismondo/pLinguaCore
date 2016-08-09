package org.gcn.plinguacore.parser.output.probabilisticGuarded;

import java.util.Set;

import org.gcn.plinguacore.parser.output.simplekernel.KernelObjectWriter;
import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.Label;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.probabilisticGuarded.ProbabilisticGuardedPsystem;

public class ProbabilisticGuardedAuxiliaryWriter {
	public static MultiSet<String> removeFlag(MultiSet<String> multiSet, Set<String> flags) {
		// TODO Auto-generated method stub
		if(multiSet==null) return null;
		MultiSet<String> returnedMultiSet = new HashMultiSet<String>(multiSet);
		returnedMultiSet.removeAll(flags);
		return returnedMultiSet;
	}
	
	protected static void writeFlag(String flag, StringBuffer psystemDescription, KernelObjectWriter objectWriter, boolean prepend, boolean append) {
		if(prepend)
			psystemDescription.append(" : ");
		if(flag==null||flag.equals("#"))
			psystemDescription.append("#");
		else
			objectWriter.writeObject(flag, psystemDescription);
		if(append)
			psystemDescription.append(" : ");
	}
	
	public static int getMaximumNumberOfRulesPerMembrane(Psystem psystem) {
		// TODO Auto-generated method stub
		int maxNumberOfRulesPerMembrane=0;
		for (Membrane membrane : psystem.getMembraneStructure().getAllMembranes()) {
			maxNumberOfRulesPerMembrane=Math.max(getNumberOfRules(membrane, psystem), maxNumberOfRulesPerMembrane);			
		}
		return maxNumberOfRulesPerMembrane;
	}
	
	public static int getNumberOfRules(Membrane membrane, Psystem psystem){
		Label label = membrane.getLabelObj();
		return psystem.getRules().getNumberOfRules(label.getLabelID(), label.getEnvironmentID(), membrane.getCharge());
	}
	
	public static String getFlag(MultiSet<String> multiSet, Set<String> flags) {
		if(multiSet==null) return null;
		MultiSet<String> copiedMultiSet = new HashMultiSet<String>(multiSet);
		copiedMultiSet.retainAll(flags);
		if(!copiedMultiSet.isEmpty())
			return copiedMultiSet.iterator().next();
		return null;
	}

}
