package org.gcn.plinguacore.util.psystem.tissueLike.membrane;

import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembraneStructure;

public class TissueLikeMembraneFactory {
	public static TissueLikeMembrane getTissueLikeMembrane(Membrane membrane,
			TissueLikeMembraneStructure structure){
		return new TissueLikeMembrane(membrane, structure);
	}
	
	public static TissueLikeMembrane getTissueLikeMembrane(String label, MultiSet<String> multiSet,
			TissueLikeMembraneStructure structure) {
		return new TissueLikeMembrane(label, multiSet, structure);
	}
	
	public static TissueLikeMembrane getKernelLikeMembrane(String label,
			TissueLikeMembraneStructure structure) {
		return new TissueLikeMembrane(label, structure);
	}

}
