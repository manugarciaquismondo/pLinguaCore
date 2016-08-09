package org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.probabilisticGuarded;

import org.gcn.plinguacore.util.psystem.rule.IKernelRule;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.CheckRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.DecoratorCheckRule;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.rule.guard.GuardTypes;
import org.gcn.plinguacore.util.psystem.rule.guard.UnitGuard;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveGuard;

public class OnlyRestrictiveGuard extends DecoratorCheckRule{

	protected String ruleName;
	protected String guardName;
	
	public OnlyRestrictiveGuard() {
		super();
		ruleName="";
		guardName="";
		// TODO Auto-generated constructor stub
	}



	public OnlyRestrictiveGuard(CheckRule cr) {
		super(cr);
		ruleName="";
		guardName="";
		// TODO Auto-generated constructor stub
	}



	@Override
	protected boolean checkSpecificPart(IRule r) {

		if (!(r instanceof IKernelRule)) return false;
		ruleName=r.toString();
		IKernelRule kernelRule = (IKernelRule) r;
		Guard guard = kernelRule.getGuard();
		guardName=guard.toString();
		return guard==null||GuardTypes.isRestrictive(guard.getType());
	}



	
	

	@Override
	protected String getSpecificCause() {
		// TODO Auto-generated method stub
		return "Rule "+ruleName+" needs to be of type IKernelRule. In addition, its guard must be an instance of RestrictiveGuard, but is "+guardName+"\n";
	}

}
