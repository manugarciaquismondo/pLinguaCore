package org.gcn.plinguacore.util.psystem.rule.checkRule.specificCheckRule.probabilisticGuarded;

import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.CheckRule;
import org.gcn.plinguacore.util.psystem.rule.checkRule.DecoratorCheckRule;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveGuard;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRule;

public abstract class NoMultipleGuards extends ProbabilisticGuardedCheckRule {

	public NoMultipleGuards() {
		super();

		// TODO Auto-generated constructor stub
	}
	
	public NoMultipleGuards(CheckRule cr) {
		super(cr);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	protected boolean checkSpecificPart(IRule r) {

		ProbabilisticGuardedRule rule =checkProbabilisticGuardedRule(r);
		if(rule==null) return false;
		MultiSet<String> affectedObjects = getCheckedMultiSet(rule);
		MultiSet<String >affectedFlags=getAffectedGuards(affectedObjects, rule);
		if(affectedFlags.size()>1){
			cause= "Rules in a Probabilistic Guarded P system can only "+operationName()+" one guard. However, rule "+r+" "+operationName()+"s flags "+affectedFlags;
			return false;
		}
		return true;
	}
	
	protected abstract String operationName();







	protected abstract MultiSet<String> getCheckedMultiSet(ProbabilisticGuardedRule rule);


	private MultiSet<String> getAffectedGuards(
			MultiSet<String> generatedObjects, ProbabilisticGuardedRule rule) {
		// TODO Auto-generated method stub
		MultiSet<String> generatedFlags= new HashMultiSet<String>();
		for(String object : generatedObjects){
			if(isAFlag(object, rule)&&!generatedFlags.contains(object))
				generatedFlags.add(object, generatedObjects.count(object));
		}
		return generatedFlags;
		
	}


}
