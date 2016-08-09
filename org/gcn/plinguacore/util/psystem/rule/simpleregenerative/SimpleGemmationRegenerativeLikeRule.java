package org.gcn.plinguacore.util.psystem.rule.simpleregenerative;

import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.regenerative.membrane.RegenerativeMembrane;
import org.gcn.plinguacore.util.psystem.rule.IKernelRule;
import org.gcn.plinguacore.util.psystem.rule.LeftHandRule;
import org.gcn.plinguacore.util.psystem.rule.RightHandRule;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.rule.regenerative.GemmationRegenerativeLikeRule;
import org.gcn.plinguacore.util.psystem.simpleregenerative.membrane.SimpleRegenerativeMembrane;

public class SimpleGemmationRegenerativeLikeRule extends
		GemmationRegenerativeLikeRule {

	protected MembraneStructureCounter membraneStructureCounter;
	public SimpleGemmationRegenerativeLikeRule(LeftHandRule leftHandRule,
			RightHandRule rightHandRule) throws PlinguaCoreException {
		super(leftHandRule, rightHandRule);
		membraneStructureCounter= new MembraneStructureCounter();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected RegenerativeMembrane createRegenerativeMembrane(ChangeableMembrane membrane)
			throws PlinguaCoreException {
		// TODO Auto-generated method stub
		return new SimpleRegenerativeMembrane(membrane, membraneStructure, true);
	}

	public SimpleGemmationRegenerativeLikeRule(LeftHandRule leftHandRule,
			RightHandRule rightHandRule, Guard guard)
			throws PlinguaCoreException {
		super(leftHandRule, rightHandRule, guard);
		membraneStructureCounter= new MembraneStructureCounter();
		// TODO Auto-generated constructor stub
	}

	@Override
	public long countExecutions(ChangeableMembrane membrane) {
		// TODO Auto-generated method stub
		if(membraneStructureCounter.countExistingMembranes(this.getRightHandRule().getAffectedMembranes(), this.getRightHandRule().getSecondOuterRuleMembrane(), membraneStructure)>0)
			return 0;
		return super.countExecutions(membrane);
	}
}
