package org.gcn.plinguacore.util.psystem.rule.regenerative;

import java.util.Set;

import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.regenerative.RegenerativePsystem;
import org.gcn.plinguacore.util.psystem.regenerative.membrane.RegenerativeMembrane;
import org.gcn.plinguacore.util.psystem.regenerative.membrane.RegenerativeMembraneStructure;
import org.gcn.plinguacore.util.psystem.rule.IKernelRule;
import org.gcn.plinguacore.util.psystem.rule.LeftHandRule;
import org.gcn.plinguacore.util.psystem.rule.OuterRuleMembrane;
import org.gcn.plinguacore.util.psystem.rule.RightHandRule;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.rule.simplekernel.DivisionKernelLikeRule;
import org.gcn.plinguacore.util.psystem.simplekernel.membrane.SimpleKernelLikeMembraneStructure;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembrane;

public class DivisionRegenerativeLikeRule extends DivisionKernelLikeRule
		implements IRegenerativeLikeRule {

	protected LinkObjectHandler linkHandler;
	protected PriorityHandler priorityHandler;
	public DivisionRegenerativeLikeRule(LeftHandRule leftHandRule, RightHandRule rightHandRule, Guard guard) throws PlinguaCoreException {
		super(false, leftHandRule, rightHandRule, guard);
		priorityHandler= new PriorityHandler(0);
		OuterRuleMembrane outerRuleMembrane= rightHandRule.getSecondOuterRuleMembrane();
		if(outerRuleMembrane!=null){
			throw new PlinguaCoreException("Second right outer membranes in Budding rules at Regenerative P systems must be passed as affected membranes");
		}
		if(rightHandRule.getAffectedMembranes().size()!=getRequiredAffectedMembranes()){
			throw new PlinguaCoreException("The number of affected membranes must be "+getRequiredAffectedMembranes()+". The set of affected membranes "+rightHandRule.getAffectedMembranes()+" is not valid");
		}
		linkHandler=new LinkObjectHandler(rightHandRule, getRequiredAffectedMembranes());
		
		// TODO Auto-generated constructor stub
	}
	
	public DivisionRegenerativeLikeRule(LeftHandRule leftHandRule, RightHandRule rightHandRule) throws PlinguaCoreException {
		this(leftHandRule, rightHandRule, null);
	}

	public void setLinkObjects(Set<String> linkObjects) throws PlinguaCoreException{
		linkHandler.setLinkObjects(linkObjects);
	}
	
	public int getObjectOffset(){
		return 1;
	}	
	
	@Override
	public void setMembraneStructure(
			SimpleKernelLikeMembraneStructure membraneStructure) {

		super.setMembraneStructure(linkHandler.transformMembraneStructure(membraneStructure));

		
	}

	
	
	
	protected int getRequiredAffectedMembranes() {
		// TODO Auto-generated method stub
		return 1;
	}
	
	@Override
	protected boolean executeSafe(ChangeableMembrane membrane,
			MultiSet<String> environment, long executions) {
		// TODO Auto-generated method stub
		boolean firstExecute= super.executeSafe(membrane, environment, executions);
		((RegenerativeMembrane)membrane).setLinkObjects(linkHandler.getLinkObjects());
		if(firstExecute){
			if(!(latestDividedMembrane instanceof RegenerativeMembrane)){
				try {
					latestDividedMembrane=createRegenerativeMembrane(latestDividedMembrane);
				} catch (PlinguaCoreException e) {
					// TODO Auto-generated catch block
					return false;
				}
			}
			RegenerativeMembrane regenerativeMembrane=((RegenerativeMembrane)latestDividedMembrane);
			regenerativeMembrane.setLinkObjects(linkHandler.getLinkObjects());
			if(linkHandler.getGeneratedLinkObject()!=null){
				regenerativeMembrane.setLinkObject(linkHandler.getGeneratedLinkObject(), linkHandler.getLinkObjects());
			}
			updateAdjacencies(membrane);
		}
		return firstExecute;
	}

	protected RegenerativeMembrane createRegenerativeMembrane(ChangeableMembrane membrane)
			throws PlinguaCoreException {
		return new RegenerativeMembrane(membrane, membraneStructure, true, linkHandler.getLinkObjects());
	}

	protected void updateAdjacencies(ChangeableMembrane membrane) {
		RegenerativeMembrane regenerativeMembrane=null;
		if(membrane instanceof RegenerativeMembrane)
			regenerativeMembrane=(RegenerativeMembrane)membrane;
		else{
			try {
				regenerativeMembrane=createRegenerativeMembrane(membrane);
			} catch (PlinguaCoreException e) {}
		}
		regenerativeMembrane.copyAdjacents((RegenerativeMembrane)latestDividedMembrane);
	}
	
	@Override
	protected ChangeableMembrane createDividedMembrane(
			ChangeableMembrane membrane) {
		((RegenerativeMembrane) membrane).setLinkObjects(linkHandler.getLinkObjects());
		RegenerativeMembrane regenerativeMembrane=(RegenerativeMembrane) membrane.divide();
		regenerativeMembrane.setLinkObjects(linkHandler.getLinkObjects());
		return regenerativeMembrane;
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
