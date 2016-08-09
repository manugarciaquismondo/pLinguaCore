package org.gcn.plinguacore.util.psystem.rule.regenerative;

import java.util.List;
import java.util.Set;

import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.regenerative.membrane.RegenerativeMembrane;
import org.gcn.plinguacore.util.psystem.rule.IKernelRule;
import org.gcn.plinguacore.util.psystem.rule.LeftHandRule;
import org.gcn.plinguacore.util.psystem.rule.OuterRuleMembrane;
import org.gcn.plinguacore.util.psystem.rule.RightHandRule;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;

public class GemmationRegenerativeLikeRule extends DivisionRegenerativeLikeRule
		implements IKernelRule {

	public GemmationRegenerativeLikeRule(LeftHandRule leftHandRule, RightHandRule rightHandRule) throws PlinguaCoreException{
		super(leftHandRule, rightHandRule);
	}
	
	public GemmationRegenerativeLikeRule(LeftHandRule leftHandRule,
			RightHandRule rightHandRule, Guard guard)
			throws PlinguaCoreException {
		super(leftHandRule, rightHandRule, guard);
		// TODO Auto-generated constructor stub
	}

	
	


	@Override
	protected void updateAdjacencies(ChangeableMembrane membrane) {
		// TODO Auto-generated method stub
		((RegenerativeMembrane)latestDividedMembrane).addLink((RegenerativeMembrane)membrane);
	}


	
	@Override
	public String getArrow(){
		return " --> ";
	}

}
