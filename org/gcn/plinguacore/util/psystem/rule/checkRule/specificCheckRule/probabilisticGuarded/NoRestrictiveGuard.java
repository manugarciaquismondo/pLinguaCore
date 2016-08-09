package org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.probabilisticGuarded;

import org.gcn.plinguacore.util.psystem.rule.IKernelRule;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.CheckRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.DecoratorCheckRule;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.rule.guard.GuardTypes;

public class NoRestrictiveGuard extends DecoratorCheckRule {

	public NoRestrictiveGuard() {
		super();
		// TODO Auto-generated constructor stub
	}



	public NoRestrictiveGuard(CheckRule cr) {
		super(cr);
		// TODO Auto-generated constructor stub
	}



	@Override
	protected boolean checkSpecificPart(IRule r) {

		if (!(r instanceof IKernelRule)) return false;
		IKernelRule kernelRule = (IKernelRule) r;
		Guard guard = kernelRule.getGuard();
		return guard==null||!GuardTypes.isRestrictive(guard.getType());
	}



	
	

	@Override
	protected String getSpecificCause() {
		// TODO Auto-generated method stub
		return "The rule need to be of IKernelRule type. In addition, its guard cannot be an instance of RestrictiveGuard";
	}

}
