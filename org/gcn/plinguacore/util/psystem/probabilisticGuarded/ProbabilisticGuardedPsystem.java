package org.gcn.plinguacore.util.psystem.probabilisticGuarded;

import java.util.HashSet;
import java.util.Set;


import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.AlphabetObject;
import org.gcn.plinguacore.util.psystem.rule.AbstractRuleFactory;
import org.gcn.plinguacore.util.psystem.rule.HandRule;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.LeftHandRule;
import org.gcn.plinguacore.util.psystem.rule.OuterRuleMembrane;
import org.gcn.plinguacore.util.psystem.rule.RightHandRule;
import org.gcn.plinguacore.util.psystem.rule.RulesSet;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveGuard;
import org.gcn.plinguacore.util.psystem.rule.guard.probabilisticGuarded.RestrictiveUnaryUnitGuard;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRule;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRuleBlockTable;
import org.gcn.plinguacore.util.psystem.rule.probabilisticGuarded.ProbabilisticGuardedRuleFactory;
import org.gcn.plinguacore.util.psystem.simplekernel.SimpleKernelLikePsystem;

public class ProbabilisticGuardedPsystem extends SimpleKernelLikePsystem {
	
	
	public static final String FLAG = "flag";

	private boolean dummyMode;
	
	private DummyMode dummyModeConfig;
	
	
	private ProbabilisticGuardedRuleBlockTable blockTable;

	public ProbabilisticGuardedPsystem() {
		super();
		blockTable= new ProbabilisticGuardedRuleBlockTable(getFlags());
		enableStrongDummyMode();
		
		// TODO Auto-generated constructor stub
	}
	
	public void enableStrongDummyMode(){
		this.dummyModeConfig=new StrongDummyMode();
		enableDummyMode();
	}
	
	public void enableWeakDummyMode(){
		this.dummyModeConfig=new DummyMode();
		enableDummyMode();
	}
	
	protected void enableDummyMode(){ 
		if(!isFlag(DummyMarkers.DUMMY_FLAG))
			addFlag(DummyMarkers.DUMMY_FLAG);
		dummyMode=true;
	}
	
	protected void disableDummyMode(){
		if(isFlag(DummyMarkers.DUMMY_FLAG))
			removeFlag(DummyMarkers.DUMMY_FLAG);
		dummyMode=false;
	}
	
	public void removeFlag(String flag) {
		if(flag!=null){
			//this.flags.remove(flag);
			this.removeProperty(flag, FLAG);
			//this.getAlphabet().remove(new AlphabetObject(flag));
			blockTable.setFlags(getFlags());
			removeFlagFromRules(flag);
		}
	}
		
	public void setDummyMode(String mode){
		setDummyMode(DummyMarkers.getMode(mode));
	}
	
	public void setDummyMode(byte mode){
		switch(mode){
		
			case(DummyMarkers.NO_DUMMY_MODE): 
				disableDummyMode(); break;
			case(DummyMarkers.WEAK_DUMMY_MODE):
				enableWeakDummyMode(); break;
			case(DummyMarkers.STRONG_DUMMY_MODE):
				enableStrongDummyMode(); break;
		}
			
		
	}

	private void removeFlagFromRules(String flag) {
		for(IRule rule: getRules()){
			(((RestrictiveGuard)((ProbabilisticGuardedRule)rule).getGuard())).removeFlag(flag);
		}			
	}

	@Override
	public boolean addRule(IRule r) {
		
		// TODO Auto-generated method stub
		if(!(r instanceof ProbabilisticGuardedRule)) return false;
		
		IRule ruleToAdd = r;
		if(dummyMode)
			ruleToAdd = dummyRule((ProbabilisticGuardedRule)r);
		if(!( super.addRule(ruleToAdd))) return false;
		ProbabilisticGuardedRule castRule = (ProbabilisticGuardedRule) ruleToAdd;
		((RestrictiveGuard)castRule
				.getGuard())
				.setFlags(getFlags());
		
		return (blockTable.addRule((ProbabilisticGuardedRule)r));
		
	}




	private IRule dummyRule(ProbabilisticGuardedRule r) {
		// TODO Auto-generated method stub
		return dummyModeConfig.dummyRule(r, getFlags());
	}

	public void addFlag(String flag){
		if(flag!=null){
			//this.flags.add(flag);
			addProperty(flag, FLAG);			
			blockTable.setFlags(getFlags());
			addFlagToRules(flag);
		}
	}
	
	private void addFlagToRules(String flag) {
		for(IRule rule: getRules()){
			(((RestrictiveGuard)((ProbabilisticGuardedRule)rule).getGuard())).addFlag(flag);
		}
		
	}

	public Set<String> getFlags(){
		return new HashSet<String>(getPropertiedObjects(FLAG));
	}
	
	public boolean isFlag(String object){
		return hasProperty(object, FLAG);
		//return flags.contains(object);
	}
	
	public ProbabilisticGuardedRuleBlockTable getBlockTable(){
		return blockTable;
	}
	
	@Override
	public String toString(){
		return "Flags:\n"+getFlags()+"\n\n"+super.toString();
	}
	
	public byte getDummyMode(){
		if(!dummyMode|| dummyModeConfig==null)
			return DummyMarkers.NO_DUMMY_MODE;
		else return dummyModeConfig.getMode();
			
	}
	
	public int getNumberOfBlocks(){
		return blockTable.size();
	}


}
