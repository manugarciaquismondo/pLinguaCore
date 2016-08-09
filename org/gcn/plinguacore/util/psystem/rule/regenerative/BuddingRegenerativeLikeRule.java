package org.gcn.plinguacore.util.psystem.rule.regenerative;

import java.util.Iterator;

import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.regenerative.membrane.RegenerativeMembrane;
import org.gcn.plinguacore.util.psystem.rule.LeftHandRule;
import org.gcn.plinguacore.util.psystem.rule.RightHandRule;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembrane;

public class BuddingRegenerativeLikeRule extends LinkingRegenerativeLikeRule {
	
	public BuddingRegenerativeLikeRule(LeftHandRule leftHandRule,
			RightHandRule rightHandRule, String linkObject, Guard guard)
			throws PlinguaCoreException {
		super(leftHandRule, rightHandRule, linkObject, guard);
		// TODO Auto-generated constructor stub
	}
	
	public BuddingRegenerativeLikeRule(LeftHandRule leftHandRule,
			RightHandRule rightHandRule, String linkObject)
			throws PlinguaCoreException {
		super(leftHandRule, rightHandRule, linkObject);
		// TODO Auto-generated constructor stub
	}
	
	public BuddingRegenerativeLikeRule(LeftHandRule leftHandRule,
			RightHandRule rightHandRule, Guard guard) throws PlinguaCoreException {
		super(leftHandRule, rightHandRule, guard);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getArrow() {
		// TODO Auto-generated method stub
		return " <--> ";
	}




	@Override
	protected RegenerativeMembrane checkLinkAndProcessMembrane(
			RegenerativeMembrane leftHandSideMembrane,
			RegenerativeMembrane processedMembrane) {
		RegenerativeMembrane membraneToRegister=null;
		if(processedMembrane.linkObjectMatches(linkObject)){
			if(processedMembrane.isAdjacent(leftHandSideMembrane)){
				processedMembrane.removeLink(leftHandSideMembrane);
			}
			latestDividedMembrane=createDividedMembrane(leftHandSideMembrane);	
			latestDividedMembrane.getMultiSet().subtraction(getLeftHandRule().getOuterRuleMembrane().getMultiSet());
			membraneStructure.add((RegenerativeMembrane)latestDividedMembrane);
			RegenerativeMembrane latestDividedRegenerativeMembrane=(RegenerativeMembrane)latestDividedMembrane;
			latestDividedRegenerativeMembrane.addLink(leftHandSideMembrane);
			latestDividedRegenerativeMembrane.addLink(processedMembrane);
			membraneToRegister= processedMembrane;
		}
		return membraneToRegister;
		
	}

	

	@Override
	protected boolean isDestinationMembrane(
			RegenerativeMembrane appliedMembrane,
			RegenerativeMembrane regenerativeMembrane) {
		return destinationMembranes.contains(regenerativeMembrane);
	}
	
	@Override
	protected boolean isLinkableMembrane(RegenerativeMembrane membrane,
			RegenerativeMembrane regenerativeMembrane) {
		return linkObject.equals(regenerativeMembrane.getLinkObject());
	}

}
