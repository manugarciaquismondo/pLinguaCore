package org.gcn.plinguacore.simulator.regenerative;

import org.gcn.plinguacore.simulator.CreateSimulator;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.checkPsystem.BaseCheckPsystem;
import org.gcn.plinguacore.util.psystem.checkPsystem.specificCheckPsystem.regenerative.CheckRightGenerationAndConsumptionOfLinksCheckPsystem;
import org.gcn.plinguacore.util.psystem.checkPsystem.specificCheckPsystem.regenerative.OneLinkPerObjectCheckPsystem;
import org.gcn.plinguacore.util.psystem.factory.AbstractPsystemFactory;
import org.gcn.plinguacore.util.psystem.regenerative.RegenerativePsystem;
import org.gcn.plinguacore.util.psystem.rule.AbstractRuleFactory;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoCharges;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoConstant;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoDissolution;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoGeneStrings;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoLeftExternalMultiSet;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoLeftInnerMembranes;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoMoreThanOneAffectedMembranes;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoPriority;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoRightExternalMultiSet;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoRightInnerMembranes;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.NoSimpleDivision;
import org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.probabilisticGuarded.NoRestrictiveGuard;
import org.gcn.plinguacore.util.psystem.rule.regenerative.RegenerativeRuleFactory;

public class RegenerativePsystemFactory extends AbstractPsystemFactory {

	private static RegenerativePsystemFactory singleton;

	protected RegenerativePsystemFactory() {
		super();
		checkRule = new NoCharges(
			new NoLeftExternalMultiSet(
				new NoRightExternalMultiSet(
					new NoRestrictiveGuard(
						new NoConstant(
							new NoDissolution(	
								new NoGeneStrings(
									new NoLeftInnerMembranes(																
										new NoRightInnerMembranes(
											new NoSimpleDivision(
												new NoMoreThanOneAffectedMembranes()))))))))));
		// TODO Auto-generated constructor stub
		checkPsystem = new CheckRightGenerationAndConsumptionOfLinksCheckPsystem(new OneLinkPerObjectCheckPsystem(new BaseCheckPsystem()));
	}

	@Override
	public CreateSimulator getCreateSimulator() throws PlinguaCoreException {
		// TODO Auto-generated method stub
		return new RegenerativeCreateSimulator(getModelName());
	}

	@Override
	protected Psystem newPsystem() {
		// TODO Auto-generated method stub
		return new RegenerativePsystem();
	}


	public static RegenerativePsystemFactory getInstance(){
		if(singleton==null){
			singleton=new RegenerativePsystemFactory();
		}
		return singleton;
	}
	@Override
	protected AbstractRuleFactory newRuleFactory() {
		// TODO Auto-generated method stub
		return new RegenerativeRuleFactory();
	}

}
