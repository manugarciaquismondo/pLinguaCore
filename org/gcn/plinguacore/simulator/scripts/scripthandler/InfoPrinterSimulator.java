package org.gcn.plinguacore.simulator.scripts.scripthandler;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.gcn.plinguacore.simulator.AbstractSelectionExecutionSimulator;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.Pair;
import org.gcn.plinguacore.util.RandomNumbersGenerator;
import org.gcn.plinguacore.util.Triple;
import org.gcn.plinguacore.util.psystem.Label;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.rule.guard.GuardTypes;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveGuard;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRule;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRuleBlockTable;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembrane;

public abstract class InfoPrinterSimulator extends AbstractSelectionExecutionSimulator{


	
	public InfoPrinterSimulator(Psystem psystem) {
		super(psystem);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getHead(ChangeableMembrane m) {
		// TODO Auto-generated method stub
		String str;
		str="CELL ID: "+m.getId();
		str += ", Label: " + m.getLabelObj();
		return str;
	}
	
	@Override
	protected void printInfoMembraneShort(MembraneStructure membraneStructure) {
		// TODO Auto-generated method stub
		Iterator<? extends Membrane>it = membraneStructure.getAllMembranes().iterator();
		while(it.hasNext())
			printInfoMembrane((ChangeableMembrane)it.next());
	}

	@Override
	protected void printInfoMembrane(ChangeableMembrane membrane) {
		// TODO Auto-generated method stub
		TissueLikeMembrane tlm = (TissueLikeMembrane)membrane;
		if (!tlm.getLabel().equals(tlm.getStructure().getEnvironmentLabel()))
		{
			getInfoChannel().println("    " + getHead(membrane));
			getInfoChannel().println("    Multiset: " + membrane.getMultiSet());
			getInfoChannel().println();
		}
	}
	
	@Override
	protected void removeLeftHandRuleObjects(ChangeableMembrane membrane,
			IRule r, long count) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
}
