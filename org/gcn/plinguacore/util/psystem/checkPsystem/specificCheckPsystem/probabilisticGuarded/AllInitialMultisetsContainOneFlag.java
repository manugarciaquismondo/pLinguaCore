package org.gcn.plinguacore.util.psystem.checkPsystem.specificCheckPsystem.probabilisticGuarded;

import java.util.Map;
import java.util.Map.Entry;

import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.checkPsystem.CheckPsystem;
import org.gcn.plinguacore.util.psystem.checkPsystem.DecoratorCheckPsystem;
import org.gcn.plinguacore.util.psystem.probabilisticGuarded.ProbabilisticGuardedPsystem;

public class AllInitialMultisetsContainOneFlag extends DecoratorCheckPsystem {

	ProbabilisticGuardedPsystem psystem;
	String specificCause;
	
	@Override
	protected boolean checkSpecificPart(Psystem r) {
		// TODO Auto-generated method stub
		if(! (r instanceof ProbabilisticGuardedPsystem)) return false;
		specificCause="";
		psystem=(ProbabilisticGuardedPsystem)r;
		return checkMultiSets();
	}

	private boolean checkMultiSets() {
		boolean onlyOneFlag=true;
		Map<String, MultiSet<String>> initialMultiSets = psystem.getInitialMultiSets();
		for(Entry<String,MultiSet<String>> entry:initialMultiSets.entrySet() ){
			if(!hasOnlyOneFlag(entry.getKey(),entry.getValue()))
				onlyOneFlag=false;
		}
		return onlyOneFlag;
	}

	public AllInitialMultisetsContainOneFlag() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AllInitialMultisetsContainOneFlag(CheckPsystem cr) {
		super(cr);
		// TODO Auto-generated constructor stub
	}

	private boolean hasOnlyOneFlag(String label, MultiSet<String> multiSet) {
		// TODO Auto-generated method stub
		boolean flagFound=false;
		for(String object:multiSet){
			if(psystem.isFlag(object)){
				flagFound=true;
				long count=multiSet.count(object);
				if(count>1){
					specificCause="All initial multisets must have one and only one flag. However, multiset associated to membrane "+label+", which is "+multiSet+", has "+count+" flags of type \""+object+"\"\n";
					return false;
				}
			}
		}
		if(!flagFound){
			specificCause="All initial multisets must have one and only one flag. However, multiset associated to membrane "+label+", which is "+multiSet+", has no flag\n";
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
