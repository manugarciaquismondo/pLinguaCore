package org.gcn.plinguacore.util.psystem.rule.simpleregenerative;

import java.util.HashSet;
import java.util.Set;

import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.regenerative.membrane.RegenerativeMembrane;
import org.gcn.plinguacore.util.psystem.rule.LeftHandRule;
import org.gcn.plinguacore.util.psystem.rule.RightHandRule;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.rule.regenerative.LinkingRegenerativeLikeRule;

public class SimpleLinkingRegenerativeLikeRule extends
		LinkingRegenerativeLikeRule {




	public SimpleLinkingRegenerativeLikeRule(LeftHandRule leftHandRule,
			RightHandRule rightHandRule)
			throws PlinguaCoreException {
		super(leftHandRule, rightHandRule);
		// TODO Auto-generated constructor stub
	}

	public SimpleLinkingRegenerativeLikeRule(LeftHandRule leftHandRule,
			RightHandRule rightHandRule, Guard guard)
			throws PlinguaCoreException {
		super(leftHandRule, rightHandRule, guard);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setLinkObjects(Set<String> linkObjects)
			throws PlinguaCoreException {
		// TODO Auto-generated method stub
		super.setLinkObjects(new HashSet<String>());
	}

	@Override
	protected void updateLinkHandlerLinkObjects(Set<String> candidateLinkObjects)
			throws PlinguaCoreException {
		// TODO Auto-generated method stub
		super.updateLinkHandlerLinkObjects(new HashSet<String>());
	}
	
	@Override
	protected void extractLinkingObjectsFromMembraneStructure() {
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return baseString();
	}

	@Override
	protected boolean isLinkableMembrane(RegenerativeMembrane membrane,
			RegenerativeMembrane regenerativeMembrane) {
		// TODO Auto-generated method stub
		return areNotAdjacentMembranes(membrane, regenerativeMembrane);
	}
	
	

}
