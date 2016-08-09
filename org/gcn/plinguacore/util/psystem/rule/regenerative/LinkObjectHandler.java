package org.gcn.plinguacore.util.psystem.rule.regenerative;

import java.util.Set;

import org.gcn.plinguacore.util.HashMultiSet;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.regenerative.membrane.RegenerativeMembraneStructure;
import org.gcn.plinguacore.util.psystem.rule.RightHandRule;
import org.gcn.plinguacore.util.psystem.simplekernel.membrane.SimpleKernelLikeMembraneStructure;

public class LinkObjectHandler {
	
	protected int requiredMembranes;
	protected RightHandRule rightHandRule;
	protected Set<String> linkObjects;
	protected String generatedLinkObject;
	public LinkObjectHandler(RightHandRule rightHandRule,int requiredMembranes){
		if (rightHandRule == null) {
			throw new IllegalArgumentException("Argument of type "
					+ RightHandRule.class
					+ " cannot be null when creating an object of type "
					+ getClass());
		}
		this.rightHandRule = rightHandRule;
		this.requiredMembranes=requiredMembranes;
	}
	
	public Set<String> getLinkObjects() {
		return linkObjects;
	}

	public String getGeneratedLinkObject() {
		return generatedLinkObject;
	}


	
	public void setLinkObjects(Set<String> linkObjects) throws PlinguaCoreException{
		if(linkObjects==null)
			throw new PlinguaCoreException("The input link object set cannot be null");
		this.linkObjects=linkObjects;
		if(requiredMembranes>=1){
			MultiSet<String> affectedMultiSet=new HashMultiSet<String>(rightHandRule.getAffectedMembranes().get(0).getMultiSet());
			affectedMultiSet.retainAll(linkObjects);
			if(!affectedMultiSet.isEmpty()){
				generatedLinkObject=affectedMultiSet.entrySet().iterator().next();
			}
		}
	}
	
	public RegenerativeMembraneStructure transformMembraneStructure(
			SimpleKernelLikeMembraneStructure membraneStructure) {
		// TODO Auto-generated method stub
		if(membraneStructure instanceof RegenerativeMembraneStructure){
			return (RegenerativeMembraneStructure)membraneStructure;
		}else{
			return  new RegenerativeMembraneStructure(membraneStructure);
		}
		
	}

}
