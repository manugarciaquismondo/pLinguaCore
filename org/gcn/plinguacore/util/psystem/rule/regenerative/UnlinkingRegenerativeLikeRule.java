package org.gcn.plinguacore.util.psystem.rule.regenerative;

import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.regenerative.membrane.RegenerativeMembrane;
import org.gcn.plinguacore.util.psystem.rule.LeftHandRule;
import org.gcn.plinguacore.util.psystem.rule.RightHandRule;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;


public class UnlinkingRegenerativeLikeRule extends
		LinkingRegenerativeLikeRule {

	@Override
	public boolean existLinkableMembranes(RegenerativeMembrane membrane) {
		// TODO Auto-generated method stub
		return !super.existLinkableMembranes(membrane);
	}

	public UnlinkingRegenerativeLikeRule(LeftHandRule leftHandRule,
			RightHandRule rightHandRule, Guard guard)
			throws PlinguaCoreException {
		super(leftHandRule, rightHandRule, guard);
		// TODO Auto-generated constructor stub
	}

	public UnlinkingRegenerativeLikeRule(LeftHandRule leftHandRule,
			RightHandRule rightHandRule, String linkObject, Guard guard)
			throws PlinguaCoreException {
		super(leftHandRule, rightHandRule, linkObject, guard);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getArrow() {
		// TODO Auto-generated method stub
		return " -!> ";
	}

	public UnlinkingRegenerativeLikeRule(LeftHandRule leftHandRule,
			RightHandRule rightHandRule, String linkObject)
			throws PlinguaCoreException {
		super(leftHandRule, rightHandRule, linkObject);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean processLink(RegenerativeMembrane leftHandSideMembrane,
			RegenerativeMembrane processedMembrane) {
		// TODO Auto-generated method stub
		if(processedMembrane.isAdjacent(leftHandSideMembrane)){
			processedMembrane.removeLink(leftHandSideMembrane);
			return true;
		}
		else
			return false;
	}

}
