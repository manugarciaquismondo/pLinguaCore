package org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.probabilisticGuarded;

import org.gcn.plinguacore.util.psystem.rule.IKernelRule;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.CheckRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.DecoratorCheckRule;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.rule.guard.GuardTypes;

public class UnaryUnitGuardRequired extends ProbabilisticGuardedCheckRule {

	public UnaryUnitGuardRequired() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UnaryUnitGuardRequired(CheckRule cr) {
		super(cr);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean checkSpecificPart(IRule r) {
		String temporaryCause = "In a probabilistic guarded P system, all rules should have a guard of type unary unit. However, rule ";
		// TODO Auto-generated method stub
		Guard guard =((IKernelRule) r).getGuard();
		if(guard==null){
			cause=temporaryCause+r.toString()+" does not define any guard";
			return false;
		}
		if(guard.getType()!=GuardTypes.UNARY_UNIT_RESTRICTIVE){
			cause=temporaryCause+r.toString()+" defines guard "+guard;
			return false;
		}
		return true;
	}


	
	

}
