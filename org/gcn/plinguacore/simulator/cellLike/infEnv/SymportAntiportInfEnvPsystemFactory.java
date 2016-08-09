package org.gcn.plinguacore.simulator.cellLike.infEnv;

import org.gcn.plinguacore.simulator.CreateSimulator;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.factory.cellLike.AbstractCellLikeInfEnvPsystemFactory;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.InfEnvSymportAntiport;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoChangeableLabel;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoCharges;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoConstant;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoCooperationWithDivision;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoCreation;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoDissolution;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoDivision;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoDivisionWithChangeableLabel;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoEmptyLeftMultiSet;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoEvolution;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoGeneStrings;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoGuard;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoLeftExternalMultiSet;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoLeftInnerMembranes;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoMultipleProductionWithDivision;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoPriority;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoRightExternalMultiSet;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoRightInnerMembranes;


public class SymportAntiportInfEnvPsystemFactory extends AbstractCellLikeInfEnvPsystemFactory {

	private static SymportAntiportInfEnvPsystemFactory singleton = null;

	protected SymportAntiportInfEnvPsystemFactory() {
		super();
		checkRule = new NoCharges(
				//new NoLeftExternalMultiSet(
						//new NoRightExternalMultiSet(
								new NoConstant(
										new NoDissolution(
												new NoEmptyLeftMultiSet(
														new NoEvolution(
																new NoGeneStrings(
																		new NoLeftInnerMembranes(
																				new NoPriority(
																						new NoRightInnerMembranes(
																								new NoMultipleProductionWithDivision(
																										new NoCooperationWithDivision(
																												new NoGuard(
																														new NoDivisionWithChangeableLabel()))))))))))));//));
		checkRule = new InfEnvSymportAntiport(checkRule);
		
		// TODO Auto-generated constructor stub
	}

	/**
	 * Gets only instance of SymportAntiportInfEnvPsystemFactory class, as stated in
	 * Singleton pattern
	 * 
	 * @return the only instance of SymportAntiportInfEnvPsystemFactory class
	 */
	public static SymportAntiportInfEnvPsystemFactory getInstance() {
		if (singleton == null)
			singleton = new SymportAntiportInfEnvPsystemFactory();
		return singleton;
	}

	/**
	 * @see org.gcn.plinguacore.util.psystem.factory.IPsystemFactory#getCreateSimulator()
	 */
	public CreateSimulator getCreateSimulator() throws PlinguaCoreException {
		return new InfEnvCreateSimulator(getModelName());
	}

}
