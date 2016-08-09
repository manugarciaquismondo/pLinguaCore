package org.gcn.plinguacore.util.psystem.rule.simpleregenerative;

import java.util.List;

import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.rule.OuterRuleMembrane;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembraneStructure;

public class MembraneStructureCounter {

	public int countExistingMembranes(List<OuterRuleMembrane> affectedMembranes, OuterRuleMembrane secondRightHandMembrane, TissueLikeMembraneStructure structure){
		int membraneCounter=0;
		if(affectedMembranes!=null){
			for(OuterRuleMembrane membrane: affectedMembranes){
				membraneCounter+=structure.countMembranes(membrane.getLabel());
				
			}
		}
		if(secondRightHandMembrane!=null){
			membraneCounter+=structure.countMembranes(secondRightHandMembrane.getLabel());
		}
		return membraneCounter;
	}
}
