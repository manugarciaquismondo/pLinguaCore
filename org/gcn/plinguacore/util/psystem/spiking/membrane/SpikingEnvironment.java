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

package org.gcn.plinguacore.util.psystem.spiking.membrane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gcn.plinguacore.util.HashInfiniteMultiSet;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.rule.spiking.SpikingRule;


// class SpikingEnvironment

public class SpikingEnvironment extends SpikingMembrane {

	
	private static final long serialVersionUID = 5585656570644646361L;
	private Map<Integer, List<Short>> binarySpikeTrain = null;
	private Map<Integer, List<Long>> naturalSpikeTrain = null;
	private Map<Long,Long> inputSequence = null;
	private long stepsTaken = 0L;
	

	public SpikingEnvironment(String label,
			SpikingMembraneStructure structure) {
		super(label,new HashInfiniteMultiSet<String>(), structure, false);
		binarySpikeTrain = new HashMap<Integer, List<Short>>();
		naturalSpikeTrain = new HashMap<Integer, List<Long>>();
		inputSequence = new HashMap<Long,Long>();
		stepsTaken = 0L;

		

		// TODO Auto-generated constructor stub
	}
	
	public Map<Integer, List<Short>> getBinarySpikeTrain()
	{
		return binarySpikeTrain;
	}
	
	
	public Map<Integer, List<Long>> getNaturalSpikeTrain()
	{
		return naturalSpikeTrain;
	}
	
	
	protected void initializeSpikeTrain(SpikingMembrane o)
	{
		binarySpikeTrain.put(o.getId(), new ArrayList<Short>());
		naturalSpikeTrain.put(o.getId(), new ArrayList<Long>());
	}
	
	protected void destroySpikeTrain(SpikingMembrane o)
	{
		binarySpikeTrain.remove(o.getId());
		naturalSpikeTrain.remove(o.getId());
		
	}
		
	public void cloneSpikeTran(SpikingMembrane source, SpikingMembrane target)
	{
		int sid = source.getId();
		int tid = target.getId();
		
		List<Short>	bst = binarySpikeTrain.get(sid);
		List<Long>	nst = naturalSpikeTrain.get(sid);
		
		if(bst == null || nst == null)
			;	// do nothing
		else
		{
			List<Short> bstClon = (List<Short>) ((ArrayList<Short>) bst).clone();
			List<Long> nstClon = (List<Long>) ((ArrayList<Long>) nst).clone();
			
			binarySpikeTrain.put(tid, bstClon);
			naturalSpikeTrain.put(tid, nstClon);
		}
		

	}
	
	public Map<Long,Long> getInputSequence()
	{
		return inputSequence;
	}
	
	public void setInputSequence (Map<Long,Long> inputSequence)
	{
		this.inputSequence = inputSequence;
	}
	
	public long getStepsTaken()
	{
		return stepsTaken;	
	}
	
	public long increaseStepsTaken()
	{
		stepsTaken++;
		return stepsTaken;
	}
	
	public long setStepsTaken(long stepsTaken)
	{
		this.stepsTaken = stepsTaken;
		return this.stepsTaken;
	}
	
	public long getInputSequenceValue(long step)
	{
		long result = 0L;
		
		if(step < 0L)
			return result;
		
		if(inputSequence.isEmpty())
			return result;
		
		if(inputSequence.containsKey(step))
			result = inputSequence.get(step);
		
		return result;
	}
	
	public void addToBinarySpikeTrain(int memId, int value)
	{
		List<Short> result = binarySpikeTrain.get(memId);
		result.add(new Short((short) value));
		binarySpikeTrain.put(memId, result);
	}
	
	public void addToNaturalSpikeTrain(int memId, long value)
	{
		List<Long> result = naturalSpikeTrain.get(memId);
		result.add(new Long(value));
		naturalSpikeTrain.put(memId, result);
	}
	
	@Override
	public void dissolve() throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public ChangeableMembrane divide() throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}



}
