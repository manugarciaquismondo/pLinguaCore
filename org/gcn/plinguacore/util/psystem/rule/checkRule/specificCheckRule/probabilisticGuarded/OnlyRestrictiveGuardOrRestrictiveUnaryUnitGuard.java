package org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.probabilisticGuarded;

import org.gcn.plinguacore.util.psystem.probabilisticGuarded.ProbabilisticGuardedPsystem;
import org.gcn.plinguacore.util.psystem.rule.IKernelRule;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.CheckRule;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.rule.guard.GuardTypes;
import org.gcn.plinguacore.util.psystem.rule.guard.UnitGuard;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveGuard;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveUnaryUnitGuard;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveUnitGuard;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRule;

public class OnlyRestrictiveGuardOrRestrictiveUnaryUnitGuard extends OnlyRestrictiveGuard {

	public OnlyRestrictiveGuardOrRestrictiveUnaryUnitGuard() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OnlyRestrictiveGuardOrRestrictiveUnaryUnitGuard(CheckRule cr) {
		super(cr);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean checkSpecificPart(IRule r) {
		// TODO Auto-generated method stub
		if (!super.checkSpecificPart(r)) return false;
		ProbabilisticGuardedRule probabilisticGuardedRule = (ProbabilisticGuardedRule) r;
		Guard guard = probabilisticGuardedRule.getGuard();
		if(guard==null) return false;
		return guard.getType()!=GuardTypes.UNIT_RESTRICTIVE;
	}

	@Override
	protected String getSpecificCause() {
		// TODO Auto-generated method stub
		return super.getSpecificCause()+". Moreover, the guard object multiplicity must be equal to 1.";
	}

}
