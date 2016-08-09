/* 
 * pLinguaCore: A JAVA library for Membrane Computing
 *              http://www.p-lingua.org
 *
 * Copyright (C) 2009  Research Group on Natural Computing
 *                     http://www.gcn.us.es
 *                      
 * This file is part of pLinguaCore.
 *
 * pLinguaCore is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * pLinguaCore is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with pLinguaCore.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.gcn.plinguacore.util.psystem.rule.simplekernel;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.Label;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.rule.IDoubleCommunicationRule;
import org.gcn.plinguacore.util.psystem.rule.LeftHandRule;
import org.gcn.plinguacore.util.psystem.rule.OuterRuleMembrane;
import org.gcn.plinguacore.util.psystem.rule.RightHandRule;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembrane;

public class EvolutionCommunicationKernelLikeRule extends DivisionKernelLikeRule implements IDoubleCommunicationRule{

	private byte ruleType;
	private ChangeableMembrane rightHandMembrane;
	public void setRightHandMembrane(ChangeableMembrane rightHandMembrane) {
		this.rightHandMembrane = rightHandMembrane;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 5601163359552027963L;
	
	private long executions;
	
	protected EvolutionCommunicationKernelLikeRule(boolean dissolves,
			LeftHandRule leftHandRule, RightHandRule rightHandRule) {
		super(dissolves, leftHandRule, rightHandRule);
		executions=0;
		// TODO Auto-generated constructor stub
	}
	
	
	protected EvolutionCommunicationKernelLikeRule(boolean dissolves,
			LeftHandRule leftHandRule, RightHandRule rightHandRule, Guard guard, byte ruleType) {
		super(dissolves, leftHandRule, rightHandRule, guard);
		executions=0;
		this.ruleType = ruleType;
	}
	
	@Override
	public String getArrow(){
		return " --> ";
	}
	
	@Override
	public String toString()
	{
		String arrow = (ruleType==KernelRuleTypes.INPUT_OUTPUT)?" <--> ":getArrow();
		String accumulator = getLeftHandRule().toString()+arrow+getRightHandRule().toString(false);
		if(guard!=null)
			accumulator=guard.toString() + " ? "+ accumulator;
		return accumulator;
	}
	
	@Override
	protected void checkState() {
		
		super.checkState();
	}
	
	
	
	
		
	@Override
	public long countExecutions(ChangeableMembrane membrane, ChangeableMembrane original) {
		if(super.countExecutions(membrane,original)<=0) return 0; 
		
		
		if(membraneStructure!=null){
			RightHandRule rightHandRule = this.getRightHandRule();
			if(!membraneStructure.iterator(rightHandRule.getOuterRuleMembrane().getLabel()).hasNext()) return 0;
			for(OuterRuleMembrane affectedMembrane : rightHandRule.getAffectedMembranes())
				if(!membraneStructure.iterator(affectedMembrane.getLabel()).hasNext()) return 0;

		}
		executions = multiSetCount(getLeftHandRule().getOuterRuleMembrane().getMultiSet(),membrane.getMultiSet());
		return executions;
	}
	
	
	

	
	@Override
	protected boolean executeSafe(ChangeableMembrane membrane,
			MultiSet<String> environment, long executions) {
		if (dissolves()){
			membraneStructure.remove((TissueLikeMembrane) membrane);			
			return true;
		}

		if (!(membrane instanceof TissueLikeMembrane))
			throw new IllegalArgumentException("Invalid membrane type");
		membrane.getMultiSet().subtraction(getLeftHandRule().getOuterRuleMembrane().getMultiSet(), executions);
		List<ChangeableMembrane> modifiedMultisets = new LinkedList<ChangeableMembrane>();
		if (isEvolution())
			modifiedMultisets.add(membrane);
		else
			modifiedMultisets.add(rightHandMembrane);
		
		
		
		for(OuterRuleMembrane outerRuleMembrane : this.getRightHandRule().getAffectedMembranes()){
			Iterator<TissueLikeMembrane> tissueMembraneIterator = membraneStructure.iterator(outerRuleMembrane.getLabel());
			while(tissueMembraneIterator.hasNext())
				modifiedMultisets.add(tissueMembraneIterator.next());
		}
		for(ChangeableMembrane iteratedMembrane : modifiedMultisets){
			this.addMultiSet(iteratedMembrane, executions);
		}
			

		
		return true;
		
	
	}
	
	private void addMultiSet(ChangeableMembrane iteratedMembrane,
			long executions2) {
		MultiSet<String> sourceMultiSet =  getSourceMultiSet(iteratedMembrane.getLabelObj());
		if(sourceMultiSet!=null)
			iteratedMembrane.getMultiSet().addAll(sourceMultiSet, executions2);
		
		// TODO Auto-generated method stub
		
	}


	private MultiSet<String> getSourceMultiSet(Label label) {
		// TODO Auto-generated method stub
		if(getRightHandRule().getOuterRuleMembrane().getLabelObj().equals(label))
			return getRightHandRule().getOuterRuleMembrane().getMultiSet();
		for(OuterRuleMembrane outerRuleMembrane: getRightHandRule().getAffectedMembranes()){
			if(outerRuleMembrane.getLabelObj().equals(label))
				return outerRuleMembrane.getMultiSet();
		}
		return null;
	}


	private boolean isEvolution(){
		return getLeftHandRule().getOuterRuleMembrane().getLabel().equals(getRightHandRule().getOuterRuleMembrane().getLabel());
	}


	@Override
	public byte getRuleType() {
		// TODO Auto-generated method stub
		return ruleType;
	}
	
	

	

}
