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

package org.gcn.plinguacore.simulator.probabilisticGuarded;

import org.gcn.plinguacore.simulator.CreateSimulator;
import org.gcn.plinguacore.simulator.simplekernel.SimpleKernelCreateSimulator;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.checkPsystem.BaseCheckPsystem;
import org.gcn.plinguacore.util.psystem.checkPsystem.specificCheckPsystem.probabilisticGuarded.AllInitialMultisetsContainOneFlag;
import org.gcn.plinguacore.util.psystem.checkPsystem.specificCheckPsystem.probabilisticGuarded.CompleteProbabilitiesForProbabilisticGuardedPSystems;
import org.gcn.plinguacore.util.psystem.checkPsystem.specificCheckPsystem.probabilisticGuarded.ConsistentRulesForProbabilisticGuardedPSystems;
import org.gcn.plinguacore.util.psystem.checkPsystem.specificCheckPsystem.probabilisticGuarded.NonOverlappingRulesForProbabilisticGuardedPSystems;
import org.gcn.plinguacore.util.psystem.checkPsystem.specificCheckPsystem.probabilisticGuarded.GuardObjectsAreFlags;
import org.gcn.plinguacore.util.psystem.factory.AbstractPsystemFactory;
import org.gcn.plinguacore.util.psystem.probabilisticGuarded.ProbabilisticGuardedPsystem;
import org.gcn.plinguacore.util.psystem.rule.AbstractRuleFactory;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.*;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.probabilisticGuarded.NoGuardConsumptionWithoutGeneration;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.probabilisticGuarded.NoGuardConsumptionWithoutGuarding;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.probabilisticGuarded.NoGuardGenerationWithoutConsumption;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.probabilisticGuarded.NoMultipleConsumptionOfGuards;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.probabilisticGuarded.NoMultipleGenerationOfGuards;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.probabilisticGuarded.NoRemoteGuardGeneration;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.probabilisticGuarded.OnlyRestrictiveGuard;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.probabilisticGuarded.OnlyRestrictiveGuardOrRestrictiveUnaryUnitGuard;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.probabilisticGuarded.UnaryUnitGuardRequired;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRuleFactory;

public class ProbabilisticGuardedPsystemFactory extends AbstractPsystemFactory {

	private static ProbabilisticGuardedPsystemFactory singleton = null;

	protected ProbabilisticGuardedPsystemFactory() {
		super();
		checkRule = new NoCharges(
				new NoLeftExternalMultiSet(
						new NoRightExternalMultiSet(
										new OnlyRestrictiveGuardOrRestrictiveUnaryUnitGuard(
												new NoRemoteGuardGeneration(
														new NoMultipleConsumptionOfGuards(
																new NoMultipleGenerationOfGuards(																		
										new NoGuardConsumptionWithoutGuarding(
												new NoGuardGenerationWithoutConsumption(
														new UnaryUnitGuardRequired(
																new NoGuardConsumptionWithoutGeneration(
												new Ratio(
										new NoDissolution(	
												new NoGeneStrings(
														new NoLeftInnerMembranes(
																new NoPriority(																		
																			new NoRightInnerMembranes(
																					new NoDivision())))))))))))))))));
		checkPsystem = new CompleteProbabilitiesForProbabilisticGuardedPSystems(new ConsistentRulesForProbabilisticGuardedPSystems(
				new GuardObjectsAreFlags(
						new NonOverlappingRulesForProbabilisticGuardedPSystems(
								new AllInitialMultisetsContainOneFlag(
				new BaseCheckPsystem())))));
	}

	public static ProbabilisticGuardedPsystemFactory getInstance() {
		if (singleton == null)
			singleton = new ProbabilisticGuardedPsystemFactory();
		return singleton;
	}

	@Override
	public CreateSimulator getCreateSimulator() throws PlinguaCoreException {
		// TODO Auto-generated method stub
		return new ProbabilisticGuardedCreateSimulator(getModelName());

	}

	@Override
	protected Psystem newPsystem() {
		// TODO Auto-generated method stub
		return new ProbabilisticGuardedPsystem();
	}


	@Override
	protected AbstractRuleFactory newRuleFactory() {		
		return new ProbabilisticGuardedRuleFactory();
	}

}
