package org.gcn.plinguacore.util.psystem.rule.simpleregenerative;

import java.util.HashSet;
import java.util.Set;

import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.rule.LeftHandRule;
import org.gcn.plinguacore.util.psystem.rule.RightHandRule;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.rule.regenerative.CommunicationRegenerativeLikeRule;

public class SimpleCommunicationRegenerativeLikeRule extends
		CommunicationRegenerativeLikeRule {

	public SimpleCommunicationRegenerativeLikeRule(LeftHandRule leftHandRule,
			RightHandRule rightHandRule) throws PlinguaCoreException {
		super(leftHandRule, rightHandRule);
		// TODO Auto-generated constructor stub
	}

	public SimpleCommunicationRegenerativeLikeRule(LeftHandRule leftHandRule,
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

}
