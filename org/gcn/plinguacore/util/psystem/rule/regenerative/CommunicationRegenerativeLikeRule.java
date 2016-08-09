package org.gcn.plinguacore.util.psystem.rule.regenerative;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Label;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.regenerative.membrane.RegenerativeMembrane;
import org.gcn.plinguacore.util.psystem.rule.IKernelRule;
import org.gcn.plinguacore.util.psystem.rule.LeftHandRule;
import org.gcn.plinguacore.util.psystem.rule.OuterRuleMembrane;
import org.gcn.plinguacore.util.psystem.rule.RightHandRule;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.rule.simplekernel.DivisionKernelLikeRule;
import org.gcn.plinguacore.util.psystem.rule.simplekernel.EvolutionCommunicationKernelLikeRule;
import org.gcn.plinguacore.util.psystem.rule.simplekernel.InputOutputKernelLikeRule;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembrane;


public class CommunicationRegenerativeLikeRule extends
EvolutionCommunicationKernelLikeRule implements IRegenerativeLikeRule {


	protected LinkObjectHandler linkHandler;
	protected Set<String> consumedLinkObjects, generatedLinkObjects;
	String firstConsumedLinkObject, firstGeneratedLinkObject;
	protected boolean sourceAndDestinationEqual;
	protected PriorityHandler priorityHandler;
	public CommunicationRegenerativeLikeRule(LeftHandRule leftHandRule, RightHandRule rightHandRule, Guard guard) throws PlinguaCoreException {
		super(false, leftHandRule, rightHandRule, guard, (byte)0);
		priorityHandler=new PriorityHandler(0);
		OuterRuleMembrane outerRuleMembrane= rightHandRule.getSecondOuterRuleMembrane();
		if(outerRuleMembrane!=null){
			throw new PlinguaCoreException("Second right outer membranes in Budding rules at Regenerative P systems must be passed as affected membranes");
		}
		if(rightHandRule.getAffectedMembranes().size()!=getRequiredAffectedMembranes()){
			throw new PlinguaCoreException("The number of affected membranes must be "+getRequiredAffectedMembranes()+". The set of affected membranes "+rightHandRule.getAffectedMembranes()+" is not valid");
		}
		sourceAndDestinationEqual=leftHandRule.getOuterRuleMembrane().getLabel().equals(rightHandRule.getOuterRuleMembrane().getLabel());
		linkHandler = new LinkObjectHandler(rightHandRule, getRequiredAffectedMembranes());
		// TODO Auto-generated constructor stub
	}
	
	public CommunicationRegenerativeLikeRule(LeftHandRule leftHandRule, RightHandRule rightHandRule) throws PlinguaCoreException {
		this(leftHandRule, rightHandRule, null);
		// TODO Auto-generated constructor stub
	}


	private void removeLinkObjects() throws PlinguaCoreException{
		OuterRuleMembrane leftOuterRuleMembrane=leftHandRule.getOuterRuleMembrane();
		OuterRuleMembrane rightOuterRuleMembrane=rightHandRule.getOuterRuleMembrane();
		MultiSet<String> leftMultiSet=new HashMultiSet<String>(leftOuterRuleMembrane.getMultiSet());
		MultiSet<String> rightMultiSet=new HashMultiSet<String>(rightOuterRuleMembrane.getMultiSet());
		consumedLinkObjects=new HashSet<String>(leftMultiSet.entrySet());
		generatedLinkObjects=new HashSet<String>(rightMultiSet.entrySet());
		consumedLinkObjects.retainAll(linkHandler.getLinkObjects());
		generatedLinkObjects.retainAll(linkHandler.getLinkObjects());
		if(!consumedLinkObjects.isEmpty()){
			firstConsumedLinkObject=consumedLinkObjects.iterator().next();
		}
		if(!generatedLinkObjects.isEmpty()){
			firstGeneratedLinkObject=generatedLinkObjects.iterator().next();
		}
		leftMultiSet.removeAll(linkHandler.getLinkObjects());
		rightMultiSet.removeAll(linkHandler.getLinkObjects());
		OuterRuleMembrane leftHandOuterRuleMembrane=new OuterRuleMembrane(
				leftOuterRuleMembrane.getLabelObj(), (byte)0, leftMultiSet);
		OuterRuleMembrane rightHandOuterRuleMembrane=new OuterRuleMembrane(
				rightOuterRuleMembrane.getLabelObj(), (byte)0, rightMultiSet);
		leftHandRule=new LeftHandRule(leftHandOuterRuleMembrane, new HashMultiSet<String>());
		rightHandRule=new RightHandRule(rightHandOuterRuleMembrane, new HashMultiSet<String>());

		// TODO Auto-generated method stub

	}

	public void setLinkObjects(Set<String> linkObjects) throws PlinguaCoreException{
		linkHandler.setLinkObjects(linkObjects);
		removeLinkObjects();
		checkSizeOfConsumedAndGeneratedObjects();

	}


	protected void checkSizeOfConsumedAndGeneratedObjects()
			throws PlinguaCoreException {
		String errorMessage="The number of consumed and generated link objects must be the same, but consumed objects are "+consumedLinkObjects+" and generated objects are "+generatedLinkObjects;
		if((consumedLinkObjects==null||consumedLinkObjects.isEmpty())!=(generatedLinkObjects==null||generatedLinkObjects.isEmpty())){
			throw new PlinguaCoreException(errorMessage);
		} else{
			if(consumedLinkObjects!=null&&generatedLinkObjects!=null){
				if(consumedLinkObjects.size()!=generatedLinkObjects.size()){
					throw new PlinguaCoreException(errorMessage);
				}
			}
		}
	}


	protected int getRequiredAffectedMembranes(){
		return 0;
	}

	@Override
	public long countExecutions(ChangeableMembrane membrane) {
		// TODO Auto-generated method stub
		setAndExtractLinkObject(membrane);
		long previousExecutions=super.countExecutions(membrane);
		previousExecutions = checkLinkObjectForConsumption(membrane,
				previousExecutions);
		return previousExecutions;


	}

	@Override
	public long countExecutions(ChangeableMembrane membrane,
			ChangeableMembrane original) {
		// TODO Auto-generated method stub
		setAndExtractLinkObject(original);
		return countExecutions(membrane);
	}

	protected long checkLinkObjectForConsumption(ChangeableMembrane membrane,
			long previousExecutions) {
		if(consumedLinkObjects!=null&&!consumedLinkObjects.isEmpty()){
			if(firstConsumedLinkObject!=null){
				if(!((RegenerativeMembrane)membrane).getLinkObject().equals(firstConsumedLinkObject)){
					previousExecutions=0;
				}

			}
		}
		return previousExecutions;
	}


	protected void setAndExtractLinkObject(ChangeableMembrane membrane) {
		if(((RegenerativeMembrane)membrane).getLinkObject()==null)
			try {
				((RegenerativeMembrane)membrane).setLinkObjects(linkHandler.getLinkObjects());
				((RegenerativeMembrane)membrane).extractLinkObject();
			} catch (PlinguaCoreException e) {
				// TODO Auto-generated catch block
			}
	}


	@Override
	protected boolean executeSafe(ChangeableMembrane membrane,
			MultiSet<String> environment, long executions) {
		// TODO Auto-generated method stub
		Set<Integer> visitedIDs=new HashSet<Integer>();
		((RegenerativeMembrane)membrane).setLinkObjects(linkHandler.getLinkObjects());
		RegenerativeMembrane appliedMembrane=(RegenerativeMembrane)membrane;
		MultiSet<String> rightMultiSet=this.getRightHandRule().getOuterRuleMembrane().getMultiSet();
		String labelOfInterest=this.getRightHandRule().getOuterRuleMembrane().getLabel();
		checkAndSubstractMultiSet(membrane, executions);
		Iterator<TissueLikeMembrane> membranesWithMatchingLabel= membraneStructure.iterator(labelOfInterest);
		boolean applied=false;
		while(membranesWithMatchingLabel.hasNext()){
			RegenerativeMembrane regenerativeMembrane=(RegenerativeMembrane)membranesWithMatchingLabel.next();
			if(!visitedIDs.contains(regenerativeMembrane.getId())){
				visitedIDs.add(regenerativeMembrane.getId());
				regenerativeMembrane.setLinkObjects(linkHandler.getLinkObjects());
				if(isDestinationMembrane(appliedMembrane, regenerativeMembrane)){
					addMultiSet(rightMultiSet, regenerativeMembrane.getMultiSet(), executions);
					((RegenerativeMembrane)regenerativeMembrane).setLinkObjects(linkHandler.getLinkObjects());
					applied=true;
				}
			}
		}
		if(sourceAndDestinationEqual&&generatedLinkObjects!=null&&generatedLinkObjects.size()>0){
			((RegenerativeMembrane)membrane).setLinkObject(firstGeneratedLinkObject, linkHandler.getLinkObjects());
		}
		return applied;
	}

	protected boolean isDestinationMembrane(
			RegenerativeMembrane appliedMembrane,
			RegenerativeMembrane regenerativeMembrane) {
		return appliedMembrane.isAdjacent(regenerativeMembrane)||(appliedMembrane.equals(regenerativeMembrane));
	}

	public int getObjectOffset(){
		return 0;
	}

	@Override
	public String getArrow(){
		return " --> ";
	}

	@Override
	public void setPriority(int priority){
		priorityHandler.setPriority(priority);
	}

	@Override
	public int getPriority(){
		return priorityHandler.getPriority();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return priorityHandler.printRuleWithPriority(super.toString());
			
	}
}
