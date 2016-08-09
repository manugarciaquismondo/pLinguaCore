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
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

import org.gcn.plinguacore.util.Pair;
import org.gcn.plinguacore.util.psystem.spiking.SpikingConstants;


public class FunctionalAstrocyte extends Astrocyte {

	
	private List<Pair<Integer,Integer>> orderedArcs = null;
	private boolean updatePotential = true;
	private boolean controlAsOperand = false;
	
	// this method is called from the parser

	public FunctionalAstrocyte(String label, 
			List<Pair<String, String>> arcs, List<Pair<String,String>> ctrlArcs,
			boolean controlAsOperand,
			SortedSet<Long> lThresholds, List<String> lFunctionNames,
			Long potential, boolean updatePotential, SpikingMembraneStructure structure) {
		
		super(label, arcs, lThresholds, lFunctionNames, potential, "functional", structure);
		
		structure.addAstrocyte(this, ctrlArcs, true);
		
		this.orderedArcs = structure.getAstrocyteArcs(label);
		this.updatePotential = updatePotential;
		this.controlAsOperand = controlAsOperand;

		// TODO Auto-generated constructor stub
	}
	
	public FunctionalAstrocyte(String label,
			SpikingMembraneStructure structure,
			FunctionalAstrocyte a) {
		// TODO Auto-generated constructor stub
		super(label,structure,a);
		
		this.orderedArcs = (List<Pair<Integer, Integer>>) ((ArrayList<Pair<Integer,Integer>>)a.orderedArcs).clone();
		this.updatePotential = new Boolean(a.updatePotential);
		this.controlAsOperand = new Boolean(a.controlAsOperand);
	}

	public List<Pair<Integer,Integer>> getCtrlArcs()
	{
		if(getStructure() == null)
			return new ArrayList<Pair<Integer,Integer>>();
		else
			return getStructure().getAstrocyteCtrlArcs(getLabel());
	}
	
	public boolean flush()
	{
		
		Long spikes = getSpikes();
		this.clearSpikes();
		
		Long thresholdSelector = spikes + getPotential();
		
		if(updatePotential)
			setPotential(spikes);
		
		int indexOfFunction = -1;
		
		SortedSet<Long> set = this.getLThresholds();
		int sizeSet = set.size();

		if(sizeSet > 0)
		{
			if(thresholdSelector <= set.first())
				indexOfFunction = 0;
			else if(thresholdSelector >= set.last())
				indexOfFunction = sizeSet - 1;
			else
				indexOfFunction = getIndex(set,thresholdSelector);
		}
		
		if(indexOfFunction < 0)
			return true; // in this case, we do nothing
		
		Long fResult = 0L;
					
		String fName = this.getLFunctionNames().get(indexOfFunction);
			
		EvaluableFunction ef = getStructure().getAstrocyteFunction(fName);
		
		List<Pair<Integer,Integer>> listArcs = this.orderedArcs;
		
		if(this.controlAsOperand)
		{
			if(fName.equals("pol()"))
				fResult = thresholdSelector;
			else if(fName.equals("sub()"))
				fResult = thresholdSelector;
			else
				fResult = evaluateSingleParamFunction(ef,thresholdSelector);
		}
		else
		{
			// we calculate the result for the selected function

			if(fName.equals("pol()"))
				fResult = evaluatePolynomio(listArcs);
			else if(fName.equals("sub()"))
				fResult = evaluateSubstraction(listArcs);
			else
				fResult = evaluateGenericFunction(ef,listArcs);
			
		}

		// we update the result for the output arc and inhibit the other operand arcs
		
		return updateArcs(fResult);
		
	}
		
	private boolean updateArcs(long fResult)
	{
		boolean result = true;
		
		List<Pair<Integer,Integer>> lArcs = getArcs();
		
		int i = 0;
		
		int size = lArcs.size();
		
		SpikingMembraneStructure structure = getStructure();
		
		Iterator<Pair<Integer,Integer>> it = lArcs.iterator();
		
		while(it.hasNext())
		{
			Pair<Integer,Integer> p = (Pair<Integer,Integer>) it.next();
			ArcInfo aInfo = structure.getArcInfo(p.getFirst(), p.getSecond());
			boolean inhibitArc = aInfo.getInhibited() || (i == size - 1);
			
			if(inhibitArc)
			{
				aInfo.setInhibited(true);
				aInfo.setSpikesOutput(0L);
				aInfo.setObject(SpikingConstants.spikeSymbol);
			}
			else
			{
				aInfo.setInhibited(false);
				aInfo.setSpikesOutput(aInfo.getSpikesOutput() + fResult);
				aInfo.setObject(SpikingConstants.spikeSymbol);
			}
			
			i++;
			
		}
		
		return result;
	}
		
	
	private int getIndex(SortedSet<Long> set, Long thresholdSelector)
	{
		boolean found = false;		
		int pos = -1;
		
		Iterator<Long> it = set.iterator();
		
		while(it.hasNext() && !found)
		{
			pos++;
			
			Long element = (Long) it.next();
			
			if(element > thresholdSelector)
			{
				found = true;
				pos--;
			}
			else if(element > thresholdSelector)
				found = true;
		}
		
		return pos;

	}
	
	private Long evaluatePolynomio(List<Pair<Integer,Integer>> listArcs)
	{
		Long result = 0L;
		
		int numParams = listArcs.size();
		
		if(numParams > 0)
		{

			Pair<Integer,Integer> p =  listArcs.get(0);
			ArcInfo aInfo = getStructure().getArcInfo(p.getFirst(), p.getSecond());
			result = aInfo.getSpikesInput(); 
			
			if(numParams > 1)
			{
			
				p =  listArcs.get(numParams - 1);
				aInfo = getStructure().getArcInfo(p.getFirst(), p.getSecond());
				Long variable = aInfo.getSpikesInput();
				
				int i = 1;
						
				while(i < numParams - 1)
				{
					p =  listArcs.get(i);
					aInfo = getStructure().getArcInfo(p.getFirst(), p.getSecond());
					Long param = aInfo.getSpikesInput();
					result += new Double(param * Math.pow(variable, i)).longValue();
						
				}
			
			}
		}
				
		return result;
	}
	
	private Long evaluateSubstraction(List<Pair<Integer,Integer>> listArcs)
	{
		Long result = 0L;
		
		int numParams = listArcs.size();
		
		if(numParams < 2)
			return result;

		int i = 0;
		
		Pair<Integer,Integer> p =  listArcs.get(i);
		ArcInfo aInfo = getStructure().getArcInfo(p.getFirst(), p.getSecond());
		Long param = aInfo.getSpikesInput();
	
		result = param;
		
		i++;
		
		boolean fin = false;
		
		while(i < numParams - 1 && !fin)
		{
			p =  listArcs.get(i);
			aInfo = getStructure().getArcInfo(p.getFirst(), p.getSecond());
			param = aInfo.getSpikesInput();
			result -= param;
			
			if(param <= 0L)
			{
				param = 0L;
				fin = true;
			}
				
		}
		
		return result;
	}
	
	private Long evaluateGenericFunction(EvaluableFunction ef, List<Pair<Integer,Integer>> listArcs)
	{
		Long result = 0L;
		
		List<Object> params = new ArrayList<Object>();
				
		int numParams = ef.getNumParams();
		
		int i = 0;
				
		while(i < numParams)
		{
			Pair<Integer,Integer> p =  listArcs.get(i);
			ArcInfo aInfo = getStructure().getArcInfo(p.getFirst(), p.getSecond());
			params.add(aInfo.getSpikesInput());	
			
			i++;
		}

		result = getStructure().evalFunction(ef, params);
		
		return result;
	}

	private Long evaluateSingleParamFunction(EvaluableFunction ef, Long param)
	{
		Long result = 0L;
		
		List<Object> params = new ArrayList<Object>();
		
		int numParams = ef.getNumParams();
		
		if(numParams != 1)
			return result;
		
		params.add(param);

		result = getStructure().evalFunction(ef, params);
		
		return result;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + "\n" + "ctrlArcs=" + this.getCtrlArcs().toString();
		
	}
	
}


