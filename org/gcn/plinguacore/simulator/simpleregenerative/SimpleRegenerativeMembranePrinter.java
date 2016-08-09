package org.gcn.plinguacore.simulator.simpleregenerative;

import java.util.LinkedList;
import java.util.List;

import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;
import org.gcn.plinguacore.util.psystem.regenerative.membrane.RegenerativeMembrane;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembraneStructure;

public class SimpleRegenerativeMembranePrinter {
	
	public String printMembranes(RegenerativeMembrane membrane){
		MembraneStructure structure=((RegenerativeMembrane)membrane).getStructure();
		List<Integer> linkedMembranesIDs=(membrane).getLinkedMembranes();
		List<String> linkedMembranesLabels=new LinkedList<String>();
		for(Integer membraneID: linkedMembranesIDs){
			linkedMembranesLabels.add("("+structure.getMembrane(membraneID).getLabel()+")");
		}
		return linkedMembranesLabels+"";
	}

}
