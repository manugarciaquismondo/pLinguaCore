package org.gcn.plinguacore.util.psystem.checkPsystem.specificCheckPsystem.probabilisticGuarded;

import java.util.HashSet;
import java.util.Set;

import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.checkPsystem.CheckPsystem;
import org.gcn.plinguacore.util.psystem.checkPsystem.DecoratorCheckPsystem;
import org.gcn.plinguacore.util.psystem.probabilisticGuarded.ProbabilisticGuardedPsystem;
import org.gcn.plinguacore.util.psystem.rule.HandRule;
import org.gcn.plinguacore.util.psystem.rule.IKernelRule;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.RulesSet;
import org.gcn.plinguacore.util.psystem.rule.guard.GeneralUnitGuard;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveGuard;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRule;

public class GuardObjectsAreFlags extends DecoratorCheckPsystem {

	private String specificCause;
	
	@Override
	protected boolean checkSpecificPart(Psystem r) {
		// TODO Auto-generated method stub
		specificCause="";
		if(! (r instanceof ProbabilisticGuardedPsystem)) return false;
		ProbabilisticGuardedPsystem psystem = (ProbabilisticGuardedPsystem)r;
		return checkThatGuardObjectsAreFlags(psystem.getRules(), psystem.getFlags());
		
	}

	public GuardObjectsAreFlags() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GuardObjectsAreFlags(CheckPsystem cr) {
		super(cr);
		// TODO Auto-generated constructor stub
	}

	private boolean checkThatGuardObjectsAreFlags(RulesSet rules,
			Set<String> flags) {
		// TODO Auto-generated method stub
		for(IRule r: rules){
			if(!checkObjectsAndFlags(r, flags)) return false;
			
		}
		return true;
	}

	private boolean checkObjectsAndFlags(IRule r, Set<String> flags) {
		boolean result=true;
		result=result&&checkThatRuleIsKernelRule(r);
		result=result&&checkThatRuleDoesNotConsumeMoreThanOneFlag(r, flags);
		result=result&&checkThatRuleDoesNotGenerateMoreThanOneFlag(r, flags);
		result=result&&checkThatAllObjectsAreFlagsAndGuardMultiSetCardinalityIsLessOrEqualTo1(r, flags);
		result=result&&checkThatFlagConsumingRulesGuardThem(r, flags);
		return result;
		
	}
	
	private boolean checkThatFlagConsumingRulesGuardThem(IRule r,
			Set<String> flags) {
		String flag = findFlag(r.getLeftHandRule().getOuterRuleMembrane().getMultiSet(), flags);
		if(flag==null) return true;
		GeneralUnitGuard guard = (GeneralUnitGuard)((ProbabilisticGuardedRule)r).getGuard();
		boolean result = flag.equals(guard.getObj());
		if(!result)
			specificCause+= "Rules which consume a flag should have a guard checking that flag.\n However, rule "+r+" consumes flag "+flag+", has guard "+guard+", and the flags are "+flags+"\n";
		return result;
		// TODO Auto-generated method stub
		
	}

	private String findFlag(MultiSet<String> multiSet, Set<String> flags) {
		// TODO Auto-generated method stub
		for(String flag: flags)
			if(multiSet.contains(flag))
				return flag;
		return null;
	}

	protected boolean checkThatAllObjectsAreFlagsAndGuardMultiSetCardinalityIsLessOrEqualTo1(IRule r, Set<String> flags){
		MultiSet<String> guardMultiSet = ((IKernelRule)r).getGuard().getMultiSet();
		boolean result=true;
		result=result&&checkThatMultiSetCardinalityIsLessOrEqualThan1(r.toString(), guardMultiSet);
		result=result&&checkThatAllObjectsAreFlags(r.toString(), flags, guardMultiSet);
		return result;
	}

	protected boolean checkThatMultiSetCardinalityIsLessOrEqualThan1(String ruleString,
			MultiSet<String> guardMultiSet) {
		if(guardMultiSet.size()>1){
			specificCause+= "Cardinalities of guards in Probabilistic Guarded P Systems must be less or equal to 0. In rule "+ruleString+", it is "+guardMultiSet.size()+"\n";
			return false;
		}
		return true;
	}

	protected boolean checkThatAllObjectsAreFlags(String ruleString,
			Set<String> flags, MultiSet<String> guardMultiSet) {
		if(!(flags.containsAll(guardMultiSet))){
			specificCause+= "All guarded objects must be flags. In rule "+ruleString+", it does not happen for flags: "+flags+"\n";
			return false;
		}
		return true;
	}

	protected boolean checkThatRuleDoesNotGenerateMoreThanOneFlag(IRule r,
			Set<String> flags) {

		return checkConsumptionAndGeneration(r.getRightHandRule(), flags, "generate", r.toString());
	}

	protected boolean checkThatRuleDoesNotConsumeMoreThanOneFlag(IRule r,
			Set<String> flags) {
		return checkConsumptionAndGeneration(r.getLeftHandRule(), flags, "consume", r.toString());
		
	}
	
	
	protected boolean checkConsumptionAndGeneration(HandRule handSide, Set<String> flags, String message, String ruleString){
		MultiSet<String> multiSetCopy = new HashMultiSet<String>(handSide.getOuterRuleMembrane().getMultiSet());
		multiSetCopy.retainAll(flags);
		long consumedFlags = multiSetCopy.size(); 
		if(consumedFlags>1){
			specificCause +="Probabilistic Guarded P Systems rules cannot "+message+" more than one flag objects. Rule "+ruleString+" ·"+message+"s "+consumedFlags+" flags\n";
			return false;
		}
		return true;
	}

	protected boolean checkThatRuleIsKernelRule(IRule r) {
		if(! (r instanceof IKernelRule)) {
			specificCause+= "Rule "+r+" must be of type IKernelRule\n";
			return false;
		}
		return true;
	}

	@Override
	protected String getSpecificCause() {
		// TODO Auto-generated method stub
		return specificCause;
	}

}
