package org.gcn.plinguacore.util.psystem.rule.simplekernel;

import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.rule.IDoubleCommunicationRule;
import org.gcn.plinguacore.util.psystem.rule.LeftHandRule;
import org.gcn.plinguacore.util.psystem.rule.RightHandRule;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.rule.tissueLike.TissueLikeRuleFactory;

public class InputOutputKernelLikeRule extends DivisionKernelLikeRule implements IDoubleCommunicationRule{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected IDoubleCommunicationRule innerRule;
	
	protected InputOutputKernelLikeRule(boolean dissolves,	LeftHandRule leftHandRule, RightHandRule rightHandRule, Guard guard) {
		super(dissolves, leftHandRule, rightHandRule, guard);
		this.innerRule = new TissueLikeRuleFactory().createDoubleCommunicationRule(dissolves, leftHandRule, rightHandRule);
		// TODO Auto-generated constructor stub
	}

	public InputOutputKernelLikeRule(boolean dissolves,
			LeftHandRule leftHandRule, RightHandRule rightHandRule) {
		super(dissolves, leftHandRule, rightHandRule);
		this.innerRule = new TissueLikeRuleFactory().createDoubleCommunicationRule(dissolves, leftHandRule, rightHandRule);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public byte getRuleType() {
		  return KernelRuleTypes.INPUT_OUTPUT;
		 }
	
	 @Override
	 public boolean execute(ChangeableMembrane membrane,
	   MultiSet<String> environment, long executions) {
	  // TODO Auto-generated method stub
	  return innerRule.execute(membrane, environment, executions);
	 }
	 
	 @Override
	 public long countExecutions(ChangeableMembrane membrane) {
		  if (!guardEvaluates(membrane)) return 0;
		  return innerRule.countExecutions(membrane);		  
	 }

}
