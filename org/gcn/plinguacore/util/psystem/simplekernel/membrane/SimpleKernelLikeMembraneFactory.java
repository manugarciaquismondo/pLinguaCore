package org.gcn.plinguacore.util.psystem.simplekernel.membrane;

import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembraneStructure;

public class SimpleKernelLikeMembraneFactory {
	
	public static SimpleKernelLikeMembrane getKernelLikeMembrane(Membrane membrane,
			TissueLikeMembraneStructure structure){
		return new SimpleKernelLikeMembrane(membrane, structure);
	}
	
	public static SimpleKernelLikeMembrane getKernelLikeMembrane(String label, MultiSet<String> multiSet,
			TissueLikeMembraneStructure structure) {
		return new SimpleKernelLikeMembrane(label, multiSet, structure);
		// TODO Auto-generated constructor stub
	}
	
	public static SimpleKernelLikeMembrane getKernelLikeMembrane(String label,
			TissueLikeMembraneStructure structure) {
		return new SimpleKernelLikeMembrane(label, structure);
		// TODO Auto-generated constructor stub
	}

}
