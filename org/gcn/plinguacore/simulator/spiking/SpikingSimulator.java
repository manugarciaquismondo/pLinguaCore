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

package org.gcn.plinguacore.simulator.spiking;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gcn.plinguacore.simulator.AbstractSelectionExecutionSimulator;
import org.gcn.plinguacore.util.Pair;
import org.gcn.plinguacore.util.RandomNumbersGenerator;
import org.gcn.plinguacore.util.psystem.Configuration;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.spiking.SpikingRule;
import org.gcn.plinguacore.util.psystem.spiking.SpikingConstants;
import org.gcn.plinguacore.util.psystem.spiking.membrane.ArcInfo;
import org.gcn.plinguacore.util.psystem.spiking.membrane.Astrocyte;
import org.gcn.plinguacore.util.psystem.spiking.membrane.SpikingEnvironment;
import org.gcn.plinguacore.util.psystem.spiking.membrane.SpikingMembrane;
import org.gcn.plinguacore.util.psystem.spiking.membrane.SpikingMembraneStructure;

import java.util.ArrayList;

public class SpikingSimulator extends AbstractSelectionExecutionSimulator {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6170733461524439912L;
	/**
	 * 
	 */

	
	protected int sequential = 0;	// 0: false, 1: normal sequen, 2: max pseudo-seq, 3: max seq, 4: min pseudo-seq, 5: min seq
	protected int asynch = 0; 		// 0: false, 1: normal asynch, 2: limited (bounded) asynchronous mode, 3: local synch mode
	protected Map<String, Long> validConfiguration = null;
	protected long bound = 0;
	protected long executionStep = 0L;	// Only usable when having and output membrane at least otw it returns zero.
	
	protected Map<Integer,SpikingMembrane> toFireNotFiltered = null;
	protected Map<Integer,SpikingMembrane> toFireFiltered = null;
	protected Map<Integer,SpikingMembrane> justOpenNow = null;
	
	protected List<SpikingMembrane> division = null;
	protected List<SpikingMembrane> budding = null;
	protected List<SpikingMembrane> spiking = null;

	
	protected boolean showBinarySequence = false;
	protected List<Object> showNaturalSequence = null;
	
	protected boolean showSummatories = false;
	
	protected boolean writeToFile = true;
	private static boolean DEBUG = false;
	
		
	public SpikingSimulator(Psystem psystem) {
		super(psystem);

		// TODO Auto-generated constructor stub
	} 

	/*
	This simulator executes (basically) the following simulation algorithm:
	
	boolean step()
	{
		if(firstTime)
		{
			printInfo(true);
			firstTime = false;
		}
		
		microStepInit();
		microStepSelectRules();
				
		if (hasSelectedRules()) {
			microStepExecuteRules();
			currentConfig.setNumber(currentConfig.getNumber()+1);
		}
		
		printInfo(false);
		
		return hasSelectedRules();
	}
	 */
	
	


	@Override
	protected void microStepInit() {
		// TODO Auto-generated method stub
		
		super.microStepInit();
		
		SpikingEnvironment env = ((SpikingMembraneStructure) currentConfig.getMembraneStructure()).getEnvironmentMembrane();
		executionStep = env.increaseStepsTaken();
		
		if(this.executionStep == 1L)
		{
			// we synch the parameters from the membrane structure to the p system simulator
			
			SpikingMembraneStructure s = 
				(SpikingMembraneStructure) currentConfig.getMembraneStructure();
			
			this.showBinarySequence = s.getShowBinarySequence();
			this.showNaturalSequence = (List<Object>) s.getShowNaturalSequence();
			this.showSummatories = s.getShowSummatories();
			this.setSequential(s.getSequentialMode());
			this.setAsynch(s.getAsynchMode());
			this.setValidConfiguration(s.getValidConfiguration());
			this.setBound(s.getBound());
			env.restartBoundTimer();
						
			// we initialize the data structures
			
			toFireNotFiltered = new HashMap<Integer,SpikingMembrane>();
			toFireFiltered = new HashMap<Integer,SpikingMembrane>();
			justOpenNow = new HashMap<Integer,SpikingMembrane>(); 

			division = new ArrayList<SpikingMembrane>();
			budding = new ArrayList<SpikingMembrane>();
			spiking = new ArrayList<SpikingMembrane>();
			
		}
		else
		{
			// we clear the data structures
			
			toFireNotFiltered.clear();
			toFireFiltered.clear();
			justOpenNow.clear();

			division.clear();
			budding.clear();
			spiking.clear();
			
			// we show the results of the previous step
			
			microStepPrintResults();
		}
		
		
		if(writeToFile)
		{
			PrintStream sout = System.out;
			PrintStream serr = System.err;
					
			try 
			{

				String filename = System.getProperty("user.dir") + "/steps/step-" + executionStep + ".txt";
				OutputStream output = new FileOutputStream(filename);
				PrintStream printOut = new PrintStream(output);
				
				System.setOut(printOut);
				System.setErr(printOut);     
			} 
			catch (Exception e)
			{
				System.setOut(sout);
				System.setErr(serr);
				writeToFile = false;
			}			
		}
		
	}
	
	@Override
	protected void microStepSelectRules(Configuration cnf, Configuration tmpCnf) {
		// TODO Auto-generated method stub

		super.microStepSelectRules(cnf, tmpCnf);	// call the method in the parent class, which in turn calls to microStepSelectRules(ChangeableMembrane m,ChangeableMembrane temp) in this class

		this.doSequentialFilter();					// performs sequential filter
		this.doAsynchFilter();						// performs asych filter		
		this.disableFilteredNeurons();				// disables non-surviving neurons after filters are applied (marked as open and skipped)

		this.removeLeftHandRuleObjects();			// remove the left hand objects of the neurons (firing/forgetting) / clear the neurons (budding/division)
													// only for the neurons which selected rule starts its execution in this step
		
		this.classifySelectedRules();				// we classify the rules to end their execution in the current step
		
		if(!hasSelectedRules())
			microStepPrintFinalResults();
		
	}

	@Override
	protected void microStepSelectRules(ChangeableMembrane m,
			ChangeableMembrane temp) {

		SpikingMembrane s = (SpikingMembrane) m;
		SpikingMembraneStructure structure = (SpikingMembraneStructure) currentConfig.getMembraneStructure();

		
		if (s.isBoundTimerZero() && s.isFiring()) // If the bound timer is zero and neuron is firing (it is closed and it is not performing budding/division) then no other rule can be selected 
		{	
			s.decreaseStepsToOpen(); // we must decrease the stepsToOpen counter
			
			if(s.isFiring())	// if it is still waiting to become open
				; // do nothing
			else
				justOpenNow.put(s.getId(), s);
			
			SpikingRule rule = s.getSelectedRule();
			
			super.selectRule(rule, s, 1);	// we add the rule to selectedRules
		}
		else
		{	
			
			// Now we proceed to select a new rule
			
			s.setMembraneOpen();	// First we clean the neuron state
			s.setSkipped(false);	// And also mark it as not skipped
			
			if(!s.hasToCheckApplicability())	// If we do not have to check the applicability, we are done
				return;
			else
			{
			
				// To find a rule, we iterate over the RuleSet, and non-deterministically choose an applicable rule
				
				ArrayList<SpikingRule> activeRules = new ArrayList<SpikingRule>();

				Iterator<IRule> it = this.getPsystem().getRules().iterator(s.getLabel(), s.getCharge());

				while (it.hasNext())	// we iterate over the RuleSet
				{
					SpikingRule frule = (SpikingRule) it.next();
					
					if (frule.canBeExecuted(s,structure))	// we check the applicability
							activeRules.add(frule);			// we pick up applicable rules
				}
				
				if(activeRules.isEmpty())	// if there are no rules, we are done
				{
					s.doNotCheckApplicability();	// since no rule is going to be executed, in principle we do not have to check applicability in the next step
				}
				else								// if there are applicable rules, we non-deterministically choose one
				{
					int size = activeRules.size();
					
					SpikingRule selectedRule = null;
					
					if(size == 1)
						selectedRule = activeRules.get(0);
					else
					{
						RandomNumbersGenerator rgenerator = RandomNumbersGenerator.getInstance();
						
						int selected = rgenerator.nextInt(activeRules.size());
						selectedRule = activeRules.get(selected);
					}
					
					s.setSelectedRule(selectedRule);
					
					if(this.asynch == 2)		// if we are operating in bounded mode, we have to restart/update the neuron bound timer
					{
						if(s.isBoundTimerZero())	// if the bound timer got to zero, we have to restart the bound before decreasing it.
						{
							s.restartBoundTimer();	// we restart the bound timer
							s.decreaseBoundTimer(); // after restarting the bound timer, we must decrease it for the first time, as neuron should fire in [t,t+b-1] (b >= 2)
						}
						else
						{
							s.decreaseBoundTimer(); // we must decrease it for the first time, as it should fire in [t,t+b-1] (b >= 2)
						}
						 
					}
					
					toFireNotFiltered.put(s.getId(), s);
					toFireFiltered.put(s.getId(), s);
					super.selectRule(selectedRule, s, 1); // we add the rule to selectedRules
					
				}
					
			}
			
		}

	}
	
	private void doSequentialFilter()
	{
		if(toFireFiltered.isEmpty())	// if there is no selected rules, there is nothing to do
			return;
		
		if (this.getSequential() == 0) 	// if we are in parallel mode, there is nothing to do
			return;
		
		// this method expect for the rule to be executable (rules in execution should not be considered)
		
		Map<Integer,SpikingMembrane> filtered	 = new HashMap<Integer,SpikingMembrane>();
		
		List<SpikingMembrane> toFireList = new ArrayList<SpikingMembrane>();
		
		long max = Long.MIN_VALUE;
		long min = Long.MAX_VALUE;
		
		Iterator<SpikingMembrane> it = toFireFiltered.values().iterator();
		
		while(it.hasNext())
		{
			SpikingMembrane s = it.next();
						
			toFireList.add(s);
			
			long spikeSize = s.getMembraneSpikingStringSize();
			
			if(spikeSize > max)
				max = spikeSize;
			
			if(spikeSize < min)
				min = spikeSize;

		}
		

		decideSequential(min,max,toFireList,filtered);
		
		toFireFiltered.clear();
		toFireFiltered.putAll(filtered);
			
	}
	
	private void decideSequential(long min, long max, List<SpikingMembrane> toFireList, Map<Integer, SpikingMembrane> filtered)
	{
		// 0: false, 1: normal sequen, 2: max pseudo-seq, 3: max seq, 4: min pseudo-seq, 5: min seq
		
		// this method expect for the rule to be executable (rules in execution should not be considered)
				
		int sequentialMode = this.getSequential();
	
		if(sequentialMode == 0)		// if we are in parallel mode, there is nothing to do
			return;
		
		if(sequentialMode == 1)		// normal sequential
		{
			RandomNumbersGenerator rgenerator = RandomNumbersGenerator.getInstance();
			
			int selected = rgenerator.nextInt(toFireList.size());
			
			SpikingMembrane s = toFireList.get(selected);
			
			Integer memId = s.getId();
			
			filtered.put(memId,s);
		}
		else if(sequentialMode == 2)	// max pseudo-sequential
		{
			
			Iterator <SpikingMembrane> it = toFireList.iterator();
			
			while(it.hasNext())
			{
				SpikingMembrane s = it.next();
				
				Integer memId = s.getId();
				
				if(s.getMembraneSpikingStringSize() == max)
					filtered.put(memId,s);
					
			}
			
		}
		else if(sequentialMode == 3)	// max sequential
		{
			List<SpikingMembrane> toFireListAux = new ArrayList<SpikingMembrane>();
			
			Iterator <SpikingMembrane> it = toFireList.iterator();
			
			while(it.hasNext())
			{
				SpikingMembrane s = it.next();
				
				if(s.getMembraneSpikingStringSize() == max)
					toFireListAux.add(s);
					
			}
			
			RandomNumbersGenerator rgenerator = RandomNumbersGenerator.getInstance();
			
			int selected = rgenerator.nextInt(toFireListAux.size());
			
			SpikingMembrane s = toFireListAux.get(selected);
			
			Integer memId = s.getId();
			
			filtered.put(memId,s);

		}
		else if(sequentialMode == 4)	// min pseudo-sequential
		{
			
			Iterator <SpikingMembrane> it = toFireList.iterator();
			
			while(it.hasNext())
			{
				SpikingMembrane s = it.next();
				
				Integer memId = s.getId();
				
				if(s.getMembraneSpikingStringSize() == min)
					filtered.put(memId,s);
					
			}
			
		}
		else if(sequentialMode == 5)	// min sequential
		{
			List<SpikingMembrane> toFireListAux = new ArrayList<SpikingMembrane>();
			
			Iterator <SpikingMembrane> it = toFireList.iterator();
			
			while(it.hasNext())
			{
				SpikingMembrane s = it.next();
				
				if(s.getMembraneSpikingStringSize() == min)
					toFireListAux.add(s);
					
			}
			
			RandomNumbersGenerator rgenerator = RandomNumbersGenerator.getInstance();
			
			int selected = rgenerator.nextInt(toFireListAux.size());
			
			SpikingMembrane s = toFireListAux.get(selected);
			
			Integer memId = s.getId();
			
			filtered.put(memId,s);

		}
		
	}
	
	private void doAsynchFilter()
	{
		if(toFireFiltered.isEmpty())	// if there is no selected rules, there is nothing to do
			return;
		
		if(this.asynch == 0)	// if we are in synchronous mode, there is nothing to do
			return;

		Map<Integer,SpikingMembrane> filtered = new HashMap<Integer,SpikingMembrane>();
		
		Iterator<SpikingMembrane> it = toFireFiltered.values().iterator();

		while (it.hasNext())
		{
			
			SpikingMembrane s = (SpikingMembrane) it.next();
						
			if(this.decideAsynch(s))
				filtered.put(s.getId(),s);
		}
		
		if(this.asynch == 3)	// asynch with local synch
		{
			Map<Integer,SpikingMembrane> filteredAux = new HashMap<Integer,SpikingMembrane>();  
			
			Set<Integer> locProcessed = new HashSet<Integer>();
			
			for(Integer elem:filtered.keySet())
				addLocMembranes(elem,locProcessed,filteredAux);
			
			filtered = filteredAux;
		}
		
		toFireFiltered.clear();
		toFireFiltered.putAll(filtered);


		
	}
		
	private boolean decideAsynch(SpikingMembrane s)
	{
		
		// this method expect for the rule to be executable (rules in execution should not be considered)
		
		boolean result = false;
				
		if(this.asynch == 0)
			result = true;
		
		else if(this.asynch == 1)	// normal asynch
		{
			RandomNumbersGenerator rgenerator = RandomNumbersGenerator
					.getInstance();
			
			result = (rgenerator.nextInt(2) == 0);
		}
		else if(this.asynch == 2)	// bounded asynch
		{
			
			if(s.isBoundTimerZero())
				result = true;
			else
			{
				RandomNumbersGenerator rgenerator = RandomNumbersGenerator
						.getInstance();
								
				result = (rgenerator.nextInt(2) == 0);
				
				if(result)
					s.setBoundTimerToZero();	// we artificially simulate that the timer has reach zero
			}
			
		}
		else if(this.asynch == 3)	// asynch with local synch
		{
			
			RandomNumbersGenerator rgenerator = RandomNumbersGenerator
					.getInstance();
			
			result = (rgenerator.nextInt(2) == 0);
		}

		
		return result;
	}
	
	private void addLocMembranes(Integer memId, Set<Integer> locProcessed, Map<Integer,SpikingMembrane> filteredAux)
	{
				
		if(locProcessed.contains(memId))
			return;
		else
		{
			locProcessed.add(memId);
						
			if(toFireNotFiltered.containsKey(memId))
			{

				// filteredAux is filled with neurons from filtered (that are always in toFire) plus neurons from the locSet that are also in toFireNotFiltered
				
				SpikingMembrane s = toFireNotFiltered.get(memId);
				
				s.setBoundTimerToZero();
			
				filteredAux.put(memId, s);
				
				SpikingMembraneStructure structure = (SpikingMembraneStructure) currentConfig.getMembraneStructure();
				
				Set<Integer> set = structure.getLocFlatSet(memId);
					
				for(Integer elem:set)
					addLocMembranes(elem,locProcessed,filteredAux);
			}

		}
	}
	
	private void disableFilteredNeurons()
	{
		if(this.sequential == 0 && this.asynch == 0)	// if we are in parallel / synchronous mode, there is nothing to disable
			return;
		
		Iterator<SpikingMembrane> it = toFireNotFiltered.values().iterator();
		
		while(it.hasNext())
		{
			SpikingMembrane s = it.next();
			int memId = s.getId();
			
			if(!toFireFiltered.containsKey(memId))
			{
				s.setMembraneOpen();
				s.setSkipped(true);
			}
				
			
		}
	}
	
	private void classifySelectedRules()
	{
		// We are going to prepare the spiking, budding, division
		
		// for firing/spiking rules that go to spiking, we have to consider:
				// (a) rules that start their execution now:
				//		(a1) if they are closed, they do not emit spike	--> we do not consider them
				//		(a2) if they are open, they emit spike only if they are firing rules
				// (b) rules that have become open now: they emit spike only if they are firing rules
		
		Iterator<SpikingMembrane> it = toFireFiltered.values().iterator();

		while (it.hasNext())
		{

			SpikingMembrane s = it.next();
			SpikingRule rule = s.getSelectedRule();
						
			// rules that start their execution now and are open
			if((rule.isFiringRule() || rule.isForgettingRule()) && s.isOpen())
				spiking.add(s);
			else if(rule.isBuddingRule())
				budding.add(s);
			else if(rule.isDivisionRule())
				division.add(s);
							
		}
		
		// rules that have become open now (all of them are firing/forgetting rules)
		
		spiking.addAll(justOpenNow.values());
		
		/*
		
		getInfoChannel().println(
				"···············································");
		getInfoChannel().println("toFire: "+ toFire);
		getInfoChannel().println();
		getInfoChannel().println("waitingToOpen: "+ waitingToOpen);
		getInfoChannel().println();
		getInfoChannel().println("justOpenNow: "+ justOpenNow);
		getInfoChannel().println(		
				"···············································");
		getInfoChannel().println();
		
		*/
	}
	
	@Override
	public void microStepExecuteRules() {
		// TODO Auto-generated method stub
						
		microStepExecuteBuddingDivisionRules();
		microStepExecuteSpikingRules();
		microStepExecuteInputSequence();
		microStepExecuteOutputMembranes();
		
	}


	
	private void microStepExecuteBuddingDivisionRules() {
		
		SpikingMembraneStructure structure = (SpikingMembraneStructure) currentConfig.getMembraneStructure();

		SpikingEnvironment env = (SpikingEnvironment) structure.getEnvironmentMembrane();
								
		ArrayList<SpikingMembrane>
			buddyList = new ArrayList<SpikingMembrane>(); 

		ArrayList<Pair<SpikingMembrane,SpikingMembrane>> 
			divisionList = new ArrayList<Pair<SpikingMembrane, SpikingMembrane>>();
		
		Iterator<SpikingMembrane> it = null;

		// 1. We execute division phase one
		
		it = division.iterator();

		while (it.hasNext())
		{
			
			SpikingMembrane s = it.next();
			SpikingRule rule  = s.getSelectedRule();
			boolean isOutput  = structure.isOutput(s);

			printDebug();
			printDebug("Trying execution for...");
			printDebug(s);
			printDebug("Executing rule...");
			printDebug(rule);
		
			rule.executeSafeBuddingDivisionPhaseOne(s, currentConfig, buddyList, divisionList);
			
			if (isOutput)
			{
				
				SpikingMembrane buddy1 = (SpikingMembrane) divisionList.get(buddyList.size() - 1).getFirst();
				env.addToBinarySpikeTrain(buddy1.getId(), 0);
				
				SpikingMembrane buddy2 = (SpikingMembrane) divisionList.get(buddyList.size() - 1).getSecond();
				env.addToBinarySpikeTrain(buddy2.getId(), 0);
								
			}

		}	
		
		// 2. We execute budding phase one

		it = budding.iterator();

		while (it.hasNext())
		{
			SpikingMembrane s = it.next();
			SpikingRule rule  = s.getSelectedRule();
			boolean isOutput  = structure.isOutput(s);

			printDebug();
			printDebug("Trying execution for...");
			printDebug(s);
			printDebug("Executing rule...");
			printDebug(rule);
			
			rule.executeSafeBuddingDivisionPhaseOne(s, currentConfig, buddyList, divisionList);
						
			if (isOutput)
			{
				SpikingMembrane buddy = (SpikingMembrane) buddyList.get(buddyList.size() - 1);
				env.addToBinarySpikeTrain(buddy.getId(), 0);		
			}

		}	
		
		// At the end of phase one, neurons are created, the synapses are inherited and the output spike trains are updated.
		// Let's continue with phase two, iterating over divisionList and buddyList.

		boolean isBudding;
		
		// 3. We execute division phase two
		
		isBudding = false;
		
		Iterator<Pair<SpikingMembrane,SpikingMembrane>> itdivision = divisionList.iterator();
		
		while(itdivision.hasNext())
		{
			Pair<SpikingMembrane,SpikingMembrane> pair = (Pair<SpikingMembrane,SpikingMembrane>) itdivision.next();
			
			SpikingMembrane s1 = pair.getFirst();
			SpikingMembrane s2 = pair.getSecond();
			SpikingRule.executeSafeBuddingDivisionPhaseTwo(s1, s2, currentConfig, isBudding);			
			
		}
		
		// 4. We execute budding phase two
		
		isBudding = true;
		
		Iterator<SpikingMembrane> itbuddy = buddyList.iterator();
		
		while(itbuddy.hasNext())
		{
			SpikingMembrane s = (SpikingMembrane) itbuddy.next();
			SpikingRule.executeSafeBuddingDivisionPhaseTwo(s, null, currentConfig, isBudding);
		}
		

	}
	
	private void microStepExecuteSpikingRules()
	{
		Set<Pair<Integer,Integer>> affectedArcs = new HashSet<Pair<Integer,Integer>>();
		
		fireSpikesToArcs(affectedArcs);
		loadFlushAstrocytes(affectedArcs);
		fireSpikesToNeurons(affectedArcs);
	}
	
	private void fireSpikesToArcs(Set<Pair<Integer,Integer>> affectedArcs)
	{
		// TODO Auto-generated method stub
		
		Iterator<SpikingMembrane> it = spiking.iterator();

		while (it.hasNext()) {

			SpikingMembrane s = it.next();
			SpikingRule rule = s.getSelectedRule();						
			
			printDebug();
			printDebug("Trying to send spikes for neuron...");
			printDebug(s);
			printDebug("With respect to rule...");
			printDebug(rule);
			
			rule.executeSafeSpiking(s, currentConfig, affectedArcs);	// this method load spikes in the arc
		}
		
	}
	
	private void loadFlushAstrocytes(Set<Pair<Integer,Integer>> affectedArcs)
	{
		// TODO Auto-generated method stub
		
		SpikingMembraneStructure structure = ((SpikingMembraneStructure) currentConfig.getMembraneStructure());
		
		structure.loadAstrocytes(affectedArcs);		// this method loads astrocytes from arcs
		
		structure.flushAstrocytes(affectedArcs);	// this method flushes astrocytes from arcs (possibly modifying the arcs traffic)
		
	}
	
	private void fireSpikesToNeurons(Set<Pair<Integer,Integer>> affectedArcs)
	{
		SpikingMembraneStructure structure = ((SpikingMembraneStructure) currentConfig.getMembraneStructure());
		
		SpikingEnvironment env = structure.getEnvironmentMembrane();
		
		Iterator<Pair<Integer,Integer>> itpairs = affectedArcs.iterator();
		
		while(itpairs.hasNext())
		{
			Pair<Integer,Integer> p = (Pair<Integer,Integer>) itpairs.next();
			
			ArcInfo aInfo = structure.getArcInfo(p.getFirst(), p.getSecond());
			
			Pair<Long,String> arcInfoContent = aInfo.restartArcState();
			
			Long spikes = arcInfoContent.getFirst();
			String object = arcInfoContent.getSecond();

			SpikingMembrane source = structure.getCellById(p.getFirst());
			SpikingMembrane target = structure.getCellById(p.getSecond());
			
			boolean isOutput = structure.isOutput(source);
						
			if(target.isOpen())
				target.addSpikes(object,spikes);	
			
			if(isOutput)
			{
				if(spikes > 0L)
				{
					env.addToBinarySpikeTrain(source.getId(), 1);
					env.addToNaturalSpikeTrain(source.getId(),this.executionStep);
				}
				else
				{
					env.addToBinarySpikeTrain(source.getId(), 0);
				}
			}
		
		}
	}
	
	
	private void microStepExecuteInputSequence()
	{
		SpikingMembraneStructure structure = (SpikingMembraneStructure) currentConfig.getMembraneStructure();
		
		SpikingEnvironment env = (SpikingEnvironment) structure.getEnvironmentMembrane();
		
		SpikingMembrane input = structure.getInputMembrane();
		
		if(input != null)
		{

	
			printDebug();
			printDebug("********************************************************************");
			printDebug("Input Membrane: " + input);
			printDebug("Step: " + this.executionStep);
			printDebug("Trying to receive input spikes from the input spike train...");
			
			if(input.isClosed())
			{
				printDebug("membrane closed, not doing anything");
			}
			else
			{
				long spikes = env.getInputSequenceValue(this.executionStep);
				
				if(spikes == 0L)
					printDebug("zero input spikes for this step, not doing anything");
				else
					SpikingRule.executeSafeInputSpiking(input, spikes);
			}
		
			printDebug("********************************************************************");
		}
	
	}
	
	private void microStepExecuteOutputMembranes()
	{
		
		SpikingMembraneStructure structure = (SpikingMembraneStructure) currentConfig.getMembraneStructure();
		SpikingEnvironment env = (SpikingEnvironment) structure.getEnvironmentMembrane();
		Map<Integer, List<Short>> binarySpikeTrain = env.getBinarySpikeTrain();
		
		Iterator<Integer> it = binarySpikeTrain.keySet().iterator();
		
		while(it.hasNext())
		{
			int key = it.next();
			
			List<Short> result = binarySpikeTrain.get(key);

			if(result.size() == this.executionStep - 1)		// if the binary spike train has not been added anything for this step, we have to add zero
				env.addToBinarySpikeTrain(key, 0);
								
		}
		
	}
	
	private void microStepPrintResults()
	{
		
		SpikingMembraneStructure structure = (SpikingMembraneStructure) currentConfig.getMembraneStructure();
		
		SpikingEnvironment env = (SpikingEnvironment) structure.getEnvironmentMembrane();
		

		getInfoChannel().println(
				"···············································");
		getInfoChannel().println("Execution Results at step: "+ (this.executionStep - 1));
		getInfoChannel().println();
		getInfoChannel().println("Binary Sequence:");
		getInfoChannel().println();
		getInfoChannel().println(env.getBinarySpikeTrain());
		getInfoChannel().println();
		getInfoChannel().println("Natural Sequence:");
		getInfoChannel().println();
		getInfoChannel().println(env.getNaturalSpikeTrain());
		getInfoChannel().println();
		getInfoChannel().println("Configuration:");
		getInfoChannel().println();
		getInfoChannel().println(structure);
		getInfoChannel().println(
				"···············································");
		getInfoChannel().println();
		
	}
	
	private void microStepPrintFinalResults()
	{
		if(!hasSelectedRules())
		{
			if(this.showBinarySequence)
			{
				getInfoChannel().println("For the halting configuration, Binary Sequence");
				getInfoChannel().println(this.computeBinarySequence());
				getInfoChannel().println();
			}
			
			if(this.showNaturalSequence != null)
			{
							
				long k = (Long) this.showNaturalSequence.get(0);
				boolean strong  = (Boolean) this.showNaturalSequence.get(1);
				boolean alternate = (Boolean) this.showNaturalSequence.get(2);
				
				getInfoChannel().println("For the halting configuration, Natural Sequence");
				getInfoChannel().println(this.computeNaturalSequence(k,strong,alternate));
				getInfoChannel().println();
			}
			
			if(this.showSummatories)
			{
				getInfoChannel().println("For the halting configuration, Output Summatories");
				getInfoChannel().println(this.computeOutputSummatories());
				getInfoChannel().println();
			}
			
			if(this.validConfiguration != null)
			{
				getInfoChannel().println("For the halting configuration, Valid Configuration");
				getInfoChannel().println(this.computeValidConfigurationCheck());
				getInfoChannel().println();
			}	
		}
	}
	
	
	private Map<Integer, List<Short>> computeBinarySequence()
	{
		
		SpikingMembraneStructure structure = (SpikingMembraneStructure) currentConfig
		.getMembraneStructure();

		SpikingEnvironment env = (SpikingEnvironment) structure.getEnvironmentMembrane();
						
		Map<Integer, List<Short>> binarySequence = env.getBinarySpikeTrain();
		
		return binarySequence;
	}
	
	private Map<Integer, Set<Long>> computeNaturalSequence(long k, boolean strong, boolean alternate)
	{
		
		Map<Integer, Set<Long>> result = new HashMap<Integer, Set<Long>>();

		if(k < 2)
			return result;
		
		SpikingMembraneStructure structure = (SpikingMembraneStructure) currentConfig
		.getMembraneStructure();
		
		SpikingEnvironment env = (SpikingEnvironment) structure.getEnvironmentMembrane();

		Map<Integer, List<Long>> natualSequence = env.getNaturalSpikeTrain();
						
		if(natualSequence.size() == 0)	// if we don't have any results (i. e. no output membranes, 
			return result;				// no computations...) then we are done
		
		Iterator<Integer> it = natualSequence.keySet().iterator();
		
		while(it.hasNext())
		{
			int key = (int) it.next();
			List<Long> sequence = natualSequence.get(key);
			Set<Long> keyResult = new HashSet<Long>();
			
			long spikeCount = sequence.size(); 

			// strong = true  --> number of times that output fires is exactly k
			// strong = false --> number of times that output fires is at least k
			
			if((strong == true && spikeCount != k) || (strong == false && spikeCount < k))
				result.put(key, keyResult);	// if the conditions are not satisfied we add with keyResult = []
			else
			{
				keyResult = computeSingleNaturalSequence(k,alternate,sequence);	// if the conditions are satisfied we calculate the sequence and store the result
				result.put(key, keyResult);
			}
			
		}
			
		return result;
	}
	
	private Set<Long> computeSingleNaturalSequence(long k, boolean alternate, List<Long>sequence)
	{
		
		HashSet<Long> result = new HashSet<Long>();
				
		long spikeCount = sequence.size(); 
		
		int i = 0;
		
		while(i < spikeCount - 1)
		{
			long first = sequence.get(i);
			long second = sequence.get(i+1);
			
			if(alternate)
			{	
				if(i%2 == 0)
					result.add(second-first);
			}
			else
			{
				result.add(second-first);
			}
			
			i++;
			
		}
		
		return result;
	}
	
	private Map<Integer, Long> computeOutputSummatories()
	{
		
		Map<Integer, Long> result = new HashMap<Integer, Long>();

		SpikingMembraneStructure structure = (SpikingMembraneStructure) currentConfig
		.getMembraneStructure();
		
		SpikingEnvironment env = (SpikingEnvironment) structure.getEnvironmentMembrane();

		Map<Integer, List<Long>> natualSequence = env.getNaturalSpikeTrain();
		
		if(natualSequence.size() == 0)	// if we don't have any results (i. e. no output membranes, 
			return result;				// no computations...) then we are done
		
		Iterator<Integer> it = natualSequence.keySet().iterator();
		
		while(it.hasNext())
		{
			int key = (int) it.next();
			List<Long> sequence = natualSequence.get(key);
			long spikeCount = sequence.size(); 
			result.put(key, spikeCount);
			
		}
			
		return result;
	}
	
	private boolean computeValidConfigurationCheck()
	{
		boolean result = true;
		
		SpikingMembraneStructure structure = (SpikingMembraneStructure) currentConfig
		.getMembraneStructure();

		Map<String,Long> valid = this.getValidConfiguration();
		
		Iterator<String> it = valid.keySet().iterator();
		
		while(it.hasNext() && result)
		{
			String label = (String) it.next();
			long value = valid.get(label);
			
			Iterator<SpikingMembrane> itm = structure.getCellsByLabel(label).iterator();
			
			while(itm.hasNext() && result)
			{
				SpikingMembrane m = (SpikingMembrane) itm.next();
				
				long spikes = m.getMembraneSpikingStringSize();
				
				if(spikes != value)
					result = false;
			}
		}
		
		return result;
	}

	
	private void removeLeftHandRuleObjects()
	{
		
		Iterator<SpikingMembrane> it = toFireFiltered.values().iterator();
		
		while (it.hasNext()) {
			
			SpikingMembrane s = it.next();
			SpikingRule rule = s.getSelectedRule();			
			this.removeLeftHandRuleObjects(s, rule, 1);
			
		}

	}
	
	@Override
	protected void removeLeftHandRuleObjects(ChangeableMembrane membrane,
			IRule r, long count) {
		// TODO Auto-generated method stub
		
		SpikingMembrane s = (SpikingMembrane) membrane;
		SpikingRule rule = (SpikingRule) r;

		if(rule.isFiringRule() || rule.isForgettingRule())	// we have an spiking rule
		{
			long ruleLeftHandRuleMultisetSize = rule.getLeftHandRuleSpikingStringSize();
			s.removeSpikes(ruleLeftHandRuleMultisetSize);
		}
		else if(rule.isBuddingRule())
		{
			s.clearSpikes();	
		}
		else if(rule.isDivisionRule())
		{
			s.clearSpikes();
		}
	}


	@Override
	protected void printInfoMembrane(ChangeableMembrane membrane) {
		// TODO Auto-generated method stub
		
		getInfoChannel().println("    " + getHead(membrane));
		getInfoChannel().println("    Multiset: " + membrane.getMultiSet());
		getInfoChannel().println();
	}
	
	@Override
	protected void printInfoMembraneShort(MembraneStructure membraneStructure) {
		// TODO Auto-generated method stub
		Iterator<? extends Membrane>it = membraneStructure.getAllMembranes().iterator();
		while(it.hasNext())
			printInfoMembrane((ChangeableMembrane)it.next());
	}
	
	@Override
	protected String getHead(ChangeableMembrane m) {
		// TODO Auto-generated method stub
		
		SpikingMembrane s = (SpikingMembrane) m;
		
		String str;
		str = "NEURON ID: " + s.getId();
		str += ", Label: " + s.getLabelObj();
		str += ", Skipped: " + s.isSkipped();
		str += ", Bound timer: " + s.getBoundTimer();
		str += ", Open in (w/ bound 0): " + s.getStepsToOpen();
		return str;
	}
	
	private void setAsynch(int asynch) {
		if (asynch >= 0 && asynch <= 3)
			this.asynch = asynch;
		else
		{
			this.asynch = 0; // if not valid asynch parameter then set asynch off
			this.bound = 0;
		}
		
		if(asynch == 2 || asynch == 3)
			this.setSequential(0);

		// TODO Auto-generated constructor stub
	}

	public int getAsynch() {
		return this.asynch;
	}
	
	private void setValidConfiguration(Map<String, Long> validSpikes) {
		this.validConfiguration = new HashMap<String, Long>(validSpikes);
	}
	
	public Map<String, Long> getValidConfiguration() {
		return this.validConfiguration;
	}
	
	public long getBound()
	{
		return bound;
	}
	
	private void setBound(long bound)
	{
		if(bound < 2)
			;
		else 
			this.bound = bound;
	}


	private void setSequential(int sequential) {
		if (sequential >= 0 && sequential <= 5)
			this.sequential = sequential;
		else
			this.sequential = 0; // if not valid sequential parameter then set sequential off

		// TODO Auto-generated constructor stub
		
	}

	public int getSequential() {
		return this.sequential;
	}

	
	private static void printDebug(Object o)
	{
		if(DEBUG)
			System.out.println(o);
	}
	
	private static void printDebug()
	{
		if(DEBUG)
			System.out.println();
	}
	
	public List<Object> getExecutionResult() {
		
		SpikingMembraneStructure structure = (SpikingMembraneStructure) currentConfig
				.getMembraneStructure();

		String envLabel = structure.getEnvironmentLabel();

		SpikingEnvironment env = (SpikingEnvironment) structure.getEnvironmentMembrane();
						
		long envSpikes = env.getMembraneSpikingStringSize();
		
		Map<Integer, List<Short>> binarySpikeTrain = env.getBinarySpikeTrain();

		Map<Integer, List<Long>> naturalSpikeTrain = env.getNaturalSpikeTrain();
		
		Map<Integer, String> membraneMapping = new HashMap<Integer, String>();
		
		Map<Integer, Long> spikes = new HashMap<Integer, Long>();
		
		boolean validAsynchExecution = true;
		
		boolean strongCase = true;

		Iterator<? extends Membrane> itmem = structure.getAllMembranes().iterator();

		while (itmem.hasNext()) {

			SpikingMembrane mem = (SpikingMembrane) itmem.next();

			int id = mem.getId();

			String label = mem.getLabel();
			
			membraneMapping.put(id, label);
			
			long size = mem.getMembraneSpikingStringSize();

			spikes.put(id, size);

			if (size > 0L && !mem.getLabel().equals(envLabel))
				strongCase = false;

		}
		
		if(this.validConfiguration != null)
			validAsynchExecution = computeValidConfigurationCheck();
				
		List<Object> executionResult = new ArrayList<Object>();

		executionResult.add(membraneMapping);
		executionResult.add(envSpikes);
		executionResult.add(spikes);
		executionResult.add(validAsynchExecution);
		executionResult.add(strongCase);
		executionResult.add(binarySpikeTrain);
		executionResult.add(naturalSpikeTrain);

		return executionResult;

	}

	
}
