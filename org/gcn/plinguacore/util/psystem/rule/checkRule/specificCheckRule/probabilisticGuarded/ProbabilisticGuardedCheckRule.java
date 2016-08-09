package org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.probabilisticGuarded;

import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.CheckRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.DecoratorCheckRule;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveGuard;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRule;

public abstract class ProbabilisticGuardedCheckRule extends DecoratorCheckRule {

	protected String cause;
	
	protected ProbabilisticGuardedRule checkProbabilisticGuardedRule(IRule r) {
		// TODO Auto-generated method stub
		if(! (r instanceof ProbabilisticGuardedRule)){
			cause="Only Probabilistic Guarded Rules are allowed. However, "+r.toString()+" is not a Probabilistic Guarded Rule";					
			return null;
		}
		return (ProbabilisticGuardedRule)r;
	}

	public ProbabilisticGuardedCheckRule() {
		super();
		cause="";
		// TODO Auto-generated constructor stub
	}

	public ProbabilisticGuardedCheckRule(CheckRule cr) {
		super(cr);
		cause="";
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected String getSpecificCause(){
		return cause;
	}
	

	protected boolean isAFlag(String object, ProbabilisticGuardedRule rule) {
		// TODO Auto-generated method stub
		return ((RestrictiveGuard)rule.getGuard()).isAFlag(object);
	}

}
