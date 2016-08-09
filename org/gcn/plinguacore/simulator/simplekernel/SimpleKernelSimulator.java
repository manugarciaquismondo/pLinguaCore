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

package org.gcn.plinguacore.simulator.simplekernel;



import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.gcn.plinguacore.simulator.AbstractSelectionExecutionSimulator;

import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.Triple;
import org.gcn.plinguacore.util.psystem.Configuration;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;
import org.gcn.plinguacore.util.psystem.rule.IKernelRule;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.RightHandRule;
import org.gcn.plinguacore.util.psystem.rule.RulesSet;
import org.gcn.plinguacore.util.psystem.rule.simplekernel.DivisionKernelLikeRule;
import org.gcn.plinguacore.util.psystem.rule.simplekernel.EvolutionCommunicationKernelLikeRule;
import org.gcn.plinguacore.util.psystem.rule.simplekernel.InputOutputKernelLikeRule;
import org.gcn.plinguacore.util.psystem.simplekernel.membrane.SimpleKernelLikeMembraneStructure;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembrane;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembraneStructure;



import java.util.List;

public class SimpleKernelSimulator extends AbstractSelectionExecutionSimulator {

	/**
	 * 
	 */
	private static final int DIVISION_STAGE=2;
	private static final int INPUT_OUTPUT_STAGE=1;
	private static final int EVOLUTION_AND_COMMUNICATION_STAGE=0;
	private static final long serialVersionUID = -1556761252509150318L;
	private static final int STATE_SELECT_DIVISION=10;
	private static final int STATE_SELECT_COMMUNICATION=20;
	private static final int STATE_SELECT_INPUT_OUTPUT=30;
	private static final String HALT = "halt";
	private int state;
	protected SimpleKernelLikeMembraneStructure structure;
	private Map<Integer,Triple<DivisionKernelLikeRule,ChangeableMembrane,Long>>membranesToDivide;
	private List<Triple<EvolutionCommunicationKernelLikeRule, ChangeableMembrane, Long>> evolutionRules;
	private List<Triple<InputOutputKernelLikeRule, ChangeableMembrane, Long>> inputOutputRules;
	private boolean haltingConditionMode = false;
	protected boolean haltDetected = false;
	private boolean halted = false;
	
	public SimpleKernelSimulator(Psystem psystem) {
		super(psystem);
		evolutionRules = new LinkedList<Triple<EvolutionCommunicationKernelLikeRule, ChangeableMembrane, Long>>();
		membranesToDivide = new HashMap<Integer,Triple<DivisionKernelLikeRule,ChangeableMembrane,Long>>();
		inputOutputRules = new LinkedList<Triple<InputOutputKernelLikeRule, ChangeableMembrane, Long>>();
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
	protected void microStepInit() {
		// TODO Auto-generated method stub
		super.microStepInit();
		haltDetected = false;
		structure = (SimpleKernelLikeMembraneStructure)getPsystem().getMembraneStructure();
		membranesToDivide.clear();
		evolutionRules.clear();
		inputOutputRules.clear();
		state=STATE_SELECT_COMMUNICATION;
	}

	@Override
	protected void microStepSelectRules(Configuration cnf, Configuration tmpCnf) {
		if (!isHalted()){		
			for (int i=EVOLUTION_AND_COMMUNICATION_STAGE;i<=DIVISION_STAGE;i++)
			{
				if (i==INPUT_OUTPUT_STAGE)
					state = STATE_SELECT_INPUT_OUTPUT;
				else if (i==DIVISION_STAGE)
					state = STATE_SELECT_DIVISION;
				Iterator<? extends Membrane> it = tmpCnf.getMembraneStructure().getAllMembranes().iterator();
				Iterator<? extends Membrane> it1 = cnf.getMembraneStructure().getAllMembranes().iterator();
				while(it.hasNext())
				{
					ChangeableMembrane tempMembrane = (ChangeableMembrane) it.next();
					ChangeableMembrane m = (ChangeableMembrane)it1.next();
					microStepSelectRules(m,tempMembrane);
				}
			}
		}
	}

	@Override
	protected void microStepSelectRules(ChangeableMembrane m,
			ChangeableMembrane temp) {
		Iterator<IRule> it = getApplicableRules(temp);

		while (it.hasNext()) {
			IRule r = it.next();
			
			structure = (SimpleKernelLikeMembraneStructure)((TissueLikeMembrane)m).getStructure();
			
			DivisionKernelLikeRule r1 = (DivisionKernelLikeRule)r;
			r1.setMembraneStructure(structure);
			if (state==STATE_SELECT_COMMUNICATION && (r instanceof EvolutionCommunicationKernelLikeRule) && !isDivision(r)) {					
				selectEvolutionCommunicationRule(m, temp, r1);
			} else if (state==STATE_SELECT_DIVISION && isDivision(r) 
					&& !membranesToDivide.containsKey(m.getId())) {
				selectDivisionRule(m, temp, r1);
			} else if (state==STATE_SELECT_INPUT_OUTPUT && 
					(r instanceof InputOutputKernelLikeRule)){
				selectInputOutputRule(m, temp, r1);
			}
		}
		
	}

	protected Iterator<IRule> getApplicableRules(ChangeableMembrane temp) {
		return getPsystem().getRules().iterator(temp.getLabel(),
				temp.getCharge(),true);
	}

	protected void selectDivisionRule(ChangeableMembrane m,
			ChangeableMembrane temp, DivisionKernelLikeRule r) {
		long count = 0;
		
		if (r.guardEvaluates(m))
			count = r.countExecutions(temp,m);
		if (count>0)
		{
			count=1;
			selectRule(r, m, count);
			membranesToDivide.put(m.getId(),new Triple<DivisionKernelLikeRule,ChangeableMembrane,Long>(r,m,count));
			removeLeftHandRuleObjects(temp, r, count);
			
		}
	}

	protected void selectEvolutionCommunicationRule(ChangeableMembrane m,
			ChangeableMembrane temp, DivisionKernelLikeRule r1) {
		r1.setMembraneStructure(structure);
		long count = r1.countExecutions(temp,m);
		if (count>0)
		{

				selectRule(r1, m, count);
				removeLeftHandRuleObjects(temp, r1,count);
			evolutionRules.add(new Triple<EvolutionCommunicationKernelLikeRule, ChangeableMembrane, Long>((EvolutionCommunicationKernelLikeRule)r1, m,count));
		}
	}

	protected void selectInputOutputRule(ChangeableMembrane m,
			ChangeableMembrane temp, DivisionKernelLikeRule r1) {
		r1.setMembraneStructure(structure);
		long count = r1.countExecutions(temp,m);
		if (count>0)
		{
				selectRule(r1, m, count);
				removeLeftHandRuleObjects(temp, r1,count);
			inputOutputRules.add(new Triple<InputOutputKernelLikeRule, ChangeableMembrane, Long>((InputOutputKernelLikeRule)r1, m,count));
		}
	}

	private boolean isDivision(IRule r) {		
		return (!(r instanceof EvolutionCommunicationKernelLikeRule) &&
				!(r instanceof InputOutputKernelLikeRule));
	}

	protected TissueLikeMembrane getMembraneByLabel(
			DivisionKernelLikeRule r1,
			TissueLikeMembraneStructure structure) {
		Iterator<TissueLikeMembrane> it = structure.iterator(r1.getRightHandRule().getOuterRuleMembrane().getLabelObj().getLabelID());
		TissueLikeMembrane tlm = null;
		if (it.hasNext()){
			tlm = it.next();
		}
		
		return tlm;
	}

	protected boolean isEvolution(IRule r){
		return r.getLeftHandRule().getOuterRuleMembrane().getLabel().equals(r.getRightHandRule().getOuterRuleMembrane().getLabel());
	}
	
	protected RulesSet filterRules(Iterator<IRule> iterator, ChangeableMembrane m) {
		RulesSet returnedRuleSet = new RulesSet();
		while(iterator.hasNext()){
			IKernelRule rule= (IKernelRule) iterator.next();
			if(rule.guardEvaluates(m))
				returnedRuleSet.add(rule);
		}
		return returnedRuleSet;
	}

	@Override
	public void microStepExecuteRules() {
		executeEvolutionCommunicationRules();
		executeInputOutputRules();
		executeDivisionRules();		
	}

	protected void executeDivisionRules() {
		Iterator<Triple<DivisionKernelLikeRule,ChangeableMembrane,Long>>it1 = membranesToDivide.values().iterator();
		while(it1.hasNext())
		{
			Triple<DivisionKernelLikeRule,ChangeableMembrane,Long>p = it1.next();
			p.getFirst().execute(p.getSecond(), null);
			checkHalt(p.getFirst());
		}
	}

	protected void executeEvolutionCommunicationRules() {
		for(Triple<EvolutionCommunicationKernelLikeRule, ChangeableMembrane, Long> Triple: evolutionRules){
			EvolutionCommunicationKernelLikeRule r1 = Triple.getFirst();
			ChangeableMembrane m = Triple.getSecond();
			//long count = r1.countExecutions(m,original);
			long count = Triple.getThird();
			TissueLikeMembrane tlm = getMembraneByLabel(r1, structure);
			if (tlm != null)
				r1.setRightHandMembrane(tlm);
			
			TissueLikeMembrane existentMembrane = structure.getCell(m.getId());
			if (existentMembrane != null){
				r1.execute(existentMembrane, currentConfig.getEnvironment(), count);
				checkHalt(r1);
			}
		}
	}
	
	protected void executeInputOutputRules() {
		for(Triple<InputOutputKernelLikeRule, ChangeableMembrane, Long> Triple: inputOutputRules){
			InputOutputKernelLikeRule r1 = Triple.getFirst();
			ChangeableMembrane m = Triple.getSecond();
			//long count = r1.countExecutions(m,original);
			long count = Triple.getThird();
			r1.execute(structure.getCell(m.getId()), currentConfig.getEnvironment(), count);
			checkHalt(r1);
		}
	}

	protected void checkHalt(IKernelRule rule) {
		if (hasHaltObject(rule.getRightHandRule().getObjects())){
			haltDetected = true;
			setHalted(true);
		}
	}
	

	private boolean hasHaltObject(Set<String> objects) {
		for(String object: objects)
			if(getPsystem().hasProperty(object, HALT))
				return true;
		return false;
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
			printMembraneContent(membrane);
			getInfoChannel().println();
		}
	}

	protected void printMembraneContent(ChangeableMembrane membrane) {
		getInfoChannel().println("    " + getHead(membrane));
		getInfoChannel().println("    Multiset: " + membrane.getMultiSet());
	}
	

	@Override
	protected void removeLeftHandRuleObjects(ChangeableMembrane membrane,
			IRule r, long count) {
		// TODO Auto-generated method stub
		MultiSet<String>ms = r.getLeftHandRule().getOuterRuleMembrane().getMultiSet();
		if (!ms.isEmpty())
			membrane.getMultiSet().subtraction(ms, count);
	}

	public boolean isHaltingConditionMode() {
		return haltingConditionMode;
	}

	public void setHaltingConditionMode(boolean haltingConditionMode) {
		this.haltingConditionMode = haltingConditionMode;
	}

	@Override
	protected boolean specificStep() throws PlinguaCoreException{
		boolean result = super.specificStep();
		if (result){
			if (haltingConditionMode && haltDetected)
				result = false;
		}
		return result;
	}

	public boolean isHalted() {
		return halted;
	}

	public void setHalted(boolean halted) {
		this.halted = halted;
	}

}
