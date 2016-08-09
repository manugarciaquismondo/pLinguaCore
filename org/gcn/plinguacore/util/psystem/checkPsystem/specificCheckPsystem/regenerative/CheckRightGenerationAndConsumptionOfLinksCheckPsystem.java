package org.gcn.plinguacore.util.psystem.checkPsystem.specificCheckPsystem.regenerative;

import java.util.HashSet;
import java.util.Set;

import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.checkPsystem.CheckPsystem;
import org.gcn.plinguacore.util.psystem.checkPsystem.DecoratorCheckPsystem;
import org.gcn.plinguacore.util.psystem.regenerative.RegenerativePsystem;
import org.gcn.plinguacore.util.psystem.rule.IRule;
import org.gcn.plinguacore.util.psystem.rule.OuterRuleMembrane;
import org.gcn.plinguacore.util.psystem.rule.regenerative.CommunicationRegenerativeLikeRule;
import org.gcn.plinguacore.util.psystem.rule.regenerative.IRegenerativeLikeRule;
import org.gcn.plinguacore.util.psystem.rule.regenerative.LinkingRegenerativeLikeRule;

public class CheckRightGenerationAndConsumptionOfLinksCheckPsystem extends
		DecoratorCheckPsystem {


	public CheckRightGenerationAndConsumptionOfLinksCheckPsystem(CheckPsystem cr) {
		super(cr);
		// TODO Auto-generated constructor stub
	}

	protected RegenerativePsystem psystem;
	protected String specificCause;
	@Override
	protected boolean checkSpecificPart(Psystem r) {
		// TODO Auto-generated method stub
		boolean systemPassedCheck=true;
		specificCause="";
		psystem=(RegenerativePsystem)r;
		for(IRule rule: r.getRules()){
			systemPassedCheck = systemPassedCheck&&checkLinkConsumptionAndGenerationForRule(systemPassedCheck, rule);
		}
		return systemPassedCheck;
	}

	protected boolean checkLinkConsumptionAndGenerationForRule(
			boolean systemPassedCheck, IRule rule) {
		Set<String> consumedLinkObjects=countLinkObjects(rule.getLeftHandRule().getOuterRuleMembrane().getMultiSet());
		if(notIsCommunicationWithSameSideLabels(rule)){
			systemPassedCheck = checkLinksForNotCommunicationRules(
					systemPassedCheck, rule, consumedLinkObjects);
		}
		else{
			systemPassedCheck = checkLinksForCommunicationRules(
					systemPassedCheck, rule, consumedLinkObjects);
			
		}
		return systemPassedCheck;
	}

	protected boolean notIsCommunicationWithSameSideLabels(IRule rule) {
		if(rule instanceof CommunicationRegenerativeLikeRule&&!(rule instanceof LinkingRegenerativeLikeRule))
			return !(rule.getLeftHandRule().getOuterRuleMembrane().getLabel().equals(rule.getRightHandRule().getOuterRuleMembrane().getLabel()));
		else{
			return true;
		}
	}

	protected boolean checkLinksForCommunicationRules(
			boolean systemPassedCheck, IRule rule,
			Set<String> consumedLinkObjects) {
		if(consumedLinkObjects.size()>1){
			systemPassedCheck=false;
			specificCause="A rule such as "+rule+" cannot consume more than one link objects, but it consumes: "+consumedLinkObjects;
		}
		systemPassedCheck=checkGeneratedMembranes(systemPassedCheck, rule,consumedLinkObjects, consumedLinkObjects.size());
		return systemPassedCheck;
	}

	protected boolean checkLinksForNotCommunicationRules(
			boolean systemPassedCheck, IRule rule,
			Set<String> consumedLinkObjects) {
		boolean localeSystemPassedCheck=true;
		if(consumedLinkObjects.size()>0){
			systemPassedCheck=false;
			localeSystemPassedCheck=false;
			specificCause+="A rule such as "+rule+" cannot consume link objects: "+consumedLinkObjects+"\n";
		}
		if(localeSystemPassedCheck){
			int expectedCount=0;
			if(rule instanceof LinkingRegenerativeLikeRule){
				expectedCount=1;
			}
			systemPassedCheck = checkGeneratedMembranes(
					systemPassedCheck, rule, consumedLinkObjects,
					expectedCount);
		}
		return systemPassedCheck;
	}

	protected boolean checkGeneratedMembranes(boolean systemPassedCheck,
			IRule rule, Set<String> consumedLinkObjects, int expectedCount) {
		boolean localeSystemPassedCheck=systemPassedCheck;
		int offsetLinkObjects=((IRegenerativeLikeRule)rule).getObjectOffset();
		localeSystemPassedCheck=countAndCheckGeneratedLinkObjects(rule.getRightHandRule().getOuterRuleMembrane().getMultiSet(), consumedLinkObjects, expectedCount, rule);
		for(OuterRuleMembrane membrane: rule.getRightHandRule().getAffectedMembranes()){
			localeSystemPassedCheck=localeSystemPassedCheck&&countAndCheckGeneratedLinkObjects(membrane.getMultiSet(), consumedLinkObjects, expectedCount+offsetLinkObjects, rule);
		}
		return localeSystemPassedCheck;
	}

	protected boolean countAndCheckGeneratedLinkObjects(MultiSet<String> multiSet,
			Set<String> consumedLinkObjects, int requiredCount, IRule rule) {
		Set<String> generatedLinkObjects = countLinkObjects(multiSet);
		boolean systemPassedCheck = true;
		if(requiredCount!=generatedLinkObjects.size()){
			systemPassedCheck=false;
			specificCause="A regenerative rule such as "+rule+" must consume exactly "+requiredCount+" link objects, but it consumes: "+consumedLinkObjects+" and generates: "+generatedLinkObjects;
		}
		return systemPassedCheck;
	}

	private Set<String> countLinkObjects(MultiSet<String> multiSet) {
		// TODO Auto-generated method stub
		Set<String> linkObjects=new HashSet<String>();
		for(String object:multiSet.entrySet()){
			if(psystem.isLinkObject(object))
				linkObjects.add(object);
		}
		return linkObjects;
	}

	@Override
	protected String getSpecificCause() {
		// TODO Auto-generated method stub
		return specificCause;
	}

}
