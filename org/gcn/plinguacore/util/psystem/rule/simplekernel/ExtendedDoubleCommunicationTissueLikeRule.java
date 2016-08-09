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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;
import org.gcn.plinguacore.util.psystem.rule.IDoubleCommunicationRule;
import org.gcn.plinguacore.util.psystem.rule.LeftHandRule;
import org.gcn.plinguacore.util.psystem.rule.OuterRuleMembrane;
import org.gcn.plinguacore.util.psystem.rule.RightHandRule;
import org.gcn.plinguacore.util.psystem.rule.tissueLike.Communication;
import org.gcn.plinguacore.util.psystem.rule.tissueLike.DoubleCommunicationTissueLikeRule;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembrane;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembraneStructure;

public class ExtendedDoubleCommunicationTissueLikeRule extends DoubleCommunicationTissueLikeRule implements IDoubleCommunicationRule{

	protected TissueLikeMembrane tlm;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5601163359552027963L;
	
	protected long max;
	
	protected ExtendedDoubleCommunicationTissueLikeRule(boolean dissolves,
			LeftHandRule leftHandRule, RightHandRule rightHandRule) {
		super(dissolves, leftHandRule, rightHandRule);
		communications = new ArrayList<Communication>();
		executions=0;
		// TODO Auto-generated constructor stub
	}

	
	
	
	protected void selectCommunications(TissueLikeMembrane firstMembrane, String label, MultiSet<String> ms){
		
		TissueLikeMembraneStructure structure = firstMembrane.getStructure();
		long count=0;
		Iterator<TissueLikeMembrane>it=structure.iterator(label, true);
		
		while(it.hasNext() && count<=max)
		{
			TissueLikeMembrane secondMembrane = it.next();
			long n;
			if (ms.isEmpty())
				n=max;
			else
				n = multiSetCount(ms,secondMembrane.getMultiSet());
			if (n>0)
			{
				if (count+n>max)
					n=max-count;
				count+=n;
				communications.add(new Communication(firstMembrane,secondMembrane,this,n));
			}
		}
		executions=count;
		
	}
	
	protected void selectCommunications(TissueLikeMembrane firstMembrane,long max)
	{
		communications.clear();
		this.max = multiSetCount(getLeftHandRule().getOuterRuleMembrane().getMultiSet(), firstMembrane.getMultiSet());
		selectCommunications(firstMembrane, getRightHandRule().getOuterRuleMembrane().getLabel(), getRightHandRule().getOuterRuleMembrane().getMultiSet());
		List<OuterRuleMembrane> affectedMembranes = getRightHandRule().getAffectedMembranes();
		if(affectedMembranes!=null)
			for(OuterRuleMembrane membrane: affectedMembranes)
				selectCommunications(firstMembrane, membrane.getLabel(), membrane.getMultiSet());
		
		
	}
	
	private void selectCommunications(TissueLikeMembrane firstMembrane)
	{
		selectCommunications(firstMembrane,multiSetCount(getLeftHandRule().getOuterRuleMembrane().getMultiSet(), firstMembrane.getMultiSet()));
		
	}
	
	protected void updateMembrane(ChangeableMembrane sourceMembrane, ChangeableMembrane destinationMembrane,
			MultiSet<String> environment, long executions){
		
	}
	
	@Override
	protected boolean executeSafe(ChangeableMembrane membrane,
			MultiSet<String> environment, long executions) {
		if (!(membrane instanceof TissueLikeMembrane))
			throw new IllegalArgumentException("Invalid membrane type");
		tlm = (TissueLikeMembrane)membrane;
		
		MultiSet<String>ms1 = getLeftHandRule().getOuterRuleMembrane().getMultiSet();
		MultiSet<String>ms2 = getRightHandRule().getOuterRuleMembrane().getMultiSet();
		
		selectCommunications(tlm,executions);
		
		tlm.getMultiSet().subtraction(ms1, executions);
		tlm.getMultiSet().addAll(ms2,executions);
						
		Iterator <Communication>it = communications.iterator();
	
		while(it.hasNext())
		{
			Communication com = it.next();
			TissueLikeMembrane m = com.getSecondMembrane();
			long mul = com.getExecutions();
			ms2 = getCorrespondingMultiSet(com.getSecondMembrane().getLabel(), this.getRightHandRule());
			if(ms2!=null){
				m.getMultiSet().subtraction(ms2, mul);
				m.getMultiSet().addAll(ms1,mul);
			}
		}
		
		return true;
	}
	
		
	private MultiSet<String> getCorrespondingMultiSet(String label,
			RightHandRule rightHandRule) {
		// TODO Auto-generated method stub
		List<OuterRuleMembrane> affectedMembranes = rightHandRule.getAffectedMembranes(); 
		if(affectedMembranes==null||rightHandRule.getOuterRuleMembrane().getLabel().equals(label))
			return rightHandRule.getOuterRuleMembrane().getMultiSet();
		for(OuterRuleMembrane membrane: affectedMembranes){
			if(membrane.getLabel().equals(label))
				return membrane.getMultiSet();
		}
		return null;
	}




	@Override
	public long countExecutions(ChangeableMembrane membrane) {
		// TODO Auto-generated method stub
		if (!checkLabel(membrane))
			return 0;
		if (!(membrane instanceof TissueLikeMembrane))
			throw new IllegalArgumentException("Invalid membrane type");
		TissueLikeMembrane tlm = (TissueLikeMembrane)membrane;
		selectCommunications(tlm);
		return executions;
	}
	
	
	

	
	
	

	

}
