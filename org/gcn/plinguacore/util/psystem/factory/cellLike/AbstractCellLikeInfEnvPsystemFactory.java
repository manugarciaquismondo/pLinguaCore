package org.gcn.plinguacore.util.psystem.factory.cellLike;

import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.cellLike.CellLikeInfEnvPsystem;
import org.gcn.plinguacore.util.psystem.factory.AbstractPsystemFactory;
import org.gcn.plinguacore.util.psystem.rule.AbstractRuleFactory;
import org.gcn.plinguacore.util.psystem.rule.cellLike.CellLikeRuleFactory;



public abstract class AbstractCellLikeInfEnvPsystemFactory extends AbstractPsystemFactory {
	
	@Override
	protected final Psystem newPsystem() {
		// TODO Auto-generated method stub
		return new CellLikeInfEnvPsystem();
	}

	@Override
	protected AbstractRuleFactory newRuleFactory() {
		// TODO Auto-generated method stub
		return new CellLikeRuleFactory();
	}

	

}
