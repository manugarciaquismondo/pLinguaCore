package org.gcn.plinguacore.util.psystem.simplekernel.membrane;

import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembrane;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembraneStructure;

public class SimpleKernelLikeMembrane extends TissueLikeMembrane {

	protected SimpleKernelLikeMembrane(Membrane membrane,
			TissueLikeMembraneStructure structure) {
		super(membrane, structure);
		// TODO Auto-generated constructor stub
	}

	protected SimpleKernelLikeMembrane(String label, MultiSet<String> multiSet,
			TissueLikeMembraneStructure structure) {
		super(label, multiSet, structure);
		// TODO Auto-generated constructor stub
	}

	protected SimpleKernelLikeMembrane(String label,
			TissueLikeMembraneStructure structure) {
		super(label, structure);
		// TODO Auto-generated constructor stub
	}

}
