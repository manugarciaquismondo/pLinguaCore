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

import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.rule.IKernelRule;
import org.gcn.plinguacore.util.psystem.rule.LeftHandRule;
import org.gcn.plinguacore.util.psystem.rule.OuterRuleMembrane;
import org.gcn.plinguacore.util.psystem.rule.RightHandRule;
import org.gcn.plinguacore.util.psystem.rule.AbstractRule;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.simplekernel.membrane.SimpleKernelLikeMembraneStructure;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembrane;

public class DivisionKernelLikeRule extends AbstractRule implements IKernelRule {

	protected Guard guard;
	
	protected SimpleKernelLikeMembraneStructure membraneStructure;
	/**
	 * 
	 */
	private static final long serialVersionUID = -2311858756048522881L;

	protected ChangeableMembrane latestDividedMembrane;

	protected DivisionKernelLikeRule(boolean dissolves, LeftHandRule leftHandRule,
			RightHandRule rightHandRule) {
		super(dissolves, leftHandRule, rightHandRule);
		guard=null;
		
	}
	
		

	public void setMembraneStructure(SimpleKernelLikeMembraneStructure membraneStructure) {
		this.membraneStructure = membraneStructure;
	}
	
	

	protected DivisionKernelLikeRule(boolean dissolves, LeftHandRule leftHandRule,
			RightHandRule rightHandRule, Guard guard) {
		this(dissolves, leftHandRule, rightHandRule);
		this.guard = guard;
	}



	@Override
	protected void checkState() {
		
		super.checkState();
		/*if (dissolves())
			throw new IllegalArgumentException("Dissolution rules are not allowed");*/
	}

	@Override
	public boolean guardEvaluates(ChangeableMembrane membrane){
		if(guard==null) return true;
		guard.setMultiSet(membrane.getMultiSet());
		return guard.evaluate();
	}


	@Override
	public long countExecutions(ChangeableMembrane membrane) {
		if (!checkLabel(membrane))
			return 0;
		if (!(membrane instanceof TissueLikeMembrane))
			throw new IllegalArgumentException("Invalid membrane type");
		long executions = 0;
		MultiSet<String> ms = getLeftHandRule().getOuterRuleMembrane().getMultiSet();
		//if (!guardEvaluates(membrane)) return 0;
		if (!checkLabel(membrane)) return 0;
		if (ms==null || ms.isEmpty())
			executions = 1;
		else
			executions = multiSetCount(ms, membrane.getMultiSet());
		return executions;
	}
	
	public long countExecutions(ChangeableMembrane membrane, ChangeableMembrane original) {
		if (!checkLabel(membrane))
			return 0;
		if (!(membrane instanceof TissueLikeMembrane))
			throw new IllegalArgumentException("Invalid membrane type");
		long executions = 0;
		MultiSet<String> ms = getLeftHandRule().getOuterRuleMembrane().getMultiSet();
		if (!guardEvaluates(original)) return 0;
		if (!checkLabel(membrane)) return 0;
		if (ms==null || ms.isEmpty())
			executions = 1;
		else
			executions = multiSetCount(ms, membrane.getMultiSet());
		return executions;
	}

	

	@Override
	protected boolean executeSafe(ChangeableMembrane membrane,
			MultiSet<String> environment, long executions) {

		if (dissolves()){
			System.out.println("Trying to dissolve");
			membraneStructure.getAllMembranes().remove(membrane);
		} else {
			checkAndSubstractMultiSet(membrane, executions);

			Iterator<OuterRuleMembrane> outerMembranes = this.getRightHandRule().getAffectedMembranes().iterator();
			while (outerMembranes.hasNext())
			{
				OuterRuleMembrane outerRuleMembrane= outerMembranes.next();
				latestDividedMembrane = createDividedMembrane(membrane);			
				updateMembrane(latestDividedMembrane,outerRuleMembrane,executions);
			} 
			
			updateMembrane(membrane, getRightHandRule().getOuterRuleMembrane(),executions);
			membraneStructure.updateMembranes(getLeftHandRule().getOuterRuleMembrane().getLabel());
		}
		return true;
	}



	protected ChangeableMembrane createDividedMembrane(
			ChangeableMembrane membrane) {
		return membrane.divide();
	}



	protected void checkAndSubstractMultiSet(ChangeableMembrane membrane,
			long executions) {
		MultiSet<String> ms = getLeftHandRule().getOuterRuleMembrane().getMultiSet();

		if (ms!=null && !ms.isEmpty())
			subtractMultiSet(ms, membrane.getMultiSet(), executions);
	}

	@Override
	public Guard getGuard() {
		return guard;
	}
	
	public String getArrow(){
		return " |--> ";
	}
	
	@Override
	public String toString()
	{
		String accumulator = getLeftHandRule().toString()+getArrow()+getRightHandRule().toString(false);
		if(guard!=null)
			accumulator=guard.toString() + " ? " + accumulator;
		return accumulator;
	}



	@Override
	public byte getRuleType() {
		// TODO Auto-generated method stub
		return KernelRuleTypes.DIVISION;
	}



	@Override
	protected void updateMembrane(ChangeableMembrane m, OuterRuleMembrane orm,
			long executions) {
		// TODO Auto-generated method stub
		super.updateMembrane(m, orm, executions);
		m.setLabelObj(orm.getLabelObj());
	}
	
	@Override
	public boolean execute(ChangeableMembrane membrane,
			MultiSet<String> environment, long executions) {
		return executeSafe(membrane, environment, executions);
	}
	
	@Override
	public boolean equals(Object obj){
	if(!super.equals(obj)) return false;
	IKernelRule comparedRule =(IKernelRule)obj;
	if(comparedRule.getRuleType()!=getRuleType()) return false;
	if((this.getGuard()==null)!=(comparedRule.getGuard()==null)) return false;
	if(getGuard()!=null&&(!comparedRule.getGuard().equals(getGuard()))) return false;
	return true;
	}
	
}
