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

package org.gcn.plinguacore.simulator.cellLike.transition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.gcn.plinguacore.simulator.cellLike.CellLikeSimulator;
import org.gcn.plinguacore.util.RandomNumbersGenerator;
import org.gcn.plinguacore.util.ShuffleIterator;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.rule.IPriorityRule;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.CheckRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoDivision;

public final class NonDeterministicTransitionSimulatorWithoutPriorities extends
		CellLikeSimulator {

	private static final long serialVersionUID = 290211051490843577L;
	private static final Comparador comparador = new Comparador();
	protected static final CheckRule noDivision = new NoDivision();
	
	public NonDeterministicTransitionSimulatorWithoutPriorities(Psystem psystem) {
		super(psystem);
	}

	@Override
	protected void microStepSelectRules(ChangeableMembrane m,
			ChangeableMembrane temp) {	
		
		int n=1;
		List<IRule>aux = new ArrayList<IRule>();
	
		for (int i=n;i>0;i--)
		{
			Iterator<IRule> it = getPsystem().getRules().iterator(
					temp.getLabel(),
					temp.getLabelObj().getEnvironmentID(),
					temp.getCharge(),true);
			
			aux.clear();
		
			while (it.hasNext())
				aux.add(it.next());
			
			//RulesSet.sortByPriority(aux);
			/*if (RandomNumbersGenerator.getInstance().nextInt(10)!=0)
			{
				Collections.sort(aux,comparador);
			}*/
			
			boolean possibleApplications = true;
			int rulesSize = aux.size();
			
			while (possibleApplications) {	
				//long applied = 0;
				//it = new ShuffleIterator<IRule>(aux);
				//it = aux.iterator();
				
				//while (it.hasNext()) {
					//IRule r = it.next();
					
				int randomIndex = RandomNumbersGenerator.getInstance().nextInt(rulesSize);
				IRule r = aux.get(randomIndex);
			    if (!r.dissolves() || r.dissolves() && i==1)
			    {
			    	long max = r.countExecutions(temp);
			    	long count = max>0?1:0;
			    	//applied += count;
					/*if (!(r instanceof IPriorityRule) && count>0 && i!=1)
					{
						
						count = RandomNumbersGenerator.getInstance().nextLong(count);
						
					}*/
				
					if (count > 0) {
						selectRule(r, m, count);
						removeLeftHandRuleObjects(temp, r, count);
					} else {
						aux.remove(randomIndex);
						rulesSize--;
						if (rulesSize==0)
							possibleApplications = false;
					}
			    }
				
				//if (applied == 0)
				//	possibleApplications = false;
			}	
			
		}
	}
	
	static class Comparador implements Comparator<IRule>
	{

		@Override
		public int compare(IRule arg0, IRule arg1) {
			// TODO Auto-generated method stub
			if (arg0.dissolves() && arg1.dissolves())
			return 0;
			if (!arg0.dissolves() && !arg1.dissolves())
			return 0;
			if (arg0.dissolves() && !arg1.dissolves())
			return 1;
			return -1;
		}
		
	}

}
