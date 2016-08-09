package org.gcn.plinguacore.util.psystem.rule.regenerative;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.membrane.ChangeableMembrane;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.regenerative.RegenerativePsystem;
import org.gcn.plinguacore.util.psystem.regenerative.membrane.RegenerativeMembrane;
import org.gcn.plinguacore.util.psystem.rule.IKernelRule;
import org.gcn.plinguacore.util.psystem.rule.LeftHandRule;
import org.gcn.plinguacore.util.psystem.rule.OuterRuleMembrane;
import org.gcn.plinguacore.util.psystem.rule.RightHandRule;
import org.gcn.plinguacore.util.psystem.rule.guard.Guard;
import org.gcn.plinguacore.util.psystem.rule.simplekernel.InputOutputKernelLikeRule;
import org.gcn.plinguacore.util.psystem.simplekernel.membrane.SimpleKernelLikeMembraneStructure;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembrane;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembraneStructure;


public class LinkingRegenerativeLikeRule extends CommunicationRegenerativeLikeRule
		implements IKernelRule {
	protected String linkObject;
	protected MultiSet<String> rightHandMultiSet;
	protected Set<RegenerativeMembrane> destinationMembranes;
	public LinkingRegenerativeLikeRule(LeftHandRule leftHandRule,
			RightHandRule rightHandRule, Guard guard) throws PlinguaCoreException {
		super(leftHandRule, rightHandRule, guard);
		initializeApplicationFields(rightHandRule);
		initializeRuleAttributes(rightHandRule, false);
		if(sourceAndDestinationEqual)
			throw new PlinguaCoreException("In linking regenerative rules, source and destination labels cannot be equal");
		// TODO Auto-generated constructor stub
	}
	
	public LinkingRegenerativeLikeRule(LeftHandRule leftHandRule,
			RightHandRule rightHandRule, String linkObject, Guard guard) throws PlinguaCoreException {
		super(leftHandRule, rightHandRule, guard);
		initializeApplicationFields(rightHandRule);
		this.linkObject=linkObject;
		initializeRuleAttributes(rightHandRule, true);
		// TODO Auto-generated constructor stub
		
	}
	
	public LinkingRegenerativeLikeRule(LeftHandRule leftHandRule,
			RightHandRule rightHandRule, String linkObject) throws PlinguaCoreException {
		this(leftHandRule, rightHandRule, linkObject, null);
		// TODO Auto-generated constructor stub
		
	}
	
	public LinkingRegenerativeLikeRule(LeftHandRule leftHandRule,
			RightHandRule rightHandRule) throws PlinguaCoreException {
		super(leftHandRule, rightHandRule);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setLinkObjects(Set<String> linkObjects) throws PlinguaCoreException{
		updateLinkHandlerLinkObjects(linkObjects);
	}

	@Override
	public void setMembraneStructure(
			SimpleKernelLikeMembraneStructure membraneStructure) {
		// TODO Auto-generated method stub
		super.setMembraneStructure(membraneStructure);
		extractLinkingObjectsFromMembraneStructure();
	}

	protected void extractLinkingObjectsFromMembraneStructure() {
		try{
			updateLinkObjects(this.membraneStructure);
			if(linkObject==null){
				linkObject=extractAndRemoveLinkObject();
			}
			else{
				checkLinkingObjects(0);
			}
		}
		catch(PlinguaCoreException e){
			System.err.println(e.getMessage());
			super.setMembraneStructure(null);
		}
	}

	protected void updateLinkObjects(
			SimpleKernelLikeMembraneStructure membraneStructure) throws PlinguaCoreException {
		Set<String> candidateLinkObjects=null;
		if(membraneStructure.getPsystem()!=null){
			candidateLinkObjects=((RegenerativePsystem)membraneStructure.getPsystem()).getLinkObjects();
		}
		if(candidateLinkObjects!=null&&!candidateLinkObjects.isEmpty()&&(linkHandler.getLinkObjects()==null||linkHandler.getLinkObjects().isEmpty())){
			updateLinkHandlerLinkObjects(candidateLinkObjects);
		}
	}

	protected void updateLinkHandlerLinkObjects(Set<String> candidateLinkObjects)
			throws PlinguaCoreException {
		linkHandler.setLinkObjects(candidateLinkObjects);
	}

	

	protected void initializeApplicationFields(RightHandRule rightHandRule) {
		rightHandMultiSet=rightHandRule.getOuterRuleMembrane().getMultiSet();
		destinationMembranes=new HashSet<RegenerativeMembrane>();
	}

	protected void initializeRuleAttributes(RightHandRule rightHandRule, boolean checkLinkObject) throws PlinguaCoreException {
		OuterRuleMembrane outerRuleMembrane= rightHandRule.getSecondOuterRuleMembrane();
		if(outerRuleMembrane!=null){
			throw new PlinguaCoreException("Linking rules in Regenerative P systems cannot have more than one membrane on the right-hand side, but the extra membranes are "+outerRuleMembrane);
		}
		if(checkLinkObject&&(linkObject==null||linkObject.isEmpty())){
			throw new NullPointerException("The link object for a Linking Regenerative Rule cannot be null nor empty");
		}
	}
	private String extractAndRemoveLinkObject() throws PlinguaCoreException {
		// TODO Auto-generated method stub
		MultiSet<String> bufferMultiSet = checkLinkingObjects(1);
		return bufferMultiSet.iterator().next();
	}

	protected MultiSet<String> checkLinkingObjects(int expectedLinkObjects)
			throws PlinguaCoreException {
		MultiSet<String> bufferMultiSet=new HashMultiSet<String>(rightHandMultiSet);
		bufferMultiSet.retainAll(linkHandler.getLinkObjects());
		if(expectedLinkObjects>0&&bufferMultiSet.isEmpty()){
			throw new PlinguaCoreException("No link object present in the right-hand side of the linking rule "+this.toString());
		}
		if(bufferMultiSet.size()!=expectedLinkObjects){
			bufferMultiSet.remove(linkObject);
			if(bufferMultiSet.size()!=expectedLinkObjects){
				throw new PlinguaCoreException("The multiset: ["+rightHandMultiSet+"] contains more or less than one linking objects. All linking objects in the multiset are: ["+bufferMultiSet+"] and all linking objects in the system are: ["+linkHandler.getLinkObjects()+"]");
			}
		}
		return bufferMultiSet;
	}
	@Override
	protected boolean executeSafe(ChangeableMembrane membrane,
			MultiSet<String> environment, long executions) {
		// TODO Auto-generated method stub
		destinationMembranes.clear();
		RegenerativeMembrane leftHandRegenerativeMembrane=(RegenerativeMembrane)membrane;
		leftHandRegenerativeMembrane.setLinkObjects(linkHandler.getLinkObjects());
		Iterator<TissueLikeMembrane> membraneIterator=this.membraneStructure.iterator(rightHandRule.getOuterRuleMembrane().getLabel());
		while (membraneIterator.hasNext()) {
			TissueLikeMembrane iteratedMembrane=membraneIterator.next();
			RegenerativeMembrane regenerativeMembrane=(RegenerativeMembrane) iteratedMembrane;
			regenerativeMembrane.setLinkObjects(linkHandler.getLinkObjects());
			checkLinkProcessAndRegisterMembrane(leftHandRegenerativeMembrane, regenerativeMembrane);
		}
		return super.executeSafe(membrane, environment, executions);
		
	}

	protected void checkLinkProcessAndRegisterMembrane(RegenerativeMembrane leftHandSideMembrane,
			RegenerativeMembrane processedMembrane) {
		RegenerativeMembrane membraneToRegister=checkLinkAndProcessMembrane(leftHandSideMembrane, processedMembrane);
		if(membraneToRegister!=null){
			destinationMembranes.add(processedMembrane);
		}
	}

	protected RegenerativeMembrane checkLinkAndProcessMembrane(
			RegenerativeMembrane leftHandSideMembrane,
			RegenerativeMembrane processedMembrane) {
		 if( processLink(leftHandSideMembrane, processedMembrane))
			 return processedMembrane;
		 else
			 return null;
		 
	}

	protected boolean processLink(RegenerativeMembrane leftHandSideMembrane,
			RegenerativeMembrane processedMembrane) {
		return processedMembrane.addLink(leftHandSideMembrane, linkObject);
	}
	
	protected boolean isDestinationMembrane(
			RegenerativeMembrane appliedMembrane,
			RegenerativeMembrane regenerativeMembrane) {
		return appliedMembrane.isAdjacent(regenerativeMembrane)&&destinationMembranes.contains(regenerativeMembrane);
	}
	@Override
	public long countExecutions(ChangeableMembrane membrane) {
		// TODO Auto-generated method stub
		if(!existLinkableMembranes((RegenerativeMembrane) membrane))
			return 0;
		long executionsBuffer= super.countExecutions(membrane);
		if(leftHandRule.getOuterRuleMembrane().getMultiSet().isEmpty())
			executionsBuffer=Math.min(executionsBuffer, 1l);
		return executionsBuffer;
	}

	@Override
	public long countExecutions(ChangeableMembrane membrane,
			ChangeableMembrane original){
		return countExecutions(membrane);
		
	}
	public boolean existLinkableMembranes(RegenerativeMembrane membrane){
		Iterator<TissueLikeMembrane> membraneIterator=this.membraneStructure.iterator(rightHandRule.getOuterRuleMembrane().getLabel());
		while (membraneIterator.hasNext()) {
			TissueLikeMembrane iteratedMembrane=membraneIterator.next();
			RegenerativeMembrane regenerativeMembrane=(RegenerativeMembrane) iteratedMembrane;
			regenerativeMembrane.setLinkObjects(linkHandler.getLinkObjects());
			if(isLinkableMembrane(membrane, regenerativeMembrane))
				return true;
		}
		return false;
			
	}

	protected boolean isLinkableMembrane(RegenerativeMembrane membrane,
			RegenerativeMembrane regenerativeMembrane) {
		return linkObject.equals(regenerativeMembrane.getLinkObject())&&areNotAdjacentMembranes(membrane, regenerativeMembrane);
	}

	protected boolean areNotAdjacentMembranes(RegenerativeMembrane membrane,
			RegenerativeMembrane regenerativeMembrane) {
		return !regenerativeMembrane.isAdjacent(membrane)&&!membrane.isAdjacent(regenerativeMembrane);
	}
		
	@Override
	public String getArrow(){
		return " |--> ";
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return printLinkObject()+baseString();
	}

	protected String baseString() {
		return super.toString();
	}

	private String printLinkObject() {
		// TODO Auto-generated method stub
		return "<"+linkObject+">";
	}
	
	

}
